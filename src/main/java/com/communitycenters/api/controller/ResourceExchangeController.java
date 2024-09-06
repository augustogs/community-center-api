package com.communitycenters.api.controller;

import com.communitycenters.api.dto.ResourceExchangeDTO;
import com.communitycenters.api.model.ResourceExchange;
import com.communitycenters.api.service.ResourceExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/api/resource-exchanges")
public class ResourceExchangeController {

    @Autowired
    private ResourceExchangeService resourceExchangeService;

    @PostMapping
    public ResponseEntity<?> exchangeResources(@RequestBody ResourceExchangeDTO resourceExchangeDTO) {
        try {
            ResourceExchange exchange = resourceExchangeService.exchangeResources(resourceExchangeDTO);
            return new ResponseEntity<>(exchange, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-center")
    public List<ResourceExchange> getExchangesByCenter(@RequestParam String centerId) {
        return resourceExchangeService.getExchangesByCenterIdAndDateRange(centerId);
    }

    @GetMapping("/by-date-range")
    public List<ResourceExchange> getExchangesByDateRange(@RequestParam Instant startTime, @RequestParam Instant endTime) {
        return resourceExchangeService.getExchangesByDateRange(startTime, endTime);
    }
}
