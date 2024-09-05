package com.communitycenters.api.dto;

import com.communitycenters.api.model.ResourceType;

import java.util.Map;
import java.util.Set;

public class ResourceExchangeDTO {

    private String centerFromId;
    private String centerToId;
    private Map<ResourceType, Integer> resourcesOffered;
    private Map<ResourceType, Integer> resourcesRequested;

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

}

