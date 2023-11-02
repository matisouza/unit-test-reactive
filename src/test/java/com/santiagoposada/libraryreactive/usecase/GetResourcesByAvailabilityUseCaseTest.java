package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import com.santiagoposada.libraryreactive.usecase.GetResourcesByAvailabilityUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.Mockito.when;

public class GetResourcesByAvailabilityUseCaseTest {

    private ResourceRepository resourceRepository;

    private ResourceMapper resourceMapper;

    private GetResourcesByAvailabilityUseCase getResourcesByAvailabilityUseCase;

    @BeforeEach
    public void setup() {
        resourceRepository = Mockito.mock(ResourceRepository.class);
        resourceMapper = Mockito.mock(ResourceMapper.class);
        getResourcesByAvailabilityUseCase = new GetResourcesByAvailabilityUseCase(resourceMapper, resourceRepository);
    }

    @Test
    public void getTest() {
        Resource resource1 = new Resource();
        resource1.setUnitsAvailable(1);
        Resource resource2 = new Resource();
        resource2.setUnitsAvailable(2);

        ResourceDTO resourceDTO1 = new ResourceDTO();
        resourceDTO1.setUnitsAvailable(1);
        ResourceDTO resourceDTO2 = new ResourceDTO();
        resourceDTO2.setUnitsAvailable(2);

        when(resourceRepository.findAll()).thenReturn(Flux.just(resource1, resource2));
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO1, r -> resourceDTO2);

        StepVerifier.create(getResourcesByAvailabilityUseCase.get())
                .expectNext(resourceDTO2, resourceDTO1)
                .verifyComplete();
    }
}