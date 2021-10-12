package com.interview.rakuten.preinterviewassignment.services.impl;

import com.interview.rakuten.preinterviewassignment.converter.CDRConverter;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.dto.GPRSInfoDto;
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
import java.util.stream.Collectors;

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
        cdrDtoAdded = cdrConverter.convertEntityToDto(cdrEntityAdded);
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
            cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity));
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
                    cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity));
                } catch (CDRException e) {
                    e.printStackTrace();
                }
            });
        }
        return cdrDtoList;
    }

    @Override
    public List<String> fetchAnumByMaxCharge() throws ResourceNotFoundException, CDRException {
        List<CDRDto> cdrDtoList = new ArrayList<>();
        Iterable<CDREntity> cdrEntities = cdrRepository.findAll();
        Iterator<CDREntity> iterator = cdrEntities.iterator();
        CDREntity cdrEntity = null;
        while (iterator.hasNext()){
            cdrEntity = iterator.next();
            cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity));
        }
        Map<String, Double> map = new HashMap<>();
        for(CDRDto cdrDto : cdrDtoList){
            if(map.keySet().contains(cdrDto.getANUM())) {
                double charge = map.get(cdrDto.getANUM());
                charge = charge + Double.valueOf(cdrDto.getCharge());
                map.put(cdrDto.getANUM(),charge);
            }else {
                map.put(cdrDto.getANUM(),Double.valueOf(cdrDto.getCharge()));
            }
        }

        List<String> anumList = new ArrayList<>();
        Map.Entry<String, Double> maxEntry = Collections.max(map.entrySet(), Comparator.comparing(Map.Entry::getValue));
        double maxCharge = maxEntry.getValue();
        for(String anum : map.keySet()){
            if(map.get(anum) == (maxCharge)){
                anumList.add(anum);
            }
        }
        return anumList;
    }

    @Override
    public List<String> fetchAnumByMaxDuration() throws ResourceNotFoundException, CDRException {
        List<CDRDto> cdrDtoList = new ArrayList<>();
        Iterable<CDREntity> cdrEntities = cdrRepository.findAll();
        Iterator<CDREntity> iterator = cdrEntities.iterator();
        CDREntity cdrEntity = null;
        while (iterator.hasNext()){
            cdrEntity = iterator.next();
            cdrDtoList.add(cdrConverter.convertEntityToDto(cdrEntity));
        }
        Map<String, Integer> map = new HashMap<>();
        cdrDtoList = cdrDtoList.stream().filter(cdrDto -> cdrDto.getServiceType().equals(CDRConverter.ServiceType.VOICE.toString())).collect(Collectors.toList());
        for(CDRDto cdrDto : cdrDtoList){
            if(map.keySet().contains(cdrDto.getANUM())) {
                int duration = map.get(cdrDto.getANUM());
                duration = duration + Integer.valueOf(cdrDto.getUsedAmount().replace("s",""));
                map.put(cdrDto.getANUM(),duration);
            }else {
                map.put(cdrDto.getANUM(),Integer.valueOf(cdrDto.getUsedAmount().replace("s","")));
            }
        }

        List<String> anumList = new ArrayList<>();
        Map.Entry<String, Integer> maxEntry = Collections.max(map.entrySet(), Comparator.comparing(Map.Entry::getValue));
        int maxDuration = maxEntry.getValue();
        for(String anum : map.keySet()){
            if(map.get(anum) == (maxDuration)){
                anumList.add(anum);
            }
        }
        return anumList;
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

    @Override
    public Map<String,String> getChargePerHour(String date) throws ResourceNotFoundException {
        List<CDREntity> cdrEntities = cdrRepository.findByDate(date);
        Map<String,String> map = new HashMap<>();
        for(CDREntity cdrEntity : cdrEntities){
            int i = Integer.parseInt(cdrEntity.getStartDateTime().substring(8,10));
            String period = i + "-" + (i+1);
            if(map.keySet().contains(period)){
                double charge = Double.parseDouble(map.get(period));
                charge = charge + Double.parseDouble(cdrEntity.getCharge());
                map.put(period,String.valueOf(charge));
            }else {
                map.put(period,cdrEntity.getCharge());
            }
        }
        return map;
    }

    private VoiceCallInfoDto getVoiceCallInfo(String callCategory,String date) throws ResourceNotFoundException {
        List<CDREntity> cdrEntityList = cdrRepository.findByServiceTypeAndCallCategoryAndDate(CDRConverter.ServiceType.VOICE.toString(), callCategory ,date);
        VoiceCallInfoDto voiceCallInfoDto = new VoiceCallInfoDto();
        voiceCallInfoDto.setCallCategory(callCategory);
        if(!cdrEntityList.isEmpty()){
            Set<String> fileNameSet = new HashSet<>();
            cdrEntityList.forEach(cdrEntity -> {
                fileNameSet.add(cdrEntity.getFileName().trim());
            });
            fileNameSet.forEach(fileName->{
                Optional<Integer> totalUsedAmount = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Integer.parseInt(cdrEntity.getUsedAmount().replace("s","")))
                        .reduce((a,b)->(a+b));
                voiceCallInfoDto.setAbsoluteTotalUsedAmount(String.valueOf(totalUsedAmount.get()));
                voiceCallInfoDto.setRoundedTotalUsedAmount(RoundingUtil.roundDuration(String.valueOf(totalUsedAmount.get())));
                voiceCallInfoDto.setFileName(fileName);
                Optional<Double> totalCharge = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Double.parseDouble(cdrEntity.getCharge()))
                        .reduce((a,b)->(a+b));
                voiceCallInfoDto.setTotalChargePerFileAndDay(String.valueOf(totalCharge.get()));
            });
        }
        return voiceCallInfoDto;
    }

    private GPRSInfoDto getGPRSInfo(String subscriberType,String date) throws ResourceNotFoundException {
        List<CDREntity> cdrEntityList = cdrRepository.findByServiceTypeAndSubscriberTypeAndDate(CDRConverter.ServiceType.GPRS.toString(), subscriberType ,date);
        GPRSInfoDto gprsInfoDto = new GPRSInfoDto();
        gprsInfoDto.setSubscriberType(subscriberType);
        if(!cdrEntityList.isEmpty()){
            Set<String> fileNameSet = new HashSet<>();
            cdrEntityList.forEach(cdrEntity -> {
                fileNameSet.add(cdrEntity.getFileName().trim());
            });
            fileNameSet.forEach(fileName->{
                Optional<Integer> totalUsedAmount = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Integer.parseInt(cdrEntity.getUsedAmount().replace("KB","")))
                        .reduce((a,b)->(a+b));
                gprsInfoDto.setAbsoluteTotalUsedAmount(String.valueOf(totalUsedAmount.get()));
                gprsInfoDto.setRoundedTotalUsedAmount(RoundingUtil.roundVolume(String.valueOf(totalUsedAmount.get())));
                gprsInfoDto.setFileName(fileName);
                Optional<Double> totalCharge = cdrEntityList.stream().filter(cdrEntity -> cdrEntity.getFileName().equals(fileName))
                        .map(cdrEntity -> Double.parseDouble(cdrEntity.getCharge()))
                        .reduce((a,b)->(a+b));
                gprsInfoDto.setTotalChargePerFileAndDay(String.valueOf(totalCharge.get()));
            });
        }
        return gprsInfoDto;
    }
}
