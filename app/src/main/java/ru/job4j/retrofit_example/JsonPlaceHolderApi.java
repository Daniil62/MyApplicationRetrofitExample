package ru.job4j.retrofit_example;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@Field("userId") int userId,
                          @Field("title") String title,
                          @Field("body") String text);
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
