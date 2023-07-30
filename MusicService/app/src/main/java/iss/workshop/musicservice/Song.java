package iss.workshop.musicservice;

import java.io.Serializable;

public class Song implements Serializable {
    private final String title;
    private final String desc;
    private final String filename;

    public Song(String title, String desc, String filename){
        this.title = title;
        this.desc = desc;
        this.filename = filename;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDesc(){
        return this.desc;
    }

    public String getFilename(){
        return this.filename;
    }
}
