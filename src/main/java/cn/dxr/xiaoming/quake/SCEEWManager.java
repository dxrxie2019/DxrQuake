package cn.dxr.xiaoming.quake;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;

public class SCEEWManager extends SimpleInteractors<DxrQuake> {
    @Filter("开启地震预警")
    @Required("DxrQuake.EEW")
    void Manager(XiaoMingUser user, @FilterParameter("地震预警") String text) {
        user.sendMessage("地震预警功能已开启,当四川省地震局发布地震预警时,机器人将会向所有群聊推送预警信息,如果群聊过多(建议群聊不超过5个)可能导致机器人被风控，请酌情选择。");
        getXiaoMingBot().getScheduler().run(SCEEW.EEW());
    }
}
