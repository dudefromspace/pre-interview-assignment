package com.interview.rakuten.preinterviewassignment.repository;

import com.interview.rakuten.preinterviewassignment.entity.ChargesEntity;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChargesRepository extends CrudRepository<ChargesEntity,Long> {

    Optional<ChargesEntity> findByServiceTypeAndCallCategoryAndSubscriberType(String serviceType, String callCategory, String subscriberType) throws ResourceNotFoundException;
}
