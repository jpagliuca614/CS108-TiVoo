package working;

import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserICSCal {

    public String myXMLFile;
    static ArrayList<Event> myEventList = new ArrayList<Event>();

    
    private static final String[] myDays = { "Sun", "Mon", "Tue",
        "Wed", "Thu", "Fri", "Sat"};
    
//    private static final List<String> myDaysList = Arrays.asList(myDays);

    
    public ParserICSCal(String xmlFilePath) {
        myXMLFile = xmlFilePath;
    }

    public ArrayList<Event> parse() throws SAXException {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                Event myEvent;

                boolean myCalendar = false;
                boolean mySubject = false;
                boolean myStartDate = false;
                boolean myEndDate = false;
                boolean myStartTime = false;
                boolean myEndTime = false;
                
                boolean myDescription = false;
                boolean myLocation = false;

//                boolean atEndEntry = false;
                boolean atEntry = false;

                boolean[] myTags = { myCalendar, 
                                     mySubject, 
                                     myStartDate,
                                     myEndDate,
                                     myStartTime,
                                     myEndTime,
                                     myDescription,
                                     myLocation 
                                   };

                int myTagIndex;

                String[] myTagStrings = {"Calendar", 
                                         "Subject", 
                                         "StartDate",
                                         "StartTime", 
                                         "EndDate", 
                                         "EndTime",
                                         "Description",
                                         "Location"
                                         };
                
                List<String> myTagStringArray = Arrays.asList(myTagStrings);

                public void startElement(String uri, String localName,
                        String qName, Attributes attributes)
                        throws SAXException {

                    if (qName.equalsIgnoreCase("Calendar")) {
                        myEvent = new Event();
                        atEntry = true;
                    }

                    
//                    System.out.println("Start: " + qName);
                  
//                    if (qName.equalsIgnoreCase("end")) atEndEntry = true;
                                        
                    
                    myTagIndex = myTagStringArray.indexOf(qName);

                    
                    
                    if (myTagIndex == -1)
                        return;
                    else
                        myTags[myTagIndex] = true;
                   
                    
                }

                public void endElement(String uri, String localName,
                        String qName) throws SAXException {


//                    System.out.println("End: " + qName);

                    
                    if (qName.equals("Calendar")) {
                        int[] startDateInfo = myEvent.getDateInformation("start");
                        int[] endDateInfo = myEvent.getDateInformation("end");
                        
//                        creatorIndicator = false;
//                        atEndEntry = false;
                        atEntry = false;
                        
                        
                        Date startDate = new Date(startDateInfo[0] - 1900,
                                startDateInfo[1] - 1, startDateInfo[2],
                                startDateInfo[3], startDateInfo[4]);
                        Date endDate = new Date(endDateInfo[0] - 1900,
                                endDateInfo[1] - 1, endDateInfo[2],
                                endDateInfo[3], endDateInfo[4]);
                        myEvent.setStartDay(myDays[startDate.getDay()]);
                        myEvent.setEndDay(myDays[endDate.getDay()]);
                        myEvent.setStartTime(startDate);
                        myEvent.setEndTime(endDate);
                        myEventList.add(myEvent);

                    }

                }

                public void characters(char ch[], int start, int length)
                        throws SAXException {
                    
                    if (myTagIndex == -1)
                        return;
                    
                    String tagString = myTagStrings[myTagIndex];
                    String tagInfo = new String(ch, start, length);
  
                    
                    if (myTags[myTagIndex] && atEntry) {


                        if (tagString.equals("Subject")) {
                            myEvent.setTitle(tagInfo);
                        }

                        if (tagString.equals("StartDate")) {
                            String[] dateInfo = tagInfo.split("/");
//                            System.out.println(tagInfo);
                            myEvent.setDateInformation("start", 1, Integer.parseInt(dateInfo[0])-1);
                            myEvent.setDateInformation("start", 2, Integer.parseInt(dateInfo[1]));
                            myEvent.setDateInformation("start", 0, Integer.parseInt(dateInfo[2])-1900);
                            
                            
                        }

                        if (tagString.equals("StartTime")) {
                            String[] timeInfo = tagInfo.split(" ");
                            String[] hourMinSec = timeInfo[0].split(":");
                            int hour = Integer.parseInt(hourMinSec[0]);
                            if (timeInfo[1].contains("P") && hour != 12) hour += 12;
                            myEvent.setDateInformation("start", 3, hour);
                            myEvent.setDateInformation("start", 4, Integer.parseInt(hourMinSec[1]));
//                            myEvent.getStartTime().setSeconds(Integer.parseInt(hourMinSec[2]));
                            
                        }
                        
                        if (tagString.equals("EndDate")) {
                            String[] dateInfo = tagInfo.split("/");
                            myEvent.setDateInformation("end", 1, Integer.parseInt(dateInfo[0])-1);
                            myEvent.setDateInformation("end", 2, Integer.parseInt(dateInfo[1]));
                            myEvent.setDateInformation("end", 0, Integer.parseInt(dateInfo[2])-1900);                            
                            
                        }

                        if (tagString.equals("EndTime")) {
                            String[] timeInfo = tagInfo.split(" ");
                            String[] hourMinSec = timeInfo[0].split(":");
                            int hour = Integer.parseInt(hourMinSec[0]);
                            if (timeInfo[1].contains("P") && hour != 12) hour += 12;
                            myEvent.setDateInformation("end", 3, hour);
                            myEvent.setDateInformation("end", 4, Integer.parseInt(hourMinSec[1]));
//                            myEvent.getStartTime().setSeconds(Integer.parseInt(hourMinSec[2]));
                            
                        }
                        if (tagString.equals("Description")) {
                            myEvent.setDescription(tagInfo);
                        }

                        if (tagString.equals("Location")) {
                            myEvent.setAuthor(tagInfo);
                        }
               
                        myTags[myTagIndex] = false;
                    }

                }

            };

            saxParser.parse(myXMLFile, handler);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return myEventList;

    }

}