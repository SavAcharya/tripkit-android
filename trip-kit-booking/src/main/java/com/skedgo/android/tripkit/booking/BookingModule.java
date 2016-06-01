package com.skedgo.android.tripkit.booking;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skedgo.android.tripkit.TripKit;
import com.skedgo.android.tripkit.booking.viewmodel.AuthenticationViewModel;
import com.skedgo.android.tripkit.booking.viewmodel.AuthenticationViewModelImpl;
import com.skedgo.android.tripkit.booking.viewmodel.BookingViewModel;
import com.skedgo.android.tripkit.booking.viewmodel.BookingViewModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class BookingModule {
  @Provides BookingApi bookingApi(OkHttpClient httpClient) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(FormField.class, new FormFieldJsonAdapter())
        .create();
    return new Retrofit.Builder()
        /* This base url is ignored as the api relies on @Url. */
        .baseUrl(HttpUrl.parse("https://tripgo.skedgo.com/satapp/"))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()
        .create(BookingApi.class);
  }

  @Provides QuickBookingApi quickBookingApi(OkHttpClient httpClient) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new GsonAdaptersQuickBooking())
        .create();
    return new Retrofit.Builder()
        /* This base url is ignored as the api relies on @Url. */
        .baseUrl(HttpUrl.parse("https://tripgo.skedgo.com/satapp/"))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()
        .create(QuickBookingApi.class);
  }

  @Provides AuthApi authApi(OkHttpClient httpClient) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(FormField.class, new FormFieldJsonAdapter())
        .registerTypeAdapterFactory(new GsonAdaptersAuthProvider())
        .registerTypeAdapterFactory(new GsonAdaptersLogOutResponse())
        .create();
    return new Retrofit.Builder()
        /* This base url is ignored as the api relies on @Url. */
        .baseUrl(HttpUrl.parse("https://tripgo.skedgo.com/satapp/"))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()
        .create(AuthApi.class);
  }

  @Provides AuthService authService(AuthApi authApi) {
    return new AuthServiceImpl(authApi);
  }

  @Provides BookingViewModel bookingViewModel(BookingApi bookingApi) {
    return new BookingViewModelImpl(bookingApi);
  }

  @Provides AuthenticationViewModel authenticationViewModel() {
    return new AuthenticationViewModelImpl();
  }

  @Provides ExternalOAuthStore getExternalOAuthStore(SQLiteOpenHelper databaseHelper, ExternalOAuthEntityAdapter adapter) {
    return new ExternalOAuthStoreImpl(TripKit.singleton().configs().context(), databaseHelper, adapter);
  }

  @Singleton @Provides SQLiteOpenHelper databaseHelper() {
    return new ExternalOAuthDbHelper(TripKit.singleton().configs().context(), "externalOAuths.db");
  }

  @Provides ExternalOAuthEntityAdapter provideOlympicEntityAdapter() {
    return new ExternalOAuthEntityAdapter();
  }

  @Provides ExternalOAuthService getExternalOAuthService(ExternalOAuthStore store) {
    return new ExternalOAuthServiceImpl(store);
  }
}