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

    @Query(value = "SELECT * FROM CDR cdr WHERE SUBSTR(cdr.START_DATE_TIME,1,8) = :date",nativeQuery = true)
    List<CDREntity> findByDate(@Param("date") String date) throws ResourceNotFoundException;

    @Query(value = "SELECT * FROM CDR cdr WHERE cdr.CHARGE = (SELECT MAX(CHARGE) FROM CDR)",nativeQuery = true)
    List<CDREntity> findByMaxCharge() throws ResourceNotFoundException;

    @Query(value = "SELECT * FROM CDR WHERE SUBSTR(USED_AMOUNT,1,LENGTH(USED_AMOUNT)-1) = (SELECT MAX(SUBSTR(USED_AMOUNT,1,LENGTH(USED_AMOUNT)-1)) FROM CDR WHERE SERVICE_TYPE = 'VOICE')",nativeQuery = true)
    List<CDREntity> findByMaxDuration() throws ResourceNotFoundException;

    @Query(value = "SELECT * FROM CDR WHERE SERVICE_TYPE = :serviceType AND CALL_CATEGORY = :callCategory AND SUBSTR(cdr.START_DATE_TIME,1,8) = :date", nativeQuery = true)
    List<CDREntity> findByServiceTypeAndCallCategoryAndDate(String serviceType, String callCategory,String date) throws ResourceNotFoundException;

    @Query(value = "SELECT * FROM CDR WHERE SERVICE_TYPE = :serviceType AND SUBSCRIBER_TYPE = :subscriberType AND SUBSTR(cdr.START_DATE_TIME,1,8) = :date", nativeQuery = true)
    List<CDREntity> findByServiceTypeAndSubscriberTypeAndDate(String serviceType, String subscriberType, String date) throws ResourceNotFoundException;
}
