package com.romeo.birdssighting.controller;


import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.service.BirdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * This class represents a REST controller for managing
 * bird-related operations within an API.
 * This class serves as a controller component responsible for
 * handling HTTP requests related to bird resources
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/birds")
public class BirdController {

    private final BirdService birdService;

    /**
     * This method is used for returns all birds
     */
    @GetMapping
    public ResponseEntity<List<BirdDTO>> getAllBirds() {
        return ResponseEntity.ok(birdService.getAllBirds());
    }

    /**
     * This method is used for save a bird
     */
    @PostMapping
    public ResponseEntity<BirdDTO> saveBird(@RequestBody BirdDTO birdDTO) {
        log.info("REST request to save birdDTO: {}", birdDTO);
        return ResponseEntity.ok(birdService.saveBird(birdDTO));
    }

    /**
     * This method is used for returns a bird by color
     */
    @GetMapping("/color")
    public ResponseEntity<BirdDTO> findByColor(@RequestParam String color) {
        log.info("REST request to get a bird by color: {}", color);
        return ResponseEntity.ok(birdService.findBirdByColor(color));
    }

    /**
     *  This method is used for returns a bird by name
     */
    @GetMapping("/name")
    public ResponseEntity<BirdDTO> findByName(@RequestParam String name) {
        log.info("REST request to get a bird by name: {}", name);
        return ResponseEntity.ok(birdService.findBirdByName(name));
    }

    /**
     * This method is used for update a bird
     */
    @PutMapping("/{id}")
    public ResponseEntity<BirdDTO> updateBird(@PathVariable("id") Long id, @RequestBody BirdDTO birdDTO) {
        log.info("REST request to update birdDTO: {}", birdDTO);
        return ResponseEntity.ok(birdService.updateBird(id, birdDTO));
    }

    /**
     * This method is used for delete a bird by id
     */
    @DeleteMapping("/{id}")
    public void deleteBird(@PathVariable Long id) {
        log.info("REST request to delete bird : {}", id);
        birdService.deleteBird(id);
    }
}
