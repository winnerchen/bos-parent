package cn.chen.bos.dao;

import cn.chen.bos.domain.auth.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by hasee on 2017/7/30.
 */
public interface FunctionDao extends JpaRepository<Function,String>,JpaSpecificationExecutor<Function> {
}

