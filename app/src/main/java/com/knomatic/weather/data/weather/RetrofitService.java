/*************************************************************************
 * CONFIDENTIAL
 * __________________
 *
 * [2016] All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of {The Company} and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to {The Company}
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from {The Company}.
 */
package com.knomatic.weather.data.weather;

import android.support.annotation.DrawableRes;
import com.knomatic.weather.BuildConfig;
import com.knomatic.weather.R;
import com.knomatic.weather.data.weather.entity.Datum;
import com.knomatic.weather.data.weather.entity.WeatherEntry;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitService implements WeatherService {

  public static final String CLEAR_DAY_IMAGE =
      "http://www.hinzie.com/writer/media/image/34592_max.jpg";
  public static final String CLEAR_NIGHT_IMAGE =
      "http://acebox.uwaterloo.ca/eureka/Eureka2004/EurekaPictures/feb23_weather_station_at_night.jpg";
  public static final String RAIN_IMAGE =
      "https://s.w-x.co/56a3d494-ae4e-48be-a657-43197dac5401.jpg";
  public static final String SNOW_IMAGE =
      "http://i.dailymail.co.uk/i/pix/2013/01/16/article-2263222-16F96AC7000005DC-685_964x615.jpg";
  public static final String SLEET_IMAGE =
      "http://mediad.publicbroadcasting.net/p/ketr/files/styles/x_large/public/201403/Sleet_2.jpg";
  public static final String WIND_IMAGE = "http://troymayr.com/gallery/art/images/01/0003.jpg";
  public static final String FOG_IMAGE =
      "http://i.telegraph.co.uk/multimedia/archive/02061/leicester-park_2061557i.jpg";
  public static final String CLOUDY_IMAGE =
      "http://photovide.com/wp-content/uploads/2012/10/Cloudy-Weather-01.jpg";
  public static final String PARTLY_CLOUDY_DAY_IMAGE =
      "http://i2.dailyrecord.co.uk/incoming/article7565006.ece/ALTERNATES/s615/WA5774859.jpg";
  public static final String PARTLY_CLOUDY_NIGHT_IMAGE =
      "http://jayrickerts.com/wp-content/uploads/2012/02/Cloudy_Night_Wallpaper_n6gep2.jpg";
  private final Service service;
  private Callback callback;

  public RetrofitService(Retrofit retrofit) {
    service = retrofit.create(Service.class);
  }

  @Override public void get(double lat, double lon, Callback callback) {
    this.callback = callback;
    weather(lat, lon);
  }

  private void weather(double lat, double lon) {
    service.weather(BuildConfig.API_KEY, String.valueOf(lat), String.valueOf(lon))
        .enqueue(new retrofit2.Callback<WeatherEntry>() {
          @Override public void onResponse(Response<WeatherEntry> response) {
            if (response.isSuccess() && response.body() != null) {
              WeatherHeader header = headerFrom(response.body());
              List<Weather> weather = listFrom(response.body());
              callback.onSuccess(header, weather);
            } else {
              onFailure(new Throwable());
            }
          }

          @Override public void onFailure(Throwable t) {
            callback.onFailure();
          }
        });
  }

  private List<Weather> listFrom(WeatherEntry entry) {
    List<Weather> weathers = new ArrayList<>();
    for (int i = 1; i < entry.daily.data.size(); i++) {
      Datum dailyData = entry.daily.data.get(i);
      weathers.add(weatherFrom(dailyData));
    }
    return weathers;
  }

  private Weather weatherFrom(Datum dailyData) {
    Date date = new Date(Long.valueOf(dailyData.time) * 1000l);
    String day = dayName(date);
    int icon = iconFor(dailyData.icon);
    return Weather.create(day, icon, String.valueOf(dailyData.temperatureMax));
  }

  private @DrawableRes int iconFor(String iconName) {
    if (iconName.equals("clear-day")) return R.drawable.sun;
    if (iconName.equals("clear-night")) return R.drawable.moonnew;
    if (iconName.equals("rain")) return R.drawable.clouddrizzle;
    if (iconName.equals("snow")) return R.drawable.cloudsnow;
    if (iconName.equals("sleet")) return R.drawable.cloudsnowalt;
    if (iconName.equals("wind")) return R.drawable.wind;
    if (iconName.equals("fog")) return R.drawable.cloudfog;
    if (iconName.equals("cloudy")) return R.drawable.cloud;
    if (iconName.equals("partly-cloudy-day")) return R.drawable.cloudsun;
    if (iconName.equals("partly-cloudy-night")) return R.drawable.cloudmoon;
    throw new IllegalStateException();
  }

  private String urlFor(String iconName) {
    if (iconName.equals("clear-day")) return CLEAR_DAY_IMAGE;
    if (iconName.equals("clear-night")) return CLEAR_NIGHT_IMAGE;
    if (iconName.equals("rain")) return RAIN_IMAGE;
    if (iconName.equals("snow")) return SNOW_IMAGE;
    if (iconName.equals("sleet")) return SLEET_IMAGE;
    if (iconName.equals("wind")) return WIND_IMAGE;
    if (iconName.equals("fog")) return FOG_IMAGE;
    if (iconName.equals("cloudy")) return CLOUDY_IMAGE;
    if (iconName.equals("partly-cloudy-day")) return PARTLY_CLOUDY_DAY_IMAGE;
    if (iconName.equals("partly-cloudy-night")) return PARTLY_CLOUDY_NIGHT_IMAGE;
    throw new IllegalStateException();
  }

  private WeatherHeader headerFrom(WeatherEntry entry) {
    Date date = new Date();
    String day = dayName(date);
    int icon = iconFor(entry.currently.icon);
    String url = urlFor(entry.currently.icon);
    return WeatherHeader.create(day, icon, String.valueOf(entry.currently.temperature), url,
        entry.currently.summary);
  }

  String dayName(Date date) {
    return date.toString().substring(0, 3).toUpperCase();
  }

  interface Service {
    @GET("/forecast/{key}/{latitude},{longitude}") Call<WeatherEntry> weather(
        @Path("key") String key, @Path("latitude") String latitude,
        @Path("longitude") String longitude);
  }
}
