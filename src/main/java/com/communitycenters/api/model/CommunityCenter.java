package com.communitycenters.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Document(collection = "community_centers")
public class CommunityCenter {

    @Id
    private String id;

    private String name;
    private String address;
    private String location;
    private int capacity;
    private int occupancy;
    private List<Resource> resources;

    public CommunityCenter(String name, String address, String location, int capacity, int occupancy, List<Resource> resources) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
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

    public int getOcuppancy() {
        return this.occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityCenter that = (CommunityCenter) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
