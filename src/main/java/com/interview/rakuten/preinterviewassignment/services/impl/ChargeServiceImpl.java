package com.interview.rakuten.preinterviewassignment.services.impl;

import com.interview.rakuten.preinterviewassignment.converter.ChargesConverter;
import com.interview.rakuten.preinterviewassignment.dto.ChargesDto;
import com.interview.rakuten.preinterviewassignment.entity.ChargesEntity;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.repository.ChargesRepository;
import com.interview.rakuten.preinterviewassignment.services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private ChargesRepository chargesRepository;

    @Autowired
    private ChargesConverter chargesConverter;

    @Override
    public String fetchChargePerUnitByServiceTypeCallCategoryAndSubscriberType(String serviceType, String callCategory, String subscriberType) throws ResourceNotFoundException {

        ChargesDto chargesDto = null;

        Optional<ChargesEntity> chargesEntity = chargesRepository.findByServiceTypeAndCallCategoryAndSubscriberType(serviceType,callCategory,subscriberType);

        if(chargesEntity.isPresent()) {
            chargesDto = chargesConverter.convertEntityToDto(chargesEntity.get());
        }

        return chargesDto.getChargePerUnit();
    }

    @Override
    public String calculateCharges(String roundedUsedAmount, String chargePerUnit) {
        int usedAmount = roundedUsedAmount.isEmpty()? 0 : Integer.parseInt(roundedUsedAmount);
        double charges = Integer.parseInt(chargePerUnit);

        return String.valueOf(usedAmount==0 ? charges : usedAmount * charges);
    }


}
