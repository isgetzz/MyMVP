package com.baselib.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.baselib.R;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.baselib.util.CodeUtil.INTENT_REQUEST_CODE;
import static com.baselib.util.CodeUtil.READ_PHONE_STATE;


/**
 * 基础数据 Created by cc on 2018/3/7.
 */
public class BaseUtil {
    public static Toast toast;
    //登录失败
    public final static int LGOIN_FALI_CODE = -1;
    //登录成功
    public final static int LGOIN_SUCCEE_CODE = 1;

    //清除缓存
    public static void syncCookie() {
     /*   //安卓自带浏览器内核
        android.webkit.CookieSyncManager.createInstance(UserApplication.getInstance());
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        cookieManager.removeAllCookie();*/
        //X5浏览器内核
     /*   CookieSyncManager.createInstance(UserApplication.getInstance());
        CookieManager x5cookieManager = CookieManager.getInstance();
        x5cookieManager.removeAllCookie();*/
    }

    /**
     * @param activity            判断是否有权限
     * @param Manifest_Permission 权限名称
     * @return
     */
    static boolean IsPermission(Activity activity, String Manifest_Permission) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest_Permission);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取两位小数
     *
     * @param value v
     * @return 两位小数
     */
    public static String getValue(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String distanceString = decimalFormat.format(value);//format 返回的是字符串
        return getMoney(distanceString);
    }

    /**
     * 三位加一个逗号
     *
     * @param str s
     * @return
     */
    private static String getMoney(String str) {
        String date = str.substring(0, str.indexOf("."));
        String dot = str.substring(str.indexOf("."), str.length());
        String money = new StringBuilder(date).reverse().toString();     //先将字符串颠倒顺序
        String str2 = "";
        for (int i = 0; i < money.length(); i++) {
            if (i * 3 + 3 > money.length()) {
                str2 += money.substring(i * 3, money.length());
                break;
            }
            str2 += money.substring(i * 3, i * 3 + 3) + ",";
        }
        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        return new StringBuilder(str2).reverse().toString() + dot;
    }

    //获取手机IMEI
    @SuppressLint("HardwareIds")
    public static String getIMEI1(Context context) {
        String deviceId = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
            } else {
                if (tm.getDeviceId() != null) {
                    deviceId = tm.getDeviceId();
                } else {
                    deviceId = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            }
        }
        return deviceId;
    }

    /**
     * 文本设置特殊字体
     *
     * @param content 文字
     * @param str     需要设置颜色的文字
     * @return
     */
    public static SpannableStringBuilder getSpan(String content, String str, int color) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);
        int start = content.indexOf(str);
        int end = start + str.length();
        stringBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }

    /**
     * 生成随机数字和字母,
     *
     * @param length 表示生成几位随机数
     * @return
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static void Toast(Context context, String text) {
        if (toast == null) {
            toast = makeText(context, text);
        } else {
            ((TextView) toast.getView().findViewById(R.id.tv_info)).setText(text);
        }
        toast.show();
    }

    /**
     * 自定义Toast
     *
     * @param context
     * @param msg     显示文本
     * @return
     */
    private static Toast makeText(Context context, String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.getBackground().setAlpha(100);
        tv.setText(msg);
        toast.setDuration(Toast.LENGTH_SHORT);
        // toast.setGravity(Gravity.BOTTOM, 0, 100);
        return toast;
    }

    public static void MyIntent(Context context, Class c) {
        Intent intent = new Intent();
        intent.setClass(context, c);
        context.startActivity(intent);
    }

    /**
     * 跳到系统应用界面
     *
     * @param context
     * @param Settings
     * @param uri
     */
    public static void MyIntent(Context context, String Settings, Uri uri) {
        Intent intentUri = new Intent();
        intentUri.setAction(Settings);
        intentUri.setData(uri);
        context.startActivity(intentUri);
    }

    /**
     * 发送广播
     *
     * @param activity
     * @param load     是否加载了x5webview
     */
    static void MyIntentBroadcast(Context activity, Boolean load) {
        Intent MyIntentBroadcast = new Intent("initX5");
        MyIntentBroadcast.putExtra("code", 888);
        MyIntentBroadcast.putExtra("initX5", load);
        activity.sendBroadcast(MyIntentBroadcast);
    }

    /**
     * 带链接跳转
     *
     * @param context
     * @param c       类
     * @param LinkUrl 链接
     */
    public static void MyIntent(Context context, Class c, String LinkUrl) {
        Intent intentLinkUrl = new Intent();
        intentLinkUrl.setClass(context, c);
        intentLinkUrl.putExtra("LinkUrl", LinkUrl);
        context.startActivity(intentLinkUrl);
    }

    /**
     * 带链接跳转
     *
     * @param context
     * @param c          类
     * @param title      标题
     * @param showSearch 链接
     */
    public static void MyIntent(Context context, Class c, String title, boolean showSearch, int category_id) {
        Intent intentTitle = new Intent();
        intentTitle.setClass(context, c);
        intentTitle.putExtra("title", title);
        intentTitle.putExtra("showSearch", showSearch);
        intentTitle.putExtra("category_id", category_id);
        context.startActivity(intentTitle);
    }

    /**
     * activity 跳转返回会调
     *
     * @param activity
     * @param c
     */
    public static void MyResultIntent(Activity activity, Class c) {
        Intent intentResult = new Intent();
        intentResult.setClass(activity, c);
        activity.startActivityForResult(intentResult, INTENT_REQUEST_CODE);
    }

    /**
     * activity 跳转返回会调
     *
     * @param activity
     * @param c
     */
    public static void MyResultIntent(Activity activity, Class c, String str) {
        Intent intentResult = new Intent();
        intentResult.putExtra("LinkUrl", str);
        intentResult.setClass(activity, c);
        activity.startActivityForResult(intentResult, INTENT_REQUEST_CODE);
    }

    /**
     * fragment 跳转返回会调
     *
     * @param fragment
     * @param c
     */
    public static void MyResultIntent(Fragment fragment, Class c) {
        Intent intentResult = new Intent();
        intentResult.setClass(fragment.getActivity(), c);
        fragment.startActivityForResult(intentResult, INTENT_REQUEST_CODE);
    }

    /**
     * fragment 跳转返回会调
     *
     * @param fragment
     * @param c
     */
    public static void MyResultIntent(Fragment fragment, Class c, String str) {
        Intent intentResult = new Intent();
        intentResult.putExtra("LinkUrl", str);
        intentResult.setClass(fragment.getActivity(), c);
        fragment.startActivityForResult(intentResult, INTENT_REQUEST_CODE);
    }

    /**
     * 拨号显示号码页面
     *
     * @param activity
     * @param num      号码
     */
    public static void call(Activity activity, String num) {
        Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse(num));
        activity.startActivityForResult(intentPhone, 1);
    }

    /**
     * 获取屏幕精密度值
     *
     * @param activity
     */
    public static DisplayMetrics getMeasure(Activity activity) {
        return activity.getResources().getDisplayMetrics();
    }

    /**
     * 判断软键盘是否显示
     *
     * @return boolean
     */
    public static void isSoftShowing(Activity context, View view) {
        //获取当屏幕内容的高度
        int screenHeight = context.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        if (screenHeight * 2 / 3 > rect.bottom)
            hideInput(view);
    }

    public static void hideInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    /**
     * //开启软键盘
     *
     * @param view 关联view
     */
    public static void shopInput(final View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        new Timer().schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(view, 0);
            }
        }, 300);
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable Drawable 值
     * @return Bitmap 值
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /* *//**
     * 无圆角option
     *
     * @return 加载时显示默认图片
     *//*
    public static RequestOptions getRequestOptions() {
        return new RequestOptions().error(ContextCompat.getDrawable(UserApplication.getInstance(), R.drawable.default_image)).placeholder(ContextCompat.getDrawable(UserApplication.getInstance(), R.drawable.default_image)).fallback(ContextCompat.getDrawable(UserApplication.getInstance(), R.drawable.default_image));
    }

    *//**
     * 获取 SharedPreferences实例
     *
     * @return
     *//*
    public static SharedPreferences getShare() {
        return UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
    }
*/
    /* *//**
     * 集合put
     *
     * @param hashtable 集合
     *//*
    public static void SharePut(Hashtable<String, String> hashtable) {
        SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String str : hashtable.keySet()
        ) {
            editor.putString(str, hashtable.get(str));
        }
        editor.apply();
    }

    *//**
     * 存储token
     *
     * @param token
     *//*
    public static void SharePutToken(String token) {
        SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", token);
        editor.apply();
    }

    //存储有序数组
    public static <T> void putHistory(List<T> list) {
        // 注释1
        Type personType = new TypeToken<List<T>>() {
        }.getType();
        Gson gson = new Gson();
        String json = gson.toJson(list, personType);
        SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("history", json);
        editor.apply();
    }

    */

    /**
     * 存储token、user_phone
     *
     * @param token      用户登录标识
     * @param user_phone 用户手机号
     *//*
    public static void SharePutToken(String token, String user_phone) {
        SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", token);
        editor.putString("user_phone", user_phone);
        editor.apply();
    }

    public static String SharegetToken() {
        SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }

    public static String SharegetUserPhone() {
        SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_phone", "");
    }

    public static <T> List getHistory(Class<T> clazz) {
        List<T> list = null;
        try {
            SharedPreferences sharedPreferences = UserApplication.getInstance().getSharedPreferences("gaoluo", Context.MODE_PRIVATE);
            String string = sharedPreferences.getString("history", null);
            Gson gson = new Gson();
            //注释2
            Type type = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
            list = gson.fromJson(string, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList() : list;
    }

    */

    /**
     * 获取系统版本号
     *
     * @param context 获取context 所在模块的版本号
     * @return code
     */
    public static int getVersionCode(Context context) {
        int code = 1;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                code = (int) info.getLongVersionCode();
            } else {
                code = info.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取资源图片【和主体有关】
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        }
        return context.getResources().getDrawable(resId);
    }

    /**
     * 获取应用的图标
     *
     * @param context
     * @return
     */
    public static Drawable getAppIcon(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void glide() {
/*    Glide 图片获取对象
        Glide.with(this).load(arrayList.get(0).get("name").toString()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                MenuItem item = menu.findItem(R.id.list_item1);
                item.setIcon(resource);
                item.setVisible(list_item1);
                Log.e("list_item3", "===" + arrayList.get(0).get("name").toString());
            }
        });
*/
    }

    /**
     * @param input String类型
     * @return String 返回的String为半角（英文）类型
     * @Description 解决textview的问题---半角字符与全角字符混乱所致；这种情况一般就是汉字与数字、英文字母混用
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * @param input String类型
     * @return String  返回的String为全角（中文）类型
     * @Description 解决textview的问题---半角字符与全角字符混乱所致；这种情况一般就是汉字与数字、英文字母混用
     */
    public static String toSBC(String input) { //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * @param str String类型
     * @return String
     * @Description 替换、过滤特殊字符
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll(" ", "").replaceAll(" ", "").replaceAll("：", ":").replaceAll("：", "：").replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");//替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
