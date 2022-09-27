package com.example.noteworthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NoteEditor extends AppCompatActivity {


    ImageView backButton, optionButton;
    EditText title, body;
    String noteID = null;
    Note note;
    private AdView editorAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        backButton = findViewById(R.id.backbtn);
        optionButton = findViewById(R.id.optionbtn);
        title = findViewById(R.id.editTitle);
        body = findViewById(R.id.editBody);
        editorAd = findViewById(R.id.editorscreenAd);

        if (getIntent() != null) {
            if(getIntent().getStringExtra("NoteID").equals("")){

            }else {
                noteID = getIntent().getStringExtra("NoteID");
                RespositoryDB getnote = new RespositoryDB(NoteEditor.this);
                note = getnote.getNote(noteID);
                title.setText(note.getTitle());
                body.setText(note.getBody());
            }



        }

        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(view.getContext(),view);
                //Inflating the Popup using xml fiesle
                popup.getMenuInflater().inflate(R.menu.editor_options, popup.getMenu());
                popup.getMenu().findItem(R.id.save).setVisible(false);
                if(noteID != null) {

                   // RespositoryDB data = new RespositoryDB(NoteEditor.this);
                    // Note currentNote = data.getNote(noteID);

                    if (note.getFavourite().equals("true")) {
                        popup.getMenu().findItem(R.id.favs).setTitle("Remove from Favourites");
                    }

                }else{
                    popup.getMenu().findItem(R.id.delete).setVisible(false);
                    popup.getMenu().findItem(R.id.favs).setVisible(false);
                }
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                            //



                        switch (item.getItemId()) {

                            case R.id.favs:
                                //Intent opencart =  new Intent(Cart.this,Cart.class);
                                //startActivity(opencart);
                                if(noteID != null) {
                                    RespositoryDB data = new RespositoryDB(NoteEditor.this);
                                    Note currentNote = data.getNote(noteID);

                                    if (currentNote.getFavourite().equals("false")) {
                                       data.updateFavourite(noteID, "true");

                                    }else if(currentNote.getFavourite().equals("true")) {
                                        data.updateFavourite(noteID, "false");
                                    }
                                }
                                return true;

                            case R.id.delete:
                                RespositoryDB remove = new RespositoryDB(NoteEditor.this);
                                remove.deleteNote(noteID);
                                Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
                                startActivity(openhome);
                                finish();

                                return true;

                            case R.id.save:
                                if( title.getText().toString().equals("") && body.getText().toString().equals("")){
                                    Toast.makeText(NoteEditor.this, "Empty Note", Toast.LENGTH_SHORT).show();

                                }else {
                                    String Ttext = title.getText().toString();
                                    String btext = body.getText().toString();
                                    FileOutputStream fileOutputStream = null;
                                    try {
                                        fileOutputStream = openFileOutput(Ttext, 0);
                                        fileOutputStream.write(btext.getBytes());
                                        Toast.makeText(NoteEditor.this, "Saved file to "+ getFilesDir() + "/"+Ttext
                                                , Toast.LENGTH_LONG).show();

                                        Intent openhome1 =  new Intent(NoteEditor.this,MainActivity.class);
                                        startActivity(openhome1);
                                        finish();

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                        Intent openhome2 =  new Intent(NoteEditor.this,MainActivity.class);
                                        startActivity(openhome2);
                                        finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Intent openhome3 =  new Intent(NoteEditor.this,MainActivity.class);
                                        startActivity(openhome3);
                                        finish();
                                    }finally {
                                        if(fileOutputStream !=null){
                                            try {
                                                fileOutputStream.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }
                                //Intent opencart =  new Intent(Cart.this,Cart.class);
                                //startActivity(opencart);
                                return true;

                            default:
                                return false;
                        }


                    }
                });
                popup.show();//showing popup menu

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //title.getText().toString().isEmpty() == true &&  body.getText().toString().isEmpty() == true
                if( title.getText().toString().equals("") && body.getText().toString().equals("")){
                    Toast.makeText(NoteEditor.this, "Note is empty", Toast.LENGTH_SHORT).show();
                    Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
                    startActivity(openhome);
                    finish();
                }else if(noteID != null){
                    RespositoryDB submit = new RespositoryDB(NoteEditor.this);
                    submit.updateNote(noteID,title.getText().toString().trim(),body.getText().toString().trim());
                    Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
                    startActivity(openhome);
                    finish();

                }else{
                    RespositoryDB submit = new RespositoryDB(NoteEditor.this);
                    Note note = new Note(title.getText().toString().trim(), body.getText().toString().trim(),"false");
                    submit.addNote(note);
                    Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
                    startActivity(openhome);
                    finish();
                }
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        editorAd.loadAd(adRequest);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if( title.getText().toString().equals("") && body.getText().toString().equals("")){
            Toast.makeText(NoteEditor.this, "Note is empty", Toast.LENGTH_SHORT).show();
            Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
            startActivity(openhome);
            finish();
        }else if(noteID != null){

            RespositoryDB submit = new RespositoryDB(NoteEditor.this);
            submit.updateNote(noteID,title.getText().toString().trim(),body.getText().toString().trim());
            Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
            startActivity(openhome);
            finish();

        }else{
            RespositoryDB submit = new RespositoryDB(NoteEditor.this);
            Note note = new Note(title.getText().toString().trim(), body.getText().toString().trim(),"false");
            submit.addNote(note);
            Intent openhome =  new Intent(NoteEditor.this,MainActivity.class);
            startActivity(openhome);
            finish();
        }

    }


}