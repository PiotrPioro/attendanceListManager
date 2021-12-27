package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.service.ContractDetailService;
import com.openSource.attendanceListManager.service.ContractService;
import com.openSource.attendanceListManager.service.InspectorService;
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
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;
    private final InspectorService inspectorService;
    private final ContractDetailService contractDetailService;

    @GetMapping("/addContract")
    public String addContractView(Model model){
        model.addAttribute("contract", new Contract());
        return "addContract";
    }

    @PostMapping("/addContract")
    public String addContract(@ModelAttribute("contract") @Valid Contract contract, BindingResult result){
        if(result.hasErrors()){
            return "addContract";
        }
        contractService.addContract(contract);
        return "redirect:/superAdmin/superAdminHome";
    }

    @GetMapping("/contractList")
    public String contractList(Model model){
        List<Contract> contractList = contractService.findAllContracts();
        model.addAttribute("contracts", contractList);
        return "contractList";
    }

    @GetMapping("/inspectorContractList")
    public String inspectorContractList(Model model, HttpSession session){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        List<Contract> contractList = contractService.findContractByContractAdministrator(inspector);
        model.addAttribute("contracts", contractList);
        return "contractList";
    }

    @GetMapping("/deleteContractView/{id}/{name}")
    public String deleteContractView(@PathVariable("id") Long id, @PathVariable("name") String name, Model model, HttpSession session){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        String role = "SuperAdmin";
        model.addAttribute("inspector", inspector);
        model.addAttribute("role", role);
        model.addAttribute("contractId", id);
        model.addAttribute("contractName",name);
        return "deleteContract";
    }

    @GetMapping("/deleteContract/{id}")
    public String deleteContract(@PathVariable("id") Long id, HttpSession session){
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        contractService.deleteContract(id);
        if("SuperAdmin".equals(inspector.getRole())){
            return "redirect:/contract/contractList";
        }
        else {
            return "redirect:/contract/inspectorContractList";
        }
    }

    @GetMapping("/editContract")
    public String editContractView(@RequestParam(name = "id") Long contractId, Model model){
        Contract contract = contractService.findContractById(contractId);
        List<ContractDetails> contractDetailsList = contract.getContractDetails();
        model.addAttribute("contract", contract);
        model.addAttribute("contractDetailsList", contractDetailsList);
         return "editContract";
    }

    @PostMapping("/editContract")
    public String editContract(@ModelAttribute("contract") @Valid Contract contract, BindingResult result,
                               HttpSession session){
        if(result.hasErrors()){
            return "editContract";
        }
        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        contractService.addContract(contract);

        if("SuperAdmin".equals(inspector.getRole())){
            return "redirect:/contract/contractList";
        }
        else {
            return "redirect:/contract/inspectorContractList";
        }
    }

    @GetMapping("/addInstructor")
    public String addInstructorView(@RequestParam("id") Long contractId, Model model){

        Contract contract = contractService.findContractById(contractId);
        model.addAttribute("contract", contract);
        return "addInstructor";
    }

    @PostMapping("/addInstructor")
    public String addInstructor(@ModelAttribute("contract") @Valid Contract contract, HttpSession session){

        Inspector inspector = (Inspector) session.getAttribute("loggedInspector");
        contract.setInspectorList(contract.getInspectorList());
        contractService.addContract(contract);

        if("SuperAdmin".equals(inspector.getRole())){
            return "redirect:/contract/contractList";
        }
        else {
            return "redirect:/contract/inspectorContractList";
        }
    }

    @ModelAttribute("inspectors")
    public List<Inspector> inspectorList(){
        return inspectorService.findAllInspectors();
    }
}
