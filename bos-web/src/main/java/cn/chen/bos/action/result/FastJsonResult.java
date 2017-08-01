package cn.chen.bos.action.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;

public class FastJsonResult implements Result {
    private String root;// action业务代码 是否获取栈顶数据还是搜索值栈的一个标识

    protected Set<String> excludeProperties = Collections.emptySet();// 不需要序列化属性名添加
    // 的集合
    protected Set<String> includeProperties = Collections.emptySet();// 需要序列化json字符串的集合添加

    public Set<String> getExcludeProperties() {
        return excludeProperties;
    }

    public void setExcludeProperties(String excludeProperties) {
        // 该TextParseUtil 由struts2 框架提供 用于切割 逗号分隔的字符串 该方法来源 struts2 自带的拦截器源码
        // MethodFilterIntercepter
        this.excludeProperties = TextParseUtil.commaDelimitedStringToSet(excludeProperties);
    }

    public Set<String> getIncludeProperties() {
        return includeProperties;
    }

    public void setIncludeProperties(String includeProperties) {
        this.includeProperties = TextParseUtil.commaDelimitedStringToSet(includeProperties);
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        // 结果集 执行的方法
        // 从值栈中获取目标对象 进行fastjson 自定义属性序列化
        response.setContentType("text/json;charset=utf-8");
        Object rootObject = findRootObject(invocation);//
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        // 阿里过滤器 配置哪些属性需要梳序列化 哪些属性 不需要序列化
        // 序列化json的属性对象
        if (includeProperties != null && includeProperties.size() != 0) {
            for (String in : includeProperties) {
                filter.getIncludes().add(in);// 将需要的属性添加即可
            }
        }
        if (excludeProperties != null && excludeProperties.size() != 0) {
            for (String ex : excludeProperties) {
                filter.getExcludes().add(ex);// 不需要序列化json字符串的集合添加
            }
        }
        //String jsonString = JSON.toJSONString(rootObject, filter);// 阿里提供的序列化json字符串
        String jsonString = JSON.toJSONString(rootObject, filter, SerializerFeature.DisableCircularReferenceDetect);


        System.out.println("fastjson 序列化json字符串=" + jsonString);
        response.getWriter().println(jsonString);

    }

    // 搜索值栈获取 序列化java对象
    protected Object findRootObject(ActionInvocation invocation) {
        Object rootObject;
        if (this.root != null) {
            ValueStack stack = invocation.getStack();
            rootObject = stack.findValue(root);
        } else {
            rootObject = invocation.getStack().peek(); // model overrides action
        }
        return rootObject;
    }

}
