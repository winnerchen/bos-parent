package cn.chen.bos.service.impl;

import cn.chen.bos.dao.DecidedZoneDao;
import cn.chen.bos.dao.NoticeBillDao;
import cn.chen.bos.dao.RegionDao;
import cn.chen.bos.dao.WorkBillDao;
import cn.chen.bos.domain.bc.Region;
import cn.chen.bos.domain.courier.NoticeBill;
import cn.chen.bos.service.NoticeBillService;
import cn.chen.bos.service.base.BaseInterface;
import cn.chen.crm.domain.Customer;
import cn.chen.bos.domain.bc.DecidedZone;
import cn.chen.bos.domain.bc.Staff;
import cn.chen.bos.domain.bc.Subarea;
import cn.chen.bos.domain.courier.WorkBill;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.Set;

/**
 * Created by hasee on 2017/7/27.
 */
@Service
@Transactional
public class NoticeBillServiceImpl implements NoticeBillService {
    @Autowired
    private DecidedZoneDao decidedZoneDao;
    @Autowired
    private WorkBillDao workBillDao;
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private NoticeBillDao noticeBillDao;


    @Override
    public Customer findCustomerByTelephone(String telephone) {
        String url = BaseInterface.CRM_BASE_URL + "/findcustomerbytelephone/" + telephone;
        Customer c = WebClient.create(url).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        return c;

    }

    @Override
    public void save(final NoticeBill model, String province, String city, String district) {

        // 业务通知单录入
        boolean flag = false;// 作用: 控制crm系统老客户地址是否更新
        // 业务通知单录入
        noticeBillDao.saveAndFlush(model);// saveAndFlush model瞬时 --->持久态 model OID
        //System.out.println(model.getId() + "=================" + String.valueOf(model.getCustomerId()));

        final String pickaddress = model.getPickaddress();
        model.setPickaddress(province + city + district + model.getPickaddress());
        String url = BaseInterface.CRM_BASE_URL + "/findcustomerbyaddress/" + model.getPickaddress();

        Customer customer = WebClient.create(url).accept(MediaType.APPLICATION_JSON_TYPE).get
                (Customer.class);
        if (customer != null) {
            String decidedzoneId = customer.getDecidedzoneId();
            if (StringUtils.isNotBlank(decidedzoneId)) {
                DecidedZone decidedZone = decidedZoneDao.findOne(decidedzoneId);
                final Staff staff = decidedZone.getStaff();
                model.setStaff(staff);
                model.setOrdertype("自动");
                generateWorkBill(model, staff);
                sendToMq(model, pickaddress, staff);
                flag = true;
                System.out.println("地址库完全匹配");
                crmCustomer(model, flag);// 不需要更新客户地址和定区id
                return;
            }
        }
        Region region = regionDao.findRegionByProvinceAndCityAndDistrict(province, city, district);
        System.out.println("管理分区匹配法 省市区信息" + region.getProvince() + region.getCity() + region.getDistrict());

        Set<Subarea> subareas = region.getSubareas();
        if (subareas != null && subareas.size() != 0) {
            for (Subarea subarea : subareas) {
                if (model.getPickaddress().contains(subarea.getAddresskey())) {
                    DecidedZone zone = subarea.getDecidedZone();
                    if (zone != null) {
                        final Staff staff = zone.getStaff();
                        model.setStaff(staff);
                        model.setOrdertype("自动");
                        generateWorkBill(model,staff);
                        //sendToMq(model, pickaddress, staff);
                        System.out.println("管理分区匹配");
                        flag = false;
                        crmCustomer(model, flag);
                        return;
                    }
                }
            }
        }
        crmCustomer(model, flag);
        model.setOrdertype("人工");


    }

    private void sendToMq(final NoticeBill model, final String pickaddress, final Staff staff) {
        jmsTemplate.send("bos_staff", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("customername", model.getCustomerName());
                mapMessage.setString("customertel", model.getTelephone());
                mapMessage.setString("customeraddr", pickaddress);
                mapMessage.setString("stafftelephone", staff.getTelephone());
                return mapMessage;
            }
        });
    }

    private void crmCustomer(final NoticeBill model, final boolean flag) {
        System.out.println(model.getId() + "=================" + String.valueOf(model.getCustomerId()));

        // 1 : 客户是否是一个新客户 crm录入 返回 customerId
        if (!("null".equalsIgnoreCase(String.valueOf(model.getCustomerId())))) {
            if (!flag) {
                // flag = false 需要修改crm地址 客户关联的定区id要置null
                // 老客户 更新crm地址
                System.out.println("-----------crm----------老客户地址修改了 !");
                String urlupdate = BaseInterface.CRM_BASE_URL + "/updateadressbyid/" + model.getCustomerId() + "/" + model.getPickaddress();
                WebClient.create(urlupdate).put(null);
            }

        } else {
            // 新客户 crm系统录入客户信息 返回Cusotmer id String.valueOf(model.getCustomerId()) 新客户id是null字符串
            String urlsave = BaseInterface.CRM_BASE_URL + "/save";
            Customer customer = new Customer();
            customer.setName(model.getCustomerName());
            customer.setAddress(model.getPickaddress());
            customer.setDecidedzoneId(null);
            customer.setStation("传智播客");
            customer.setTelephone(model.getTelephone());
            Response post = WebClient.create(urlsave).accept(MediaType.APPLICATION_JSON).post(customer);
            // 响应体 获取实体模型
            Customer entity = post.readEntity(Customer.class);// 主键从crm系统获取
            model.setCustomerId(entity.getId());// noticebill --->customerId
        }
    }

    private void generateWorkBill(final NoticeBill model, final Staff staff) {
        WorkBill bill = new WorkBill();
        bill.setAttachbilltimes(0);
        bill.setBuildtime(new Date(System.currentTimeMillis()));
        bill.setNoticeBill(model);
        bill.setType("新");
        bill.setStaff(staff);
        bill.setRemark(model.getRemark());
        bill.setPickstate("新单");
        workBillDao.save(bill);
    }

}
