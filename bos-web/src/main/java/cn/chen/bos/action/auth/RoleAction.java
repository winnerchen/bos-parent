package cn.chen.bos.action.auth;

import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.domain.auth.Role;
import cn.chen.bos.service.impl.FacadeService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by hasee on 2017/7/30.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class RoleAction extends BaseAction<Role> {
    @Autowired
    private FacadeService facadeService;

    @Action(value = "roleAction_save", results = {@Result(name = "save", location =
            "/WEB-INF/pages/admin/role.jsp")})
    public String save() {
        String menuIds = getRequest().getParameter("menuIds");
        String[] functionIds = getRequest().getParameterValues("functionIds");

        facadeService.getRoleService().save(model, menuIds, functionIds);
        return "save";
    }


    @Action(value = "roleAction_pageQuery")
    public String pageQuery() {
        super.setPage(Integer.parseInt(getParameter("page")));
        Page<Role> pageData = facadeService.getRoleService().pageQuery(getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }
/*
    @Action(value = "menuAction_ajaxListHasSonMenus", results = {@Result(name =
            "ajaxListHasSonMenus" , type = "fastJson")})
    public String ajaxListHasSonMenus() {
        List<Menu> menus = facadeService.getMenuService().ajaxListHasSonMenus();
        push(menus);
        return "ajaxListHasSonMenus";

    }
    */
    @Action(value = "roleAction_ajaxList", results = {@Result(name =
            "ajaxList" , type = "fastJson")})
    public String ajaxList() {
        List<Role> roles = facadeService.getRoleService().ajaxList();
        push(roles);
        return "ajaxList";

    }

}
