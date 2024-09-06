package com.communitycenters.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunityCenterTest {

    private CommunityCenter communityCenter;
    private Map<ResourceType, Integer> resources;

    @BeforeEach
    public void setUp() {
        resources = new HashMap<>();
        resources.put(ResourceType.MEDICAL_DOCTOR, 5);
        resources.put(ResourceType.TRANSPORT_VEHICLE, 3);

        communityCenter = new CommunityCenter("Test Center", "123 Street", "Test Location", 100, 50, resources);
    }

    @Test
    public void testGetName() {
        assertThat(communityCenter.getName()).isEqualTo("Test Center");
    }

    @Test
    public void testSetName() {
        communityCenter.setName("New Center Name");
        assertThat(communityCenter.getName()).isEqualTo("New Center Name");
    }

    @Test
    public void testGetAddress() {
        assertThat(communityCenter.getAddress()).isEqualTo("123 Street");
    }

    @Test
    public void testSetAddress() {
        communityCenter.setAddress("456 Avenue");
        assertThat(communityCenter.getAddress()).isEqualTo("456 Avenue");
    }

    @Test
    public void testGetLocation() {
        assertThat(communityCenter.getLocation()).isEqualTo("Test Location");
    }

    @Test
    public void testSetLocation() {
        communityCenter.setLocation("New Location");
        assertThat(communityCenter.getLocation()).isEqualTo("New Location");
    }

    @Test
    public void testGetCapacity() {
        assertThat(communityCenter.getCapacity()).isEqualTo(100);
    }

    @Test
    public void testSetCapacity() {
        communityCenter.setCapacity(200);
        assertThat(communityCenter.getCapacity()).isEqualTo(200);
    }

    @Test
    public void testGetOccupancy() {
        assertThat(communityCenter.getOccupancy()).isEqualTo(50);
    }

    @Test
    public void testSetOccupancy() {
        communityCenter.setOccupancy(75);
        assertThat(communityCenter.getOccupancy()).isEqualTo(75);
    }

    @Test
    public void testGetResources() {
        assertThat(communityCenter.getResources()).isEqualTo(resources);
    }

    @Test
    public void testEquality() {
        CommunityCenter anotherCenter = new CommunityCenter("Test Center", "123 Street", "Test Location", 100, 50, resources);
        assertThat(communityCenter).isEqualTo(anotherCenter);

        anotherCenter.setName("Different Name");
        assertThat(communityCenter).isNotEqualTo(anotherCenter);
    }

    @Test
    public void testHashCode() {
        CommunityCenter anotherCenter = new CommunityCenter("Test Center", "123 Street", "Test Location", 100, 50, resources);
        assertThat(communityCenter.hashCode()).isEqualTo(anotherCenter.hashCode());

        anotherCenter.setName("Different Name");
        assertThat(communityCenter.hashCode()).isNotEqualTo(anotherCenter.hashCode());
    }
}

