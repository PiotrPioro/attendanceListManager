package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/view")
    public String listAttendanceView(@RequestParam(name = "insp") Long inspectorId, @RequestParam(name = "conDet") Long conDetId, Model model,
                                     @RequestParam(name = "con") Long conId, @RequestParam(name = "dayAmountId") int dayAmountId,
                                     @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year){

        Inspector insp = inspectorService.findById(inspectorId);
        ContractDetails contractDetails = contractDetailService.findContractDetailsById(conDetId);
        Contract contract = contractService.findContractById(conId);
        DaysAmount daysAmount = daysAmountService.findDaysAmountById(dayAmountId);

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("dayAmountId", dayAmountId);
        model.addAttribute("daysAmount", daysAmount);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue, year));
        model.addAttribute("currentDate", date);
        model.addAttribute("year", year);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month", month);
        model.addAttribute("month2", month2);
        model.addAttribute("insp", insp);
        model.addAttribute("contractDetails", contractDetails);
        model.addAttribute("contract", contract);

        return "listAttendance";
    }

    @GetMapping("/setDay")
    public String addDayToAttendanceList(@RequestParam(name = "monthDay") int monthDay, HttpSession session, @RequestParam(name = "weekDay") int weekDay,
                                         @RequestParam(name = "dayAmountId") Integer dayAmountId, Model model, @RequestParam(name = "contractId") Long contractId,
                                         @RequestParam(name = "insp") Long inspectorId, @RequestParam(name = "contractDetailsId") Long contractDetailsId,
                                         @RequestParam(name = "year") int year, @RequestParam(name = "monthValue") int monthValue){

        DaysAmount daysAmount = daysAmountService.findDaysAmountById(dayAmountId);
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");

        List<ContractDetails> contractDetailsList = contractDetailService.findContractDetailsByInspectorId(inspectorId);
        String message = "";

        if(daysAmount.getAmountOfDaysInMonth() == daysAmount.getAttendanceList().size()){

            model.addAttribute("monthDay", monthDay);
            model.addAttribute("inspector", inspector);
            model.addAttribute("weekDay", weekDay);
            model.addAttribute("dayAmountId", dayAmountId);
            model.addAttribute("con", contractId);
            model.addAttribute("insp", inspectorId);
            model.addAttribute("conDet", contractDetailsId);
            model.addAttribute("year", year);
            model.addAttribute("month", monthValue);
            model.addAttribute("message", message);

            if("SuperAdmin".equals(inspector.getRole())){
                return "redirect:/superAdmin/superAdminHome";
            }
            else if("contractAdmin".equals(inspector.getRole())){
                return "redirect:/contractAdmin/contractAdminHome";
            }
            else{
                return "redirect:/inspector/profile";
            }
        }
        else {
            for(ContractDetails cd : contractDetailsList){
                for(DaysAmount da : cd.getListDaysAmount()){
                    for(Days d : da.getAttendanceList()){
                        if(d.getMonthDay() == monthDay && da.getMonthNumber() == monthValue && da.getYear() == year){
                            message = "W tym dniu inspektor już został wpisany";

                            model.addAttribute("monthDay", monthDay);
                            model.addAttribute("inspector", inspector);
                            model.addAttribute("weekDay", weekDay);
                            model.addAttribute("dayAmountId", dayAmountId);
                            model.addAttribute("con", contractId);
                            model.addAttribute("insp", inspectorId);
                            model.addAttribute("conDet", contractDetailsId);
                            model.addAttribute("year", year);
                            model.addAttribute("month", monthValue);
                            model.addAttribute("message", message);

                            if("SuperAdmin".equals(inspector.getRole())){
                                return "redirect:/superAdmin/superAdminHome";
                            }
                            else if("contractAdmin".equals(inspector.getRole())){
                                return "redirect:/contractAdmin/contractAdminHome";
                            }
                            else{
                                return "redirect:/inspector/profile";
                            }
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

        model.addAttribute("insp", inspectorId);
        model.addAttribute("conDet", contractDetailsId);
        model.addAttribute("con", contractId);
        model.addAttribute("dayAmountId", dayAmountId);
        model.addAttribute("message", message);
        model.addAttribute("monthDay", monthDay);
        model.addAttribute("inspector", inspector);

        if("SuperAdmin".equals(inspector.getRole())){
            return "redirect:/superAdmin/superAdminHome";
        }
        else if("contractAdmin".equals(inspector.getRole())){
            return "redirect:/contractAdmin/contractAdminHome";
        }
        else{
            return "redirect:/inspector/profile";
        }
    }
    }
}
