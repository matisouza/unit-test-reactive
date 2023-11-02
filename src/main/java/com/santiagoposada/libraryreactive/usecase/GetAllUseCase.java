package com.santiagoposada.libraryreactive.usecase;


import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllUseCase implements Supplier<Flux<ResourceDTO>> {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    public GetAllUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
    }


    @Override
    public Flux<ResourceDTO> get() {
        return resourceRepository.findAll()
                .map(resource->resourceMapper.fromResourceEntityToDTO().apply(resource));
    }
}
