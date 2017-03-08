package com.nocompany.frank.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nocompany.frank.weather.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Frank on 2017/3/7.
 * SearchActivity中的List控件的Adapter
 */

public class CityAdapter extends BaseAdapter implements Filterable{

    private Context mContext;
    private List<String> mList;
    private CityFilter mCityFilter;

    public CityAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        ViewHolder viewHolder;

        if (view == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_city, null);

            viewHolder = new ViewHolder();
            viewHolder.tvHolder = (TextView) v.findViewById(R.id.item_city);
            v.setTag(viewHolder);
        } else {
            v = view;
            viewHolder = (ViewHolder) v.getTag();
        }


        viewHolder.tvHolder.setText(mList.get(i));

        return v;
    }

    @Override
    public Filter getFilter() {
        if (mCityFilter == null) {
            mCityFilter = new CityFilter(mList);
        }
        return mCityFilter;
    }

    // 输入歌曲，筛选
    private class CityFilter extends Filter {

        private List<String> mOriginal;

        CityFilter(List<String> list) {
            mOriginal = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filterResults.values = mOriginal;
                filterResults.count = mOriginal.size();
            } else {
                List<String> list = new ArrayList<>();
                String pattern = "";
                String chip = ".*";
                for (int i = 0; i < constraint.length(); i++) {
                    pattern += chip + constraint.toString().charAt(i);
                }
                for (String s : mOriginal) {
                    if (s.matches("^" + pattern + ".*$")) {
                        list.add(s);
                    }
                }
                filterResults.values = list;
                filterResults.count = list.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (List<String>) results.values;

            notifyDataSetChanged();     // 通知适配器数据集改变
        }
    }

    private static class ViewHolder {
        private TextView tvHolder;
    }
}
