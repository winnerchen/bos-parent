package cn.chen.bos.dao;

import cn.chen.bos.domain.bc.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/17.
 */
public interface StandardDao extends JpaRepository<Standard, Integer> {
    @Modifying
    @Query("update Standard set deltag = 0 where id = ?1")
    public void delBatch(int i);
    @Query("from Standard where deltag=1")
    public List<Standard> findAllInUse();

    @Modifying
    @Query("update Standard set deltag = 1 where id = ?1")
    void recoverBatch(int i);
}
