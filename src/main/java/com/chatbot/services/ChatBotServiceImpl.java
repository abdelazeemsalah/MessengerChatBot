package com.chatbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.dao.ChatBotRepository;
import com.chatbot.entity.BotInteractionMessage;

@Service
public class ChatBotServiceImpl implements ChatBotService {
	@Autowired
	private ChatBotRepository chatBotRepository;

	public List<BotInteractionMessage> findByBotInteractionInteractionId(Long interactionId) {
		return chatBotRepository.findByBotInteractionInteractionId(interactionId);
	}
}
