package cn.chen.bos.service.impl;

import cn.chen.bos.dao.DecidedZoneDao;
import cn.chen.bos.dao.SubareaDao;
import cn.chen.bos.service.DecidedZoneService;
import cn.chen.bos.service.base.BaseInterface;
import cn.chen.crm.domain.Customer;
import cn.chen.bos.domain.bc.DecidedZone;
import org.apache.cxf.jaxrs.client.WebClient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hasee on 2017/7/25.
 */
@Service
@Transactional
@SuppressWarnings("all")
public class DecidedZoneServiceImpl implements DecidedZoneService {
    @Autowired
    private DecidedZoneDao decidedZoneDao;
    @Autowired
    private SubareaDao subareaDao;
    @Override
    public DecidedZone validId(String id) {
        return decidedZoneDao.findOne(id);
    }

    @Override
    public void save(String[] sid, DecidedZone model) {
        decidedZoneDao.save(model);
        if (sid != null && sid.length != 0) {
            for (String id : sid) {
                subareaDao.associationtoDecidedzone(id, model);
            }
        }
    }

    @Override
    public Page<DecidedZone> pageQuery(PageRequest pageRequest, Specification<DecidedZone> specification) {
        Page<DecidedZone> all=decidedZoneDao.findAll(specification,pageRequest);
        List<DecidedZone> list = all.getContent();
        for (DecidedZone decidedZone : list) {
            Hibernate.initialize(decidedZone.getStaff());
        }
        return all;
        //return decidedZoneDao.findAll(specification, pageRequest);
    }

    @Override
    public Page<DecidedZone> pageQuery(PageRequest pageRequest) {
        return decidedZoneDao.findAll(pageRequest);
    }

    @Override
    public List<Customer> findnoassociationcustomers() {
        String url= BaseInterface.CRM_BASE_URL;
        url= url+"/noassociation";
        List<Customer> customers=(List<Customer>)WebClient.create(url).accept(MediaType.APPLICATION_JSON_TYPE).getCollection(Customer
                .class);
        return customers;
    }

    @Override
    public List<Customer> findassociationcustomers(String id) {
        String url= BaseInterface.CRM_BASE_URL;
        System.out.println("url = "+url);
        url = url + "/findcustomerbydecidedzoneid/" + id;
        List<Customer> customers = (List<Customer>) WebClient.create(url).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        return customers;
    }

    @Override
    public void assigncustomerstodecidedzone(String id, String[] customerIds) {
        String url = BaseInterface.CRM_BASE_URL + "/assigencusotmertodecidedzone/" + id;
        if (customerIds != null && customerIds.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (String cid : customerIds) {
                sb.append(cid).append(",");
            }
            String s = sb.substring(0, sb.length() - 1);// 1,2,3
            url = url + "/" + s;
        } else {
            url = url + "/tps";// 如果以tps出现 表示当前定区没有选择客户信息
        }

        WebClient.create(url).put(null);

    }


}
