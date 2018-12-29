package top.onehundred.android.oneapi.demo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitTest {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @POST("users/{user}/repos")
    Call<List<Repo>> postUser(@Path("user") String user, @Field("dd") String dd);

    @Multipart
    @POST("users/{user}/repos")
    Call<List<Repo>> upload(@Path("user") String user, @Part MultipartBody.Part file);
}
