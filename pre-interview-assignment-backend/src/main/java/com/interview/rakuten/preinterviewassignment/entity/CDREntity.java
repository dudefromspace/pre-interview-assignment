package com.interview.rakuten.preinterviewassignment.entity;

import com.interview.rakuten.preinterviewassignment.constants.DBConstants;

import javax.persistence.*;

@Entity
@Table(name= DBConstants.CDR_TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames="id"))
public class CDREntity {

    @Id
    @Column(unique=true, nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String ANUM;

    @Column(nullable = true)
    private String BNUM;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String callCategory;

    @Column(nullable = false)
    private String subscriberType;

    @Column(nullable = false)
    private String startDateTime;

    @Column(nullable = true)
    private String usedAmount;

    @Column(nullable = true)
    private String roundedUsedAmount;

    @Column(nullable = false)
    private String charge;

    @Column(nullable = false)
    private String fileName;

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

    public String getRoundedUsedAmount() {
        return roundedUsedAmount;
    }

    public void setRoundedUsedAmount(String roundedUsedAmount) {
        this.roundedUsedAmount = roundedUsedAmount;
    }
}
