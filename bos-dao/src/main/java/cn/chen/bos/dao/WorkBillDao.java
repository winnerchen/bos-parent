package cn.chen.bos.dao;

import cn.chen.bos.domain.courier.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by hasee on 2017/7/27.
 */
public interface WorkBillDao extends JpaRepository<WorkBill,String>,JpaSpecificationExecutor<WorkBill> {
}
