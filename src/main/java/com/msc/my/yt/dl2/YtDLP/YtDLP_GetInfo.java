package com.msc.my.yt.dl2.YtDLP;

import com.google.gson.Gson;
import com.msc.my.yt.dl2.Config;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.groupingBy;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author mchinchole
 */
public class YtDLP_GetInfo {

    public List<Format> getInfo(String link) {
        String arr[] = {Config.getInstance().getYtp_dlpExe(), "-sJ", link};
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

            List<Format> lf = ytdlp.formats.stream().filter(format -> format.format_id.matches("[0-9]+") && format.format_note != null).toList();
            return sort(lf);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(YtDLP_GetInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>(0);
    }

    private List<Format> sort(List<Format> rawListFormat) {

        List<Format> sonsOnly = new ArrayList(rawListFormat.parallelStream().filter(f -> f.video_ext.contains("none") && f.format_note.contains("medium")).toList());
       Map<String, List<Format>> videosSplit = rawListFormat.parallelStream().filter(f -> f.audio_ext.contains("none")).collect(groupingBy(Format::getVideo_ext));

        sonsOnly.sort((t, t1) -> {
            int a = getSoundExtPoids(t.audio_ext);
            int b = getSoundExtPoids(t1.audio_ext);
            if (a > b) {
                return -1;
            }
            if (a < b) {
                return 1;
            }
            return 0;
        });
        Format sonOnly = sonsOnly.get(0);

        List<Format> lfres = new ArrayList<>();
        for (Entry<String, List<Format>> kv : videosSplit.entrySet()) {
            kv.getValue().forEach(f -> {
                if (f.format_note.contains("Premium")) {
                    return;
                }
                if (!lfres.contains(f)) {
                    lfres.add(f);
                }
            });
        }
        List<Format> res = new ArrayList<>(lfres.size() + 1);
        res.add(sonOnly);
        res.addAll(lfres);
        return res;
    }

    //aext: Audio Extension (m4a > aac > mp3 > ogg > opus > webm > other)
    private int getSoundExtPoids(String a) {
        if (a.contains("m4a")) {
            return 6;
        }
        if (a.contains("aac")) {
            return 5;
        }
        if (a.contains("mp3")) {
            return 4;
        }
        if (a.contains("ogg")) {
            return 3;
        }
        if (a.contains("opus")) {
            return 2;
        }
        if (a.contains("webm")) {
            return 1;
        }
        return 0;
    }

}
