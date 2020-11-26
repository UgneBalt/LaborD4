package org.ugne.labord4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ugne.labord4.database.Note;

import java.util.List;

import static org.ugne.labord4.MainActivity.db;

public class AddNoteActivity extends AppCompatActivity {

    private Button saveNote;
    private TextView inputTitle;
    private TextView inputContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        saveNote = findViewById(R.id.saveNote);
        inputTitle = findViewById(R.id.title);
        inputContent = findViewById(R.id.content);

        saveNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Note newNote = new Note();
                newNote.title = inputTitle.getText().toString();
                newNote.content = inputContent.getText().toString();

                saveNote(newNote);
            }
        });

    }

    private void saveNote(final Note n) {
        new AsyncTask<Void, Void, Void>()
        {

            @Override
            protected Void doInBackground(Void... voids) {
                db.noteDao().insertAll(n);
                return null;
            }
            @Override
            protected void onPostExecute(Void v)
            {
                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
}
