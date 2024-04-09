package com.romeo.birdssighting.mapper;

import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.SightingDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class SightingMapper extends BaseMapper<Sighting, SightingDTO> {


    @Override
    public Sighting convertToEntity(SightingDTO dto, Object... args) {
        Sighting sighting = new Sighting();

        if (dto != null) {
            BeanUtils.copyProperties(dto, sighting);
        }
        return sighting;
    }

    @Override
    public SightingDTO convertToDto(Sighting entity, Object... args) {
        SightingDTO sightingDTO = new SightingDTO();

        if (entity != null) {
            BeanUtils.copyProperties(entity, sightingDTO);
        }

        return sightingDTO;
    }
}
