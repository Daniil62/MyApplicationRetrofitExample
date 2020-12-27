package ru.job4j.retrofit_example;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.job4j.retrofit_example.model.Comment;
import ru.job4j.retrofit_example.model.Post;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts();
    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int postId);
    @GET("posts/1/comments")
    Call<List<Comment>> getComments();
}
