package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class GetResourceByIdUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    @SpyBean
    private GetResourceByIdUseCase getResourceByIdUseCase;

    @BeforeEach
    void setup() {
        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        getResourceByIdUseCase = new GetResourceByIdUseCase(resourceMapper, resourceRepository);
    }
    @Test
    public void testGetResourceByIdUseCase() {
        Resource resource = new Resource();
        resource.setId("1");
        resource.setName("Nombre #1");
        resource.setType("Tipo #1");
        resource.setCategory("Area tematica #1");
        resource.setUnitsAvailable(10);
        resource.setUnitsOwed(5);
        resource.setLastBorrow(LocalDate.parse("2020-01-10"));

        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setId(resource.getId());
        resourceDTO.setName(resource.getName());
        resourceDTO.setType(resource.getType());
        resourceDTO.setCategory(resource.getCategory());
        resourceDTO.setUnitsAvailable(resource.getUnitsAvailable());
        resourceDTO.setUnitsOwed(resource.getUnitsOwed());
        resourceDTO.setLastBorrow(resource.getLastBorrow());

        when(resourceRepository.findById(resource.getId())).thenReturn(Mono.just(resource));
        Function<Resource, ResourceDTO> mapperFunction = r -> resourceDTO;
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(mapperFunction);

        StepVerifier.create(getResourceByIdUseCase.apply(resource.getId()))
                .expectNextMatches(rDto ->
                        rDto.getName().equals("Nombre #1")
                        && rDto.getType().equals("Tipo #1")
                        && rDto.getCategory().equals("Area tematica #1")
                        && rDto.getUnitsAvailable().equals(10)
                )
                .verifyComplete();
    }
}