package cn.chen.bos.action.noticebill;

import cn.chen.bos.domain.user.User;
import cn.chen.crm.domain.Customer;
import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.domain.courier.NoticeBill;
import cn.chen.bos.service.impl.FacadeService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by hasee on 2017/7/27.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class NoticeBillAction extends BaseAction<NoticeBill> {

    @Autowired
    private FacadeService facadeService;

    @Action(value = "noticeBillAction_findCustomerByTelephone", results = {@Result(name =
            "findCustomerByTelephone", type = "fastJson")})
    public String findCustomerByTelephone() {
        // 查询数据库 List<Standard> 序列化
        Customer c = facadeService.getNoticeBillService().findCustomerByTelephone(model
                .getTelephone());
        push(c);// name
        return "findCustomerByTelephone";
    }
    @Action(value = "noticeBillAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/qupai/noticebill_add.jsp") })
    public String save() {
        User user = (User) getSessionAttribute("existUser");// 受理人
        // 客户id 业务层封装
        model.setUser(user);
        String province = getParameter("nprovince");
        String city = getParameter("ncity");
        String district = getParameter("ndistrict");
        //model.setPickaddress(province + city + district + model.getPickaddress());// 业务通知单表里面插入
        // 2: 调用业务层完成业务通知单录入 以及自动分单的实现
        facadeService.getNoticeBillService().save(model, province, city, district);
        // 3: 业务层 3.1 业务通知单录入 3.2 自动分单 (地址库完全匹配/管理分区匹配法) 3.3 客户新客户 crm(插入)录入 3.4 老客户 address更新
        return "save";
    }


}
