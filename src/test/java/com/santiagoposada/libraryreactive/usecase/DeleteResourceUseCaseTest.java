package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class DeleteResourceUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private DeleteResourceUseCase deleteResourceUseCase;

    @BeforeEach
    public void setup() {
        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        deleteResourceUseCase = new DeleteResourceUseCase(resourceMapper ,resourceRepository);
    }

    @Test
    public void testDeleteResourceUseCase() {
        String id = "1";

        when(resourceRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(deleteResourceUseCase.apply(id))
                .expectNext()
                .verifyComplete();
    }
}