package com.wmeimob.tool.sms;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PropertyResourceBundle;

/**
 * Created by Shinez on 2017/3/8.
 */
public class AlidayuSms {

    private final static Logger logger = LoggerFactory.getLogger(AlidayuSms.class);
    private static PropertyResourceBundle property = (PropertyResourceBundle) PropertyResourceBundle.getBundle("common");
    private static final TaobaoClient client = new DefaultTaobaoClient(property.getString("ALIDAYU_REQ_URL"), property.getString("ALIDAYU_APP_KEY"), property.getString("ALIDAYU_APP_SECRET"));
    private static final AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
    public static String sendValidCode(String tel,String code){

        req.setExtend( "" );
        req.setSmsType( "normal" );
        req.setSmsFreeSignName( "" );
        req.setSmsParamString(  "{code:'"+code+"'}"  );
        req.setRecNum( tel );
        req.setSmsTemplateCode(property.getString("ALIDAYU_TEMPLATE_CODE"));
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            logger.error(e.getMessage(),e);
        }
        System.out.println(rsp.getBody());
        return rsp.getBody();
    }
}
