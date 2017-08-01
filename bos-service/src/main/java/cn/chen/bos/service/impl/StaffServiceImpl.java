package cn.chen.bos.service.impl;

import cn.chen.bos.dao.StaffDao;
import cn.chen.bos.domain.bc.Staff;
import cn.chen.bos.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hasee on 2017/7/18.
 */
@Transactional
@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    @Override
    public Staff validTelephone(String telephone) {
        return staffDao.findByTelephone(telephone);
    }

    @Override
    public void save(Staff model) {
        staffDao.save(model);
    }

    @Override
    public Page<Staff> pageQuery(PageRequest pageRequest) {
        return staffDao.findAll(pageRequest);
    }

    @Override
    public Page<Staff> pageQuery(PageRequest pageRequest, Specification<Staff> specification) {
        return staffDao.findAll(specification, pageRequest);
    }

    @Override
    public List<Staff> ajaxListInUse() {
        return staffDao.ajaxListInUse();
    }

    @Override
    public void delBatch(String[] idArr) {
        for (String s : idArr) {
            staffDao.del(s);
        }
    }

    @Override
    public void restoreBatch(String[] idArr) {
        for (String s : idArr) {
            staffDao.restore(s);
        }
    }
}
