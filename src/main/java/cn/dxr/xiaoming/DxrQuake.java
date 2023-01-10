package cn.dxr.xiaoming;

import cn.chuanwise.xiaoming.interactor.Interactors;
import cn.chuanwise.xiaoming.plugin.JavaPlugin;
import cn.chuanwise.xiaoming.plugin.Plugin;
import cn.dxr.xiaoming.message.Helper;
import cn.dxr.xiaoming.quake.*;

public class DxrQuake extends JavaPlugin {
    private static final DxrQuake INSTANCE = new DxrQuake();

    public static DxrQuake getInstance() {
        return INSTANCE;
    }

    public void onEnable() {
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new Helper(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new CencData(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new JmaData(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new CencHistory(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new JmaHistory(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new TimeSender(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors) new StationData(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors)new JmaDataSenderManager(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors)new JmaEEWManager(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors)new SCEEWManager(), (Plugin) this);
        getXiaoMingBot().getInteractorManager().registerInteractors((Interactors)new CencDataSenderManager(), (Plugin) this);
        getXiaoMingBot().getScheduler().run(TWEEW.EEW());
        getXiaoMingBot().getScheduler().run(CWBDataAutoSender.sender());
    }
}
