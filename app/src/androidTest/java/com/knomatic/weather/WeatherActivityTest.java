package com.knomatic.weather;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class) @LargeTest public class WeatherActivityTest {
  @Rule public final ActivityTestRule<WeatherActivity> rule;

  public WeatherActivityTest() {
    rule = new ActivityTestRule<>(WeatherActivity.class);
  }

  @Test public void showInfo() {
    onView(withId(R.id.weather)).check(matches(withEffectiveVisibility(VISIBLE)));
  }
}