package com.iktpreobuka.ediary.services;

import com.iktpreobuka.ediary.entities.GradingEntity;

public interface EmailService {

	void sendTemplateMessage(GradingEntity grading, String subject) throws Exception;
}
