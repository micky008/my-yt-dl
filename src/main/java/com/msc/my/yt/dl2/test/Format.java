/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msc.my.yt.dl2.test;

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
     * exemple:
     * - low
     * - medium 
     * - 720p 
     * - low, DRC
     * - 720p60
     * - null
     */
    public String format_note;
    /**
     * exemple:
     * -mp4
     * -webm
     * -none
     */
    public String audio_ext;
    /**
     * exemple:
     * -mp4
     * -webm
     * -none
     */
    public String video_ext;
    /**
     * in byte
     */
    public long filesize;
}
