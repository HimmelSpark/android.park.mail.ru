package com.example.petrosadaman.codenotes.NotesActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.petrosadaman.codenotes.R;


public class NoteListFragment extends Fragment {

    private NotesAdapter notesAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notelist, container, false);
        RecyclerView rv = view.findViewById(R.id.rv_notes);
        rv.setAdapter(notesAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        if (rv.getParent() != null) {
            ((ViewGroup)rv.getParent()).removeView(rv);
        }
        return rv;
    }

    public void setNotesAdapter(NotesAdapter notesAdapter) {
        this.notesAdapter = notesAdapter;
    }
}
