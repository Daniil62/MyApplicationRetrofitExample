package ru.job4j.retrofit_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText enterId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadPostsButton = findViewById(R.id.load_posts_button);
        Button loadCommentsButton = findViewById(R.id.load_comments_button);
        Button loadPostByIdButton = findViewById(R.id.load_post_by_id_button);
        enterId = findViewById(R.id.editText);
        loadPostsButton.setOnClickListener(this::click);
        loadCommentsButton.setOnClickListener(this::click);
        loadPostByIdButton.setOnClickListener(this::click);
        loadPostByIdButton.setEnabled(false);
        enterId.addTextChangedListener(new NumberMaster() {
            @Override
            public void afterTextChanged(Editable s) {
                loadPostByIdButton.setEnabled(s.length() > 0 && Integer.parseInt(s.toString()) > 0
                        && Integer.parseInt(s.toString()) < 101);
            }
        });
    }
    private void click(View view) {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
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
        }
        intent.putExtra("number", number);
        startActivity(intent);
    }
}
