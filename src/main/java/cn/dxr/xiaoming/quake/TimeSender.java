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

public class TimeSender extends SimpleInteractors<DxrQuake> {
    @Filter("时间")
    @Required("DxrQuake.time")
    void GetInfo(XiaoMingUser user, @FilterParameter("时间取得") String text) {
        user.sendMessage("正在向api接口请求信息，请稍候...");
        String url = HttpUtil.sendGet("https://api.wolfx.jp", "/ntp.json?");
        try {
            JSONObject jsonObject = JSON.parseObject(url);
            JSONObject data = jsonObject.getJSONObject("JST");
            JSONObject data1 = jsonObject.getJSONObject("CST");
            user.sendMessage("当前准确时间:" + "\n北京时间: " + data.getString("str") + "\n 东京时间: " + data1.getString("str"));
        }catch (Exception e) {
            getLogger().error("出现错误!" + e);
            user.sendError("出现错误!" + e);
        }
    }
}
