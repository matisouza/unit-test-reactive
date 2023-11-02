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

    private GetResourcesByAvailabilityUseCase getResourcesByAvailabilityUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        getResourcesByAvailabilityUseCase = Mockito.mock(GetResourcesByAvailabilityUseCase.class);
        createResourceUseCase = Mockito.mock(CreateResourceUseCase.class);
        getAllUseCase = Mockito.mock(GetAllUseCase.class);
        getResourceByIdUseCase = Mockito.mock(GetResourceByIdUseCase.class);
        updateUseCase = Mockito.mock(UpdateUseCase.class);

        resourceRouter = new ResourceRouter();
        webTestClient = WebTestClient.bindToRouterFunction(resourceRouter.getResourcesByAvilabilityRouter(getResourcesByAvailabilityUseCase)
                .and(resourceRouter.createResourceRoute(createResourceUseCase))).build();
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
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResourceDTO.class);
    }
   /** @Test
    public void getAllRouterTest() {
        ResourceDTO resourceDTO = new ResourceDTO();
        when(getAllUseCase.get()).thenReturn(Flux.just(resourceDTO));

        webTestClient.get().uri("/resources")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResourceDTO.class);

        Mockito.verify(getAllUseCase, times(1)).get();
    }
    */
    @Test
    public void testGetResourceById() {
    }
    @Test
    void updateResourceRoute() {
    }

    @Test
    void deleteResourceToute() {
    }

    @Test
    void checkForAvailabilityRoute() {
    }

    @Test
    void getByTypeRoute() {
    }

    @Test
    void getByCategory() {
    }

    @Test
    void borrowResourceRoute() {
    }

    @Test
    void returnRoute() {
    }
}