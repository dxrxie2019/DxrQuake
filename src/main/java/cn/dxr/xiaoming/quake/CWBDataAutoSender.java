package cn.dxr.xiaoming.quake;

import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CWBDataAutoSender {

    private static String EventID = null;
    public static Runnable sender() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String httpGet = HttpUtil.sendGet("https://exptech.com.tw", "/api/v1/earthquake/reports?limit=1");
                    JSONArray jsonArray = JSONArray.parseArray(httpGet);
                    JSONObject json = null;
                    for (Object object : jsonArray) {
                        json = JSONArray.parseObject(object.toString());
                    }
                    assert json != null;
                    if (!Objects.equals(json.getString("earthquakeNo"), EventID)) {
                        String num = json.getString("earthquakeNo");
                        String time = json.getString("originTime");
                        String region = json.getString("location");
                        String mag = json.getString("magnitudeValue");
                        String depth = json.getString("depth");
                        DxrQuake.getInstance().getLogger().info("台湾地震报告更新");
                        DxrQuake.getInstance().getXiaoMingBot().getContactManager().getGroupContacts().forEach(groupContact -> groupContact.sendMessage("台湾地震报告 \n 地震编号: " + num + "\n 时间: " + time + "\n 震央: " + region + "\n 震级: M" + mag + "\n 深度: " + depth + "Km"));
                        EventID = json.getString("earthquakeNo");
                    }
                } catch (IllegalStateException ignored) {
                }
            }
        };
        new Timer().schedule(timerTask, 0L, 5000L);
        return null;
    }
}
