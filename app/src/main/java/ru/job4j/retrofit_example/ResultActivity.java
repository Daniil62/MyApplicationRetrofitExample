package ru.job4j.retrofit_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private TextView errorReport;
    private Call<List<Comment>> commentCall;
    private Call<List<Post>> postCall;
    private Call<Post> postByIdCall;
    private Call<Post> newPostCall;
    private Call<Post> patchPostCall;
    private Call<Post> putPostCall;
    private Call<Void> deletePostCall;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        recycler = findViewById(R.id.recycler);
        errorReport = findViewById(R.id.error_textView);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        int userId = intent.getIntExtra("user_id", 0);
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        levelSelector(interceptor);
        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .client(createClientErrorIntercept())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        this.commentCall = jsonPlaceHolderApi.getComments();
        this.postCall = jsonPlaceHolderApi.getPosts();
        this.postByIdCall = jsonPlaceHolderApi.getPostById(id);
        this.newPostCall = jsonPlaceHolderApi.createPost(userId, title, text);
        Post post = new Post(userId, title, text);
        this.patchPostCall = jsonPlaceHolderApi.patchPost(id, post);
        this.putPostCall = jsonPlaceHolderApi.putPost(id, post);
        this.deletePostCall = jsonPlaceHolderApi.deletePost(id);
        loadItems(intent.getIntExtra("number", 1));
    }
    private void levelSelector(HttpLoggingInterceptor interceptor) {
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
    }
    private OkHttpClient createClientErrorIntercept() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);
            int code = response.code();
            if (code >= 400 && code <= 599) {
                String report = "Сетевая ошибка.\nКод: " + code;
                runOnUiThread(() -> errorReport.setText(report));
                return response;
            }
            return response;
        }).build();
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
                break;
            }
            case 4: {
                addPost();
                break;
            }
            case 5: {
                patchPost();
                break;
            }
            case 6: {
                putPost();
                break;
            }
            case 7: {
                deletePost();
                break;
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
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
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
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
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
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
    private void addPost() {
        newPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    recycler.setAdapter(new PostLoader.ResultAdapter(
                            Collections.singletonList(response.body())));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
    private void patchPost() {
        patchPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    recycler.setAdapter(new PostLoader.ResultAdapter(
                            Collections.singletonList(response.body())));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
    private void putPost() {
        putPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    recycler.setAdapter(new PostLoader.ResultAdapter(
                            Collections.singletonList(response.body())));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
    private void deletePost() {
        deletePostCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(),
                            String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(ResultActivity.this, "Ошибка: "
                        + t.getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
}
