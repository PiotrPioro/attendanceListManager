package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Days;
import com.openSource.attendanceListManager.entity.DaysAmount;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.service.DaysAmountService;
import com.openSource.attendanceListManager.service.DaysService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/daysAmount")
public class DaysAmountController {

    private final DaysAmountService daysAmountService;
    private final DaysService daysService;

    @GetMapping("/editDaysAmount")
    public String editDaysAmountView(@RequestParam(name = "dayAmountId") Integer dayAmountId, Model model,
                                          @RequestParam(name = "month") int monthValue, @RequestParam(name = "year") int year,
                                          @RequestParam(name = "contractDetailsId") Long contractDetailsId){

        DaysAmount dayAmount = daysAmountService.findDaysAmountById(dayAmountId);
        model.addAttribute("dayList", dayAmount.getAttendanceList());
        model.addAttribute("daysAmount", dayAmount);
        model.addAttribute("contractDetailsId", contractDetailsId);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("year", year);

        return "addDaysAmount";
    }

    @PostMapping("/editDaysAmount")
    public String editDaysAmount(@ModelAttribute("daysAmount") @Valid DaysAmount daysAmount, BindingResult result,
                                      @RequestParam(name = "contractDetailsId") Long contractDetailsId,
                                        HttpSession session){
        if(result.hasErrors()){
            return "addDaysAmount";
        }
        List<Days> daysList = daysService.findAllDaysByDaysAmountId(daysAmount.getId());
        if(daysList.size() <= daysAmount.getAmountOfDaysInMonth()){
            daysAmount.setAttendanceList(daysList);
        }
        else {
            daysAmount.setAttendanceList(null);
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        daysAmountService.addDaysAmount(daysAmount);

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

    @GetMapping("/addDaysAmount")
    public String addDaysAmountView(Model model, @RequestParam(name = "month") int monthValue, @RequestParam(name = "year") int year,
                                    @RequestParam(name = "contractDetailsId") Long contractDetailsId, HttpSession session){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        List<DaysAmount> daysAmountList = daysAmountService.daysAmountList(contractDetailsId);

        for(DaysAmount da : daysAmountList){
            if(da.getMonthNumber() == monthValue && da.getYear() == year){
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

        model.addAttribute("contractDetailsId", contractDetailsId);
        model.addAttribute("monthValue", monthValue);
        model.addAttribute("year", year);
        model.addAttribute("daysAmount", new DaysAmount());

        return "addDaysAmount";
    }

    @PostMapping("/addDaysAmount")
    public String addDaysAmount(@ModelAttribute("daysAmount") @Valid DaysAmount daysAmount,
                                BindingResult result, @RequestParam(name = "contractDetailsId") Long contractDetailsId,
                                HttpSession session){
        if(result.hasErrors()){
            return "addDaysAmount";
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        daysAmountService.addDaysAmount(daysAmount);
        daysAmountService.insertContractDetailsId(contractDetailsId);

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

    @GetMapping("/deleteDaysAmountView")
    public String deleteDaysAmountView(@RequestParam(name = "dayAmountId") Integer dayAmountId, Model model,
                                            @RequestParam(name = "month2") String month2, HttpSession session){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        String role = "SuperAdmin";
        model.addAttribute("inspector", inspector);
        model.addAttribute("role", role);
        model.addAttribute("dayAmountId", dayAmountId);
        model.addAttribute("month2", month2);

        return "deleteDaysAmount";
    }

    @GetMapping("/deleteDaysAmount")
    public String deleteDaysAmount(@RequestParam(name = "dayAmountId") Integer dayAmountId, HttpSession session){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        daysAmountService.deleteDaysAmount(dayAmountId);

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
