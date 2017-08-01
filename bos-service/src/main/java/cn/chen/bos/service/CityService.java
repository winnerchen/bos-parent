package cn.chen.bos.service;

import cn.chen.bos.domain.city.City;

import java.util.List;

/**
 * Created by hasee on 2017/7/27.
 */
public interface CityService {
    List<City> findAll(Integer pid);
}
