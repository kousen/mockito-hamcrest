package com.oreilly.astro;

import com.oreilly.astro.json.AstroResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenNotify {
    @GET("astros.json")
    Call<AstroResponse> getAstronautsInSpace();
}
