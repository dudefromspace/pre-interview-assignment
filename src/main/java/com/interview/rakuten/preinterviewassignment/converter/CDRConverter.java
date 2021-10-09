package com.interview.rakuten.preinterviewassignment.converter;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;

public interface CDRConverter {

    public CDREntity convertDtoToEntity(CDRDto cdrDto) throws CDRException;

    public CDRDto convertEntityToDto(CDREntity cdrEntity) throws CDRException;

    public enum ServiceType {
        VOICE,
        SMS,
        GPRS
    }
    public enum CallCategory {
        LOCAL,
        ROAMING
    }
    public enum SubscriberType {
        POSTPAID,
        PREPAID
    }
}
