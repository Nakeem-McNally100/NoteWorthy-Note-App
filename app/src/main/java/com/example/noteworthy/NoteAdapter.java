package com.example.noteworthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {



    List<Note> data;
    int selectedItem = 0;
    Context context;

    OnNoteclick onNoteclick;

    public interface OnNoteclick{
        void onClick(int pos);
    }


    public NoteAdapter(List<Note>data, Context context, OnNoteclick onNoteclick){
        this.context = context;
        this.onNoteclick = onNoteclick;
        this.data = data;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.noteasset,parent, false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.body.setText(data.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class NoteHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView body;
        CardView cardView;
        LinearLayout linearLayout;

        public NoteHolder(View holder){
            super(holder);

            title = holder.findViewById(R.id.notetitle);
            body = holder.findViewById(R.id.notebody);
            cardView = holder.findViewById(R.id.noteCard);
          //  linearLayout = holder.findViewById(R.id.linearlayoutback);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    if(onNoteclick != null){
                        onNoteclick.onClick(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }
            });

        }


    }



}
