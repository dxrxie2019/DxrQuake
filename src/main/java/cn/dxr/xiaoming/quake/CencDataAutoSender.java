package cn.dxr.xiaoming.quake;

import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CencDataAutoSender {

    private static String md5 = null;

    public static Runnable sender() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String httpGet = HttpUtil.sendGet("https://api.projectbs.cn", "/v2/ceic/get_data.json?");
                    JSONObject jsonObject = JSON.parseObject(httpGet);
                    JSONObject json = jsonObject.getJSONObject("data");
                    if (!Objects.equals(json.getString("md5"), md5)) {
                        String time = json.getString("occurTime");
                        String type = json.getString("type");
                        String region = json.getString("epicenter");
                        String mag = json.getString("magnitude");
                        String depth = json.getString("depth");
                        String Int = json.getString("maxInt");
                        DxrQuake.getInstance().getLogger().info("中国地震台网信息更新");
                        DxrQuake.getInstance().getXiaoMingBot().getContactManager().getGroupContacts().forEach(groupContact -> groupContact.sendMessage("中国地震台网" + type + "测定," + time + "在" + region + "发生" + mag + "级地震," + "震源深度" + depth + "公里,粗估最大烈度" + Int + "度。"));
                        md5 = json.getString("md5");
                    }
                } catch (IllegalStateException ignored) {
                }
            }
        };
        new Timer().schedule(timerTask, 0L, 1000L);
        return null;
    }
}
