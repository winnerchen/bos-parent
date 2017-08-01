package cn.chen.bos.service.impl;

import cn.chen.bos.dao.RoleDao;
import cn.chen.bos.domain.auth.Menu;
import cn.chen.bos.domain.auth.Role;
import cn.chen.bos.domain.auth.Function;
import cn.chen.bos.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by hasee on 2017/7/31.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public void save(Role model, String menuIds, String[] functionIds) {
        roleDao.saveAndFlush(model);
        if (functionIds != null && functionIds.length != 0) {
            Set<Function> functions = model.getFunctions();
            for (String fid : functionIds) {
                Function f = new Function();
                f.setId(fid);
                functions.add(f);
            }
        }

        if (StringUtils.isNotBlank(menuIds)) {
            Set<Menu> menus = model.getMenus();
            for (String mid : menuIds.split(",")) {
                Menu m = new Menu();
                m.setId(mid);
                menus.add(m);
            }
        }
    }

    @Override
    public Page<Role> pageQuery(PageRequest pageRequest) {
        return roleDao.findAll(pageRequest);
    }

    @Override
    public List<Role> ajaxList() {
        return roleDao.findAll();
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<Role> findRolesByUserId(Integer id) {
        return roleDao.findRolesByUserId(id);
    }
}
