package org.ugne.labord4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.ugne.labord4.database.AppDatabase;
import org.ugne.labord4.database.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase db;

    ListView listViewId;
    private Spinner optionsSpinner;
    ArrayAdapter<Note> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "notes-db").build();

        listViewId = findViewById(R.id.listViewId);
        optionsSpinner = findViewById(R.id.optionsSpinner);

        arrayAdapter = new ArrayAdapter<Note>(
            this,
            android.R.layout.simple_list_item_1
        );

        listViewId.setAdapter(arrayAdapter);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                int itemCountIndex = optionsSpinner.getSelectedItemPosition();
                if (itemCountIndex == 1) {
                    Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                    startActivity(intent);
                } if (itemCountIndex == 2) {
                    Intent intent = new Intent(MainActivity.this, DeleteNoteActivity.class);
                    //intent.putExtra("animal", message);
                    startActivity(intent);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        loadAllTodos(); //kreipiasi i duombaze ir sudeda irasus i array adapteri

    }

    private void loadAllTodos() {
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

}