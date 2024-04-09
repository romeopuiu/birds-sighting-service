package com.romeo.birdssighting.unit.service;

import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repository.IBirdRepository;
import com.romeo.birdssighting.repository.ISightingRepository;
import com.romeo.birdssighting.service.SightingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class SightingServiceTest {

    @Mock
    private ISightingRepository iSightingRepository;

    @Mock
    private SightingMapper sightingMapper;

    @Mock
    private IBirdRepository iBirdRepository;

    @InjectMocks
    private SightingService sightingService;

    /**
     * This method is used for testing to find all sightings by bird when bird exist
     */
    @Test
    public void testFindAllSightingsByBirdId_WhenBirdExists() {
        var birdId = 1L;
        var bird = new Bird();

        var sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        Sighting sighting = new Sighting();
        sighting.setId(1L);
        sighting.setLocation("London");
        bird.setId(birdId);

        List<Sighting> sightings = new ArrayList<>();
        List<SightingDTO> sightingDTOS = new ArrayList<>();
        sightingDTOS.add(sightingDTO);
        sightings.add(sighting);

        when(iBirdRepository.existsById(birdId)).thenReturn(true);
        when(iSightingRepository.findByBirdId(birdId)).thenReturn(sightings);
        when(sightingMapper.convertToDto(sightings)).thenReturn(sightingDTOS);

        var result = sightingService.findAllSightingsByBirdId(birdId);

        assertEquals(1, result.size());
        verify(iBirdRepository).existsById(birdId);

        verify(sightingMapper).convertToDto(sightings);
        verify(iSightingRepository).findByBirdId(birdId);
        verify(sightingMapper, times(1)).convertToDto(sightings);

    }

    /**
     * This method is used for testing to find all sightings by bird when bird does not exist
     */
    @Test
    public void testFindAllSightingsByBirdId_WhenBirdDoesNotExist() {
        var birdId = 1L;

        when(iBirdRepository.existsById(birdId)).thenReturn(false);

        // Call the method under test and assert that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,
                () -> sightingService.findAllSightingsByBirdId(birdId));

        verify(iBirdRepository).existsById(birdId);
        verifyNoInteractions(iSightingRepository);
    }

    /**
     * This method is used for testing to find a sighting when exist
     */
    @Test
    public void testFindSighting_WhenSightingExists() {
        Long sightingId = 1L;
        var sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        Sighting sighting = new Sighting();
        sighting.setId(sightingId);

        when(iSightingRepository.findById(sightingId)).thenReturn(Optional.of(sighting));
        when(sightingMapper.convertToDto(sighting)).thenReturn(sightingDTO);

        var result = sightingService.findSighting(sightingId);

        verify(iSightingRepository).findById(sightingId);
        verify(sightingMapper).convertToDto(sighting);
        assertEquals(sightingId, result.getId());
    }

    /**
     * This method is used for testing sightings by location does not Exist
     */
    @Test
    public void testFindSighting_WhenSightingDoesNotExist() {
        var sightingId = 1L;

        when(iSightingRepository.findById(sightingId)).thenReturn(Optional.empty());

        // Call the method under test and assert that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> sightingService.findSighting(sightingId));

        verify(iSightingRepository).findById(sightingId);
        verifyNoMoreInteractions(sightingMapper);
    }

    /**
     * This method is used for testing all sightings by location when sightings exist
     */
    @Test
    public void testFindByLocation_WhenSightingsExist() {
        var location = "Bucharest";
        var sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        Sighting sighting = new Sighting();
        sighting.setId(1L);
        sighting.setLocation("Test");

        List<Sighting> sightings = new ArrayList<>();
        List<SightingDTO> sightingDTOS = new ArrayList<>();
        sightingDTOS.add(sightingDTO);
        sightings.add(sighting);

        when(iSightingRepository.findByLocation(location)).thenReturn(sightings);
        when(sightingMapper.convertToDto(sightings)).thenReturn(sightingDTOS);

        var result = sightingService.findByLocation(location);

        verify(iSightingRepository).findByLocation(location);
        verify(sightingMapper, times(1)).convertToDto(sightings);
        assertEquals(1, result.size());
    }

    /**
     * This method is used for testing all sightings by Date Time when sightings exist
     */
    @Test
    public void testFindByDateTime_WhenSightingsExist() {
        var dateTime = LocalDateTime.now();
        var sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        Sighting sighting = new Sighting();
        sighting.setId(1L);
        sighting.setLocation("Test");

        List<Sighting> sightings = new ArrayList<>();
        List<SightingDTO> sightingDTOS = new ArrayList<>();
        sightingDTOS.add(sightingDTO);
        sightings.add(sighting);

        when(iSightingRepository.findByDateTime(dateTime)).thenReturn(sightings);
        when(sightingMapper.convertToDto(sightings)).thenReturn(sightingDTOS);

        List<SightingDTO> result = sightingService.findByDateTime(dateTime);

        verify(iSightingRepository).findByDateTime(dateTime);
        verify(sightingMapper, times(1)).convertToDto(sightings);
        assertEquals(1, result.size());
    }
    /**
     * This method is used for testing all sightings by Date Time when sightings do not exist
     */
    @Test
    public void testFindByDateTime_WhenSightingsDoNotExist() {
        var dateTime = LocalDateTime.now();
        when(iSightingRepository.findByDateTime(dateTime)).thenReturn(Collections.emptyList());

        var result = sightingService.findByDateTime(dateTime);

        verify(iSightingRepository).findByDateTime(dateTime);
        assertEquals(0, result.size());
    }
    /**
     * This method is used for testing all sightings when do not exist
     */
    @Test
    public void testFindByLocation_WhenSightingsDoNotExist() {
        String location = "Test";
        when(iSightingRepository.findByLocation(location)).thenReturn(Collections.emptyList());
        List<SightingDTO> result = sightingService.findByLocation(location);

        verify(iSightingRepository).findByLocation(location);
        assertEquals(0, result.size());
    }

    /**
     * This method is used for testing for save sighting when a bird exist
     */
    @Test
    public void testCreateSighting_WhenBirdExists() {
        var birdId = 1L;
        var sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        sightingDTO.setLocation("Test");

        var bird = new Bird();
        bird.setId(birdId);
        when(iBirdRepository.findById(birdId)).thenReturn(java.util.Optional.of(bird));

        var sighting = new Sighting();
        when(sightingMapper.convertToEntity(sightingDTO)).thenReturn(sighting);
        when(sightingMapper.convertToDto(sighting)).thenReturn(sightingDTO);
        var result = sightingService.createSighting(birdId, sightingDTO);

        verify(iBirdRepository).findById(birdId);
        verify(sightingMapper).convertToEntity(sightingDTO);
        verify(iSightingRepository).save(sighting);
        verify(sightingMapper).convertToDto(sighting);

        assertEquals(sightingDTO, result);
    }

    /**
     * This method is used for testing for save sighting when a bird does not exist
     */
    @Test(expected = EntityNotFoundException.class)
    public void testSaveSighting_WhenBirdDoesNotExist() {
        var birdId = 1L;
        var sightingDTO = new SightingDTO();
        when(iBirdRepository.findById(birdId)).thenReturn(Optional.empty());

        // Call the method under test - it should throw EntityNotFoundException
        sightingService.createSighting(birdId, sightingDTO);
    }

    /**
     * This method is used for testing for returns all sightings
     */
    @Test
    public void testFindAllSightings() {
        // Mock data
        Sighting sighting1 = new Sighting();
        sighting1.setId(1L);
        Sighting sighting2 = new Sighting();
        sighting2.setId(2L);

        List<Sighting> sightingList = new ArrayList<>();
        sightingList.add(sighting1);
        sightingList.add(sighting2);

        SightingDTO sightingDTO1 = new SightingDTO();
        sightingDTO1.setId(1L);
        SightingDTO sightingDTO2 = new SightingDTO();
        sightingDTO2.setId(2L);

        List<SightingDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(sightingDTO1);
        expectedDTOList.add(sightingDTO2);

        // Mock repository method
        when(iSightingRepository.findAll()).thenReturn(sightingList);

        // Mock mapper method
        when(sightingMapper.convertToDto(sightingList)).thenReturn(expectedDTOList);

        // Call the service method
        List<SightingDTO> actualDTOList = sightingService.findAllSightings();

        // Verify the result
        assertEquals(expectedDTOList, actualDTOList);

        // Verify that the repository method was called once
        verify(iSightingRepository, times(1)).findAll();

        // Verify that the mapper method was called once
        verify(sightingMapper, times(1)).convertToDto(sightingList);
    }

    /**
     * This method is used for testing for update sighting when a sighting exists
     */
    @Test
    public void testUpdateSighting_WhenSightingExists() {
        var id = 1L;
        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation("Bucharest");
        sightingDTO.setDateTime(LocalDateTime.now());

        var sighting = new Sighting();
        sighting.setId(id);
        sighting.setLocation("Bucharest");
        sighting.setDateTime(LocalDateTime.now().minusDays(1));

        // Mock behavior to simulate existing sighting
        when(iSightingRepository.findById(id)).thenReturn(Optional.of(sighting));
        when(iSightingRepository.save(any(Sighting.class))).thenReturn(sighting);
        when(sightingMapper.convertToDto(any(Sighting.class))).thenReturn(sightingDTO);

        // Call the method under test
        var result = sightingService.updateSighting(id, sightingDTO);

        verify(iSightingRepository, times(1)).findById(id);
        verify(iSightingRepository, times(1)).save(any(Sighting.class));
        verify(sightingMapper, times(1)).convertToDto(any(Sighting.class));

        assertEquals(sightingDTO.getLocation(), result.getLocation());
        assertEquals(sightingDTO.getDateTime(), result.getDateTime());
    }

    /**
     * This method is used for testing for update sighting when a sighting does not exist
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateSighting_WhenSightingDoesNotExist() {
        Long id = 1L;
        SightingDTO sightingDTO = new SightingDTO();

        // Mock behavior to simulate non-existing sighting
        when(iSightingRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method under test, which should throw ResourceNotFoundException
        sightingService.updateSighting(id, sightingDTO);
    }

    /**
     * This method is used for testing for delete sighting when a sighting exist
     */
    @Test
    public void testDeleteSightingExists() {
        var id = 1L;

        when(iSightingRepository.existsById(id)).thenReturn(true);

        sightingService.deleteSighting(id);

        // Verify that deleteByBirdId is called with the correct id
        verify(iSightingRepository, times(1)).deleteById(id);
    }

    /**
     *  This method is used for testing for delete sighting when a sighting exist
     */
    @Test
    public void testDeleteSightingDoesNotExist_ShouldThrowException() {
        // Mock data
        var id = 1L;

        // Mock behavior to simulate non-existing sighting
        when(iSightingRepository.existsById(id)).thenReturn(false);

        // Call the method under test and assert that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> sightingService.deleteSighting(id));
    }
}
