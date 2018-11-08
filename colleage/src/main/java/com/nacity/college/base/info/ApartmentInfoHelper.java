package com.nacity.college.base.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.college.common_libs.domain.apartment.ApartmentInfoTo;


/**
 * Created by Admin on 2015-03-05
 */
public class ApartmentInfoHelper {

    protected static ApartmentInfoHelper instance;

    private ApartmentInfoTo apartmentInfoTo;
    private Context mContext;

    public ApartmentInfoHelper(Context context) {
         mContext = context;
        load();
    }

    public static ApartmentInfoHelper getInstance(Context context) {
        if(instance == null) {
            synchronized (ApartmentInfoHelper.class){
                if (instance == null) {
                    instance = new ApartmentInfoHelper(context);
                }
            }
        }

        instance.mContext = context;
        return instance;
    }

    public ApartmentInfoTo getApartmentInfoTo() {

        return apartmentInfoTo;
    }

    public String getSid(){
        return apartmentInfoTo == null ? "" : apartmentInfoTo.getGardenId();
    }

    public String getGardenName() {
        if (apartmentInfoTo == null) return "";
        if (!TextUtils.isEmpty(apartmentInfoTo.getGardenName())) {
            return apartmentInfoTo.getGardenName();
        }

//        if (apartmentInfoTo.getPlace() != null) {
//            return apartmentInfoTo.getPlace().getName();
//        }
        return "";
    }

 public String getApartmentAddress(){
     if (apartmentInfoTo == null)  return "";
//
//     if (apartmentInfoTo.getPlace().getAddress() != null) {
//               return apartmentInfoTo.getPlace().getAddress();
//     }

     return "";
 }
    public void updateApartment(ApartmentInfoTo apartment) {
        if(apartment == null) return;
        apartmentInfoTo = apartment;
        save();
    }

    public void save() {

        ConfigUtil.saveString(PreferenceManager.getDefaultSharedPreferences(mContext),
                KeyValue.KEY_APARTMENT_DATA,
                JSON.toJSONString(apartmentInfoTo));

    }


    private void load() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        String apartmentJson = ConfigUtil.getString(sp, KeyValue.KEY_APARTMENT_DATA, "");
        if (!TextUtils.isEmpty(apartmentJson)) {
            apartmentInfoTo = JSON.parseObject(apartmentJson, ApartmentInfoTo.class);
        }
    }


}
