import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {
        try {
            TelegramBotsApi pattiesRequestBot = new TelegramBotsApi(DefaultBotSession.class);
            pattiesRequestBot.registerBot(new Bot("PattiesRequestsBot", "2079647791:AAEyMFT87p3FSsA-YoS7hSpIUaWVMaJWz9E"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
