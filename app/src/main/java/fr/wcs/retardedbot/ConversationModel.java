package fr.wcs.retardedbot;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConversationModel {

    User user;
    User botUser;
    ArrayList<ChatMessage> chatMessages;
    String conversationId;
    ConversationRecyclerAdapter adapter;
    RecyclerView mRecyclerView;

    public ConversationModel(User user, User botUser, ArrayList<ChatMessage> chatMessages, String conversationId, RecyclerView recyclerView) {
        this.user = user;
        this.botUser = botUser;
        this.chatMessages = chatMessages;
        this.conversationId = conversationId;
        this.mRecyclerView = recyclerView;
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

    public void sendNewMessage(String message) {
        Date date = Calendar.getInstance().getTime();
        ChatMessage messageToSend = new ChatMessage(message, user.getIdUser(), user.getName(), user.getPhoto(), date);
        //TODO Stocker cela dans la firebase et ne passer à la suite que lorsque c'est fait !
        chatMessages.add(messageToSend);
        adapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(chatMessages.size()-1);
    }

    public void setSendButton(ImageButton sendButton, final EditText inputText) {

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                message = inputText.getText().toString().trim();
                if(!message.equals("")) {
                    inputText.getText().clear();
                    sendNewMessage(message);
                }
            }
        });
    }
}
