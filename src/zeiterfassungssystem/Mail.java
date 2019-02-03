package zeiterfassungssystem;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Mail {

    public void sendMail(String empfaengermail, String betreff, String nachricht) throws UnsupportedEncodingException, MessagingException {
        Datenbank ini = new Datenbank();
        String[] daten = ini.readIni();
        //Absender-Mail
        final String FROM = daten[10];
        //Absender-Name
        final String FROMNAME = daten[9];
        //User
        final String SMTP_USERNAME = daten[6];
        //Passwort
        final String SMTP_PASSWORD = daten[7];
        //SMTP-Server
        final String HOST = daten[5];
        //Port
        final int PORT = Integer.parseInt(daten[8]);
        //Empfaenger
        final String TO = empfaengermail;
        //Betreff
        final String SUBJECT = betreff;
        //Nachricht
        final String BODY = nachricht;
        //Mail verschicken
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY, "text/html");
        Transport transport = session.getTransport();
        try {
            System.out.println("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        } finally {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
