package com.wmeimob.custom.system.service;

import com.wmeimob.custom.system.bean.SysUserDataRole;
import com.wmeimob.custom.yhpz.bean.Branch;
import com.wmeimob.tool.SpringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static com.wmeimob.custom.system.bean.SysUserDataRole.*;

/**
 * Created by Shinez on 2017/6/21.
 */

@Service
public class SysUserDataRoleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 超管权限集map
     */
    private static Map<String, SysUserDataRole> adminDataRoleMap;

    /**
     * 子账号权限集map
     */
    private static Map<String, SysUserDataRole> childrenDataRoleMap;


    /**
     * 根据权限CODE获取超管数据权限
     *
     * @param dataAuthCode
     * @return
     */
    public SysUserDataRole getAdminDataRoleByDataRoleCode(String dataAuthCode) {
        return getAdminDataRole().get(dataAuthCode);
    }

    /**
     * 根据权限CODE获取子账号数据权限
     *
     * @param dataAuthCode
     * @return
     */
    public SysUserDataRole getChildrenDataRoleByCode(String dataAuthCode) {
        return getChildrenDataRole().get(dataAuthCode);
    }


    /**
     * 更新超管数据权限
     */
    public void updateAdminDataRole(String dataAuthCode, Object selectData) {
        updateDataRole(dataAuthCode, selectData, SysUserDataRole.DATA_ROLE_TYPE_ADMIN);
    }

    /**
     * 更新子账号数据权限
     */
    public void updateChildrenDataRole(String dataAuthCode, Object selectData) {
        updateDataRole(dataAuthCode, selectData, SysUserDataRole.DATA_ROLE_TYPE_CHILDREN);
    }

    private void removeOldSelect(Integer src, List<Object> list) throws IllegalAccessException, NoSuchFieldException {
        Iterator<Object> iterator = list.iterator();
        Class cls;
        Object select;
        while (iterator.hasNext()) {
            select = iterator.next();
            cls = select.getClass();
            Field s = cls.getDeclaredField("id");
            s.setAccessible(true);
            Integer id = (Integer) s.get(select);
            if (id.equals(src)) {
                iterator.remove();
            }
        }
    }

    /**
     * 更新数据权限
     */
    private void updateDataRole(String dataAuthCode, Object selectData, Integer type) {
        SysUserDataRole sysUserDataRole;
        Class cls = selectData.getClass();
        try {
            Field field = cls.getDeclaredField("id");
            field.setAccessible(true);
            Integer idVal = (Integer) field.get(selectData);
            if (SysUserDataRole.DATA_ROLE_TYPE_ADMIN.equals(type)) {
                sysUserDataRole = getAdminDataRole().get(dataAuthCode);
                List<Object> selects = sysUserDataRole.getDataSelects();
                removeOldSelect(idVal, selects);
                selects.add(selectData);
                sysUserDataRole.setDataSelects(selects);
                adminDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);
            } else {
                sysUserDataRole = getChildrenDataRole().get(dataAuthCode);
                List<Object> selects = sysUserDataRole.getDataSelects();
                removeOldSelect(idVal, selects);
                selects.add(selectData);
                sysUserDataRole.setDataSelects(selects);
//            childrenDataRole.add(sysUserDataRole);
                childrenDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 超管支持的数据权限
     */
    public Map<String, SysUserDataRole> getAdminDataRole() {
        if (adminDataRoleMap == null) {
            Object object;
            Method method;
            SysUserDataRole sysUserDataRole;
            try {
                sysUserDataRole = new SysUserDataRole();
                sysUserDataRole.setDataRoleCode("BY_MP_ID");
                sysUserDataRole.setRoleName("按公众号");
                sysUserDataRole.setTableName("wechatMps");
                sysUserDataRole.setColumnName("id");
                object = SpringHelper.getBean(sysUserDataRole.getTableName() + "Mapper");
                method = ReflectionUtils.findMethod(object.getClass(), QUERY_DATA_ROLE_METHOD);
                sysUserDataRole.setDataSelects((List<Object>) ReflectionUtils.invokeMethod(method, object));
                adminDataRoleMap = new HashMap<>();
                adminDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);

                sysUserDataRole = new SysUserDataRole();
                sysUserDataRole.setDataRoleCode("BY_BRANCH_ID");
                sysUserDataRole.setRoleName("按分公司");
                sysUserDataRole.setTableName("branch");
                sysUserDataRole.setColumnName("id");
                object = SpringHelper.getBean(sysUserDataRole.getTableName() + "Mapper");
                method = ReflectionUtils.findMethod(object.getClass(), QUERY_DATA_ROLE_METHOD);
                sysUserDataRole.setDataSelects((List<Object>) ReflectionUtils.invokeMethod(method, object));
                adminDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);

                sysUserDataRole = new SysUserDataRole();
                sysUserDataRole.setDataRoleCode("BY_LITE_ID");
                sysUserDataRole.setRoleName("按加盟店");
                sysUserDataRole.setTableName("lite");
                sysUserDataRole.setColumnName("id");
                object = SpringHelper.getBean(sysUserDataRole.getTableName() + "Mapper");
                method = ReflectionUtils.findMethod(object.getClass(), QUERY_DATA_ROLE_METHOD);
                sysUserDataRole.setDataSelects((List<Object>) ReflectionUtils.invokeMethod(method, object));
                adminDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return adminDataRoleMap;
            }
        }
        return adminDataRoleMap;
    }

    /**
     * 子账号支持的数据权限
     */
    public Map<String, SysUserDataRole> getChildrenDataRole() {
////        if (childrenDataRole == null) {
//        //TODO 从数据库或配置文件初始化
//        SysUserDataRole sysDataRole = new SysUserDataRole();
//        sysDataRole.level = 2;
//        sysDataRole.setRoleCode("BY_SCHOOL_ID");
//        sysDataRole.setRoleName("按学校");
//        sysDataRole.setTableName("schoolyard");
//        sysDataRole.setColumnName("id");
//        Object object = SpringHelper.getBean(sysDataRole.getTableName() + "Mapper");
//        Method method =ReflectionUtils.findMethod(object.getClass(),"selectForDataRole");
//        sysDataRole.setDataSelects((List<Object>) ReflectionUtils.invokeMethod(method,object));
//        childrenDataRole = new ArrayList<>();
//        childrenDataRole.add(sysDataRole);

        //添加其它权限规则
//        }
        return childrenDataRoleMap;
    }

    /**
     * 移除超管数据权限
     *
     * @param dataAuthCode
     * @param dataSelectId
     */
    public void removeAdminDataRole(String dataAuthCode, Integer dataSelectId) {
        removeDataRole(dataAuthCode,dataSelectId,SysUserDataRole.DATA_ROLE_TYPE_ADMIN);
    }

    /**
     * 移除子账号数据权限
     * @param dataAuthCode
     * @param dataSelectId
     */
    public void removeChildDataRole(String dataAuthCode, Integer dataSelectId) {
        removeDataRole(dataAuthCode,dataSelectId,SysUserDataRole.DATA_ROLE_TYPE_CHILDREN);
    }

    /**
     * 移除数据权限
     * @param dataAuthCode
     * @param dataSelectId
     * @param type
     */
    private void removeDataRole(String dataAuthCode, Integer dataSelectId, Integer type) {
        SysUserDataRole sysUserDataRole;
        if (SysUserDataRole.DATA_ROLE_TYPE_ADMIN.equals(type)) {
            sysUserDataRole = getAdminDataRole().get(dataAuthCode);
            List<Object> selects = sysUserDataRole.getDataSelects();
            try {
                removeOldSelect(dataSelectId, selects);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                logger.error(e.getMessage());
            }
            sysUserDataRole.setDataSelects(selects);
            adminDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);
        } else {
            sysUserDataRole = getChildrenDataRole().get(dataAuthCode);
            List<Object> selects = sysUserDataRole.getDataSelects();
            try {
                removeOldSelect(dataSelectId, selects);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                logger.error(e.getMessage());
            }
            sysUserDataRole.setDataSelects(selects);
            childrenDataRoleMap.put(sysUserDataRole.getDataRoleCode(), sysUserDataRole);
        }
    }
}
