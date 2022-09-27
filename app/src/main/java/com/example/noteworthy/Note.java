package com.example.noteworthy;

import androidx.annotation.Nullable;

public class Note {

    @Nullable
    private String Body;

    @Nullable
    private String Title;

    @Nullable
    private String NoteID;



    @Nullable
    private String Favourite;

    public Note(String noteID,String title,String body,String favourite) {
        Body = body;
        Title = title;
        NoteID = noteID;
        Favourite = favourite;

    }


    public Note(String title,String body,String favourite) {
        Body = body;
        Title = title;
        Favourite = favourite;

    }

    @Nullable
    public String getNoteID() {
        return NoteID;
    }

    public void setNoteID(@Nullable String noteID) {
        NoteID = noteID;
    }

    ///result.getText().toString().isEmpty() == true
    @Nullable
    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(@Nullable String favourite) {
        Favourite = favourite;
    }



    @Nullable
    public String getBody() {
        return Body;
    }

    public void setBody(@Nullable String body) {
        Body = body;
    }

    @Nullable
    public String getTitle() {
        return Title;

    }

    public void setTitle(@Nullable String title) {
        Title = title;

    }


}
