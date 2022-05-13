import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sun.org.apache.bcel.internal.classfile.SourceFile;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Downloader {


    public static void downloadJavaScriptLibrary(String libraryName, String version, String downloadPath) {

        String apiURL = String.format("https://api.cdnjs.com/libraries/%s/%s", libraryName, version);
        String json = HttpRequest.get(apiURL).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(json);

        jsonObject.getJSONArray("files").forEach(file -> {
            String downloadURL = String.format("https://cdnjs.cloudflare.com/ajax/libs/%s/%s/%s", libraryName, version, file);
            System.out.printf("Downloading: %s\n", downloadURL);
            String content = HttpRequest.get(downloadURL).execute().body();

            try {
                File downloadPathFile = new File(downloadPath + file);
                downloadPathFile.getParentFile().mkdirs();
                FileWriter fileWriter = new FileWriter(downloadPathFile);
                fileWriter.append(content);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void main(String[] args) {
        downloadJavaScriptLibrary("prism", "1.27.0", "D:\\Temp\\Download\\");
    }
}
