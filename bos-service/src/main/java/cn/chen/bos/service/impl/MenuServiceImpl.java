package cn.chen.bos.service.impl;

import cn.chen.bos.dao.MenuDao;
import cn.chen.bos.domain.auth.Menu;
import cn.chen.bos.service.MenuService;
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
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public void save(Menu model) {
        menuDao.save(model);
    }

    @Override
    public Page<Menu> pageQuery(PageRequest pageRequest) {
        return menuDao.findAll(pageRequest);
    }

    @Override
    public List<Menu> ajaxListHasSonMenus() {
        return menuDao.ajaxListHasSonMenus();
    }

    @Override
    public List<Menu> ajaxList() {
        return menuDao.ajaxList();
    }

    @Override
    public List<Menu> findMenuByUserId(Integer id) {
        return menuDao.findMenuByUserId(id);
    }
}
