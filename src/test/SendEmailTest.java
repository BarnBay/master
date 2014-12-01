package test;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * SendEmailTest Class
 * 
 * @author Anne
 *
 */
public class SendEmailTest {

	/**
	 * Main method for distinct testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SendEmailTest emailer = new SendEmailTest();
		String to = "anne-lauscher@web.de";
		String url = "";

		emailer.sendEmail(to, url);
	}

	/**
	 * Send a single email as order notification to the farmer
	 * 
	 * @param to, String with the recipient email address
	 * @param url, String for creating link to our app in the email content
	 * 
	 */
	public void sendEmail(String to, String url) {
		Session mailSession = createSmtpSession();
		mailSession.setDebug(true);

		try {
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("New Orders@Barnbay");
			message.setFrom(new InternetAddress(
					"barnbay.organic.food@gmail.com"));
			String content = "<h1 style='color:green'>You have received new orders for tomorrow!</h1><p>Please visit your Dashboard on Barnbay:</p><p><a href="
					+ url
					+ ">"
					+ url
					+ "</a></p><p>Best regards</p><p>Your Barnbay-Team!</p>";
			message.setContent(content, "text/html");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			transport.connect();
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
		} catch (MessagingException e) {
			System.err.println("Cannot Send email");
			e.printStackTrace();
		}
	}

	/**
	 * Method which creates an smtpSession
	 * @return PasswordAuthentication
	 */
	private Session createSmtpSession() {
		final Properties props = new Properties();
		props.setProperty("mail.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.port", "" + 587);
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.transport.protocol", "smtp");

		return Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						"barnbay.organic.food@gmail.com", "Apdtld14");
			}
		});
	}

}