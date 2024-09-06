package com.communitycenters.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "exchanges")
public class ResourceExchange {
    @Id
    private String id;

    private String centerFromId;
    private String centerToId;
    private Map<ResourceType, Integer> resourcesOffered;
    private Map<ResourceType, Integer> resourcesRequested;
    private Instant timestamp;

    public ResourceExchange(String centerFromId, String centerToId, Map<ResourceType, Integer> resourcesOffered, Map<ResourceType, Integer> resourcesRequested) {
        this.centerFromId = centerFromId;
        this.centerToId = centerToId;
        this.resourcesOffered = resourcesOffered;
        this.resourcesRequested = resourcesRequested;
        this.timestamp = Instant.now();
    }

    public String getCenterFromId() {
        return this.centerFromId;
    }

    public void setCenterFrom(String centerFrom) {
        this.centerFromId = this.centerFromId;
    }

    public String getCenterToId() {
        return this.centerToId;
    }

    public void setCenterTo(String centerTo) {
        this.centerToId = centerToId;
    }

    public Map<ResourceType, Integer> getResourcesOffered() {
        return this.resourcesOffered;
    }

    public Map<ResourceType, Integer> getResourcesRequested() {
        return this.resourcesRequested;
    }
    
}
