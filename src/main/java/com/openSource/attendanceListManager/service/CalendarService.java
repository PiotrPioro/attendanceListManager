package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Days;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarService {

    public List<Days> getCalendarList(int month, int year){
        List<Days> calendarList = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Integer weekday = calendar.get(Calendar.DAY_OF_WEEK);

        //tworzy początkowe wcięcie
        for(int i=2; i<weekday; i++){
            calendarList.add(new Days(i));
        }

        //tworzenie kalendarza
        for(Integer i=1; i<=monthDays; i++){
            Days days = new Days();
            days.setMonthDay(i);
            days.setWeekDay(weekday);
            calendarList.add(days);
            //zwiększa dzień tygodnia o 1
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            weekday = calendar.get(Calendar.DAY_OF_WEEK);
        }

        return calendarList;
    }

    public int currentMonth(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }
}
