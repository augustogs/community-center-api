package com.communitycenters.api.service;

import com.communitycenters.api.dto.CommunityCenterDTO;
import com.communitycenters.api.model.CommunityCenter;
import com.communitycenters.api.repository.CommunityCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityCenterService {

    @Autowired
    private CommunityCenterRepository communityCenterRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public CommunityCenter addCommunityCenter(CommunityCenterDTO center) {
        if (center.getOccupancy() > center.getCapacity()) {
            throw new IllegalArgumentException("Occupancy cannot exceed capacity");
        }

        CommunityCenter cc = new CommunityCenter(center.getName(), center.getAddress(), center.getLocation(),
                center.getCapacity(), center.getOccupancy(), center.getResources());
        return communityCenterRepository.save(cc);
    }

    @Transactional
    public CommunityCenter updateOccupancy(String centerId, int occupancy) {
        CommunityCenter communityCenter = communityCenterRepository.findById(centerId)
                .orElseThrow(() -> new IllegalArgumentException("Community Center not found"));

        if (occupancy > communityCenter.getCapacity()) {
            throw new IllegalArgumentException("Occupancy cannot exceed capacity");
        }

        communityCenter.setOccupancy(occupancy);

        if (occupancy == communityCenter.getCapacity()) {
            notificationService.sendCapacityReachedNotification(communityCenter);
        }
        return communityCenterRepository.save(communityCenter);
    }

    public List<CommunityCenter> findCentersWithHighOccupancy() {
        double occupancyThresholdPercentage = 0.9;

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("capacity").gt(0)),
                Aggregation.project("name", "address", "location", "capacity", "occupancy", "resources")
                        .andExpression("occupancy / capacity").as("occupancyPercentage"),
                Aggregation.match(Criteria.where("occupancyPercentage").gt(occupancyThresholdPercentage))
        );

        AggregationResults<CommunityCenter> results = mongoTemplate.aggregate(aggregation, "community_centers", CommunityCenter.class);
        return results.getMappedResults();
    }

    public List<Map> getAverageResourceQuantity() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("{$objectToArray: '$resources'}").as("resourcesArray"),
                Aggregation.unwind("resourcesArray"),
                Aggregation.group("resourcesArray.k")
                        .sum("resourcesArray.v").as("totalQuantity")
                        .count().as("totalCenters"),
                Aggregation.project()
                        .and("_id").as("resourceType")
                        .and("totalQuantity").divide("totalCenters").as("avegereQuantity")
                        .andExclude("_id")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "community_centers", Map.class);
        return results.getMappedResults();
    }

}

