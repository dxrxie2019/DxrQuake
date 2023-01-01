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

public class CencData extends SimpleInteractors<DxrQuake> {
    @Filter("台网信息")
    @Required("DxrQuake.quake")
    void GetInfo(XiaoMingUser user, @FilterParameter("地震信息") String text) {
        user.sendMessage("正在向api接口请求信息,请稍候...");
        String url = HttpUtil.sendGet("https://api.projectbs.cn", "/v2/ceic/get_data.json?");
        try {
            JSONObject jsonObject = JSON.parseObject(url);
            JSONObject data = jsonObject.getJSONObject("data");
            user.sendMessage("中国地震台网" + data.getString("type") + "报" + "\n 发震时刻:" + data.getString("occurTime") + "\n 震中:" + data.getString("epicenter") + "\n震级:M" + data.getString("magnitude") + "\n 深度:" + data.getString("depth") + "KM" + "\n最大烈度(粗估):" + data.getString("maxInt") + "度");
        } catch (Exception e) {
            System.out.println("出现错误!" + e);
            user.sendMessage("出现错误!" + e);
        }
    }
}
