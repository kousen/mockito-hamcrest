package com.oreilly.astro;

import com.oreilly.astro.json.AstroResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        OpenNotify service = retrofit.create(OpenNotify.class);
        try {
            return service.getAstronautsInSpace().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        AtomicReference<AstroResponse> resultRef = new AtomicReference<>();
//        service.getAstronautsInSpace().enqueue(new Callback<AstroResponse>() {
//            @Override
//            public void onResponse(Call<AstroResponse> call, Response<AstroResponse> response) {
//                resultRef.set(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<AstroResponse> call, Throwable t) {
//                throw new RuntimeException(t);
//            }
//        });
//        return resultRef.get();
    }
}
