package com.iyoyogo.android.app;


import com.iyoyogo.android.camera.utils.asset.NvAsset;

public class Constants {
    // http://app.iyoyogo.com
    //http://192.168.0.145
    public static final String BASE_URL="http://192.168.0.145/";
    /**
     * 美摄使用
     */
    public static final String KEY_PARAMTER = "paramter";

    public static final int EDIT_MODE_CAPTION = 0;
    public static final int EDIT_MODE_STICKER = 1;
    public static final int EDIT_MODE_WATERMARK = 2;
    public static final long NS_TIME_BASE = 1000000;

    public static final int MEDIA_TYPE_AUDIO = 1;

    public static final int ACTIVITY_START_CODE_MUSIC_SINGLE = 100;
    public static final int ACTIVITY_START_CODE_MUSIC_MULTI = 101;

    public static final String START_ACTIVITY_FROM_CAPTURE = "start_activity_from_capture";
    public static final String CAN_USE_ARFACE_FROM_MAIN = "can_use_arface_from_main";

    public static final int FROMMAINACTIVITYTOVISIT = 1001;//从主页面进入视频选择页面
    public static final int FROMCLIPEDITACTIVITYTOVISIT = 1002;//从片段编辑页面进入视频选择页面

    //图片运动
    public static final int EDIT_MODE_PHOTO_AREA_DISPLAY = 2001;//图片运动-区域显示
    public static final int EDIT_MODE_PHOTO_TOTAL_DISPLAY = 2002;//图片运动-全图显示

    //自定义贴纸
    public static final int CUSTOMSTICKER_EDIT_FREE_MODE = 2003;//自由
    public static final int CUSTOMSTICKER_EDIT_CIRCLE_MODE = 2004;//圆形
    public static final int CUSTOMSTICKER_EDIT_SQUARE_MODE = 2005;//正方


    public static final String NO_FX = "None"; // 无特效的ID

    // capture
    public static final int RECORD_TYPE_NULL = 3000;
    public static final int RECORD_TYPE_PICTURE = 3001;
    public static final int RECORD_TYPE_VIDEO = 3002;

    public static final String SELECT_MEDIA_FROM = "select_media_from"; // key
    public static final int SELECT_IMAGE_FROM_WATER_MARK = 4001;        // 从水印入口进入图片选择页面
    public static final int SELECT_IMAGE_FROM_MAKE_COVER = 4002;        // 从制作封面入口进入图片选择页面
    public static final int SELECT_IMAGE_FROM_CUSTOM_STICKER = 4003;    // 从自定义贴纸入口进入图片选择页面
    public static final int SELECT_VIDEO_FROM_PARTICLE = 4004;          // 从粒子入口进入视频选择页面

    public static final int POINT16V9 = NvAsset.AspectRatio_16v9;
    public static final int POINT1V1 = NvAsset.AspectRatio_1v1;
    public static final int POINT9V16 = NvAsset.AspectRatio_9v16;
    public static final int POINT3V4 = NvAsset.AspectRatio_3v4;
    public static final int POINT4V3 = NvAsset.AspectRatio_4v3;

    // music
    public static final String MUSIC_EXTRA_AUDIOCLIP = "extra";
    public static final String MUSIC_EXTRA_LAST_AUDIOCLIP = "extra_last";
    public static final long MUSIC_MIN_DURATION = 1000000;

    // douyin
    public static final int REQUEST_CODE_DOUYIN_EDIT = 500;

    //视音频音量值
    public static final float VIDEOVOLUME_DEFAULTVALUE = 2.0f;
    public static final float VIDEOVOLUME_MAXVOLUMEVALUE = 8.0f;
    public static final int VIDEOVOLUME_MAXSEEKBAR_VALUE = 100;

    //屏幕点击常量定义
    public final static int HANDCLICK_DURATION = 200;//点击时长，单位微秒
    public final static double HANDMOVE_DISTANCE = 10.0D;//touch移动距离，单位像素值


    final static public String PERSON_ZUJI_ID = "person_zuji_id";
    final static public String PERSON_MEIPAI_ID = "person_meipai_id";
    final static public String COMMENT_ID = "comment_id";
    final static public String DETAIL_ID = "detail";

    //照片位置精度
    final static public int PIC_LOCATION_LIMITS = 1000;

    final static public String PHOTO = "photo";
    final static public String TAG_LIST = "tag_list";

    final static public String MY_ADD_ADDRESS = "my_add_address";
    final static public String VIDEO_URL = "video_url";

    final static public String PARAM_ZIJI_POST_DATA = "param_zuji_post_data";
    final static public String PARAM_ZIJI_POST_DATA_LIST = "param_zuji_post_data_list";
    public static final int Comment_Type_MeIPai = 1;
    public static final int Comment_Type_ZuJi = 2;
    public static final String Comment_Type = "comment_type";
    //按钮点击时间间隔
    public static final int ClickDuration = 1;
    final static public String PARAM_CITY = "param_city";

    final static public String PARAM_CHANNEL = "param_channel";
    final static public String PARAM_ADDRESS = "param_address";
    final static public String PARAM_VIDEO_MP4 = "video/mp4";

    final static public String PARAM_BIND_PHONE = "param_bind_phone";

    final static public String PARAM_IMAGE_LIST_DATA = "param_image_list_data";
    final static public String USER_OTHER_ID = "user_other_id";
    final static public String PARAM_CUSTOM_ADDR = "param_custom_addr";
    final static public String PARAM_SEARCH_ADDR = "param_search_addr";
    final static public String PARAM_SHOW_DISLIKE = "param_show_dislike";
    final static public String PARAM_FOOTPOINT_DATA = "param_footpoint_data";
    final static public String PARAM_FOOTPOINT_IMAGE_SELECT = "param_footpoint_image_select";
    final static public String PARAM_FOOTPOINT_TAG_LIST = "param_footpoint_tag_list";
    final static public String PARAM_CLASS = "param_class";
    final static public String PARAM_SHOW_PLACE = "param_show_PLACE";
    final static public String PARAM_LOCATION = "param_location";
}
