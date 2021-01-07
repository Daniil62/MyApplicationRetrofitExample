package ru.job4j.retrofit_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreatePostFragment extends Fragment {
    private EditText userIdText;
    private EditText titleText;
    private EditText postText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_post_fragment, container, false);
        userIdText = view.findViewById(R.id.user_id_editText);
        titleText = view.findViewById(R.id.title_editText);
        postText = view.findViewById(R.id.text_editText);
        Button cancel = view.findViewById(R.id.cancel_button);
        Button ok = view.findViewById(R.id.ok_button);
        userIdText.addTextChangedListener(new TextMaster() {
            @Override
            public void afterTextChanged(Editable s) {
                ok.setEnabled(s.length() != 0 && titleText.getText().length() != 0
                        && postText.getText().length() != 0);
            }
        });
        titleText.addTextChangedListener(new TextMaster() {
            @Override
            public void afterTextChanged(Editable s) {
                ok.setEnabled(s.length() != 0 && userIdText.getText().length() != 0
                        && postText.getText().length() != 0);
            }
        });
        postText.addTextChangedListener(new TextMaster() {
            @Override
            public void afterTextChanged(Editable s) {
                ok.setEnabled(s.length() != 0 && userIdText.getText().length() != 0
                        && titleText.getText().length() != 0);
            }
        });
        cancel.setOnClickListener(v -> cancelClick());
        ok.setEnabled(false);
        ok.setOnClickListener(v -> okClick());
        return view;
    }
    private void okClick() {
        Intent intent = new Intent(getActivity(), ResultActivity.class);
        intent.putExtra("number", 4);
        intent.putExtra("user_id", Integer.parseInt(userIdText.getText().toString()));
        intent.putExtra("title", titleText.getText().toString());
        intent.putExtra("text", postText.getText().toString());
        startActivity(intent);
    }
    private void cancelClick() {
        FragmentManager manager = getFragmentManager();
        Fragment fragment = new MainFragment();
        if (manager != null) {
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }
    }
}
