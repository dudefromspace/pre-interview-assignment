package com.interview.rakuten.preinterviewassignment;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.CSVParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.JsonParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.XMLParseUtil;
import com.interview.rakuten.preinterviewassignment.validators.CDRInputValidator;
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

	@Autowired
	private CDRService cdrService;

	@Override
	public void run(String... args) throws Exception {
		Resource fileResource1 = resourceLoader.getResource("classpath:CDRs_0002.xml");
		/*File xmlFile = fileResource.getFile();
		XMLParseUtil xmlParseUtil = new XMLParseUtil();
		List<CDRDto> dtoList1 = xmlParseUtil.parse(xmlFile);
		CDRInputValidator validator = new CDRInputValidator(dtoList1);
		validator.validate();
		cdrService.addCDR(dtoList1);*/
		Resource fileResource2 = resourceLoader.getResource("classpath:CDRs0003.json");
		File jsonFile = fileResource2.getFile();
		JsonParseUtil jsonParseUtil = new JsonParseUtil();
		List<CDRDto> dtoList2 = jsonParseUtil.parse(jsonFile);
		/*CDRInputValidator validator2 = new CDRInputValidator(dtoList2);
		validator2.validate();*/
		//cdrService.addCDR(dtoList2);
		/*Resource fileResource3 = resourceLoader.getResource("classpath:CDRs0001.csv");
		File csvFile = fileResource3.getFile();
		CSVParseUtil csvParseUtil = new CSVParseUtil();
		List<CDRDto> dtoList3 = csvParseUtil.parse(csvFile);
		CDRInputValidator validator3 = new CDRInputValidator(dtoList3);
		validator3.validate();
		cdrService.addCDR(dtoList3);*/
	}
}
