package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    String dur[];

    public ProgramAdapter(Context context, String dur[]) {
        super(context, R.layout.row,R.id.st,dur);
        this.context = context;
        this.dur = dur;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent)
    {
        View row = convertView;
        ProgramViewHolder holder =null;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= layoutInflater.inflate(R.layout.row,parent,false);
            holder = new ProgramViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (ProgramViewHolder) row.getTag();
        }

        holder.txt.setText(dur[position]);
        return row;
    }
}
