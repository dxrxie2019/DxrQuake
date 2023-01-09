package cn.dxr.xiaoming.quake;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;

public class CencDataSenderManager extends SimpleInteractors<DxrQuake> {
    @Filter("开启台网信息推送")
    @Required("DxrQuake.sender")
    void Manager(XiaoMingUser user, @FilterParameter("推送") String text) {
        user.sendMessage("台网信息推送已开启,当中国地震台网发布地震信息时,机器人将会向所有群聊推送信息,如果群聊过多(建议群聊不超过5个)可能导致机器人被风控，请酌情选择。");
        getXiaoMingBot().getScheduler().run(CencDataAutoSender.sender());
    }
}
