package fr.wcs.retardedbot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailInput;
    private EditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        //TODO Améliorer la saisie pour que le bouton "ok du clavier" fonctionne
        mEmailInput = findViewById(R.id.email_input);
        mPasswordInput = findViewById(R.id.password_input);
        Button signInButton = findViewById(R.id.sign_in_button);
        TextView signUpButton = findViewById(R.id.inscription);

        setSignInButton(signInButton);
        setSignUpButton(signUpButton);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setSignInButton(Button signInButton) {

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = "";
                String password = "";
                email = mEmailInput.getText().toString().trim();
                password = mPasswordInput.getText().toString().trim();

                if (!email.equals("") && !password.equals("")) {
                    SignIn(email, password);
                    //TODO Insert Loader Here
                } else {
                    Toast.makeText(MainActivity.this, "Veuillez renseigner votre adresse mail et votre mot de passe.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void SignIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            initializeUserFromFirebaseDatas(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Email ou mot de passe incorrect",
                                    Toast.LENGTH_SHORT).show();
                            refreshPage();
                        }
                    }
                });
    }

    public void initializeUserFromFirebaseDatas(FirebaseUser user) {

        if(user != null) {
            final Singleton singleton = Singleton.getInstance();
            singleton.initUser(user.getUid(), new FirebaseListener() {
                @Override
                public void onSuccess(Object result) {
                    launchActivity(ConversationActivity.class);
                    singleton.initBot();
                }
                @Override
                public void onError(String error) {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    refreshPage();
                }
            });
        } else {
            //TODO GROS FOIRAGE. Cas à gérer.
        }
    }

    public void setSignUpButton(TextView signUpButton) {

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(SignUpActivity.class);
            }
        });

    }

    public void launchActivity(Class targetActivity) {
        Intent intent = new Intent(MainActivity.this, targetActivity);
        startActivity(intent);
    }

    public void refreshPage() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
