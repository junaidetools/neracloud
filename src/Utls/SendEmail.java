package Utls;

import java.io.IOException;



import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;


public class SendEmail {

	public static void main(String[] args) throws EmailException, IOException {
		
		
		
		  System.out.println("===================Email Sending Started============================" ); // Create the attachment 
		  EmailAttachment attachment = new  EmailAttachment(); 
		  String ReportPath =ExcelDriven_XLSX.readExcelData("Testdata", "Config","ReportPath").get("Value").toString(); 
		  attachment.setPath(ReportPath);
		  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		  attachment.setDescription("NeRA Cloud - Automation Testing Report"); String
		  ReportBy = ExcelDriven_XLSX.readExcelData("Testdata", "Config",
		  "ReportBy").get("Value").toString(); attachment.setName(ReportBy);
		  
		  // Create the email message 
		  MultiPartEmail email = new MultiPartEmail();
		  email.setHostName("smtp.office365.com");
		  email.addTo("amushtaq@e-tools.com.au", "Ana Mushtaq");//"mzohaib@ucm.com.au", "Muhammad Zohaib", 
		  email.setFrom("jtariq@ucm.com.au","xGG78o#51VvT"); 
		  email.setSubject("NeRA Cloud - Automation Testing Report");
		  email.setMsg("Hi, Here is the test automation report on NeRA Cloud. Please see attachment for detailed report on automation execution.");
		  
		  // add the attachment 
		  email.attach(attachment);
		  
		  // send the email 
		  email.send();
		  
		  System.out.println("===================Email Sent============================");
		 
	
		
/*		  System.out.println("=================Email Sending Started===================================");
		  Email email = new SimpleEmail();
		  email.setHostName("smtp.gmail.com");
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("junaidtariq86@gmail.com", "xxxxxxxxx"));
		  email.setSSLOnConnect(true);
		  email.setFrom("junaidtariq86@gmail.com");
		  email.setSubject("TestMail");
		  email.setMsg("This is a test mail ... :-)");
		  email.addTo("junaidtariq85@yahoo.com");
		  email.send();
		  System.out.println("=================Email Sent===================================");*/
	}

}
