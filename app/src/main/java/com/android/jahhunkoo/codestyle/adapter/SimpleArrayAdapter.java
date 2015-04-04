package com.android.jahhunkoo.codestyle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.jahhunkoo.codestyle.R;
import com.android.jahhunkoo.codestyle.dto.BigRegionDTO;

import java.util.List;

/**
 * Created by Jahun Koo on 2015-04-04.
 */
public class SimpleArrayAdapter extends ArrayAdapter<BigRegionDTO>{

    public SimpleArrayAdapter(Context context, List<BigRegionDTO> objects) {
        super(context, R.layout.item_simple, objects);
    }

    private static class ViewHolder{
        public final TextView regionNameTextView;

        public ViewHolder(View view){
            regionNameTextView = (TextView) view.findViewById(R.id.textview_item_simple);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_simple, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        BigRegionDTO bigRegion = getItem(position);

        viewHolder.regionNameTextView.setText(bigRegion.getRegionName());

        return convertView;
    }

}
