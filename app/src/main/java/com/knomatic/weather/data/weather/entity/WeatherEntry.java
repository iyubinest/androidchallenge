package com.knomatic.weather.data.weather.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherEntry {

  @SerializedName("currently") @Expose public Currently currently;
  @SerializedName("daily") @Expose public Daily daily;
}
