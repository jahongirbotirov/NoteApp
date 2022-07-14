package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private NotesAdapter adapter;
    private final ArrayList<Note> notes = new ArrayList<>();

    private NotesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        database.delete(NotesContract.NotesEntry.TABLE_NAME,null,null);
//        if (notes.isEmpty()){
//            notes.add(new Note("Parikmaxer","Sdelat prichesku","Monday",2));
//            notes.add(new Note("Basketball","Playing at school","tuesday",3));
//            notes.add(new Note("Football","Playing on the ground","Monday",3));
//            notes.add(new Note("Stomatolog","Zubi","Wednesday",2));
//            notes.add(new Note("Parikmaxer","Sdelat prichesku","Monday",1));
//            notes.add(new Note("Shop","Shoping","Thursday",3));
//            notes.add(new Note("car","driving","Friday",1));
//            notes.add(new Note("Parikmaxer","Sdelat prichesku","Monday",2));
//        }
//        for (Note note: notes){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE,note.getTitle());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION,note.getDescription());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK,note.getDayOfWeek());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY,note.getPriority());
//            database.insert(NotesContract.NotesEntry.TABLE_NAME,null,contentValues);
//        }

//        ArrayList<Note> notesFromDB = new ArrayList<>();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
           @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
           @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
           @SuppressLint("Range") String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
           @SuppressLint("Range") int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
           Note note = new Note(title,description,dayOfWeek,priority);
           notes.add(note);
//           notesFromDB.add(note);
        }
        cursor.close();
        adapter = new NotesAdapter(notes);

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
                removeNote(position);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeNote(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    public void removeNote(int position){
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }
}