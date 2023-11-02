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
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetByTypeUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    @SpyBean
    private GetByTypeUseCase getByTypeUseCase;

    @BeforeEach
    public void setup() {
        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        getByTypeUseCase = new GetByTypeUseCase(resourceMapper, resourceRepository);
    }

    @Test
    public void testGetByTypeUseCase() {
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

        List<Resource> resources = List.of(resource);

        when(resourceRepository.findAllByType(resource.getType())).thenReturn(Flux.fromIterable(resources));
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO);

        StepVerifier.create(getByTypeUseCase.apply(resource.getType()))
                .expectNext(resourceDTO)
                .verifyComplete();
    }
}