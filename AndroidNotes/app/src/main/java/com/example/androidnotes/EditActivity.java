package com.example.androidnotes;

import java.util.Calendar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements Serializable{
    private EditText t;
    private EditText b;

    public Note note;
    public String getpreviousTitle = "", getpreviousContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        t = findViewById(R.id.noteEditTitle);
        b = findViewById(R.id.noteBody);
        b.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        if(intent.hasExtra("EDIT_NOTE")){
            note = (Note) intent.getSerializableExtra("EDIT_NOTE");
            t.setText(note.getTitle());
            b.setText(note.getBody());
        }

        if(intent.hasExtra("NOTE")){
            getpreviousContent = intent.getStringExtra("NOTE");
            b.setText(getpreviousContent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public static String getPresentTime(){
        Date Cal = Calendar.getInstance().getTime();
        SimpleDateFormat SDF = new SimpleDateFormat("EEE MMM d, h:mm a");
        String SaveDate = SDF.format(Cal).toString();
        return SaveDate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.save) {
            String title = t.getText().toString().trim();

            if (title.isEmpty()) {
                setResult(-1, getIntent());
                finish();
                Toast.makeText(this, "Note without a title won't be saved.", Toast.LENGTH_LONG).show();
            }
            else {
                String body = b.getText().toString();
                String date = getPresentTime();

                Note new_note = new Note(title, body, date);

                String key = "NEW_NOTE";

                Intent intent = getIntent();
                if (intent.hasExtra("EDIT_NOTE")) {
                    key = "UPDATE_NOTE";
                }

                Intent data = new Intent();
                data.putExtra(key, new_note);
                if (intent.hasExtra("EDIT_POS")) {
                    int p = intent.getIntExtra("EDIT_POS", 0);
                    data.putExtra("UPDATE_POS", p);
                }

                setResult(RESULT_OK, data);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void doSaveNote(View view){
        String title = t.getText().toString().trim();
        String body = b.getText().toString().trim();
        String date = getPresentTime();

        Note note = new Note(title, body, date);

        String key = "NEW_EMPLOYEE";

        Intent i = getIntent();
        if (i.hasExtra("EDIT_EMPLOYEE")) {
            key = "UPDATE_EMPLOYEE";
        }

        Intent data = new Intent();
        data.putExtra(key, note);
        if (i.hasExtra("EDIT_POS")) {
            int pos = i.getIntExtra("EDIT_POS", 0);
            data.putExtra("UPDATE_POS", pos);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed(){
        String title = t.getText().toString().trim();
        String body = b.getText().toString().trim();

        if(title.isEmpty()){
            //Intent toMain = new Intent();
            //setResult(-1, toMain);
            Toast.makeText(this, "Untitled Note wasn't saved.", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
            //finish();
        }

        else if(getpreviousTitle.equals(t.getText().toString()) && getpreviousContent.equals(b.getText().toString())){
            super.onBackPressed();
        }

        /*if (note != null) {
            if (note.getTitle().equals(title) && note.getBody().equals(body)) {
                super.onBackPressed();
            }
        }*/

        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doSaveNote(null);
                }

            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditActivity.super.onBackPressed();
                }
            });

            builder.setTitle("Unsaved Changes");
            builder.setMessage("Do you want to save your changes before exiting?");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void saveNote(){
        Note newNote = new Note(t.getText().toString(), b.getText().toString(), getPresentTime());
        /*newNote.setTitle(t.getText().toString());
        newNote.setBody(b.getText().toString());
        String d = getPresentTime();
        newNote.setDate(d);*/
        Intent resultIntent = new Intent();
        resultIntent.putExtra("EDIT_NOTE", newNote);
        if(t.getText().toString().isEmpty()) {
            resultIntent.putExtra("STATUS", "NO_CHANGE");
            Toast.makeText(this, "Empty Note Cannot Be Saved", Toast.LENGTH_SHORT).show();
        }
        else if(getpreviousTitle.isEmpty() && getpreviousContent.isEmpty())
            resultIntent.putExtra("STATUS", "NEW");
        else if(getpreviousTitle.equals(t.getText().toString()) && getpreviousContent.equals(b.getText().toString()))
            resultIntent.putExtra("STATUS", "NO_CHANGE");
        else
            resultIntent.putExtra("STATUS", "CHANGE");
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}