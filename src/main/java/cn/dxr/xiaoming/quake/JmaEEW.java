package cn.dxr.xiaoming.quake;

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
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class JmaEEW {

    public static Runnable EEW() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1500).setConnectionRequestTimeout(1500).setSocketTimeout(1500).build();
                Thread thread = new Thread(() -> {
                    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
                    try {
                        HttpGet request = new HttpGet("https://api.wolfx.jp/nied_eew.json?");
                        HttpResponse response = httpClient.execute(request);
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            String responseData = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                            JsonObject json = JsonParser.parseString(responseData).getAsJsonObject();
                            if (json.get("eew").getAsBoolean()) {
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
                                String origin_time = new SimpleDateFormat().format(origin_time2);
                                if (json.get("is_final").getAsBoolean()) {
                                    type = "最终";
                                }
                                String finalType = type;
                                DxrQuake.getInstance().getXiaoMingBot().getContactManager().getPrivateContacts().forEach(privateContact -> privateContact.sendMessage("紧急地震速报" + "(" + flag + ")" +  "(" + finalType + "第" + num + "报)" + "\n 时间:" + origin_time + "(北京时间)" + "\n 震源地:" + region + "\n 震级:M" + mag + "\n 深度:" + depth + "\n 最大震度:" + shindo + "\n 发报时间:" + report_time + "(东京时间)"));
                            }
                        }
                    } catch (IllegalStateException | IOException | ParseException e) {
                        System.out.println("api接口错误，正在重试..." + e);
                    } finally {
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            System.out.println("无法关闭连接，正在重试..." + e);
                        }
                    }
                });
                thread.start();
            }
        };
        new Timer().schedule(timerTask, 0L, 1000L);
        return null;
    }
}
        
 