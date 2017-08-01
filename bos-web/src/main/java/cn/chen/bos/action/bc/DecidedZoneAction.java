package cn.chen.bos.action.bc;

import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.service.impl.FacadeService;
import cn.chen.crm.domain.Customer;
import cn.chen.bos.domain.bc.DecidedZone;
import cn.chen.bos.domain.bc.Staff;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hasee on 2017/7/25.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class DecidedZoneAction extends BaseAction<DecidedZone> {
    @Autowired
    private FacadeService facadeService;
    @Action(value = "decidedZoneAction_validId",results = { @Result(name = "validId", type="json") } )
    public String validId() {
        DecidedZone decidedZone = facadeService.getDecidedZoneService().validId(model.getId());
        boolean flag=false;
        if (decidedZone == null) {
            flag = true;
        }
        push(flag);
        return "validId";
    }
    @Action(value = "decidedZoneAction_save",results = { @Result(name = "save", location = "/WEB-INF/pages/base/decidedzone.jsp")} )
    public String save() {
        // 接受sid数组
        try {
            String[] sid = getRequest().getParameterValues("sid");
            facadeService.getDecidedZoneService().save(sid, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save";
    }
    @Action(value = "decidedZoneAction_pageQuery")
    public String pageQuery() {

        /*Page<DecidedZone> pageData = facadeService.getDecidedZoneService().pageQuery(getPageRequest());*/

        Specification<DecidedZone> specification = getSpecification();
        Page<DecidedZone> pageData=facadeService.getDecidedZoneService().pageQuery(getPageRequest(), specification);
        setPageData(pageData);
        return "pageQuery";
    }
    /*@Action(value = "decidedZoneAction_edit")
    public String edit() {

        *//*
        * TODO 2017/7/26
        *
        * *//*



        return "edit";
    }*/
    private Specification<DecidedZone> getSpecification () {
        // model 瞬时态 分页 条件查询 ...
        // molde 数据 封装 Specification 实现类中
        // Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb); 方法 将请求参数 封装 Specification
        Specification<DecidedZone> spec = new Specification<DecidedZone>() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 将请求参数 model 数据 封装 Predicate
                // 1: root 表示 Subarea from Subarea join .... where .... 省市区条件 Region 关键字 SubArea 定区 DecidedZone oid
                // 2:cb 连接条件构建器 类似以前hibernate Restrictions.like/eq/gt
                List<Predicate> list = new ArrayList<Predicate>(); // 存放所有条件对象Predicate
                if (StringUtils.isNotBlank(model.getId())) {
                    // 连自己表
                    Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
                    list.add(p1);
                }
                // 3: 连接 省市区 多表Region 查询
                if (model.getStaff() != null) {
                    // subarea 连接 region 表
                    Join staffJoin = root.join(root.getModel().getSingularAttribute("staff", Staff.class), JoinType.LEFT);
                    if (StringUtils.isNotBlank(model.getStaff().getStation())) {
                        Predicate p2 = cb.like(staffJoin.get("station").as(String.class), "%" + model.getStaff().getStation() + "%");
                        list.add(p2);
                    }

                }
                // 4: 定区是否管理分区
                // 4: 获取是否存在分区下拉框的值
                String association = getParameter("isAssociationSubarea");
                if (StringUtils.isNotBlank(association)) {
                    // "" 0 未关联分区的定区 1 关联定区
                    if ("1".equals(association)) {
                        Predicate p3 = cb.isNotEmpty(root.get("subareas").as(Set.class));
                        list.add(p3);
                    } else if ("0".equals(association)) {
                        Predicate p3 = cb.isEmpty(root.get("subareas").as(Set.class));
                        list.add(p3);
                    }
                }
                // List<Predicate> list = new ArrayList<Predicate>(); 集合 长度大小 由用户 表单请求参数 决定
                Predicate[] p = new Predicate[list.size()];// 定义数组泛型
                // list.toArray 返回的 Object 数组
                return cb.and(list.toArray(p));// Predicate数组 内部所有条件 and 关系
            }
        };
        return spec;
    }
    @Action(value = "decidedZoneAction_findnoassociationcustomers", results = { @Result(name = "findnoassociationcustomers", type = "fastJson", params = { "includeProperties", "id,name" }) })
    public String findnoassociationcustomers() {
        List<Customer> customers = facadeService.getDecidedZoneService().findnoassociationcustomers();
        push(customers);
        return "findnoassociationcustomers";
    }
    @Action(value = "decidedZoneAction_findassociationcustomers", results = { @Result(name = "findassociationcustomers", type = "fastJson", params = { "includeProperties", "id,name" }) })
    public String findassociationcustomers() {
        List<Customer> customers = facadeService.getDecidedZoneService().findassociationcustomers(model.getId());
        push(customers);
        return "findassociationcustomers";
    }

    @Action(value = "decidedZoneAction_assigncustomerstodecidedzone",
            results = { @Result(name = "assigncustomerstodecidedzone", location = "/WEB-INF/pages/base/decidedzone.jsp") })
    public String assigncustomerstodecidedzone() {
        // 获取定区id 和 选中右边所有客户ids
        String[] customerIds = getRequest().getParameterValues("customerIds");
        facadeService.getDecidedZoneService().assigncustomerstodecidedzone(model.getId(), customerIds);
        return "assigncustomerstodecidedzone";
    }





}
