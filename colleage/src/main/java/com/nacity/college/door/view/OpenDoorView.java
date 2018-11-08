package com.nacity.college.door.view;

import com.college.common_libs.domain.door.OpenDoorListTo;
import com.college.common_libs.domain.door.OpenDoorTypeTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 * Created by xzz on 2018/1/28.
 **/

public interface OpenDoorView extends BaseView {

   void  getApartmentDoorStatueSuccess(OpenDoorTypeTo doorTypeTo);


   void getDoorTypeFail();


   void getDoorListSuccess(List<OpenDoorListTo> doorList);

   void toApplyDoor();

   void getDoorListFail();


}
