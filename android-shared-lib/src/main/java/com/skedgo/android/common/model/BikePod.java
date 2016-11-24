package com.skedgo.android.common.model;

import com.google.gson.annotations.JsonAdapter;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
@JsonAdapter(GsonAdaptersBikePod.class)
public abstract class BikePod {
  abstract int available();
  abstract int capacity();
  abstract long lastUpdated();
}
