package com.example.petrosadaman.codenotes.Activities.LogRegActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.petrosadaman.codenotes.Activities.NotesActivity.NotesActivity;
import com.example.petrosadaman.codenotes.LoginManager;
import com.example.petrosadaman.codenotes.LoginValidator;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.UserApi;

public class LogReg extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private final LoginValidator validator = new LoginValidator();
    private final LoginManager manager = new LoginManager();

    private ProgressBar progressBar;


    private ListenerHandler<UserApi.OnUserGetListener> userHandler;

    //todo добавить в регистрацию стоп и старт. в активити не получилось, потому что требуется доступ к кнопкам, который в фрагментах
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_log_registr);
        progressBar = findViewById(R.id.progress);


        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();

        transaction.replace(R.id.log_reg_container, loginFragment);

        transaction.commit();


    }

    protected void switchToNotes() {
        Intent intent = new Intent(LogReg.this, NotesActivity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("password", password);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (userHandler != null) {
            userHandler.unregister();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // он попросил реализовать, я хз
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        findViewById(R.id.main).setVisibility(View.VISIBLE);
    }
}
