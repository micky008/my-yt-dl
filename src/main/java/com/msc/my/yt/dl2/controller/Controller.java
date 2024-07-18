package com.msc.my.yt.dl2.controller;

import com.msc.my.yt.dl2.YtDLP.Format;
import com.msc.my.yt.dl2.YtDLP.YtDLP_Download;
import com.msc.my.yt.dl2.YtDLP.YtDLP_GetInfo;
import com.msc.my.yt.dl2.dto.DownloadFile;
import com.msc.my.yt.dl2.dto.FormatDTO;
import com.msc.my.yt.dl2.dto.PostInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Michael
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Controller {

    @POST
    @Path("infos")
    public List<FormatDTO> getInfo(PostInfo infos) {
        YtDLP_GetInfo g = new YtDLP_GetInfo();
        List<Format> lformats = g.getInfo(infos.link);
        List<FormatDTO> lfdto = new ArrayList<>(lformats.size());
        for (Format f : lformats) {
            lfdto.add(new FormatDTO(f.format_note, f.format_id));
        }
        return lfdto;
    }

    @POST
    @Path("download")
    public Response downloadFile(DownloadFile format) {
        StreamingOutput stream = (OutputStream os) -> {
            YtDLP_Download d = new YtDLP_Download();
            File file = d.Download(format);
            FileInputStream fis = FileUtils.openInputStream(file);
            IOUtils.copy(fis, os);
        };
        return Response.ok(stream).build();
    }
}
