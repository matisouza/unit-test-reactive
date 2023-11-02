package com.santiagoposada.libraryreactive.repository;

import com.santiagoposada.libraryreactive.entity.Resource;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ResourceRepository extends ReactiveCrudRepository<Resource, String> {
    Flux<Resource> findAllByType(String type);
    Flux<Resource> findAllByCategory(String category);
}
