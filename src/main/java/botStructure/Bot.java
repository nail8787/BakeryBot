package botStructure;

import excelClasses.Excel;
import initCollections.clientMap;
import initCollections.rowsHashMap;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.Date;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    public static Logger log = Logger.getLogger(Bot.class.getName());
    public Excel excel;

    public Bot(String botName, String botToken) throws IOException {
        super();
        this.botName= botName;
        this.botToken = botToken;
        excel = new Excel();
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        System.out.println("Сообщение: " + msg.getText() + "\nВремя сообщения: " +
                (new Date((long) msg.getDate() * 1000)) + "\nОт кого: " + msg.getFrom().getFirstName());
        try {
            int currentCell = excel.checkOrdersRow(msg.getFrom());
            excel.addNameFrom(Utils.getNameFromMap(msg.getFrom().getUserName()), currentCell);
            String[] parsedMessage = parseMsg(msg);
            if (!checkMsgNameAndNumber(parsedMessage, msg.getChatId().toString())){
                System.out.println("Некорректные данные");
                return;
            }
            excel.addOrderToColumn(parsedMessage, currentCell);
            excel.recalculateFormulas();
            excel.writeToFile();
            sendMessage(msg.getChatId().toString(), "Заказ принят");
        } catch (TelegramApiException e) {
            log.severe("Exception: " + e);
        }
    }

    private void sendMessage(String chatId, String msg) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(msg);
        execute(sendMessage);
    }

    private String[] parseMsg(Message msg){
        String msgText = msg.getText();
        String[] strings = msgText.split(" - ");
        return strings;
    }

    private boolean checkMsgNameAndNumber(String[] strings, String chatId) throws TelegramApiException {

        if (!(rowsHashMap.pattiesRows.containsKey(strings[0]))) {
            sendMessage(chatId, "Некорректное название пирожков");
            return false;
        }
        if (Integer.parseInt(strings[1]) < 0) {
            sendMessage(chatId, "Некорректное кол-во пирожков");
            return false;
        }
        return true;
    }

}
