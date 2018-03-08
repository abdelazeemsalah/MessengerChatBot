package com.chatbot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the BOT_INTERACTION_MESSAGES database table.
 */
@Entity
@Table(name = "BOT_INTERACTION_MESSAGES")
@NamedQuery(name = "BotInteractionMessage.findAll", query = "SELECT b FROM BotInteractionMessage b")
public class BotInteractionMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MESSAGE_ID")
	private long messageId;

	@Column(name = "MESSAGE_PRIORITY")
	private Long messagePriority;

	// bi-directional many-to-one association to BotInteraction
	@ManyToOne
	@JoinColumn(name = "INTERACTION_ID")
	private BotInteraction botInteraction;

	// bi-directional many-to-one association to BotMessageType
	@ManyToOne
	@JoinColumn(name = "MESSAGE_TYPE")
	private BotMessageType botMessageType;

	// bi-directional many-to-one association to BotTextMessage
	@OneToMany(mappedBy = "botInteractionMessage")
	private List<BotTextMessage> botTextMessages;

	public BotInteractionMessage() {
	}

	public long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public Long getMessagePriority() {
		return this.messagePriority;
	}

	public void setMessagePriority(Long messagePriority) {
		this.messagePriority = messagePriority;
	}

	public BotInteraction getBotInteraction() {
		return this.botInteraction;
	}

	public void setBotInteraction(BotInteraction botInteraction) {
		this.botInteraction = botInteraction;
	}

	public BotMessageType getBotMessageType() {
		return this.botMessageType;
	}

	public void setBotMessageType(BotMessageType botMessageType) {
		this.botMessageType = botMessageType;
	}

	public List<BotTextMessage> getBotTextMessages() {
		return this.botTextMessages;
	}

	public void setBotTextMessages(List<BotTextMessage> botTextMessages) {
		this.botTextMessages = botTextMessages;
	}

	public BotTextMessage addBotTextMessage(BotTextMessage botTextMessage) {
		getBotTextMessages().add(botTextMessage);
		botTextMessage.setBotInteractionMessage(this);

		return botTextMessage;
	}

	public BotTextMessage removeBotTextMessage(BotTextMessage botTextMessage) {
		getBotTextMessages().remove(botTextMessage);
		botTextMessage.setBotInteractionMessage(null);

		return botTextMessage;
	}

}