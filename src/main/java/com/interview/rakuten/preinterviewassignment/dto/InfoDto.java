package com.interview.rakuten.preinterviewassignment.dto;

public class InfoDto {
    private String absoluteTotalUsedAmount;
    private String roundedTotalUsedAmount;
    private String fileName;
    private String totalChargePerFileAndDay;

    public String getTotalChargePerFileAndDay() {
        return totalChargePerFileAndDay;
    }

    public void setTotalChargePerFileAndDay(String totalChargePerFileAndDay) {
        this.totalChargePerFileAndDay = totalChargePerFileAndDay;
    }

    public String getAbsoluteTotalUsedAmount() {
        return absoluteTotalUsedAmount;
    }

    public void setAbsoluteTotalUsedAmount(String absoluteTotalUsedAmount) {
        this.absoluteTotalUsedAmount = absoluteTotalUsedAmount;
    }

    public String getRoundedTotalUsedAmount() {
        return roundedTotalUsedAmount;
    }

    public void setRoundedTotalUsedAmount(String roundedTotalUsedAmount) {
        this.roundedTotalUsedAmount = roundedTotalUsedAmount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
