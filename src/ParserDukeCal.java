package working;

import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserDukeCal {

    public String myXMLFile;
    static ArrayList<Event> myEventList = new ArrayList<Event>();

    public ParserDukeCal(String xmlFilePath) {
        myXMLFile = xmlFilePath;
    }

    public ArrayList<Event> parse() throws SAXException {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                Event myEvent;

                boolean myEntry = false;
                boolean myStart = false;
                boolean myEnd = false;
                boolean myDescription = false;
                boolean mySummary = false;
                boolean myAuthor = false;

                boolean myYear = false;
                boolean myMonthDigits = false;
                boolean myMonthName = false;
                boolean myDayDigits = false;
                boolean myDayName = false;
                boolean myHour = false;
                boolean myMinutes = false;

                boolean creatorIndicator = false;
                boolean atEndEntry = false;
                boolean atEntry = false;

                boolean[] myTags = { myEntry, myStart, myEnd, myDescription,
                        mySummary, myAuthor, myYear, myMonthDigits,
                        myDayDigits, myDayName, myHour, myMinutes };

                int myTagIndex;

                String[] myTagStrings = { "event", "start", "end",
                        "description", "summary", "creator", "year", "month",
                        "day", "dayname", "hour24", "minute" };
                List<String> myTagStringArray = Arrays.asList(myTagStrings);

                public void startElement(String uri, String localName,
                        String qName, Attributes attributes)
                        throws SAXException {

                    if (qName.equalsIgnoreCase("event")) {
                        myEvent = new Event();
                        atEntry = true;
                    }

                    // System.out.println("Start: " + qName);
                    //

                    if (qName.equalsIgnoreCase("end"))
                        atEndEntry = true;

                    myTagIndex = myTagStringArray.indexOf(qName.toLowerCase());

                    if (myTagIndex == -1)
                        return;
                    else
                        myTags[myTagIndex] = true;

                }

                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    // System.out.println("End: " + qName);

                    if (qName.equals("event")) {
                        int[] startDateInfo = myEvent
                                .getDateInformation("start");
                        int[] endDateInfo = myEvent.getDateInformation("end");

                        creatorIndicator = false;
                        atEndEntry = false;
                        atEntry = false;

                        Date startDate = new Date(startDateInfo[0] - 1900,
                                startDateInfo[1] - 1, startDateInfo[2],
                                startDateInfo[3], startDateInfo[4]);
                        Date endDate = new Date(endDateInfo[0] - 1900,
                                endDateInfo[1] - 1, endDateInfo[2],
                                endDateInfo[3], endDateInfo[4]);
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

                    // if (tagString.equals("creator"))
                    // System.out.println(tagString + ": " + tagInfo);

                    if (myTags[myTagIndex] && atEntry) {

                        if (!atEndEntry) {

                            if (tagString.equals("year")) {
                                myEvent.setDateInformation("start", 0,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("month")) {
                                myEvent.setDateInformation("start", 1,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("day")) {
                                myEvent.setDateInformation("start", 2,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("dayname")) {
                                myEvent.setStartDay(tagInfo);
                            }

                            if (tagString.equals("hour24")) {
                                myEvent.setDateInformation("start", 3,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("minute")) {
                                myEvent.setDateInformation("start", 4,
                                        Integer.parseInt(tagInfo));
                            }

                        }

                        if (atEndEntry) {

                            if (tagString.equals("year")) {
                                myEvent.setDateInformation("end", 0,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("month")) {
                                myEvent.setDateInformation("end", 1,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("day")) {
                                myEvent.setDateInformation("end", 2,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("dayname")) {
                                myEvent.setEndDay(tagInfo);
                            }

                            if (tagString.equals("hour24")) {
                                myEvent.setDateInformation("end", 3,
                                        Integer.parseInt(tagInfo));
                            }

                            if (tagString.equals("minute")) {
                                myEvent.setDateInformation("end", 4,
                                        Integer.parseInt(tagInfo));
                            }

                        }

                        if (tagString.equals("creator") && !creatorIndicator) {
                            myEvent.setAuthor(tagInfo);
                            creatorIndicator = true;
                        }

                        if (tagString.equals("summary")) {
                            myEvent.setTitle(tagInfo);
                        }

                        if (tagString.equals("description")) {
                            myEvent.setDescription(tagInfo);
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