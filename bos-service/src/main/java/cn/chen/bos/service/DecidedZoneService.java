package cn.chen.bos.service;

import cn.chen.bos.domain.bc.DecidedZone;
import cn.chen.crm.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by hasee on 2017/7/25.
 */
public interface DecidedZoneService {
    DecidedZone validId(String id);

    void save(String[] sid, DecidedZone model);

    Page<DecidedZone> pageQuery(PageRequest pageRequest, Specification<DecidedZone> specification);

    Page<DecidedZone> pageQuery(PageRequest pageRequest);

    List<Customer> findnoassociationcustomers();

    List<Customer> findassociationcustomers(String id);

    void assigncustomerstodecidedzone(String id, String[] customerIds);
}
