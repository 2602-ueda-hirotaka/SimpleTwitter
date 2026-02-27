package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable {
	private int id;
	private int messageId;
	private int userId;
	private String account;
	private String name;
	private String text;
	private Date createdDate;

	// --- id ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// --- messageId ---
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	// --- userId ---
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	// --- account ---
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	// --- name ---
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// --- text ---
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// --- createdDate ---
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}