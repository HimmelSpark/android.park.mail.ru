package com.example.petrosadaman.codenotes.NotesActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petrosadaman.codenotes.R;

public class SingleNoteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_note, container, false);
        if (view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        return view;
    }

    //TODO прописать установку данных и заливку при аттаче
}
