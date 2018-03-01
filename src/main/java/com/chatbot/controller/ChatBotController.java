package com.chatbot.controller;

import static java.util.Optional.empty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.exception.MessengerVerificationException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.message.quickreply.QuickReply;
import com.github.messenger4j.send.message.quickreply.TextQuickReply;
import com.github.messenger4j.send.message.template.GenericTemplate;
import com.github.messenger4j.send.message.template.Template;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.PostbackButton;
import com.github.messenger4j.send.message.template.common.Element;
import com.github.messenger4j.webhook.event.PostbackEvent;
import com.github.messenger4j.webhook.event.QuickReplyMessageEvent;
import com.github.messenger4j.webhook.event.TextMessageEvent;

@RestController
@RequestMapping("/callback")
public class ChatBotController {

	private final Messenger messenger;

	private static final Logger logger = LoggerFactory.getLogger(ChatBotController.class);

	@Autowired
	public ChatBotController(final Messenger sendClient) {
		this.messenger = sendClient;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") final String mode,
			@RequestParam("hub.verify_token") final String verifyToken, @RequestParam("hub.challenge") final String challenge) {

		logger.debug("Received Webhook verification request - mode: {} | verifyToken: {} | challenge: {}", mode, verifyToken, challenge);
		try {
			this.messenger.verifyWebhook(mode, verifyToken);
			return ResponseEntity.status(HttpStatus.OK).body(challenge);
		} catch (MessengerVerificationException e) {
			logger.warn("Webhook verification failed: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> handleCallback(@RequestBody final String payload, @RequestHeader("X-Hub-Signature") final String signature) {

		logger.debug("Received Messenger Platform callback - payload: {} | signature: {}", payload, signature);
		try {
			// this.receiveClient.processCallbackPayload(payload, signature);
			messenger.onReceiveEvents(payload, Optional.of(signature), event -> {

				final String senderId = event.senderId();
				final java.time.Instant timestamp = event.timestamp();

				if (event.isTextMessageEvent()) {
					final TextMessageEvent textMessageEvent = event.asTextMessageEvent();
					final String messageId = textMessageEvent.messageId();
					String text = textMessageEvent.text();

					logger.debug("Received text message from '{}' at '{}' with content: {} (mid: {})", senderId, timestamp, text, messageId);
					String textToSend = "";
					// boolean isArabicMsg = isProbablyArabic(text);
					// if (isArabicMsg) {
					// text = translateToEn(text);
					// }
					textToSend = "Hey there! how can I help you?";

					// sendQuickReplyMessage(textToSend, messenger, senderId);
					sendTemplateMessage(messenger, senderId);
				} else if (event.isQuickReplyMessageEvent()) {
					final QuickReplyMessageEvent quickReplyMessageEvent = event.asQuickReplyMessageEvent();
					final String messageId = quickReplyMessageEvent.messageId();
					String text = quickReplyMessageEvent.payload();
					sendQuickReplyMessage(text, messenger, senderId);
				} else if (event.isPostbackEvent()) {
					PostbackEvent postbackEvent = event.asPostbackEvent();
					String text = postbackEvent.payload().get();
					sendQuickReplyMessage(text, messenger, senderId);
				}

			});
			logger.debug("Processed callback payload successfully");
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (MessengerVerificationException e) {
			logger.warn("Processing of callback payload failed: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	private void sendTextMessage(String text, Messenger messenger, String senderId) {

		final String recipientId = senderId;
		final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE, TextMessage.create(text));

		try {
			messenger.send(payload);
		} catch (MessengerApiException | MessengerIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendQuickReplyMessage(String text, Messenger messenger, String senderId) {

		final String recipientId = senderId;
		QuickReply qR1 = TextQuickReply.create("brilliant1", "BRILLIANT_PAYLOAD1");
		QuickReply qR2 = TextQuickReply.create("brilliant2", "BRILLIANT_PAYLOAD2");
		QuickReply qR3 = TextQuickReply.create("brilliant3", "BRILLIANT_PAYLOAD3");

		List<QuickReply> quickReplies = new ArrayList<>();
		quickReplies.add(qR1);
		quickReplies.add(qR2);
		quickReplies.add(qR3);
		Optional<List<QuickReply>> quickRepliesOp = Optional.of(quickReplies);
		final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE, TextMessage.create(text, quickRepliesOp, empty()));

		try {
			messenger.send(payload);
		} catch (MessengerApiException | MessengerIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendTemplateMessage(Messenger messenger, String recipientId) {

		// List<Button> buttons=new ArrayList<>();
		// buttons.add(e)
		URL imageUrl = null;

		try {
			imageUrl = new URL("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button button11 = PostbackButton.create("button1", "button1 in element1");
		Button button12 = PostbackButton.create("button2", "button2 in element1");
		List<Button> buttons1 = new ArrayList<>();
		buttons1.add(button11);
		buttons1.add(button12);
		Optional<List<Button>> buttons1Op = Optional.of(buttons1);

		Button button21 = PostbackButton.create("button1", "button1 in element2");
		Button button22 = PostbackButton.create("button2", "button2 in element2");
		List<Button> buttons2 = new ArrayList<>();
		buttons2.add(button21);
		buttons2.add(button22);
		Optional<List<Button>> buttons2Op = Optional.of(buttons2);

		Button button31 = PostbackButton.create("button1", "button1 in element3");
		Button button32 = PostbackButton.create("button2", "button2 in element3");
		List<Button> buttons3 = new ArrayList<>();
		buttons3.add(button31);
		buttons3.add(button32);
		Optional<List<Button>> buttons3Op = Optional.of(buttons3);

		Optional<URL> imageUrlOp = Optional.of(imageUrl);
		Element element1 = Element.create("Title1", Optional.of("subtitle1"), imageUrlOp, empty(), buttons1Op);
		Element element2 = Element.create("Title2", Optional.of("subtitle2"), empty(), empty(), buttons2Op);
		Element element3 = Element.create("Title3", Optional.of("subtitle3"), empty(), empty(), buttons3Op);
		List<Element> elements = new ArrayList<>();
		elements.add(element1);
		elements.add(element2);
		elements.add(element3);
		Template template = GenericTemplate.create(elements);
		final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE, TemplateMessage.create(template));

		try {
			messenger.send(payload);
		} catch (MessengerApiException | MessengerIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity<String> getMsg(@PathVariable("param") String msg) {

		String output = "Jersey say : " + msg;

		return ResponseEntity.status(200).body(output);

	}

}
