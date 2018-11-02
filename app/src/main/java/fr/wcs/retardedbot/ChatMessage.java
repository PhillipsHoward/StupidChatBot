package fr.wcs.retardedbot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {

    private String textMessage;
    private String IdAuthor;
    private String NameAuthor;
    private String photoAuthor;
    private String messageDate;

    public ChatMessage(String textMessage, String idAuthor, String nameAuthor, String photoAuthor, Date brutChatDate) {
        this.textMessage = textMessage;
        IdAuthor = idAuthor;
        NameAuthor = nameAuthor;
        this.photoAuthor = photoAuthor;
        this.messageDate = buildAFormattedDate(brutChatDate);
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

    public void setMessageDate(Date messageDate) {
        this.messageDate = buildAFormattedDate(messageDate);
    }

    public String buildAFormattedDate(Date chatDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd"); //TODO vérifier ce binz
        SimpleDateFormat hourFormat = new SimpleDateFormat("kk:mm");
        String formattedDate = dateFormat.format(chatDate);
        String formattedHour = hourFormat.format(chatDate);
        return "Envoyé le " + formattedDate +" à " + formattedHour;
    }

}
