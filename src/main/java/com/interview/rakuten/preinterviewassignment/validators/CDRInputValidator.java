package com.interview.rakuten.preinterviewassignment.validators;

import com.google.common.base.Strings;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;

import java.util.List;
import java.util.regex.Pattern;

public class CDRInputValidator {

    private List<CDRDto> cdrDtoList;
    private static final String ANUM_REGEX_PATTERN = "^[0-9]{12}$";
    private static final String BNUM_REGEX_PATTERN = "^[0-9]{2}?[0-9]{10}$|^[\\+]?[0-9]{10}$";
    private static final String SERVICE_TYPE_REGEX_PATTERN = "^[1-3]$";
    private static final String CALL_CATEGORY_SUBSCRIBER_TYPE_REGEX_PATTERN = "^[1-2]$";
    private static final String START_DATE_TIME_REGEX_PATTERN = "^[0-9]{14}$";
    private static final String USED_AMOUNT_REGEX_PATTERN = "^[0-9]*$";

    private static Pattern anumRegexPattern = Pattern.compile(ANUM_REGEX_PATTERN);
    private static Pattern bnumRegexPattern = Pattern.compile(BNUM_REGEX_PATTERN);
    private static Pattern serviceTypeRegexPattern = Pattern.compile(SERVICE_TYPE_REGEX_PATTERN);
    private static Pattern callCategorySubscriberTypeRegexPattern = Pattern.compile(CALL_CATEGORY_SUBSCRIBER_TYPE_REGEX_PATTERN);
    private static Pattern startDateTimeRegexPattern = Pattern.compile(START_DATE_TIME_REGEX_PATTERN);
    private static Pattern usedAmountRegexPattern = Pattern.compile(USED_AMOUNT_REGEX_PATTERN);
    public  CDRInputValidator(List<CDRDto> cdrDtoList) {
        this.cdrDtoList = cdrDtoList;
    }

    public void validate() throws ValidationException {
        if(this.cdrDtoList.isEmpty()) {
            throw new ValidationException("Input cannot be empty");
        }
        for(CDRDto cdrDto : cdrDtoList) {
            validateANUM(cdrDto.getANUM());
            validateServiceType(cdrDto.getServiceType());
            validateCallCategory(cdrDto.getCallCategory());
            validateSubscriberType(cdrDto.getSubscriberType());
            validateStartDateTime(cdrDto.getStartDateTime());
            validateBNUM(cdrDto.getBNUM(),cdrDto.getServiceType());
            validateUsedAmount(cdrDto.getUsedAmount(),cdrDto.getServiceType());
        }
    }

    private void validateANUM(String anum) throws ValidationException {
        if(Strings.isNullOrEmpty(anum)){
            throw new ValidationException("ANUM cannot be empty");
        }
        if(!anumRegexPattern.matcher(anum).matches()) {
            throw new ValidationException("Incorrect ANUM");
        }
    }

    private void validateBNUM(String bnum, String serviceType) throws ValidationException {
        if(serviceType.equals("3") && !Strings.isNullOrEmpty(bnum)) {
            throw new ValidationException("BNUM is not required for GPRS service");
        }
        if(!Strings.isNullOrEmpty(bnum) && !bnumRegexPattern.matcher(bnum).matches()){
            throw new ValidationException("Incorrect BNUM");
        }
    }

    private void validateServiceType(String serviceType) throws ValidationException {
        if(Strings.isNullOrEmpty(serviceType)){
            throw new ValidationException("Service type cannot be empty");
        }
        if(!serviceTypeRegexPattern.matcher(serviceType).matches()) {
            throw new ValidationException("Incorrect service type");
        }
    }

    private void validateCallCategory(String callCategory) throws ValidationException {
        if(Strings.isNullOrEmpty(callCategory)){
            throw new ValidationException("Call category cannot be empty");
        }
        if(!callCategorySubscriberTypeRegexPattern.matcher(callCategory).matches()) {
            throw new ValidationException("Incorrect call category");
        }
    }

    private void validateSubscriberType(String subscriberType) throws ValidationException {
        if(Strings.isNullOrEmpty(subscriberType)){
            throw new ValidationException("Subscriber type cannot be empty");
        }
        if(!callCategorySubscriberTypeRegexPattern.matcher(subscriberType).matches()) {
            throw new ValidationException("Incorrect subscriber type");
        }
    }

    private void validateStartDateTime(String startDateTime) throws ValidationException {
        if(Strings.isNullOrEmpty(startDateTime)){
            throw new ValidationException("Start date and time cannot be empty");
        }
        if(!startDateTimeRegexPattern.matcher(startDateTime).matches()) {
            throw new ValidationException("Incorrect start date and time");
        }
        String month = startDateTime.substring(4,6);
        String day = startDateTime.substring(6,8);
        String hour = startDateTime.substring(8,10);
        String minutes = startDateTime.substring(10,12);
        String seconds = startDateTime.substring(12,14);
        if(Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12)
            throw new ValidationException("Incorrect month");
        if(Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31)
            throw new ValidationException("Incorrect month");
        if(Integer.parseInt(hour) < 1 || Integer.parseInt(hour) > 23)
            throw new ValidationException("Incorrect hour");
        if(Integer.parseInt(minutes) < 1 || Integer.parseInt(minutes) > 59)
            throw new ValidationException("Incorrect hour");
        if(Integer.parseInt(seconds) < 1 || Integer.parseInt(hour) > 59)
            throw new ValidationException("Incorrect seconds");
    }

    private void validateUsedAmount(String usedAmount, String serviceType) throws ValidationException {
        if(serviceType.equals("2") && !Strings.isNullOrEmpty(usedAmount)){
            throw new ValidationException("Used amount is not applicable for SMSs");
        }else if(Strings.isNullOrEmpty(usedAmount)){
            throw new ValidationException("Used amount cannot be empty");
        }
        if(!usedAmountRegexPattern.matcher(usedAmount).matches()) {
            throw new ValidationException("Incorrect used amount");
        }
    }
}
