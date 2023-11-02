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

public class ReturnUseCaseTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ResourceMapper resourceMapper;

    @Mock
    private UpdateUseCase updateUseCase;

    private ReturnUseCase returnUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        returnUseCase = new ReturnUseCase(resourceMapper, resourceRepository, updateUseCase);
    }

    @Test
    public void applyTest() {
        String id = "1";
        Resource resource = new Resource();
        resource.setId(id);
        resource.setUnitsOwed(1);
        resource.setUnitsAvailable(0);

        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setId(id);
        resourceDTO.setUnitsOwed(0);
        resourceDTO.setUnitsAvailable(1);

        when(resourceRepository.findById(id)).thenReturn(Mono.just(resource));
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO);
        when(updateUseCase.apply(resourceDTO)).thenReturn(Mono.just(resourceDTO));

        StepVerifier.create(returnUseCase.apply(id))
                .expectNext("The resource with id: "
                        + resourceDTO.getId() + "was returned successfully")
                .verifyComplete();
    }
}