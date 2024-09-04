package com.communitycenters.api.service;

import com.communitycenters.api.dto.CommunityCenterDTO;
import com.communitycenters.api.model.CommunityCenter;
import com.communitycenters.api.repository.CommunityCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommunityCenterService {

    @Autowired
    private CommunityCenterRepository communityCenterRepository;

    @Autowired
    private NotificationService notificationService;

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

}
