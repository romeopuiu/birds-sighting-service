package com.romeo.birdssighting.unit.service;

import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.BirdMapper;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repository.IBirdRepository;
import com.romeo.birdssighting.repository.ISightingRepository;
import com.romeo.birdssighting.service.BirdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class BirdServiceTest {

    @Mock
    private IBirdRepository iBirdRepository;
    @Mock
    private ISightingRepository iSightingRepository;

    @Mock
    private  SightingMapper sightingMapper;

    @Mock
    private BirdMapper birdMapper;

    @InjectMocks
    private BirdService birdService;


    /**
     * This method is used for testing get all birds
     */
    @Test
    public void testGetAllBirds() {
        var bird = new Bird();
        bird.setId(1L);
        bird.setName("Test 1");
        bird.setColor("Red");
        bird.setHeight(1);
        bird.setWeight(2);

        // Create a list with one element
        List<Bird> birds = Collections.singletonList(bird);
        //birds.add(bird);
        List<Sighting> sightings = new ArrayList<>();
        List<SightingDTO> sightingDTOS = new ArrayList<>();
        SightingDTO sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        sightingDTOS.add(sightingDTO);
        Sighting sighting = new Sighting();
        sighting.setLocation("London");
        sighting.setId(1L);
        sightings.add(sighting);

        var birdDto = new BirdDTO();
        birdDto.setId(1L);
        birdDto.setName("Test");
        birdDto.setSightings(sightingDTOS);

        // Mock the behavior of iBirdRepository.findAll()
        when(iBirdRepository.findAll()).thenReturn(birds);
        when(iSightingRepository.findByBird(bird)).thenReturn(sightings);
        when(birdMapper.convertToDto(bird)).thenReturn(birdDto);
        when(sightingMapper.convertToDto(sightings)).thenReturn(sightingDTOS);

        // Call the service method
        List<BirdDTO> actualBirdDTOs = birdService.getAllBirds();

        // Verify that the repository method and the mapper methods were called as expected
        verify(iBirdRepository, times(1)).findAll();
        // Verify that the findByBird method was called with the correct bird object
        verify(iSightingRepository, times(1)).findByBird(bird);
        verify(birdMapper, times(1)).convertToDto(bird);
        verify(sightingMapper, times(1)).convertToDto(sightings);

        // Verify that the method returns the expected list of BirdDTO objects
        assertEquals(1, actualBirdDTOs.size());
        assertEquals(birdDto, actualBirdDTOs.get(0));

     /*   var bird = new Bird();
        bird.setId(1L);
        bird.setName("Test 1");
        bird.setColor("Red");
        bird.setHeight(1);
        bird.setWeight(2);

        // Create a list with one element
        List<Bird> birds = Collections.singletonList(bird);
        List<Sighting> sightings = new ArrayList<>();
        List<SightingDTO> sightingDTOS = new ArrayList<>();
        SightingDTO sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        sightingDTOS.add(sightingDTO);
        Sighting sighting = new Sighting();
        sighting.setLocation("London");
        sighting.setId(1L);
        sightings.add(sighting);

        var birdDto = new BirdDTO();
        birdDto.setId(1L);
        birdDto.setName("Test");
        birdDto.setSightings(sightingDTOS);

        when(iBirdRepository.findAll()).thenReturn(birds);
        when(iSightingRepository.findByBird(bird)).thenReturn(sightings);
        when(birdMapper.convertToDto(birds)).thenReturn(Collections.singletonList(birdDto));
        when(sightingMapper.convertToDto(sightings)).thenReturn(sightingDTOS);

        List<BirdDTO> actualBirdDTOs = birdService.getAllBirds();

        verify(iBirdRepository, times(1)).findAll();
        verify(birdMapper, times(1)).convertToDto(birds);
        // Verify that the method returns the expected list of BirdDTO objects
        assertEquals(1, actualBirdDTOs.size());
        assertEquals(birdDto, actualBirdDTOs.get(0));*/

    }
    /**
     * This method is used to testing when a bird is saved
     */
    @Test
    public void testSaveBird() {
        var birdDTO = new BirdDTO();
        birdDTO.setId(1L);
        birdDTO.setName("Test Bird");
        birdDTO.setColor("Red");
        birdDTO.setHeight(10);
        birdDTO.setWeight(20);

        var bird = new Bird();
        bird.setId(1L);
        bird.setName("Test Bird");
        bird.setColor("Red");
        bird.setHeight(10);
        bird.setWeight(20);

        when(birdMapper.convertToEntity(birdDTO)).thenReturn(bird);
        when(iBirdRepository.save(bird)).thenReturn(bird);
        when(birdMapper.convertToDto(bird)).thenReturn(birdDTO);

        var result = birdService.saveBird(birdDTO);

        verify(birdMapper).convertToEntity(birdDTO);
        verify(iBirdRepository).save(bird);
        verify(birdMapper).convertToDto(bird);

        // Verify the result
        assertEquals(birdDTO, result);
    }

    /**
     * This method is used to testing when update a bird when existing
     */
    @Test
    public void testUpdateBird_WhenExisting() {
        var id = 1L;
        var birdDTO = new BirdDTO();
        birdDTO.setName("Test 2");
        birdDTO.setColor("Blue");
        birdDTO.setHeight(10);
        birdDTO.setWeight(20);

        var existingBird = new Bird();
        existingBird.setId(id);
        existingBird.setName("Test 1");
        existingBird.setColor("Green");
        existingBird.setHeight(5);
        existingBird.setWeight(15);

        // Mock behavior
        when(iBirdRepository.findById(id)).thenReturn(java.util.Optional.of(existingBird));
        when(iBirdRepository.save(any(Bird.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(birdMapper.convertToDto(any(Bird.class))).thenAnswer(invocation -> {
            Bird bird = invocation.getArgument(0);
            BirdDTO dto = new BirdDTO();
            dto.setName(bird.getName());
            dto.setColor(bird.getColor());
            dto.setHeight(bird.getHeight());
            dto.setWeight(bird.getWeight());
            return dto;
        });

        var updatedBirdDTO = birdService.updateBird(id, birdDTO);

        // Verify behavior
        verify(iBirdRepository, times(1)).findById(id);
        verify(iBirdRepository, times(1)).save(any(Bird.class));
        assertEquals(birdDTO.getName(), updatedBirdDTO.getName());
        assertEquals(birdDTO.getColor(), updatedBirdDTO.getColor());
        assertEquals(birdDTO.getHeight(), updatedBirdDTO.getHeight());
        assertEquals(birdDTO.getWeight(), updatedBirdDTO.getWeight());
    }

    /**
     * This method is used to testing update a bird when do not exist
     */
    @Test
    public void testUpdateBird_WhenNonExisting_ShouldThrowResourceNotFoundException() {
        var id = 1L;
        var birdDTO = new BirdDTO();
        birdDTO.setName("Test");
        birdDTO.setColor("Green");
        birdDTO.setHeight(10);
        birdDTO.setWeight(20);
        // Mock behavior
        when(iBirdRepository.findById(id)).thenReturn(java.util.Optional.empty());
        // Invoke method and verify exception
        assertThrows(ResourceNotFoundException.class, () -> birdService.updateBird(id, birdDTO));
        // Verify behavior
        verify(iBirdRepository, times(1)).findById(id);
        verify(iBirdRepository, never()).save(any());
    }

    /**
     * This method is used to testing a bird filtered by color when exist
     */
    @Test
    public void testFindBirdByColor_WhenBirdExists() {
        var color = "Red";
        var bird = new Bird();
        bird.setId(1L);
        bird.setColor(color);
        // Mock behavior to simulate the bird existing in the repository
        when(iBirdRepository.findByColor(color)).thenReturn(bird);
        // Mock behavior of birdMapper
        var expectedBirdDTO = new BirdDTO();
        expectedBirdDTO.setId(bird.getId());
        expectedBirdDTO.setColor(bird.getColor());
        when(birdMapper.convertToDto(bird)).thenReturn(expectedBirdDTO);
        // Call the method under test
        var result = birdService.findBirdByColor(color);
        // Verify that findByColor(color) is called once with the specified color
        verify(iBirdRepository, times(1)).findByColor(color);
        // Verify that the result is not null
        assertNotNull(result);
        // Verify that the returned BirdDTO matches the expected one
        assertEquals(expectedBirdDTO.getId(), result.getId());
        assertEquals(expectedBirdDTO.getColor(), result.getColor());
    }

    /**
     * Test that if return bird by color when bird does not exist
     */
    @Test
    public void testFindBirdByColor_WhenBirdDoesNotExist() {
        var color = "Blue";
        // Mock behavior to simulate the bird not existing in the repository
        when(iBirdRepository.findByColor(color)).thenReturn(null);
        // Call the method under test and verify that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> birdService.findBirdByColor(color));
        // Verify that findByColor(color) is called once with the specified color
        verify(iBirdRepository, times(1)).findByColor(color);
    }

    /**
     * Test that if return bird by name when bird exist
     */
    @Test
    public void testFindBirdByName_WhenBirdExists() {
        var name = "Test";
        var bird = new Bird();
        bird.setId(1L);
        bird.setName(name);
        // Mock behavior to simulate the bird existing in the repository
        when(iBirdRepository.findByName(name)).thenReturn(bird);
        // Mock behavior of birdMapper
        BirdDTO expectedBirdDTO = new BirdDTO();
        expectedBirdDTO.setId(bird.getId());
        expectedBirdDTO.setName(bird.getName());
        when(birdMapper.convertToDto(bird)).thenReturn(expectedBirdDTO);

        var result = birdService.findBirdByName(name);
        // Verify that findByName(name) is called once with the specified name
        verify(iBirdRepository, times(1)).findByName(name);
        // Verify that the result is not null
        assertNotNull(result);
        // Verify that the returned BirdDTO matches the expected one
        assertEquals(expectedBirdDTO.getId(), result.getId());
        assertEquals(expectedBirdDTO.getName(), result.getName());
    }

    /**
     * Test that if return bird by name when bird does not exist
     */
    @Test
    public void testFindBirdByName_WhenBirdDoesNotExist() {
        var name = "Test";
        // Simulate the bird not existing in the repository
        when(iBirdRepository.findByName(name)).thenReturn(null);
        // Verify that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> birdService.findBirdByName(name));
        // Verify that findByName(name) is called once with the specified name
        verify(iBirdRepository, times(1)).findByName(name);
    }

    /**
     * Test that to delete a bird if exist
     */
    @Test
    public void testDeleteBirdTest() {
        var id = 1L;
        // Simulate the bird existing in the repository
        when(iBirdRepository.existsById(id)).thenReturn(true);
        doNothing().when(iBirdRepository).deleteById(id);

        birdService.deleteBird(id);

        verify(iBirdRepository, times(1)).existsById(id);
        verify(iBirdRepository, times(1)).deleteById(id);
    }

    /**
     * Test that to delete a bird if do not exist
     */
    @Test
    public void testDeleteNonExistentBirdTest() {
        var nonExistentId = 1L;

        when(iBirdRepository.existsById(nonExistentId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> birdService.deleteBird(nonExistentId));
        verify(iBirdRepository, times(1)).existsById(nonExistentId);
    }
}
