package fr.wcs.retardedbot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConversationActivity extends AppCompatActivity {

    ConversationModel mConversation;
    ConversationRecyclerAdapter mAdapter;
    RecyclerView mListMessages;
    ImageButton mSendButton;
    EditText mInputText;
    Singleton mSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        mListMessages = findViewById(R.id.messages_list);
        mSendButton = findViewById(R.id.send_button);
        mInputText = findViewById(R.id.text_response);
        mSingleton = Singleton.getInstance();
        mSingleton.getBot().setContext(ConversationActivity.this);

        setUpHeadUI();
        String conversationId = mSingleton.getBot().getIdUser()+"_WITH_"+mSingleton.getUser().getIdUser();
        getConversationFromFirebase(conversationId);
    }

    private void setUpHeadUI() {

        //TODO Mise à jour de la photo de profil en fonction de l'uri du bot. Voir. Glide.
        TextView mTopStatus = findViewById(R.id.interlocutor_status);
        TextView mTopName = findViewById(R.id.interlocutor_name);

        mTopName.setText(mSingleton.getBot().getName());
        mTopStatus.setText(mSingleton.getBot().getStatus());
    }

    private void setUpUIChatroom(RecyclerView listMessages, ImageButton sendButton, EditText inputText) {
        mConversation.setmRecyclerView(listMessages);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMessages.setLayoutManager(layoutManager);
        ConversationRecyclerAdapter adapter = new ConversationRecyclerAdapter(mConversation.getChatMessages(), ConversationActivity.this);
        listMessages.setAdapter(adapter);
        mConversation.setAdapter(adapter);
        mConversation.setSendButton(sendButton, inputText);
    }

    private void getConversationFromFirebase(final String conversationIdToLookFor) {
        //Put a new Conversation in Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference conversationsReference = database.getReference("conversations");
        DatabaseReference currentConversationRef = conversationsReference.child(conversationIdToLookFor);
        DatabaseReference messagesListRef = currentConversationRef.child("chatMessages");

        //When new Conversation is correctly put in database, launch next event
        messagesListRef.orderByChild("orderInConversation").limitToLast(70).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ChatMessage> chatMessages =  new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage message =
                            messageSnapshot.getValue(ChatMessage.class);
                    chatMessages.add(message);
                }
                mConversation = new ConversationModel(mSingleton.getUser(), mSingleton.getBot(), chatMessages, conversationIdToLookFor);
                setUpUIChatroom(mListMessages, mSendButton, mInputText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO Cas de foirage. Ecrire un toast et refresh la page
            }
        });
    }
}
