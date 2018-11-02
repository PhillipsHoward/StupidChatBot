package fr.wcs.retardedbot;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;

public class ConversationModel {

    User user;
    User botUser;
    ArrayList<ChatMessage> chatMessages;
    String conversationId;

    public ConversationModel(User user, User botUser, ArrayList<ChatMessage> chatMessages, String conversationId) {
        this.user = user;
        this.botUser = botUser;
        this.chatMessages = chatMessages;
        this.conversationId = conversationId;
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

    public User getBotUser() {
        return botUser;
    }

    public void setBotUser(User botUser) {
        this.botUser = botUser;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void setSendButton(ImageButton buttonSend, final EditText textInput, ConversationRecyclerAdapter adapter) {

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageToSend = "";
                messageToSend = textInput.getText().toString().trim();
                if(!messageToSend.equals("")) {
                    sendNewMessage();
                }
            }
        });
    }

    public void sendNewMessage() {
        //TODO Envoie un nouveau message et le stocke dans la firebase
    }
}
