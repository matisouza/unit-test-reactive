package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.ReturnUseCase;
import com.santiagoposada.libraryreactive.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class ResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> getResourcesByAvilabilityRouter(GetResourcesByAvailabilityUseCase getResourcesByAvailabilityUseCase){
        return route(
                GET("/resourcesAvailable"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getResourcesByAvailabilityUseCase.get(), ResourceDTO.class)));
    }
    @Bean
    public RouterFunction<ServerResponse> createResourceRoute(CreateResourceUseCase createResourceUseCase){
        Function<ResourceDTO, Mono<ServerResponse>> executor =
                resourceDTO -> createResourceUseCase.apply(resourceDTO)
                        .flatMap(result-> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(POST("/create")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDTO.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllRouter(GetAllUseCase getAllUseCase){
        return route(GET("/resources"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllUseCase.get(), ResourceDTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getResourceById(GetResourceByIdUseCase getResourceById){
        //.and(accept(MediaType.APPLICATION_JSON))
        return route(GET("/resource/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getResourceById.apply(request.pathVariable("id")), ResourceDTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> updateResourceRoute(UpdateUseCase updateUseCase){
        Function<ResourceDTO, Mono<ServerResponse>> executor =
                resourceDTO -> updateUseCase.apply(resourceDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(PUT("/update")
                .and(accept(MediaType.APPLICATION_JSON)), request -> request
                .bodyToMono(ResourceDTO.class)
                .flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteResourceToute(DeleteResourceUseCase deleteResourceUseCase){
        //.and(accept(MediaType.APPLICATION_JSON))
        return route(
                DELETE("/delete/{id}"),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteResourceUseCase.apply(request.pathVariable("id")), Void.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> checkForAvailabilityRoute(CheckAvailabilityUseCase checkAvailabilityUseCase){
        //.and(accept(MediaType.APPLICATION_JSON))
        return route(GET("/availability/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(checkAvailabilityUseCase.apply(request.pathVariable("id")), String.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getByTypeRoute(GetByTypeUseCase getByTypeUseCase){
        //.and(accept(MediaType.APPLICATION_JSON))
        return route(GET("/getByType/{type}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getByTypeUseCase.apply(request.pathVariable("type")), ResourceDTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getByCategory(GetByCategoryUseCase getByCategoryUseCase){
        //.and(accept(MediaType.APPLICATION_JSON))
        return route(GET("/getByCategory/{category}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getByCategoryUseCase.apply(request.pathVariable("category")), ResourceDTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> borrowResourceRoute(BorrowResourceUseCase borrowResourceUseCase){
        return route(
                PUT("/borrow/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(borrowResourceUseCase.apply(request.pathVariable("id")), String.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> returnRoute(ReturnUseCase returnUseCase){
        return route(
                PUT("/return/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(returnUseCase.apply(request.pathVariable("id")), String.class)));
    }


}
