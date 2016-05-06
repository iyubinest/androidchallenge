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
package com.knomatic.weather.di;

import com.knomatic.weather.App;
import com.knomatic.weather.BuildConfig;
import com.knomatic.weather.data.cache.WeatherCache;
import com.knomatic.weather.data.cache.WeatherMemoryCache;
import com.knomatic.weather.data.getweather.DefaultWeatherSource;
import com.knomatic.weather.data.gps.AndroidGPS;
import com.knomatic.weather.data.gps.GPS;
import com.knomatic.weather.data.weather.RetrofitService;
import com.knomatic.weather.data.weather.WeatherService;
import com.knomatic.weather.ui.weather.WeatherSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Module public class AppModule {

  private final App app;

  public AppModule(App app) {
    this.app = app;
  }

  @Provides public App app() {
    return app;
  }

  @Singleton @Provides
  public WeatherSource weatherSource(GPS gps, WeatherService service, WeatherCache cache) {
    return new DefaultWeatherSource(gps, service, cache);
  }

  @Singleton @Provides public WeatherCache weatherCache() {
    return new WeatherMemoryCache();
  }

  @Provides public GPS gps() {
    return new AndroidGPS(app);
  }

  @Provides public WeatherService weatherService(Retrofit retrofit) {
    return new RetrofitService(retrofit);
  }

  @Provides public Retrofit retrofit() {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build();
  }
}
