package com.santiagoposada.libraryreactive.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Resource {

    @Id
    private String id;
    private String name;
    private String category;
    private String type;
    private LocalDate lastBorrow;
    private Integer unitsOwed;
    private Integer unitsAvailable;

    public Resource() {
    }

    public Resource(String id, String name, String category, String type, LocalDate lastBorrow, Integer unitsOwed, Integer unitsAvailable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.lastBorrow = lastBorrow;
        this.unitsOwed = unitsOwed;
        this.unitsAvailable = unitsAvailable;
    }

    public Resource(String name, String category, String type, LocalDate lastBorrow, Integer unitsOwed, Integer unitsAvailable) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.lastBorrow = lastBorrow;
        this.unitsOwed = unitsOwed;
        this.unitsAvailable = unitsAvailable;
    }
}
