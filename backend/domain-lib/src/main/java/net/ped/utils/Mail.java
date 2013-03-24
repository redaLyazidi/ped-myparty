package net.ped.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	
	private static Mail instance;
	
	private Mail() {
		instance = this;
	}
	
	public static Mail getInstance() {
		if(instance == null) {
			instance = new Mail();
		}
		
		return instance;
	}
	
	public void sendMail(String dest) throws MessagingException {
		
		Properties prop;
		try {
			//prop = PropertyLoader.load("net/ped/utils/mail.properties");
			//final String mail = prop.getProperty("mailMyParty");
			//final String password = prop.getProperty("passwordMyParty");
			//final String object = prop.getProperty("object");

			final String mail = "myparty@hotmail.fr";
			final String password = "PASSword123";
			final String object = "Confirmation de réservation";
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.live.com");
			props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.port", "587");

		    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() { 
	                	return new PasswordAuthentication(mail,password);
                	}
	            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, 
                InternetAddress.parse(dest));
            message.setSubject(object);
            message.setContent("Yes, this is the dog", "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
        	e.printStackTrace();
		} /*catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		    
	} 
		

}
