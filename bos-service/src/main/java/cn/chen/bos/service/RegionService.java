package cn.chen.bos.service;

import cn.chen.bos.domain.bc.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/7/21.
 */
public interface RegionService {
    void save(ArrayList<Region> regions);

    Page<Region> pageQuery(PageRequest pageRequest);

    Region validRegionCode(String id);

    Region validPostcode(String postcode);

    void save(Region model);

    List<Region> findAll(String params);
}
