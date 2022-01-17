package com.openSource.attendanceListManager.controller;

import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.entity.Token;
import com.openSource.attendanceListManager.service.EmailService;
import com.openSource.attendanceListManager.service.InspectorService;
import com.openSource.attendanceListManager.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/password")
public class PasswordController {

    private final EmailService emailService;
    private final InspectorService inspectorService;
    private final TokenService tokenService;

    @GetMapping("/resetPassword")
    public String resetPasswordView(){
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam(name = "email") String mail, HttpSession session){

        String token = UUID.randomUUID().toString();
        Inspector inspector = inspectorService.findByEmail(mail);
        tokenService.addToken(token, inspector);

        String subject = "Zmiana hasła";
        String text = "Twój kod to: " + token + " wejdź na http://localhost:8080/password/verifyToken";
        emailService.sendMessage(subject, text, mail);

        session.setAttribute("inspector", inspector);

        return "redirect:/password/verifyToken";
    }

    @GetMapping("/verifyToken")
    public String verifyTokenView(HttpSession session, Model model){
        Inspector inspector = (Inspector) session.getAttribute("inspector");
        model.addAttribute("inspector", inspector);
        return "verifyToken";
    }

    @PostMapping("/verifyToken")
    public String verifyToken(@RequestParam(name = "email") String mail,
                              @RequestParam(name = "token") String token, HttpSession session){

        Inspector inspector = inspectorService.findByEmail(mail);

        Token token2 = tokenService.findByName(token);
        LocalDateTime date = LocalDateTime.now();
        Set<Token> tokens = inspector.getTokens();

        for(Token token1 : tokens){
            if(token1.getDate().isAfter(date) && token1.getName().equals(token2.getName())){
                session.setAttribute("changeInspectorPassword", inspector);
                return "redirect:/password/newPassword";
            }
        }
        return "redirect:/attendanceListManager/login";
    }

    @GetMapping("/newPassword")
    public String newPassword(HttpSession session, Model model){
        Inspector inspector = (Inspector) session.getAttribute("changeInspectorPassword");
        model.addAttribute("inspector", inspector);
        return "editPassword";
    }

    @PostMapping("/newPassword")
    public String editPassword(@RequestParam("password") String password,
                               @RequestParam("repassword") String repassword, @RequestParam(name = "email") String mail){

        if(password.equals(repassword)) {
            inspectorService.editPassword(password, mail);
            return "redirect:/attendanceListManager/login";
        }

        return "redirect:/attendanceListManager/login";
    }
}
