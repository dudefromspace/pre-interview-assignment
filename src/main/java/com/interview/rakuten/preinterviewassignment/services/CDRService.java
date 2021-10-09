package com.interview.rakuten.preinterviewassignment.services;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;

public interface CDRService {

    CDRDto addCDR(CDRDto cdrDto) throws CDRException, ResourceNotFoundException;
}
