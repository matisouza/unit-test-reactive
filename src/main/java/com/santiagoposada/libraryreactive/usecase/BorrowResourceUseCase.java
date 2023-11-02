package com.santiagoposada.libraryreactive.usecase;


import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class BorrowResourceUseCase implements Function<String, Mono<String>> {
    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private UpdateUseCase updateUseCase;

    public BorrowResourceUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository, UpdateUseCase updateUseCase){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
        this.updateUseCase = updateUseCase;
    }

    @Override
    public Mono<String> apply(String id) {
        if(id == null) return Mono.error(new IllegalArgumentException("Id required to borrow a book"));
        return resourceRepository.findById(id).flatMap(
                resource->{
                    if(resource.getUnitsAvailable() > 0){
                        resource.setUnitsOwed(resource.getUnitsOwed() + 1);
                        resource.setUnitsAvailable(resource.getUnitsAvailable() - 1);
                        resource.setLastBorrow(LocalDate.now());
                        return updateUseCase.apply(resourceMapper.fromResourceEntityToDTO().apply(resource))
                                .thenReturn("The resource "
                                        + resource.getName() + " has been borrowed, there are "
                                        + resource.getUnitsAvailable() + " units available");
                    }
                    return Mono.just("There arent units left to be borrow of that resource");
                }
        );
    }
}
