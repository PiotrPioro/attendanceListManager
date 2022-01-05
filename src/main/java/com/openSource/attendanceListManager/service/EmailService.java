package com.openSource.attendanceListManager.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Transactional
    public void sendMessage(String subject, String text, String to){

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreplaytest777@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    javaMailSender.send(message);
    }
}
