package com.iyoyogo.android.utils;

import android.content.Context;
import android.text.TextUtils;


import com.iyoyogo.android.R;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.dataInfo.VideoClipFxInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/11/15.
 */

public class AssetFxUtil {

    // 获取滤镜数据
    public static ArrayList<FilterItem> getFilterData(Context context,
                                                      ArrayList<NvAsset> filterAssetList,
                                                      List<String> builtinVideoFxList,
                                                      boolean isAddCartoon,
                                                      boolean isFitRatio) {
        ArrayList<FilterItem> filterList = new ArrayList<>();
        FilterItem            filterItem = new FilterItem();
        filterItem.setFilterName("原图");
        filterItem.setImageId(R.mipmap.filter_default);
        filterList.add(filterItem);

//        if (isAddCartoon) {
//            // 新增漫画特效
//            FilterItem cartoon1 = new FilterItem();
//            cartoon1.setIsCartoon(true);
//            cartoon1.setFilterName("水墨");
//            cartoon1.setImageId(R.mipmap.sage);
//            cartoon1.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
//            cartoon1.setStrokenOnly(true);
//            cartoon1.setGrayScale(true);
//            filterList.add(cartoon1);
//
//            FilterItem cartoon2 = new FilterItem();
//            cartoon2.setIsCartoon(true);
//            cartoon2.setFilterName("漫画书");
//            cartoon2.setImageId(R.mipmap.maid);
//            cartoon2.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
//            cartoon2.setStrokenOnly(false);
//            cartoon2.setGrayScale(false);
//            filterList.add(cartoon2);
//
//            FilterItem cartoon3 = new FilterItem();
//            cartoon3.setIsCartoon(true);
//            cartoon3.setFilterName("单色");
//            cartoon3.setImageId(R.mipmap.mace);
//            cartoon3.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
//            cartoon3.setStrokenOnly(false);
//            cartoon3.setGrayScale(true);
//
//            filterList.add(cartoon3);
//        }

        String bundlePath = "filter/info.txt";
        getBundleFilterInfo(context, filterAssetList, bundlePath);

        int ratio = TimelineData.instance().getMakeRatio();
        for (NvAsset asset : filterAssetList) {
            if (isFitRatio && (ratio & asset.aspectRatio) == 0)
                continue;

            FilterItem newFilterItem = new FilterItem();
            if (asset.isReserved()) {
                String coverPath = "file:///android_asset/filter/";
                coverPath += asset.uuid;
                coverPath += ".png";
                asset.coverUrl = coverPath;//加载assets/filter文件夹下的图片
            }
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_PACKAGE);
            newFilterItem.setFilterName(asset.name);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
            filterList.add(newFilterItem);
        }

        //暂时先注掉内建滤镜特效
        int[] resImags = {
                R.mipmap.filter_mk,
                R.mipmap.filter_jz,
                R.mipmap.filter_qx,
                R.mipmap.filter_ls,
                R.mipmap.filter_zg,
                R.mipmap.filter_yq,
                R.mipmap.filter_bh,
                R.mipmap.filter_cm,
                R.mipmap.filter_fn,
                R.mipmap.filter_ql
        };
        if (builtinVideoFxList != null) {
            for (int i = 0; i < builtinVideoFxList.size(); i++) {
                String     transitionName = builtinVideoFxList.get(i);
                FilterItem newFilterItem  = new FilterItem();
                newFilterItem.setFilterName(getBuiltinName(i));
                newFilterItem.setFilterId(transitionName);
                if (i < resImags.length) {
                    newFilterItem.setImageId(resImags[i]);
                }
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
                filterList.add(newFilterItem);
            }
        }

        return filterList;
    }

    private static String getBuiltinName(int i) {
        switch (i) {
            case 0:

                return "明快";
            case 1:

                return "精致";
            case 2:

                return "清晰";
            case 3:

                return "蕾丝";
            case 4:

                return "质感";
            case 5:

                return "元气";
            case 6:

                return "薄荷";
            case 7:

                return "草莓";
            case 8:

                return "粉嫩";
            case 9:

                return "清凉";
        }
        return "";
    }


    //获取滤镜当前选择位置
    public static int getSelectedFilterPos(ArrayList<FilterItem> filterDataArrayList, VideoClipFxInfo videoClipFxInfo) {
        if (filterDataArrayList == null || filterDataArrayList.size() == 0)
            return -1;

        String fxName = videoClipFxInfo.getFxId();
        if (TextUtils.isEmpty(fxName))
            return 0;

        for (int i = 0; i < filterDataArrayList.size(); i++) {
            FilterItem newFilterItem = filterDataArrayList.get(i);
            if (newFilterItem == null)
                continue;

            int    filterMode = newFilterItem.getFilterMode();
            String filterName;
            if (filterMode == FilterItem.FILTERMODE_BUILTIN) {
                filterName = newFilterItem.getFilterName();
            } else {
                filterName = newFilterItem.getPackageId();
            }

            if (fxName.equals(filterName)) {
                return i;
            }
        }

        return 0;
    }


    public static boolean getBundleFilterInfo(Context context, ArrayList<NvAsset> assetArrayList, String bundlePath) {
        if (context == null)
            return false;

        if (TextUtils.isEmpty(bundlePath))
            return false;

        try {
            InputStream    nameListStream = context.getAssets().open(bundlePath);
            BufferedReader nameListBuffer = new BufferedReader(new InputStreamReader(nameListStream, "GBK"));

            String strLine;
            while ((strLine = nameListBuffer.readLine()) != null) {
                String[] strNameArray = strLine.split(",");
                if (strNameArray.length < 3)
                    continue;

                for (int i = 0; i < assetArrayList.size(); ++i) {
                    NvAsset assetItem = assetArrayList.get(i);
                    if (assetItem == null)
                        continue;

                    if (!assetItem.isReserved)
                        continue;

                    String packageId = assetItem.uuid;
                    if (TextUtils.isEmpty(packageId))
                        continue;

                    if (packageId.equals(strNameArray[0])) {
                        assetItem.name = strNameArray[1];
                        assetItem.aspectRatio = Integer.parseInt(strNameArray[2]);
                        break;
                    }

                }
            }
            nameListBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
