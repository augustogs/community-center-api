package com.communitycenters.api.dto;

import com.communitycenters.api.model.ResourceType;

import java.util.Map;
import java.util.Set;

public class ResourceExchangeDTO {

    private String centerFromId;
    private String centerToId;
    private Map<ResourceType, Integer> resourcesOffered;
    private Map<ResourceType, Integer> resourcesRequested;

    public ResourceExchangeDTO(String centerFromId, String centerToId, Map<ResourceType, Integer> resourcesOffered, Map<ResourceType, Integer> resourcesRequested) {
        this.centerFromId = centerFromId;
        this.centerToId = centerToId;
        this.resourcesOffered = resourcesOffered;
        this.resourcesRequested = resourcesRequested;
    }

    public String getCenterFromId() {
        return centerFromId;
    }

    public void setCenterFromId(String centerFromId) {
        this.centerFromId = centerFromId;
    }

    public String getCenterToId() {
        return centerToId;
    }

    public void setCenterToId(String centerToId) {
        this.centerToId = centerToId;
    }

    public Map<ResourceType, Integer> getResourcesOffered() {
        return resourcesOffered;
    }

    public Map<ResourceType, Integer> getResourcesRequested() {
        return resourcesRequested;
    }

    public void setResourcesOffered(Map<ResourceType, Integer> resourcesOffered) {
        this.resourcesOffered = resourcesOffered;
    }

    public void setResourcesRequested(Map<ResourceType, Integer> resourcesRequested) {
        this.resourcesRequested = resourcesRequested;
    }
}

