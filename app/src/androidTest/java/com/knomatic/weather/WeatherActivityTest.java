package com.knomatic.weather;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.knomatic.weather.data.gps.GPS;
import com.knomatic.weather.di.AppComponent;
import com.knomatic.weather.di.AppModule;
import com.knomatic.weather.di.DaggerAppComponent;
import org.junit.Before;
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

  @Before public void before() {
    App app = ((App) InstrumentationRegistry.getTargetContext().getApplicationContext());
    AppComponent injector = DaggerAppComponent.builder().appModule(new AppModule(app) {
      @Override public GPS gps() {
        return new GPS() {
          @Override public void locate(Callback callback) {
            callback.onSuccess(-82.411629, 28.054553);
          }
        };
      }
    }).build();
    app.setComponent(injector);
  }

  @Test public void showInfo() {
    onView(withId(R.id.weather)).check(matches(withEffectiveVisibility(VISIBLE)));
  }
}