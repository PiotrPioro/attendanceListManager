package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.repository.MonthNameRepository;
import com.openSource.attendanceListManager.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@SessionAttributes("loggedInspector")
@RequestMapping("/listAttendance")
public class ListAttendanceController {

    private final ContractDetailService contractDetailService;
    private final InspectorService inspectorService;
    private final CalendarService calendarService;
    private final ContractService contractService;
    private final DaysAmountService daysAmountService;
    private final DaysService daysService;
    private final MonthNameRepository monthNameRepository;
    private final EmailService emailService;

    @GetMapping("/view")
    public String listAttendanceView(@RequestParam(name = "insp", defaultValue = "0") Long inspectorId, @RequestParam(name = "conDet", defaultValue = "0") Long conDetId, Model model,
                                     @RequestParam(name = "con", defaultValue = "0") Long conId, @RequestParam(name = "dayAmountId", defaultValue = "0") int dayAmountId,
                                     @RequestParam(name = "monthValue", defaultValue = "0") int monthValue, @RequestParam(name = "year", defaultValue = "0") int year,
                                     HttpSession session, @RequestParam(value = "message", defaultValue = "") String message){
        if(inspectorId == 0){
            inspectorId = (Long) session.getAttribute("insp");
        }
        if(conDetId == 0){
            conDetId = (Long) session.getAttribute("conDet");
        }
        if(conId == 0){
            conId = (Long) session.getAttribute("con");
        }
        if(dayAmountId == 0){
            dayAmountId = (Integer) session.getAttribute("dayAmountId");
        }
        if(monthValue == 0){
            monthValue = (Integer) session.getAttribute("monthValue");
        }
        if(year == 0){
            year = (Integer) session.getAttribute("year");
        }
        if("".equals(message)){
            message = (String) session.getAttribute("message");
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        Inspector insp = inspectorService.findById(inspectorId);
        ContractDetails contractDetails = contractDetailService.findContractDetailsById(conDetId);
        Contract contract = contractService.findContractById(conId);
        DaysAmount daysAmount = daysAmountService.findDaysAmountById(dayAmountId);

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        Map<Contract, ContractDetails> contractDetailsMap = contractService.contractMap(inspectorId);

        model.addAttribute("dayAmountId", dayAmountId);
        model.addAttribute("daysAmount", daysAmount);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("currentDate", date);
        model.addAttribute("year", year);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month", month);
        model.addAttribute("month2", month2);
        model.addAttribute("inspector", inspector);
        model.addAttribute("insp", insp);
        model.addAttribute("contractDetails", contractDetails);
        model.addAttribute("contract", contract);
        model.addAttribute("contractDetailsMap", contractDetailsMap);
        model.addAttribute("message", message);

        return "listAttendance";
    }

    @GetMapping("/setDay")
    public String addDayToAttendanceList(@RequestParam(name = "monthDay") int monthDay, @RequestParam(name = "weekDay") int weekDay,
                                         @RequestParam(name = "dayAmountId") Integer dayAmountId, @RequestParam(name = "insp") Long inspectorId,
                                         @RequestParam(name = "year") int year, @RequestParam(name = "monthValue") int monthValue,
                                         @RequestParam(name = "contractId") Long contractId, HttpSession session,
                                         @RequestParam(name = "contractDetailsId") Long conDetId){

        DaysAmount daysAmount = daysAmountService.findDaysAmountById(dayAmountId);
        List<ContractDetails> contractDetailsList = contractDetailService.findContractDetailsByInspectorId(inspectorId);
        Contract contract = contractService.findContractById(contractId);
        Inspector inspector1 = inspectorService.findById(inspectorId);

        if(daysAmount.getAmountOfDaysInMonth() == daysAmount.getAttendanceList().size()){

            String message = "Inspektor " + inspector1.getFullName() + " na kontrakcie " + contract.getName() + " ma już uzupełnione wszystkie dniówki!";

            session.setAttribute("message", message);
            session.setAttribute("insp", inspectorId);
            session.setAttribute("conDet", conDetId);
            session.setAttribute("con", contractId);
            session.setAttribute("dayAmountId", dayAmountId);
            session.setAttribute("monthValue", monthValue);
            session.setAttribute("year", year);

            return "redirect:/listAttendance/view";

        }
        else {
            for(ContractDetails cd : contractDetailsList){
                for(DaysAmount da : cd.getListDaysAmount()){
                    for(Days d : da.getAttendanceList()){
                        if(d.getMonthDay() == monthDay && da.getMonthNumber() == monthValue && da.getYear() == year){

                            String monthName = monthNameRepository.findMonthNameById(monthValue).getNameVariation();
                            String message = "Inspektor " + inspector1.getFullName() + " w " + monthName + " " + monthDay + "-go był już wpisany!";

                            session.setAttribute("message", message);
                            session.setAttribute("insp", inspectorId);
                            session.setAttribute("conDet", conDetId);
                            session.setAttribute("con", contractId);
                            session.setAttribute("dayAmountId", dayAmountId);
                            session.setAttribute("monthValue", monthValue);
                            session.setAttribute("year", year);

                            return "redirect:/listAttendance/view";
                        }
                    }
                }
            }

        Integer i = 1;
        Days day = new Days();
        day.setMonthDay(monthDay);
        day.setWeekDay(weekDay);
        day.setColor(i);
        daysService.addDay(day);

        List<Days> daysList = daysAmount.getAttendanceList();
        daysList.add(day);
        daysAmount.setAttendanceList(daysList);
        daysAmountService.addDaysAmount(daysAmount);

        String addDay = String.valueOf(monthDay);
        String addMonth = String.valueOf(monthNameRepository.findMonthNameById(monthValue).getName());
        String addYear = String.valueOf(year);

        String to = inspector1.getEmail();
        String subject = "Temat";
        String text = "Dodano dzień do listy obecności inspektora " + inspector1.getFullName() + " na kontrakcie " + contract.getName() + ": " + addDay + " " + addMonth + " " + addYear;

        emailService.sendMessage(subject, text, to);

        String to2 = contract.getContractAdministrator().getEmail();

        emailService.sendMessage(subject, text, to2);

        session.setAttribute("message", text);
        session.setAttribute("insp", inspectorId);
        session.setAttribute("conDet", conDetId);
        session.setAttribute("con", contractId);
        session.setAttribute("dayAmountId", dayAmountId);
        session.setAttribute("monthValue", monthValue);
        session.setAttribute("year", year);

        return "redirect:/listAttendance/view";
    }
    }

    @GetMapping("/deleteDay")
    public String deleteDay(@RequestParam(name = "monthDay") int monthDay, @RequestParam(name = "insp") Long inspectorId,
                            @RequestParam(name = "dayAmountId") Integer dayAmountId,
                            @RequestParam(name = "year") int year, @RequestParam(name = "monthValue") int monthValue,
                            @RequestParam(name = "contractId") Long contractId, HttpSession session,
                            @RequestParam(name = "contractDetailsId") Long conDetId){

        ContractDetails contractDetails = contractDetailService.findContractDetailsByInspectorIdAndContractId(inspectorId, contractId);
        Contract contract = contractService.findContractById(contractId);
        Inspector inspector1 = inspectorService.findById(inspectorId);
        String monthName = monthNameRepository.findMonthNameById(monthValue).getName();


        for(DaysAmount da : contractDetails.getListDaysAmount()){
            for(Days d : da.getAttendanceList()){
                if(d.getMonthDay() == monthDay && da.getMonthNumber() == monthValue && da.getYear() == year){

                    daysService.deleteDay(d.getId());
                    String message = "Usunięto dzień z listy obecności inspektora " + inspector1.getFullName() + " na kontrakcie " + contract.getName() + ": " + monthDay + " " + monthName + " " + year;

                    session.setAttribute("message", message);
                    session.setAttribute("insp", inspectorId);
                    session.setAttribute("conDet", conDetId);
                    session.setAttribute("con", contractId);
                    session.setAttribute("dayAmountId", dayAmountId);
                    session.setAttribute("monthValue", monthValue);
                    session.setAttribute("year", year);

                    return "redirect:/listAttendance/view";
                }
            }
        }

        String message = "Dnia " + monthDay + " " + monthName + " " + year + " nie ma na liście obecności inspektora " + inspector1.getFullName() + " na kontrakcie " + contract.getName();

        session.setAttribute("message", message);
        session.setAttribute("insp", inspectorId);
        session.setAttribute("conDet", conDetId);
        session.setAttribute("con", contractId);
        session.setAttribute("dayAmountId", dayAmountId);
        session.setAttribute("monthValue", monthValue);
        session.setAttribute("year", year);

        return "redirect:/listAttendance/view";
    }
}
