package com.example.civiladvocacyapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OfficialAdapter extends RecyclerView.Adapter<OfficialViewHolder> {

    public static final String TAG = "OfficialAdapter";
    private List<Official> officialList;
    private MainActivity mainActivity;


    public OfficialAdapter(List<Official> offList, MainActivity ma){
        this.officialList = offList;
        mainActivity = ma;
    }

    @Override
    public OfficialViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        Log.d(TAG, "onCreateViewHolder: Making New");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.official_list_row2, parent, false);

        itemView.setOnClickListener(mainActivity);

        return new OfficialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfficialViewHolder holder, int position){
        Official official = officialList.get(position);
        holder.name.setText(String.format(official.getName() + " (" + official.getParty()+ ")"));
        holder.office.setText(official.getOffice());
        if(!official.getPhotoUrl().equals("missing")){
            Picasso.get().load(official.getPhotoUrl()).error(R.drawable.brokenimage).into(holder.i);
        } else {
            holder.i.setImageResource(R.drawable.missing);
        }
    }

    @Override
    public int getItemCount(){ return officialList.size();}
}
