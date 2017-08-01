package cn.chen.bos.service;

import cn.chen.crm.domain.Customer;
import cn.chen.bos.domain.courier.NoticeBill;

/**
 * Created by hasee on 2017/7/27.
 */
public interface NoticeBillService {
    Customer findCustomerByTelephone(String telephone);

    void save(NoticeBill model, String province, String city, String district);
}
