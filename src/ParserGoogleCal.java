import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserGoogleCal {

    public String myXMLFile;
    static ArrayList<Event> myEventList = new ArrayList<Event>();
    
    public ParserGoogleCal(String xmlFilePath) {
        myXMLFile = xmlFilePath;
    }

    public ArrayList<Event> parse() throws SAXException {

        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                Event myEvent;
                
                boolean myEntry = false;
                boolean myID = false;
                boolean myPublished = false;
                boolean myUpdated = false;
                boolean myTitle = false;
                boolean mySummary = false;
                boolean myAuthor = false;
                
                boolean atEntry = false;
                
                public char[] temporarySummaryTagInfo;
                int summaryStart;
                int summaryEnd;
                boolean sum = false;
                
                boolean[] myTags = {myEntry, myID, myPublished, myUpdated, myTitle,
                        mySummary, myAuthor};

                int myTagIndex;

                String[] myTagStrings = {"entry", "id", "published", "updated",
                        "title", "summary", "name" };
                List<String> myTagStringArray = Arrays.asList(myTagStrings);

                public void startElement(String uri, String localName,
                        String qName, Attributes attributes)
                        throws SAXException {
                    
                    if (qName.equalsIgnoreCase("entry")) {    
                        myEvent = new Event();
                        atEntry = true;
                    }
                    
                    myTagIndex = myTagStringArray.indexOf(qName.toLowerCase());
                    
                    if (myTagIndex == -1) return;
                    else myTags[myTagIndex] = true;
                    
                }

                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    if (qName.equals("entry")) {
                        
                        atEntry = false;
                        myEventList.add(myEvent);
                    
                    }
                    
//                    if (qName.equalsIgnoreCase("summary") && sum) {
//                        System.out.println(new String(temporarySummaryTagInfo, summaryStart, summaryEnd-summaryStart));
//                    }

                    
                }

                public void characters(char ch[], int start, int length)
                        throws SAXException {
                        if (myTagIndex == -1) return;

                        String tagString = myTagStrings[myTagIndex];
                        String tagInfo = new String(ch, start, length);
                    
                        if (myTags[myTagIndex] && atEntry) {

                        if (tagString.equals("id")) {
                            myEvent.setDescription(tagInfo);
                        }

                        if (tagString.equals("title")) {
                            myEvent.setTitle(tagInfo);
                        }

                        if (tagString.equals("name")) {
                            myEvent.setAuthor(tagInfo);
                        }

                        if (tagString.equals("summary")) {
                            String[] tagStringArray = tagInfo.split(" ");
                        
                            
                            StringBuilder extendedTagInfo = new StringBuilder();

                           
                            
                            if (tagStringArray[0].equals("Recurring")) {
                         
                        
//                                temporarySummaryTagInfo = ch;
//                                summaryStart = start;
//                                summaryEnd = start + length;
//                                sum = true;
//                                
//                                extendedTagInfo = new String(ch,start,length);                                
//                                
//                                String[] extendedTagInfoArray = extendedTagInfo.split(" ");
                                System.out.println(extendedTagInfo);
//                                for (String s: extendedTagInfoArray) {
//                                    System.out.println("SUMMARY: " + s);
//                                }
                            }
                            
                            
                            ParseSummaryInfo.parseSummary(tagStringArray,
                                    myEvent);
                        }
                        myTags[myTagIndex] = false;
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