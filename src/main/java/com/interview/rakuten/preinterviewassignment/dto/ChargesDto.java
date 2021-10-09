package com.interview.rakuten.preinterviewassignment.dto;

public class ChargesDto {

    private long id;

    private String serviceType;

    private String callCategory;

    private String subscriberType;

    private String chargePerUnit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getChargePerUnit() {
        return chargePerUnit;
    }

    public void setChargePerUnit(String chargePerUnit) {
        this.chargePerUnit = chargePerUnit;
    }
}
