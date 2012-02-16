import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class ParseSummaryInfo {
    
    
    private static final Pattern DAY_MONTH_REGEX =
    Pattern.compile("\\[A-Za-z]{3}");
    
    private static final Pattern DATE_REGEX =
            Pattern.compile("\\[0-9]+");
            
    
    private static final String[] myDaysAndMonths = { "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat", "Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    private static final List<String> myDaysAndMonthsList = Arrays.asList(myDaysAndMonths);
    
    
    public static int parseDate(String s) {
        StringBuilder date = new StringBuilder();
        for (char c: s.toCharArray())
        {
            if (!Character.isDigit(c)) break;
            date.append(c);
        }
        return Integer.parseInt(new String(date));
    }
    
    public static int parseMonth(String s) {
        return myDaysAndMonthsList.indexOf(s)-6;
    }
    
    public static int[] getHourAndMin(String s) {
        
        StringBuilder[] hourAndMinBuilder = new StringBuilder[2];
        hourAndMinBuilder[0] = new StringBuilder();
        hourAndMinBuilder[1] = new StringBuilder("0");
        
        boolean afterNoon = false;
        
        int index = 0;
        
        for (char c: s.toCharArray()) {
            if (c == ':') {
                index = 1;
                hourAndMinBuilder[index] = new StringBuilder();
                continue;
            }
            
            if (Character.isDigit(c)) {
                hourAndMinBuilder[index].append(c);
            }
          
            if (Character.isLetter(c)) {
                if (c == 'p') afterNoon = true;
            }
        }
        
        
        
        int hour = Integer.parseInt(new String(hourAndMinBuilder[0]));
        int min = Integer.parseInt(new String(hourAndMinBuilder[1]));

        
        if (afterNoon && hour != 12) hour += 12;
        
        int[] hourAndMin = new int[2];
        hourAndMin[0] = hour;
        hourAndMin[1] = min;
        
        return hourAndMin;
    }
    
    public static void parseSummary(String[] s, Event e) 
    {
        int index = -1;
        int month = 0;
        int year = 0;
        int date = 0;
        int[] startTime = new int[2];
        int[] endTime = new int[2];
        boolean endTimeIndicator = false;
        
        for (String str: s) {
            
            if (str.equals("Recurring") || str.equals("event")) {
                e.setRecurringEvent();
                return;
            }
            
            if (str.contains(",")) { 
                date = parseDate(str);
                continue;
            }
            
            if (str.matches("[0-9]{4}")) {
                year = Integer.parseInt(str);
                
                continue;
            }
            
            if (str.contains("pm") || str.contains("am")) {
                if (endTimeIndicator) 
                            endTime = getHourAndMin(str);
                else {
                    startTime = getHourAndMin(str);
                    endTimeIndicator = true;
                }
                
                continue;
            }
            
            index = myDaysAndMonthsList.indexOf(str);
            if (index == -1) continue;

            if (index < 7) {
                e.setStartDay(str);                
                e.setEndDay(str);
            }
            
            month = parseMonth(str);

        }
        
        Date startDate = new Date(year-1900, month-1, date, startTime[0],
                startTime[1]);
        Date endDate = new Date(year-1900, month-1, date, endTime[0],
                endTime[1]);
        e.setStartTime(startDate);
        e.setEndTime(endDate);
    }

    
    public int getIndexOf(String s) {
        int index = myDaysAndMonthsList.indexOf(s);
        return index;
    }

}
