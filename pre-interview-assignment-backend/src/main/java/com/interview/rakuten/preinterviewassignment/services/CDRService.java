package com.interview.rakuten.preinterviewassignment.services;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.dto.GPRSInfoDto;
import com.interview.rakuten.preinterviewassignment.dto.VoiceCallInfoDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;

import java.util.List;
import java.util.Map;

public interface CDRService {

    CDRDto addCDR(CDRDto cdrDto, String fileName) throws CDRException;

    List<CDRDto> addCDR(List<CDRDto> cdrDtoList, String fileName) throws CDRException;

    List<CDRDto> fetchAll() throws CDRException;

    List<CDRDto> fetchByDate(String date) throws ResourceNotFoundException;

    List<String> fetchAnumByMaxCharge() throws ResourceNotFoundException, CDRException;

    List<String> fetchAnumByMaxDuration() throws ResourceNotFoundException, CDRException;

    List<VoiceCallInfoDto> fetchTotalVoiceCallDurationByCategoryAndDate(String date) throws ResourceNotFoundException;

    List<GPRSInfoDto> fetchTotalGPRSVolumeBySubscriberTypeAndDate(String date) throws ResourceNotFoundException;

    Map<String,String> getChargePerHour(String date) throws ResourceNotFoundException;
}
