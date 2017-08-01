package cn.chen.bos.dao;

import cn.chen.bos.domain.bc.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/18.
 */
public interface StaffDao extends JpaRepository<Staff, String>, JpaSpecificationExecutor<Staff> {

    Staff findByTelephone(String telephone);

    @Query("from Staff where deltag=1")
    List<Staff> ajaxListInUse();
    @Modifying
    @Query("update Staff set deltag=0 where id=?1")
    void del(String s);
    @Modifying
    @Query("update Staff set deltag=1 where id=?1")
    void restore(String s);
}
