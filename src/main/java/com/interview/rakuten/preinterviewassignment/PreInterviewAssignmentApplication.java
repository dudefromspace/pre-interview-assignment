package com.interview.rakuten.preinterviewassignment;

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



	@Override
	public void run(String... args) throws Exception {
		/*String FILE_NAME_REGEX_PATTERN = "^CDRs[0-9]{4}$";
		Pattern p = Pattern.compile(FILE_NAME_REGEX_PATTERN);

		String st = "CDRs0001";
		System.out.println(p.matcher(st).matches());*/
		/*String date = "20210807";
		System.out.println(date.substring(0,4));
		System.out.println(date.substring(0,4));
		System.out.println(date.substring(0,4));*/
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);list.add(5);
		list.add(4);list.add(3);list.add(9);
		Optional<Integer> temp = list.stream().reduce((a, b)-> (a + b));
		System.out.println(temp.get());
	}
}
