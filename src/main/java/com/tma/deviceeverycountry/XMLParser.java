package com.tma.deviceeverycountry;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Component
public class XMLParser {

    public String parseXML(String xmlText, String tagName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource inputSource;
        String result = "";
        try {
            builder = factory.newDocumentBuilder();
            inputSource = new InputSource(new StringReader(xmlText));
            Document doc = builder.parse(inputSource);
            NodeList list = doc.getElementsByTagName(tagName);
            result = list.item(0).getTextContent();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return result;
    }

}
