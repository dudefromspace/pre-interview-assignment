package com.interview.rakuten.preinterviewassignment.dto;

import java.util.List;

public class VoiceCallInfoDto {

    private String callCategory;
    private List<InfoDto> infoDtoList;

    public String getCallCategory() {
        return callCategory;
    }

    public void setCallCategory(String callCategory) {
        this.callCategory = callCategory;
    }

    public List<InfoDto> getInfoDtoList() {
        return infoDtoList;
    }

    public void setInfoDtoList(List<InfoDto> infoDtoList) {
        this.infoDtoList = infoDtoList;
    }
}
