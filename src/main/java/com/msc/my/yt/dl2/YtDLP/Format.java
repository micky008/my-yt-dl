package com.msc.my.yt.dl2.YtDLP;

import java.util.Objects;

/**
 *
 * @author mchinchole
 */
public class Format {

    /**
     * sb3 or 139
     */
    public String format_id; //139
    /**
     * exemple: - low - medium - 720p - low, DRC - 720p60 - null
     */
    public String format_note;
    /**
     * exemple: -mp4 -webm -none
     */
    public String audio_ext;
    /**
     * exemple: -mp4 -webm -none
     */
    public String video_ext;
    /**
     * in byte
     */
    public long filesize;

    public String vcodec;
    public String acodec;

    public String getVideo_ext() {
        return video_ext;
    }

    public void setVideo_ext(String video_ext) {
        this.video_ext = video_ext;
    }

    public String toString() {
        if (audio_ext.equals("none")) {
            return "video=>" + format_id + "-" + format_note + "-" + video_ext + "-" + vcodec;
        }
        return "audio=>" + format_id + "-" + format_note + "-" + audio_ext + "-" + acodec;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.format_note);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Format other = (Format) obj;
        return Objects.equals(this.format_note, other.format_note);
    }

    
    
}
