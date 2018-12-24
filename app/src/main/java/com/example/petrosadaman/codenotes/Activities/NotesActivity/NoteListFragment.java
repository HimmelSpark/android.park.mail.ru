package com.example.petrosadaman.codenotes.Activities.NotesActivity;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.NoteApi;

import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class NoteListFragment extends Fragment implements
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private DividerItemDecoration decoration;

    private NotesAdapter notesAdapter;
    private Toolbar toolbar;
    private RecyclerView rv;


    public NoteApi.OnNoteDeleteListener listener = new NoteApi.OnNoteDeleteListener() {
        @Override
        public void onNoteDeleteSuccess(MessageModel message) {
            //TODO что-то сделать
        }

        @Override
        public void onNoteDeleteFailure(Exception e) {
            //TODO что-то сделать
        }
    };
    public NoteApi.OnNoteCreateListener listener1 = new NoteApi.OnNoteCreateListener() {
        @Override
        public void onMessageSuccess(MessageModel message) {

        }

        @Override
        public void onMessageError(Exception error) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notelist, container, false);
        rv = view.findViewById(R.id.rv_notes);
        rv.setAdapter(notesAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        if (rv.getParent() != null) {
            ((ViewGroup)rv.getParent()).removeView(rv);
        }

        decoration = new DividerItemDecoration(rv.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        rv.addItemDecoration(decoration);
        rv.addItemDecoration(new CharacterItemDecoration(5)); //TODO | может пригодиться

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new
                RecyclerItemTouchHelper(
                        0,
                ItemTouchHelper.LEFT,
                this
        );
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);

        return rv;
    }

    public void setNotesAdapter(NotesAdapter notesAdapter) {
        this.notesAdapter = notesAdapter;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public NoteModel getNoteByPosition(int position) {
        return notesAdapter.getItemByPosition(position);
    }

    public RecyclerView getRecyclerView() {
        return rv;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NotesAdapter.NoteViewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            NoteModel removedNote = notesAdapter.getItemByPosition(viewHolder.getAdapterPosition());

            notesAdapter.removeItem(adapterPosition);

            NoteApi noteApi = NoteApi.getInstance();
            noteApi.deleteNote(removedNote, listener);

            Snackbar snackbar = Snackbar
                    .make(((NotesActivity)Objects.requireNonNull(getActivity())).getDrawer(), "note removed", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", (v) -> {
                notesAdapter.restoreItem(removedNote , adapterPosition);
                noteApi.createNote(removedNote, listener1);
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
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
