package com.interview.rakuten.preinterviewassignment.entity;

import javax.persistence.*;

@Entity
public class CDREntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String ANUM;

    @Column(nullable = false)
    private String BNUM;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String callCategory;

    @Column(nullable = false)
    private String subscriberType;

    @Column(nullable = false)
    private String startDateTime;

    @Column(nullable = false)
    private String usedAmount;

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
}
