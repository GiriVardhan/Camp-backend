package com.jbent.peoplecentral.email;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.exception.DataException;

@SuppressWarnings("unchecked")
@Controller
public class CustomEmail{
	
	private static MailSender mailSender;
	
	static final Logger logger = Logger.getLogger(CustomEmail.class);
	
	@SuppressWarnings("static-access")
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@RequestMapping(value = {"/entity/email/link","/*/entity/email/link"}, method = RequestMethod.POST)
	public View emailEntity(HttpServletRequest request,Model model,@RequestParam("to") String to,
			@RequestParam("sub") String sub,@RequestParam("message") String message) throws DataException, IOException{
		SimpleMailMessage mail = new SimpleMailMessage();		
		String[] sendAdd = to.split(",");
		mail.setTo(sendAdd);
		mail.setSubject(sub);
		mail.setText(message);
		try{
			mailSender.send(mail);	
		}catch(Exception e){
			logger.error("Error in CustomEmail :"+e.getMessage());
			e.printStackTrace();
		}		
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/entity/share/entities","/*/entity/share/entities"}, method = RequestMethod.POST)
	public View shareEntities(HttpServletRequest request,Model model,@RequestParam("to") String to,
			@RequestParam("sub") String sub,@RequestParam("shareEntities") String shareEntities) throws DataException, IOException{
		SimpleMailMessage mail = new SimpleMailMessage();	
		String[] sendAdd = to.split(",");
		mail.setTo(sendAdd);
		mail.setSubject(sub);
		mail.setText(shareEntities);
		try{
			mailSender.send(mail);	
		}catch(Exception e){
			logger.error("Error in CustomEmail :"+e.getMessage());
			e.printStackTrace();
		}		
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/entity/bulk/email","/*/entity/bulk/email"}, method = RequestMethod.POST)
	public View bulkEmails(HttpServletRequest request,Model model,@RequestParam("to") String to,
			@RequestParam("sub") String sub,@RequestParam("bDesc") String bDesc) throws DataException, IOException{
		SimpleMailMessage mail = new SimpleMailMessage();	
		String[] sendAdd = to.split(",");
		mail.setTo(sendAdd);
		mail.setSubject(sub);
		mail.setText(bDesc);
		try{
			mailSender.send(mail);	
		}catch(Exception e){
			logger.error("Error in CustomEmail :"+e.getMessage());
			e.printStackTrace();
		}		
		
		return new MappingJackson2JsonView();
	}
}
