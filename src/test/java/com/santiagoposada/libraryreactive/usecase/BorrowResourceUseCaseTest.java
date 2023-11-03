package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BorrowResourceUseCaseTest {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private UpdateUseCase updateUseCase;
    private BorrowResourceUseCase borrowResourceUseCase;

    @BeforeEach
    public void setup() {

        resourceMapper = Mockito.mock(ResourceMapper.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);
        updateUseCase = Mockito.mock(UpdateUseCase.class);
        borrowResourceUseCase = new BorrowResourceUseCase(resourceMapper, resourceRepository, updateUseCase);
    }

    @Test
    public void testBorrowResourceUseCase_ResourceAvailable() {
        Resource resource = new Resource();
        resource.setId("1");
        resource.setName("Nombre #1");
        resource.setUnitsAvailable(10);
        resource.setUnitsOwed(5);

        Resource resourceUpdated = new Resource();
        resourceUpdated.setName(resource.getName());
        resourceUpdated.setUnitsAvailable(9);
        resourceUpdated.setUnitsOwed(6);


        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setId(resource.getId());
        resourceDTO.setName(resource.getName());
        resourceDTO.setUnitsAvailable(resource.getUnitsAvailable());
        resourceDTO.setUnitsOwed(resource.getUnitsOwed());

        when(resourceRepository.findById(resource.getId())).thenReturn(Mono.just(resource));
        when(resourceMapper.fromResourceEntityToDTO()).thenReturn(r -> resourceDTO);
        when(updateUseCase.apply(resourceDTO)).thenReturn(Mono.just(resourceDTO));


        StepVerifier.create(borrowResourceUseCase.apply(resource.getId()))
                .expectNext("The resource "
                        + resourceUpdated.getName() + " has been borrowed, there are "
                        + resourceUpdated.getUnitsAvailable() + " units available")
                .verifyComplete();
    }
    @Test
    public void testBorrowResourceUseCase_ResourceNotAvailable() {
        String id = "1";
        Resource resource = new Resource();
        resource.setId(id);
        resource.setUnitsAvailable(0);

        when(resourceRepository.findById(id)).thenReturn(Mono.just(resource));

        StepVerifier.create(borrowResourceUseCase.apply(id))
                .expectNext("There arent units left to be borrow of that resource")
                .verifyComplete();
    }

    @Test
    public void testRequireNonNullId() {
        String id = null;

        StepVerifier.create(borrowResourceUseCase.apply(id))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Id required to borrow a book"))
                .verify();
    }
}