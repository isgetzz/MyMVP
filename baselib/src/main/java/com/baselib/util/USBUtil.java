package com.baselib.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.fs.UsbFileOutputStream;
import com.github.mjdev.libaums.fs.UsbFileStreamFactory;
import com.github.mjdev.libaums.partition.Partition;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * U盘操作
 */
public class USBUtil {

    public static UsbMassStorageDevice[] storageDevices;
    public static PendingIntent mPendingIntent;
    private static final String ACTION_USB_PERMISSION = "com.android.usb.USB_PERMISSION";
    private static final String ROOTPATH = "ZLProjectData";
    private static final String XMLPATH = "XmlData";
    private static final String RADIOPATH = "ZL_VIDEO";

    private static UsbFile readDeviceList(Context context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        storageDevices = UsbMassStorageDevice.getMassStorageDevices(context);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        if (storageDevices.length == 0) {
            return null;
        }
        for (UsbMassStorageDevice device : storageDevices) {
            if (usbManager.hasPermission(device.getUsbDevice())) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return readDevice(device);
            } else {
                usbManager.requestPermission(device.getUsbDevice(), mPendingIntent);
            }
        }
        return null;
    }

    private static UsbFile readDevice(UsbMassStorageDevice device) {
        try {
            device.init();
            Partition partition = device.getPartitions().get(0);
            FileSystem currentFs = partition.getFileSystem();
            return currentFs.getRootDirectory();
        } catch (IOException e) {
            return null;
        }
    }

    public static List<UsbFile> readFile(Context context) {
        UsbFile root = readDeviceList(context);
        if (root == null) {
            BaseUtil.Toast(context, "未检测到U盘");
            return null;
        }
        List<UsbFile> lists = new ArrayList<>();
        try {
            for (UsbFile f : root.listFiles()) {
                if (f.getName().equals(ROOTPATH) && f.isDirectory()) {
                    for (UsbFile uf : f.listFiles()) {
                        if ((uf.getName().equals(XMLPATH) || uf.getName().equals(RADIOPATH)) && uf.isDirectory()) {
                            lists.addAll(Arrays.asList(uf.listFiles()));
                        }
                    }
                }
            }
            return lists;
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeFile(Context context, String fileName, byte[] b) {
        UsbFile root = readDeviceList(context);
        if (root == null) {
            BaseUtil.Toast(context, "未检测到U盘");
            return;
        }
        try {
            boolean flag = false;
            for (UsbFile f : root.listFiles()) {
                if (f.getName().equals(ROOTPATH) && f.isDirectory()) {
                    flag = true;
                    for (UsbFile uf : f.listFiles()) {
                        if (uf.getName().equals(RADIOPATH) && uf.isDirectory() && fileName.contains(".amr")) {
                            writeFile(uf, fileName, b);
                        } else if (uf.getName().equals(XMLPATH) && uf.isDirectory() && !fileName.contains(".amr")) {
                            writeFile(uf, fileName, b);
                        }
                    }
                }
            }
            if (!flag) {
                UsbFile uf = root.createDirectory(ROOTPATH);
                uf.createDirectory(RADIOPATH);
                uf.createDirectory(XMLPATH);
                writeFile(context, fileName, b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(UsbFile uf, String fileName, byte[] b) {
        try {
            UsbFile file = null;
            for (UsbFile f : uf.listFiles()) {
                if (f.getName().equals(fileName)) {
                    file = f;
                }
            }
            file = file == null ? uf.createFile(fileName) : file;
            OutputStream out = new UsbFileOutputStream(file);
            out.write(b);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件到U盘
     *
     * @param context
     * @param hashMap
     */
    public static void UsbFileWrite(Context context, HashMap<String, byte[]> hashMap, PendingIntent pendingIntent) {
        //USB管理器
        UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        try {
            UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(context);
            for (UsbMassStorageDevice storageDevice : storageDevices) {
                //一般手机只有一个USB设备
                // 申请USB权限
                if (!mUsbManager.hasPermission(storageDevice.getUsbDevice())) {
                    mUsbManager.requestPermission(storageDevice.getUsbDevice(), pendingIntent);
                    break;
                }
                // 初始化
                storageDevice.init();
                // 获取分区
                List<Partition> partitions = storageDevice.getPartitions();
                if (partitions.size() == 0) {
                    BaseUtil.Toast(context, "未检测到U盘,请重新连接");
                    return;
                }
                // 仅使用第一分区
                FileSystem fileSystem = partitions.get(0).getFileSystem();
                boolean isExists = false;
                UsbFile root = fileSystem.getRootDirectory();
                UsbFile[] files = root.listFiles();
                for (UsbFile file : files) {
                    if (file.isDirectory() && file.getName().equals(ROOTPATH)) {
                        isExists = true;
                        for (UsbFile file1 : file.listFiles()) {
                            for (String key : hashMap.keySet()) {
                                for (UsbFile ZL_VIDEO : file1.listFiles()) {
                                    if (ZL_VIDEO.getName().equals(key)) {
                                        ZL_VIDEO.delete();
                                        break;
                                    }
                                }
                                if (file1.isDirectory() && file1.getName().equals(RADIOPATH) && key.contains(".amr")) {
                                    UsbFile newFile = file1.createFile(key);
                                    // 写文件到U盘
                                    // OutputStream os = new UsbFileOutputStream(newFile);
                                    OutputStream os = UsbFileStreamFactory.createBufferedOutputStream(newFile, fileSystem);
                                    os.write(hashMap.get(key));
                                    os.close();
                                } else if (file1.isDirectory() && file1.getName().equals(XMLPATH) && key.contains(".xml")) {
                                    UsbFile newFile = file1.createFile(key);
                                    OutputStream os = UsbFileStreamFactory.createBufferedOutputStream(newFile, fileSystem);
                                    os.write(hashMap.get(key));
                                    os.close();
                                }
                            }
                        }

                    }
                }
                if (!isExists) {
                    UsbFile usbFile = root.createDirectory(ROOTPATH);
                    usbFile.createDirectory(XMLPATH);
                    usbFile.createDirectory(RADIOPATH);
                }
                storageDevice.close();
          /*      // 写文件到本地
                // InputStream is = new UsbFileInputStream(newFile);
                InputStream is = UsbFileStreamFactory.createBufferedInputStream(newFile, fileSystem);
                byte[] buffer = new byte[fileSystem.getChunkSize()];
                int len;
                File sdFile = new File("/sdcard/111");
                sdFile.mkdirs();
                FileOutputStream sdOut = new FileOutputStream(sdFile.getAbsolutePath() + "/" + newFile.getName());
                while ((len = is.read(buffer)) != -1) {
                    sdOut.write(buffer, 0, len);
                }
                is.close();
                sdOut.close();*/
                BaseUtil.Toast(context, "已成功复制到U盘");
            }
        } catch (Exception e) {
            BaseUtil.Toast(context, "无法识别U盘,请重新连接");
        }
    }

}
