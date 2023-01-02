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

public class CencHistory extends SimpleInteractors<DxrQuake> {
    @Filter("台网历史地震")
    @Required("DxrQuake.History")
    void GetInfo(XiaoMingUser user, @FilterParameter("历史地震") String text) {
        user.sendMessage("正在向api接口请求信息，请稍候...");
        String url = HttpUtil.sendGet("https://api.wolfx.jp", "/cenc_eqlist.json?");
        try {
            JSONObject jsonObject = JSON.parseObject(url);
            JSONObject data  = jsonObject.getJSONObject("No1");
            JSONObject data1  = jsonObject.getJSONObject("No2");
            JSONObject data2  = jsonObject.getJSONObject("No3");
            JSONObject data3  = jsonObject.getJSONObject("No4");
            JSONObject data4  = jsonObject.getJSONObject("No5");
            JSONObject data5  = jsonObject.getJSONObject("No6");
            user.sendMessage("中国地震台网历史地震信息(6条)" + "\n1.发震时刻:" + data.getString("time") + "\n震中:" + data.getString("location") + "\n震级:M" + data.getString("magnitude") + "\n深度:" + data.getString("depth") + "KM   " +  "\n2.发震时刻:" + data1.getString("time") + "\n震中:" + data1.getString("location") + "\n震级:M" + data1.getString("magnitude") + "\n深度:" + data1.getString("depth") + "KM   " +  "\n3.发震时刻:" + data2.getString("time") + "\n震中:" + data2.getString("location") + "\n震级:M" + data2.getString("magnitude") + "\n深度:" + data2.getString("depth") + "KM   " +  "\n4.发震时刻:" + data3.getString("time") + "\n震中:" + data3.getString("location") + "\n震级:M" + data3.getString("magnitude") + "\n深度:" + data3.getString("depth") + "KM   " +  "\n5.发震时刻:" + data4.getString("time") + "\n震中:" + data4.getString("location") + "\n震级:M" + data4.getString("magnitude") + "\n深度:" + data4.getString("depth") + "KM   " +  "\n6.发震时刻:" + data5.getString("time") + "\n震中:" + data5.getString("location") + "\n震级:M" + data5.getString("magnitude") + "\n深度:" + data5.getString("depth") + "KM   ");
        } catch (Exception e) {
            getLogger().error("出现错误!" + e);
            user.sendError("出现错误!" + e);
        }
    }
}
