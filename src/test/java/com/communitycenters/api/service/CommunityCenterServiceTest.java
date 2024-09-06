package com.communitycenters.api.service;

import com.communitycenters.api.dto.CommunityCenterDTO;
import com.communitycenters.api.model.CommunityCenter;
import com.communitycenters.api.repository.CommunityCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommunityCenterServiceTest {

    @InjectMocks
    private CommunityCenterService communityCenterService;

    @Mock
    private CommunityCenterRepository communityCenterRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private MongoTemplate mongoTemplate;

    private CommunityCenter communityCenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        communityCenter = new CommunityCenter(
                "Test Center",
                "123 Test St",
                "Test Location",
                100,
                50,
                null
        );
    }

    @Test
    void testUpdateOccupancy_MaxCapacity() {
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.of(communityCenter));

        communityCenterService.updateOccupancy("center1", 100);

        assertEquals(100, communityCenter.getOccupancy());
        verify(notificationService, times(1)).sendCapacityReachedNotification(communityCenter);
        verify(communityCenterRepository, times(1)).save(communityCenter);
    }

    @Test
    void testUpdateOccupancy_BelowMaxCapacity() {
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.of(communityCenter));

        communityCenterService.updateOccupancy("center1", 99);

        assertEquals(99, communityCenter.getOccupancy());
        verify(notificationService, never()).sendCapacityReachedNotification(communityCenter);
        verify(communityCenterRepository, times(1)).save(communityCenter);
    }

    @Test
    void testUpdateOccupancy_ExceedCapacity() {
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.of(communityCenter));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            communityCenterService.updateOccupancy("center1", 101); // greater than capacity
        });

        assertEquals("Occupancy cannot exceed capacity", exception.getMessage());
        verify(notificationService, never()).sendCapacityReachedNotification(communityCenter);
        verify(communityCenterRepository, never()).save(communityCenter);
    }

    @Test
    void testUpdateOccupancy_ZeroOccupancy() {
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.of(communityCenter));

        communityCenterService.updateOccupancy("center1", 0);

        assertEquals(0, communityCenter.getOccupancy());
        verify(notificationService, never()).sendCapacityReachedNotification(communityCenter);
        verify(communityCenterRepository, times(1)).save(communityCenter);
    }

    @Test
    void testAddCommunityCenter_OccupancyExceedsCapacity() {
        CommunityCenterDTO centerDTO = new CommunityCenterDTO();
        centerDTO.setCapacity(50);
        centerDTO.setOccupancy(60);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            communityCenterService.addCommunityCenter(centerDTO);
        });

        assertEquals("Occupancy cannot exceed capacity", exception.getMessage());
        verify(communityCenterRepository, never()).save(any());
    }

    @Test
    void testAddCommunityCenter_ValidCenter() {
        CommunityCenterDTO centerDTO = new CommunityCenterDTO();
        centerDTO.setCapacity(100);
        centerDTO.setOccupancy(50);

        when(communityCenterRepository.save(any())).thenReturn(communityCenter);

        CommunityCenter savedCenter = communityCenterService.addCommunityCenter(centerDTO);

        assertNotNull(savedCenter);
        assertEquals(50, savedCenter.getOccupancy());
        assertEquals(100, savedCenter.getCapacity());
        verify(communityCenterRepository, times(1)).save(any(CommunityCenter.class));
    }

    @Test
    void testFindCentersWithHighOccupancy() {
        AggregationResults<CommunityCenter> aggregationResults = mock(AggregationResults.class);
        List<CommunityCenter> expectedCenters = List.of(communityCenter);
        when(aggregationResults.getMappedResults()).thenReturn(expectedCenters);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("community_centers"), eq(CommunityCenter.class)))
                .thenReturn(aggregationResults);

        List<CommunityCenter> result = communityCenterService.findCentersWithHighOccupancy();

        assertEquals(expectedCenters, result);
        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("community_centers"), eq(CommunityCenter.class));
    }

    @Test
    void testGetAverageResourceQuantity() {
        Map<String, Object> resourceData = new HashMap<>();
        resourceData.put("resourceType", "MEDICAL_SUPPLIES");
        resourceData.put("averageQuantity", 10.0);

        AggregationResults<Map> aggregationResults = mock(AggregationResults.class);
        List<Map> expectedResources = List.of(resourceData);
        when(aggregationResults.getMappedResults()).thenReturn(expectedResources);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("community_centers"), eq(Map.class)))
                .thenReturn(aggregationResults);

        List<Map> result = communityCenterService.getAverageResourceQuantity();

        assertEquals(expectedResources, result);
        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("community_centers"), eq(Map.class));
    }

    @Test
    void testUpdateOccupancy_CenterNotFound() {
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            communityCenterService.updateOccupancy("nonExistentId", 50);
        });

        assertEquals("Community Center not found", exception.getMessage());
        verify(communityCenterRepository, never()).save(any(CommunityCenter.class));
    }

    @Test
    void testAddCommunityCenter_OccupancyAtCapacity() {
        CommunityCenterDTO centerDTO = new CommunityCenterDTO();
        centerDTO.setCapacity(100);
        centerDTO.setOccupancy(100);

        CommunityCenter communityCenter = new CommunityCenter(
                centerDTO.getName(),
                centerDTO.getAddress(),
                centerDTO.getLocation(),
                centerDTO.getCapacity(),
                centerDTO.getOccupancy(),
                centerDTO.getResources()
        );

        when(communityCenterRepository.save(any(CommunityCenter.class))).thenReturn(communityCenter);

        CommunityCenter savedCenter = communityCenterService.addCommunityCenter(centerDTO);

        assertNotNull(savedCenter);
        assertEquals(100, savedCenter.getOccupancy());
        assertEquals(100, savedCenter.getCapacity());

        verify(communityCenterRepository, times(1)).save(any(CommunityCenter.class));
    }

    @Test
    void testUpdateOccupancy_SameAsCurrent() {
        // Test updating the occupancy to the same value (should still save)
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.of(communityCenter));

        communityCenterService.updateOccupancy("center1", 50);

        assertEquals(50, communityCenter.getOccupancy());
        verify(notificationService, never()).sendCapacityReachedNotification(communityCenter);
        verify(communityCenterRepository, times(1)).save(communityCenter);
    }

    @Test
    void testAddCommunityCenter_InvalidCenter() {
        CommunityCenterDTO centerDTO = new CommunityCenterDTO();
        centerDTO.setCapacity(50);
        centerDTO.setOccupancy(60); // Invalid

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            communityCenterService.addCommunityCenter(centerDTO);
        });

        assertEquals("Occupancy cannot exceed capacity", exception.getMessage());
        verify(communityCenterRepository, never()).save(any());
    }

    @Test
    void testUpdateOccupancy_SendNotification() {
        when(communityCenterRepository.findById(anyString())).thenReturn(Optional.of(communityCenter));

        communityCenterService.updateOccupancy("center1", 100);

        assertEquals(100, communityCenter.getOccupancy());
        verify(notificationService, times(1)).sendCapacityReachedNotification(communityCenter);
        verify(communityCenterRepository, times(1)).save(communityCenter);
    }
}
