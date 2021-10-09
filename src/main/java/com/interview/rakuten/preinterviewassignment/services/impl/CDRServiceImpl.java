package com.interview.rakuten.preinterviewassignment.services.impl;

import com.interview.rakuten.preinterviewassignment.converter.CDRConverter;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.repository.CDRRepository;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.services.ChargeService;
import com.interview.rakuten.preinterviewassignment.utils.RoundingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CDRServiceImpl implements CDRService {

    @Autowired
    private CDRRepository cdrRepository;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private CDRConverter cdrConverter;

    @Override
    public CDRDto addCDR(CDRDto cdrDto) throws CDRException {

        if(cdrDto == null) {
            throw new CDRException("CDR information is incomplete");
        }
        CDRDto cdrDtoAdded = null;
        CDREntity cdrEntity = null;
        CDREntity cdrEntityAdded = null;
        String roundedUsedAmount = "";
        String chargePerUnit = "";
        try {
            chargePerUnit = chargeService.fetchChargePerUnitByServiceTypeCallCategoryAndSubscriberType(cdrDto.getServiceType(),cdrDto.getCallCategory(),cdrDto.getSubscriberType());
        } catch (ResourceNotFoundException e) {
            throw new CDRException(e.getErrorCode());
        }
        if(cdrDto.getServiceType().equals("1"))
            roundedUsedAmount = RoundingUtil.roundDuration(cdrDto.getUsedAmount());
        else if (cdrDto.getServiceType().equals("3"))
            roundedUsedAmount = RoundingUtil.roundVolume(cdrDto.getUsedAmount());

        String charge = chargeService.calculateCharges(roundedUsedAmount,chargePerUnit);
        cdrDto.setCharge(charge);
        cdrEntity = cdrConverter.convertDtoToEntity(cdrDto);
        cdrEntityAdded = cdrRepository.save(cdrEntity);
        cdrDtoAdded = cdrConverter.convertEntityToDto(cdrEntityAdded);
        return cdrDtoAdded;
    }
}
