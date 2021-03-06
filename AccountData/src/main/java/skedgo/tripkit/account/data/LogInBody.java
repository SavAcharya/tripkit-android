package skedgo.tripkit.account.data;

import com.google.gson.annotations.JsonAdapter;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters
@Value.Immutable
@JsonAdapter(GsonAdaptersLogInBody.class)
abstract class LogInBody {
  public abstract String password();
  public abstract String username();
}
