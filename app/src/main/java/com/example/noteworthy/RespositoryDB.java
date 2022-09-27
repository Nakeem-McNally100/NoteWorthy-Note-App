package com.example.noteworthy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;


public class RespositoryDB extends SQLiteOpenHelper{


    public RespositoryDB(@Nullable Context context) {
        super(context, "NoteStorageDB.db", null, 1);

    }
/*
    public RespositoryDB(NoteViewModel noteViewModel) {
        super(No.this,"NoteStorageDB.db", null, 1);
    }
*/

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE NoteStoragetable(noteID INTEGER primary key " +
                "autoincrement,title TEXT, notebody TEXT,favourite INTEGER,favouritecode TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists NoteStoragetable");
    }


    public boolean addNote(Note note){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", note.getTitle());
        cv.put("notebody", note.getBody());
        cv.put("favouritecode", note.getFavourite());

        long insert = db.insert("NoteStoragetable", null, cv);
        if(insert ==-1){
            return false;

        }else{
            return true;
        }

    }



    public void deleteNote(String noteID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("NoteStoragetable", "noteID=?", new String[]{noteID});
        db.close();

    }


    public void deleteNote(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM NoteStoragetable");
        db.close();

    }


    public void updateNote(String noteid, String title, String body){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("notebody",body);

        db.update("NoteStoragetable", contentValues,
                "noteID=?", new String[]{noteid});
        db.close();
    }


    public void updateFavourite(String noteid, String favourite){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("favouritecode",favourite);

        db.update("NoteStoragetable", contentValues,
                "noteID=?", new String[]{noteid});
        db.close();
    }



    public void alterCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("ALTER TABLE NoteStoragetable ADD favouritecode TEXT");
        db.close();
    }

    public List<Note> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String[] sqlSelect = {"noteID","title","notebody", "favouritecode"};
        String sqltable = "NoteStoragetable";

        queryBuilder.setTables(sqltable);
        Cursor cursor = queryBuilder.query(db,sqlSelect,null, null,null,null,null);

        final List<Note> selectresult = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                selectresult.add(new Note(cursor.getString(cursor.getColumnIndex("noteID")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("notebody")),
                        cursor.getString(cursor.getColumnIndex("favouritecode"))
                        )
                );

            }while(cursor.moveToNext());

        }
        db.close();
        return selectresult;
    }


    public List<Note> getFavNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String[] sqlSelect = {"noteID","title","notebody", "favouritecode"};
        String sqltable = "NoteStoragetable";

        queryBuilder.setTables(sqltable);
        Cursor cursor = queryBuilder.query(db,sqlSelect,"favouritecode=?", new String[]{"true"},null,null,null);

        final List<Note> selectresult = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                selectresult.add(new Note(cursor.getString(cursor.getColumnIndex("noteID")),
                                cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("notebody")),
                                cursor.getString(cursor.getColumnIndex("favouritecode"))
                        )
                );

            }while(cursor.moveToNext());

        }
        db.close();
        return selectresult;
    }

    public Note getmyNote(String noteid){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String[] sqlSelect = {"noteID","title","notebody", "favouritecode"};
        String sqltable = "NoteStoragetable";

        queryBuilder.setTables(sqltable);
        Cursor cursor = queryBuilder.query(db,sqlSelect,"noteID=?", new String[]{noteid},null,null,null);

        final List<Note> selectresult = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                selectresult.add(new Note(cursor.getString(cursor.getColumnIndex("noteID")),
                                cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("notebody")),
                                cursor.getString(cursor.getColumnIndex("favouritecode"))
                        )
                );

            }while(cursor.moveToNext());

        }
        db.close();
        return selectresult.get(0);
    }









    public Note getNote(String noteid){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT noteID, title,notebody, favouritecode FROM NoteStoragetable" +
                " WHERE noteID=?",new String[]{noteid});

        //db.delete("NoteStoragetable", "noteID=?", new String[]{Integer.toString(noteID)});
        //fdb.close();


        /*
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String[] sqlSelect = {"noteID","title","notebody", "favouritecode"};
        String sqltable = "NoteStoragetable";
        queryBuilder.setTables(sqltable);
        Cursor cursor = queryBuilder.query(db,sqlSelect,null, null,null,null,null);
        */

        final List<Note> selectresult = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                selectresult.add(new Note(cursor.getString(cursor.getColumnIndex("noteID")),
                                cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("notebody")),
                                cursor.getString(cursor.getColumnIndex("favouritecode"))
                        )
                );

            }while(cursor.moveToNext());

        }
        db.close();
        return selectresult.get(0);

    }


    public MutableLiveData<List<Note>> sentNotes(){

        MutableLiveData<List<Note>> data = new MutableLiveData<>();
        data.setValue(getNotes());
        return data;
    }

    public MutableLiveData<List<Note>> sentfavNotes(){

        MutableLiveData<List<Note>> data = new MutableLiveData<>();
        data.setValue(getFavNotes());
        return data;

    }


}
