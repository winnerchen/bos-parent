package cn.chen.bos.service;

import cn.chen.bos.domain.auth.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by hasee on 2017/7/30.
 */
public interface FunctionService {

    void save(Function model);

    Page<Function> pageQuery(PageRequest pageRequest);

    List<Function> ajaxList();

    List<Function> findAll();
}
