package com.iktpreobuka.ediary.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.GradingEntity;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
    public JavaMailSender emailSender;
	
	@Override
	public void sendTemplateMessage(GradingEntity grading, String subject) throws Exception{
		MimeMessage mail = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(grading.getStudent().getParent().getEmail());
        helper.setSubject(subject);
        String text="<html><head><style> table, th, td { border: 1px solid black; border-collapse: collapse;} th, td { padding: 15px;} </style> </head> <body>"
				   + "<tr><th>Student</th><th>Subject</th><th>Mark</th><th>Teacher</th></tr>"
				  + "<tr><td>" + grading.getStudent().getFirstName() + " " + grading.getStudent().getLastName() +"</td>"
				+ "<td>" + grading.getTeacherSubjectClass().getTeacherSubject().getSubject().getName() + "</td>"
				+ "<td>" + grading.getMark()+ "</td>"
				+ "<td>" + grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getFirstName() 
				+ " "+ grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getLastName() + "</td></tr></table></body></html>";
        
        helper.setText(text, true);
    	
        emailSender.send(mail);
	}
	
	
}
