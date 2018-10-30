package com.example.petrosadaman.codenotes.NotesActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petrosadaman.codenotes.R;

public class NoteListFragment extends Fragment {

    public NoteListFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ConstraintLayout noteList = (ConstraintLayout) inflater.inflate(R.layout.notelist, container, false);
        return noteList;
    }
}
