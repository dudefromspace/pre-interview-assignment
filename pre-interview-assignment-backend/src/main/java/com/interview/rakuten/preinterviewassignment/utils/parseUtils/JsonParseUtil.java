package com.interview.rakuten.preinterviewassignment.utils.parseUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParseUtil implements ParseUtil<CDRDto>{

    @Override
    public List<CDRDto> parse(File file) throws CDRException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        TypeReference<List<CDRDto>> mapType = new TypeReference<List<CDRDto>>() {};
        List<CDRDto> cdrDtoList = null;
        try {
            cdrDtoList = mapper.readValue(file,mapType);
        } catch (IOException e) {
            throw new CDRException("Invalid JSON file");
        }
        return cdrDtoList;
    }
}