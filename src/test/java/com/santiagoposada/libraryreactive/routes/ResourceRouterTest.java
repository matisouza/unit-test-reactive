package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ResourceRouterTest {

    @Autowired
    private ResourceRouter resourceRouter;
    @Autowired
    private WebTestClient webTestClient;
    private UpdateUseCase updateUseCase;
    private GetResourceByIdUseCase getResourceByIdUseCase;
    private GetAllUseCase getAllUseCase;
    private CreateResourceUseCase createResourceUseCase;

    private DeleteResourceUseCase deleteResourceUseCase;

    private BorrowResourceUseCase borrowResourceUseCase;

    private CheckAvailabilityUseCase checkAvailabilityUseCase;

    private GetByCategoryUseCase getByCategoryUseCase;

    private GetByTypeUseCase getByTypeUseCase;

    private ReturnUseCase returnUseCase;
    private GetResourcesByAvailabilityUseCase getResourcesByAvailabilityUseCase;

    @BeforeEach
    public void setup() {
        getResourcesByAvailabilityUseCase = Mockito.mock(GetResourcesByAvailabilityUseCase.class);
        createResourceUseCase = Mockito.mock(CreateResourceUseCase.class);
        getAllUseCase = Mockito.mock(GetAllUseCase.class);
        getResourceByIdUseCase = Mockito.mock(GetResourceByIdUseCase.class);
        updateUseCase = Mockito.mock(UpdateUseCase.class);
        deleteResourceUseCase = Mockito.mock(DeleteResourceUseCase.class);
        borrowResourceUseCase = Mockito.mock(BorrowResourceUseCase.class);
        checkAvailabilityUseCase = Mockito.mock(CheckAvailabilityUseCase.class);
        getByCategoryUseCase = Mockito.mock(GetByCategoryUseCase.class);
        getByTypeUseCase = Mockito.mock(GetByTypeUseCase.class);
        returnUseCase = Mockito.mock(ReturnUseCase.class);

       resourceRouter = Mockito.spy(new ResourceRouter());

        webTestClient = WebTestClient
                .bindToRouterFunction(
                        resourceRouter.getResourcesByAvilabilityRouter(getResourcesByAvailabilityUseCase)
                            .and(resourceRouter.createResourceRoute(createResourceUseCase)
                            .and(resourceRouter.getAllRouter(getAllUseCase))
                            .and(resourceRouter.getResourceById(getResourceByIdUseCase))
                            .and(resourceRouter.updateResourceRoute(updateUseCase))
                            .and(resourceRouter.deleteResourceToute(deleteResourceUseCase))
                            .and(resourceRouter.checkForAvailabilityRoute(checkAvailabilityUseCase))
                            .and(resourceRouter.getByCategory(getByCategoryUseCase))
                            .and(resourceRouter.getByTypeRoute(getByTypeUseCase))
                            .and(resourceRouter.borrowResourceRoute(borrowResourceUseCase))
                            .and(resourceRouter.returnRoute(returnUseCase))
                )).build();
    }

    @Test
    public void getResourcesByAvilabilityRouterTest() {
        ResourceDTO resourceDTO = new ResourceDTO();
        when(getResourcesByAvailabilityUseCase.get()).thenReturn(Flux.just(resourceDTO));

        webTestClient.get().uri("/resourcesAvailable")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResourceDTO.class);
    }
    @Test
    public void createResourceRouteTest() {
        ResourceDTO resourceDTO = new ResourceDTO();
        when(createResourceUseCase.apply(resourceDTO)).thenReturn(Mono.just(resourceDTO));

        webTestClient.post().uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(resourceDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResourceDTO.class);
    }
    @Test
    public void getAllRouterTest() {
        ResourceDTO resourceDTO = new ResourceDTO();
        when(getAllUseCase.get()).thenReturn(Flux.just(resourceDTO));

        webTestClient.get().uri("/resources")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ResourceDTO.class);

        Mockito.verify(getAllUseCase, times(1)).get();
    }

    @Test
    public void testGetResourceById() {
        ResourceDTO resourceDTO = new ResourceDTO();
        when(getResourceByIdUseCase.apply("1")).thenReturn(Mono.just(resourceDTO));

        webTestClient.get().uri("/resource/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResourceDTO.class);

        Mockito.verify(getResourceByIdUseCase, times(1)).apply("1");
    }
    @Test
    void updateResourceRoute() {
        ResourceDTO resourceDTO = new ResourceDTO();
        when(updateUseCase.apply(resourceDTO)).thenReturn(Mono.just(resourceDTO));

        webTestClient.put().uri("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(resourceDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResourceDTO.class);
    }

    @Test
    public void testDeleteResourceRoute() {
        String id = "testId";

        when(deleteResourceUseCase.apply(id)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    void checkForAvailabilityRoute() {
        String id = "1";
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setName("Book");
        resourceDTO.setId(id);

        when(checkAvailabilityUseCase.apply(id)).thenReturn(Mono.just(resourceDTO.getName() + "is available"));

        webTestClient.get().uri("/availability/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }

    @Test
    void getByTypeRoute() {
        String type = "Book";
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setName("Book");
        resourceDTO.setType(type);

        when(getByTypeUseCase.apply(type)).thenReturn(Flux.just(resourceDTO));

        webTestClient.get().uri("/getByType/{type}", type)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDTO.class);
    }

    @Test
    void getByCategory() {
        String category = "Book";
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setCategory(category);

        when(getByCategoryUseCase.apply(category)).thenReturn(Flux.just(resourceDTO));

        webTestClient.get().uri("/getByCategory/{category}", category)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDTO.class);
    }

    @Test
    void borrowResourceRoute() {
        String id = "1";
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setName("Book");
        resourceDTO.setId(id);

        when(borrowResourceUseCase.apply(id)).thenReturn(Mono.just("The resource "
                + resourceDTO.getName() + " has been borrowed, there are "
                + resourceDTO.getUnitsAvailable() + " units available"));

        webTestClient.put().uri("/borrow/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }

    @Test
    void returnRoute() {
        String id = "1";
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setName("Book");
        resourceDTO.setId(id);

        when(returnUseCase.apply(id)).thenReturn(Mono.just(
                "The resource with id: " + resourceDTO.getId() + "was returned successfully"));

        webTestClient.put().uri("/return/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }
}