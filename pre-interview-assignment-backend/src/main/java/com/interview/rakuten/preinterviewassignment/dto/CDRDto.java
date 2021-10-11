package com.interview.rakuten.preinterviewassignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CDRDto {

    private long id;
    @CsvBindByName
    private String ANUM;
    @CsvBindByName
    private String BNUM;
    @CsvBindByName
    private String serviceType;
    @CsvBindByName
    private String callCategory;
    @CsvBindByName
    private String subscriberType;
    @CsvBindByName
    private String startDateTime;
    @CsvBindByName
    private String usedAmount;

    private String charge;

    private String fileName;

    private String roundedUsedAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getANUM() {
        return ANUM;
    }

    public void setANUM(String ANUM) {
        this.ANUM = ANUM;
    }

    public String getBNUM() {
        return BNUM;
    }

    public void setBNUM(String BNUM) {
        this.BNUM = BNUM;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCallCategory() {
        return callCategory;
    }

    public void setCallCategory(String callCategory) {
        this.callCategory = callCategory;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public void setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(String usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getRoundedUsedAmount() {
        return roundedUsedAmount;
    }

    public void setRoundedUsedAmount(String roundedUsedAmount) {
        this.roundedUsedAmount = roundedUsedAmount;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
