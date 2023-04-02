
import com.opencsv.CSVWriter;
import netscape.javascript.JSObject;
import org.apache.commons.text.Builder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.management.HotSpotDiagnosticMXBean.ThreadDumpFormat.JSON;

public class ClientLog {

    private static File txtFile = new File("./src/main/resources/txtFile");
    private static File csvFile;
    private static FileWriter fw;
    private static JSObject jsonFile;


    static {
        try {
            fw = new FileWriter(txtFile);

            fw.append("productNum,amount\n");
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    static JSONObject obj = new JSONObject();
    static JSONArray productNumList =new JSONArray();
    static JSONArray amountList =new JSONArray();

    private List<String[]> log = new ArrayList<>();



    public static File getTxtFile() {
        return txtFile;
    }

    public static JSObject logJson(int productNum, int amount) {

        String newStr = new String("");
        StringBuilder sb = new StringBuilder("");
        sb.append(productNum);
        sb.append(",");
        sb.append(amount);
        sb.append("\n");
        newStr = sb.toString();

        productNumList.add(productNum);
        amountList.add(amount);

        obj.put("productNum", productNumList);
        obj.put("amount", amountList);


        try (FileWriter file = new FileWriter("log.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFile;
    }


    public void log(int productNum, int amount) {

        log.add(new String[] {"" + productNum, "" + amount});
    }

    public static File jsonExportAsCSV() {
        csvFile = new File("./src/main/resources/csvFile");

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("log.json"));
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));

            JSONObject buyInfo = (JSONObject) obj;

            JSONArray productNum = (JSONArray) buyInfo.get("productNum");
            JSONArray amount = (JSONArray) buyInfo.get("amount");

            writer.writeNext((String[]) productNum.stream().toArray());

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    return csvFile;
    }

    public void exportAsCSV(File txtFile) {
        if (!txtFile.exists()) {
            log.add(0,new String[] {"productNum,amount"});
        }
        try(CSVWriter writer = new CSVWriter(new FileWriter(txtFile,true))) {
            writer.writeAll(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
