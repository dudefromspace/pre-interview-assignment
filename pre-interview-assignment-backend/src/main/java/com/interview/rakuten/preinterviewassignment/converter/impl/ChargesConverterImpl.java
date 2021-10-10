package com.interview.rakuten.preinterviewassignment.converter.impl;

import com.interview.rakuten.preinterviewassignment.converter.ChargesConverter;
import com.interview.rakuten.preinterviewassignment.dto.ChargesDto;
import com.interview.rakuten.preinterviewassignment.entity.ChargesEntity;
import org.springframework.stereotype.Component;

@Component
public class ChargesConverterImpl implements ChargesConverter {

    @Override
    public ChargesDto convertEntityToDto(ChargesEntity chargesEntity) {
        ChargesDto chargesDto = new ChargesDto();
        chargesDto.setId(chargesEntity.getId());
        chargesDto.setServiceType(chargesEntity.getServiceType());
        chargesDto.setCallCategory(chargesEntity.getCallCategory());
        chargesDto.setSubscriberType(chargesEntity.getSubscriberType());
        chargesDto.setChargePerUnit(chargesEntity.getChargePerUnit());
        return chargesDto;
    }

    @Override
    public ChargesEntity convertDtoToEntity(ChargesDto chargesDto) {
        ChargesEntity chargesEntity = new ChargesEntity();
        chargesEntity.setServiceType(chargesDto.getServiceType());
        chargesEntity.setCallCategory(chargesDto.getCallCategory());
        chargesEntity.setSubscriberType(chargesDto.getSubscriberType());
        chargesEntity.setChargePerUnit(chargesDto.getChargePerUnit());
        chargesEntity.setId(chargesDto.getId());
        return chargesEntity;
    }
}
