package fr.wcs.retardedbot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConversationModel {

    User user;
    ChatBot bot;
    ArrayList<ChatMessage> chatMessages;
    String conversationId;
    ConversationRecyclerAdapter adapter;
    RecyclerView mRecyclerView;

    public ConversationModel(User user, ChatBot botUser, ArrayList<ChatMessage> chatMessages, String conversationId) {
        this.user = user;
        this.bot = botUser;
        this.chatMessages = chatMessages;
        this.conversationId = conversationId;
    }

    public ConversationModel(){};

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public ConversationRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ConversationRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatBot getBot() {
        return bot;
    }

    public void setBot(ChatBot bot) {
        this.bot = bot;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void updateConversation(final ChatMessage messageToAdd) {

        //Put new ChatMessage in database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference conversationsReference = database.getReference("conversations");
        DatabaseReference currentConversationRef = conversationsReference.child(this.conversationId);
        DatabaseReference messagesListRef = currentConversationRef.child("chatMessages");
        DatabaseReference newMessageReference = messagesListRef.child(String.valueOf(messageToAdd.getOrderInConversation()));
        newMessageReference.setValue(messageToAdd);

        //When ChatMessage is correctly put in database, then notify adapter
        newMessageReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages.add(messageToAdd);
                adapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(chatMessages.size()-1);

                //TODO A Loader here ?
                //TODO Empêcher l'utilisateur d'envoyer un nouveau message dans que le bot n'a pas répondu
                //If Message comes from user, ask bot for an answer
                if(messageToAdd.getIdAuthor().equals(Singleton.getInstance().getUser().idUser)) {
                    askBotAnAnswer(messageToAdd, chatMessages);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO "Envoi impossible" ou un truc comme ça.
            }
        });
    }

    public void setSendButton(ImageButton sendButton, final EditText inputText) {

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                message = inputText.getText().toString().trim();
                if(!message.equals("")) {
                    inputText.getText().clear();
                    int newMessageIndex = chatMessages.size();
                    updateConversation(user.sendNewMessage(message, newMessageIndex));
                }
            }
        });
    }

    public void askBotAnAnswer(ChatMessage userMessage, final ArrayList<ChatMessage> chatMessages) {

    bot.getAnAnswer(userMessage, chatMessages, new BotListener() {
        @Override
        public void onSuccess(Object result) {
            updateConversation(bot.getMessageFromBot());
        }

        @Override
        public void onError(String error) {
            //TODO Gérer ce cas d'erreur
        }
    });

    }

}
