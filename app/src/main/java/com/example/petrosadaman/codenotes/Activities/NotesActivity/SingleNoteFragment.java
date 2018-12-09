package com.example.petrosadaman.codenotes.Activities.NotesActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.petrosadaman.codenotes.R;

public class SingleNoteFragment extends Fragment {

    EditText editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_note, container, false);
        if (view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        editor = view.findViewById(R.id.note_editor);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            editor.setText(bundle.getString("body"));
        }

        editor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("in BeforeChanged | char " + s + "| start " + start + "| count " + count + "| after " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("in TextChanged | char " + s + "| start " + start + "| count " + count + "| before " + before);
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("in afterChanged " + s.toString());
            }
        });

        return view;
    }

    EditText getEditor() {
        return this.editor;
    }


    //TODO прописать установку данных и заливку при аттаче
}
