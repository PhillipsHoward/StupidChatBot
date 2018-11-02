package fr.wcs.retardedbot;

class Singleton {

    private static final Singleton ourInstance = new Singleton();

    User user;
    User bot;

    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getBot() {
        return bot;
    }

    public void setBot(User bot) {
        this.bot = bot;
    }

    public void initUser() {

        //TODO remplaçer cette instantiacion en dur par une requette Firebase
        String uriImage = "https://texasbarblog.lexblogplatformtwo.com/files/2011/12/housto-bankruptcy-attorney-adam-schachter1.jpg";
        user = new User("AUTH_ID_USER", "Thibault", uriImage);
    }

    public void initBot() {

        //TODO remplaçer cette instantiacion en dur par une requette Firebase
        String uriImage = "https://img.fireden.net/v/image/1506/78/1506786636491.png";
        bot = new User("ID_BOT", "Retarded bot", uriImage);
    }

}
