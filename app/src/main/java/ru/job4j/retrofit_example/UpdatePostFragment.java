package ru.job4j.retrofit_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UpdatePostFragment extends Fragment {
    private EditText userIdText;
    private EditText titleText;
    private EditText postText;
    private Integer id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_post_fragment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.id = bundle.getInt("id");
        }
        userIdText = view.findViewById(R.id.update_user_id_editText);
        titleText = view.findViewById(R.id.update_title_editText);
        postText = view.findViewById(R.id.update_text_editText);
        Button cancelButton = view.findViewById(R.id.update_cancel_button);
        Button patchButton = view.findViewById(R.id.update_patch_button);
        Button putButton =view.findViewById(R.id.update_put_button);
        cancelButton.setOnClickListener(v -> cancelClick());
        patchButton.setOnClickListener(v -> updateClick(5));
        putButton.setOnClickListener(v -> updateClick(6));
        return view;
    }
    private void updateClick(int number) {
        Intent intent = new Intent(getActivity(), ResultActivity.class);
        int userId = userIdText.getText().toString().equals("")
                ? 0 : Integer.parseInt(userIdText.getText().toString());
        String title = titleText.getText().toString().equals("")
                ? null : titleText.getText().toString();
        String text = postText.getText().toString().equals("")
                ? null : postText.getText().toString();
        intent.putExtra("id", id);
        intent.putExtra("number", number);
        intent.putExtra("user_id", userId);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
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
