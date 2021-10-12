package com.interview.rakuten.preinterviewassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.rakuten.preinterviewassignment.converter.CDRConverter;
import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.dto.GPRSInfoDto;
import com.interview.rakuten.preinterviewassignment.dto.VoiceCallInfoDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;
import com.interview.rakuten.preinterviewassignment.exceptions.ResourceNotFoundException;
import com.interview.rakuten.preinterviewassignment.exceptions.ValidationException;
import com.interview.rakuten.preinterviewassignment.services.CDRService;
import com.interview.rakuten.preinterviewassignment.utils.RoundingUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtil;
import com.interview.rakuten.preinterviewassignment.utils.parseUtils.ParseUtilFactory;
import com.interview.rakuten.preinterviewassignment.validators.CDRInputValidator;
import com.interview.rakuten.preinterviewassignment.validators.DateTimeFormatValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
        File cdrFile = this.uploadFile(file,fileType);
        List<CDRDto> cdrDtoList = parseUtil.parse(cdrFile);
        CDRInputValidator cdrInputValidator = new CDRInputValidator(cdrDtoList);
        cdrInputValidator.validate();
        List<CDRDto> cdrDtoListAdded = cdrService.addCDR(cdrDtoList,inputFile);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Successfully uploaded %s CDR records",cdrDtoListAdded.size()));
    }

    @GetMapping(value = "/cdr")
    public ResponseEntity<List<CDRDto>> getCDRs() throws CDRException {
        System.out.println("Inside getCDR" );
        List<CDRDto> cdrDtoList = cdrService.fetchAll();
        return ResponseEntity.ok(cdrDtoList);
    }

    @GetMapping(value = "/cdr/download", produces = "text/csv; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public Resource getOutputFile(HttpServletResponse response) throws CDRException, IOException {
        List<CDRDto> cdrDtoList = cdrService.fetchAll();
        Collections.sort(cdrDtoList,
                (cdrDto1, cdrDto2) -> Double.parseDouble(cdrDto1.getCharge()) < Double.parseDouble(cdrDto2.getCharge()) ? -1 : Double.parseDouble(cdrDto1.getCharge()) == Double.parseDouble(cdrDto2.getCharge()) ? 0 : 1);

        Path dirPath = Paths.get(uploadFilePath + File.separator + "cdr");
        if(!dirPath.toFile().exists()){
            dirPath = Files.createDirectory(dirPath);
        }

        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + "CDROUT.json");
        response.setHeader("filename", "CDROUT.json");
        return new FileSystemResource(dirPath + File.separator + "CDROUT.json");
    }

    @GetMapping(value = "/cdr/duration")
    public ResponseEntity<String> getTotalDurationByDate(@RequestParam ("date") String date) throws ValidationException, ResourceNotFoundException {
        DateTimeFormatValidator dateTimeFormatValidator = new DateTimeFormatValidator();
        dateTimeFormatValidator.validateDate(date);
        List<CDRDto> cdrDtoList = cdrService.fetchByDate(date);
        Optional<Integer> totalDuration = cdrDtoList.stream().filter(cdrDto -> cdrDto.getServiceType().equals(CDRConverter.ServiceType.VOICE.toString()))
                .map(cdrDto -> Integer.parseInt(cdrDto.getUsedAmount().replace("s","")))
                .reduce((a,b)->(a + b));
        return ResponseEntity.ok(RoundingUtil.roundDuration(String.valueOf(totalDuration.get()))+"minutes");
    }

    @GetMapping(value = "/cdr/volume")
    public ResponseEntity<String> getTotalVolumeByDate(@RequestParam ("date") String date) throws ValidationException, ResourceNotFoundException {
        DateTimeFormatValidator dateTimeFormatValidator = new DateTimeFormatValidator();
        dateTimeFormatValidator.validateDate(date);
        List<CDRDto> cdrDtoList = cdrService.fetchByDate(date);
        Optional<Integer> totalVolume = cdrDtoList.stream().filter(cdrDto -> cdrDto.getServiceType().equals(CDRConverter.ServiceType.GPRS.toString()))
                .map(cdrDto -> Integer.parseInt(cdrDto.getUsedAmount().replace("KB","")))
                .reduce((a,b)->(a + b));
        return ResponseEntity.ok(RoundingUtil.roundVolume(String.valueOf(totalVolume.get())));
    }

    @GetMapping(value = "/cdr/anum/maxcharge")
    public ResponseEntity<List<String>> getAnumWithMaxCharges() throws ResourceNotFoundException {
        List<CDRDto> cdrDtoList = cdrService.fetchByMaxCharge();
        List<String> anumWithMaxCharges = cdrDtoList.stream().map(cdrDto -> cdrDto.getANUM()).collect(Collectors.toList());
        return ResponseEntity.ok(anumWithMaxCharges);
    }

    @GetMapping(value = "/cdr/anum/maxduration")
    public ResponseEntity<List<String>> getAnumWithMaxDuration() throws ResourceNotFoundException {
        List<CDRDto> cdrDtoList = cdrService.fetchByMaxDuration();
        List<String> anumWithMaxDuration = cdrDtoList.stream().map(cdrDto -> cdrDto.getANUM()).collect(Collectors.toList());
        return ResponseEntity.ok(anumWithMaxDuration);
    }

    @GetMapping(value = "/cdr/serviceType/maxcharge")
    public ResponseEntity<List<String>> getServiceTypeWithMaxChargeByDate(@RequestParam ("date") String date) throws ResourceNotFoundException, ValidationException {
        DateTimeFormatValidator dateTimeFormatValidator = new DateTimeFormatValidator();
        dateTimeFormatValidator.validateDate(date);
        List<CDRDto> cdrDtoList = cdrService.fetchByDate(date);
        Map<String,Double> map = new HashMap<>();
        Optional<Double> voiceCharge = cdrDtoList.stream().filter(cdrDto -> cdrDto.getServiceType().equals(CDRConverter.ServiceType.VOICE.toString()))
                .map(cdrDto -> Double.parseDouble(cdrDto.getCharge()))
                .reduce((a,b)->(a+b));

        map.put(CDRConverter.ServiceType.VOICE.toString(),voiceCharge.get());

        Optional<Double> gprsCharge = cdrDtoList.stream().filter(cdrDto -> cdrDto.getServiceType().equals(CDRConverter.ServiceType.GPRS.toString()))
                .map(cdrDto -> Double.parseDouble(cdrDto.getCharge()))
                .reduce((a,b)->(a+b));
        map.put(CDRConverter.ServiceType.GPRS.toString(),gprsCharge.get());

        Optional<Double> smsCharge = cdrDtoList.stream().filter(cdrDto -> cdrDto.getServiceType().equals(CDRConverter.ServiceType.SMS.toString()))
                .map(cdrDto -> Double.parseDouble(cdrDto.getCharge()))
                .reduce((a,b)->(a+b));
        map.put(CDRConverter.ServiceType.SMS.toString(),smsCharge.get());

        Map.Entry<String, Double> maxEntry = Collections.max(map.entrySet(), Comparator.comparing(Map.Entry::getValue));

        return ResponseEntity.ok(Arrays.asList(date,maxEntry.getKey(), maxEntry.getValue().toString()));
    }

    @GetMapping(value = "/cdr/duration/callcategory")
    public ResponseEntity<List<VoiceCallInfoDto>> getTotalVoiceCallDurationByCategoryAndDate(@RequestParam ("date") String date) throws ResourceNotFoundException, ValidationException {
        DateTimeFormatValidator dateTimeFormatValidator = new DateTimeFormatValidator();
        dateTimeFormatValidator.validateDate(date);
        List<VoiceCallInfoDto> voiceCallInfoDtoList = cdrService.fetchTotalVoiceCallDurationByCategoryAndDate(date);
        return ResponseEntity.ok(voiceCallInfoDtoList);
    }

    @GetMapping(value = "/cdr/volume/subscriberType")
    public ResponseEntity<List<GPRSInfoDto>> getTotalGPRSVolumeByCategoryAndDate(@RequestParam ("date") String date) throws ResourceNotFoundException, ValidationException {
        DateTimeFormatValidator dateTimeFormatValidator = new DateTimeFormatValidator();
        dateTimeFormatValidator.validateDate(date);
        List<GPRSInfoDto> gprsInfoDtoList = cdrService.fetchTotalGPRSVolumeBySubscriberTypeAndDate(date);
        return ResponseEntity.ok(gprsInfoDtoList);
    }

    @GetMapping(value = "/cdr/chargePerHour")
    public ResponseEntity<Map<String,String>> getChargePerHour(@RequestParam ("date") String date) throws ResourceNotFoundException, ValidationException {
        DateTimeFormatValidator dateTimeFormatValidator = new DateTimeFormatValidator();
        dateTimeFormatValidator.validateDate(date);
        Map<String,String> map = cdrService.getChargePerHour(date);
        return ResponseEntity.ok(map);
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

    private File uploadFile(MultipartFile file, String fileType) throws IOException {
        if(fileType.equals("JSON")){
            Path dirPath = Paths.get(uploadFilePath + File.separator + "json");
            if(!dirPath.toFile().exists()){
                dirPath = Files.createDirectory(dirPath);
            }
            File uploadedFile = new File(dirPath + File.separator+ file.getOriginalFilename());
            file.transferTo(uploadedFile);
            return uploadedFile;
        }else if(fileType.equals("XML")){
            Path dirPath = Paths.get(uploadFilePath + File.separator + "xml");
            dirPath = Files.createDirectory(dirPath);
            File uploadedFile = new File(dirPath + File.separator+ file.getOriginalFilename());
            file.transferTo(uploadedFile);
            return uploadedFile;
        }else {
            Path dirPath = Paths.get(uploadFilePath + File.separator + "csv");
            dirPath = Files.createDirectory(dirPath);
            File uploadedFile = new File(dirPath + File.separator+ file.getOriginalFilename());
            file.transferTo(uploadedFile);
            return uploadedFile;

        }
    }
}
