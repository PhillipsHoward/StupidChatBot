package fr.wcs.retardedbot;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class ChatBot extends User {

    private AIService aiService;
    private AIConfiguration config;
    private AIDataService aiDataService;
    private AIRequest aiRequest;
    private Context context;
    private final static String BOT_KEY = "66f25c46933843698072745a4393b981";
    private ChatMessage messageFromBot = null;

    public ChatBot(String idUser, String name, String photo) {
        super(idUser, name, photo);

        this.config = new AIConfiguration(BOT_KEY, AIConfiguration.SupportedLanguages.French,
                AIConfiguration.RecognitionEngine.System);
        this.aiDataService = new AIDataService(config);
        this.aiRequest = new AIRequest();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ChatMessage getMessageFromBot() {
        return messageFromBot;
    }

    public void setMessageFromBot(ChatMessage messageFromBot) {
        this.messageFromBot = messageFromBot;
    }

    public String generateRandomDefaultResponse() {
        String result = "";

        ArrayList<String> defaultResponses = new ArrayList<>();
        defaultResponses.add("Gne?");
        defaultResponses.add("J'ai pas compris ta phrase. Stp redis-le en plus simple :-(");
        defaultResponses.add("Toi-même, d'abord.");
        defaultResponses.add("Je comprend rien...");
        defaultResponses.add("Vous parlez avec des mots trop compliqués pour moi :(");

        Random random = new Random();
        int index = random.nextInt(defaultResponses.size()-1) + 1 - 0;

        return defaultResponses.get(index);
    }

    public void clearMessageToSend() {
        this.messageFromBot = null;
    }

    public void getAnAnswer(ChatMessage userMessage, final ArrayList<ChatMessage> chatMessages, final BotListener listener) {

        final String textMessage = userMessage.getTextMessage();
        aiService = AIService.getService(context, config);

        if (!textMessage.equals("")) {

            aiRequest.setQuery(textMessage);
            new AsyncTask<AIRequest,Void,AIResponse>(){

                @Override
                protected AIResponse doInBackground(AIRequest... aiRequests) {
                    final AIRequest request = aiRequests[0];
                    try {
                        final AIResponse response = aiDataService.request(aiRequest);
                        return response;
                    } catch (AIServiceException e) {
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(AIResponse response) {
                    if (response != null) {

                        Result result = response.getResult();
                        String reply = result.getFulfillment().getSpeech();
                        if(reply.equals("")) {
                            reply = generateRandomDefaultResponse();
                        }
                        Date dateBrut = Calendar.getInstance().getTime();
                        String date = buildAFormattedDate(dateBrut);
                        int newMessageIndex = chatMessages.size();
                        messageFromBot = new ChatMessage(reply, idUser, name, photo, date, newMessageIndex);
                        listener.onSuccess(new Object());

                    }
                }

                @Override
                protected void onCancelled(AIResponse aiResponse) {
                    super.onCancelled(aiResponse);
                    listener.onError("Le bot a glitché. Déso !"); //TODO Changer ce message d'erreur...
                }
            }.execute(aiRequest);
        }
        else {
            aiService.startListening();
            //TODO Gérer ce cas d'erreur
        }
    }
}
