package com.interview.rakuten.preinterviewassignment.services;

import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;

public interface ChargeService {

    public String fetchChargePerUnitByServiceTypeCallCategoryAndSubscriberType(String serviceType, String callCategory, String subscriberType) throws ResourceNotFoundException;

    public String calculateCharges(String roundedUsedAmount, String chargePerUnit);
}
