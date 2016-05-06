package com.knomatic.weather.data.weather.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

  @SerializedName("time") @Expose public Integer time;
  @SerializedName("icon") @Expose public String icon;
  @SerializedName("temperatureMax") @Expose public Float temperatureMax;
}
