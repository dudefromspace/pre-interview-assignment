package com.interview.rakuten.preinterviewassignment.validators;

import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;

public class DateTimeFormatValidator {

    public void validateDate(String date) throws ValidationException {
        if(date.isEmpty() || date.length()<8)
            throw new ValidationException("Date should be in YYYYMMDD format");
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        if(Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12)
            throw new ValidationException("Incorrect month");
        if(Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31)
            throw new ValidationException("Incorrect month");
    }

    public void validateTime(String time) throws ValidationException {
        if(time.isEmpty() || time.length()<6)
            throw new ValidationException("Date should be in HHMMSS format");
        String hour = time.substring(0,2);
        String minutes = time.substring(2,4);
        String seconds = time.substring(4,6);
        if(Integer.parseInt(hour) < 1 || Integer.parseInt(hour) > 23)
            throw new ValidationException("Incorrect hour");
        if(Integer.parseInt(minutes) < 1 || Integer.parseInt(minutes) > 59)
            throw new ValidationException("Incorrect hour");
        if(Integer.parseInt(seconds) < 1 || Integer.parseInt(hour) > 59)
            throw new ValidationException("Incorrect seconds");
    }
}
