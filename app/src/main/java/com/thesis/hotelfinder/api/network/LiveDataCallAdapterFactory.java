package com.thesis.hotelfinder.api.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.thesis.hotelfinder.api.response.HotelsResponse;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations,@NonNull Retrofit retrofit) {

        // Check #1
        // Make sure the CallAdapter is returning a type of LiveData
        if(CallAdapter.Factory.getRawType(returnType) != LiveData.class){
            return null;
        }

        // Check #2
        // Type that LiveData is wrapping
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        // Check if it's of Type ApiResponse
        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if(rawObservableType != Resource.class){
            throw new IllegalArgumentException("type must be a defined resource");
        }

        // Check #3
        // Check if ApiResponse is parameterized. AKA: Does ApiResponse<T> exist? (must wrap around T)
        // FYI: T is either RecipeResponse or RecipeSearchResponse in this app. But T can be anything theoretically.
        if(!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("resource must be parameterized");
        }

        // get the Response type. (RecipeSearchResponse or RecipeResponse)
        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<Type>(bodyType);
    }
}