package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        if (notes.isEmpty()){
            notes.add(new Note("Parikmaxer","Sdelat prichesku","Monday",2));
            notes.add(new Note("Basketball","Playing at school","tuesday",3));
            notes.add(new Note("Football","Playing on the ground","Monday",3));
            notes.add(new Note("Stomatolog","Zubi","Wednesday",2));
            notes.add(new Note("Parikmaxer","Sdelat prichesku","Monday",1));
            notes.add(new Note("Shop","Shoping","Thursday",3));
            notes.add(new Note("car","driving","Friday",1));
            notes.add(new Note("Parikmaxer","Sdelat prichesku","Monday",2));
        }

        NotesAdapter adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }
}