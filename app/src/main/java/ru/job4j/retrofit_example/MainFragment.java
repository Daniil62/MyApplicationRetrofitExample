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

public class MainFragment extends Fragment {
    private EditText enterId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        Button loadPostsButton = view.findViewById(R.id.load_posts_button);
        Button loadCommentsButton = view.findViewById(R.id.load_comments_button);
        Button createPostButton = view.findViewById(R.id.create_post_button);
        Button updatePostButton = view.findViewById(R.id.update_post_button);
        Button deletePostButton = view.findViewById(R.id.delete_post_button);
        Button loadPostByIdButton = view.findViewById(R.id.load_post_by_id_button);
        enterId = view.findViewById(R.id.editText);
        loadPostsButton.setOnClickListener(this::click);
        loadCommentsButton.setOnClickListener(this::click);
        createPostButton.setOnClickListener(v -> addClick());
        updatePostButton.setEnabled(false);
        updatePostButton.setOnClickListener(v -> updateClick());
        deletePostButton.setEnabled(false);
        deletePostButton.setOnClickListener(this::click);
        loadPostByIdButton.setEnabled(false);
        loadPostByIdButton.setOnClickListener(this::click);
        enterId.addTextChangedListener(new NumberMaster() {
            @Override
            public void afterTextChanged(Editable s) {
                boolean permission = s.length() > 0 && Integer.parseInt(s.toString()) > 0
                        && Integer.parseInt(s.toString()) < 101;
                updatePostButton.setEnabled(permission);
                deletePostButton.setEnabled(permission);
                loadPostByIdButton.setEnabled(permission);
            }
        });
        return view;
    }
    private void click(View view) {
        Intent intent = new Intent(getActivity(), ResultActivity.class);
        int number = 0;
        switch (view.getId()) {
            case R.id.load_posts_button: {
                number = 1;
                break;
            }
            case R.id.load_comments_button: {
                number = 2;
                break;
            }
            case R.id.load_post_by_id_button: {
                number = 3;
                intent.putExtra("id", Integer.parseInt(enterId.getText().toString()));
                break;
            }
            case R.id.delete_post_button: {
                number = 7;
                intent.putExtra("id", Integer.parseInt(enterId.getText().toString()));
            }
        }
        intent.putExtra("number", number);
        startActivity(intent);
    }
    private void addClick() {
        FragmentManager manager = getFragmentManager();
        Fragment fragment = new CreatePostFragment();
        if (manager != null) {
            manager.beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();
        }
    }
    private void updateClick() {
        FragmentManager manager = getFragmentManager();
        Fragment fragment = new UpdatePostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.parseInt(enterId.getText().toString()));
        fragment.setArguments(bundle);
        if (manager != null) {
            manager.beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();
        }
    }
}
