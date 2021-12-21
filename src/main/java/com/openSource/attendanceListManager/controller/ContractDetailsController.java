package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.service.ContractDetailService;
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
@RequestMapping("/contractDetails")
public class ContractDetailsController {

    private final ContractDetailService contractDetailsService;
    private final DaysAmountService daysAmountService;
    private final DaysService daysService;

    @GetMapping("/editContractDetails")
    public String editContractDetailsView(@RequestParam(name = "contractId") Long contractDetailsId, Model model){

        ContractDetails contractDetails = contractDetailsService.findContractDetailsById(contractDetailsId);
        model.addAttribute("contractDetails", contractDetails);

        return "addContractDetails";
    }

    @PostMapping("/editContractDetails")
    public String editContractDetails(@ModelAttribute("contractDetails") @Valid ContractDetails contractDetails,
                                      BindingResult result, HttpSession session){
        if(result.hasErrors()){
            return "addContractDetails";
        }

        List<DaysAmount> daysAmountList = daysAmountService.daysAmountList(contractDetails.getId());
        for(DaysAmount daysAmount : daysAmountList){
            List<Days> daysList = daysService.findAllDaysByDaysAmountId(daysAmount.getId());
            daysAmount.setAttendanceList(daysList);
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        contractDetails.setListDaysAmount(daysAmountList);
        contractDetailsService.addContractDetails(contractDetails);

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

    @GetMapping("/addContractDetails")
    public String addContractDetailsView(Model model, @RequestParam(name = "contractId") Long contractId,
                                         @RequestParam(name = "insp") Long inspectorId, HttpSession session){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");

        if(contractDetailsService.findContractDetailsByInspectorIdAndContractId(inspectorId, contractId) != null){
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
            model.addAttribute("inspectorId", inspectorId);
            model.addAttribute("contractId", contractId);
            model.addAttribute("contractDetails", new ContractDetails());
        }
        return "addContractDetails";
    }

    @PostMapping("/addContractDetails")
    public String addContractDetails(@ModelAttribute("contractDetails") @Valid ContractDetails contractDetails, BindingResult result,
                                     @RequestParam(name = "contractId") Long contractId, HttpSession session){
        if(result.hasErrors()){
            return "addContractDetails";
        }

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        contractDetailsService.addContractDetails(contractDetails);
        contractDetailsService.insertContractId(contractId);

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

    @GetMapping("/deleteContractDetailsView/{id}")
    public String deleteContractDetailsView(@PathVariable("id") Long id, Model model, HttpSession session){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        String role = "SuperAdmin";
        model.addAttribute("contractDetailsId", id);
        model.addAttribute("inspector", inspector);
        model.addAttribute("role", role);
        return "deleteContractDetails";
    }

    @GetMapping("/deleteContractDetails/{id}")
    public String deleteContractDetails(@PathVariable("id") Long id, HttpSession session){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        contractDetailsService.deleteContractDetails(id);

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
