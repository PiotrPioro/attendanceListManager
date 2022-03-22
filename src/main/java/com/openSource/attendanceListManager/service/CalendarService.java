package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.repository.DayAmountInMonthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@AllArgsConstructor
public class CalendarService {

    private final ContractService contractService;
    private final ContractDetailService contractDetailService;
    private final DaysAmountService daysAmountService;
    private final InspectorService inspectorService;
    private final DayAmountInMonthRepository dayAmountInMonthRepository;

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

    public LocalDate easterMonday(int year){
        LocalDate date = LocalDate.of(year, 2, 1);
        LocalDate easter = date;
        /*Do obliczeń potrzebne są dwie liczby A i B. Stałe z internetu obecne dla lat do 2099*/
        final int A = 24;
        final int B = 5;

        int a = date.getYear() % 19;
        int b = date.getYear() % 4;
        int c = date.getYear() % 7;
        int d = (a*19 + A) % 30;
        int e = (2*b + 4*c + 6*d + B) % 7;

        /*wielkanoc = 22 marzec + d + e
        Zatem suma zmiennych d oraz e oznacza ile dni po 22 marca przypada wielkanoc.*/

        /*dla podanej metody mamy dwa wyjątki:
        jeżeli d = 29 oraz e = 6 to Wielkanoc miałaby przypaść na dzień 26 kwietnia. Wtedy zawsze obchodzi się ją tydzień wcześniej, tzn. 19 kwietnia
        jeżeli d = 28 oraz e = 6 to Wielkanoc miałaby przypaść 25 kwietnia. Wtedy zawsze obchodzi się ją tydzień wcześniej, tzn. 18 kwietnia*/

        if(d == 29 && e == 6) {
            easter = LocalDate.of(date.getYear(), 4, 19);
        }

        else if(d == 28 && e == 6) {
            easter = LocalDate.of(date.getYear(), 4, 18);
        }

        else {
            LocalDate base = LocalDate.of(date.getYear(), 3, 22);
            easter = base.plus(Period.ofDays(d + e + 1));
        }

        return easter;
    }

    public LocalDate bozeCialo(LocalDate easter){

        LocalDate bozeCialo = easter.plus(Period.ofDays(59));
        return bozeCialo;
    }

/*    public void getWorkingDays(int year){

        GregorianCalendar calendar = new GregorianCalendar();
        int maxDaysInCurrentMonth = 0;
        WorkingDays workingDays = new WorkingDays();

        for(int i=1; i<=12; i++){

            calendar.set(Calendar.MONTH, i);
            calendar.set(Calendar.YEAR, year);
            maxDaysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            int sum = 0;
            LocalDate easter = easterMonday(year);
            LocalDate bozeCialo = bozeCialo(easter);

            if(i == 1){
                for(int j=1; j<=maxDaysInCurrentMonth; j++){
                    if(calendar.get(Calendar.DAY_OF_WEEK) != 1 && j != 1 && j != 6){
                        sum++;
                    }
                }
                workingDays.setMonth(i);
                workingDays.setWorkingDay(sum);
            }
            else if(easter.getMonthValue() == i){
                for(int j=1; j<=maxDaysInCurrentMonth; j++){
                    if(calendar.get(Calendar.DAY_OF_WEEK) != 1 && j != easter.getDayOfMonth()){
                        sum++;
                    }
                }
                workingDays.setMonth(easter.getMonthValue());
                workingDays.setWorkingDay(sum);
            }
        }
    }*/

    public List<DaysList> daysList(int year, int month){

        List<Inspector> inspectorList = inspectorService.findAllInspectors();
        List<Contract> contractList = contractService.findAllContracts();
        List<DaysList> daysLists = new LinkedList<>();

        for(Inspector inspector: inspectorList){

            List<Contract> inspectorContractList = inspector.getContractList();
            int sum = 0;

            DaysList daysList = new DaysList();
            List<DayAmountInMonth> dayAmountInMonthList = new LinkedList<>();

            daysList.setFirstName(inspector.getFirstName());
            daysList.setLastName(inspector.getLastName());

            for(Contract c: contractList){

                int amountOfDays = 0;
                DayAmountInMonth dayAmountInMonth = new DayAmountInMonth();
                dayAmountInMonth.setContractName(c.getName());
                dayAmountInMonth.setDayAmountOnContract(amountOfDays);
                dayAmountInMonthRepository.save(dayAmountInMonth);

                for(Contract con: inspectorContractList){

                    if(c.equals(con)){

                        if(contractDetailService.findContractDetailsByInspectorIdAndContractId(inspector.getId(), c.getId()) == null){
                            dayAmountInMonth.setDayAmountOnContract(amountOfDays);
                            dayAmountInMonthRepository.save(dayAmountInMonth);
                        }
                        else {
                            ContractDetails contractDetails = contractDetailService.findContractDetailsByInspectorIdAndContractId(inspector.getId(), c.getId());
                            DaysAmount daysAmount = daysAmountService.findByMonthAndYearAndContractDetailsID(month, year, contractDetails.getId());

                            if (daysAmount == null) {
                                dayAmountInMonth.setDayAmountOnContract(amountOfDays);
                                dayAmountInMonthRepository.save(dayAmountInMonth);
                            } else {
                                amountOfDays = daysAmountService.findByMonthAndYearAndContractDetailsID(month, year, contractDetails.getId()).getAmountOfDaysInMonth();
                                dayAmountInMonth.setDayAmountOnContract(amountOfDays);
                                dayAmountInMonthRepository.save(dayAmountInMonth);
                                sum += amountOfDays;
                            }
                        }
                    }
                }
                dayAmountInMonthList.add(dayAmountInMonth);
            }
            daysList.setSum(sum);
            daysList.setMax(27);
            daysList.setDayAmountInMonth(dayAmountInMonthList);
            daysLists.add(daysList);
        }
        return daysLists;
    }
}
