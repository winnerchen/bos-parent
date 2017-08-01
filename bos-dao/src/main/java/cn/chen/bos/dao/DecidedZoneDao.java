package cn.chen.bos.dao;

import cn.chen.bos.domain.bc.DecidedZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by hasee on 2017/7/25.
 */
public interface DecidedZoneDao extends JpaRepository<DecidedZone,String>,JpaSpecificationExecutor<DecidedZone>{
}
