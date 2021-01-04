package com.mao.ky.entity;

import com.mao.ky.entity.sys.Browser;
import com.mao.ky.util.OAuthUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : create by zongx at 2020/11/10 10:30
 */
@Getter
@Setter
public class Sign {

    private Boolean del;            //删除标识
    private Long create;            //创建时间
    private String creator;         //创建者
    private Long update;            //更新时间
    private String updater;         //更新者

    public static void sign(Sign sign) {
        Browser browser = OAuthUtil.getBrowser();
        sign.setDel(false);
        sign.setCreate(System.currentTimeMillis());
        sign.setUpdate(System.currentTimeMillis());
        if (null != browser) {
            sign.setCreator(browser.getUser());
            sign.setUpdater(browser.getUser());
        }
    }

    public static void sign(Sign tVo, Sign tDo) {
        tVo.setCreate(tDo.getCreate());
        tVo.setCreator(tDo.getCreator());
        tVo.setUpdate(tDo.getUpdate());
        tVo.setUpdater(tDo.getUpdater());
        tVo.setDel(tDo.getDel());
    }

}
