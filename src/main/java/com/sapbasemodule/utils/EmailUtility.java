package com.sapbasemodule.utils;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailUtility {

	@Autowired
	Properties configProperties;
	
	// SEND EMAILS TO ADMIN
	
		private final String PROP_SMTP_HOST = "mail.smtp.host";

		private final String PROP_SMTP_PORT = "mail.smtp.port";

		private final String PROP_SMTP_AUTH = "mail.smtp.auth";

		private final String PROP_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

		private final String PROP_HOST = "smtp.gmail.com";

		private final String PROP_PORT = "587";

		private final String PROP_AUTH = "true";

		private final String PROP_STARTTLS_ENABLE = "true";

		/**
		 * Sends email to the toAddress specified, with message body containing the baseURL appended with activate user account page. This forms the activation link for the user's account
		 * 
		 * @param 		toAddress		The email address to which mail is to be sent
		 * @param 		baseURL			The base URl of the application
		 * @return		isEmailSent		true if mail is sent successfully.false if mail sending fails
		 */
		public boolean sendEmail(String toAddress, String message, String subject, String attachmentFilePath, 
				String fileName){

			final String PROP_USERNAME = configProperties.getProperty("email.username");

			final String PROP_PASSWORD = configProperties.getProperty("email.password");


			boolean isEmailSend = false;
			try{
				// sets SMTP server properties
				Properties properties = new Properties();
				properties.put(PROP_SMTP_HOST, PROP_HOST);
				properties.put(PROP_SMTP_PORT, PROP_PORT);
				properties.put(PROP_SMTP_AUTH, PROP_AUTH);
				properties.put(PROP_SMTP_STARTTLS_ENABLE, PROP_STARTTLS_ENABLE);

				// creates a new session with an authenticator
			
				Authenticator auth = new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PROP_USERNAME, PROP_PASSWORD);
					}
				};
				Session session = Session.getInstance(properties, auth);

				
				// creates a new e-mail message
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(PROP_USERNAME));
				String[] emailIds = toAddress.split(",");

				InternetAddress[] toAddresses = new InternetAddress[emailIds.length];
				for(int i=0; i<emailIds.length; i++) {
					toAddresses[i] = new InternetAddress(emailIds[i]);
				}

				//Set Admin Email ID's as BCC
				String adminEmailList = configProperties.getProperty("email.admin.list");
						//masterDataService.selectAdminEmailId(MasterDataValue.GROUP_ADMIN.toString());
						//configProperties.getProperty("email.admin.list");
				String[] adminEmailIds = adminEmailList.split(",");
				
				InternetAddress[] ccAddresses = new InternetAddress[adminEmailIds.length];
				//InternetAddress[] bccAddresses = new InternetAddress[adminEmailIds.length];
				for(int i=0; i<adminEmailIds.length; i++) {
//					bccAddresses[i] = new InternetAddress(adminEmailIds[i]);
					ccAddresses[i] = new InternetAddress(adminEmailIds[i]);
				}

				msg.setRecipients(Message.RecipientType.TO, toAddresses);
				msg.setRecipients(Message.RecipientType.CC, ccAddresses);
//				msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
				msg.setSubject(subject);
				msg.setSentDate(new Date());

				//Create a multipart message
				Multipart multipart = new MimeMultipart();

				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(message, "text/html");
				multipart.addBodyPart(messageBodyPart);	// Set text message part


				// Part two is attachment
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachmentFilePath);
				attachmentBodyPart.setDataHandler(new DataHandler(source));
				attachmentBodyPart.setFileName(fileName);
				System.out.println("attachmentFilePath : "+attachmentFilePath);
				multipart.addBodyPart(attachmentBodyPart);

				// Send the complete message parts
				msg.setContent(multipart);

				//		msg.setContent(message,"text/html");
				// sends the e-mail
				Transport.send(msg);
				isEmailSend = true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return isEmailSend;
		}
		
}
