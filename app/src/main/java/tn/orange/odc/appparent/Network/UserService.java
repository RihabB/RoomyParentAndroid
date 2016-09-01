package tn.orange.odc.appparent.Network;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import tn.orange.odc.appparent.Entites.register;
import tn.orange.odc.appparent.Entites.registerRep;
import tn.orange.odc.appparent.Entites.user;
import tn.orange.odc.appparent.Entites.webServieceRep;

/**
 * Endpoint for the Search Engine REST API.
 * Using Retrofit annotations.
 */
public interface UserService {


    @POST("/authenticate/")
    @FormUrlEncoded
    public Call<webServieceRep> Connect(@Field("username") String login, @Field("password") String password);

    @PUT("")
   public Call<Void> updateUser(@Path("id") int id, @Body user u);


    @POST("/users/register")
    @FormUrlEncoded
    public Call<registerRep> addUser(@Body register u);









}
