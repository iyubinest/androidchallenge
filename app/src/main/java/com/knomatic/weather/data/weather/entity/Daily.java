package com.knomatic.weather.data.weather.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Daily {

  @SerializedName("icon") @Expose public String icon;
  @SerializedName("data") @Expose public List<Datum> data = new ArrayList<Datum>();
}
