package com.example.androidnotes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public TextView lastSaveDate;
    public TextView noteTitle;
    public TextView content;
    public TextView trimText;
    public TextView trimTitle;

    public NoteViewHolder(View v) {
        super(v);
        lastSaveDate = v.findViewById(R.id.lastSaveDate);
        content = v.findViewById(R.id.textContents);
        noteTitle = v.findViewById(R.id.noteTitle);
        trimText = v.findViewById(R.id.textContents);
        trimTitle = v.findViewById(R.id.noteTitle);
    }
}
