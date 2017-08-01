package cn.chen.bos.action.city;

import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.domain.city.City;
import cn.chen.bos.service.impl.FacadeService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by hasee on 2017/7/27.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class LoadCityAction extends BaseAction<City> {
    @Autowired
    private FacadeService facadeService;

    @Action(value = "loadCityAction_load", results = {@Result(name = "load", type = "json")})
    public String load() {
        // 1: 获取请求的Pid
        List<City> citys = facadeService.getCityService().findAll(model.getPid());
        System.out.println("----------" + citys);
        push(citys);
        return "load";
    }
}

