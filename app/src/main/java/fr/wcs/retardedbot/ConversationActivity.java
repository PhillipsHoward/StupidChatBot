package fr.wcs.retardedbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        final RecyclerView listMessages = findViewById(R.id.messages_list);


        //TODO GET CONVERSATION FIREBASE EN FONCTION DE L'ID de l'USER. Récupérer Conversation.

        //En attendant, une mise en place provisoire :
        Singleton singleton = Singleton.getInstance();
        Date fakeDate = Calendar.getInstance().getTime();
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("Salut ça va ?", singleton.getUser().getIdUser(), singleton.getUser().getName(), singleton.getUser().getPhoto(), fakeDate));
        chatMessages.add(new ChatMessage("Oui et toi ?", singleton.getBot().getIdUser(), singleton.getBot().getName(), singleton.getBot().getPhoto(), fakeDate));
        chatMessages.add(new ChatMessage("Nickel. Comme un lundi.", singleton.getUser().getIdUser(), singleton.getUser().getName(), singleton.getUser().getPhoto(), fakeDate));
        chatMessages.add(new ChatMessage("Tip top alors.", singleton.getBot().getIdUser(), singleton.getBot().getName(), singleton.getBot().getPhoto(), fakeDate));
        chatMessages.add(new ChatMessage("On s'éclate hein. Houlala oui mm hm hm", singleton.getUser().getIdUser(), singleton.getUser().getName(), singleton.getUser().getPhoto(), fakeDate));
        ConversationModel conversation = new ConversationModel(singleton.getUser(), singleton.getBot(), chatMessages, singleton.getUser().getIdUser());


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMessages.setLayoutManager(layoutManager);
        ConversationRecyclerAdapter adapter = new ConversationRecyclerAdapter(conversation.getChatMessages(), ConversationActivity.this);
        listMessages.setAdapter(adapter);

        //TODO Set Send Button Method

    }
}
