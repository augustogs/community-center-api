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

    @Transactional
    public CommunityCenter addCommunityCenter(CommunityCenterDTO center) {
        if (center.getOccupancy() > center.getCapacity()) {
            throw new IllegalArgumentException("Occupancy cannot exceed capacity");
        }

        CommunityCenter cc = new CommunityCenter(center.getName(), center.getAddress(), center.getLocation(),
                                                 center.getCapacity(), center.getOccupancy(), center.getResources());
        return communityCenterRepository.save(cc);
    }
}
