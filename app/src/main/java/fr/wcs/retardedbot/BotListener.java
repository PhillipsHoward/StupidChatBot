package fr.wcs.retardedbot;

public interface BotListener {

    void onSuccess(Object result);
    void onError(String error);

}
