package controllers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import play.GlobalSettings;
import play.Play;


public class Global extends GlobalSettings{
	
	public static void sendMail(String subject, String messageTo, String code){
		
		final String smtpHost = Play.application().configuration().getString("smtp.host");
		final String smtpPort = Play.application().configuration().getString( "smtp.port" );
		final String smtpUser = Play.application().configuration().getString( "smtp.user" );
		final String smtpPassword = Play.application().configuration().getString( "smtp.password" );
		
		
		
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpUser, smtpPassword);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUser));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(messageTo));
			message.setSubject(subject);
			message.setText("Votre code parrainage : " + code);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
//	public static void sendMail1(){
//		// Load SMTP configuration
//        String smtpHost = Play.application().configuration().getString("smtp.host");
//        Integer smtpPort = Play.application().configuration().getInt( "smtp.port" );
//        String smtpUser = Play.application().configuration().getString( "smtp.user" );
//        String smtpPassword = Play.application().configuration().getString( "smtp.password" );
//        
//        System.out.println("host " + smtpHost);
//        System.out.println("port " + smtpPort);
//        System.out.println("user " + smtpUser);
//        System.out.println("pass " + smtpPassword);
//        // Render template
//        //String body = email.render( created ).body();
//
//    // Prepare email
//    Email mail = new SimpleEmail();
//    try {
//        mail.setFrom("nhatminh1809@gmail.com");
//        mail.setSubject("Songuku");
//        mail.setMsg("This is the message from BEF");
//        mail.addTo("nhatminh1809@yahoo.com.vn");
//    } catch (EmailException e) {
//        e.printStackTrace();
//    }
//
//    System.out.println("ok");
//    
//    // Application de la configuration SMTP
//    mail.setHostName( smtpHost );
//    if ( smtpPort != null && smtpPort > 1 && smtpPort < 65536 ) {
//        mail.setSmtpPort( smtpPort );
//        System.out.println("check port ok");
//       // mail.setSSL(true);
//    }
//    if ( ! smtpUser.isEmpty() ) {
//        mail.setAuthentication( smtpUser, smtpPassword );
//    }
//System.out.println("iden ok");
//    // And finally
//    try {
//        mail.send();
//    } catch (EmailException e) {
//        e.printStackTrace();
//    }
		
		
//	}
	
	 public static String getTimeDiff(Date begin) {
		 DateTime start = new DateTime();
		 if(begin != null)
		 		start = new DateTime(begin.getTime());
		 DateTime end = new DateTime();
		 Duration duration =  new Duration(start, end);
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		 if(duration.getStandardDays() > 1){
			 return sdf.format(begin);
		 }else{
			 if(duration.getStandardDays() > 0){
				 SimpleDateFormat oneDay = new SimpleDateFormat("HH:mm:ss");
				 return "hier à " + oneDay.format(begin);
			 }else{
				 if(duration.getStandardHours() > 1)
					 return String.valueOf(duration.getStandardHours()) + " h avant";
				 else
				 return String.valueOf(duration.getStandardMinutes()) + " mins avant";
			  }
		 }
	}
	 
	
}
