package com.chatbot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the BOT_INTERACTIONS database table.
 */
@Entity
@Table(name = "BOT_INTERACTIONS")
@NamedQuery(name = "BotInteraction.findAll", query = "SELECT b FROM BotInteraction b")
public class BotInteraction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INTERACTION_ID")
	private long interactionId;

	@Column(name = "INTERACTION_NAME")
	private String interactionName;

	@Column(name = "IS_SECURE")
	private Long isSecure;

	// bi-directional many-to-one association to BotButton
	@OneToMany(mappedBy = "botInteraction")
	private List<BotButton> botButtons;

	// bi-directional many-to-one association to BotInteractionMessage
	@OneToMany(mappedBy = "botInteraction")
	private List<BotInteractionMessage> botInteractionMessages;

	public BotInteraction() {
	}

	public long getInteractionId() {
		return this.interactionId;
	}

	public void setInteractionId(long interactionId) {
		this.interactionId = interactionId;
	}

	public String getInteractionName() {
		return this.interactionName;
	}

	public void setInteractionName(String interactionName) {
		this.interactionName = interactionName;
	}

	public Long getIsSecure() {
		return this.isSecure;
	}

	public void setIsSecure(Long isSecure) {
		this.isSecure = isSecure;
	}

	public List<BotButton> getBotButtons() {
		return this.botButtons;
	}

	public void setBotButtons(List<BotButton> botButtons) {
		this.botButtons = botButtons;
	}

	public BotButton addBotButton(BotButton botButton) {
		getBotButtons().add(botButton);
		botButton.setBotInteraction(this);

		return botButton;
	}

	public BotButton removeBotButton(BotButton botButton) {
		getBotButtons().remove(botButton);
		botButton.setBotInteraction(null);

		return botButton;
	}

	public List<BotInteractionMessage> getBotInteractionMessages() {
		return this.botInteractionMessages;
	}

	public void setBotInteractionMessages(List<BotInteractionMessage> botInteractionMessages) {
		this.botInteractionMessages = botInteractionMessages;
	}

	public BotInteractionMessage addBotInteractionMessage(BotInteractionMessage botInteractionMessage) {
		getBotInteractionMessages().add(botInteractionMessage);
		botInteractionMessage.setBotInteraction(this);

		return botInteractionMessage;
	}

	public BotInteractionMessage removeBotInteractionMessage(BotInteractionMessage botInteractionMessage) {
		getBotInteractionMessages().remove(botInteractionMessage);
		botInteractionMessage.setBotInteraction(null);

		return botInteractionMessage;
	}

}