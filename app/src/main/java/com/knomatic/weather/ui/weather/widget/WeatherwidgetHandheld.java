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
package com.knomatic.weather.ui.weather.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.knomatic.weather.R;
import com.knomatic.weather.ui.ImageLoader;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import java.util.List;

public class WeatherwidgetHandheld extends RecyclerView implements WeatherWidget {
  private Adapter adapter;

  public WeatherwidgetHandheld(Context context) {
    super(context);
    init();
  }

  public WeatherwidgetHandheld(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WeatherwidgetHandheld(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  @Override public void header(WeatherHeader header) {
    adapter.header(header);
  }

  @Override public void weathers(List<Weather> weathers) {
    adapter.weathers(weathers);
  }

  private void init() {
    final GridLayoutManager manager =
        new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grids));
    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return position == 0 ? manager.getSpanCount() : 1;
      }
    });
    setLayoutManager(manager);
    setHasFixedSize(false);
    adapter = new Adapter();
    setAdapter(adapter);
  }

  private class Adapter extends RecyclerView.Adapter {

    private static final int HEADER = 100;
    private WeatherHeader header;
    private List<Weather> weathers;

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      if (viewType == HEADER) {
        return new HeaderHolder(
            LayoutInflater.from(getContext()).inflate(R.layout.weather_header_item, parent, false));
      }

      return new Holder(
          LayoutInflater.from(getContext()).inflate(R.layout.weather_item, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      if (holder instanceof HeaderHolder) ((HeaderHolder) holder).header(header);
      if (holder instanceof Holder) ((Holder) holder).weather(weathers.get(position - 1));
    }

    @Override public int getItemViewType(int position) {
      if (position == 0) return HEADER;
      return super.getItemViewType(position);
    }

    @Override public int getItemCount() {
      if (header == null || weathers == null) return 0;
      return 1 + weathers.size();
    }

    public void header(WeatherHeader header) {
      this.header = header;
      notifyDataSetChanged();
    }

    public void weathers(List<Weather> weathers) {
      this.weathers = weathers;
      notifyDataSetChanged();
    }
  }

  private class Holder extends RecyclerView.ViewHolder {

    private final ImageView iconView;
    private final TextView dayView;
    private final TextView temperatureView;

    public Holder(View itemView) {
      super(itemView);
      iconView = (ImageView) itemView.findViewById(R.id.weather_icon);
      dayView = (TextView) itemView.findViewById(R.id.weather_day);
      temperatureView = (TextView) itemView.findViewById(R.id.weather_temperature);
    }

    public void weather(Weather weather) {
      iconView.setImageResource(weather.icon());
      dayView.setText(weather.day());
      temperatureView.setText(weather.temperature());
    }
  }

  private class HeaderHolder extends RecyclerView.ViewHolder {
    private final ImageView iconView;
    private final ImageView backgroundView;
    private final TextView dayView;
    private final TextView temperatureView;
    private final TextView summaryView;

    public HeaderHolder(View itemView) {
      super(itemView);
      iconView = (ImageView) itemView.findViewById(R.id.weather_icon);
      backgroundView = (ImageView) itemView.findViewById(R.id.weather_background);
      dayView = (TextView) itemView.findViewById(R.id.weather_day);
      temperatureView = (TextView) itemView.findViewById(R.id.weather_temperature);
      summaryView = (TextView) itemView.findViewById(R.id.weather_summary);
    }

    public void header(WeatherHeader header) {
      iconView.setImageResource(header.icon());
      dayView.setText(header.date());
      temperatureView.setText(header.temperature());
      summaryView.setText(header.summary());
      ImageLoader.load(backgroundView, header.urlImage());
    }
  }
}
