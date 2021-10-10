package com.interview.rakuten.preinterviewassignment.services;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.dto.GPRSInfoDto;
import com.interview.rakuten.preinterviewassignment.dto.VoiceCallInfoDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;

import java.util.List;

public interface CDRService {

    CDRDto addCDR(CDRDto cdrDto, String fileName) throws CDRException;

    List<CDRDto> addCDR(List<CDRDto> cdrDtoList, String fileName) throws CDRException;

    List<CDRDto> fetchAll() throws CDRException;

    List<CDRDto> fetchByDate(String date) throws ResourceNotFoundException;

    List<CDRDto> fetchByMaxCharge() throws ResourceNotFoundException;

    List<CDRDto> fetchByMaxDuration() throws ResourceNotFoundException;

    List<VoiceCallInfoDto> fetchTotalVoiceCallDurationByCategoryAndDate(String date) throws ResourceNotFoundException;

    List<GPRSInfoDto> fetchTotalGPRSVolumeBySubscriberTypeAndDate(String date) throws ResourceNotFoundException;
}
