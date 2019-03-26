package com.wmeimob.tool.message;

/**
 * Created by xiangzhao on 2016/1/5.
 */

import java.util.Map;

/**
 * 提示类目
 */
public final class MessageConst extends EnumMapBuild {


    public static boolean showInfo = true;

    private final static Map<Integer, String> map = initEnumMap(new MessageConst());

    protected Enum[] callbackGetValues() {
        return Msg.values();
    }

    protected Integer callbackGetID(Enum enum_obj) {
        return ((Msg) enum_obj).id;
    }

    protected String callbackGetName(Enum enum_obj) {
        return ((Msg) enum_obj).info;
    }


    public enum Msg {
        VALID_FILED(-32767, "提交失败"),
        HANDLE_FAIL(-1, "fail"),//通用失败
        SUCCESS(0, "success"),//成功
        INVALID_USER(10000, "用户名或密码错误"),
        USERNAME_EXISTS(10100, "已被注册的用户"),
        UN_LOGIN(10200, "用户未登录"),
        WECHAT_UN_LOGIN(10300, "获取微信授权信息失败，请重新载入页面在再试"),
        LOGIN_NO_AUTH(10400, "用户没有权限查看管理页面"),
        SAVE_ROLEMENU_SUCCESS(20000, "修改权限菜单成功，如您修改的用户正在使用中，该用户的权限将在下次登录时生效"),
        NO_PASS_AUTH(20001,"权限不足"),
        UNIQUE_CONFLICTING(30000, "用户名与现有冲突"),

        ILLEGAL_PARAM(4001, "非法参数"),
        NOT_NULL_PARAM(51000, "缺少必要的信息，请核对"),
        TOKEN_ERROR(52000, "当前页面信息已过期，或您还有其他正在进行中的会话，请核对后重试"),

        CONFIG_DEVMODE_FAIL(90000, "配置微信开发模式失败"),
        UNKNOW_ERROR(-2, "啊哦，发生了不可描述的错误，过段时间再试试？"),

        //业务 60000起始
        PROBABILITY_ERROR(60000,"操作失败，对于一种奖品，同种参与方式只能存在一个，或您的中奖概率总和超过了100%" ),
        INVALID_DRAW_CODE(60100,"抽奖号码有误，请注意号码的大小写"),
        DRAW_CODE_USED(60101,"抽奖号码已被使用"),
        TOO_MANY_ERROR(60200,"连续失败多次，今天您已不能再参与此活动"),
        ULTIMATE_CODE_MANY_COUNT(60210,"商用码使用次数超限"),
        CONTACT_INFO_ERROR(60300,"信息填写有误，请仔细核对\n此外，领奖人应为年满18周岁的中国大陆公民"),
        BALANCE_TOO_LOW(60400, "红包提现不能低于1元"),
        IP_UNALLOWED(60500,"未经授权的IP地址，请检查系统代理设置。如仍有问题，请联系网络管理员。"),
        MERCHANT_NOT_FOUND(60600,"商户未找到"),
        MERCHANT_ALREADY_REGISTER(60610,"已注册的商户号"),
        AUTHORIZE_CODE_ERROR(60700,"授权信息已失效"),
        DECODE_FAIL(60800,"无法识别的密文信息"),
        AUTHORIZE_TIMEOUT(60900,"授权请求超时"),
        INVALID_LOTTERY_TOKEN(61000,"无法识别的请求码"),
        REQUEST_TIMEOUT(61100,"请求超时"),
        AUTHORIZE_APPID_ERROR(61200,"授权appid不匹配"),
        NO_AUTH_FOR_MPS(61300,"没有管理公众号的权限"),
        HANDLE_TOO_MUCH(61400,"您的操作太快了，休息一下吧" ),
        SERVER_TOO_BUSY(61500, "服务器繁忙，请稍后再试"),
        WECHAT_ERROR_403(403,"您无权进行此操作，如果有疑问请与管理员联系" );


        public final Integer id;
        public final String info;

        Msg(int i, String info) {
            this.id = i;
            this.info = info;
        }
    }

    public static String info(Integer id) {
        String info = map.get(id);
        if (info == null || info.trim().equals("")) {
            info = "未声明的响应标识";
        }
        return info;
    }

    public static String info(Msg msg) {

        return info(msg.id);
    }

}


