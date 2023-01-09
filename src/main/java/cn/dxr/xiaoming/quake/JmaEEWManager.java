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
        user.sendMessage("紧急地震速报推送功能已开启,当日本气象厅发布紧急地震速报时,机器人将会向所有群聊推送速报信息,如果群聊过多(建议群聊不超过5个)可能导致机器人被风控，请酌情选择。");
        getXiaoMingBot().getScheduler().run(JmaEEW.EEW());
    }
}
