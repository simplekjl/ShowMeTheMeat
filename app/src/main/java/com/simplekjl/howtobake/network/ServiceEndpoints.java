package com.simplekjl.howtobake.network;

import com.simplekjl.howtobake.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ServiceEndpoints {
    //https://api.themoviedb.org/3/movie/
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
