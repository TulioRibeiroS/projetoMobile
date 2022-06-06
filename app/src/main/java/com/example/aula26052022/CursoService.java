package com.example.aula26052022;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface CursoService {

    @POST("courses")
    Call<CursoResponse> createRequestPost(@Body CursoPost cursoPost);

    @GET("courses")
    Call<List<CursoResponse>> getAllCourses();

    @PUT("courses/{course_id}")
    Call<CursoResponse> createRequestPut(@Body CursoPost cursoPut, @Path("course_id") int id);

    @DELETE("courses/{course_id}")
    Call<Object> deleteRequest(@Path("course_id") int id);
}
