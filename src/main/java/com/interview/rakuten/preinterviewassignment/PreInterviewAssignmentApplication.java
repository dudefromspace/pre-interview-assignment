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
import java.util.regex.Pattern;

@SpringBootApplication
public class PreInterviewAssignmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PreInterviewAssignmentApplication.class, args);
	}

	@Autowired
	private ResourceLoader resourceLoader;



	@Override
	public void run(String... args) throws Exception {
		String FILE_NAME_REGEX_PATTERN = "^CDRs[0-9]{4}$";
		Pattern p = Pattern.compile(FILE_NAME_REGEX_PATTERN);

		String st = "CDRs0001";
		System.out.println(p.matcher(st).matches());
	}
}
