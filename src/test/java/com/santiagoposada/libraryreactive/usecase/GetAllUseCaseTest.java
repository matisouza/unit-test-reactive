package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetAllUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private GetAllUseCase getAllUseCase;

    @BeforeEach
    void setup() {
        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        getAllUseCase = new GetAllUseCase(resourceMapper, resourceRepository);
    }
    @Test
    void testGetAllUseCase() {

        Resource resource = new Resource();
        resource.setId("1");
        resource.setName("Harry Poter");
        resource.setType("nada");
        resource.setCategory("fantasia");
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

        when(resourceRepository.findAll()).thenReturn(Flux.fromIterable(resources));
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO);

        StepVerifier.create(getAllUseCase.get())
                .expectNextMatches(resourceDTO1 ->
                    resourceDTO1.getId().equalsIgnoreCase("1") &&
                    resourceDTO1.getName().equalsIgnoreCase("Harry Poter") &&
                    resourceDTO1.getType().equalsIgnoreCase("nada") &&
                    resourceDTO1.getCategory().equalsIgnoreCase("fantasia") &&
                    resourceDTO1.getUnitsAvailable() == 10
                )
                .verifyComplete();
    }
}