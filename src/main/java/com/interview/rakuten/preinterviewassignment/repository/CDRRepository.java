package com.interview.rakuten.preinterviewassignment.repository;

import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CDRRepository extends CrudRepository<CDREntity, Long> {

}
