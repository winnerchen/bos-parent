package cn.chen.bos.action.auth;

import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.service.impl.FacadeService;
import cn.chen.bos.domain.auth.Function;
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


public class FunctionAction extends BaseAction<Function> {
    @Autowired
    private FacadeService facadeService;


    @Action(value = "functionAction_save", results = {@Result(name = "save", location =
            "/WEB-INF/pages/admin/function.jsp")})
    public String save() {
        facadeService.getFunctionService().save(model);
        return "save";
    }

    @Action(value = "functionAction_pageQuery")
    public String pageQuery() {
        Page<Function> pageData = facadeService.getFunctionService().pageQuery(getPageRequest());
        setPageData(pageData);// 父类
        return "pageQuery";
    }

    @Action(value = "functionAction_ajaxList", results = {@Result(name = "ajaxList", type =
            "fastJson")})
    public String ajaxList() {
        List<Function> list = facadeService.getFunctionService().ajaxList();
        push(list);
        return "ajaxList";
    }


}
