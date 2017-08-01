package cn.chen.bos.service.base;

import java.util.ResourceBundle;

/**
 * Created by hasee on 2017/7/26.
 */
public interface BaseInterface {
    public static final String CRM_BASE_URL = ResourceBundle.getBundle("crm").getString
            ("crm_base_url");

}
