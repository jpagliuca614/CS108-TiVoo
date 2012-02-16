package working;

import java.util.ArrayList;
import java.util.Date;

public class EventFilters {

    public ArrayList<Event> filterByTime(ArrayList<Event> parsedEvents,
            Date startDate, Date endDate) throws Exception {
        if (parsedEvents.isEmpty()){
            throw new Exception("No parsed events to filter!");
        }
        ArrayList<Event> filteredEvents = new ArrayList<Event>();
        for (Event e : parsedEvents) {
            Date startTime = e.getStartTime();
            Date endTime = e.getEndTime();
            if (startTime.after(startDate) && endTime.before(endDate)) {
                filteredEvents.add(e);
            }
        }
        return filteredEvents;
    }

    public ArrayList<Event> filterByTitle(ArrayList<Event> parsedEvents,
            String titleFilter) throws Exception {
        if (parsedEvents.isEmpty()){
            throw new Exception("No parsed events to filter!");
        }
        ArrayList<Event> filteredEvents = new ArrayList<Event>();
        for (Event e : parsedEvents) {
            String title = e.getTitle();
            if (!(title.indexOf(titleFilter) >= 0)) {
                filteredEvents.add(e);
            }
        }
        return filteredEvents;
    }

}