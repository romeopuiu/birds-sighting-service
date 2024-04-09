package com.romeo.birdssighting.mapper;

import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.dto.BirdDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 *  This class serves as a mapper between Bird entity objects and BirdDTO
 *  data transfer objects, providing methods for converting between the two representations
 */

@Component
public class BirdMapper extends BaseMapper<Bird, BirdDTO>{

    // Method to convert a BirdDTO to a Bird entity
    @Override
    public Bird convertToEntity(BirdDTO dto, Object... args) {
        Bird bird = new Bird();
        // Copies properties from DTO to entity using Spring's BeanUtils
        if (dto != null) {
            BeanUtils.copyProperties(dto, bird);
        }

        return bird;
    }
    // Method to convert a Bird entity to a BirdDTO
    @Override
    public BirdDTO convertToDto(Bird entity, Object... args) {
        BirdDTO birdDTO = new BirdDTO();
        // Copies properties from entity to DTO using Spring's BeanUtils
        if (entity != null) {
            BeanUtils.copyProperties(entity, birdDTO);
        }

        return birdDTO;
    }
}
