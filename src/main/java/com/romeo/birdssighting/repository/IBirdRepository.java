package com.romeo.birdssighting.repository;

import com.romeo.birdssighting.domain.Bird;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for managing persistence operations related to the Bird entity
 */

public interface IBirdRepository extends JpaRepository<Bird, Long> {

    /**
     * This method is used to find bird by name
     */
    Bird findByName(String name);

    /**
     * This method is used to find bird by color
     */
    Bird findByColor(String color);
}
