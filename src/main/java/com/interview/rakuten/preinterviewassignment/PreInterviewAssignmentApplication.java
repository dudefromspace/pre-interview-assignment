package com.interview.rakuten.preinterviewassignment;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.CSVParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.JsonParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.XMLParseUtil;
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
//		final Resource fileResource = resourceLoader.getResource("classpath:CDRs0003.json");
//		File csvFile = fileResource.getFile();
//		CSVParseUtil csvParseUtil = new CSVParseUtil();
//		XMLParseUtil xmlParseUtil = new XMLParseUtil();
//		JsonParseUtil jsonParseUtil = new JsonParseUtil();
//		List<CDRDto> dtoList = csvParseUtil.parse(csvFile);
//		List<CDRDto> dtoList = xmlParseUtil.parse(csvFile);
//		List<CDRDto> dtoList = jsonParseUtil.parse(csvFile);
//		System.out.println(dtoList);
		/*String startDateTime = "20210708141050";
		String month = startDateTime.substring(4,6);
		String day = startDateTime.substring(6,8);
		String hour = startDateTime.substring(8,10);
		String minute = startDateTime.substring(10,12);
		String seconds = startDateTime.substring(12,14);*/
	}
}
