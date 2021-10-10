package com.interview.rakuten.preinterviewassignment;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.CSVParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.XMLParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@SpringBootApplication
public class PreInterviewAssignmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PreInterviewAssignmentApplication.class, args);
	}

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private CDRService cdrService;

	@Override
	public void run(String... args) throws Exception {
		final Resource fileResource = resourceLoader.getResource("classpath:CDRs_0002.xml");
		File xmlFile = fileResource.getFile();
		XMLParseUtil xmlParseUtil = new XMLParseUtil();
		List<CDRDto> dtoList = xmlParseUtil.parse(xmlFile);
		cdrService.addCDR(dtoList);
		System.out.println(dtoList);
	}
}
