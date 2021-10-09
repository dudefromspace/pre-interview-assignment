package com.interview.rakuten.preinterviewassignment.controller;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/preinterviewassignment/api/v1")
public class PreInterviewAssignmentController {

    private static String FILE_NAME_REGEX_PATTERN = "^CDRs[0-9]{4}$";

    @Value("${temp.storage.path}")
    private String uploadFilePath;

    @Autowired
    private CDRService cdrService;

    @PostMapping(value = "/cdr/upload")
    public ResponseEntity<String> uploadCDRFile(@RequestParam ("file") MultipartFile file) throws IOException, CDRException {

        String inputFile = file.getOriginalFilename();
        String fileType = this.getFileType(inputFile);
        ParseUtilFactory factory = new ParseUtilFactory();
        ParseUtil parseUtil = factory.getParseUtil(fileType);
        this.uploadFile(file,fileType);
        File cdrFile = this.convertMultipartFile(file);
        List<CDRDto> cdrDtoList = parseUtil.parse(cdrFile);
        List<CDRDto> cdrDtoListAdded = cdrService.addCDR(cdrDtoList);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully uploaded the CDR data file");
    }

    private String getFileType(String fileName) {
       if(fileName.endsWith(".json"))
           return "JSON";
       else if (fileName.endsWith(".xml"))
           return "XML";
       else
           return "CSV";
    }

    private File convertMultipartFile(MultipartFile file) throws IOException {
        String inputFile = file.getOriginalFilename();
        File convertedFile = new File(inputFile);
        convertedFile.createNewFile();
        try(OutputStream stream = new FileOutputStream(convertedFile)){
            stream.write(file.getBytes());
        }
        return convertedFile;
    }

    private void uploadFile(MultipartFile file, String fileType) throws IOException {
        if(fileType.equals("JSON")){
            Path dirPath = Paths.get(uploadFilePath + File.separator + "json");
            dirPath = Files.createDirectory(dirPath);
            File uploadedFile = new File(dirPath + File.separator+ file.getOriginalFilename());
            file.transferTo(uploadedFile);
        }else if(fileType.equals("XML")){
            Path dirPath = Paths.get(uploadFilePath + File.separator + "xml");
            dirPath = Files.createDirectory(dirPath);
            File uploadedFile = new File(dirPath + File.separator+ file.getOriginalFilename());
            file.transferTo(uploadedFile);
        }else {
            Path dirPath = Paths.get(uploadFilePath + File.separator + "csv");
            dirPath = Files.createDirectory(dirPath);
            File uploadedFile = new File(dirPath + File.separator+ file.getOriginalFilename());
            file.transferTo(uploadedFile);
        }
    }
}
