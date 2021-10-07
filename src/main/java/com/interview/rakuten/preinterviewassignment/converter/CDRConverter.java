package com.interview.rakuten.preinterviewassignment.converter;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import org.springframework.stereotype.Component;

@Component
public class CDRConverter {

    public CDREntity convertDtoToEntity(CDRDto cdrDto) {
        CDREntity cdrEntity = new CDREntity();
        cdrEntity.setAnum(cdrDto.getAnum());
        cdrEntity.setBnum(cdrDto.getBnum());
        cdrEntity.setServiceType(cdrDto.getServiceType());
        cdrEntity.setCallCategory(cdrDto.getCallCategory());
        cdrEntity.setSubscriberType(cdrDto.getSubscriberType());
        cdrEntity.setStartDateTime(cdrDto.getStartDateTime());
        cdrEntity.setUsedAmount(cdrDto.getUsedAmount());
        cdrEntity.setId(cdrDto.getId());
        return cdrEntity;
    }

    public CDRDto convertEntityToDto(CDREntity cdrEntity) {
        CDRDto cdrDto = new CDRDto();
        cdrDto.setAnum(cdrEntity.getAnum());
        cdrDto.setBnum(cdrEntity.getBnum());
        cdrDto.setServiceType(cdrEntity.getServiceType());
        cdrDto.setCallCategory(cdrEntity.getCallCategory());
        cdrDto.setSubscriberType(cdrEntity.getSubscriberType());
        cdrDto.setStartDateTime(cdrEntity.getStartDateTime());
        cdrDto.setUsedAmount(cdrEntity.getUsedAmount());
        cdrDto.setId(cdrEntity.getId());
        return cdrDto;
    }
}
