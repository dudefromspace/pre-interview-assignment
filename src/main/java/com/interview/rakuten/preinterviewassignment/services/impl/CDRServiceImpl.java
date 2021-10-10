package com.interview.rakuten.preinterviewassignment.services.impl;

import com.interview.rakuten.preinterviewassignment.converter.CDRConverter;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.dto.GPRSInfoDto;
import com.interview.rakuten.preinterviewassignment.dto.InfoDto;
import com.interview.rakuten.preinterviewassignment.dto.VoiceCallInfoDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.repository.CDRRepository;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.services.ChargeService;
import com.interview.rakuten.preinterviewassignment.utils.RoundingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CDRServiceImpl implements CDRService {

    @Autowired
    private CDRRepository cdrRepository;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private CDRConverter cdrConverter;

    @Override
    public CDRDto addCDR(CDRDto cdrDto, String fileName) throws CDRException {

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
        cdrDto.setFileName(fileName);
        cdrEntity = cdrConverter.convertDtoToEntity(cdrDto);
        cdrEntityAdded = cdrRepository.save(cdrEntity);
        cdrDtoAdded = cdrConverter.convertEntityToDto(cdrEntityAdded,true);
        return cdrDtoAdded;
    }

    @Override
    public List<CDRDto> addCDR(List<CDRDto> cdrDtoList,String fileName) throws CDRException {
        List<CDRDto> cdrDtoListAdded = new ArrayList<>();
        if(cdrDtoList.isEmpty())
            throw new CDRException("CDR List is empty");
        cdrDtoList.forEach(cdrDto -> {
            try {
                cdrDtoListAdded.add(addCDR(cdrDto,fileName));
            } catch (CDRException e) {
                e.printStackTrace();
            }
        });
        return cdrDtoListAdded;
    }

    @Override
    public List<CDRDto> fetchAll() throws CDRException {
        Iterable<CDREntity> cdrEntities = cdrRepository.findAll();
        Iterator<CDREntity> iterator = cdrEntities.iterator();
        CDREntity cdrEntity = null;
        List<CDRDto> cdrDtoList = new ArrayList<>();
        while (iterator.hasNext()){
            cdrEntity = iterator.next();
            cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity,true));
        }
        return cdrDtoList;
    }

    @Override
    public List<CDRDto> fetchByDate(String date) throws ResourceNotFoundException {
        List<CDRDto> cdrDtoList = new ArrayList<>();
        List<CDREntity> cdrEntities = cdrRepository.findByDate(date);
        if(!cdrEntities.isEmpty()){
            cdrEntities.forEach(cdrEntity -> {
                try {
                    cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity,true));
                } catch (CDRException e) {
                    e.printStackTrace();
                }
            });
        }
        return cdrDtoList;
    }

    @Override
    public List<CDRDto> fetchByMaxCharge() throws ResourceNotFoundException {
        List<CDRDto> cdrDtoList = new ArrayList<>();
        List<CDREntity> cdrEntityList = cdrRepository.findByMaxCharge();
        if(!cdrEntityList.isEmpty()){
            cdrEntityList.forEach(cdrEntity -> {
                try {
                    cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity,true));
                } catch (CDRException e) {
                    e.printStackTrace();
                }
            });
        }
        return cdrDtoList;
    }

    @Override
    public List<CDRDto> fetchByMaxDuration() throws ResourceNotFoundException {
        List<CDRDto> cdrDtoList = new ArrayList<>();
        List<CDREntity> cdrEntityList = cdrRepository.findByMaxDuration();
        if(!cdrEntityList.isEmpty()){
            cdrEntityList.forEach(cdrEntity -> {
                try {
                    cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity,true));
                } catch (CDRException e) {
                    e.printStackTrace();
                }
            });
        }
        return cdrDtoList;
    }

    @Override
    public List<VoiceCallInfoDto> fetchTotalVoiceCallDurationByCategoryAndDate(String date) throws ResourceNotFoundException {
        List<VoiceCallInfoDto> voiceCallInfoDtoList = new ArrayList<>();
        VoiceCallInfoDto localVoiceCallInfoDto = this.getVoiceCallInfo(CDRConverter.CallCategory.LOCAL.toString(),date);
        VoiceCallInfoDto roamingVoiceCallInfoDto = this.getVoiceCallInfo(CDRConverter.CallCategory.ROAMING.toString(),date);
        voiceCallInfoDtoList.add(localVoiceCallInfoDto);
        voiceCallInfoDtoList.add(roamingVoiceCallInfoDto);
        return voiceCallInfoDtoList;
    }

    @Override
    public List<GPRSInfoDto> fetchTotalGPRSVolumeBySubscriberTypeAndDate(String date) throws ResourceNotFoundException {

        List<GPRSInfoDto> gprsInfoDtoList = new ArrayList<>();
        GPRSInfoDto prepaidGPRSInfoDto = this.getGPRSInfo(CDRConverter.SubscriberType.PREPAID.toString(),date);
        GPRSInfoDto postpaidGPRSInfoDto = this.getGPRSInfo(CDRConverter.SubscriberType.POSTPAID.toString(),date);
        gprsInfoDtoList.add(prepaidGPRSInfoDto);
        gprsInfoDtoList.add(postpaidGPRSInfoDto);
        return gprsInfoDtoList;
    }

    private VoiceCallInfoDto getVoiceCallInfo(String callCategory,String date) throws ResourceNotFoundException {
        List<CDREntity> cdrEntityList = cdrRepository.findByServiceTypeAndCallCategoryAndDate(CDRConverter.ServiceType.VOICE.toString(), callCategory ,date);
        VoiceCallInfoDto voiceCallInfoDto = new VoiceCallInfoDto();
        voiceCallInfoDto.setCallCategory(callCategory);
        List<InfoDto> infoDtoList = new ArrayList<>();
        if(!cdrEntityList.isEmpty()){
            Set<String> fileNameSet = new HashSet<>();
            cdrEntityList.forEach(cdrEntity -> {
                fileNameSet.add(cdrEntity.getFileName().trim());
            });
            fileNameSet.forEach(fileName->{
                InfoDto infoDto = new InfoDto();
                Optional<Integer> totalUsedAmount = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Integer.parseInt(cdrEntity.getUsedAmount().replace("s","")))
                        .reduce((a,b)->(a+b));
                infoDto.setAbsoluteTotalUsedAmount(String.valueOf(totalUsedAmount.get()));
                infoDto.setRoundedTotalUsedAmount(RoundingUtil.roundDuration(String.valueOf(totalUsedAmount.get())));
                infoDto.setFileName(fileName);
                Optional<Double> totalCharge = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Double.parseDouble(cdrEntity.getCharge()))
                        .reduce((a,b)->(a+b));
                infoDto.setTotalChargePerFileAndDay(String.valueOf(totalCharge.get()));
                infoDtoList.add(infoDto);
            });
            voiceCallInfoDto.setInfoDtoList(infoDtoList);
        }
        return voiceCallInfoDto;
    }

    private GPRSInfoDto getGPRSInfo(String subscriberType,String date) throws ResourceNotFoundException {
        List<CDREntity> cdrEntityList = cdrRepository.findByServiceTypeAndSubscriberTypeAndDate(CDRConverter.ServiceType.GPRS.toString(), subscriberType ,date);
        GPRSInfoDto gprsInfoDto = new GPRSInfoDto();
        gprsInfoDto.setSubscriberType(subscriberType);
        List<InfoDto> infoDtoList = new ArrayList<>();
        if(!cdrEntityList.isEmpty()){
            Set<String> fileNameSet = new HashSet<>();
            cdrEntityList.forEach(cdrEntity -> {
                fileNameSet.add(cdrEntity.getFileName().trim());
            });
            fileNameSet.forEach(fileName->{
                InfoDto infoDto = new InfoDto();
                Optional<Integer> totalUsedAmount = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Integer.parseInt(cdrEntity.getUsedAmount().replace("KB","")))
                        .reduce((a,b)->(a+b));
                infoDto.setAbsoluteTotalUsedAmount(String.valueOf(totalUsedAmount.get()));
                infoDto.setRoundedTotalUsedAmount(RoundingUtil.roundVolume(String.valueOf(totalUsedAmount.get())));
                infoDto.setFileName(fileName);
                Optional<Double> totalCharge = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Double.parseDouble(cdrEntity.getCharge()))
                        .reduce((a,b)->(a+b));
                infoDto.setTotalChargePerFileAndDay(String.valueOf(totalCharge.get()));
                infoDtoList.add(infoDto);
            });
            gprsInfoDto.setInfoDtoList(infoDtoList);
        }
        return gprsInfoDto;
    }
}
