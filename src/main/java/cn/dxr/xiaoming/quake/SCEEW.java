package cn.dxr.xiaoming.quake;

import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class SCEEW {

    private static String EventID = null;

    public static Runnable EEW() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String httpGet = HttpUtil.sendGet("https://api.wolfx.jp", "/sc_eew.json?");
                    JSONObject json = JSON.parseObject(httpGet);
                        if (!Objects.equals(json.getString("EventID"), EventID)) {
                            String origin_time = json.getString("OriginTime");
                            String num = json.getString("ReportNum");
                            String region = json.getString("HypoCenter");
                            String mag = json.getString("Magunitude");
                            String depth = "10km";
                            String Int = json.getString("MaxIntensity");
                            DxrQuake.getInstance().getLogger().info("收到新的地震预警");
                            DxrQuake.getInstance().getXiaoMingBot().getContactManager().getGroupContacts().forEach(groupContact -> groupContact.sendMessage("四川省地震局地震预警" + "(第" + num + "报)" + "\n 发震时刻: " + origin_time + "\n 震中: " + region + "\n 震级: M" + mag + "\n 深度: " + depth + "\n 最大烈度: " + Int + "度"));
                            EventID = json.getString("EventID");
                        }
            }catch (IllegalStateException ignored) {
                }
            }
        };
        new Timer().schedule(timerTask, 0L, 1000L);
        return null;
    }
}
