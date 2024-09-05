package com.communitycenters.api.repository;

import com.communitycenters.api.model.ResourceExchange;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceExchangeRepository extends MongoRepository<ResourceExchange, String> {
}

