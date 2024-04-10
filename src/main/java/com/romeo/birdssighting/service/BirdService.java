package com.romeo.birdssighting.service;


import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.BirdMapper;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repository.IBirdRepository;
import com.romeo.birdssighting.repository.ISightingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class represents a service component responsible
 * for managing business logic related to birds
 */

@Transactional
@Service
@RequiredArgsConstructor
public class BirdService {

    // Dependency on IBirdRepository for database operations
    private final IBirdRepository iBirdRepository;
    // Dependency on BirdMapper for entity-DTO mapping
    private final BirdMapper birdMapper;

    private final ISightingRepository iSightingRepository;
    private final SightingMapper sightingMapper;

    /**
     * This method is used for save a bird
     */
    public BirdDTO saveBird(BirdDTO birdDTO) {
        // Convert BirdDTO to Bird entity
        var bird = birdMapper.convertToEntity(birdDTO);

        // Convert and save Sightings
        var sightingDTOS = birdDTO.getSightings();
        if (sightingDTOS != null && !sightingDTOS.isEmpty()) {
            List<Sighting> sightings = (List<Sighting>) sightingMapper.convertToEntity(sightingDTOS);
            Bird finalBird = bird;
            sightings.forEach(sighting -> {
                // Set the associated bird for each sighting
                sighting.setBird(finalBird);
            });
            iSightingRepository.saveAll(sightings);
            // Update the bird with the saved sightings
            bird.setSightings(sightings);
            bird = iBirdRepository.save(bird);
        }

        return birdMapper.convertToDto(bird);
    }

    /**
     * This method is used for update a bird.
     */
    public BirdDTO updateBird(Long id, BirdDTO birdDTO) {
        var bird = iBirdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bird not exist with id :" + id));

        bird.setName(birdDTO.getName());
        bird.setColor(birdDTO.getColor());
        bird.setHeight(birdDTO.getHeight());
        bird.setWeight(birdDTO.getWeight());
        List<Sighting> sightings = (List<Sighting>) sightingMapper.convertToEntity(birdDTO.getSightings());
        sightings.forEach(sighting -> {
            // Set the associated bird for each sighting
            sighting.setBird(bird);
        });
        iSightingRepository.saveAll(sightings);
        bird.setSightings(sightings);
        iBirdRepository.save(bird);

        return birdMapper.convertToDto(bird);
    }

    /**
     * This method is used for returns all birds.
     */
    public List<BirdDTO> getAllBirds() {
        // Retrieve all Bird entities from the repository
        List<Bird> birds = iBirdRepository.findAll();

        // Convert Bird entities to BirdDTO objects
        List<BirdDTO> birdDTOs = new ArrayList<>();
        for (Bird bird : birds) {
            BirdDTO birdDTO = birdMapper.convertToDto(bird);
            List<Sighting> sightings = iSightingRepository.findByBird(bird);
            List<SightingDTO> sightingDTOs = (List<SightingDTO>) sightingMapper.convertToDto(sightings);
            birdDTO.setSightings(sightingDTOs);
            birdDTOs.add(birdDTO);
        }

        return birdDTOs;
    }

    /**
     * This method is used to returns a bird by name
     */
    public BirdDTO findBirdByName(String name) {
        var bird = iBirdRepository.findByName(name);
        if (bird == null) {
            throw new ResourceNotFoundException("Bird not found with name: " + name);
        }

        return birdMapper.convertToDto(bird);
    }

    /**
     * This method is used to returns a bird by name
     */
    public BirdDTO findBirdByColor(String color) {
        Bird bird = iBirdRepository.findByColor(color);
        if (bird == null) {
            throw new ResourceNotFoundException("Bird not found with color: " + color);
        }
        return birdMapper.convertToDto(bird);

    }

    /**
     * This method is used to delete a bird by id
     */
    public void deleteBird(Long id) {
        if (iBirdRepository.existsById(id)) {
            iBirdRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Bird not exist with id :" + id);
        }
    }
}
