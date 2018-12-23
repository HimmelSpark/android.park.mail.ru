package com.example.petrosadaman.codenotes.Activities.NotesActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.petrosadaman.codenotes.R;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class NoteListFragment extends Fragment {

    private DividerItemDecoration decoration;

    private NotesAdapter notesAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notelist, container, false);
        RecyclerView rv = view.findViewById(R.id.rv_notes);
        rv.setAdapter(notesAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        FancyBehavior fancyBehavior = new FancyBehavior();
//        layoutManager.generateLayoutParams(fancyBehavior);
        rv.setLayoutManager(layoutManager);

        if (rv.getParent() != null) {
            ((ViewGroup)rv.getParent()).removeView(rv);
        }

        decoration = new DividerItemDecoration(rv.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        rv.addItemDecoration(decoration);
        rv.addItemDecoration(new CharacterItemDecoration(5)); //TODO | может пригодиться

        return rv;
    }

    public void setNotesAdapter(NotesAdapter notesAdapter) {
        this.notesAdapter = notesAdapter;
    }

    private class CharacterItemDecoration extends RecyclerView.ItemDecoration {

        private int offset;

        CharacterItemDecoration(int offset) {
            this.offset = offset;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = offset;
        }
    }
}
