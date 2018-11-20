package com.example.petrosadaman.codenotes.Activities.LogRegActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.petrosadaman.codenotes.Activities.NotesActivity.NotesActivity;
import com.example.petrosadaman.codenotes.LoginManager;
import com.example.petrosadaman.codenotes.LoginValidator;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.User.UserModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.UserApi;

public class LogReg extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private final LoginValidator validator = new LoginValidator();
    private final LoginManager manager = new LoginManager();


    private ListenerHandler<UserApi.OnUserGetListener> userHandler;

    private UserApi.OnUserGetListener listener = new UserApi.OnUserGetListener() {

        @Override
        public void onUserSuccess(MessageModel message) {
            System.out.println("ON_USER_SUCCESS: " + message.getMessage());
            //TODO | проверить сообщение. Если удалось залогиниться, то сохранить пользователя в БД
            //TODO | перекинуть на следующий активити, стерев из истории текущий активити
            //TODO | переделать проверку сообщения через перечисления

            stopProgress();

            if (message.getMessage().equals("SUCCESSFULLY_AUTHED")) {
                switchToNotes();
            }
        }

        @Override
        public void onUserError(Exception error) {
            stopProgress();
            System.out.println("ON_USER_ERROR: " + error.getMessage());
            //TODO написать логирование
        }
    };

    private EditText loginInput;
    private EditText passwordInput;
    private Button enterButton;
    private Button registerButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        progressBar = findViewById(R.id.progress);
        this.loginInput = findViewById(R.id.edit_user);
        this.passwordInput = findViewById(R.id.edit_password);
        this.enterButton = findViewById(R.id.button_login);
        this.registerButton = findViewById(R.id.button_register);

        this.enterButton.setOnClickListener((view) -> login());
        this.registerButton.setOnClickListener((view) -> switchToReg());

    }

    protected void login() {

        this.startProgress();

        //do login process
        String email = loginInput.getText().toString();
        String password = passwordInput.getText().toString();

        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(password);

        userHandler = UserApi.getInstance().authUser(user, listener);

    }

    protected void switchToNotes() {
        Intent intent = new Intent(LogReg.this, NotesActivity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("password", password);
        startActivity(intent);
    }

    protected void switchToReg() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        RegistrationFragment rf = new RegistrationFragment();
        transaction.replace(R.id.container, rf);
        findViewById(R.id.main).setVisibility(View.INVISIBLE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void startProgress() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.enterButton.setEnabled(false);
        this.registerButton.setEnabled(false);
    }

    protected void stopProgress() {
        this.progressBar.setVisibility(View.INVISIBLE);
        this.enterButton.setEnabled(true);
        this.registerButton.setEnabled(true);
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
        findViewById(R.id.main).setVisibility(View.VISIBLE);
    }
}
