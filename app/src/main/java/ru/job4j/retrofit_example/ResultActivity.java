package ru.job4j.retrofit_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.job4j.retrofit_example.loaders.CommentLoader;
import ru.job4j.retrofit_example.loaders.PostLoader;
import ru.job4j.retrofit_example.model.Comment;
import ru.job4j.retrofit_example.model.Post;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private Call<List<Comment>> commentCall;
    private Call<List<Post>> postCall;
    private Call<Post> postByIdCall;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        this.commentCall = jsonPlaceHolderApi.getComments();
        this.postCall = jsonPlaceHolderApi.getPosts();
        this.postByIdCall = jsonPlaceHolderApi.getPostById(
                intent.getIntExtra("id", 1));
        loadItems(intent.getIntExtra("number", 1));
    }
    private void loadItems(int number) {
        switch (number) {
            case 1: {
                loadPosts();
                break;
            }
            case 2: {
                loadComments();
                break;
            }
            case 3: {
                loadPostById();
            }
            default: {
                break;
            }
        }
    }
    private void loadComments() {
        commentCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call,
                                   @NonNull Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                recycler.setAdapter(new CommentLoader.ResultAdapter(response.body()));
            }
            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
            }
        });
    }
    private void loadPosts() {
        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call,
                                   @NonNull Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                recycler.setAdapter(new PostLoader.ResultAdapter(response.body()));
            }
            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
            }
        });
    }
    private void loadPostById() {
        postByIdCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call,
                                   @NonNull Response<Post> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                recycler.setAdapter(new PostLoader.ResultAdapter(
                        Collections.singletonList(response.body())));
            }
            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
            }
        });
    }
}
