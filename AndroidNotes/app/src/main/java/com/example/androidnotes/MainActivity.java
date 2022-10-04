/* Shambhawi Sharma
A20459117
CS442 Assignment 2: Android Notes
10/4/2022
*/

package com.example.androidnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final ArrayList <Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private ActivityResultLauncher <Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        noteAdapter = new NoteAdapter(noteList,this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),this::getNoteData);

        this.loadJSONFile();
        if(noteList.size() > 0){
            getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
        }
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        this.writeJSONFile();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.addNote){
            Intent intent = new Intent(this, EditActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void writeJSONFile() {
        try{
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.noteFile), Context.MODE_PRIVATE);

            JsonWriter jw = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            jw.setIndent("  ");
            jw.beginArray();

            for(int i = 0; i < noteList.size(); i++){
                jw.beginObject();
                jw.name("SaveDate").value(noteList.get(i).getLastSaveDate());
                jw.name("noteTitle").value(noteList.get(i).getTitle());
                jw.name("noteText").value(noteList.get(i).getBody());
                jw.endObject();
            }
            jw.endArray();
            jw.close();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadJSONFile() {
        try{
            InputStream is = getApplicationContext().openFileInput(getString(R.string.noteFile));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(is, getString(R.string.encoding)));

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = stdin.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject noteObject = jsonArray.getJSONObject(i);
                String lastSaveDate = noteObject.getString("SaveDate");
                String noteTitle = noteObject.getString("noteTitle");
                String noteText = noteObject.getString("noteText");
                noteList.add(new Note(noteTitle, noteText, lastSaveDate));
            }
            noteAdapter.notifyDataSetChanged();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int p = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(p);

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("EDIT_NOTE", note);
        intent.putExtra("EDIT_POS", p);

        activityResultLauncher.launch(intent);
        noteAdapter.notifyDataSetChanged();

        /*intent.putExtra("TITLE", note.getTitle());
        intent.putExtra("NOTETEXT", note.getBody());

        activityResultLauncher.launch(intent);
        noteAdapter.notifyDataSetChanged();*/
    }

    @Override
    public boolean onLongClick(View view) {
        int p = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(p);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("YES", (dialogInterface, i) -> {
            noteList.remove(p);
            noteAdapter.notifyItemRemoved(p);
            getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
        });

        builder.setNegativeButton("NO", (dialogInterface, i) -> {
        });

        builder.setTitle("Delete Note?");
        builder.setMessage("Do you want to delete '" + note.getTitle() + "'?");
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You pressed the back button", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    public static String getPresentTime(){
        Date Cal = Calendar.getInstance().getTime();
        SimpleDateFormat SDF = new SimpleDateFormat("EEE MMM d, h:mm a");
        String SaveDate = SDF.format(Cal).toString();
        return SaveDate;
    }

    public void getNoteData(ActivityResult activityResult) {
        if (activityResult.getResultCode() == RESULT_OK) {
            Intent data = activityResult.getData();
            if (data == null)
                return;

            if (data.hasExtra("NEW_NOTE")) {
                Note newNote = (Note) data.getSerializableExtra("NEW_NOTE");
                noteList.add(newNote);
                getSupportActionBar().setTitle(getString(R.string.app_name) + "(" + noteList.size() + ")");
                noteAdapter.notifyItemInserted(noteList.size());
            }

            else if (data.hasExtra("UPDATE_NOTE")) {
                Note editNote = (Note) data.getSerializableExtra("UPDATE_NOTE");
                int p = data.getIntExtra("UPDATE_POS", 0);

                Note toUpdate = noteList.get(p);
                toUpdate.setTitle(editNote.getTitle());
                toUpdate.setBody(editNote.getBody());
                noteAdapter.notifyItemChanged(p);
            }
        }
    }
}