package com.example.petrosadaman.codenotes.Activities.LogRegActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.petrosadaman.codenotes.DBManager.DBManager;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.User.UserModel;
import com.example.petrosadaman.codenotes.R;
import com.example.petrosadaman.codenotes.Web.ListenerHandler;
import com.example.petrosadaman.codenotes.Web.UserApi;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private DBManager db;

    private ListenerHandler<UserApi.OnUserGetListener> userHandler;
    private UserApi.OnUserGetListener listener = new UserApi.OnUserGetListener() {

        @Override
        public void onUserSuccess(MessageModel message) {
            System.out.println("ON_USER_SUCCESS: " + message.getMessage());
            //TODO | проверить сообщение. Если удалось залогиниться, то сохранить пользователя в БД
            //TODO | перекинуть на следующий активити, стерев из истории текущий активити
            //TODO | переделать проверку сообщения через перечисления

//            ((LogReg) Objects.requireNonNull(getActivity())).stopProgress();

            if (message.getMessage().equals("SUCCESSFULLY_REGISTERED")) {
                ((LogReg) Objects.requireNonNull(getActivity())).switchToNotes(message.getMessage());

            }
        }

        @Override
        public void onUserError(Exception error) {
//            ((LogReg) Objects.requireNonNull(getActivity())).stopProgress();
            System.out.println("ON_USER_ERROR: " + error.getMessage());
            //TODO написать логирование
        }
    };

    // Fragment Elements
    private EditText login;
    private EditText password;
    private EditText confirm;
    private Button registerButton;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setDB(DBManager db){
        this.db = db;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        this.login = view.findViewById(R.id.edit_user);
        this.password = view.findViewById(R.id.edit_password);
        this.confirm = view.findViewById(R.id.edit_confirm_password);
        this.registerButton = view.findViewById(R.id.button_login);

        this.registerButton.setOnClickListener((v) -> doRegister());

        if (view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        return view;
    }

    private void doRegister() {

//        ((LogReg) Objects.requireNonNull(getActivity())).startProgress();

        final String login = this.login.getText().toString();
        final String password = this.password.getText().toString();
        final String confirm = this.confirm.getText().toString();

        if (!password.equals(confirm)) {
            //TODO вывести сообщение об ошибке
//            ((LogReg) Objects.requireNonNull(getActivity())).stopProgress();
            return;
        }

        UserModel user = UserModel.getUser();
        user.setEmail(login);
        user.setUsername(login);
        user.setPassword(password);
        UserApi some = UserApi.getInstance();
        some.setDB(db);
        userHandler = some.regUser(user, listener);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
