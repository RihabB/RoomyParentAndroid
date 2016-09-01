package tn.orange.odc.appparent.Network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 02/06/16.
 */
public class ProjectApi {


    private static ProjectApi instance;

    UserService userServ;



    /**
     * Returns the instance of this singleton.
     */
    public static ProjectApi getInstance() {
        if (instance == null) {
            instance = new ProjectApi();
        }
        return instance;
    }


    private ProjectApi() {
        Retrofit  retrofit = buildRetrofit();
        userServ = retrofit.create(UserService.class);

    }


    public Retrofit buildRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


    }


    public UserService getUserServ() {
        return userServ;
    }


}
