package com.interview.rakuten.preinterviewassignment.services;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;

import java.util.List;

public interface CDRService {

    CDRDto addCDR(CDRDto cdrDto) throws CDRException;

    List<CDRDto> addCDR(List<CDRDto> cdrDtoList) throws CDRException;

    List<CDRDto> fetchAll() throws CDRException;

    List<CDRDto> fetchByDate(String date) throws ResourceNotFoundException;

    List<CDRDto> fetchByMaxCharge() throws ResourceNotFoundException;

    List<CDRDto> fetchByMaxDuration() throws ResourceNotFoundException;

    List<CDRDto> fetchByServiceTypeAndCallCategory(String serviceType,String callCategory) throws ResourceNotFoundException;
}
