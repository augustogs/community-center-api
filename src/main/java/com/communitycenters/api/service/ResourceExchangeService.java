package com.communitycenters.api.service;

import com.communitycenters.api.dto.ResourceExchangeDTO;
import com.communitycenters.api.model.CommunityCenter;
import com.communitycenters.api.model.ResourceExchange;
import com.communitycenters.api.model.ResourceType;
import com.communitycenters.api.repository.CommunityCenterRepository;
import com.communitycenters.api.repository.ResourceExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class ResourceExchangeService {

    @Autowired
    private CommunityCenterRepository communityCenterRepository;

    @Autowired
    private ResourceExchangeRepository resourceExchangeRepository;

    public ResourceExchange exchangeResources(ResourceExchangeDTO resourceExchangeDTO) {

        CommunityCenter centerFrom = communityCenterRepository.findById(resourceExchangeDTO.getCenterFromId())
                .orElseThrow(() -> new IllegalArgumentException("Center From not found"));

        CommunityCenter centerTo = communityCenterRepository.findById(resourceExchangeDTO.getCenterToId())
                .orElseThrow(() -> new IllegalArgumentException("Center To not found"));

        Map<ResourceType, Integer> resourcesOffered = resourceExchangeDTO.getResourcesOffered();
        Map<ResourceType, Integer> resourcesRequested = resourceExchangeDTO.getResourcesRequested();

        if (calcOccupancyPercent(centerFrom) < 90.0 && calcOccupancyPercent(centerTo) < 90.0) {
            int pointsResourcesOffered = calcPointsExchange(resourcesOffered.keySet());
            int pointsResourcesRequested = calcPointsExchange(resourcesRequested.keySet());

            if (pointsResourcesOffered != pointsResourcesRequested) {
                throw new IllegalArgumentException("Exchange not allowed, points must be equal");
            }
        }

        //implement check values is not < 0
        for (ResourceType resourceType : resourcesOffered.keySet()) {
            if (centerTo.getResources().containsKey(resourceType)) {
                centerTo.getResources().put(resourceType, centerTo.getResources().get(resourceType) + resourcesOffered.get(resourceType));
                centerFrom.getResources().put(resourceType, centerFrom.getResources().get(resourceType) - resourcesOffered.get(resourceType));
            } else {
                centerTo.getResources().put(resourceType, resourcesOffered.get(resourceType));
                centerFrom.getResources().put(resourceType, centerFrom.getResources().get(resourceType) - resourcesOffered.get(resourceType));
            }
        }

        for (ResourceType resourceType : resourcesRequested.keySet()) {
            if (centerFrom.getResources().containsKey(resourceType)) {
                centerFrom.getResources().put(resourceType, centerFrom.getResources().get(resourceType) + resourcesRequested.get(resourceType));
                centerTo.getResources().put(resourceType, centerTo.getResources().get(resourceType) - resourcesRequested.get(resourceType));
            } else {
                centerFrom.getResources().put(resourceType, resourcesRequested.get(resourceType));
                centerTo.getResources().put(resourceType, centerTo.getResources().get(resourceType) - resourcesRequested.get(resourceType));
            }
        }

        communityCenterRepository.save(centerFrom);
        communityCenterRepository.save((centerTo));

        ResourceExchange resourceExchange = new ResourceExchange(
                resourceExchangeDTO.getCenterFromId(),
                resourceExchangeDTO.getCenterToId(),
                resourceExchangeDTO.getResourcesOffered(),
                resourceExchangeDTO.getResourcesRequested()
        );

        resourceExchangeRepository.save(resourceExchange);
        return resourceExchange;
    }

    private double calcOccupancyPercent(CommunityCenter communityCenter) {
        return ((double) communityCenter.getOccupancy() / communityCenter.getCapacity() * 100);
    }

    private int calcPointsExchange(Set<ResourceType> resources) {
        int points = 0;
        for (ResourceType resourceType: resources) {
            points += resourceType.getPoints();
        }
        return points;
    }

}
