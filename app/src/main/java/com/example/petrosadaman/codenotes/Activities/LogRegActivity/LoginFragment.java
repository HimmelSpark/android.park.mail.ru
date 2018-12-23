package com.example.petrosadaman.codenotes.Activities.LogRegActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.petrosadaman.codenotes.DBManager.DBManager;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.User.UserModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.UserApi;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFragment extends Fragment {
    private EditText loginInput;
    private EditText passwordInput;
    private Button enterButton;
    private Button registerButton;
    private ProgressBar progressBar;
    private LogReg logReg;
    private ListenerHandler<UserApi.OnUserGetListener> userHandler;
    private DBManager db;

    private Logger logger = Logger.getLogger("LOGIN FRAGMENT");

    public void setDB(DBManager db){
        this.db = db;
    }

    public UserApi.OnUserGetListener listener = new UserApi.OnUserGetListener() {

        @Override
        public void onUserSuccess(MessageModel message) {
            //TODO | проверить сообщение. Если удалось залогиниться, то сохранить пользователя в БД
            //TODO | переделать проверку сообщения через перечисления

            stopProgress();
            ((LogReg) Objects.requireNonNull(getActivity())).switchToNotes(message.getMessage());

        }

        @Override
        public void onUserError(Exception error) {
            stopProgress();
            logger.log(Level.SEVERE, error.getMessage());
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        progressBar = view.findViewById(R.id.progress);
        this.loginInput = view.findViewById(R.id.edit_user);
        this.passwordInput = view.findViewById(R.id.edit_password);
        this.enterButton = view.findViewById(R.id.button_login);
        this.registerButton = view.findViewById(R.id.button_register);
        this.enterButton.setOnClickListener((v) -> login());
        this.registerButton.setOnClickListener((v) -> switchToReg());

        if (view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        return view;
    }

    protected void login() {

        this.startProgress();

//        do login process
        String email = loginInput.getText().toString();
        String password = passwordInput.getText().toString();

        UserModel user = UserModel.getUser();
        user.setEmail(email);
        user.setPassword(password);
        UserApi some =  UserApi.getInstance();
        some.setDB(db);
        userHandler = some.authUser(user, listener);
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

    protected void switchToReg() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        RegistrationFragment rf = new RegistrationFragment();
        rf.setDB(db);
        transaction.replace(R.id.log_reg_container, rf);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
