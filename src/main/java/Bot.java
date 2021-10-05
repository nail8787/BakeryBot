
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class Bot extends TelegramLongPollingBot {
    public final String botName;
    public final String botToken;

    public Bot(String botName, String botToken) {
        super();
        this.botName= botName;
        this.botToken = botToken;
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
        System.out.println("Сообщение: " + msg.getText());
        String file = "C:/Users/dnail/OneDrive/Рабочий стол/Пекарня/Лист заказа1.xlsm";
        try {
            Workbook excelFile = WorkbookFactory.create(new FileInputStream(file));
            Sheet excelTable = excelFile.getSheet("Форма");
            Row row = excelTable.getRow(6);
            Cell cell = row.getCell(5);
            if (cell == null)
                cell = row.createCell(5);
            cell.setCellValue(6);
            OutputStream fileOut= new FileOutputStream(file);
            excelFile.write(fileOut);
            System.out.println("Записано в файл");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
