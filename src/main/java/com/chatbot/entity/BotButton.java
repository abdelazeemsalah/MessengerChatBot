package com.chatbot.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BOT_BUTTONS database table.
 * 
 */
@Entity
@Table(name="BOT_BUTTONS")
@NamedQuery(name="BotButton.findAll", query="SELECT b FROM BotButton b")
public class BotButton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BUTTON_ID")
	private long buttonId;

	@Column(name="BUTTON_PAYLOAD")
	private String buttonPayload;

	@Column(name="BUTTON_TYPE")
	private String buttonType;

	@Column(name="BUTTON_URL")
	private String buttonUrl;

	//bi-directional many-to-one association to BotInteraction
	@ManyToOne
	@JoinColumn(name="INTERACTION_ID")
	private BotInteraction botInteraction;

	//bi-directional many-to-one association to BotQuickReplyMessage
	@OneToMany(mappedBy="botButton")
	private List<BotQuickReplyMessage> botQuickReplyMessages;

	//bi-directional many-to-one association to BotText
	@OneToMany(mappedBy="botButton")
	private List<BotText> botTexts;

	public BotButton() {
	}

	public long getButtonId() {
		return this.buttonId;
	}

	public void setButtonId(long buttonId) {
		this.buttonId = buttonId;
	}

	public String getButtonPayload() {
		return this.buttonPayload;
	}

	public void setButtonPayload(String buttonPayload) {
		this.buttonPayload = buttonPayload;
	}

	public String getButtonType() {
		return this.buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public String getButtonUrl() {
		return this.buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public BotInteraction getBotInteraction() {
		return this.botInteraction;
	}

	public void setBotInteraction(BotInteraction botInteraction) {
		this.botInteraction = botInteraction;
	}

	public List<BotQuickReplyMessage> getBotQuickReplyMessages() {
		return this.botQuickReplyMessages;
	}

	public void setBotQuickReplyMessages(List<BotQuickReplyMessage> botQuickReplyMessages) {
		this.botQuickReplyMessages = botQuickReplyMessages;
	}

	public BotQuickReplyMessage addBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		getBotQuickReplyMessages().add(botQuickReplyMessage);
		botQuickReplyMessage.setBotButton(this);

		return botQuickReplyMessage;
	}

	public BotQuickReplyMessage removeBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		getBotQuickReplyMessages().remove(botQuickReplyMessage);
		botQuickReplyMessage.setBotButton(null);

		return botQuickReplyMessage;
	}

	public List<BotText> getBotTexts() {
		return this.botTexts;
	}

	public void setBotTexts(List<BotText> botTexts) {
		this.botTexts = botTexts;
	}

	public BotText addBotText(BotText botText) {
		getBotTexts().add(botText);
		botText.setBotButton(this);

		return botText;
	}

	public BotText removeBotText(BotText botText) {
		getBotTexts().remove(botText);
		botText.setBotButton(null);

		return botText;
	}

}