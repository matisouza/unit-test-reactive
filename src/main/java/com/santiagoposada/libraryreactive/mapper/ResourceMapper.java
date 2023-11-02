package com.santiagoposada.libraryreactive.mapper;


import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class ResourceMapper {

    public Function<ResourceDTO, Resource> fromResourceDTOtoEntity(){
        return resourceDTO -> {
            Resource resource = new Resource();
            resource.setId(resourceDTO.getId());
            resource.setName(resourceDTO.getName());
            resource.setCategory(resourceDTO.getCategory());
            resource.setType(resourceDTO.getType());
            resource.setLastBorrow(resourceDTO.getLastBorrow());
            resource.setUnitsAvailable(resourceDTO.getUnitsAvailable());
            resource.setUnitsOwed(resourceDTO.getUnitsOwed());
            return resource;
        };
    }

    public Function<Resource, ResourceDTO> fromResourceEntityToDTO(){
        return resource -> {
            ResourceDTO resourceDTO = new ResourceDTO();
            resourceDTO.setId(resource.getId());
            resourceDTO.setName(resource.getName());
            resourceDTO.setCategory(resource.getCategory());
            resourceDTO.setType(resource.getType());
            resourceDTO.setLastBorrow(resource.getLastBorrow());
            resourceDTO.setUnitsAvailable(resource.getUnitsAvailable());
            resourceDTO.setUnitsOwed(resource.getUnitsOwed());
            return resourceDTO;
        };
    }

}
