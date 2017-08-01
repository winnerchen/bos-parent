package cn.chen.bos.dao;

import cn.chen.bos.domain.bc.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/21.
 */
public interface RegionDao extends JpaRepository<Region,String>, JpaSpecificationExecutor<Region>{

    Region findByPostcode(String postcode);
    @Query("from Region where province like ?1 or city like ?1 or district like ?1")
    List<Region> findByCriterion(String params);

    @Query("from Region where province = ?1 and city = ?2 and district =?3")
    Region findRegionByProvinceAndCityAndDistrict(String province, String city, String district);
}
