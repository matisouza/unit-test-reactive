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

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetByCategoryUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private GetByCategoryUseCase getByCategoryUseCase;

    @BeforeEach
    public void setup() {
        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        getByCategoryUseCase = new GetByCategoryUseCase(resourceMapper, resourceRepository);
    }

    @Test
    public void testGetByCategoryUseCase() {
        Resource resource = new Resource();
        ResourceDTO resourceDTO = new ResourceDTO();
        List<Resource> resources = List.of(resource);

        when(resourceRepository.findAllByCategory(anyString())).thenReturn(Flux.fromIterable(resources));
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO);

        StepVerifier.create(getByCategoryUseCase.apply(anyString()))
                .expectNext(resourceDTO)
                .verifyComplete();
    }
}