package excelClasses;

import botStructure.Utils;
import initCollections.rowsHashMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Excel {
    private String filePath = "C:/Users/dnail/OneDrive/Рабочий стол/Пекарня/Лист заказа1.xlsm";
    public Workbook excelFile;
    public Sheet excelTable;

    public Excel() {
        try {
//            this.excelFile = WorkbookFactory.create(new FileInputStream(filePath));
            this.excelFile = new XSSFWorkbook(new FileInputStream(filePath));
            this.excelTable = excelFile.getSheet("Форма");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNameFrom(String user, int currentCell) {
        Row nameRow = excelTable.getRow(2);
        Cell nameCell = nameRow.getCell(currentCell);
        nameCell.setCellValue(user);
    }

    public int checkOrdersRow(User agent) {
        Row nameRow = excelTable.getRow(2);
        for (int i = 1; i <= 44; i++) {
            if (i > 21 && i < 25) //3 строчки в файле заняты концом и началом страниц
                continue;
            String cellValue = nameRow.getCell(i).getStringCellValue();
            if (cellValue.isEmpty())
                return i;
            if (cellValue.equals(Utils.getNameFromMap(agent.getUserName())))
                return i;
        }
        return 25; //todo remove later
    }

    public void writeToFile() {
        try {
            OutputStream fileOut = new FileOutputStream(filePath);
            excelFile.write(fileOut);
            System.out.println("Записано в файл");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOrderToColumn (String[] strings, int currentCell) throws TelegramApiException {
        Row row = excelTable.getRow(rowsHashMap.pattiesRows.get(strings[0]));
        Cell cell = row.getCell(currentCell);
        cell.setCellValue(Double.parseDouble(strings[1]));
    }

    public void recalculateFormulas() {
        XSSFFormulaEvaluator.evaluateAllFormulaCells(excelFile);
    }

}
