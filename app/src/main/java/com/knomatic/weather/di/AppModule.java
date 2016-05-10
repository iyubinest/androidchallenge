/**
 * Copyright (C) 2015 Cristian Gómez Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
