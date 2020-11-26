package org.ugne.labord4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.ugne.labord4.database.Note;

import java.util.List;

import static org.ugne.labord4.MainActivity.db;

public class DeleteNoteActivity extends AppCompatActivity {

    private Spinner notesSpinner;
    ArrayAdapter<Note> arrayAdapter;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        notesSpinner = findViewById(R.id.notesSpinner);
        delete = findViewById(R.id.delete);

        arrayAdapter = new ArrayAdapter<Note>(
                this,
                android.R.layout.simple_list_item_1//,
                //db.noteDao().getAll()
        );

        notesSpinner.setAdapter(arrayAdapter); //surisame notesSpinner su arrayAdapter

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int itemCountIndex = notesSpinner.getSelectedItemPosition();
                Note n = arrayAdapter.getItem(itemCountIndex);
                deleteNote(n);
            }
        });

        loadAllTodos();
    }

    private void loadAllTodos() { //ikelti visas zinutes
        new AsyncTask<String, Void, List<Note>>() {
            @Override
            protected List<Note> doInBackground(String... params) {
                return db.noteDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Note> todoList) {
                arrayAdapter.addAll(todoList);
            }
        }.execute();
    }

    private void deleteNote(Note n) { //istrinti irasa
        new AsyncTask<Note, Void, Void>() {
            @Override
            protected Void doInBackground(Note... notes) {
                db.noteDao().delete(notes[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                Intent intent = new Intent(DeleteNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.execute(n);
    }
}