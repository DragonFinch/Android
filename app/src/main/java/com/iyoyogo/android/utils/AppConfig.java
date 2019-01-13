package com.iyoyogo.android.utils;

import android.os.Environment;

import java.io.File;

/**
 * ***********************************************
 * **                  _oo0oo_                  **
 * **                 o8888888o                 **
 * **                 88" . "88                 **
 * **                 (| -_- |)                 **
 * **                 0\  =  /0                 **
 * **               ___/'---'\___               **
 * **            .' \\\|     |// '.             **
 * **           / \\\|||  :  |||// \\           **
 * **          / _ ||||| -:- |||||- \\          **
 * **          | |  \\\\  -  /// |   |          **
 * **          | \_|  ''\---/''  |_/ |          **
 * **          \  .-\__  '-'  __/-.  /          **
 * **        ___'. .'  /--.--\  '. .'___        **
 * **     ."" '<  '.___\_<|>_/___.' >'  "".     **
 * **    | | : '-  \'.;'\ _ /';.'/ - ' : | |    **
 * **    \  \ '_.   \_ __\ /__ _/   .-' /  /    **
 * **====='-.____'.___ \_____/___.-'____.-'=====**
 * **                  '=---='                  **
 * ***********************************************
 * **              佛祖保佑  镇类之宝              **
 * ***********************************************
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2014-09-05 one_one:49
 */
public class AppConfig {

    /**
     *
     */
    /****
     * SD卡文件位置
     */
    public static final String SDKA = Environment.getExternalStorageDirectory() + File.separator;
    /**
     * 下载的文件存放的根目录
     */
    public static final String FILE_DOWNLOAD = SDKA + "yoyogo/";
    public static final String DEBUG_TAG = "111";// LogCat的标记
    public static final boolean DEBUG_ENABLE = false;// 是否调试模式
    public static final String VOICE = FILE_DOWNLOAD + "apk/";
}
