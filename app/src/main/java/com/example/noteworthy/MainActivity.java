package com.example.noteworthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FloatingActionButton addnote;
    private RecyclerView noteRecycler;
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;
    private List<Note> notesArray, favnotesArray;
    private AdView homeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = findViewById(R.id.toptoolbar);
            setSupportActionBar(toolbar);
            drawer =findViewById(R.id.maindrawer);
            addnote = findViewById(R.id.addnote);
            noteRecycler = findViewById(R.id.notesRecycler);
            homeAd = findViewById(R.id.homescreenAd);

            //ca-app-pub-3940256099942544/6300978111

            NavigationView navigationView = findViewById(R.id.navView);
            navigationView.setNavigationItemSelectedListener(this);

            //MobileAds.initialize(this);






            noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
            noteViewModel.initialise(MainActivity.this);
            noteViewModel.getNotepad().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    //triggered when a change is made ot our live data object

                    noteAdapter.notifyDataSetChanged();
                }
            });

            notesArray = noteViewModel.getNotepad().getValue();
            favnotesArray = noteViewModel.getfavNotepad().getValue();

            noteAdapter = new NoteAdapter(notesArray, this, new NoteAdapter.OnNoteclick() {
                @Override
                public void onClick(int pos) {
                    Intent openNote =  new Intent(MainActivity.this,NoteEditor.class);
                    openNote.putExtra("NoteID",noteViewModel.getNotepad().getValue().get(pos).getNoteID());
                    startActivity(openNote);

                }
            });


            RecyclerView.LayoutManager gridlayout = new GridLayoutManager(MainActivity.this,2);
            noteRecycler.setLayoutManager(gridlayout);
            noteRecycler.setAdapter(noteAdapter);

            addnote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newNote =  new Intent(MainActivity.this,NoteEditor.class);
                    newNote.putExtra("NoteID","");
                    startActivity(newNote);
                }
            });



            /*
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return false;
                }
            });
            */


            ActionBarDrawerToggle togglesettings = new ActionBarDrawerToggle(this, drawer,toolbar,
                    R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawer.addDrawerListener(togglesettings);
            togglesettings.syncState();
            if(savedInstanceState == null) {
                navigationView.setCheckedItem(R.id.nav_notefiles);
            }




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        homeAd.loadAd(adRequest);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_notefiles:

                /*
                Intent reload =  new Intent(MainActivity.this,MainActivity.class);
                finish();
                overridePendingTransition(0,0);

                startActivity(reload);
                overridePendingTransition(0,0);
                */


                noteAdapter = new NoteAdapter(notesArray, this, new NoteAdapter.OnNoteclick() {
                    @Override
                    public void onClick(int pos) {
                        Intent openNote =  new Intent(MainActivity.this,NoteEditor.class);
                        openNote.putExtra("NoteID",noteViewModel.getNotepad().getValue().get(pos).getNoteID());
                        startActivity(openNote);

                    }
                });


                RecyclerView.LayoutManager gridlayout = new GridLayoutManager(MainActivity.this,2);
                noteRecycler.setLayoutManager(gridlayout);
                noteRecycler.setAdapter(noteAdapter);

                Toast.makeText(this, "All Notes", Toast.LENGTH_SHORT).show();

                break;

            case R.id.nav_favourites:

                noteAdapter = new NoteAdapter(favnotesArray, this, new NoteAdapter.OnNoteclick() {
                    @Override
                    public void onClick(int pos) {
                        Intent openNote =  new Intent(MainActivity.this,NoteEditor.class);
                        openNote.putExtra("NoteID",noteViewModel.getNotepad().getValue().get(pos).getNoteID());
                        startActivity(openNote);

                    }
                });


                RecyclerView.LayoutManager gridlayout2 = new GridLayoutManager(MainActivity.this,2);
                noteRecycler.setLayoutManager(gridlayout2);
                noteRecycler.setAdapter(noteAdapter);

                Toast.makeText(this, "Favourite Notes", Toast.LENGTH_SHORT).show();

                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private long backpresstime;
    private Toast toast;

    public void onBackPressed(){

        super.onBackPressed();
        MainActivity.this.finish();
        System.exit(0);
        /* if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            System.exit(0);
        }

        if(backpresstime+2000> System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            System.exit(0);
        }else{
            toast =  Toast.makeText(getBaseContext(), "Press Back again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }
        backpresstime = System.currentTimeMillis();
        */
    }


    /*
    * @Override
    public void onBackPressed() {
            super.onBackPressed();
            System.exit(0);
    }
    *
    * */

}