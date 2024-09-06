package com.communitycenters.api.controller;

import com.communitycenters.api.dto.CommunityCenterDTO;
import com.communitycenters.api.model.CommunityCenter;
import com.communitycenters.api.model.ResourceType;
import com.communitycenters.api.service.CommunityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community-centers")
public class CommunityCenterController {

    @Autowired
    private CommunityCenterService communityCenterService;

    @PostMapping
    public ResponseEntity<?> addCommunityCenter(@RequestBody CommunityCenterDTO center) {
        try {
            CommunityCenter createdCenter = communityCenterService.addCommunityCenter(center);
            return new ResponseEntity<>(createdCenter, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{centerId}/occupancy")
    public ResponseEntity<?> updateOccupancy(@PathVariable String centerId, @RequestParam int occupancy) {
        try {
            CommunityCenter updatedCenter = communityCenterService.updateOccupancy(centerId, occupancy);
            return new ResponseEntity<>(updatedCenter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/high-occupancy")
    public List<CommunityCenter> getCommunityCentersWithHighOccupancy() {
        return communityCenterService.findCentersWithHighOccupancy();
    }

    @GetMapping("/average-resources")
    public List<Map> getAverageResourceQuantity() {
        return communityCenterService.getAverageResourceQuantity();
    }

}

