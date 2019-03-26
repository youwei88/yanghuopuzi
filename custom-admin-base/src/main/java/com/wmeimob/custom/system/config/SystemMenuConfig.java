package com.wmeimob.custom.system.config;


import com.wmeimob.custom.system.bean.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Shinez on 2017/1/16.
 */
public class SystemMenuConfig {

    private static Random random = new Random();


    /**
     * 加载root（SaaS）账号菜单
     * @return
     */
    public static List<SysMenu> loadSysFn() {
        List<SysMenu> systemFn=new ArrayList<>();
        //系统菜单
        SysMenu sysFn = new SysMenu();
        sysFn.setId(random.nextInt(100000));
        sysFn.setParentId(0);
        sysFn.setIcon("icon-settings");
        sysFn.setMenuName("权限");
        sysFn.setMenuHref("");
        List<SysMenu> childrenSysFnList = new ArrayList<>();
        SysMenu childrenSysFn = new SysMenu();

        childrenSysFn.setId(random.nextInt(100000) + 2);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-user-secret");
        childrenSysFn.setMenuName("角色管理");
        childrenSysFn.setMenuHref("/base/sys-role");
        childrenSysFnList.add(childrenSysFn);
        sysFn.setSysMenus(childrenSysFnList);

        childrenSysFn = new SysMenu();
        childrenSysFn.setId(random.nextInt(100000) + 1);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-users");
        childrenSysFn.setMenuName("管理员账号");
        childrenSysFn.setMenuHref("/base/sys-user");
        childrenSysFnList.add(childrenSysFn);
        sysFn.setSysMenus(childrenSysFnList);

        childrenSysFn = new SysMenu();
        childrenSysFn.setId(random.nextInt(100000) + 2);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-database");
        childrenSysFn.setMenuName("资源管理");
        childrenSysFn.setMenuHref("/base/sys-resource");
        childrenSysFnList.add(childrenSysFn);
        sysFn.setSysMenus(childrenSysFnList);

        systemFn.add(sysFn);

        //公众号菜单
        sysFn = new SysMenu();
        sysFn.setId(random.nextInt(100000) + 2);
        sysFn.setParentId(0);
        sysFn.setIcon("fa fa-weixin");
        sysFn.setMenuName("公众号");
        sysFn.setMenuHref("");
        childrenSysFnList = new ArrayList<>();
        childrenSysFn = new SysMenu();
        childrenSysFn.setId(random.nextInt(100000) + 3);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-weixin");
        childrenSysFn.setMenuName("公众号设置");
        childrenSysFn.setMenuHref("/base/mps");
        childrenSysFnList.add(childrenSysFn);
        sysFn.setSysMenus(childrenSysFnList);
        systemFn.add(sysFn);


        return  systemFn;
    }


    /**
     * 加载管理员角色菜单
     * @return
     */
    public static List<SysMenu> loadAdminFn() {
        List<SysMenu> adminFn=new ArrayList<>();
        //权限
        SysMenu sysFn = new SysMenu();
        sysFn.setId(random.nextInt(100000));
        sysFn.setParentId(0);
        sysFn.setIcon("icon-lock");
        sysFn.setMenuName("权限");
        sysFn.setMenuHref("");
        List<SysMenu> childrenSysFnList = new ArrayList<>();

        SysMenu childrenSysFn = new SysMenu();

        childrenSysFn.setId(random.nextInt(100000) + 2);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-user-secret");
        childrenSysFn.setMenuName("角色管理");
        childrenSysFn.setMenuHref("/base/sys-role");
        childrenSysFnList.add(childrenSysFn);

        childrenSysFn = new SysMenu();
        childrenSysFn.setId(random.nextInt(100000) + 1);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-users");
        childrenSysFn.setMenuName("子账号管理");
        childrenSysFn.setMenuHref("/base/sys-user");
        childrenSysFnList.add(childrenSysFn);


        childrenSysFn = new SysMenu();
        childrenSysFn.setId(random.nextInt(100000) + 2);
        childrenSysFn.setParentId(sysFn.getId());
        childrenSysFn.setIcon("fa fa-database");
        childrenSysFn.setMenuName("资源管理");
        childrenSysFn.setMenuHref("/base/sys-resource");
        childrenSysFnList.add(childrenSysFn);

        sysFn.setSysMenus(childrenSysFnList);
        adminFn.add(sysFn);

        return adminFn;
    }


}
