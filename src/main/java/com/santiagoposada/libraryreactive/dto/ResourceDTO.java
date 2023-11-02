package com.santiagoposada.libraryreactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {

    private String id;
    private String name;
    private String category;
    private String type;
    private LocalDate lastBorrow;
    private Integer unitsOwed;
    private Integer unitsAvailable;


}
