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
package com.knomatic.weather.data.cache;

import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import java.util.List;

public final class WeatherMemoryCache implements WeatherCache {

  private WeatherHeader header;
  private List<Weather> weathers;

  @Override public WeatherHeader getHeader() {
    return header;
  }

  @Override public List<Weather> getWeather() {
    return weathers;
  }

  @Override public boolean isValid() {
    return header != null && weathers != null;
  }

  @Override public void store(WeatherHeader header, List<Weather> weathers) {
    this.header = header;
    this.weathers = weathers;
  }
}
