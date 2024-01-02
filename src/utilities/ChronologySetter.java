package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This is the ChronologySetter class. This class sets an algorithm called dynamicBusinessHoursInit. This method allows
 * the scheduler to determine whether or not a proposed appointment time is in congruence with 'business hours' which
 * correspond to preferred hours of doing business in the Eastern time zone.
 */
    public class ChronologySetter {

        public static ObservableList<LocalTime> dynamicBusinessHoursInit(ZoneId osZId, ZoneId businessZId, LocalTime businessHourStart, int workHours) {
            ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
            ZonedDateTime businessZDT = ZonedDateTime.of(LocalDate.now(), businessHourStart, businessZId);
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(businessZDT.toInstant(), osZId);
            int localStartingHour = localZDT.getHour();
            int totalHours = localStartingHour + workHours;
            int midnightOrGreater = 0;

            for(int i = localStartingHour; i <= totalHours; i++) {
                if(i<24) {
                    timeList.add(LocalTime.of(i,0));
                }
                if(i>23) {
                    timeList.add(LocalTime.of(midnightOrGreater,0));
                    midnightOrGreater += 1;
                }
            }
            return timeList;
        }
    }
