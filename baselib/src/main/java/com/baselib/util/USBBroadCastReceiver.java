package com.baselib.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

/**
 * Author ： cc
 * Time : 2019/5/10 10:07
 * Description : USB 广播
 */

public class USBBroadCastReceiver extends BroadcastReceiver {

    private UsbListener usbListener;
    public static final String ACTION_USB_PERMISSION = "USB_PERMISSION";

    public USBBroadCastReceiver(UsbListener usbListener) {
        this.usbListener = usbListener;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        if (action == null)
            return;
        switch (action) {
            case ACTION_USB_PERMISSION:
                //用户授权广播
                synchronized (this) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) { //允许权限申请
                        if (usbListener != null) {
                            usbListener.getReadUsbPermission(usbDevice);
                        }
                    } else {
                        if (usbListener != null) {
                            usbListener.failedReadUsb(usbDevice);
                        }
                    }
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                //USB设备插入广播
                if (usbListener != null) {
                    usbListener.insertUsb(usbDevice);
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED:
                //USB设备拔出广播
                if (usbListener != null) {
                    usbListener.removeUsb(usbDevice);
                }
                break;
        }
    }

    /**
     * USB 操作监听
     */
    public interface UsbListener {
        //USB 插入
        void insertUsb(UsbDevice device_add);

        //USB 移除
        void removeUsb(UsbDevice device_remove);

        //获取读取USB权限
        void getReadUsbPermission(UsbDevice usbDevice);

        //读取USB信息失败
        void failedReadUsb(UsbDevice usbDevice);
    }

    private void initReceiver() {
        //注册广播,监听USB插入和拔出
       /* mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        intentFilter.addAction(ACTION_USB_PERMISSION);
        usbBroadCastReceiver = new USBBroadCastReceiver(this);
        registerReceiver(usbBroadCastReceiver, intentFilter);*/
    }

}
