import com.opencsv.CSVWriter;

import java.io.*;

public class ClientLog {

    private static File txtFile = new File("./src/main/resources/txtFile");
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

    public static File log(int productNum, int amount) {

        String newStr = new String("");
        StringBuilder sb = new StringBuilder("");
        sb.append(productNum);
        sb.append(",");
        sb.append(amount);
        newStr = sb.toString();

        try {
            fw.append(newStr).append("\n");
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return txtFile;
    }

    public static File exportAsCSV(File txtFile) {

        try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {

            while (br.readLine() != null) {

                String[] nextLine = br.readLine().split(",");

                try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {

                    writer.writeNext(nextLine);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return txtFile;
    }
}
