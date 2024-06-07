package com.romeo.birdssighting.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import java.util.List;


/**
 *
 This class represents a data model for a bird entity in a database
 */
@Entity
@Table(name = "bird")
@Getter
@Setter
public class Bird {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "bird", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sighting> sightings;

    @Column(name = "color")
    private String color;

    @Column(name = "weight")
    private int weight;

    @Column(name = "height")
    private int height;
}
