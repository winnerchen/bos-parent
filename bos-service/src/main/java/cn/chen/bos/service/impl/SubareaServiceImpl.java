package cn.chen.bos.service.impl;

import cn.chen.bos.dao.SubareaDao;
import cn.chen.bos.domain.bc.Subarea;
import cn.chen.bos.service.SubareaService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hasee on 2017/7/22.
 */
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService{
    @Autowired
    private SubareaDao subareaDao;
    @Override
    public void save(Subarea model) {
        subareaDao.save(model);
    }

    @Override
    public Page<Subarea> pageQuery(PageRequest pageRequest) {
        Page<Subarea> all=subareaDao.findAll(pageRequest);
        List<Subarea> list = all.getContent();
        for (Subarea subarea : list) {
            Hibernate.initialize(subarea.getRegion());
        }
        return all;
    }

    @Override
    public Page<Subarea> pageQuery(PageRequest pageRequest, Specification<Subarea> specification) {
        Page<Subarea> all=subareaDao.findAll(specification,pageRequest);
        List<Subarea> list = all.getContent();
        for (Subarea subarea : list) {
            Hibernate.initialize(subarea.getRegion());
        }
        return all;
    }

    @Override
    public List<Subarea> findAllBySpecification(Specification<Subarea> specification) {
        List<Subarea> all = subareaDao.findAll(specification);
        for (Subarea subarea : all) {
            Hibernate.initialize(subarea.getRegion());
        }
        return all;
    }

    @Override
    public List<Subarea> noassociation() {
        return subareaDao.noassociation();
    }
}
