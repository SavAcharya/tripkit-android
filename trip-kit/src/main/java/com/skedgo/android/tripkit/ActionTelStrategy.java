package com.skedgo.android.tripkit;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import rx.Observable;

public class ActionTelStrategy implements ExternalActionStrategy {
  private final Resources resources;

  public ActionTelStrategy(Resources resources) {
    this.resources = resources;
  }

  @Override public Observable<BookingAction> performExternalActionAsync(ExternalActionParams params) {
    return Observable.error(new UnsupportedOperationException());
  }

  @Nullable @Override public String getTitleForExternalAction(String externalAction) {
    if (externalAction.contains("name=")) {
      String name = externalAction.substring(externalAction.indexOf("name=") + "name=".length());
      try {
        name = URLDecoder.decode(name, "UTF-8");
      } catch (UnsupportedEncodingException e) {
      }
      return resources.getString(R.string.calltaxiformat, name);
    } else {
      return resources.getString(R.string.call);
    }
  }
}
