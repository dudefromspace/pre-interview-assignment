package com.interview.rakuten.preinterviewassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtilFactory;
import com.interview.rakuten.preinterviewassignment.validators.CDRInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
    public ResponseEntity<String> uploadCDRFile(@RequestParam ("file") MultipartFile file) throws IOException, CDRException, ValidationException {

        String inputFile = file.getOriginalFilename();
        String fileType = this.getFileType(inputFile);
        ParseUtilFactory factory = new ParseUtilFactory();
        ParseUtil parseUtil = factory.getParseUtil(fileType);
        this.uploadFile(file,fileType);
        File cdrFile = this.convertMultipartFile(file);
        List<CDRDto> cdrDtoList = parseUtil.parse(cdrFile);
        CDRInputValidator cdrInputValidator = new CDRInputValidator(cdrDtoList);
        cdrInputValidator.validate();
        List<CDRDto> cdrDtoListAdded = cdrService.addCDR(cdrDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Successfully uploaded %s CDR records",cdrDtoListAdded.size()));
    }

    @GetMapping(value = "/cdr")
    public ResponseEntity<List<CDRDto>> getCDRs() throws CDRException, ResourceNotFoundException {
        List<CDRDto> cdrDtoList = cdrService.fetchAll();
        return ResponseEntity.ok(cdrDtoList);
    }

    @GetMapping(value = "/cdr/download")
    public ResponseEntity<Resource> getOutputFile() throws ResourceNotFoundException, CDRException, IOException {
        List<CDRDto> cdrDtoList = cdrService.fetchAll();
        Collections.sort(cdrDtoList,
                (cdrDto1, cdrDto2) -> Double.parseDouble(cdrDto1.getCharge()) < Double.parseDouble(cdrDto1.getCharge()) ? -1 : Double.parseDouble(cdrDto1.getCharge()) == Double.parseDouble(cdrDto1.getCharge()) ? 0 : 1);

        String fileName = uploadFilePath + File.separator + "output"+ File.separator + "CDROUT.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName),cdrDtoList);
        Resource resource = new UrlResource(fileName);
        if(resource.exists())
            return ResponseEntity.ok(resource);
        else
            return ResponseEntity.notFound().build();
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
