package cn.chen.bos.service;

import cn.chen.bos.domain.auth.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by hasee on 2017/7/31.
 */
public interface RoleService {
    void save(Role model, String menuIds, String[] functionIds);

    Page<Role> pageQuery(PageRequest pageRequest);

    List<Role> ajaxList();

    List<Role> findAll();

    List<Role> findRolesByUserId(Integer id);
}
