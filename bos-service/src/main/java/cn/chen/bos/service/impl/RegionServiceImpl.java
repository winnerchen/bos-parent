package cn.chen.bos.service.impl;

import cn.chen.bos.dao.RegionDao;
import cn.chen.bos.domain.bc.Region;
import cn.chen.bos.service.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/7/21.
 */
@Transactional
@Service
public class RegionServiceImpl implements RegionService{
    @Autowired
    private RegionDao regionDao;

    @Override
    public void save(ArrayList<Region> regions) {
        regionDao.save(regions);
    }

    @Override
    public Page<Region> pageQuery(PageRequest pageRequest) {
        return regionDao.findAll(pageRequest);
    }

    @Override
    public Region validRegionCode(String id) {
        return regionDao.findOne(id);
    }

    @Override
    public Region validPostcode(String postcode) {
        return regionDao.findByPostcode(postcode);
    }

    @Override
    public void save(Region model) {
        regionDao.save(model);
    }

    @Override
    public List<Region> findAll(String params) {
        if (StringUtils.isNotBlank(params)) {
            return regionDao.findByCriterion("%"+params+"%");
        } else {
            return regionDao.findAll();
        }
    }
}
