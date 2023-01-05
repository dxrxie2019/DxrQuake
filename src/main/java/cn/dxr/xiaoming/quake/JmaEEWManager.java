package cn.dxr.xiaoming.quake;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;

public class JmaEEWManager extends SimpleInteractors<DxrQuake> {
    @Filter("开启紧急地震速报")
    @Required("DxrQuake.EEW")
    void Manager(XiaoMingUser user, @FilterParameter("紧急地震速报") String text) {
        user.sendMessage("紧急地震速报推送功能已开启,当日本气象厅发布紧急地震速报时,机器人将会向全局(机器人的所有好友,不包括群聊)推送速报内容,该功能可能会导致机器人被风控,非必要建议不使用该功能,若要关闭该功能,请重启机器人。");
        getXiaoMingBot().getScheduler().run(JmaEEW.EEW());
    }
}
