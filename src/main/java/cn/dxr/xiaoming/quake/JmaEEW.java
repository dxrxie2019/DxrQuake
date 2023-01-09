package cn.dxr.xiaoming.quake;

import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class JmaEEW {

    public static String md5 = null;

    public static Runnable EEW() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                    try {
                        String httpGet = HttpUtil.sendGet("https://api.wolfx.jp", "/nied_eew.json?");
                        JSONObject json = JSON.parseObject(httpGet);
                            if (!(json.getString("origin_time") + json.getString("alertflg") + json.getString("report_time") + json.getString("report_num") + json.getString("region_name") + json.getString("magunitude") +  json.getString("depth") + json.getString("calcintensity")).equals(md5)) {
                                String type = "";
                                String time = json.getString("origin_time");
                                String flag = json.getString("alertflg");
                                String report_time = json.getString("report_time");
                                String num = json.getString("report_num");
                                String region = json.getString("region_name");
                                String mag = json.getString("magunitude");
                                String depth = json.getString("depth");
                                String shindo = json.getString("calcintensity");
                                SimpleDateFormat origin_time1 = new SimpleDateFormat("yyyyMMddHHmmss");
                                origin_time1.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
                                Date origin_time2 = origin_time1.parse(json.getString("origin_time"));
                                String origin_time = new SimpleDateFormat().format(origin_time2);
                                if (json.getBoolean("is_final")) {
                                    type = "最终";
                                }
                                md5 = time + flag + report_time + num + region + mag + depth + shindo;
                                String finalType = type;
                                DxrQuake.getInstance().getLogger().info("收到新的紧急地震速报");
                                DxrQuake.getInstance().getXiaoMingBot().getContactManager().getGroupContacts().forEach(groupContact -> groupContact.sendMessage("紧急地震速报" + "(" + flag + ")" +  "(" + finalType + "第" + num + "报)" + "\n 时间:" + origin_time + "(北京时间)" + "\n 震源地:" + region + "\n 震级:M" + mag + "\n 深度:" + depth + "\n 最大震度:" + shindo + "\n 发报时间:" + report_time + "(东京时间)"));
                            }

                    } catch (IllegalStateException | ParseException ignored) {
                    }
            }
        };
        new Timer().schedule(timerTask, 0L, 1000L);
        return null;
    }
}
        
 