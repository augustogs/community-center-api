package com.communitycenters.api.dto;

import com.communitycenters.api.model.ResourceType;

import java.util.Map;
import java.util.Set;

public class CommunityCenterDTO {

    private String name;
    private String address;
    private String location;
    private int capacity;
    private int occupancy;
    private Map<ResourceType, Integer> resources;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupancy() {
        return this.occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public Map<ResourceType, Integer> getResources() {
        return this.resources;
    }


}
