package fr.wcs.retardedbot;

public class User {

    private String idUser;
    private String name;
    private String photo;
    private String status = "Hello there!";

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
}
