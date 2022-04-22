package com.oreilly.astro;

import com.oreilly.astro.json.AstroResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.io.IOException;

@SuppressWarnings("HttpUrlsUsage")
public class AstroGateway implements Gateway<AstroResponse> {
    private static final String DEFAULT_URL = "http://api.open-notify.org/";
    private final String url;

    public AstroGateway() {
        this(DEFAULT_URL);
    }

    public AstroGateway(String url) {
        this.url = url;
    }

    @Override
    public AstroResponse getResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenNotify openNotify = retrofit.create(OpenNotify.class);

        try {
            return openNotify.getAstronautsInSpace().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    interface OpenNotify {
        @GET("astros.json")
        Call<AstroResponse> getAstronautsInSpace();
    }
}
