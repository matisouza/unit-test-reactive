package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.when;

public class CheckAvailabilityUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private CheckAvailabilityUseCase checkAvailabilityUseCase;

    @BeforeEach
    public void setup() {
        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        checkAvailabilityUseCase = new CheckAvailabilityUseCase(resourceMapper, resourceRepository);
    }
    @Test
    public void testCheckAvailabilityUseCase() {
        String id = "1";
        Resource resource = new Resource();
        resource.setId(id);
        resource.setName("Resource Name");
        resource.setUnitsAvailable(1);

        when(resourceRepository.findById(id)).thenReturn(Mono.just(resource));

        StepVerifier.create(checkAvailabilityUseCase.apply(id))
                .expectNext(resource.getName() + "is available")
                .verifyComplete();
    }

    @Test
    public void testCheckAvailabilityUseCase_ResourceNotAvailable() {
        String id = "1";
        Resource resource = new Resource();
        resource.setId(id);
        resource.setName("Resource Name");
        resource.setUnitsAvailable(0);
        resource.setLastBorrow(LocalDate.now());

        when(resourceRepository.findById(id)).thenReturn(Mono.just(resource));

        StepVerifier.create(checkAvailabilityUseCase.apply(id))
                .expectNext(resource.getName() + "is not available, last borrow " + resource.getLastBorrow())
                .verifyComplete();
    }
}