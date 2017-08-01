package cn.chen.bos.action.bc;

import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.domain.bc.Staff;
import cn.chen.bos.service.impl.FacadeService;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/7/18.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class StaffAction extends BaseAction<Staff> {
    @Autowired
    private FacadeService facadeService;

    @Action(value = "staffAction_validTelephone", results = {@Result(name = "validTelephone",
            type = "json")})
    public String validTelephone() {
        Staff staff = facadeService.getStaffService().validTelephone(model.getTelephone());
        boolean flag = false;
        if (staff == null) {
            flag = true;
        }
        push(flag);
        return "validTelephone";
    }

    @Action(value = "staffAction_save", results = {@Result(name = "save", location =
            "/WEB-INF/pages/base/staff.jsp")})
    public String save() {
        facadeService.getStaffService().save(model);
        return "save";
    }

    @Action(value = "staffAction_ajaxListInUse", results = {@Result(name = "ajaxListInUse", type
            = "json")})
    public String ajaxListInUse() {
        List<Staff> staffs = facadeService.getStaffService().ajaxListInUse();
        push(staffs);
        return "ajaxListInUse";
    }

    @Action(value = "staffAction_delBatch", results = {@Result(name = "delBatch", type = "json")})
    public String delBatch() {
        try {
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                facadeService.getStaffService().delBatch(idArr);
                push(true);
            } else {
                push(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "delBatch";
    }

    @Action(value = "staffAction_restoreBatch", results = {@Result(name = "restoreBatch", type =
            "json")})
    public String restoreBatch() {
        try {
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                facadeService.getStaffService().restoreBatch(idArr);
                push(true);
            } else {
                push(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "restoreBatch";
    }

    @Action(value = "staffAction_pageQuery")
    public String pageQuery() {

        Specification<Staff> specification = new Specification<Staff>() {
            @Override
            public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                System.out.println("---------进入匿名内部类1111------");
                ArrayList<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(model.getName())) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + model.getName
                            () + "%");
                    predicates.add(p1);
                    //System.out.println("名字带条件");
                }
                if (StringUtils.isNotBlank(model.getTelephone())) {
                    Predicate p2 = cb.equal(root.get("telephone").as(String.class), model
                            .getTelephone());
                    predicates.add(p2);
                }
                if (StringUtils.isNotBlank(model.getStation())) {
                    Predicate p3 = cb.equal(root.get("station").as(String.class), model
                            .getStation());
                    predicates.add(p3);
                }
                Predicate[] ps = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(ps));
            }
        };
        Page<Staff> pageData = facadeService.getStaffService().pageQuery(getPageRequest(),
                specification);
        setPageData(pageData);
        return "pageQuery";
    }

}
