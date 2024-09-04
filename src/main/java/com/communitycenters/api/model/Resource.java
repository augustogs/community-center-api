package com.communitycenters.api.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resources")
public class Resource {

    private ResourceType resourceType;
    private int quantity;

    public Resource(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public int getQuantity() {
        return this.quantity;
    }

}
