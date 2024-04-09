package com.romeo.birdssighting.dto;

import lombok.Data;

import java.util.List;

@Data
public class BirdDTO {

    private Long id;
    private String name;
    private List<SightingDTO> sightings;
    private String color;
    private int weight;
    private int height;
}
