package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.repository.MonthNameRepository;
import com.openSource.attendanceListManager.service.CalendarService;
import com.openSource.attendanceListManager.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@SessionAttributes("loggedInspector")
@RequestMapping("/superAdmin")
public class SuperAdminController {

    private final ContractService contractService;
    private final CalendarService calendarService;
    private final MonthNameRepository monthNameRepository;

    @GetMapping("/superAdminHome")
    public String superAdminHomeView(HttpSession session, Model model, HttpServletRequest request){

        if(request.getParameter("message") != null){
            String message = request.getParameter("message");
            model.addAttribute("message", message);
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");

        if("SuperAdmin".equals(inspector.getRole())){

            List<Contract> contractList = contractService.findAllContracts();
            Map<Contract, Map<Inspector, ContractDetails>> inspectorWithDetails = new LinkedHashMap<>();

            for(Contract c : contractList){
                inspectorWithDetails.put(c, contractService.inspectorAndDetailsMap(c.getId()));
            }

            LocalDate date = LocalDate.now();
            MonthsName month = monthNameRepository.findMonthNameById(date.getMonthValue());

            model.addAttribute("inspectorMap", inspectorWithDetails);
            model.addAttribute("inspector", inspector);
            model.addAttribute("monthList", calendarService.getCalendarList(calendarService.currentMonth(), date.getYear()));
            model.addAttribute("currentDay", date.getDayOfMonth());
            model.addAttribute("currentDate", date);
            model.addAttribute("year", date.getYear());
            model.addAttribute("monthValue", date.getMonthValue());
            model.addAttribute("month", month);

            return "superAdminHome";
        }
        else {
            return "redirect:/inspector/checkRole";
        }
    }

    @GetMapping("/inspectorList")
    public String inspectorList(Model model, @RequestParam(name = "monthValue") int monthValue,
                                @RequestParam(name = "year") int year, HttpSession session){

        List<Contract> contractList = contractService.findAllContracts();
        MonthsName month = monthNameRepository.findMonthNameById(monthValue);
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        List<DaysList> inspectorList = calendarService.daysList(year, monthValue);

        model.addAttribute("inspectorList", inspectorList);
        model.addAttribute("contracts", contractList);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("inspector", inspector);

        return "inspectorList";
    }
}
