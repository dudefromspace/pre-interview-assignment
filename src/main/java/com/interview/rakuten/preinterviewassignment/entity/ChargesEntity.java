package com.interview.rakuten.preinterviewassignment.entity;

import com.interview.rakuten.preinterviewassignment.constants.DBConstants;

import javax.persistence.*;

@Entity
@Table(name = DBConstants.CHARGES_TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ChargesEntity {

    @Id
    @Column(unique=true, nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String callCategory;

    @Column(nullable = false)
    private String subscriberType;

    @Column(nullable = false)
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
