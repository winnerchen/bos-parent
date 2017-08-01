package cn.chen.bos.service;

import cn.chen.bos.domain.bc.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by hasee on 2017/7/18.
 */
public interface StaffService {
    Staff validTelephone(String telephone);

    void save(Staff model);

    Page<Staff> pageQuery(PageRequest pageRequest);

    Page<Staff> pageQuery(PageRequest pageRequest, Specification<Staff> specification);

    List<Staff> ajaxListInUse();

    void delBatch(String[] idArr);

    void restoreBatch(String[] idArr);
}
