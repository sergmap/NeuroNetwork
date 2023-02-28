package parse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;

public class ParsingText {
    private String filename_in = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\Огниво.txt";
    private String filename_out = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\Огниво_for_learning.txt";

    public void Test() {
        // Открыть исходный файл для чтения
        File file = new File(filename_in);
        List<String> arrayString = new ArrayList<String>();
        try {
            //создаем объект FileReader для чтения объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            //System.out.println(line);
            String tempString = "";

            while (line != null) {
                tempString = "";
                for (int i = 0; i < line.length(); i++) {
                    if ((line.charAt(i) != '.')
                    && (line.charAt(i) != '…')
                    && (line.charAt(i) != '”')
                    && (line.charAt(i) != ';')) {
                        tempString = tempString + line.charAt(i);
                    }
                    else {
                        arrayString.add(tempString);
                        tempString = "";
                    }
                }
                line = reader.readLine();
            }

            
            for (int i = 0; i < arrayString.size(); i++) {
                System.out.println(i + ":" + arrayString.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Вообще нужно исходный текстовый файл разобрать по строкам по всем символам кроме запятой.
        // Разобрал, теперь массив arrayString нужно записать в результирующий файл
        //arrayString
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename_out)));

            for (int i = 0; i < arrayString.size(); i++) {
                writer.write(arrayString.get(i));
                writer.write("\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
