package com.knomatic.weather.data.weather.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currently {

  @SerializedName("time") @Expose public Integer time;
  @SerializedName("summary") @Expose public String summary;
  @SerializedName("icon") @Expose public String icon;
  @SerializedName("temperature") @Expose public Float temperature;
}
