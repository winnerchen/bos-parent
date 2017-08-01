package cn.chen.bos.dao;

import cn.chen.bos.domain.bc.DecidedZone;
import cn.chen.bos.domain.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/27.
 */
public interface CityDao extends JpaRepository<DecidedZone,String>,JpaSpecificationExecutor<City> {
    @Query("from City where pid=?1")
    List<City> findAllByPid(Integer pid);
}
