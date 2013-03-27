package net.ped.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.ped.model.Customer;
import net.ped.model.Party;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {
	private static final Logger LOG = LoggerFactory.getLogger(Mail.class);

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
	
	public void sendMail(Customer customer, Party party, String urlTicket) throws MessagingException {


		try {
//			Properties prop;
//			prop = PropertyLoader.load("../java/net/utils/mail.properties");
//			final String mail = prop.getProperty("mailMyParty");
//			final String password = prop.getProperty("passwordMyParty");
//			final String object = prop.getProperty("object");
		
			final String mail = "projet.img3d@gmail.com";
			final String password = "blopblop";
			final String object = "Confirmation de réservation";
			final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
			final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

			final String content = "<html><body> " +
					"<h2>Confirmation de réservation</h2>" +
					customer.getFirstname() + " " + customer.getLastname() + ", <br/>" +
					"Votre réservation pour " + party.getTitle() + ", le " +
					dateFormat.format(party.getDateParty().getTime()) + " à " +
					timeFormat.format(party.getTimeParty().getTime()) +
					", d'un montant de " +
					party.getPrice() + " euros, a bien été prise en compte. <br/>" +
					"Veuillez présenter le ticket à l'entré du concert, " +
					"<a href=\" "+ urlTicket +" \">disponible ici</a>." +
					"<br/><br/>" +
					"MyParty.fr </body></html>";
			LOG.debug(party.getDateParty().getTime().toString());
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

		    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() { 
	                	return new PasswordAuthentication(mail,password);
                	}
	            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customer.getMail()));
            message.setSubject(object);
            message.setContent(content, "text/html");

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
