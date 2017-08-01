package cn.chen.bos.service.impl;

import cn.chen.bos.dao.CityDao;
import cn.chen.bos.domain.city.City;
import cn.chen.bos.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hasee on 2017/7/27.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao cityDao;
    @Override
    public List<City> findAll(Integer pid) {
        return cityDao.findAllByPid(pid);
    }
}
