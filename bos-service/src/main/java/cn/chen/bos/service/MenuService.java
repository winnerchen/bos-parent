package cn.chen.bos.service;

import cn.chen.bos.domain.auth.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by hasee on 2017/7/30.
 */
public interface MenuService {
    void save(Menu model);

    Page<Menu> pageQuery(PageRequest pageRequest);

    List<Menu> ajaxListHasSonMenus();

    List<Menu> ajaxList();

    List<Menu> findMenuByUserId(Integer id);
}
