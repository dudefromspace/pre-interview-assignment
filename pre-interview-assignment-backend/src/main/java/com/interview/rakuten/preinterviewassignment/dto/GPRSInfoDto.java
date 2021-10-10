package com.interview.rakuten.preinterviewassignment.dto;

import java.util.List;

public class GPRSInfoDto {
    private String subscriberType;
    private List<InfoDto> infoDtoList;

    public String getSubscriberType() {
        return subscriberType;
    }

    public void setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
    }

    public List<InfoDto> getInfoDtoList() {
        return infoDtoList;
    }

    public void setInfoDtoList(List<InfoDto> infoDtoList) {
        this.infoDtoList = infoDtoList;
    }
}
