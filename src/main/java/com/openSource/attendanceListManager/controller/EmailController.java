package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.DaysAmount;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.repository.MonthNameRepository;
import com.openSource.attendanceListManager.service.ContractService;
import com.openSource.attendanceListManager.service.DaysAmountService;
import com.openSource.attendanceListManager.service.EmailService;
import com.openSource.attendanceListManager.service.InspectorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/message")
public class EmailController {

    private final EmailService emailService;
    private final MonthNameRepository monthNameRepository;
    private final ContractService contractService;
    private final InspectorService inspectorService;
    private final DaysAmountService daysAmountService;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam(name = "dayAmountId") Integer dayAmountId,
                              @RequestParam(name = "con") Long contractId, @RequestParam(name = "insp") Long inspectorId,
                              HttpSession session, @RequestParam(name = "year") int year,
                              @RequestParam(name = "monthValue") int monthValue){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        Contract contract = contractService.findContractById(contractId);
        Inspector inspector1 = inspectorService.findById(inspectorId);
        DaysAmount daysAmount = daysAmountService.findDaysAmountById(dayAmountId);

        String monthName = monthNameRepository.findMonthNameById(monthValue).getNameVariation();
        String subject = "Dodano ilość dniówek w " + monthName;
        String text = "Na kontrakcie " + contract.getName() + " w " + monthName + " " + year + " masz " + daysAmount.getAmountOfDaysInMonth() + " dniówek.";
        String to = inspector1.getEmail();
        emailService.sendMessage(subject, text, to);

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
