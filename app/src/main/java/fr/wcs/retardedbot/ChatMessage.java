package fr.wcs.retardedbot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChatMessage {

    private String textMessage;
    private String IdAuthor;
    private String NameAuthor;
    private String photoAuthor;
    private String chatDate;

    public ChatMessage(String textMessage, String idAuthor, String nameAuthor, String photoAuthor, Date brutChatDate) {
        this.textMessage = textMessage;
        IdAuthor = idAuthor;
        NameAuthor = nameAuthor;
        this.photoAuthor = photoAuthor;
        this.chatDate = buildAFormattedDate(brutChatDate);
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

    public String getChatDate() {
        return chatDate;
    }

    public void setChatDate(Date chatDate) {
        this.chatDate = buildAFormattedDate(chatDate);
    }

    public String buildAFormattedDate(Date chatDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
        SimpleDateFormat hourFormat = new SimpleDateFormat("kk:mm");
        String formattedDate = dateFormat.format(chatDate);
        String formattedHour = hourFormat.format(chatDate);
        return "Envoyé le " + formattedDate +" à " + formattedHour;
    }

}
