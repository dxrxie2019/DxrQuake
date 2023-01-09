package cn.dxr.xiaoming.quake;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;

public class JmaDataSenderManager extends SimpleInteractors<DxrQuake> {
    @Filter("开启日本信息推送")
    @Required("DxrQuake.sender")
    void Manager(XiaoMingUser user, @FilterParameter("自动推送") String text) {
        user.sendMessage("日本地震信息自动推送功能已开启,当日本地震信息更新时，机器人将全局(机器人的所有好友，不包括群聊)推送地震信息,该功能可能导致机器人被风控,非必要不建议使用。");
        getXiaoMingBot().getScheduler().run(JmaDataAutoSender.Sender());
    }
}
