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

public class JmaData extends SimpleInteractors<DxrQuake> {
    @Filter("日本信息")
    @Required("DxrQuake.quake")
    void GetInfo(XiaoMingUser user, @FilterParameter("地震信息") String text) {
        user.sendMessage("正在向api接口请求信息，请稍候...");
        String url = HttpUtil.sendGet("https://api.wolfx.jp", "/jma_eqlist.json?");
        try {
            JSONObject jsonObject = JSON.parseObject(url);
            JSONObject data = jsonObject.getJSONObject("No1");
            user.sendMessage("日本气象厅地震情报" + "\n 时间:" + data.getString("time") + "\n 震源地:" + data.getString("location") + "\n 震级:M" + data.getString("magnitude") + "\n 最大震度:" + data.getString("shindo") + "\n 深度:" + data.getString("depth") + "\n 津波(海啸)情报:" + data.getString("info"));
        }catch (Exception e) {
            System.out.println("出现错误!" + e);
            user.sendMessage("出现错误!" + e);
        }
    }
}

