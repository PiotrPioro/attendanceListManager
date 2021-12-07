package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.entity.Role;
import com.openSource.attendanceListManager.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
@AllArgsConstructor
@SessionAttributes("loggedInspector")
@RequestMapping("/contractAdmin")
public class ContractAdminController {

    private final InspectorService inspectorService;
    private final CalendarService calendarService;
    private final ContractService contractService;

    @GetMapping("/contractAdminHome")
    public String contractAdminHomeView(HttpSession session, Model model){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");

        List<Contract> contractList = contractService.findContractByContractAdministrator(inspector);
        Map<Contract, Map<Inspector, ContractDetails>> inspectorWithDetails = new HashMap<>();

        for(Contract c : contractList){
            inspectorWithDetails.put(c, contractService.inspectorAndDetailsMap(c.getId()));
        }

        LocalDate date = LocalDate.now();
        //nazwy miesięcy
        String month = calendarService.nameOfMonth(date.getMonthValue());
        String month2 = calendarService.monthName(date.getMonthValue());

        model.addAttribute("inspectorMap", inspectorWithDetails);
        model.addAttribute("inspector", inspector);
        model.addAttribute("monthList", calendarService.getCalendarList(calendarService.currentMonth(), date.getYear()));
        model.addAttribute("currentDay", date.getDayOfMonth());
        model.addAttribute("currentDate", date);
        model.addAttribute("year", date.getYear());
        model.addAttribute("monthValue", date.getMonthValue());
        model.addAttribute("month", month);
        model.addAttribute("month2", month2);
        return "contractAdminHome";
    }

    @GetMapping("/addRoleView")
    public String addRoleView(Model model){
        List<Inspector> inspectorList = inspectorService.findAllInspectors();
        model.addAttribute("inspectors", inspectorList);
        return "addRoleView";
    }

    @GetMapping("/addRole")
    public String addRoleView(@RequestParam(name = "id") Long inspectorId, Model model){
        Inspector inspector = inspectorService.findById(inspectorId);
        model.addAttribute("inspector", inspector);
        return "addRole";
    }

    @PostMapping("/addRole")
    public String addRole(@RequestParam(name = "role") String role, @RequestParam(name = "inspectorId") Long inspectorId){
        Inspector inspector = inspectorService.findById(inspectorId);
        inspector.setRole(role);
        inspectorService.addInspector(inspector);
        return "redirect:/inspector/checkRole";
    }
}
