package com.interview.rakuten.preinterviewassignment.converter;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.entity.CDREntity;

public interface CDRConverter {

    public CDREntity convertDtoToEntity(CDRDto cdrDto);

    public CDRDto convertEntityToDto(CDREntity cdrEntity);
}
