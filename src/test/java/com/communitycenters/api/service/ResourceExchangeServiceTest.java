package com.communitycenters.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.communitycenters.api.dto.ResourceExchangeDTO;
import com.communitycenters.api.model.CommunityCenter;
import com.communitycenters.api.model.ResourceType;
import com.communitycenters.api.model.ResourceExchange;
import com.communitycenters.api.repository.CommunityCenterRepository;
import com.communitycenters.api.repository.ResourceExchangeRepository;
import com.communitycenters.api.service.ResourceExchangeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResourceExchangeServiceTest {

    @InjectMocks
    private ResourceExchangeService resourceExchangeService;

    @Mock
    private CommunityCenterRepository communityCenterRepository;

    @Mock
    private ResourceExchangeRepository resourceExchangeRepository;

    private CommunityCenter centerFrom;
    private CommunityCenter centerTo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicialização dos centros
        centerFrom = new CommunityCenter("centerFromId", "address", "location", 10, 5, new HashMap<>());
        centerTo = new CommunityCenter("centerToId", "address", "location", 10, 5, new HashMap<>());

        // Adiciona recursos aos centros
        centerFrom.getResources().put(ResourceType.MEDICAL_DOCTOR, 1);
        centerFrom.getResources().put(ResourceType.VOLUNTEER, 1);

        centerTo.getResources().put(ResourceType.MEDICAL_SUPPLIES, 1);
    }

    @Test
    void testExchangeResources_Failure_InvalidPoints() {
        Map<ResourceType, Integer> resourcesOffered = new HashMap<>();
        resourcesOffered.put(ResourceType.MEDICAL_DOCTOR, 1);

        Map<ResourceType, Integer> resourcesRequested = new HashMap<>();
        resourcesRequested.put(ResourceType.MEDICAL_SUPPLIES, 1);

        ResourceExchangeDTO resourceExchangeDTO = new ResourceExchangeDTO(
                "centerFromId",
                "centerToId",
                resourcesOffered,
                resourcesRequested
        );

        when(communityCenterRepository.findById("centerFromId")).thenReturn(Optional.of(centerFrom));
        when(communityCenterRepository.findById("centerToId")).thenReturn(Optional.of(centerTo));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            resourceExchangeService.exchangeResources(resourceExchangeDTO);
        });

        assertEquals("Exchange not allowed, points must be equal!", thrown.getMessage());
    }

}
