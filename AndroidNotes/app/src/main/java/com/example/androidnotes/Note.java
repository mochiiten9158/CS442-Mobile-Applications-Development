package com.example.androidnotes;

import java.io.Serializable;

public class Note implements Serializable {

    private String SaveDate;
    private String noteTitle;
    private String noteText;
    private String trimText;
    private String trimTitle;

    public Note(String t, String n, String l){
        this.noteTitle = t;
        this.noteText = n;
        this.SaveDate = l;

        if(n.length() > 80){
            trimText = noteText.substring(0, 79) + "...";
        }
        else {
            trimText = noteText;
        }

        if(t.length() > 80){
            trimTitle = noteTitle.substring(0, 79) + "...";
        }
        else {
            trimTitle = noteTitle;
        }
    }

    public String getLastSaveDate() {
        return SaveDate;
    }

    public String getTitle() {
        return noteTitle;
    }

    public void setTitle(String title_text){
        noteTitle = title_text;
    }

    public String getBody() {
        return noteText;
    }

    public void setBody(String body_text){
        noteText = body_text;
    }

    public String getTrimText() {
        return this.trimText;
    }

    public String getTrimTitle() {return trimTitle; }

    public void setDate(String d){SaveDate = d;}
}