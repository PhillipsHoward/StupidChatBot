package fr.wcs.retardedbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        final RecyclerView listMessages = findViewById(R.id.messages_list);
        ImageButton sendButton = findViewById(R.id.send_button);
        EditText inputText = findViewById(R.id.text_response);

        //TODO DEBUG. DELETE LES DEUX LIGNES SUIVANTES
        String testName = Singleton.getInstance().getUser().getName();
        Toast.makeText(ConversationActivity.this, "Hello " + testName, Toast.LENGTH_SHORT).show();

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

        //Fin du gros pâté pas beau provisoire

        ConversationModel conversation = new ConversationModel(singleton.getUser(), singleton.getBot(), chatMessages, singleton.getUser().getIdUser(), listMessages);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMessages.setLayoutManager(layoutManager);
        ConversationRecyclerAdapter adapter = new ConversationRecyclerAdapter(conversation.getChatMessages(), ConversationActivity.this);
        listMessages.setAdapter(adapter);
        conversation.setAdapter(adapter);
        conversation.setSendButton(sendButton, inputText);

    }
}
