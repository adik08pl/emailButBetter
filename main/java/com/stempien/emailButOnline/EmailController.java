package com.stempien.emailButOnline;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
public class EmailController {
    @GetMapping("email")
    @ResponseBody
    public String hello(@RequestParam String email, @RequestParam String mesage) {
            String to = email;
            String from = "paprykabrokul@buziaczek.pl";
            String password = "1232etretertDFEWVCEWFWERFWEF!";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", "smtp.poczta.onet.pl");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(properties, new MyAuthenticator(from, password));
            // session.setDebug(true);

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("Mail wysłany ze strony Adriana");
                message.setText(mesage);
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        return "Wysłano maila";

    }
}
