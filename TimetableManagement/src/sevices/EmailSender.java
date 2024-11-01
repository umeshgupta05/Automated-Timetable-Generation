package sevices;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.example.com");
        Session session = Session.getInstance(props, null);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("saiumeshgupta@yahoo.com"));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
