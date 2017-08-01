package cn.chen.bos.service.impl;

import cn.chen.bos.dao.FunctionDao;
import cn.chen.bos.domain.auth.Function;
import cn.chen.bos.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hasee on 2017/7/30.
 */
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionDao functionDao;
    @Override
    public void save(Function model) {
        functionDao.save(model);
    }

    @Override
    public Page<Function> pageQuery(PageRequest pageRequest) {
        return functionDao.findAll(pageRequest);
    }

    @Override
    public List<Function> ajaxList() {
        return functionDao.findAll();
    }

    @Override
    public List<Function> findAll() {
        return functionDao.findAll();
    }
}
