package cn.dxr.xiaoming.quake;

import cn.dxr.xiaoming.DxrQuake;
import cn.dxr.xiaoming.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class TWEEW {

    private static String EventID = null;

    public static Runnable EEW() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String httpGet = HttpUtil.sendGet("https://exptech.com.tw", "/api/v1/earthquake/eew?type=earthquake");
                    JSONObject json = JSON.parseObject(httpGet);
                    if (!Objects.equals(json.getString("ID"), EventID)) {
                        String TimeStamp = json.getString("TimeStamp");
                        String region = json.getString("Location");
                        String depth = json.getString("Depth");
                        String Int = json.getString("Scale");
                        DxrQuake.getInstance().getLogger().info("台湾地震预警更新");
                        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                        Long time = new Long(TimeStamp);
                        String date = format.format(time);
                        DxrQuake.getInstance().getXiaoMingBot().getContactManager().getGroupContacts().forEach(groupContact -> groupContact.sendMessage("台湾强震及时警报" + "(" + json.getString("Unit") + ")" + "\n 时间: " + date + "\n 震央: " + region + "\n 深度: " + depth + "Km" + "\n 最大预估震度: " + Int + "级"));
                        EventID = json.getString("ID");
                    }
                }catch (IllegalStateException ignored) {
                }
            }
        };
        new Timer().schedule(timerTask, 0L, 5000L);
        return null;
    }
}
