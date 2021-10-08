package com.interview.rakuten.preinterviewassignment.utils.parseUtils;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.util.List;

public class CSVParseUtil implements ParseUtil<CDRDto> {

    @Override
    public List<CDRDto> parse(File file) throws IOException {

        Reader reader = new FileReader(file.getPath());
        List<CDRDto> cdrDtoList = new CsvToBeanBuilder(reader)
                .withType(CDRDto.class)
                .withSeparator('#')
                .withIgnoreQuotations(true)
                .withSkipLines(0)
                .build().parse();

        return cdrDtoList;
    }
}
