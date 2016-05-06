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
