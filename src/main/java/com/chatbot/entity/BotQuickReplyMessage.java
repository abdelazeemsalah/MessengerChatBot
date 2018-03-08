package com.chatbot.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BOT_QUICK_REPLY_MESSAGE database table.
 * 
 */
@Entity
@Table(name="BOT_QUICK_REPLY_MESSAGE")
@NamedQuery(name="BotQuickReplyMessage.findAll", query="SELECT b FROM BotQuickReplyMessage b")
public class BotQuickReplyMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="QUICK_MSG_ID")
	private long quickMsgId;

	//bi-directional many-to-one association to BotButton
	@ManyToOne
	@JoinColumn(name="BUTTON_ID")
	private BotButton botButton;

	//bi-directional many-to-one association to BotTextMessage
	@ManyToOne
	@JoinColumn(name="TEXT_MSG_ID")
	private BotTextMessage botTextMessage;

	//bi-directional many-to-one association to BotText
	@OneToMany(mappedBy="botQuickReplyMessage")
	private List<BotText> botTexts;

	public BotQuickReplyMessage() {
	}

	public long getQuickMsgId() {
		return this.quickMsgId;
	}

	public void setQuickMsgId(long quickMsgId) {
		this.quickMsgId = quickMsgId;
	}

	public BotButton getBotButton() {
		return this.botButton;
	}

	public void setBotButton(BotButton botButton) {
		this.botButton = botButton;
	}

	public BotTextMessage getBotTextMessage() {
		return this.botTextMessage;
	}

	public void setBotTextMessage(BotTextMessage botTextMessage) {
		this.botTextMessage = botTextMessage;
	}

	public List<BotText> getBotTexts() {
		return this.botTexts;
	}

	public void setBotTexts(List<BotText> botTexts) {
		this.botTexts = botTexts;
	}

	public BotText addBotText(BotText botText) {
		getBotTexts().add(botText);
		botText.setBotQuickReplyMessage(this);

		return botText;
	}

	public BotText removeBotText(BotText botText) {
		getBotTexts().remove(botText);
		botText.setBotQuickReplyMessage(null);

		return botText;
	}

}