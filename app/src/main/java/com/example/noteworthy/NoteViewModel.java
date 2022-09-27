package com.example.noteworthy;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends ViewModel {

    private MutableLiveData<List<Note>> notepad;
    private MutableLiveData<List<Note>> favnotepad;
    private RespositoryDB noteDBlink;

    public void initialise(Context context){
        if(notepad!=null){
            return;
        }

        noteDBlink = new RespositoryDB(context);
        notepad = noteDBlink.sentNotes();
        favnotepad = noteDBlink.sentfavNotes();

    }

    public LiveData<List<Note>> getNotepad(){

        return notepad;
    }

    public LiveData<List<Note>> getfavNotepad(){

        return favnotepad;
    }

}
