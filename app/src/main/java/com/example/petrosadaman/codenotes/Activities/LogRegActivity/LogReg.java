package com.example.petrosadaman.codenotes.Activities.LogRegActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.petrosadaman.codenotes.Activities.NotesActivity.NotesActivity;
import com.example.petrosadaman.codenotes.LoginManager;
import com.example.petrosadaman.codenotes.LoginValidator;
import com.example.petrosadaman.codenotes.Models.User.UserModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.UserApi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LogReg extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private final LoginValidator validator = new LoginValidator();
    private final LoginManager manager = new LoginManager();


    private ListenerHandler<UserApi.OnUserGetListener> userHandler;

    private UserApi.OnUserGetListener listener = new UserApi.OnUserGetListener() {
        @Override
        public void onUserSuccess(final UserModel user) {
            System.out.println(user.getUsername());
        }

        @Override
        public void onUserError(final Exception error) {
            System.out.println(error.getMessage());
        }
    };

    private EditText loginInput;
    private EditText passwordInput;
    private Button enterButton;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        loginInput = findViewById(R.id.edit_user);
        passwordInput = findViewById(R.id.edit_password);
        enterButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_register);

        enterButton.setOnClickListener((view) -> login());
        registerButton.setOnClickListener((view) -> switchToReg());

    }

    protected void login() {


        //do login process
        String username = loginInput.getText().toString();
        String password = passwordInput.getText().toString();
        userHandler = UserApi.getInstance().getUser(new UserModel(username, password), listener);

        if (validator.validate(username, password)) {
            //do login
            MyHttpClient.doResp("testUser123", "qwertyui", new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    System.out.println(e.getMessage() + "_____________message");
                    System.out.println("FAIL________________");
                    // вывести сообщение о проблемах с сетью
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    System.out.println("SUCCESS______________");
                    if (response.body() != null) {
                        System.out.println(response.body().string());
                    }
                    // произвести проверку ответа

                    //move to other Activity
                    System.out.println(username + " " + password);
                    // не забыть отправить данные через экстра
                    // сохранить пользовательские данные в бд
                    Intent intent = new Intent(LogReg.this, NotesActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            });


        }
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
