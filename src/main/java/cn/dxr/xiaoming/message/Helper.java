package cn.dxr.xiaoming.message;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import cn.dxr.xiaoming.DxrQuake;

public class Helper extends SimpleInteractors<DxrQuake> {
    @Filter("地震插件帮助")
    @Required("DxrQuake.help")
    void sender(XiaoMingUser user, @FilterParameter("插件帮助") String text) {
        user.sendMessage("指令列表: \n 1.台网信息 查看中国地震台网(CENC)最新地震信息 \n 2.日本信息 查看日本气象厅(JMA)最新地震信息 \n 3.台网历史地震 查看中国地震台网(CENC)发布的历史地震信息(6条) \n 4.日本历史地震 查看日本气象厅(JMA)发布的历史地震信息(6条) \n 5.时间 查看当前准确时间 \n 6.测站数据 查看重庆市北碚区地震测站实时数据(将在api接口增加更多测站信息后展示其他地区的测站数据)");
    }
}
