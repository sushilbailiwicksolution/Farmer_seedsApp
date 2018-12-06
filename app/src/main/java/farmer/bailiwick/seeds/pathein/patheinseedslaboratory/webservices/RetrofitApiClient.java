package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices;

/**
 * Created by Prince on 05-11-2018.
 */

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

    // exctiel
    //       private static final String BASE_URL = "http://192.168.0.145:8080/Seeds/";
    // Bailiwick 2g
    // private static final String BASE_URL = "http://192.168.0.145:8080/Seeds/";

    private static final String BASE_URL = "http://192.168.137.1:8080/Seeds/";

//    private static final String BASE_URL = "http://103.206.248.236:15000/Seeds/";

    private static Context ctx;

    private static ApiInterface REST_CLIENT;
    public static Retrofit retrofit = null;

    public RetrofitApiClient(Context ctx) {
        RetrofitApiClient.ctx = ctx;
        getRetrofitBuilder();

    }

    public static ApiInterface get() {
        if (null != REST_CLIENT) {
            return REST_CLIENT;
        } else {
            getRetrofitBuilder();
            return REST_CLIENT;
        }
    }

    static Gson gson = new GsonBuilder().setLenient().create();

    private static Retrofit getRetrofitBuilder() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(getOkHttpClient(ctx)).addConverterFactory(GsonConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson))

                .build();

        REST_CLIENT = retrofit.create(ApiInterface.class);

        return retrofit;
    }

    public static OkHttpClient getOkHttpClient(Context context) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        okClientBuilder.addInterceptor(headerAuthorizationInterceptor);
        //okClientBuilder.interceptors().add(httpLoggingInterceptor);

        okClientBuilder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true);

        return okClientBuilder.build();
    }


    private static Interceptor headerAuthorizationInterceptor = new Interceptor() {
        //TokenManager mTokenManager = new TokenManagerImpl(ctx);

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // Headers  headers = request.headers().newBuilder().add("key", "NjAxNzI1OmUyZmJlZW").build();
            Request request1 = request.newBuilder().header("Content-Type", "application/json").method(request.method(), request.body()).build();
           /* if (mTokenManager.hasToken()) {
                headers = request.headers().newBuilder().add("Authorization", "Basic " + mTokenManager.getToken()).build();
            } else {
                mTokenManager.clearToken();
                mTokenManager.refreshToken();
                headers = request.headers().newBuilder().add("Authorization", "Basic " + mTokenManager.getToken()).build();
            }*/
            // request = request.newBuilder().headers(headers).build();
            return chain.proceed(request1);
        }
    };

}

