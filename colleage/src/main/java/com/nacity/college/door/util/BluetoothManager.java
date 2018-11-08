package com.nacity.college.door.util;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by xz on 2016/7/12.
 **/
public class BluetoothManager {
    public static boolean isBluetoothSupported() {
        return BluetoothAdapter.getDefaultAdapter() != null ? true : false;
    }


    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled();
        }

        return false;
    }

    /**
     * 39
     * 强制开启当前 Android 设备的 Bluetooth
     * 40
     * <p>
     * 41
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     * 42
     */
    public static boolean turnOnBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }

        return false;
    }

    public static boolean turnOffBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }

        return false;
    }
}

