package cn.chen.bos.service;

import cn.chen.bos.domain.bc.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by hasee on 2017/7/17.
 */
public interface StandardService {

    void save(Standard model);

    Page<Standard> pageQuery(PageRequest pageRequest);

    void delBatch(String[] idArr);

    List<Standard> findAll();

    void recoverBatch(String[] idArr);
}
