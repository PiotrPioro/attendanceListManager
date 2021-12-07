package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.service.CalendarService;
import com.openSource.attendanceListManager.service.ContractDetailService;
import com.openSource.attendanceListManager.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@SessionAttributes("loggedInspector")
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final ContractDetailService contractDetailService;
    private final ContractService contractService;

    @GetMapping("/minusMonth")
    public String minusMonth(Model model, @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year, HttpSession session){
        if(monthValue == 1){
            monthValue = 12;
            year -= 1;
        }
        else {
            monthValue -= 1;
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        List<Contract> contractList = contractService.findContractByContractAdministrator(inspector);
        Map<Contract, Map<Inspector, ContractDetails>> inspectorWithDetails = new HashMap<>();

        for(Contract c : contractList){
            inspectorWithDetails.put(c, contractService.inspectorAndDetailsMap(c.getId()));
        }

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("inspectorMap", inspectorWithDetails);
        model.addAttribute("inspector", inspector);
        model.addAttribute("currentDate", date);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("month", month);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month2", month2);
        model.addAttribute("year", year);
        return "changeMonth";
    }

    @GetMapping("/plusMonth")
    public String plusMonth(Model model, @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year, HttpSession session){
        if(monthValue == 12){
            monthValue = 1;
            year += 1;
        }
        else {
            monthValue += 1;
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        List<Contract> contractList = contractService.findContractByContractAdministrator(inspector);
        Map<Contract, Map<Inspector, ContractDetails>> inspectorWithDetails = new HashMap<>();

        for(Contract c : contractList){
            inspectorWithDetails.put(c, contractService.inspectorAndDetailsMap(c.getId()));
        }

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("inspectorMap", inspectorWithDetails);
        model.addAttribute("inspector", inspector);
        model.addAttribute("currentDate", date);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("month", month);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month2", month2);
        model.addAttribute("year", year);
        return "changeMonth";
    }

    @GetMapping("/minusInspectorMonth")
    public String minusInspectorMonth(Model model, @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year, HttpSession session){
        if(monthValue == 1){
            monthValue = 12;
            year -= 1;
        }
        else {
            monthValue -= 1;
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        Map<Contract, ContractDetails> contractDetailsMap = contractService.contractMap(inspector.getId());

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("contractMap", contractDetailsMap);
        model.addAttribute("inspector", inspector);
        model.addAttribute("currentDate", date);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("month", month);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month2", month2);
        model.addAttribute("year", year);
        return "changeInspectorMonth";
    }

    @GetMapping("/plusInspectorMonth")
    public String plusInspectorMonth(Model model, @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year, HttpSession session){
        if(monthValue == 12){
            monthValue = 1;
            year += 1;
        }
        else {
            monthValue += 1;
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        Map<Contract, ContractDetails> contractDetailsMap = contractService.contractMap(inspector.getId());

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("contractMap", contractDetailsMap);
        model.addAttribute("inspector", inspector);
        model.addAttribute("currentDate", date);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("month", month);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month2", month2);
        model.addAttribute("year", year);
        return "changeInspectorMonth";
    }

    @GetMapping("/minusAdminMonth")
    public String minusAdminMonth(Model model, @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year, HttpSession session){
        if(monthValue == 1){
            monthValue = 12;
            year -= 1;
        }
        else {
            monthValue -= 1;
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");

        List<Contract> contractList = contractService.findAllContracts();
        Map<Contract, Map<Inspector, ContractDetails>> inspectorWithDetails = new HashMap<>();

        for(Contract c : contractList){
            inspectorWithDetails.put(c, contractService.inspectorAndDetailsMap(c.getId()));
        }

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("inspectorMap", inspectorWithDetails);
        model.addAttribute("inspector", inspector);
        model.addAttribute("currentDate", date);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("month", month);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month2", month2);
        model.addAttribute("year", year);
        return "changeAdminMonth";
    }

    @GetMapping("/plusAdminMonth")
    public String plusAdminMonth(Model model, @RequestParam(name = "monthValue") int monthValue, @RequestParam(name = "year") int year, HttpSession session){
        if(monthValue == 12){
            monthValue = 1;
            year += 1;
        }
        else {
            monthValue += 1;
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");

        List<Contract> contractList = contractService.findAllContracts();
        Map<Contract, Map<Inspector, ContractDetails>> inspectorWithDetails = new HashMap<>();

        for(Contract c : contractList){
            inspectorWithDetails.put(c, contractService.inspectorAndDetailsMap(c.getId()));
        }

        LocalDate date = LocalDate.now();
        String month = calendarService.nameOfMonth(monthValue);
        String month2 = calendarService.monthName(monthValue);

        model.addAttribute("inspectorMap", inspectorWithDetails);
        model.addAttribute("inspector", inspector);
        model.addAttribute("currentDate", date);
        model.addAttribute("monthList", calendarService.getCalendarList(monthValue - 1, year));
        model.addAttribute("month", month);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month2", month2);
        model.addAttribute("year", year);
        return "changeAdminMonth";
    }
}
