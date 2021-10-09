package com.interview.rakuten.preinterviewassignment.repository;

import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDRRepository extends CrudRepository<CDREntity, Long> {

    @Query(value = "SELECT * FROM CDR cdr WHERE SUBSTR(START_DATE_TIME,1,8) = :date",nativeQuery = true)
    List<CDREntity> findByDate(@Param("date") String date) throws ResourceNotFoundException;
}
