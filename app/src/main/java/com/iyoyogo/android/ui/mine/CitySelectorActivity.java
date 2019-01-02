package com.iyoyogo.android.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.CitySelectorBean;
import com.iyoyogo.android.utils.city.DataBean;
import com.iyoyogo.android.utils.city.ListUtil;
import com.iyoyogo.android.widget.SlideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市选择
 */
public class CitySelectorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SlideBar.OnTouchAssortListener {
    private SlideBar mSlideBar;
    private ListView mListView;
    private List<DataBean> mList;

    private String getString() {
        String s = "{\n" +
                "  \"City\": [\n" +
                "    {\n" +
                "      \"name\": \"定位\",\n" +
                "      \"key\": \"0\",\n" +
                "      \"first\": \"\",\n" +
                "      \"full\": \"\",\n" +
                "      \"code\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"热门\",\n" +
                "      \"key\": \"1\",\n" +
                "      \"first\": \"\",\n" +
                "      \"full\": \"\",\n" +
                "      \"code\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"北京\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"bj\",\n" +
                "      \"full\": \"beijing\",\n" +
                "      \"code\": \"11\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"上海\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"sh\",\n" +
                "      \"full\": \"shanghai\",\n" +
                "      \"code\": \"3101\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"深圳\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"sz\",\n" +
                "      \"full\": \"shenzhen\",\n" +
                "      \"code\": \"4403\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"成都\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"cd\",\n" +
                "      \"full\": \"chengdu\",\n" +
                "      \"code\": \"5101\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"杭州\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"hz\",\n" +
                "      \"full\": \"hangzhou\",\n" +
                "      \"code\": \"3301\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"广州\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"gz\",\n" +
                "      \"full\": \"guangzhou\",\n" +
                "      \"code\": \"4401\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"苏州\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"sz\",\n" +
                "      \"full\": \"suzhou\",\n" +
                "      \"code\": \"5132\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"南京\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"nj\",\n" +
                "      \"full\": \"nanjing\",\n" +
                "      \"code\": \"3201\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"天津\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"tj\",\n" +
                "      \"full\": \"tianjin\",\n" +
                "      \"code\": \"12\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"重庆\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"cq\",\n" +
                "      \"full\": \"chongqing\",\n" +
                "      \"code\": \"50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"厦门\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"xm\",\n" +
                "      \"full\": \"xiamen\",\n" +
                "      \"code\": \"3502\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"武汉\",\n" +
                "      \"key\": \"热门\",\n" +
                "      \"first\": \"wh\",\n" +
                "      \"full\": \"wuhan\",\n" +
                "      \"code\": \"4201\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5132\",\n" +
                "      \"name\": \"阿坝藏族羌族自治州\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"abzzqzzzz\",\n" +
                "      \"full\": \"abazangzuqiangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6529\",\n" +
                "      \"name\": \"阿克苏地区\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"aksdq\",\n" +
                "      \"full\": \"akesudiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1529\",\n" +
                "      \"name\": \"阿拉善盟\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"alsm\",\n" +
                "      \"full\": \"alashanmeng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6543\",\n" +
                "      \"name\": \"阿勒泰地区\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"altdq\",\n" +
                "      \"full\": \"aletaidiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5425\",\n" +
                "      \"name\": \"阿里地区\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"aldq\",\n" +
                "      \"full\": \"alidiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6109\",\n" +
                "      \"name\": \"安康\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"aks\",\n" +
                "      \"full\": \"ankang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3408\",\n" +
                "      \"name\": \"安庆\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"aqs\",\n" +
                "      \"full\": \"anqing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2103\",\n" +
                "      \"name\": \"鞍山\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"ass\",\n" +
                "      \"full\": \"anshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5204\",\n" +
                "      \"name\": \"安顺\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"ass\",\n" +
                "      \"full\": \"anshun\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4105\",\n" +
                "      \"name\": \"安阳\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"ays\",\n" +
                "      \"full\": \"anyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"8200\",\n" +
                "      \"name\": \"澳门\",\n" +
                "      \"key\": \"A\",\n" +
                "      \"first\": \"am\",\n" +
                "      \"full\": \"aomen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2208\",\n" +
                "      \"name\": \"白城\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bcs\",\n" +
                "      \"full\": \"baicheng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4510\",\n" +
                "      \"name\": \"百色\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bss\",\n" +
                "      \"full\": \"baise\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2206\",\n" +
                "      \"name\": \"白山\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bss\",\n" +
                "      \"full\": \"baishan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6204\",\n" +
                "      \"name\": \"白银\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bys\",\n" +
                "      \"full\": \"baiyin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3403\",\n" +
                "      \"name\": \"蚌埠\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bbs\",\n" +
                "      \"full\": \"bangbu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1306\",\n" +
                "      \"name\": \"保定\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bds\",\n" +
                "      \"full\": \"baoding\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6103\",\n" +
                "      \"name\": \"宝鸡\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bjs\",\n" +
                "      \"full\": \"baoji\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5305\",\n" +
                "      \"name\": \"保山\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bss\",\n" +
                "      \"full\": \"baoshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1502\",\n" +
                "      \"name\": \"包头\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bts\",\n" +
                "      \"full\": \"baotou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1508\",\n" +
                "      \"name\": \"巴彦淖尔\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bynes\",\n" +
                "      \"full\": \"bayannaoer\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6528\",\n" +
                "      \"name\": \"巴音郭楞蒙古自治州\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"byglmgzzz\",\n" +
                "      \"full\": \"bayinguolengmengguzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5119\",\n" +
                "      \"name\": \"巴中\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bzs\",\n" +
                "      \"full\": \"bazhong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4505\",\n" +
                "      \"name\": \"北海\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bhs\",\n" +
                "      \"full\": \"beihai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"11\",\n" +
                "      \"name\": \"北京\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bjs\",\n" +
                "      \"full\": \"beijing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2105\",\n" +
                "      \"name\": \"本溪\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bxs\",\n" +
                "      \"full\": \"benxi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5224\",\n" +
                "      \"name\": \"毕节地区\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bjdq\",\n" +
                "      \"full\": \"bijiediqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3716\",\n" +
                "      \"name\": \"滨州\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bzs\",\n" +
                "      \"full\": \"binzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6527\",\n" +
                "      \"name\": \"博尔塔拉蒙古自治州\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"betlmgzzz\",\n" +
                "      \"full\": \"boertalamengguzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3416\",\n" +
                "      \"name\": \"亳州\",\n" +
                "      \"key\": \"B\",\n" +
                "      \"first\": \"bzs\",\n" +
                "      \"full\": \"bozhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1309\",\n" +
                "      \"name\": \"沧州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"cangzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2201\",\n" +
                "      \"name\": \"长春\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"ccs\",\n" +
                "      \"full\": \"changchun\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4307\",\n" +
                "      \"name\": \"常德\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cds\",\n" +
                "      \"full\": \"changde\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5421\",\n" +
                "      \"name\": \"昌都地区\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cddq\",\n" +
                "      \"full\": \"changdudiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6523\",\n" +
                "      \"name\": \"昌吉回族自治州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cjhzzzz\",\n" +
                "      \"full\": \"changjihuizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4301\",\n" +
                "      \"name\": \"长沙\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"css\",\n" +
                "      \"full\": \"changsha\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1404\",\n" +
                "      \"name\": \"长治\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"changzhi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3204\",\n" +
                "      \"name\": \"常州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"changzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2113\",\n" +
                "      \"name\": \"朝阳\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cys\",\n" +
                "      \"full\": \"chaoyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4451\",\n" +
                "      \"name\": \"潮州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"chaozhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1308\",\n" +
                "      \"name\": \"承德\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cds\",\n" +
                "      \"full\": \"chengde\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5101\",\n" +
                "      \"name\": \"成都\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cds\",\n" +
                "      \"full\": \"chengdu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4310\",\n" +
                "      \"name\": \"郴州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"chenzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1504\",\n" +
                "      \"name\": \"赤峰\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cfs\",\n" +
                "      \"full\": \"chifeng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3417\",\n" +
                "      \"name\": \"池州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"chizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"50\",\n" +
                "      \"name\": \"重庆\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cqs\",\n" +
                "      \"full\": \"chongqing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4514\",\n" +
                "      \"name\": \"崇左\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"chongzuo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5323\",\n" +
                "      \"name\": \"楚雄彝族自治州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"cxyzzzz\",\n" +
                "      \"full\": \"chuxiongyizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3411\",\n" +
                "      \"name\": \"滁州\",\n" +
                "      \"key\": \"C\",\n" +
                "      \"first\": \"czs\",\n" +
                "      \"full\": \"chuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2102\",\n" +
                "      \"name\": \"大连\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dls\",\n" +
                "      \"full\": \"dalian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5329\",\n" +
                "      \"name\": \"大理白族自治州\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dlbzzzz\",\n" +
                "      \"full\": \"dalibaizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2106\",\n" +
                "      \"name\": \"丹东\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dds\",\n" +
                "      \"full\": \"dandong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2306\",\n" +
                "      \"name\": \"大庆\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dqs\",\n" +
                "      \"full\": \"daqing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1402\",\n" +
                "      \"name\": \"大同\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dts\",\n" +
                "      \"full\": \"datong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2327\",\n" +
                "      \"name\": \"大兴安岭地区\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dxaldq\",\n" +
                "      \"full\": \"daxinganlingdiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5117\",\n" +
                "      \"name\": \"达州\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dzs\",\n" +
                "      \"full\": \"dazhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5331\",\n" +
                "      \"name\": \"德宏傣族景颇族自治州\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dhdzjpzzzz\",\n" +
                "      \"full\": \"dehongdaizujingpozuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5106\",\n" +
                "      \"name\": \"德阳\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dys\",\n" +
                "      \"full\": \"deyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3714\",\n" +
                "      \"name\": \"德州\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dzs\",\n" +
                "      \"full\": \"dezhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6211\",\n" +
                "      \"name\": \"定西\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dxs\",\n" +
                "      \"full\": \"dingxi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5334\",\n" +
                "      \"name\": \"迪庆藏族自治州\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dqzzzzz\",\n" +
                "      \"full\": \"diqingzangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3705\",\n" +
                "      \"name\": \"东营\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dys\",\n" +
                "      \"full\": \"dongying\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4419\",\n" +
                "      \"name\": \"东莞\",\n" +
                "      \"key\": \"D\",\n" +
                "      \"first\": \"dzs\",\n" +
                "      \"full\": \"dongzuo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1506\",\n" +
                "      \"name\": \"鄂尔多斯\",\n" +
                "      \"key\": \"E\",\n" +
                "      \"first\": \"eedss\",\n" +
                "      \"full\": \"eerduosi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4228\",\n" +
                "      \"name\": \"恩施土家族苗族自治州\",\n" +
                "      \"key\": \"E\",\n" +
                "      \"first\": \"estjzmzzzz\",\n" +
                "      \"full\": \"enshitujiazumiaozuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4207\",\n" +
                "      \"name\": \"鄂州\",\n" +
                "      \"key\": \"E\",\n" +
                "      \"first\": \"ezs\",\n" +
                "      \"full\": \"ezhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4506\",\n" +
                "      \"name\": \"防城港\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fcgs\",\n" +
                "      \"full\": \"fangchenggang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4406\",\n" +
                "      \"name\": \"佛山\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fss\",\n" +
                "      \"full\": \"foshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2104\",\n" +
                "      \"name\": \"抚顺\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fss\",\n" +
                "      \"full\": \"fushun\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2109\",\n" +
                "      \"name\": \"阜新\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fxs\",\n" +
                "      \"full\": \"fuxin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3412\",\n" +
                "      \"name\": \"阜阳\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fys\",\n" +
                "      \"full\": \"fuyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3501\",\n" +
                "      \"name\": \"福州\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fzs\",\n" +
                "      \"full\": \"fuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3610\",\n" +
                "      \"name\": \"抚州\",\n" +
                "      \"key\": \"F\",\n" +
                "      \"first\": \"fzs\",\n" +
                "      \"full\": \"fuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6230\",\n" +
                "      \"name\": \"甘南藏族自治州\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gnzzzzz\",\n" +
                "      \"full\": \"gannanzangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3607\",\n" +
                "      \"name\": \"赣州\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gzs\",\n" +
                "      \"full\": \"ganzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5133\",\n" +
                "      \"name\": \"甘孜藏族自治州\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gzzzzzz\",\n" +
                "      \"full\": \"ganzizangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5116\",\n" +
                "      \"name\": \"广安\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gas\",\n" +
                "      \"full\": \"guangan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5108\",\n" +
                "      \"name\": \"广元\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gys\",\n" +
                "      \"full\": \"guangyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4401\",\n" +
                "      \"name\": \"广州\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gzs\",\n" +
                "      \"full\": \"guangzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4508\",\n" +
                "      \"name\": \"贵港\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"ggs\",\n" +
                "      \"full\": \"guigang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4503\",\n" +
                "      \"name\": \"桂林\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gls\",\n" +
                "      \"full\": \"guilin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5201\",\n" +
                "      \"name\": \"贵阳\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gys\",\n" +
                "      \"full\": \"guiyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6326\",\n" +
                "      \"name\": \"果洛藏族自治州\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"glzzzzz\",\n" +
                "      \"full\": \"guoluozangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6404\",\n" +
                "      \"name\": \"固原\",\n" +
                "      \"key\": \"G\",\n" +
                "      \"first\": \"gys\",\n" +
                "      \"full\": \"guyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2301\",\n" +
                "      \"name\": \"哈尔滨\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hebs\",\n" +
                "      \"full\": \"haerbin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6322\",\n" +
                "      \"name\": \"海北藏族自治州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hbzzzzz\",\n" +
                "      \"full\": \"haibeizangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6321\",\n" +
                "      \"name\": \"海东地区\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hddq\",\n" +
                "      \"full\": \"haidongdiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4601\",\n" +
                "      \"name\": \"海口\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hks\",\n" +
                "      \"full\": \"haikou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6325\",\n" +
                "      \"name\": \"海南藏族自治州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hnzzzzz\",\n" +
                "      \"full\": \"hainanzangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6328\",\n" +
                "      \"name\": \"海西蒙古族藏族自治州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hxmgzzzzzz\",\n" +
                "      \"full\": \"haiximengguzuzangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6522\",\n" +
                "      \"name\": \"哈密地区\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hmdq\",\n" +
                "      \"full\": \"hamidiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1304\",\n" +
                "      \"name\": \"邯郸\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hds\",\n" +
                "      \"full\": \"handan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3301\",\n" +
                "      \"name\": \"杭州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hzs\",\n" +
                "      \"full\": \"hangzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6107\",\n" +
                "      \"name\": \"汉中\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hzs\",\n" +
                "      \"full\": \"hanzhong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4106\",\n" +
                "      \"name\": \"鹤壁\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hbs\",\n" +
                "      \"full\": \"hebi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4512\",\n" +
                "      \"name\": \"河池\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hcs\",\n" +
                "      \"full\": \"hechi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3401\",\n" +
                "      \"name\": \"合肥\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hfs\",\n" +
                "      \"full\": \"hefei\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2304\",\n" +
                "      \"name\": \"鹤岗\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hgs\",\n" +
                "      \"full\": \"hegang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2311\",\n" +
                "      \"name\": \"黑河\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hhs\",\n" +
                "      \"full\": \"heihe\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1311\",\n" +
                "      \"name\": \"衡水\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hss\",\n" +
                "      \"full\": \"hengshui\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4304\",\n" +
                "      \"name\": \"衡阳\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hys\",\n" +
                "      \"full\": \"hengyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6532\",\n" +
                "      \"name\": \"和田地区\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"htdq\",\n" +
                "      \"full\": \"hetiandiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4416\",\n" +
                "      \"name\": \"河源\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hys\",\n" +
                "      \"full\": \"heyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3717\",\n" +
                "      \"name\": \"菏泽\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hzs\",\n" +
                "      \"full\": \"heze\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4511\",\n" +
                "      \"name\": \"贺州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hzs\",\n" +
                "      \"full\": \"hezhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5325\",\n" +
                "      \"name\": \"红河哈尼族彝族自治州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hhhnzyzzzz\",\n" +
                "      \"full\": \"honghehanizuyizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3208\",\n" +
                "      \"name\": \"淮安\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"has\",\n" +
                "      \"full\": \"huaian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3406\",\n" +
                "      \"name\": \"淮北\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hbs\",\n" +
                "      \"full\": \"huaibei\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4312\",\n" +
                "      \"name\": \"怀化\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hhs\",\n" +
                "      \"full\": \"huaihua\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3404\",\n" +
                "      \"name\": \"淮南\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hns\",\n" +
                "      \"full\": \"huainan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4211\",\n" +
                "      \"name\": \"黄冈\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hgs\",\n" +
                "      \"full\": \"huanggang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6323\",\n" +
                "      \"name\": \"黄南藏族自治州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hnzzzzz\",\n" +
                "      \"full\": \"huangnanzangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3410\",\n" +
                "      \"name\": \"黄山\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hss\",\n" +
                "      \"full\": \"huangshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4202\",\n" +
                "      \"name\": \"黄石\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hss\",\n" +
                "      \"full\": \"huangshi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1501\",\n" +
                "      \"name\": \"呼和浩特\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hhhts\",\n" +
                "      \"full\": \"huhehaote\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4413\",\n" +
                "      \"name\": \"惠州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hzs\",\n" +
                "      \"full\": \"huizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2114\",\n" +
                "      \"name\": \"葫芦岛\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hlds\",\n" +
                "      \"full\": \"huludao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1507\",\n" +
                "      \"name\": \"呼伦贝尔\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hlbes\",\n" +
                "      \"full\": \"hulunbeier\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3305\",\n" +
                "      \"name\": \"湖州\",\n" +
                "      \"key\": \"H\",\n" +
                "      \"first\": \"hzs\",\n" +
                "      \"full\": \"huzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2308\",\n" +
                "      \"name\": \"佳木斯\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jmss\",\n" +
                "      \"full\": \"jiamusi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4407\",\n" +
                "      \"name\": \"江门\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jms\",\n" +
                "      \"full\": \"jiangmen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3608\",\n" +
                "      \"name\": \"吉安\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jas\",\n" +
                "      \"full\": \"jian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4108\",\n" +
                "      \"name\": \"焦作\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jzs\",\n" +
                "      \"full\": \"jiaozuo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3304\",\n" +
                "      \"name\": \"嘉兴\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jxs\",\n" +
                "      \"full\": \"jiaxing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6202\",\n" +
                "      \"name\": \"嘉峪关\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jygs\",\n" +
                "      \"full\": \"jiayuguan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4452\",\n" +
                "      \"name\": \"揭阳\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jys\",\n" +
                "      \"full\": \"jieyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2202\",\n" +
                "      \"name\": \"吉林\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jls\",\n" +
                "      \"full\": \"jilin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"7100\",\n" +
                "      \"name\": \"基隆\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jls\",\n" +
                "      \"full\": \"jilong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3701\",\n" +
                "      \"name\": \"济南\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jns\",\n" +
                "      \"full\": \"jinan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6203\",\n" +
                "      \"name\": \"金昌\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jcs\",\n" +
                "      \"full\": \"jinchang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1405\",\n" +
                "      \"name\": \"晋城\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jcs\",\n" +
                "      \"full\": \"jincheng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3602\",\n" +
                "      \"name\": \"景德镇\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jdzs\",\n" +
                "      \"full\": \"jingdezhen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4208\",\n" +
                "      \"name\": \"荆门\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jms\",\n" +
                "      \"full\": \"jingmen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4210\",\n" +
                "      \"name\": \"荆州\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jzs\",\n" +
                "      \"full\": \"jingzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3307\",\n" +
                "      \"name\": \"金华\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jhs\",\n" +
                "      \"full\": \"jinhua\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3708\",\n" +
                "      \"name\": \"济宁\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jns\",\n" +
                "      \"full\": \"jining\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1407\",\n" +
                "      \"name\": \"晋中\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jzs\",\n" +
                "      \"full\": \"jinzhong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2107\",\n" +
                "      \"name\": \"锦州\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jzs\",\n" +
                "      \"full\": \"jinzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3604\",\n" +
                "      \"name\": \"九江\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jjs\",\n" +
                "      \"full\": \"jiujiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6209\",\n" +
                "      \"name\": \"酒泉\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jqs\",\n" +
                "      \"full\": \"jiuquan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2303\",\n" +
                "      \"name\": \"鸡西\",\n" +
                "      \"key\": \"J\",\n" +
                "      \"first\": \"jxs\",\n" +
                "      \"full\": \"jixi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4102\",\n" +
                "      \"name\": \"开封\",\n" +
                "      \"key\": \"K\",\n" +
                "      \"first\": \"kfs\",\n" +
                "      \"full\": \"kaifeng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6531\",\n" +
                "      \"name\": \"喀什地区\",\n" +
                "      \"key\": \"K\",\n" +
                "      \"first\": \"ksdq\",\n" +
                "      \"full\": \"kashidiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6502\",\n" +
                "      \"name\": \"克拉玛依\",\n" +
                "      \"key\": \"K\",\n" +
                "      \"first\": \"klmys\",\n" +
                "      \"full\": \"kelamayi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6530\",\n" +
                "      \"name\": \"克孜勒苏柯尔克孜自治州\",\n" +
                "      \"key\": \"K\",\n" +
                "      \"first\": \"kzlskekzzzz\",\n" +
                "      \"full\": \"kezilesukeerkezizizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5301\",\n" +
                "      \"name\": \"昆明\",\n" +
                "      \"key\": \"K\",\n" +
                "      \"first\": \"kms\",\n" +
                "      \"full\": \"kunming\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4513\",\n" +
                "      \"name\": \"来宾\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lbs\",\n" +
                "      \"full\": \"laibin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3712\",\n" +
                "      \"name\": \"莱芜\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lws\",\n" +
                "      \"full\": \"laiwu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1310\",\n" +
                "      \"name\": \"廊坊\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lfs\",\n" +
                "      \"full\": \"langfang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6201\",\n" +
                "      \"name\": \"兰州\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lzs\",\n" +
                "      \"full\": \"lanzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5401\",\n" +
                "      \"name\": \"拉萨\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lss\",\n" +
                "      \"full\": \"lasa\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5111\",\n" +
                "      \"name\": \"乐山\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lss\",\n" +
                "      \"full\": \"leshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5134\",\n" +
                "      \"name\": \"凉山彝族自治州\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lsyzzzz\",\n" +
                "      \"full\": \"liangshanyizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3207\",\n" +
                "      \"name\": \"连云港\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lygs\",\n" +
                "      \"full\": \"lianyungang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3715\",\n" +
                "      \"name\": \"聊城\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lcs\",\n" +
                "      \"full\": \"liaocheng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2110\",\n" +
                "      \"name\": \"辽阳\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lys\",\n" +
                "      \"full\": \"liaoyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2204\",\n" +
                "      \"name\": \"辽源\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lys\",\n" +
                "      \"full\": \"liaoyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5307\",\n" +
                "      \"name\": \"丽江\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"ljs\",\n" +
                "      \"full\": \"lijiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5309\",\n" +
                "      \"name\": \"临沧\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lcs\",\n" +
                "      \"full\": \"lincang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1410\",\n" +
                "      \"name\": \"临汾\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lfs\",\n" +
                "      \"full\": \"linfen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6229\",\n" +
                "      \"name\": \"临夏回族自治州\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lxhzzzz\",\n" +
                "      \"full\": \"linxiahuizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3713\",\n" +
                "      \"name\": \"临沂\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lys\",\n" +
                "      \"full\": \"linyi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5426\",\n" +
                "      \"name\": \"林芝地区\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lzdq\",\n" +
                "      \"full\": \"linzhidiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3311\",\n" +
                "      \"name\": \"丽水\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lss\",\n" +
                "      \"full\": \"lishui\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3415\",\n" +
                "      \"name\": \"六安\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"las\",\n" +
                "      \"full\": \"liuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5202\",\n" +
                "      \"name\": \"六盘水\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lpss\",\n" +
                "      \"full\": \"liupanshui\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4502\",\n" +
                "      \"name\": \"柳州\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lzs\",\n" +
                "      \"full\": \"liuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6212\",\n" +
                "      \"name\": \"陇南\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lns\",\n" +
                "      \"full\": \"longnan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3508\",\n" +
                "      \"name\": \"龙岩\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lys\",\n" +
                "      \"full\": \"longyan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4313\",\n" +
                "      \"name\": \"娄底\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lds\",\n" +
                "      \"full\": \"loudi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4111\",\n" +
                "      \"name\": \"漯河\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lhs\",\n" +
                "      \"full\": \"luohe\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4103\",\n" +
                "      \"name\": \"洛阳\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lys\",\n" +
                "      \"full\": \"luoyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5105\",\n" +
                "      \"name\": \"泸州\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lzs\",\n" +
                "      \"full\": \"luzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1411\",\n" +
                "      \"name\": \"吕梁\",\n" +
                "      \"key\": \"L\",\n" +
                "      \"first\": \"lls\",\n" +
                "      \"full\": \"lvliang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3405\",\n" +
                "      \"name\": \"马鞍山\",\n" +
                "      \"key\": \"M\",\n" +
                "      \"first\": \"mass\",\n" +
                "      \"full\": \"maanshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4409\",\n" +
                "      \"name\": \"茂名\",\n" +
                "      \"key\": \"M\",\n" +
                "      \"first\": \"mms\",\n" +
                "      \"full\": \"maoming\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5114\",\n" +
                "      \"name\": \"眉山\",\n" +
                "      \"key\": \"M\",\n" +
                "      \"first\": \"mss\",\n" +
                "      \"full\": \"meishan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4414\",\n" +
                "      \"name\": \"梅州\",\n" +
                "      \"key\": \"M\",\n" +
                "      \"first\": \"mzs\",\n" +
                "      \"full\": \"meizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5107\",\n" +
                "      \"name\": \"绵阳\",\n" +
                "      \"key\": \"M\",\n" +
                "      \"first\": \"mys\",\n" +
                "      \"full\": \"mianyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2310\",\n" +
                "      \"name\": \"牡丹江\",\n" +
                "      \"key\": \"M\",\n" +
                "      \"first\": \"mdjs\",\n" +
                "      \"full\": \"mudanjiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3601\",\n" +
                "      \"name\": \"南昌\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"ncs\",\n" +
                "      \"full\": \"nanchang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5113\",\n" +
                "      \"name\": \"南充\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"ncs\",\n" +
                "      \"full\": \"nanchong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3201\",\n" +
                "      \"name\": \"南京\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"njs\",\n" +
                "      \"full\": \"nanjing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4501\",\n" +
                "      \"name\": \"南宁\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nns\",\n" +
                "      \"full\": \"nanning\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3507\",\n" +
                "      \"name\": \"南平\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nps\",\n" +
                "      \"full\": \"nanping\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3206\",\n" +
                "      \"name\": \"南通\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nts\",\n" +
                "      \"full\": \"nantong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4113\",\n" +
                "      \"name\": \"南阳\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nys\",\n" +
                "      \"full\": \"nanyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5424\",\n" +
                "      \"name\": \"那曲地区\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nqdq\",\n" +
                "      \"full\": \"naqudiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5110\",\n" +
                "      \"name\": \"内江\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"njs\",\n" +
                "      \"full\": \"neijiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3302\",\n" +
                "      \"name\": \"宁波\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nbs\",\n" +
                "      \"full\": \"ningbo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3509\",\n" +
                "      \"name\": \"宁德\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"nds\",\n" +
                "      \"full\": \"ningde\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5333\",\n" +
                "      \"name\": \"怒江傈僳族自治州\",\n" +
                "      \"key\": \"N\",\n" +
                "      \"first\": \"njlszzzz\",\n" +
                "      \"full\": \"nujianglisuzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2111\",\n" +
                "      \"name\": \"盘锦\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pjs\",\n" +
                "      \"full\": \"panjin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5104\",\n" +
                "      \"name\": \"攀枝花\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pzhs\",\n" +
                "      \"full\": \"panzhihua\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4104\",\n" +
                "      \"name\": \"平顶山\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pdss\",\n" +
                "      \"full\": \"pingdingshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6208\",\n" +
                "      \"name\": \"平凉\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pls\",\n" +
                "      \"full\": \"pingliang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3603\",\n" +
                "      \"name\": \"萍乡\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pxs\",\n" +
                "      \"full\": \"pingxiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3503\",\n" +
                "      \"name\": \"莆田\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pts\",\n" +
                "      \"full\": \"putian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4109\",\n" +
                "      \"name\": \"濮阳\",\n" +
                "      \"key\": \"P\",\n" +
                "      \"first\": \"pys\",\n" +
                "      \"full\": \"puyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5226\",\n" +
                "      \"name\": \"黔东南苗族侗族自治州\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qdnmzdzzzz\",\n" +
                "      \"full\": \"qiandongnanmiaozudongzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5227\",\n" +
                "      \"name\": \"黔南布依族苗族自治州\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qnbyzmzzzz\",\n" +
                "      \"full\": \"qiannanbuyizumiaozuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5223\",\n" +
                "      \"name\": \"黔西南布依族苗族自治州\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qxnbyzmzzzz\",\n" +
                "      \"full\": \"qianxinanbuyizumiaozuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3702\",\n" +
                "      \"name\": \"青岛\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qds\",\n" +
                "      \"full\": \"qingdao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6210\",\n" +
                "      \"name\": \"庆阳\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qys\",\n" +
                "      \"full\": \"qingyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4418\",\n" +
                "      \"name\": \"清远\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qys\",\n" +
                "      \"full\": \"qingyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1303\",\n" +
                "      \"name\": \"秦皇岛\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qhds\",\n" +
                "      \"full\": \"qinhuangdao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4507\",\n" +
                "      \"name\": \"钦州\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qzs\",\n" +
                "      \"full\": \"qinzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2302\",\n" +
                "      \"name\": \"齐齐哈尔\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qqhes\",\n" +
                "      \"full\": \"qiqihaer\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2309\",\n" +
                "      \"name\": \"七台河\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qths\",\n" +
                "      \"full\": \"qitaihe\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3505\",\n" +
                "      \"name\": \"泉州\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qzs\",\n" +
                "      \"full\": \"quanzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5303\",\n" +
                "      \"name\": \"曲靖\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qjs\",\n" +
                "      \"full\": \"qujing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3308\",\n" +
                "      \"name\": \"衢州\",\n" +
                "      \"key\": \"Q\",\n" +
                "      \"first\": \"qzs\",\n" +
                "      \"full\": \"quzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5423\",\n" +
                "      \"name\": \"日喀则地区\",\n" +
                "      \"key\": \"R\",\n" +
                "      \"first\": \"rkzdq\",\n" +
                "      \"full\": \"rikazediqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3711\",\n" +
                "      \"name\": \"日照\",\n" +
                "      \"key\": \"R\",\n" +
                "      \"first\": \"rzs\",\n" +
                "      \"full\": \"rizhao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4112\",\n" +
                "      \"name\": \"三门峡\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"smxs\",\n" +
                "      \"full\": \"sanmenxia\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3504\",\n" +
                "      \"name\": \"三明\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sms\",\n" +
                "      \"full\": \"sanming\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4602\",\n" +
                "      \"name\": \"三亚\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sys\",\n" +
                "      \"full\": \"sanya\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3101\",\n" +
                "      \"name\": \"上海\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"shs\",\n" +
                "      \"full\": \"shanghai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6110\",\n" +
                "      \"name\": \"商洛\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sls\",\n" +
                "      \"full\": \"shangluo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4114\",\n" +
                "      \"name\": \"商丘\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sqs\",\n" +
                "      \"full\": \"shangqiu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3611\",\n" +
                "      \"name\": \"上饶\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"srs\",\n" +
                "      \"full\": \"shangrao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5422\",\n" +
                "      \"name\": \"山南地区\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sndq\",\n" +
                "      \"full\": \"shannandiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4405\",\n" +
                "      \"name\": \"汕头\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sts\",\n" +
                "      \"full\": \"shantou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4415\",\n" +
                "      \"name\": \"汕尾\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sws\",\n" +
                "      \"full\": \"shanwei\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4402\",\n" +
                "      \"name\": \"韶关\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sgs\",\n" +
                "      \"full\": \"shaoguan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3306\",\n" +
                "      \"name\": \"绍兴\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sxs\",\n" +
                "      \"full\": \"shaoxing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4305\",\n" +
                "      \"name\": \"邵阳\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sys\",\n" +
                "      \"full\": \"shaoyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2101\",\n" +
                "      \"name\": \"沈阳\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sys\",\n" +
                "      \"full\": \"shenyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4403\",\n" +
                "      \"name\": \"深圳\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"szs\",\n" +
                "      \"full\": \"shenzuo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1301\",\n" +
                "      \"name\": \"石家庄\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sjzs\",\n" +
                "      \"full\": \"shijiazhuang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4203\",\n" +
                "      \"name\": \"十堰\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sys\",\n" +
                "      \"full\": \"shiyan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6402\",\n" +
                "      \"name\": \"石嘴山\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"szss\",\n" +
                "      \"full\": \"shizuishan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2305\",\n" +
                "      \"name\": \"双鸭山\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"syss\",\n" +
                "      \"full\": \"shuangyashan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1406\",\n" +
                "      \"name\": \"朔州\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"szs\",\n" +
                "      \"full\": \"shuozhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5308\",\n" +
                "      \"name\": \"思茅\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sms\",\n" +
                "      \"full\": \"simao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2203\",\n" +
                "      \"name\": \"四平\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sps\",\n" +
                "      \"full\": \"siping\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2207\",\n" +
                "      \"name\": \"松原\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sys\",\n" +
                "      \"full\": \"songyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2312\",\n" +
                "      \"name\": \"绥化\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"shs\",\n" +
                "      \"full\": \"suihua\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5109\",\n" +
                "      \"name\": \"遂宁\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sns\",\n" +
                "      \"full\": \"suining\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4213\",\n" +
                "      \"name\": \"随州\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"szs\",\n" +
                "      \"full\": \"suizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3213\",\n" +
                "      \"name\": \"宿迁\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"sqs\",\n" +
                "      \"full\": \"suqian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3205\",\n" +
                "      \"name\": \"苏州\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"szs\",\n" +
                "      \"full\": \"suzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3413\",\n" +
                "      \"name\": \"宿州\",\n" +
                "      \"key\": \"S\",\n" +
                "      \"first\": \"szs\",\n" +
                "      \"full\": \"suzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6542\",\n" +
                "      \"name\": \"塔城地区\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tcdq\",\n" +
                "      \"full\": \"tachengdiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3709\",\n" +
                "      \"name\": \"泰安\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tas\",\n" +
                "      \"full\": \"taian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"7100\",\n" +
                "      \"name\": \"台北\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tbs\",\n" +
                "      \"full\": \"taibei\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1401\",\n" +
                "      \"name\": \"太原\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tys\",\n" +
                "      \"full\": \"taiyuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3310\",\n" +
                "      \"name\": \"台州\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tzs\",\n" +
                "      \"full\": \"taizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3212\",\n" +
                "      \"name\": \"泰州\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tzs\",\n" +
                "      \"full\": \"taizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1302\",\n" +
                "      \"name\": \"唐山\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tss\",\n" +
                "      \"full\": \"tangshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"12\",\n" +
                "      \"name\": \"天津\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tjs\",\n" +
                "      \"full\": \"tianjin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6205\",\n" +
                "      \"name\": \"天水\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tss\",\n" +
                "      \"full\": \"tianshui\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2112\",\n" +
                "      \"name\": \"铁岭\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tls\",\n" +
                "      \"full\": \"tieling\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6102\",\n" +
                "      \"name\": \"铜川\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tcs\",\n" +
                "      \"full\": \"tongchuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2205\",\n" +
                "      \"name\": \"通化\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"ths\",\n" +
                "      \"full\": \"tonghua\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1505\",\n" +
                "      \"name\": \"通辽\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tls\",\n" +
                "      \"full\": \"tongliao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3407\",\n" +
                "      \"name\": \"铜陵\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tls\",\n" +
                "      \"full\": \"tongling\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5222\",\n" +
                "      \"name\": \"铜仁地区\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"trdq\",\n" +
                "      \"full\": \"tongrendiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6521\",\n" +
                "      \"name\": \"吐鲁番地区\",\n" +
                "      \"key\": \"T\",\n" +
                "      \"first\": \"tlfdq\",\n" +
                "      \"full\": \"tulufandiqu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3707\",\n" +
                "      \"name\": \"潍坊\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wfs\",\n" +
                "      \"full\": \"weifang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3710\",\n" +
                "      \"name\": \"威海\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"whs\",\n" +
                "      \"full\": \"weihai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6105\",\n" +
                "      \"name\": \"渭南\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wns\",\n" +
                "      \"full\": \"weinan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5326\",\n" +
                "      \"name\": \"文山壮族苗族自治州\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wszzmzzzz\",\n" +
                "      \"full\": \"wenshanzhuangzumiaozuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3303\",\n" +
                "      \"name\": \"温州\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wzs\",\n" +
                "      \"full\": \"wenzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1503\",\n" +
                "      \"name\": \"乌海\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"whs\",\n" +
                "      \"full\": \"wuhai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4201\",\n" +
                "      \"name\": \"武汉\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"whs\",\n" +
                "      \"full\": \"wuhan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3402\",\n" +
                "      \"name\": \"芜湖\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"whs\",\n" +
                "      \"full\": \"wuhu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1509\",\n" +
                "      \"name\": \"乌兰察布\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wlcbs\",\n" +
                "      \"full\": \"wulanchabu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6501\",\n" +
                "      \"name\": \"乌鲁木齐\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wlmqs\",\n" +
                "      \"full\": \"wulumuqi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6206\",\n" +
                "      \"name\": \"武威\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wws\",\n" +
                "      \"full\": \"wuwei\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3202\",\n" +
                "      \"name\": \"无锡\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wxs\",\n" +
                "      \"full\": \"wuxi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6403\",\n" +
                "      \"name\": \"吴忠\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wzs\",\n" +
                "      \"full\": \"wuzhong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4504\",\n" +
                "      \"name\": \"梧州\",\n" +
                "      \"key\": \"W\",\n" +
                "      \"first\": \"wzs\",\n" +
                "      \"full\": \"wuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3502\",\n" +
                "      \"name\": \"厦门\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xms\",\n" +
                "      \"full\": \"xiamen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4206\",\n" +
                "      \"name\": \"襄樊\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xfs\",\n" +
                "      \"full\": \"xiangfan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"8100\",\n" +
                "      \"name\": \"香港\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xg\",\n" +
                "      \"full\": \"xianggang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4303\",\n" +
                "      \"name\": \"湘潭\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xts\",\n" +
                "      \"full\": \"xiangtan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4331\",\n" +
                "      \"name\": \"湘西土家族苗族自治州\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xxtjzmzzzz\",\n" +
                "      \"full\": \"xiangxitujiazumiaozuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4212\",\n" +
                "      \"name\": \"咸宁\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xns\",\n" +
                "      \"full\": \"xianning\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6101\",\n" +
                "      \"name\": \"西安\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xas\",\n" +
                "      \"full\": \"xian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6104\",\n" +
                "      \"name\": \"咸阳\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xys\",\n" +
                "      \"full\": \"xianyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4209\",\n" +
                "      \"name\": \"孝感\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xgs\",\n" +
                "      \"full\": \"xiaogan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1525\",\n" +
                "      \"name\": \"锡林郭勒盟\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xlglm\",\n" +
                "      \"full\": \"xilinguolemeng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1522\",\n" +
                "      \"name\": \"兴安盟\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xam\",\n" +
                "      \"full\": \"xinganmeng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1305\",\n" +
                "      \"name\": \"邢台\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xts\",\n" +
                "      \"full\": \"xingtai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6301\",\n" +
                "      \"name\": \"西宁\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xns\",\n" +
                "      \"full\": \"xining\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4107\",\n" +
                "      \"name\": \"新乡\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xxs\",\n" +
                "      \"full\": \"xinxiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4115\",\n" +
                "      \"name\": \"信阳\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xys\",\n" +
                "      \"full\": \"xinyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3605\",\n" +
                "      \"name\": \"新余\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xys\",\n" +
                "      \"full\": \"xinyu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1409\",\n" +
                "      \"name\": \"忻州\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xzs\",\n" +
                "      \"full\": \"xinzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5328\",\n" +
                "      \"name\": \"西双版纳傣族自治州\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xsbndzzzz\",\n" +
                "      \"full\": \"xishuangbannadaizuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3418\",\n" +
                "      \"name\": \"宣城\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xcs\",\n" +
                "      \"full\": \"xuancheng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4110\",\n" +
                "      \"name\": \"许昌\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xcs\",\n" +
                "      \"full\": \"xuchang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3203\",\n" +
                "      \"name\": \"徐州\",\n" +
                "      \"key\": \"X\",\n" +
                "      \"first\": \"xzs\",\n" +
                "      \"full\": \"xuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5118\",\n" +
                "      \"name\": \"雅安\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yas\",\n" +
                "      \"full\": \"yaan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6106\",\n" +
                "      \"name\": \"延安\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yas\",\n" +
                "      \"full\": \"yanan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2224\",\n" +
                "      \"name\": \"延边朝鲜族自治州\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ybcxzzzz\",\n" +
                "      \"full\": \"yanbianchaoxianzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3209\",\n" +
                "      \"name\": \"盐城\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ycs\",\n" +
                "      \"full\": \"yancheng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4417\",\n" +
                "      \"name\": \"阳江\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yjs\",\n" +
                "      \"full\": \"yangjiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1403\",\n" +
                "      \"name\": \"阳泉\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yqs\",\n" +
                "      \"full\": \"yangquan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3210\",\n" +
                "      \"name\": \"扬州\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yzs\",\n" +
                "      \"full\": \"yangzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3706\",\n" +
                "      \"name\": \"烟台\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yts\",\n" +
                "      \"full\": \"yantai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5115\",\n" +
                "      \"name\": \"宜宾\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ybs\",\n" +
                "      \"full\": \"yibin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4205\",\n" +
                "      \"name\": \"宜昌\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ycs\",\n" +
                "      \"full\": \"yichang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2307\",\n" +
                "      \"name\": \"伊春\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ycs\",\n" +
                "      \"full\": \"yichun\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3609\",\n" +
                "      \"name\": \"宜春\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ycs\",\n" +
                "      \"full\": \"yichun\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6540\",\n" +
                "      \"name\": \"伊犁哈萨克自治州\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ylhskzzz\",\n" +
                "      \"full\": \"yilihasakezizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6401\",\n" +
                "      \"name\": \"银川\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ycs\",\n" +
                "      \"full\": \"yinchuan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2108\",\n" +
                "      \"name\": \"营口\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yks\",\n" +
                "      \"full\": \"yingkou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3606\",\n" +
                "      \"name\": \"鹰潭\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yts\",\n" +
                "      \"full\": \"yingtan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4309\",\n" +
                "      \"name\": \"益阳\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yys\",\n" +
                "      \"full\": \"yiyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4311\",\n" +
                "      \"name\": \"永州\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yzs\",\n" +
                "      \"full\": \"yongzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4306\",\n" +
                "      \"name\": \"岳阳\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yys\",\n" +
                "      \"full\": \"yueyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4509\",\n" +
                "      \"name\": \"玉林\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yls\",\n" +
                "      \"full\": \"yulin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6108\",\n" +
                "      \"name\": \"榆林\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yls\",\n" +
                "      \"full\": \"yulin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1408\",\n" +
                "      \"name\": \"运城\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"ycs\",\n" +
                "      \"full\": \"yuncheng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4453\",\n" +
                "      \"name\": \"云浮\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yfs\",\n" +
                "      \"full\": \"yunfu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6327\",\n" +
                "      \"name\": \"玉树藏族自治州\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yszzzzz\",\n" +
                "      \"full\": \"yushuzangzuzizhizhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5304\",\n" +
                "      \"name\": \"玉溪\",\n" +
                "      \"key\": \"Y\",\n" +
                "      \"first\": \"yxs\",\n" +
                "      \"full\": \"yuxi\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3704\",\n" +
                "      \"name\": \"枣庄\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zzs\",\n" +
                "      \"full\": \"zaozhuang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4308\",\n" +
                "      \"name\": \"张家界\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zjjs\",\n" +
                "      \"full\": \"zhangjiajie\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1307\",\n" +
                "      \"name\": \"张家口\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zjks\",\n" +
                "      \"full\": \"zhangjiakou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6207\",\n" +
                "      \"name\": \"张掖\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zys\",\n" +
                "      \"full\": \"zhangye\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3506\",\n" +
                "      \"name\": \"漳州\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zzs\",\n" +
                "      \"full\": \"zhangzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4408\",\n" +
                "      \"name\": \"湛江\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zjs\",\n" +
                "      \"full\": \"zhanjiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4412\",\n" +
                "      \"name\": \"肇庆\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zqs\",\n" +
                "      \"full\": \"zhaoqing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5306\",\n" +
                "      \"name\": \"昭通\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zts\",\n" +
                "      \"full\": \"zhaotong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4101\",\n" +
                "      \"name\": \"郑州\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zzs\",\n" +
                "      \"full\": \"zhengzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3211\",\n" +
                "      \"name\": \"镇江\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zjs\",\n" +
                "      \"full\": \"zhenjiang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4420\",\n" +
                "      \"name\": \"中山\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zss\",\n" +
                "      \"full\": \"zhongshan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6405\",\n" +
                "      \"name\": \"中卫\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zws\",\n" +
                "      \"full\": \"zhongwei\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4116\",\n" +
                "      \"name\": \"周口\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zks\",\n" +
                "      \"full\": \"zhoukou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3309\",\n" +
                "      \"name\": \"舟山\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zss\",\n" +
                "      \"full\": \"zhoushan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4404\",\n" +
                "      \"name\": \"珠海\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zhs\",\n" +
                "      \"full\": \"zhuhai\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4117\",\n" +
                "      \"name\": \"驻马店\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zmds\",\n" +
                "      \"full\": \"zhumadian\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4302\",\n" +
                "      \"name\": \"株洲\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zzs\",\n" +
                "      \"full\": \"zhuzhou\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3703\",\n" +
                "      \"name\": \"淄博\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zbs\",\n" +
                "      \"full\": \"zibo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5103\",\n" +
                "      \"name\": \"自贡\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zgs\",\n" +
                "      \"full\": \"zigong\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5120\",\n" +
                "      \"name\": \"资阳\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zys\",\n" +
                "      \"full\": \"ziyang\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5203\",\n" +
                "      \"name\": \"遵义\",\n" +
                "      \"key\": \"Z\",\n" +
                "      \"first\": \"zys\",\n" +
                "      \"full\": \"zunyi\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);
        mSlideBar = (SlideBar) findViewById(R.id.slidebar);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
        mSlideBar.setOnTouchAssortListener(this);
        setData();

    }

    private void setData() {
        mList = new ArrayList<DataBean>();
        String string = getString();
        Gson gson = new Gson();
        CitySelectorBean citySelectorBean = gson.fromJson(string, CitySelectorBean.class);
        List<CitySelectorBean.CityBean> city = citySelectorBean.getCity();
        List<CitySelectorBean.CityBean> cityList = new ArrayList<>();
        cityList.addAll(city);

        for (int i = 0; i < cityList.size(); i++) {
            DataBean data = new DataBean(cityList.get(i).getName(), DataBean.TYPE_DATA);
            mList.add(data);
        }
        ListUtil.sortList(mList);
        mListView.setAdapter(new MyAdapter(this, mList));

    }

    @Override
    public void onTouchAssortListener(String s) {
        int select = getSelectIndex(s);
        if (select != -1) {
            mListView.setSelection(select);
        }
    }

    private int getSelectIndex(String s) {
        for (int i = 0; i < mList.size(); i++) {
            String name = mList.get(i).getName();
            if (name.equals(s)) {
                return i;
            }
        }
        return -1;
    }

    private class MyAdapter extends BaseAdapter {

        private Context mContext;
        private List<DataBean> mData;

        public MyAdapter(Context context, List<DataBean> list) {
            mContext = context;
            mData = list;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = mData.get(position).getItem_type();
            String name = mData.get(position).getName();
            if (type == DataBean.TYPE_CHARACTER) {
                convertView = View.inflate(mContext, R.layout.item_list_character, null);
                TextView tv_character = (TextView) convertView.findViewById(R.id.tv_item_character);
                tv_character.setText(name);
            } else {
                convertView = View.inflate(mContext, R.layout.item_list_people, null);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_item_people_name);
                tv_name.setText(name);
            }
            return convertView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DataBean bean = (DataBean) parent.getItemAtPosition(position);
        int type = bean.getItem_type();
        if (type == DataBean.TYPE_DATA) {
            Toast.makeText(this, bean.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            intent.putExtra("city_name",bean.getName());
            setResult(6,intent);
            finish();

        }
    }
}
