package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.service.ContractService;
import com.openSource.attendanceListManager.service.InspectorService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
@SessionAttributes("loggedInspector")
@RequestMapping("/attendanceListManager")
public class LoginController {

    private final InspectorService inspectorService;
    private final ContractService contractService;
    private final PasswordEncoder passwordEncoder;
    
    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

    @GetMapping("/register")
    public String addInspectorView(Model model){
        model.addAttribute("inspector", new Inspector());
        return "register";
    }

    @PostMapping("/register")
    public String addInspector(@ModelAttribute("inspector") @Valid Inspector inspector, BindingResult result, @RequestParam(name = "repassword") String repassword){
        if(result.hasErrors()){
            return "register";
        }
        if(repassword.equals(inspector.getPassword())){
            inspector.setRole("inspector");
            inspector.setPassword(passwordEncoder.encode(inspector.getPassword()));
            inspectorService.addInspector(inspector);
            return "redirect:/attendanceListManager/login";
        }
        String password = repassword;
        return "register";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/attendanceListManager/login";
    }

    @GetMapping("/lostPassword")
    public String lostPassword(){
        return "lostPassword";
    }

    @ModelAttribute("contracts")
    public List<Contract> contractList(){
        return contractService.findAllContracts();
    }
}
