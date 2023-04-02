
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientLogLesson {

    private List<String[]> log = new ArrayList<>();
    public void log(int productNum, int amount){

        log.add(new String[] {"" + productNum, "" + amount});
    }

    public void exportAsCsv(File txtFile) {
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
