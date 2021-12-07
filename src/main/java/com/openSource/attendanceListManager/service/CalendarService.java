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

    public int today(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int currentMonth(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }

    public int currentYear(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }

    public String nameOfMonth(int month) {
        String m = "";
        switch (month) {
            case 1:
                m = "Styczeń";
                break;
            case 2:
                m = "Luty";
                break;
            case 3:
                m = "Marzec";
                break;
            case 4:
                m = "Kwiecień";
                break;
            case 5:
                m = "Maj";
                break;
            case 6:
                m = "Czerwiec";
                break;
            case 7:
                m = "Lipiec";
                break;
            case 8:
                m = "Sierpień";
                break;
            case 9:
                m = "Wrzesień";
                break;
            case 10:
                m = "Październik";
                break;
            case 11:
                m = "Listopad";
                break;
            case 12:
                m = "Grudzień";
                break;
        }
        return m;
    }

    public String monthName(int month) {
        String m = "";
        switch (month) {
            case 1:
                m = "Styczniu";
                break;
            case 2:
                m = "Lutym";
                break;
            case 3:
                m = "Marcu";
                break;
            case 4:
                m = "Kwietniu";
                break;
            case 5:
                m = "Maju";
                break;
            case 6:
                m = "Czerwcu";
                break;
            case 7:
                m = "Lipcu";
                break;
            case 8:
                m = "Sierpniu";
                break;
            case 9:
                m = "Wrześniu";
                break;
            case 10:
                m = "Październiku";
                break;
            case 11:
                m = "Listopadzie";
                break;
            case 12:
                m = "Grudniu";
                break;
        }
        return m;
    }
}
