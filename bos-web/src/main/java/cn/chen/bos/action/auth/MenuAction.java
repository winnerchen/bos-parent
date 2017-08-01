package cn.chen.bos.action.auth;

import cn.chen.bos.domain.user.User;
import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.domain.auth.Menu;
import cn.chen.bos.service.impl.FacadeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
public class MenuAction extends BaseAction<Menu> {
    @Autowired
    private FacadeService facadeService;

    @Action(value = "menuAction_save", results = {@Result(name = "save", location =
            "/WEB-INF/pages/admin/menu.jsp")})
    public String save() {
        if (model.getMenu() == null || StringUtils.isBlank(model.getMenu().getId())) {
            model.setMenu(null);
        }
        facadeService.getMenuService().save(model);
        return "save";
    }

    @Action(value = "menuAction_pageQuery")
    public String pageQuery() {
        super.setPage(Integer.parseInt(getParameter("page")));
        Page<Menu> pageData = facadeService.getMenuService().pageQuery(getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }

    @Action(value = "menuAction_ajaxListHasSonMenus", results = {@Result(name =
            "ajaxListHasSonMenus" , type = "fastJson")})
    public String ajaxListHasSonMenus() {
        List<Menu> menus = facadeService.getMenuService().ajaxListHasSonMenus();
        push(menus);
        return "ajaxListHasSonMenus";

    }
    @Action(value = "menuAction_ajaxList", results = {@Result(name =
            "ajaxList" , type = "fastJson", params = {"includeProperties","name,id,pId"})})
    public String ajaxList() {
        List<Menu> menus = facadeService.getMenuService().ajaxList();
        push(menus);
        return "ajaxList";

    }
    @Action(value = "menuAction_findMenuByUserId", results = {@Result(name =
            "findMenuByUserId" , type = "fastJson")})
    public String findMenuByUserId() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menus = facadeService.getMenuService().findMenuByUserId(user.getId());
        push(menus);

        return "findMenuByUserId";

    }

}
