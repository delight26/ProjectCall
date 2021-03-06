package com.project.call.domain;

import java.sql.Timestamp;

public class NoticeBoard {
	private int nbNo;
	private String nbTitle;
	private String nbContent;
	private Timestamp nbDate;
	private int nbClick;
	private String nbEmail;
	private String nbToid;
	private String nbNickName;
	private int nbCount;
   private int nbMaxPage;
   private int size;
	
	public int getSize() {
	return size;
}

public void setSize(int size) {
	this.size = size;
}

	public int getNbMaxPage() {
	return nbMaxPage;
}

public void setNbMaxPage(int nbMaxPage) {
	this.nbMaxPage = nbMaxPage;
}

	public int getNbCount() {
		return nbCount;
	}

	public void setNbCount(int nbCount) {
		this.nbCount = nbCount;
	}

	public String getNbNickName() {
		return nbNickName;
	}

	public void setNbNickName(String nbNickName) {
		this.nbNickName = nbNickName;
	}

	public String getNbEmail() {
		return nbEmail;
	}

	public String getNbToid() {
		return nbToid;
	}

	public void setNbEmail(String nbEmail) {
		this.nbEmail = nbEmail;
	}

	public void setNbToid(String nbToid) {
		this.nbToid = nbToid;
	}

	public int getNbNo() {
		return nbNo;
	}

	public void setNbNo(int nbNo) {
		this.nbNo = nbNo;
	}

	public String getNbTitle() {
		return nbTitle;
	}

	public void setNbTitle(String nbTitle) {
		this.nbTitle = nbTitle;
	}

	public String getNbContent() {
		return nbContent;
	}

	public void setNbContent(String nbContent) {
		this.nbContent = nbContent;
	}

	public Timestamp getNbDate() {
		return nbDate;
	}

	public void setNbDate(Timestamp nbDate) {
		this.nbDate = nbDate;
	}

	public int getNbClick() {
		return nbClick;
	}

	public void setNbClick(int nbClick) {
		this.nbClick = nbClick;
	}

}
