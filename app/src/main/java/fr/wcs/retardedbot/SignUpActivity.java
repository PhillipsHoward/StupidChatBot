package fr.wcs.retardedbot;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private EditText mNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        //TODO Améliorer la saisie pour que le bouton "ok du clavier" fonctionne
        mNameInput = findViewById(R.id.name_input);
        mEmailInput = findViewById(R.id.email_input);
        mPasswordInput = findViewById(R.id.password_input);
        Button signUpButton = findViewById(R.id.sign_up_button);

        setSignUpButton(signUpButton);

    }

    public void setSignUpButton(Button signUpButton) {

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = "";
                String password = "";
                String userName = "";
                userName = mNameInput.getText().toString().trim();
                email = mEmailInput.getText().toString().trim();
                password = mPasswordInput.getText().toString().trim();

                if (!email.equals("") && !password.equals("") && !userName.equals("")) {
                    SignUpNewUser(email, password, userName);
                    //TODO Insert Loader Here
                } else {
                    Toast.makeText(SignUpActivity.this, "Veuillez renseigner une adresse email et un mot de passe afin de compléter votre inscription.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void SignUpNewUser(String email, String password, final String userName) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            createNewUserInDataBase(user, userName);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "L'inscription a échoué",
                                    Toast.LENGTH_SHORT).show();
                            refreshPage();
                        }
                    }
                });
    }

    public void createNewUserInDataBase(FirebaseUser firebaseUser, String userName) {

        //TODO Choisir une vraie image par défaut
        String uriImage = "https://texasbarblog.lexblogplatformtwo.com/files/2011/12/housto-bankruptcy-attorney-adam-schachter1.jpg";
        final User user = new User(firebaseUser.getUid(), userName, uriImage);

        //Put a new UserModel into database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersReference = database.getReference("users");
        DatabaseReference newUserReference = usersReference.child(firebaseUser.getUid());
        newUserReference.setValue(user);

        //When UserModel is correctly put in database, launch next event
        newUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                launchNextActivity(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO Cas de foirage. Ecrire un toast et refresh la page
            }
        });
    }

    public void launchNextActivity(User user) {
        if (mAuth.getCurrentUser() != null) {
            Singleton.getInstance().setUser(user);
            Intent intent = new Intent(SignUpActivity.this, ConversationActivity.class);
            startActivity(intent);
        }
    }

    public void refreshPage() {
        Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
