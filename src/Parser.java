import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Parser {    

    public String myXMLFile;
    static ArrayList<Event> myEventList = new ArrayList<Event>();
    
    public Parser(String xmlFilePath) {
        myXMLFile = xmlFilePath;
    }

    public ArrayList<Event> parse() throws SAXException {

        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                public void startElement(String uri, String localName,
                        String qName, Attributes attributes)
                        throws SAXException {
                
                    if (qName.equalsIgnoreCase("feed")) {   
                        ParserGoogleCal parser = new ParserGoogleCal(myXMLFile);
                        myEventList = parser.parse();
                    }
                    
                    if (qName.equalsIgnoreCase("events")) {   
                        ParserDukeCal parser = new ParserDukeCal(myXMLFile);
                        myEventList = parser.parse();
                    }
                    
                    if (qName.equalsIgnoreCase("dataroot")) {   
                        ParserICSCal parser = new ParserICSCal(myXMLFile);
                        myEventList = parser.parse();
                    }
                    
                }

            };

            saxParser.parse(myXMLFile, handler);
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return myEventList;

    }

}