package com.santiagoposada.libraryreactive.usecase;


import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class CreateResourceUseCase implements CreateResource{

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    public CreateResourceUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Mono<ResourceDTO> apply(ResourceDTO resourceDTO) {
        return resourceRepository.save(resourceMapper.fromResourceDTOtoEntity().apply(resourceDTO))
                .map(resource -> resourceMapper.fromResourceEntityToDTO().apply(resource));
    }
}
