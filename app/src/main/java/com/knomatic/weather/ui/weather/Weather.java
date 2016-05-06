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

@AutoValue public abstract class Weather {

  public static Weather create(String day, @DrawableRes int icon, String temperature) {
    return new AutoValue_Weather(day, icon, temperature);
  }

  public abstract String day();

  public abstract @DrawableRes int icon();

  public abstract String temperature();
}
