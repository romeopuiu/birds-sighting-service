package com.romeo.birdssighting.service;


import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repository.IBirdRepository;
import com.romeo.birdssighting.repository.ISightingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class represents a service component responsible
 * for managing business logic related to sightings
 */

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class SightingService {

    // Dependency on ISightingRepository for database operations
    private final ISightingRepository iSightingRepository;
    // Dependency on SightingMapper for entity-DTO mapping
    private final SightingMapper sightingMapper;
    // Dependency on IBirdRepository for database operations
    private final IBirdRepository iBirdRepository;

    /**
     * This method is used to save a sighting
     */
    public SightingDTO createSighting(Long birdId, SightingDTO sightingDTO) {
        log.info("Save a SightingDTO: {}", sightingDTO);
        // Retrieve the Bird entity from the database
        var bird = iBirdRepository.findById(birdId)
                .orElseThrow(() -> new EntityNotFoundException("Bird with birdId not found" + birdId));

        var sighting = sightingMapper.convertToEntity(sightingDTO);
        sighting.setBird(bird);
        iSightingRepository.save(sighting);

        return sightingMapper.convertToDto(sighting);
    }

    /**
     * This method is used to update sighting.
     */
    public SightingDTO updateSighting(Long id, SightingDTO sightingDTO) {
        log.info("Update a SightingDTO: {}", sightingDTO);
        var sighting = iSightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sighting not exist with id :" + id));

        sighting.setLocation(sightingDTO.getLocation());
        sighting.setDateTime(sightingDTO.getDateTime());
        iSightingRepository.save(sighting);

        return sightingMapper.convertToDto(sighting);
    }

    /**
     * This method is used to returns all sightings by location.
     */
    public List<SightingDTO> findByLocation(String location) {
        log.info("Get all sightings by location: {}", location);
        var birds = iSightingRepository.findByLocation(location);
        return new ArrayList<>(sightingMapper.convertToDto(birds));
    }

    /**
     * This method is used to returns all sightings by date time.
     */
    public List<SightingDTO> findByDateTime(LocalDateTime dateTime) {
        log.info("Get all sightings by dateTime: {}", dateTime);
        var birds = iSightingRepository.findByDateTime(dateTime);
        return new ArrayList<>(sightingMapper.convertToDto(birds));
    }

    /**
     * This method is used to returns all sightings allocated to id of bird.
     */
    public List<SightingDTO> findAllSightingsByBirdId(Long birdId) {
        if (!iBirdRepository.existsById(birdId)) {
           throw new ResourceNotFoundException("Bird not exist with id: " + birdId);
        }

        var sightings = iSightingRepository.findByBirdId(birdId);
        return new ArrayList<>(sightingMapper.convertToDto(sightings));
    }

    /**
     * This method is used to returns all sightings.
     */
    public List<SightingDTO> findAllSightings() {
        List<Sighting> sightings = iSightingRepository.findAll();

        return new ArrayList<>(sightingMapper.convertToDto(sightings));
    }

    /**
     * This method is used to returns a SightingDTO object by id.
     */
    public SightingDTO findSighting(Long id) {
        var sighting = iSightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sighting not exist with id:" + id));

        return sightingMapper.convertToDto(sighting);
    }

    /**
     * This method is used to delete sighting by id
     */
    public void deleteSighting(Long id) {
        if (iSightingRepository.existsById(id)) {
            iSightingRepository.deleteById(id);
        } else  {
            throw new ResourceNotFoundException("Sighting not exist with id :" + id);
        }
    }
}
