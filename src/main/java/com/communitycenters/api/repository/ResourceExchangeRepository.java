package com.communitycenters.api.repository;

import com.communitycenters.api.model.ResourceExchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ResourceExchangeRepository extends MongoRepository<ResourceExchange, String> {

    @Query("{ $or: [ { 'centerFromId': :#{#centerId} }, { 'centerToId': :#{#centerId} } ] }")
    List<ResourceExchange> findExchangesByCenterId(
            @Param("centerId") String centerId
    );

    @Query("{ 'timestamp': { $gte: :#{#startTime}, $lte: :#{#endTime} } }")
    List<ResourceExchange> findExchangesByDateRange(
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime
    );
}

