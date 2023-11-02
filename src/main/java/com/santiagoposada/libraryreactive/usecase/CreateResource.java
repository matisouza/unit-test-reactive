package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@FunctionalInterface
public interface CreateResource {
    Mono<ResourceDTO> apply(@Valid ResourceDTO resourceDTO);
}
