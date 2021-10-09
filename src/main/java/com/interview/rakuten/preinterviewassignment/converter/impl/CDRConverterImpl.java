package com.interview.rakuten.preinterviewassignment.converter.impl;

import com.interview.rakuten.preinterviewassignment.converter.CDRConverter;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.utils.RoundingUtil;
import org.springframework.stereotype.Component;

@Component
public class CDRConverterImpl implements CDRConverter {

    @Override
    public CDREntity convertDtoToEntity(CDRDto cdrDto) {
        CDREntity cdrEntity = new CDREntity();
        cdrEntity.setANUM(cdrDto.getANUM());
        cdrEntity.setBNUM(setNormalisedBNUM(cdrDto.getBNUM()));
        cdrEntity.setServiceType(setNormalisedServiceType(cdrDto.getServiceType()));
        cdrEntity.setCallCategory(setNormalisedCallCategory(cdrDto.getCallCategory()));
        cdrEntity.setSubscriberType(setNormalisedSubscriberType(cdrDto.getSubscriberType()));
        cdrEntity.setStartDateTime(cdrDto.getStartDateTime());
        cdrEntity.setUsedAmount(setNormalisedUsedAmount(cdrDto.getUsedAmount(),cdrDto.getServiceType()));
        cdrEntity.setCharge(cdrDto.getCharge());
        return cdrEntity;
    }

    @Override
    public CDRDto convertEntityToDto(CDREntity cdrEntity) throws CDRException {
        if(cdrEntity == null) {
            throw new CDRException("CDR entity is null");
        }
        CDRDto cdrDto = new CDRDto();
        cdrDto.setANUM(cdrEntity.getANUM());
        cdrDto.setBNUM(cdrEntity.getBNUM());
        cdrDto.setServiceType(cdrEntity.getServiceType());
        cdrDto.setCallCategory(cdrEntity.getCallCategory());
        cdrDto.setSubscriberType(cdrEntity.getSubscriberType());
        cdrDto.setStartDateTime(cdrEntity.getStartDateTime());
        cdrDto.setUsedAmount(getNormalisedUsedAmount(cdrEntity.getUsedAmount(),cdrEntity.getServiceType()));
        cdrDto.setId(cdrEntity.getId());
        cdrDto.setCharge(cdrEntity.getCharge());
        return cdrDto;
    }

    private String setNormalisedBNUM(String bnum) {
        if(bnum.isEmpty())
            return "";
        int bnumLength = bnum.length();
        if(bnumLength == 10){
            return bnum;
        }else {
            if(bnum.charAt(0) == '+'){
                return bnum.substring(1,10);
            }else {
                return bnum.substring(2,12);
            }
        }
    }

    private String setNormalisedServiceType(String serviceType) {
        switch (serviceType) {
            case "1": serviceType = ServiceType.VOICE.toString();
                        break;
            case "2" : serviceType = ServiceType.SMS.toString();
                        break;
            case "3" : serviceType = ServiceType.GPRS.toString();
                        break;
            default: break;
        }
        return serviceType;
    }

    private String setNormalisedCallCategory(String callCategory) {
        if(callCategory.equals("1"))
            return CallCategory.LOCAL.toString();
        else
            return CallCategory.ROAMING.toString();
    }

    private String setNormalisedSubscriberType(String subscriberType) {
        if(subscriberType.equals("1"))
            return SubscriberType.POSTPAID.toString();
        else
            return SubscriberType.PREPAID.toString();
    }

    private String setNormalisedUsedAmount(String usedAmount, String serviceType) {
        if(serviceType.equals("1"))
            return usedAmount + "s";
        else if(serviceType.equals("3"))
            return usedAmount + "KB";
        else
            return "";
    }

    private String getNormalisedUsedAmount(String usedAmount, String serviceType) {
        if(serviceType.equals(ServiceType.VOICE.toString()))
            return RoundingUtil.roundDuration(usedAmount);
        else if(serviceType.equals(ServiceType.GPRS.toString()))
            return RoundingUtil.roundDuration(usedAmount);
        else
            return "";
    }
}
