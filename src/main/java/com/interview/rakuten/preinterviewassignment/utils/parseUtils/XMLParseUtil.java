package com.interview.rakuten.preinterviewassignment.utils.parseUtils;

import com.interview.rakuten.preinterviewassignment.dto.CDRDto;
import com.interview.rakuten.preinterviewassignment.exceptions.CDRException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class XMLParseUtil implements ParseUtil<CDRDto> {

    @Override
    public List<CDRDto> parse(File file) throws CDRException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        List<CDRDto> cdrDtoList = new ArrayList<>();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));
            CDRDto cdrDto = null;
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "CDR":
                            cdrDto = new CDRDto();
                            break;
                        case "ANUM":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setANUM(nextEvent.asCharacters().getData());
                            }
                            break;
                        case "BNUM":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setBNUM(nextEvent.asCharacters().getData() != null ? nextEvent.asCharacters().getData() : "");
                            }
                            break;
                        case "ServiceType":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setServiceType(nextEvent.asCharacters().getData());
                            }
                            break;
                        case "CallCategory":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setCallCategory(nextEvent.asCharacters().getData());
                            }
                            break;
                        case "SubscriberType":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setSubscriberType(nextEvent.asCharacters().getData());
                            }
                            break;
                        case "StartDatetime":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setStartDateTime(nextEvent.asCharacters().getData());
                            }
                            break;
                        case "UsedAmount":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isCharacters()) {
                                cdrDto.setUsedAmount(nextEvent.asCharacters().getData() != null ? nextEvent.asCharacters().getData() : "");
                            }
                            break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("CDR")) {
                        if(cdrDto.getBNUM() == null)
                            cdrDto.setBNUM("");
                        if(cdrDto.getUsedAmount() == null)
                            cdrDto.setUsedAmount("");
                        cdrDtoList.add(cdrDto);
                        cdrDto = null;
                    }
                }
            }
        }catch (FileNotFoundException|XMLStreamException e){
            throw new CDRException("Invalid XML file");
        }
        return cdrDtoList;
    }
}