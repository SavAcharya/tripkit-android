package com.skedgo.android.tripkit;

import android.support.annotation.NonNull;

import com.skedgo.android.common.model.TripSegment;

import rx.Observable;

public interface ServiceExtrasService {
  @NonNull Observable<ServiceExtras> getServiceExtrasAsync(
      @NonNull TripSegment segment
  );
}