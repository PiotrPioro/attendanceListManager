package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.entity.MonthsName;
import com.openSource.attendanceListManager.repository.MonthNameRepository;
import com.openSource.attendanceListManager.service.CalendarService;
import com.openSource.attendanceListManager.service.ContractService;
import com.openSource.attendanceListManager.service.InspectorService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("loggedInspector")
@RequestMapping("/inspector")
@AllArgsConstructor
public class InspectorController {

    private final InspectorService inspectorService;
    private final ContractService contractService;
    private final CalendarService calendarService;
    private final MonthNameRepository monthNameRepository;

    @GetMapping("/profile")
    public String InspectorProfileView(HttpSession session, Model model){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        Map<Contract, ContractDetails> contractDetailsMap = contractService.contractMap(inspector.getId());

        LocalDate date = LocalDate.now();
        MonthsName month = monthNameRepository.findMonthNameById(date.getMonthValue());

        model.addAttribute("monthList", calendarService.getCalendarList(calendarService.currentMonth(), date.getYear()));
        model.addAttribute("currentDay", date.getDayOfMonth());
        model.addAttribute("currentDate", date);
        model.addAttribute("year", date.getYear());
        model.addAttribute("monthValue", date.getMonthValue());
        model.addAttribute("month", month);
        model.addAttribute("contractMap", contractDetailsMap);
        model.addAttribute("inspector", inspector);

        if("contractAdmin".equals(inspector.getRole())){
            model.addAttribute("admin", "yes");
        }
        else{
            model.addAttribute("admin", null);
        }

        return "inspectorProfile";
    }

    @GetMapping("/checkRole")
    public String checkRole(HttpSession session, @AuthenticationPrincipal UserDetails user){

        String email = user.getUsername();
        Inspector inspector = inspectorService.findByEmail(email);
        session.setAttribute("loggedInspector", inspector);

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

    @GetMapping("/editInspector")
    public String editInspectorView(HttpSession session, Model model){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        model.addAttribute("inspector", inspector);
        return "editInspector";
    }

    @PostMapping("/editInspector")
    public String editInspector(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, HttpSession session, Model model){
        Inspector logInspector = (Inspector) session.getAttribute("loggedInspector");
        inspectorService.editInspector(firstName, lastName, logInspector.getEmail());
        Inspector updatedInspector = inspectorService.findByEmail(logInspector.getEmail());
        model.addAttribute("loggedInspector", updatedInspector);
        return "redirect:/inspector/checkRole";
    }

    @GetMapping("/editPassword")
    public String editPasswordView(HttpSession session, Model model){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        model.addAttribute("inspector", inspector);
        return "editPassword";
    }

    @PostMapping("/editPassword")
    public String editPassword(@RequestParam("password") String password, @RequestParam("repassword") String repassword, HttpSession session){
        Inspector logInspector = (Inspector) session.getAttribute("loggedInspector");
        if(password.equals(repassword)) {
            inspectorService.findByEmail(logInspector.getEmail());
            inspectorService.editPassword(password, logInspector.getEmail());
            return "redirect:/inspector/checkRole";
        }
        return "editPassword";
    }

    @GetMapping("/showInspector")
    public String showInspectorView(HttpSession session, Model model){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        model.addAttribute("inspector", inspector);
        return "showInspector";
    }

    @GetMapping("/deleteInspector")
    public String deleteInspectorView(HttpSession session, Model model){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        model.addAttribute("inspector", inspector);
        return "deleteInspector";
    }

    @GetMapping("/delete")
    public String deleteInspector(HttpSession session){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        inspectorService.deleteInspector(inspector.getEmail());
        return "redirect:/attendanceListManager/login";
    }

    @ModelAttribute("contracts")
    public List<Contract> contractList(){
        return contractService.findAllContracts();
    }
}
