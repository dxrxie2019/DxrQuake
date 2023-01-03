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

public class StationData extends SimpleInteractors<DxrQuake> {
    @Filter("测站数据")
    @Required("DxrQuake.stations")
    void GetInfo(XiaoMingUser user, @FilterParameter("地震测站数据") String text) {
        user.sendMessage("正在向api接口请求信息，请稍候...");
        String url = HttpUtil.sendGet("https://api.wolfx.jp", "/ntp.json?");
        JSONObject jsonObject = JSON.parseObject(url);
        JSONObject data = jsonObject.getJSONObject("CST");
        String url1 = HttpUtil.sendGet("https://api.wolfx.jp", "/red68/" + data.getString("int") + ".json?");
        try {
            JSONObject jsonObject1 = JSON.parseObject(url1);
            user.sendMessage("测站位置: 重庆市北碚区 \n 测站编号: AM.RED68.00.EHZ" + "\n 时间:" + jsonObject1.getString("update_at") + "\n 当前地表加速度:" + jsonObject1.getString("pga") + " gal" + "\n 最大加速度:" + jsonObject1.getString("max_pga") + " gal");
        }catch (Exception e) {
            getLogger().error("出现错误!" + e);
            user.sendError("出现错误!" + e);
        }
    }
}
