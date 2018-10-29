package com.example.petrosadaman.codenotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LogReg extends AppCompatActivity {

    private final LoginValidator validator = new LoginValidator();
    private final LoginManager manager = new LoginManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        EditText loginInput = findViewById(R.id.edit_user);
        EditText passwordInput = findViewById(R.id.edit_password);
        Button enterButton = findViewById(R.id.button_login);

        enterButton.setOnClickListener((view) -> {
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
        });
    }
}
