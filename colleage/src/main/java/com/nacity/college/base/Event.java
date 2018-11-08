package com.nacity.college.base;

import android.view.View;


/**
 * Created by xzz on 2017/7/2.
 **/

public class Event<T> {
    private String type;
    private T mode;
    private View mView;
    private int position;
    public Event(String type, T mode){
        this.type=type;
        this.mode=mode;
    }

    public Event(String type, T mode, View mView){
        this.type=type;
        this.mode=mode;
        this.mView=mView;
    }
    public Event(String type, T mode, int position){
        this.type=type;
        this.mode=mode;
        this.position=position;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getMode() {
        return mode;
    }

    public void setMode(T mode) {
        this.mode = mode;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
