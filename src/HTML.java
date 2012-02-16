import html.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class HTML {
    public static void getHTML(ArrayList<Event> filteredEvents) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out
                    .println("Input a folder path where you want to generate HTML (include the last slash): ");
            String htmlFolderPath = reader.readLine();
            File file = new File(htmlFolderPath);

            if (file.isDirectory()) {
                getHTMLspecificEvent(filteredEvents,htmlFolderPath);
                getHTMLsummaryTable(filteredEvents,htmlFolderPath);
            } else {
                System.out.println("folder not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getHTMLspecificEvent(ArrayList<Event> filteredEvents,String htmlFolderPath) {
        for (Event e : filteredEvents) {
            Tag html = new Tag("html");
            Tag body = new Tag("body");
            Tag Title = new Tag("Title");
            Title.add("Title: " + e.getTitle() + "<br />");
            body.add(Title);

            Tag Description = new Tag("Description");
            Description.add("Description: " + e.getDescription() + "<br />");
            body.add(Description);

            if (e.getRecurringEventBoolean()) {
                Tag time = new Tag("time");
                time.add("time: RecurringEvent <br />");
                body.add(time);

            } else {
                Tag startTime = new Tag("startTime");
                startTime.add("Start Time: " + e.getStartTime().toString()
                        + "<br />");
                body.add(startTime);

                Tag endTime = new Tag("endTime");
                endTime.add("End Time: " + e.getEndTime().toString() + "<br />");
                body.add(endTime);

                Tag Day = new Tag("Day");
                Day.add("Day: " + e.getStartDay() + "<br />");
                body.add(Day);
            }

            Tag Author = new Tag("Author");
            Author.add("Author: " + e.getAuthor() + "<br />");
            body.add(Author);

            html.add(body);

            // System.out.println(html.toString());

            writeHTML(html, htmlFolderPath+"googlecal"
                    + filteredEvents.indexOf(e) + ".html");
        }
    }

    public static void getHTMLsummaryTable(ArrayList<Event> filteredEvents, String htmlFolderPath) {

        Tag table = new Tag("table", "border=1");

        Tag title = new Tag("title");
        title.add("Goolge Calenda Summary Table");
        table.add(title);

        getTableHead(table);

        Tag tr = new Tag("tr");
        for (int i = 0; i < 7; i++) {
            Tag eventThatDay = new Tag("td");
            for (Event e : filteredEvents) {
                if (!e.getRecurringEventBoolean()
                        && e.getStartTime().getDay() == i) {
                    String att = "href=c:\\Users/Donghe/workspace/Tivoo/html/googlecal"
                            + filteredEvents.indexOf(e) + ".html";
                    Tag event = new Tag("a", att);
                    event.add(e.getTitle() + "<br />");
                    eventThatDay.add(event);
                    eventThatDay.add(timeDisplay(e.getStartTime()) + " to "
                            + timeDisplay(e.getEndTime()) + "<br />");
                }
            }
            tr.add(eventThatDay);
        }
        table.add(tr);

        writeHTML(table,
                htmlFolderPath+"googlecalSummaryTable.html");

    }

    public static void getTableHead(Tag table) {
        Tag tr = new Tag("tr");
        setTableHead(tr, "Sunday");
        setTableHead(tr, "Monday");
        setTableHead(tr, "Tuesday");
        setTableHead(tr, "Wednesday");
        setTableHead(tr, "Thursday");
        setTableHead(tr, "Friday");
        setTableHead(tr, "Saturday");
        table.add(tr);
    }

    public static void setTableHead(Tag tr, String day) {
        Tag head = new Tag("th");
        head.add(day);
        tr.add(head);
    }

    public static void writeHTML(Tag html, String direction) {
        try {
            File file = new File(direction);
            FileWriter fw = new FileWriter(file);
            fw.write(html.toString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String timeDisplay(Date date) {
        String result = "";
        result += date.getHours() < 10 ? ("0" + date.getHours()) : date
                .getHours();
        result += ":";
        result += date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date
                .getMinutes();
        return result;
    }
    /*
     * public static void getHTMLsummaryList(ArrayList<Event> filteredEvents) {
     * try { Tag html = new Tag("html"); Tag body = new Tag("body");
     * 
     * Tag title = new Tag("title"); title.add("Goolge Calenda Summary List");
     * body.add(title);
     * 
     * for (Event e : filteredEvents) { String att =
     * "href=c:\\Users/Donghe/workspace/Tivoo/html/googlecal" +
     * filteredEvents.indexOf(e) + ".html"; Tag event = new Tag("a", att);
     * event.add(e.getTitle()); body.add(event);
     * 
     * if (e.getRecurringEventBoolean()) { Tag time = new Tag("time");
     * time.add("time: RecurringEvent" + "<br />"); body.add(time); } else { Tag
     * startTime = new Tag("startTime"); startTime.add("startTime:" +
     * e.getStartTime().toString()); body.add(startTime);
     * 
     * Tag endTime = new Tag("endTime"); endTime.add("endTime:" +
     * e.getEndTime().toString() + "<br />"); body.add(endTime); }
     * 
     * } html.add(body);
     * 
     * File file = new File(
     * "c:\\Users/Donghe/workspace/Tivoo/html/googlecalSummaryList.html");
     * FileWriter fw = new FileWriter(file); fw.write(html.toString());
     * fw.flush(); fw.close();
     * 
     * } catch (Exception except) { except.printStackTrace(); } }
     */
}