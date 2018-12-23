package com.example.petrosadaman.codenotes.Activities.NotesActivity;

import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;

import com.example.petrosadaman.codenotes.Activities.LogRegActivity.LogReg;
import com.example.petrosadaman.codenotes.Activities.LogRegActivity.RegistrationFragment;
import com.example.petrosadaman.codenotes.DBManager.DBManager;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.Models.User.UserModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.NoteApi;
import com.example.petrosadaman.codenotes.Web.UserApi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NotesActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        NotesAdapter.OnItemClickListener,
        RegistrationFragment.OnFragmentInteractionListener {

    private NotesAdapter notesAdapter;
    private List<Note> noteList;
    private ListenerHandler<NoteApi.OnNoteGetListener> nodeHandler;
    private ListenerHandler<NoteApi.OnNoteCreateListener> noteCreateHandler;
    private FloatingActionButton fab;

    private Dialog dialog;

    private DBManager db = new DBManager(this);
    private String user;
    private Toolbar toolbar;

    private boolean isEditing = false;
    private int currentEditing = 0;

    SingleNoteFragment noteFragment;

    public NoteApi.OnNoteGetListener listener = new NoteApi.OnNoteGetListener() {
        @Override
        public void onNoteSuccess(List<NoteModel> notes) {
            notesAdapter.setItems(notes);
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();

            NoteListFragment list = new NoteListFragment();
            list.setNotesAdapter(notesAdapter);
            list.setToolbar(toolbar);
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
//                    notesAdapter.addItem();

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new Dialog(NotesActivity.this);
        // Установите заголовок
        dialog.setTitle("Тест укукуку"); //TODO | Это что за покемон?
        // Передайте ссылку на разметку
        dialog.setContentView(R.layout.addnote);

        user = Objects.requireNonNull(this.getIntent().getExtras()).getString("username");
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            dialog.show();

            Button save = dialog.findViewById(R.id.button_save);
            save.setOnClickListener(vv -> {
                EditText titleInput = dialog.findViewById(R.id.edit_title);
                String titlee = titleInput.getText().toString();
                NoteModel notee = new NoteModel();
                notee.setAuthor(UserModel.getUser().getEmail());
                notee.setTitle(titlee);
                notee.setBody("");
                NoteApi some =  NoteApi.getInstance();
                some.setDB(db);
                some.createNote(notee, onNoteCreateListener);
//                noteCreateHandler = NoteApi.getInstance().createNote(notee, onNoteCreateListener);
                notesAdapter.addItem(notee, findViewById(R.id.rv_notes));
//                this.onClick(null , notesAdapter.getItemCount() - 1);
//                notesAdapter.getCreatedItem()
                dialog.dismiss();
            });
        });

        notesAdapter = new NotesAdapter();

        NoteApi.getInstance().setDB(db);
        nodeHandler = NoteApi.getInstance().fetchNotes(listener, user);
        notesAdapter.setItemClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
        currentEditing = position;
        Bundle bundle = new Bundle();
        bundle.putString("body", notesAdapter.getBody(position));
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        noteFragment = new SingleNoteFragment();
        noteFragment.setArguments(bundle);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.list_container, noteFragment);
        } else {
            transaction.replace(R.id.note_container,noteFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        isEditing = true;
    }

    @Override
    public void onBackPressed() {

        if (isEditing) {

            //TODO | продолжить работу тут, когда оклемаюсь

            String textToSave = this.noteFragment.getEditor().getText().toString();
            NoteModel notee = new NoteModel();
            notee.setAuthor(UserModel.getUser().getEmail());
            notee.setTitle("some_title");
            notee.setBody(textToSave);
            NoteApi some =  NoteApi.getInstance();
            some.setDB(db); // Записать в бд
            //TODO | здесь надо вызвать метод обновления заметки | З.Ы: Егор пока не добавил
            noteCreateHandler = NoteApi.getInstance().createNote(notee, onNoteCreateListener);
            isEditing = false;
        }

        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
