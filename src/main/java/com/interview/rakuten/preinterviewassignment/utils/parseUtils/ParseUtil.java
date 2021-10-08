package com.interview.rakuten.preinterviewassignment.utils.parseUtils;

import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ParseUtil<T> {

    public List<T> parse(File file) throws IOException, CsvException;
}

