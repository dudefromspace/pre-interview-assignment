package com.interview.rakuten.preinterviewassignment.converter.impl;

import com.interview.rakuten.preinterviewassignment.converter.CDRConverter;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import org.springframework.stereotype.Component;

@Component
public class CDRConverterImpl implements CDRConverter {

    @Override
    public CDREntity convertDtoToEntity(CDRDto cdrDto) {
        CDREntity cdrEntity = new CDREntity();
        cdrEntity.setANUM(cdrDto.getANUM());
        cdrEntity.setBNUM(cdrDto.getBNUM());
        cdrEntity.setServiceType(cdrDto.getServiceType());
        cdrEntity.setCallCategory(cdrDto.getCallCategory());
        cdrEntity.setSubscriberType(cdrDto.getSubscriberType());
        cdrEntity.setStartDateTime(cdrDto.getStartDateTime());
        cdrEntity.setUsedAmount(cdrDto.getUsedAmount());
        cdrEntity.setId(cdrDto.getId());
        return cdrEntity;
    }

    @Override
    public CDRDto convertEntityToDto(CDREntity cdrEntity) {
        CDRDto cdrDto = new CDRDto();
        cdrDto.setANUM(cdrEntity.getANUM());
        cdrDto.setBNUM(cdrEntity.getBNUM());
        cdrDto.setServiceType(cdrEntity.getServiceType());
        cdrDto.setCallCategory(cdrEntity.getCallCategory());
        cdrDto.setSubscriberType(cdrEntity.getSubscriberType());
        cdrDto.setStartDateTime(cdrEntity.getStartDateTime());
        cdrDto.setUsedAmount(cdrEntity.getUsedAmount());
        cdrDto.setId(cdrEntity.getId());
        return cdrDto;
    }
}
