package com.interview.rakuten.preinterviewassignment.utils.parseUtils;

public class ParseUtilFactory {

    public ParseUtil getParseUtil(String fileType){
        if(fileType == null){
            return null;
        }
        if(fileType.equalsIgnoreCase("JSON")){
            return new JsonParseUtil();

        } else if(fileType.equalsIgnoreCase("CSV")){
            return new CSVParseUtil();

        } else if(fileType.equalsIgnoreCase("XML")){
            return new XMLParseUtil();
        }

        return null;
    }
}
