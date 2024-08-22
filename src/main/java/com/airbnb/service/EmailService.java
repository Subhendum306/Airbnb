package com.airbnb.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api_key}")
    private String sendGridApiKey;

    public String sendEmail(String toEmail, String subject, String messageBody) {
        Email from = new Email("your_email@example.com"); // Replace with your SendGrid verified sender email
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", messageBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getStatusCode() == 202 ? "Email sent successfully" : "Failed to send email";
        } catch (IOException ex) {
            return "Error sending email: " + ex.getMessage();
        }
    }
}