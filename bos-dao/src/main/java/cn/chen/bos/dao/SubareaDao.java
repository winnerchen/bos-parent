package cn.chen.bos.dao;

import cn.chen.bos.domain.bc.DecidedZone;
import cn.chen.bos.domain.bc.Subarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/22.
 */
public interface SubareaDao extends JpaRepository<Subarea, String>, JpaSpecificationExecutor<Subarea> {
    @Query("from Subarea where decidedZone is null")
    List<Subarea> noassociation();
    @Modifying
    @Query("update Subarea set decidedZone=?2 where id=?1")
    void associationtoDecidedzone(String id , DecidedZone model);
}
