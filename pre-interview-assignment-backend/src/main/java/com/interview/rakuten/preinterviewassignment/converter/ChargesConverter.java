package com.interview.rakuten.preinterviewassignment.converter;

import com.interview.rakuten.preinterviewassignment.dto.ChargesDto;
import com.interview.rakuten.preinterviewassignment.entity.ChargesEntity;

public interface ChargesConverter {

    public ChargesDto convertEntityToDto(ChargesEntity chargesEntity);

    public ChargesEntity convertDtoToEntity(ChargesDto chargesDto);

}
