package com.santiagoposada.libraryreactive.usecase;


import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class DeleteResourceUseCase implements Function<String, Mono<Void>> {
    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    public DeleteResourceUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Mono<Void> apply(String s) {
        return resourceRepository.deleteById(s);
    }
}
