package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.service.ContractDetailService;
import com.openSource.attendanceListManager.service.DaysAmountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/contractDetails")
public class ContractDetailsController {

    private final ContractDetailService contractDetailsService;
    private final DaysAmountService daysAmountService;

    @GetMapping("/editContractDetails")
    public String editContractDetailsView(@RequestParam(name = "contractId") Long contractDetailsId, Model model){

        ContractDetails contractDetails = contractDetailsService.findContractDetailsById(contractDetailsId);
        model.addAttribute("contractDetails", contractDetails);

        return "addContractDetails";
    }

    @PostMapping("/editContractDetails")
    public String editContractDetails(@ModelAttribute("contractDetails") @Valid ContractDetails contractDetails,
                                      BindingResult result){
        if(result.hasErrors()){
            return "addContractDetails";
        }

        daysAmountService.editDaysAmount(contractDetails);
        contractDetailsService.addContractDetails(contractDetails);

        return "redirect:/inspector/checkRole";
    }

    @GetMapping("/addContractDetails")
    public String addContractDetailsView(Model model, @RequestParam(name = "contractId") Long contractId,
                                         @RequestParam(name = "insp") Long inspectorId){

        if(contractDetailsService.findContractDetailsByInspectorIdAndContractId(inspectorId, contractId) != null){
            return "redirect:/inspector/checkRole";
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
                                     @RequestParam(name = "contractId") Long contractId){
        if(result.hasErrors()){
            return "addContractDetails";
        }

        contractDetailsService.addContractDetails(contractDetails);
        contractDetailsService.insertContractId(contractId);

        return "redirect:/inspector/checkRole";

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
    public String deleteContractDetails(@PathVariable("id") Long id){

        contractDetailsService.deleteContractDetails(id);

        return "redirect:/inspector/checkRole";
    }
}
