package com.nacity.college.property.model;

import java.util.ArrayList;

/**
 * Created by usb on 2017/10/23.
 */

public interface ParkingModel {
    void submitParking(String userName, String carNo, String loadType, String loadArea, String applyDead, String otherDesc);
    void getUpImageToken();
    void uploadImage(ArrayList<String> pathList, String token);

}
