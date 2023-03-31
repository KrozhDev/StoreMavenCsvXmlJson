import com.opencsv.CSVWriter;

import java.io.*;
import java.util.stream.Collectors;

public class ClientLog {

    //todo сделать методы записи в файл не статичными, а создавтаь экземпляр класса и туда
    //todo в свой класс обновлять текстовый файл, а в конце уже вытащить его у
    //todo у экземпляра класса и записать конечный csv-файл

    private static File txtFile = new File("./src/main/resources/txtFile");
    private static File csvFile;
    private static FileWriter fw;


    static {
        try {
            fw = new FileWriter(txtFile);

            fw.append("productNum,amount\n");
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getTxtFile() {
        return txtFile;
    }


    public static File log(int productNum, int amount) {

        String newStr = new String("");
        StringBuilder sb = new StringBuilder("");
        sb.append(productNum);
        sb.append(",");
        sb.append(amount);
        sb.append("\n");
        newStr = sb.toString();

        try {
            fw.write(newStr);
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return txtFile;
    }

    public static File exportAsCSV(File txtFile) {

        csvFile = new File("./src/main/resources/csvFile");

        try (BufferedReader br = new BufferedReader(new FileReader(txtFile));
             CSVWriter writer = new CSVWriter(new FileWriter(csvFile));) {

            String line = br.readLine();
            while (line != null) {

                String[] nextLine = line.trim().split(",");
                writer.writeNext(nextLine,false);
                line = br.readLine();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return csvFile;
    }
}
