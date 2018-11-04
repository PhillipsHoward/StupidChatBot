package fr.wcs.retardedbot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {

    private String textMessage;
    private String IdAuthor;
    private String NameAuthor;
    private String photoAuthor;
    private String messageDate;
    private int orderInConversation;

    public ChatMessage(String textMessage, String idAuthor, String nameAuthor, String photoAuthor, String date, int orderInConversation) {
        this.textMessage = textMessage;
        IdAuthor = idAuthor;
        NameAuthor = nameAuthor;
        this.photoAuthor = photoAuthor;
        this.messageDate = date;
        this.orderInConversation = orderInConversation;
    }

    public ChatMessage(){};

    public int getOrderInConversation() {
        return orderInConversation;
    }

    public void setOrderInConversation(int orderInConversation) {
        this.orderInConversation = orderInConversation;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getIdAuthor() {
        return IdAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        IdAuthor = idAuthor;
    }

    public String getNameAuthor() {
        return NameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        NameAuthor = nameAuthor;
    }

    public String getPhotoAuthor() {
        return photoAuthor;
    }

    public void setPhotoAuthor(String photoAuthor) {
        this.photoAuthor = photoAuthor;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
}
