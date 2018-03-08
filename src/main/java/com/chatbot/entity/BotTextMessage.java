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
 * The persistent class for the BOT_TEXT_MESSAGES database table.
 */
@Entity
@Table(name = "BOT_TEXT_MESSAGES")
@NamedQuery(name = "BotTextMessage.findAll", query = "SELECT b FROM BotTextMessage b")
public class BotTextMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TEXT_MSG_ID")
	private long textMsgId;

	@Column(name = "ARABIC_TEXT")
	private String arabicText;

	@Column(name = "ENGLISH_TEXT")
	private String englishText;

	@Column(name = "IS_STATIC")
	private Long isStatic;

	// bi-directional many-to-one association to BotQuickReplyMessage
	@OneToMany(mappedBy = "botTextMessage")
	private List<BotQuickReplyMessage> botQuickReplyMessages;

	// bi-directional many-to-one association to BotText
	@OneToMany(mappedBy = "botTextMessage")
	private List<BotText> botTexts;

	// bi-directional many-to-one association to BotInteractionMessage
	@ManyToOne
	@JoinColumn(name = "MESSAGE_ID")
	private BotInteractionMessage botInteractionMessage;

	public BotTextMessage() {
	}

	public long getTextMsgId() {
		return this.textMsgId;
	}

	public void setTextMsgId(long textMsgId) {
		this.textMsgId = textMsgId;
	}

	public String getArabicText() {
		return this.arabicText;
	}

	public void setArabicText(String arabicText) {
		this.arabicText = arabicText;
	}

	public String getEnglishText() {
		return this.englishText;
	}

	public void setEnglishText(String englishText) {
		this.englishText = englishText;
	}

	public Long getIsStatic() {
		return this.isStatic;
	}

	public void setIsStatic(Long isStatic) {
		this.isStatic = isStatic;
	}

	public List<BotQuickReplyMessage> getBotQuickReplyMessages() {
		return this.botQuickReplyMessages;
	}

	public void setBotQuickReplyMessages(List<BotQuickReplyMessage> botQuickReplyMessages) {
		this.botQuickReplyMessages = botQuickReplyMessages;
	}

	public BotQuickReplyMessage addBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		getBotQuickReplyMessages().add(botQuickReplyMessage);
		botQuickReplyMessage.setBotTextMessage(this);

		return botQuickReplyMessage;
	}

	public BotQuickReplyMessage removeBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		getBotQuickReplyMessages().remove(botQuickReplyMessage);
		botQuickReplyMessage.setBotTextMessage(null);

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
		botText.setBotTextMessage(this);

		return botText;
	}

	public BotText removeBotText(BotText botText) {
		getBotTexts().remove(botText);
		botText.setBotTextMessage(null);

		return botText;
	}

	public BotInteractionMessage getBotInteractionMessage() {
		return this.botInteractionMessage;
	}

	public void setBotInteractionMessage(BotInteractionMessage botInteractionMessage) {
		this.botInteractionMessage = botInteractionMessage;
	}

}