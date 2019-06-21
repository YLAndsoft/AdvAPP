package sdk.adv.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author: FYL
 * @time: 2019/3/29
 * @email:347933430@qq.com
 * @describe: config
 */
public class FileTools {
    public static String getDataFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static void saveToFile(String str,File newWidgetPath) {
        BufferedReader br = null;
        BufferedWriter w = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        try {
            if(!newWidgetPath.exists()){
                newWidgetPath.createNewFile();
            }
            outputStream = new FileOutputStream(newWidgetPath);
            streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            w = new BufferedWriter(streamWriter);
            w.write(str);// + System.lineSeparator()
            w.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (w != null) {
                    w.close();
                }
                if (streamWriter != null) {
                    streamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
