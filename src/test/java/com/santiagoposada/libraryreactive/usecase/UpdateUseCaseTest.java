package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.Mockito.when;

public class UpdateUseCaseTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ResourceMapper resourceMapper;

    private UpdateUseCase updateUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        updateUseCase = new UpdateUseCase(resourceMapper, resourceRepository);
    }

    @Test
    public void applyTest() {
        String id = "1";
        Resource resource = new Resource();
        resource.setId(id);
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setId(id);

        when(resourceRepository.save(resource)).thenReturn(Mono.just(resource));
        when(resourceMapper.fromResourceDTOtoEntity()).thenReturn(r -> resource);
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO);

        StepVerifier.create(updateUseCase.apply(resourceDTO))
                .expectNext(resourceDTO)
                .verifyComplete();
    }
}