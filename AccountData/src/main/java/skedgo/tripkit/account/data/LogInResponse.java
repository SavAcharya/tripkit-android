package skedgo.tripkit.account.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.JsonAdapter;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters
@Value.Immutable
@JsonAdapter(GsonAdaptersLogInResponse.class)
abstract class LogInResponse {
  @Nullable public abstract String userToken();
}
