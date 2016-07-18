package mobello.amtrust.com.utility;


import mobello.amtrust.com.model.ApiStatus;
import mobello.amtrust.com.model.CreateEntity;
import mobello.amtrust.com.model.CustomerLogin;
import mobello.amtrust.com.model.GetChallenge;
import mobello.amtrust.com.model.SessionLogin;
import mobello.amtrust.com.model.Status;
import mobello.amtrust.com.model.UserExist;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by MobelloTech on 24-10-2015.
 */
public class RetrofitApi {

    public static Retrofit retrofit;

    private static final String BASE_URL = "https://aw.secondcrm.com/amtrust/webservice.php/";

    public static Retrofit getRetrofitInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)        // live url
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static ApiInterface getApiInterfaceInstance(){
        return getRetrofitInstance().create(ApiInterface.class);
    }

    public interface ApiInterface {
        @FormUrlEncoded
        @POST("logs/create")
        Call<ApiStatus> sendBugReport(
                @Field("userID") String userID,
                @Field("name") String name,
                @Field("log") String log,
                @Field("device") String device
        );


        @GET(BASE_URL)
        Call<GetChallenge>  getChallenge(
                @Query("operation") String operation,
                @Query("username") String username);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<SessionLogin> login(
                @Field("operation") String operation,
                @Field("username") String username,
                @Field("accessKey") String accessKey);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<UserExist> checkUserExists(
                @Field("operation") String operation,
                @Field("sessionName") String sessionName,
                @Field("element") String element,
                @Field("elementType") String elementType);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<CreateEntity> register(
                @Field("operation") String operation,
                @Field("sessionName") String sessionName,
                @Field("element") String element,
                @Field("elementType") String elementType);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<Status> sessionLogout(
                @Field("operation") String operation,
                @Field("sessionName") String sessionName);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<Status> changePassword(
                @Field("operation") String operation,
                @Field("email") String email,
                @Field("current_passwd") String current_passwd,
                @Field("new_passwd") String new_password);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<CustomerLogin> customerLogin(
                @Field("operation") String operation,
                @Field("username") String username,
                @Field("password") String password);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<Status> customerLogOut(
                @Field("operation") String operation,
                @Field("username") String username);

        @FormUrlEncoded
        @POST(BASE_URL)
        Call<Status> forgetPassword(
                @Field("operation") String operation,
                @Field("sessionName") String sessionName,
                @Field("email") String email,
                @Field("phone") String phone,
                @Field("lastname") String lastname);
    }
}
