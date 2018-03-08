package com.chatbot.services;

import java.util.List;

import com.chatbot.entity.BotInteractionMessage;

public interface ChatBotService {
	public List<BotInteractionMessage> findByBotInteractionInteractionId(Long interactionId);
}
