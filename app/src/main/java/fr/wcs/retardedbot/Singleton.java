package fr.wcs.retardedbot;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class Singleton {

    private static final Singleton ourInstance = new Singleton();

    User user;
    User bot;

    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
        String uriImage = "https://img.fireden.net/v/image/1506/78/1506786636491.png";
        bot = new User("ID_BOT", "Retarded bot", uriImage);
        bot.setStatus("Cil-vous-plaie, venaient me parlé :-( :-(");
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

    public void initUser(String userId, final FirebaseListener listener) {

        if (userId != null && !userId.equals("")) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersReference = database.getReference("users");
            DatabaseReference userToInitRef = usersReference.child(userId);

            //When UserModel is correctly put in database, launch next part of the app
            userToInitRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User userFromDatabase = dataSnapshot.getValue(User.class);
                    user = userFromDatabase;
                    listener.onSuccess(new Object());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //TODO Modifier ce subtil message d'erreur
                    listener.onError("Foirage du chargement des données !");
                }
            });

        } else {
            //TODO initUser impossible car l'ID fournie n'est pas correcte
        }

    }

}
