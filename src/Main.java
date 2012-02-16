import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xml.sax.SAXException;

public class Main {
    static ArrayList<Event> eventList = new ArrayList<Event>();

    public static void main(String[] args) {
        parseXML();
//        filterEventList();
        for (Event e : eventList) {
            System.out.println("---------------------------");
            e.print();
        }
        generateHTML();
    }

    public static void parseXML() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out.print("Input XML file path: ");
            String xmlFilePath = reader.readLine();
            File file = new File(xmlFilePath);

            if (file.exists()) {
                Parser parser = new Parser(xmlFilePath);
                eventList = parser.parse();
            } else {
                System.out.println("File not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void filterEventList() {
        try {
            EventFilters filter = new EventFilters();
            // eventList=filter.filterByTime(eventList, startDate, endDate);
            eventList = filter.filterByTitle(eventList, "Meet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateHTML() {
        try {
            HTML.getHTML(eventList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}