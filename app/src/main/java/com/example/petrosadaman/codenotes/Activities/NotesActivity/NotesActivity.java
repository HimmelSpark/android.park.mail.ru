package com.example.petrosadaman.codenotes.Activities.NotesActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.petrosadaman.codenotes.Activities.LogRegActivity.RegistrationFragment;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.NoteApi;
import com.example.petrosadaman.codenotes.Web.UserApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        NotesAdapter.OnItemClickListener,
        RegistrationFragment.OnFragmentInteractionListener {

    private NotesAdapter notesAdapter;
    private List<Note> noteList;
    private ListenerHandler<NoteApi.OnNoteGetListener> nodeHandler;
    private FloatingActionButton fab;

    public NoteApi.OnNoteGetListener listener = new NoteApi.OnNoteGetListener() {
        @Override
        public void onNoteSuccess(List<NoteModel> notes) {
            notesAdapter.setItems(notes);
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();

            NoteListFragment list = new NoteListFragment();
            list.setNotesAdapter(notesAdapter);
            transaction.replace(R.id.list_container, list);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                transaction.replace(R.id.note_container,new SingleNoteFragment());
            }
            transaction.commit();
        }

        @Override
        public void onNoteError(Exception error) {

        }
    };
    public NoteApi.OnNoteCreateListener onNoteCreateListener = new NoteApi.OnNoteCreateListener() {
        @Override
        public void onMessageSuccess(MessageModel message) {
            switch (message.getMessage()) {
                case "SUCCESSFULLY_ADDED": {
                    //TODO | создать заметку
                    //TODO | добавить в ресайклер
                    //TODO | перейти к редактированию заметки

                }
            }
        }

        @Override
        public void onMessageError(Exception error) {
            //TODO | тут уже прочекать ошибку
            //TODO | может быть создать локально, записать в бд
            //TODO | в бд к заметке выставить флаг Deferred
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //TODO | пока создаём с одним и тем же названием
            NoteModel note = new NoteModel();
        });

        notesAdapter = new NotesAdapter();

        nodeHandler = NoteApi.getInstance().fetchNotes(listener);
        notesAdapter.setItemClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("body", notesAdapter.getBody(position));
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        SingleNoteFragment noteFragment = new SingleNoteFragment();
        noteFragment.setArguments(bundle);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.list_container, noteFragment);
        }
        else {
            transaction.replace(R.id.note_container,noteFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
