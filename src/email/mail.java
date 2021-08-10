package email;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class mail implements Runnable{
    String user_email;
    String user_name;
    String user_vaccinated;
    String user_remarks;
    Thread t;

    public mail(String email, String user_name, String user_vaccinated, String user_remarks){
        this.user_email = email;
        this.user_name = user_name;
        this.user_vaccinated = user_vaccinated;
        this.user_remarks = user_remarks;
        t = new Thread(this, "Email Thread");
        t.start();
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        final String username = "sophists.bot@gmail.com";
        final String password = "Sophists@123";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user_email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user_email));
            message.setSubject("Report generated on: " + dtf.format(now));
            message.setText("Dear "+user_name+",\n"+"Your Report as follows\n" + "Vaccinated: "+user_vaccinated+"\nRemarks: "+ user_remarks);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}