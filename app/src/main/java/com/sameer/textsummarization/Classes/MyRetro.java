package com.sameer.textsummarization.Classes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sameer.textsummarization.Activity.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class MyRetro {

    private static final String TAG = "MyRetro";

    private String url;

    public MyRetro(String url) {
        this.url = url;
    }

    public void execute(String inputText){
        MyRetroBody body = new MyRetroBody();
        body.setInput_text(inputText);
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build();
        MyRetroInterface myRetroInterface = retrofit.create(MyRetroInterface.class);

        Call<MyRetroResult> call = myRetroInterface.summarize(body);
        call.enqueue(new Callback<MyRetroResult>() {
            @Override
            public void onResponse(Call<MyRetroResult> call, Response<MyRetroResult> response) {
                if (response.isSuccessful()){
                    MainActivity.Summary.setText("Summary: "+ response.body().getMessage());
                    Log.d(TAG, "onResponse: " + response.body().getMessage());
                }else {
                    Log.d(TAG, "onResponse: " + response.errorBody());
                    Log.d(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<MyRetroResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed");
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private class MyRetroBody{
        @Expose
        private String input_text;

        public String getInput_text() {
            return input_text;
        }

        public void setInput_text(String input_text) {
            this.input_text = input_text;
        }
    }

    private class MyRetroResult{
        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private interface MyRetroInterface{
        @Headers("Content-Type:application/json; charset=UTF-8")
        @POST("android_1")
        Call<MyRetroResult> summarize(@Body MyRetroBody body);
    }
}
