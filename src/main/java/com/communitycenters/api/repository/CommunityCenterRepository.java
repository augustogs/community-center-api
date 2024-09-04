package com.communitycenters.api.repository;

import com.communitycenters.api.model.CommunityCenter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommunityCenterRepository extends MongoRepository<CommunityCenter, String> {
}
