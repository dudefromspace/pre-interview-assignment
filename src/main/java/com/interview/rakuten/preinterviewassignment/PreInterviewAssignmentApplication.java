package com.interview.rakuten.preinterviewassignment;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.CSVParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class PreInterviewAssignmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PreInterviewAssignmentApplication.class, args);
	}

	@Autowired
	private ResourceLoader resourceLoader;



	@Override
	public void run(String... args) throws Exception {
		final Resource fileResource = resourceLoader.getResource("classpath:CDRs0001.csv");
		File csvFile = fileResource.getFile();
		CSVParseUtil csvParseUtil = new CSVParseUtil();
		List<CDRDto> dtoList = csvParseUtil.parse(csvFile);
		System.out.println(dtoList);
	}
}
