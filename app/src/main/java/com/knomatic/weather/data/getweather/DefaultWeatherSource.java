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
package com.knomatic.weather.data.getweather;

import com.knomatic.weather.data.cache.WeatherCache;
import com.knomatic.weather.data.gps.GPS;
import com.knomatic.weather.data.weather.WeatherService;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import com.knomatic.weather.ui.weather.WeatherSource;
import java.util.List;

public final class DefaultWeatherSource implements WeatherSource {
  private final GPS gps;
  private final WeatherService service;
  private final WeatherCache cache;
  private Callback callback;

  public DefaultWeatherSource(GPS gps, WeatherService service, WeatherCache cache) {
    this.gps = gps;
    this.service = service;
    this.cache = cache;
  }

  @Override public void weather(Callback callback) {
    this.callback = callback;
    if (cache.isValid()) {
      callback.onSuccess(cache.getHeader(), cache.getWeather());
    } else {
      locate();
    }
  }

  private void locate() {
    gps.locate(new GPS.Callback() {
      @Override public void onFailure() {
        callback.onFailure();
      }

      @Override public void onSuccess(double lat, double lon) {
        weathers(lat, lon);
      }
    });
  }

  private void weathers(double lat, double lon) {
    service.get(lat, lon, new WeatherService.Callback() {
      @Override public void onFailure() {
        callback.onFailure();
      }

      @Override public void onSuccess(WeatherHeader header, List<Weather> weathers) {
        cache.store(header, weathers);
        callback.onSuccess(header, weathers);
      }
    });
  }
}
