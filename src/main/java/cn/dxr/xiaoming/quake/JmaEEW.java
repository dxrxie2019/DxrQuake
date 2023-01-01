package cn.dxr.xiaoming.quake;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class JmaEEW extends SimpleInteractors<DxrQuake> {
    private static String update_report = null;
    private static String time_format;
    private static boolean notification_bool;
    @Filter("开启紧急地震速报")
    @Required("DxrQuake.EEW")
    public void EEW(XiaoMingUser user) {
        user.sendMessage("紧急地震速报接收功能已开启，当日本气象厅发布紧急地震速报时，机器人将会为您推送速报内容。");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1500).setConnectionRequestTimeout(1500).setSocketTimeout(1500).build();
        Thread thread = new Thread(() -> {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            try {
                HttpGet request = new HttpGet("https://api.wolfx.jp/nied_eew.json?");
                HttpResponse response = httpClient.execute(request);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String responseData = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    JsonObject json = JsonParser.parseString(responseData).getAsJsonObject();
                    if (json.get("eew").getAsBoolean() && !Objects.equals(json.get("report_time").getAsString(), update_report)) {
                        String type = "";
                        String flag = json.get("alertflg").getAsString();
                        String report_time = json.get("report_time").getAsString();
                        String num = json.get("report_num").getAsString();
                        String region = json.get("region_name").getAsString();
                        String mag = json.get("magunitude").getAsString();
                        String depth = json.get("depth").getAsString();
                        String shindo = json.get("calcintensity").getAsString();
                        SimpleDateFormat origin_time1 = new SimpleDateFormat("yyyyMMddHHmmss");
                        origin_time1.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
                        Date origin_time2 = origin_time1.parse(json.get("origin_time").getAsString());
                        String origin_time = new SimpleDateFormat(time_format).format(origin_time2);
                        if (json.get("is_final").getAsBoolean()) {
                            type = "最终报";
                        }
                        if (notification_bool) {
                            System.out.println("收到新的紧急地震速报");
                        }
                        update_report = report_time;
                        user.sendMessage("紧急地震速报" + "(" + flag + ")" +  "(" + type + "第" + num + "报)" + "\n 时间:" + origin_time + "\n 震源地:" + region + "\n 震级:" + mag + "\n 深度:" + depth + "\n 最大震度:" + shindo + "\n 发报时间:" + report_time);
                    } else {
                        if (notification_bool) {
                            System.out.println("暂无生效中的紧急地震速报");
                        }
                    }
                }
            } catch (IllegalStateException | IOException | ParseException e) {
                if (notification_bool) {
                    System.out.println("api接口错误，正在重试...");
                }
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    if (notification_bool) {
                        System.out.println("无法关闭连接，正在重试...");
                    }
                }
            }
        });
    }
}


