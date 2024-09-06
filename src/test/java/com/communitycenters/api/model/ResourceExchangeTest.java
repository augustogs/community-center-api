package com.communitycenters.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceExchangeTest {

    private ResourceExchange resourceExchange;
    private Map<ResourceType, Integer> resourcesOffered;
    private Map<ResourceType, Integer> resourcesRequested;

    @BeforeEach
    void setUp() {
        resourcesOffered = new HashMap<>();
        resourcesOffered.put(ResourceType.MEDICAL_SUPPLIES, 7);
        resourcesOffered.put(ResourceType.VOLUNTEER, 3);

        resourcesRequested = new HashMap<>();
        resourcesRequested.put(ResourceType.BASIC_FOOD_BASKET, 2);
        resourcesRequested.put(ResourceType.TRANSPORT_VEHICLE, 5);

        resourceExchange = new ResourceExchange("center1", "center2", resourcesOffered, resourcesRequested);
    }

    @Test
    void testResourcesOffered() {
        assertEquals(7, resourceExchange.getResourcesOffered().get(ResourceType.MEDICAL_SUPPLIES));
        assertEquals(3, resourceExchange.getResourcesOffered().get(ResourceType.VOLUNTEER));
    }

    @Test
    void testResourcesRequested() {
        assertEquals(2, resourceExchange.getResourcesRequested().get(ResourceType.BASIC_FOOD_BASKET));
        assertEquals(5, resourceExchange.getResourcesRequested().get(ResourceType.TRANSPORT_VEHICLE));
    }

    @Test
    void testEnumValues() {
        assertEquals(4, ResourceType.MEDICAL_DOCTOR.getPoints());
        assertEquals(7, ResourceType.MEDICAL_SUPPLIES.getPoints());
    }

    @Test
    void testNullResources() {
        ResourceExchange emptyExchange = new ResourceExchange("center1", "center2", null, null);
        assertNull(emptyExchange.getResourcesOffered());
        assertNull(emptyExchange.getResourcesRequested());
    }
}
