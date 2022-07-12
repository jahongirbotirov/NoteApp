package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "Position"+position, Toast.LENGTH_SHORT).show();
//                notes.remove(position);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {
                removeNote(position,adapter);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeNote(viewHolder.getAdapterPosition(),adapter);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    public void removeNote(int position,NotesAdapter adapter){
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }
}