package com.chatbot.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BOT_TEXTS database table.
 * 
 */
@Entity
@Table(name="BOT_TEXTS")
@NamedQuery(name="BotText.findAll", query="SELECT b FROM BotText b")
public class BotText implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TEXT_ID")
	private long textId;

	@Column(name="ARABIC_TEXT")
	private String arabicText;

	@Column(name="ENGLISH_TEXT")
	private String englishText;

	//bi-directional many-to-one association to BotButton
	@ManyToOne
	@JoinColumn(name="BUTTON_ID")
	private BotButton botButton;

	//bi-directional many-to-one association to BotQuickReplyMessage
	@ManyToOne
	@JoinColumn(name="QUICK_REPLY_ID")
	private BotQuickReplyMessage botQuickReplyMessage;

	//bi-directional many-to-one association to BotTextMessage
	@ManyToOne
	@JoinColumn(name="TEXT_MESSAGE_ID")
	private BotTextMessage botTextMessage;

	public BotText() {
	}

	public long getTextId() {
		return this.textId;
	}

	public void setTextId(long textId) {
		this.textId = textId;
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

	public BotButton getBotButton() {
		return this.botButton;
	}

	public void setBotButton(BotButton botButton) {
		this.botButton = botButton;
	}

	public BotQuickReplyMessage getBotQuickReplyMessage() {
		return this.botQuickReplyMessage;
	}

	public void setBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		this.botQuickReplyMessage = botQuickReplyMessage;
	}

	public BotTextMessage getBotTextMessage() {
		return this.botTextMessage;
	}

	public void setBotTextMessage(BotTextMessage botTextMessage) {
		this.botTextMessage = botTextMessage;
	}

}