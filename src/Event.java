import java.util.Date;

public class Event {

    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String startDay;
    private String endDay;
    private String author;
    private boolean recurringEvent = false;
    
    private int[] myStartDateInformation = new int[5];
    private int[] myEndDateInformation = new int[5];

    
    public void setDateInformation(String s, int index, int info)
    {
        if (s.equals("start")) myStartDateInformation[index] = info;
        else myEndDateInformation[index] = info;
    }
    
    public int[] getDateInformation(String s)
    {
        if (s.equals("start")) return myStartDateInformation;
        return myEndDateInformation;
    }
    
    
    public Event() {};
    
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription(){
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public String getStartDay() {
        return startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    
    public void setTitle(String t) {
        title = t;
    }
    
    public void setDescription(String d){
        description = d;
    }

    public void setStartTime(Date start) {
        startTime = start;
    }

    public void setEndTime(Date end) {
        endTime = end;
    }
    
    public void setAuthor(String a){
        author = a;
    }
    
    public void setStartDay(String day) {
        startDay = day;
    }
    
    public void setEndDay(String day) {
        endDay = day;
    }
    
    public void setRecurringEvent() {
        recurringEvent = true;
    }

    public boolean getRecurringEventBoolean() {
        return recurringEvent;
    }
    
    
    public void print() {
        if (recurringEvent) System.out
        .printf("Title: %s \nDescription: %s \nTime: %s \nAuthor: %s\n",
                title, description, "Recurring event", author);
        else
        System.out
                .printf("Title: %s \nDescription: %s \nStart time: %s \nEnd time: %s \nStart day: %s \nEnd day: %s \nAuthor: %s\n",
                        title, description, startTime.toString(),
                        endTime.toString(), startDay, endDay, author);
    }
}