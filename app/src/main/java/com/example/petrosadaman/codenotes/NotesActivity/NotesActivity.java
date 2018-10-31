package com.example.petrosadaman.codenotes.NotesActivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.petrosadaman.codenotes.LogRegActivity.RegistrationFragment;
import com.example.petrosadaman.codenotes.R;
import java.util.Arrays;
import java.util.List;

public class NotesActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        NotesAdapter.OnItemClickListener,
        RegistrationFragment.OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

//        final FragmentManager fragmentManager = getSupportFragmentManager();
//        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        NoteListFragment list = new NoteListFragment();
//        transaction.replace(R.id.list_container, list);
//        transaction.commit();

        //TODO:: recyclerView через фрагмент не получилось добавить
        //TODO:: пока что он вписан в layout данного activity
        //TODO:: что за view принимает onItemClickListener?
        //TODO:: использовать DifUtil, чтобы не пересоздавать весь список view при обновлении данных

        loadNotes();

        recyclerView = findViewById(R.id.notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter();
        notesAdapter.setItemClickListener(this);
        notesAdapter.setItems(noteList);
        recyclerView.setAdapter(notesAdapter);
    }

    private void loadNotes() {
        noteList = (getSomeNotes());
    }

    // Для тестирования!
    private List<Note> getSomeNotes() {
        return Arrays.asList(
                new Note("Note1"),
                new Note("Note2"),
                new Note("Note3"),
                new Note("Note4"),
                new Note("Note5"),
                new Note("Note6"),
                new Note("Note7"),
                new Note("Note8"),
                new Note("Note9"),
                new Note("Note10")
        );
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.list_container).setVisibility(View.VISIBLE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
//        System.out.println("clicked item: " + noteList.get(position).getData() + ": at position " + position);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        SingleNoteFragment noteFragment = new SingleNoteFragment();
        transaction.replace(R.id.container_for_note, noteFragment);
        findViewById(R.id.list_container).setVisibility(View.INVISIBLE);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
