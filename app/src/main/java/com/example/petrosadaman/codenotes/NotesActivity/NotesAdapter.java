package com.example.petrosadaman.codenotes.NotesActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petrosadaman.codenotes.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> noteList = new ArrayList<>();

    public void setItems(Collection<Note> notes) {
        noteList.addAll(notes);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        noteList.add(note);
        notifyDataSetChanged();
    }

    public void clearItems() {
        noteList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);
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

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.note);
        }

        void bind(Note note) {
            textView.setText(note.getData());
            textView.setVisibility(note.getData() != null ? View.VISIBLE : View.GONE);
        }
    }
}
