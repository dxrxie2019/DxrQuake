package cn.dxr.xiaoming.quake;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JmaHistory extends SimpleInteractors<DxrQuake> {
    @Filter("日本历史地震")
    @Required("DxrQuake.History")
    void GetInfo(XiaoMingUser user, @FilterParameter("历史地震") String text) {
        user.sendMessage("正在向api接口请求信息，请稍候...");
        String url = HttpUtil.sendGet("https://api.wolfx.jp", "/jma_eqlist.json?");
        try {
            JSONObject jsonObject = JSON.parseObject(url);
            JSONObject data  = jsonObject.getJSONObject("No1");
            JSONObject data1  = jsonObject.getJSONObject("No2");
            JSONObject data2  = jsonObject.getJSONObject("No3");
            JSONObject data3  = jsonObject.getJSONObject("No4");
            JSONObject data4  = jsonObject.getJSONObject("No5");
            JSONObject data5  = jsonObject.getJSONObject("No6");
            user.sendMessage("日本气象厅历史地震信息(6条)" + "\n1.时间:" + data.getString("time") + "\n 震源地:" + data.getString("location") + "\n震级:M" + data.getString("magnitude") + "\n 深度:" + data.getString("depth") + "\n 最大震度:" + data.getString("shindo") + "\n2.时间:" + data1.getString("time") + "\n震源地:" + data1.getString("location") + "\n震级:M" + data1.getString("magnitude") + "\n最大震度:" + data1.getString("shindo") + "   " + "\n3.时间:" + data2.getString("time") + "\n震源地:" + data2.getString("location") + "\n震级:M" + data2.getString("magnitude") + "\n最大震度:" + data2.getString("shindo") + "   " + "\n4.时间:" + data3.getString("time") + "\n震源地:" + data3.getString("location") + "\n震级:M" + data3.getString("magnitude") + "\n最大震度:" + data3.getString("shindo") + "   " + "\n5.时间:" + data4.getString("time") + "\n震源地:" + data4.getString("location") + "\n震级:M" + data4.getString("magnitude") + "\n最大震度:" + data4.getString("shindo") + "   " + "\n6.时间:" + data5.getString("time") + "\n震源地:" + data5.getString("location") + "\n震级:M" + data5.getString("magnitude") + "\n最大震度:" + data5.getString("shindo"));
        }catch (Exception e) {
            System.out.println("出现错误!" + e);
            user.sendMessage("出现错误!" + e);
        }
    }
}
