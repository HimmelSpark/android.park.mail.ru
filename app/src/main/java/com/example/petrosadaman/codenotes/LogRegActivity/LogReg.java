package com.example.petrosadaman.codenotes.LogRegActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.petrosadaman.codenotes.LoginManager;
import com.example.petrosadaman.codenotes.LoginValidator;
import com.example.petrosadaman.codenotes.NotesActivity;
import com.example.petrosadaman.codenotes.R;

public class LogReg extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private final LoginValidator validator = new LoginValidator();
    private final LoginManager manager = new LoginManager();

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
        if (validator.validate(username, password)) {
            //do login
            if (manager.doLogin()) {
                //move to other Activity
                System.out.println(username + " " + password);
                //не забыть отправить данные через бандл
                Intent intent = new Intent(LogReg.this, NotesActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
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
    public void onFragmentInteraction(Uri uri) {
        // он попросил реализовать, я хз
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.main).setVisibility(View.VISIBLE);
    }
}
