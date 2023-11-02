package com.santiagoposada.libraryreactive.usecase;


import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class CheckAvailabilityUseCase implements Function<String, Mono<String>> {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    public CheckAvailabilityUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
    }



    @Override
    public Mono<String> apply(String id) {
        return resourceRepository.findById(id).flatMap(
                resource -> {
                    if(resource.getUnitsAvailable() > 0){
                        return Mono.just(resource.getName() + "is available");
                    }
                    return Mono.just(resource.getName() + "is not available, last borrow "
                            + resource.getLastBorrow());
                }
        );
    }
}
