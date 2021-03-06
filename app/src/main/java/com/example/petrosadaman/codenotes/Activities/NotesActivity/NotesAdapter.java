package com.example.petrosadaman.codenotes.Activities.NotesActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<NoteModel> noteList = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public void setItems(Collection<NoteModel> notes) {
        noteList.addAll(notes);
        notifyDataSetChanged();
    }

    public void addItem(NoteModel note) {
        noteList.add(note);
        notifyDataSetChanged();
    }

    public void clearItems() {
        noteList.clear();
        notifyDataSetChanged();
    }

    public void setItemClickListener(final OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int position) {
        noteViewHolder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public String getBody(int index) {
        return noteList.get(index).getBody();
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.tv_item);
        }

        void bind(NoteModel note) {
            textView.setText(note.getTitle());
            textView.setVisibility(note.getTitle() != null ? View.VISIBLE : View.GONE);
        }


        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
