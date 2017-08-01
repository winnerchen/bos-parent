package cn.chen.bos.dao;

import cn.chen.bos.domain.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/31.
 */
public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {
    @Query("from Role r inner join fetch r.users u where u.id=?1")
    List<Role> findRolesByUserId(Integer id);
}
