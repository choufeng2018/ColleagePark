package com.nacity.college.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.college.common_libs.domain.user.MainMenuTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;

import java.util.List;


/**
 * Created by xzz on 2017/6/25.
 **/

public class PropertyServiceBannerView implements Holder<List<MainMenuTo>> {
    private GridLayout propertyServiceLayout;
    private Context mContext;

    @Override
    public View createView(Context context) {
        this.mContext = context;
        View propertyServiceView = View.inflate(context, R.layout.property_service_banner_view, null);

        propertyServiceLayout = (GridLayout) propertyServiceView.findViewById(R.id.property_service_layout);
        return propertyServiceView;
    }

    @Override
    public void UpdateUI(Context context, int position, List<MainMenuTo> data) {
        for (int i = 0; i < 8&&i<data.size(); i++) {
            View mView = View.inflate(context, R.layout.main_property_service_item, null);
            mView.setTag(data.get(i).getCode() + "");
            ImageView propertyImage = (ImageView) mView.findViewById(R.id.property_image);
            TextView propertyName = (TextView) mView.findViewById(R.id.property_text);
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(data.get(i).getPicUrl1())).into(propertyImage);
            propertyName.setText(data.get(i).getShowName());
            mView.setTag(R.id.park_service_name,data.get(i).getShowName());
            mView.setOnClickListener(v -> {
                if (listener != null)
                    listener.onPropertyServiceClick((String) v.getTag(), v);

            });
            propertyServiceLayout.addView(mView);
        }
    }


    public interface PropertyServiceOnClickListener {
        void onPropertyServiceClick(String type, View serviceName);
    }

    private PropertyServiceOnClickListener listener;

    public void setOnPropertyServiceOnClickListener(PropertyServiceOnClickListener listener) {
        this.listener = listener;
    }

}
