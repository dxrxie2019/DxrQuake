package cn.dxr.xiaoming.quake;

import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class JmaDataAutoSender {

    private static String md5 = null;

    private static String time_format_final;

    public static Runnable Sender() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String httpGet = HttpUtil.sendGet("https://api.wolfx.jp", "/jma_eqlist.json?");
                    JSONObject jsonObject = JSON.parseObject(httpGet);
                    JSONObject json = jsonObject.getJSONObject("No1");
                    if (!(json.getString("time") + json.getString("location") + json.getString("magnitude") + json.getString("depth") + json.getString("shindo") + json.getString("info").replace("\n", "").trim()).equals(md5)) {
                        String time = json.getString("time");
                        String region = json.getString("location");
                        String mag = json.getString("magnitude");
                        String depth = json.getString("depth");
                        String shindo = json.getString("shindo");
                        String info = json.getString("info").replace("\n", "").trim();
                        DxrQuake.getInstance().getLogger().info("收到新的地震信息");
                        DxrQuake.getInstance().getXiaoMingBot().getContactManager().getGroupContacts().forEach(groupContact -> groupContact.sendMessage("日本气象厅地震情报" + "\n 时间: " + time + "\n 震源地: " + region + "\n 震级: M" + mag + "\n 深度: " + depth + "\n 最大震度: " + shindo + "\n 津波(海啸)情报: " + info));
                        md5 = time + region + mag + depth + shindo + info;
                    }
                } catch (IllegalStateException ignored) {
                }
            }
        };
        new Timer().schedule(timerTask, 0L, 1000L);
        return null;
    }
}


