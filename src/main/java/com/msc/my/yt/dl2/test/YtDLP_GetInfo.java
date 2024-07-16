package com.msc.my.yt.dl2.test;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author mchinchole
 */
public class YtDLP_GetInfo implements Runnable {

    @Override
    public void run() {
        String arr[] = {"/home/mchinchole/yt-dlp/yt-dlp", "-sJ", "https://www.youtube.com/watch?v=ji5-_w9QKqo"};
        try {
            ProcessBuilder pb = new ProcessBuilder(arr);
            Process proc1 = pb.start();
            StringWriter sw;
            try (InputStream is = proc1.getInputStream()) {
                sw = new StringWriter();
                IOUtils.copy(is, sw, Charset.forName("UTF-8"));
                proc1.waitFor();
            }
            Gson gson = new Gson();
            YtDLP ytdlp = gson.fromJson(sw.toString(), YtDLP.class);
            List<Format> formats = ytdlp.formats.stream().filter(format -> format.format_id.matches("[0-9]+")).filter(format -> format.format_note != null).toList();
            Format audio = formats.stream().filter(f -> f.audio_ext.contentEquals("webm") && f.format_note.contains("medium")).findFirst().get();
            Format video = formats.stream().filter(f -> f.video_ext.contentEquals("webm") && f.format_note.contains("1080p")).findFirst().get();
            System.out.println(audio.format_id + "+" + video.format_id + " " + (audio.filesize + video.filesize));
        } catch (IOException ex) {
            Logger.getLogger(YtDLP_GetInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(YtDLP_GetInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
