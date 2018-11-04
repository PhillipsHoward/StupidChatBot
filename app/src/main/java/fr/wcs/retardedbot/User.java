package fr.wcs.retardedbot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User {

    protected String idUser;
    protected String name;
    protected String photo;
    protected String status = "Hello there!";

    public User(String idUser, String name, String photo) {
        this.idUser = idUser;
        this.name = name;
        this.photo = photo;
    }

    public User(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ChatMessage sendNewMessage(String textMessage, int newMessageIndex) {
        Date dateBrut = Calendar.getInstance().getTime();
        String date = buildAFormattedDate(dateBrut);
        final ChatMessage messageToSend = new ChatMessage(textMessage, idUser, name, photo, date, newMessageIndex);
        return messageToSend;
    }

    public String buildAFormattedDate(Date chatDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd"); //TODO vérifier ce binz
        SimpleDateFormat hourFormat = new SimpleDateFormat("kk:mm");
        String formattedDate = dateFormat.format(chatDate);
        String formattedHour = hourFormat.format(chatDate);
        return "Envoyé le " + formattedDate +" à " + formattedHour;
    }
}
