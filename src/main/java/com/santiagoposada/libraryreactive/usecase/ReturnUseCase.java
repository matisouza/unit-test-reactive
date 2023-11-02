package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.mapper.ResourceMapper;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;


@Service
@Validated
public class ReturnUseCase implements Function<String, Mono<String>> {


    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;
    private UpdateUseCase updateUseCase;

    public ReturnUseCase(ResourceMapper resourceMapper, ResourceRepository resourceRepository, UpdateUseCase updateUseCase){
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
        this.updateUseCase = updateUseCase;
    }

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "Id is required to return a resource");
        return resourceRepository.findById(id).flatMap(
                resource -> {
                    resource.setUnitsOwed(resource.getUnitsOwed() - 1);
                    resource.setUnitsAvailable(resource.getUnitsAvailable() + 1);
                    return updateUseCase.apply(resourceMapper.fromResourceEntityToDTO().apply(resource))
                            .thenReturn("The resource with id: "
                                    + resource.getId() + "was returned successfully");
                }
        );
    }
}
