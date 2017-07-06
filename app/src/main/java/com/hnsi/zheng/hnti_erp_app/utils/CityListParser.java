package com.hnsi.zheng.hnti_erp_app.utils;

import android.util.Log;

import com.hnsi.zheng.hnti_erp_app.beans.CityListEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 城市列表解析器
 * Created by Zheng on 2016/8/31.
 */
public class CityListParser{
    public static ArrayList<CityListEntity> parseCityList(){
        ArrayList<CityListEntity>mCityList=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject("{\n" +
                    "\"content\":[\n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                   \"南阳\",\n" +
                    "                    \"洛阳\",\n" +
                    "                    \"三门峡\",\n" +
                    "                    \"商丘\",\n" +
                    "                    \"焦作\",\n" +
                    "                    \"开封\",\n" +
                    "                    \"驻马店\",\n" +
                    "                    \"濮阳\",\n" +
                    "                    \"许昌\",\n" +
                    "                    \"安阳\",\n" +
                    "                    \"信阳\",\n" +
                    "                    \"漯河\",\n" +
                    "                    \"平顶山\",\n" +
                    "                    \"郑州\",\n" +
                    "                    \"新乡\",\n" +
                    "                    \"鹤壁\",\n" +
                    "                    \"周口\"\n" +
                    "                ],\n" +
                    "            \"province\":\"河南\"\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "\t\t {\n" +
                    "            \"citys\":[\n" +
                    "                    \"北京\"\n" +
                    "                ],\n" +
                    "            \"province\":\"北京\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"上海\"\n" +
                    "                ],\n" +
                    "            \"province\":\"上海\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"天津\"\n" +
                    "                ],\n" +
                    "            \"province\":\"天津\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"重庆\"\n" +
                    "                ],\n" +
                    "            \"province\":\"重庆\"\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "\t\t{\n" +
                    "            \"citys\":[\n" +
                    "                    \"丽水\",\n" +
                    "                    \"舟山\",\n" +
                    "                    \"宁波\",\n" +
                    "                    \"衢州\",\n" +
                    "                    \"温州\",\n" +
                    "                    \"杭州\",\n" +
                    "                    \"台州\",\n" +
                    "                    \"嘉兴\",\n" +
                    "                    \"绍兴\",\n" +
                    "                    \"金华\",\n" +
                    "                    \"湖州\"\n" +
                    "                ],\n" +
                    "            \"province\":\"浙江\"\n" +
                    "        },\n" +
                    "\n" +
                    " \t\t{\n" +
                    "            \"citys\":[\n" +
                    "                    \"珠海\",\n" +
                    "                    \"惠州\",\n" +
                    "                    \"清远\",\n" +
                    "                    \"韶关\",\n" +
                    "                    \"江门\",\n" +
                    "                    \"揭阳\",\n" +
                    "                    \"云浮\",\n" +
                    "                    \"佛山\",\n" +
                    "                    \"广州\",\n" +
                    "                    \"深圳\",\n" +
                    "                    \"河源\",\n" +
                    "                    \"汕头\",\n" +
                    "                    \"汕尾\",\n" +
                    "                    \"茂名\",\n" +
                    "                    \"肇庆\",\n" +
                    "                    \"东莞\",\n" +
                    "                    \"湛江\",\n" +
                    "                    \"潮州\",\n" +
                    "                    \"阳江\",\n" +
                    "                    \"中山\",\n" +
                    "                    \"梅州\"\n" +
                    "                ],\n" +
                    "            \"province\":\"广东\"\n" +
                    "        },\n" +
                    "\n" +
                    "\t\t{\n" +
                    "            \"citys\":[\n" +
                    "                   \"连云港\",\n" +
                    "                    \"盐城\",\n" +
                    "                    \"无锡\",\n" +
                    "                    \"宿迁\",\n" +
                    "                    \"扬州\",\n" +
                    "                    \"镇江\",\n" +
                    "                    \"南京\",\n" +
                    "                    \"徐州\",\n" +
                    "                    \"泰州\",\n" +
                    "                    \"南通\",\n" +
                    "                    \"常州\",\n" +
                    "                    \"淮安\",\n" +
                    "                    \"苏州\"\n" +
                    "                ],\n" +
                    "            \"province\":\"江苏\"\n" +
                    "        },\n" +
                    "\n" +
                    "\t\t{\n" +
                    "            \"citys\":[\n" +
                    "                  \"淄博\",\n" +
                    "                    \"烟台\",\n" +
                    "                    \"日照\",\n" +
                    "                    \"荷泽\",\n" +
                    "                    \"潍坊\",\n" +
                    "                    \"济南\",\n" +
                    "                    \"济宁\",\n" +
                    "                    \"青岛\",\n" +
                    "                    \"临沂\",\n" +
                    "                    \"威海\",\n" +
                    "                    \"莱芜\",\n" +
                    "                    \"泰安\",\n" +
                    "                    \"东营\",\n" +
                    "                    \"聊城\",\n" +
                    "                    \"枣庄\",\n" +
                    "                    \"滨州\",\n" +
                    "                    \"德州\"\n" +
                    "                ],\n" +
                    "            \"province\":\"山东\"\n" +
                    "        },\n" +
                    "\n" +
                    "\t\t {\n" +
                    "            \"citys\":[\n" +
                    "                    \"南平\",\n" +
                    "                    \"厦门\",\n" +
                    "                    \"福州\",\n" +
                    "                    \"宁德\",\n" +
                    "                    \"龙岩\",\n" +
                    "                    \"莆田\",\n" +
                    "                    \"漳州\",\n" +
                    "                    \"泉州\",\n" +
                    "                    \"三明\"\n" +
                    "                ],\n" +
                    "            \"province\":\"福建\"\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "\t\t {\n" +
                    "            \"citys\":[\n" +
                    "                   \"淮南\",\n" +
                    "                    \"黄山\",\n" +
                    "                    \"蚌埠\",\n" +
                    "                    \"合肥\",\n" +
                    "                    \"宿州\",\n" +
                    "                    \"六安\",\n" +
                    "                    \"池州\",\n" +
                    "                    \"芜湖\",\n" +
                    "                    \"宣城\",\n" +
                    "                    \"巢湖\",\n" +
                    "                    \"亳州\",\n" +
                    "                    \"阜阳\",\n" +
                    "                    \"铜陵\",\n" +
                    "                    \"淮北\",\n" +
                    "                    \"滁州\",\n" +
                    "                    \"马鞍山\",\n" +
                    "                    \"安庆\"\n" +
                    "                ],\n" +
                    "            \"province\":\"安徽\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                     \"宜宾\",\n" +
                    "                    \"巴中\",\n" +
                    "                    \"南充\",\n" +
                    "                    \"成都\",\n" +
                    "                    \"凉山彝族\",\n" +
                    "                    \"眉山\",\n" +
                    "                    \"阿坝\",\n" +
                    "                    \"乐山\",\n" +
                    "                    \"绵阳\",\n" +
                    "                    \"广安\",\n" +
                    "                    \"广元\",\n" +
                    "                    \"德阳\",\n" +
                    "                    \"资阳\",\n" +
                    "                    \"达州\",\n" +
                    "                    \"泸州\",\n" +
                    "                    \"自贡\",\n" +
                    "                    \"遂宁\",\n" +
                    "                    \"甘孜藏族\",\n" +
                    "                    \"雅安\",\n" +
                    "                    \"内江\",\n" +
                    "                    \"攀枝花\"\n" +
                    "                ],\n" +
                    "            \"province\":\"四川\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                    \"咸宁\",\n" +
                    "                    \"宜昌\",\n" +
                    "                    \"黄冈\",\n" +
                    "                    \"荆州\",\n" +
                    "                    \"孝感\",\n" +
                    "                    \"荆门\",\n" +
                    "                    \"十堰\",\n" +
                    "                    \"鄂州\",\n" +
                    "                    \"天门\",\n" +
                    "                    \"潜江\",\n" +
                    "                    \"恩施\",\n" +
                    "                    \"武汉\",\n" +
                    "                    \"仙桃\",\n" +
                    "                    \"神农架林\",\n" +
                    "                    \"随州\",\n" +
                    "                    \"黄石\",\n" +
                    "                    \"襄樊\"\n" +
                    "                ],\n" +
                    "            \"province\":\"湖北\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                   \"张家口\",\n" +
                    "                    \"邯郸\",\n" +
                    "                    \"邢台\",\n" +
                    "                    \"衡水\",\n" +
                    "                    \"秦皇岛\",\n" +
                    "                    \"廊坊\",\n" +
                    "                    \"保定\",\n" +
                    "                    \"承德\",\n" +
                    "                    \"唐山\",\n" +
                    "                    \"沧州\",\n" +
                    "                    \"石家庄\"\n" +
                    "                ],\n" +
                    "            \"province\":\"河北\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                   \"德宏\",\n" +
                    "                    \"玉溪\",\n" +
                    "                    \"曲靖\",\n" +
                    "                    \"保山\",\n" +
                    "                    \"怒江\",\n" +
                    "                    \"迪庆\",\n" +
                    "                    \"昭通\",\n" +
                    "                    \"昆明\",\n" +
                    "                    \"楚雄\",\n" +
                    "                    \"文山\",\n" +
                    "                    \"西双版纳\",\n" +
                    "                    \"丽江\",\n" +
                    "                    \"红河\",\n" +
                    "                    \"大理\",\n" +
                    "                    \"临沧\",\n" +
                    "                    \"思茅\"\n" +
                    "                ],\n" +
                    "            \"province\":\"云南\"\n" +
                    "        },\n" +
                    "\n" +
                    "\t\t  {\n" +
                    "            \"citys\":[\n" +
                    "                   \"鹤岗\",\n" +
                    "                    \"大兴安岭地\",\n" +
                    "                    \"大庆\",\n" +
                    "                    \"七台河\",\n" +
                    "                    \"齐齐哈尔\",\n" +
                    "                    \"牡丹江\",\n" +
                    "                    \"黑河\",\n" +
                    "                    \"双鸭山\",\n" +
                    "                    \"绥化\",\n" +
                    "                    \"伊春\",\n" +
                    "                    \"佳木斯\",\n" +
                    "                    \"哈尔滨\",\n" +
                    "                    \"鸡西\"\n" +
                    "                ],\n" +
                    "            \"province\":\"黑龙江\"\n" +
                    "        },\n" +
                    "        \n" +
                    "          {\n" +
                    "            \"citys\":[\n" +
                    "                   \"松原\",\n" +
                    "                    \"四平\",\n" +
                    "                    \"白城\",\n" +
                    "                    \"白山\",\n" +
                    "                    \"吉林\",\n" +
                    "                    \"通化\",\n" +
                    "                    \"长春\",\n" +
                    "                    \"延边朝鲜族\",\n" +
                    "                    \"辽源\"\n" +
                    "                ],\n" +
                    "            \"province\":\"吉林\"\n" +
                    "        },\n" +
                    "        \n" +
                    "          {\n" +
                    "            \"citys\":[\n" +
                    "                    \"铁岭\",\n" +
                    "                    \"葫芦岛\",\n" +
                    "                    \"营口\",\n" +
                    "                    \"本溪\",\n" +
                    "                    \"辽阳\",\n" +
                    "                    \"盘锦\",\n" +
                    "                    \"阜新\",\n" +
                    "                    \"朝阳\",\n" +
                    "                    \"锦州\",\n" +
                    "                    \"抚顺\",\n" +
                    "                    \"丹东\",\n" +
                    "                    \"沈阳\",\n" +
                    "                    \"鞍山\",\n" +
                    "                    \"大连\"\n" +
                    "                ],\n" +
                    "            \"province\":\"辽宁\"\n" +
                    "        },\n" +
                    "        \n" +
                    "          {\n" +
                    "            \"citys\":[\n" +
                    "                   \"保亭\",\n" +
                    "                    \"澄迈\",\n" +
                    "                    \"南沙群岛\",\n" +
                    "                    \"陵水黎族\",\n" +
                    "                    \"中沙群岛\",\n" +
                    "                    \"屯昌\",\n" +
                    "                    \"昌江黎族\",\n" +
                    "                    \"乐东黎族\",\n" +
                    "                    \"琼海\",\n" +
                    "                    \"儋州\",\n" +
                    "                    \"文昌\",\n" +
                    "                    \"万宁\",\n" +
                    "                    \"白沙黎族\",\n" +
                    "                    \"海口\",\n" +
                    "                    \"三亚\",\n" +
                    "                    \"五指山\",\n" +
                    "                    \"琼中\",\n" +
                    "                    \"东方\",\n" +
                    "                    \"定安\",\n" +
                    "                    \"西沙群岛\",\n" +
                    "                    \"临高\"\n" +
                    "                ],\n" +
                    "            \"province\":\"海南\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                     \"郴州\",\n" +
                    "                    \"岳阳\",\n" +
                    "                    \"怀化\",\n" +
                    "                    \"娄底\",\n" +
                    "                    \"张家界\",\n" +
                    "                    \"益阳\",\n" +
                    "                    \"湘西土家族苗族\",\n" +
                    "                    \"常德\",\n" +
                    "                    \"湘潭\",\n" +
                    "                    \"永州\",\n" +
                    "                    \"衡阳\",\n" +
                    "                    \"株洲\",\n" +
                    "                    \"长沙\",\n" +
                    "                    \"邵阳\"\n" +
                    "                ],\n" +
                    "            \"province\":\"湖南\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"毕节地\",\n" +
                    "                    \"黔南\",\n" +
                    "                    \"六盘水\",\n" +
                    "                    \"黔东南\",\n" +
                    "                    \"贵阳\",\n" +
                    "                    \"遵义\",\n" +
                    "                    \"铜仁地\",\n" +
                    "                    \"黔西南\",\n" +
                    "                    \"安顺\"\n" +
                    "                ],\n" +
                    "            \"province\":\"贵州\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"南昌\",\n" +
                    "                    \"萍乡\",\n" +
                    "                    \"景德镇\",\n" +
                    "                    \"吉安\",\n" +
                    "                    \"九江\",\n" +
                    "                    \"新余\",\n" +
                    "                    \"鹰潭\",\n" +
                    "                    \"抚州\",\n" +
                    "                    \"赣州\",\n" +
                    "                    \"上饶\",\n" +
                    "                    \"宜春\"\n" +
                    "                ],\n" +
                    "            \"province\":\"江西\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                   \"贺州\",\n" +
                    "                    \"梧州\",\n" +
                    "                    \"河池\",\n" +
                    "                    \"百色\",\n" +
                    "                    \"来宾\",\n" +
                    "                    \"贵港\",\n" +
                    "                    \"玉林\",\n" +
                    "                    \"钦州\",\n" +
                    "                    \"北海\",\n" +
                    "                    \"柳州\",\n" +
                    "                    \"桂林\",\n" +
                    "                    \"南宁\",\n" +
                    "                    \"防城港\",\n" +
                    "                    \"崇左\"\n" +
                    "                ],\n" +
                    "            \"province\":\"广西\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                     \"咸阳\",\n" +
                    "                    \"铜川\",\n" +
                    "                    \"商洛\",\n" +
                    "                    \"榆林\",\n" +
                    "                    \"渭南\",\n" +
                    "                    \"汉中\",\n" +
                    "                    \"安康\",\n" +
                    "                    \"延安\",\n" +
                    "                    \"宝鸡\",\n" +
                    "                    \"西安\"\n" +
                    "                ],\n" +
                    "            \"province\":\"陕西\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"临汾\",\n" +
                    "                    \"晋中\",\n" +
                    "                    \"朔州\",\n" +
                    "                    \"运城\",\n" +
                    "                    \"晋城\",\n" +
                    "                    \"阳泉\",\n" +
                    "                    \"忻州\",\n" +
                    "                    \"大同\",\n" +
                    "                    \"长治\",\n" +
                    "                    \"太原\",\n" +
                    "                    \"吕梁\"\n" +
                    "                ],\n" +
                    "            \"province\":\"山西\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"黄南\",\n" +
                    "                    \"海东地\",\n" +
                    "                    \"果洛\",\n" +
                    "                    \"西宁\",\n" +
                    "                    \"海北\",\n" +
                    "                    \"玉树\",\n" +
                    "                    \"海南\",\n" +
                    "                    \"海西\"\n" +
                    "                ],\n" +
                    "            \"province\":\"青海\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                     \"石嘴山\",\n" +
                    "                    \"固原\",\n" +
                    "                    \"中卫\",\n" +
                    "                    \"银川\",\n" +
                    "                    \"吴忠\"\n" +
                    "                ],\n" +
                    "            \"province\":\"宁夏\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "        {\n" +
                    "            \"citys\":[\n" +
                    "                    \"兰州\",\n" +
                    "                    \"金昌\",\n" +
                    "                    \"甘南藏族\",\n" +
                    "                    \"平凉\",\n" +
                    "                    \"嘉峪关\",\n" +
                    "                    \"天水\",\n" +
                    "                    \"白银\",\n" +
                    "                    \"武威\",\n" +
                    "                    \"张掖\",\n" +
                    "                    \"庆阳\",\n" +
                    "                    \"定西\",\n" +
                    "                    \"临夏回族\",\n" +
                    "                    \"酒泉\",\n" +
                    "                    \"陇南\"\n" +
                    "                ],\n" +
                    "            \"province\":\"甘肃\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                     \"阿里\",\n" +
                    "                    \"拉萨\",\n" +
                    "                    \"山南\",\n" +
                    "                    \"日喀则\",\n" +
                    "                    \"那曲\",\n" +
                    "                    \"昌都\",\n" +
                    "                    \"林芝\"\n" +
                    "                ],\n" +
                    "            \"province\":\"西藏\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                      \"巴彦淖尔\",\n" +
                    "                    \"锡林郭勒盟\",\n" +
                    "                    \"兴安盟\",\n" +
                    "                    \"乌兰察布\",\n" +
                    "                    \"鄂尔多斯\",\n" +
                    "                    \"乌海\",\n" +
                    "                    \"包头\",\n" +
                    "                    \"呼和浩特\",\n" +
                    "                    \"呼伦贝尔\",\n" +
                    "                    \"通辽\",\n" +
                    "                    \"阿拉善盟\",\n" +
                    "                    \"赤峰\"\n" +
                    "                ],\n" +
                    "            \"province\":\"内蒙古\"\n" +
                    "        },\n" +
                    "        \n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                    \"伊犁\",\n" +
                    "                    \"克拉玛依\",\n" +
                    "                    \"哈密\",\n" +
                    "                    \"石河子\",\n" +
                    "                    \"吐鲁番\",\n" +
                    "                    \"阿拉尔\",\n" +
                    "                    \"阿勒泰\",\n" +
                    "                    \"乌鲁木齐\",\n" +
                    "                    \"塔城地\",\n" +
                    "                    \"昌吉\",\n" +
                    "                    \"克孜勒\",\n" +
                    "                    \"图木舒克\",\n" +
                    "                    \"阿克苏\",\n" +
                    "                    \"五家渠\",\n" +
                    "                    \"巴音郭楞\",\n" +
                    "                    \"和田\",\n" +
                    "                    \"博尔塔拉\",\n" +
                    "                    \"喀什\"\n" +
                    "                ],\n" +
                    "            \"province\":\"新疆\"\n" +
                    "        },\n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                    \"台湾\"\n" +
                    "                ],\n" +
                    "            \"province\":\"台湾\"\n" +
                    "        },\n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                    \"香港\"\n" +
                    "                ],\n" +
                    "            \"province\":\"香港\"\n" +
                    "        },\n" +
                    "        \n" +
                    "         {\n" +
                    "            \"citys\":[\n" +
                    "                    \"澳门\"\n" +
                    "                ],\n" +
                    "            \"province\":\"澳门\"\n" +
                    "        }\n" +
                    "\n" +
                    "\n" +
                    "]\n" +
                    "}");

            Log.e("citylist", obj.toString());

            JSONArray listArray=obj.getJSONArray("content");

            for(int i=0;i<listArray.length();i++){
                JSONObject jsonObject=listArray.getJSONObject(i);
                CityListEntity entity=new CityListEntity();
                entity.setProvince(jsonObject.getString("province"));
                entity.setCityListArray(jsonObject.getJSONArray("citys"));
                mCityList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mCityList;
    }
}
