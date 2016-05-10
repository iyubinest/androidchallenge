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
package com.knomatic.weather.ui.weather;

import android.support.annotation.DrawableRes;
import com.google.auto.value.AutoValue;

@AutoValue public abstract class WeatherHeader {

  public static WeatherHeader create(String date, @DrawableRes int icon, String temperature,
      String urlImage, String summary) {
    return new AutoValue_WeatherHeader(date, icon, temperature, urlImage, summary);
  }

  public abstract String date();

  public abstract @DrawableRes int icon();

  public abstract String temperature();

  public abstract String urlImage();

  public abstract String summary();
}
