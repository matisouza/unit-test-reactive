package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.lang.annotation.Annotation;
import java.util.function.Function;

@Service
@Validated
public class GetByCategoryUseCase  implements Function<String, Flux<ResourceDTO>> {
    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    public GetByCategoryUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Flux<ResourceDTO> apply(String category) {
        return resourceRepository.findAllByCategory(category)
                .map(resource -> resourceMapper.fromResourceEntityToDTO().apply(resource));
    }
}
