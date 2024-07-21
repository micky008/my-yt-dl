package com.msc.my.yt.dl2.YtDLP;

import com.msc.my.yt.dl2.Config;
import com.msc.my.yt.dl2.dto.DownloadFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mchinchole
 */
public class YtDLP_Download {

    private byte buf[] = new byte[3000];

    public File Download(DownloadFile df) {
        UUID uuid = UUID.randomUUID();
        String arr[] = {Config.getInstance().getYtp_dlpExe(), "-q", "--progress", "-o", uuid.toString(), "-f", df.format, df.link};
        try {
            ProcessBuilder pb = new ProcessBuilder(arr);
            Process proc1 = pb.start();
            int lu = 0;
            InputStream is = proc1.getInputStream();
            while ((lu = is.read(buf)) != -1) {
                String line = new String(buf, 0, lu);
                System.out.println(parseLine(line));
            }
            proc1.waitFor();
            is.close();
            return new File(uuid);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(YtDLP_Download.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private double parseLine(String line) {
        //[download]   0.0% of   17.53MiB at   40.91KiB/s ETA 07:19
        int pos1 = line.indexOf(']') + 1;
        int pos2 = line.indexOf('%');
        return Double.parseDouble(line.substring(pos1, pos2).trim());
    }

}
