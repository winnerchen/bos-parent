package cn.chen.bos.realm;

import cn.chen.bos.domain.auth.Role;
import cn.chen.bos.domain.user.User;
import cn.chen.bos.service.impl.FacadeService;
import cn.chen.bos.domain.auth.Function;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Created by hasee on 2017/7/30.
 */
public class BosRealm extends AuthorizingRealm {
    @Autowired
    private FacadeService facadeService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User existUser = (User) subject.getPrincipal();
        if ("xxx@163.com".equalsIgnoreCase(existUser.getEmail())) {
            List<Role> roles = facadeService.getRoleService().findAll();
            for (Role role : roles) {
                info.addRole(role.getCode());
            }
            List<Function> functions = facadeService.getFunctionService().findAll();
            for (Function function : functions) {
                info.addStringPermission(function.getCode());
            }
        } else {
            List<Role> roles = facadeService.getRoleService().findRolesByUserId(existUser.getId());
            for (Role role : roles) {
                info.addRole(role.getCode());
                Set<Function> functions = role.getFunctions();
                for (Function function : functions) {
                    info.addStringPermission(function.getCode());
                }
            }
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----开始认证-----");
        UsernamePasswordToken myToken = (UsernamePasswordToken) authenticationToken;
        User existUser = facadeService.getUserService().findUserByEmail(myToken.getUsername());
        if (existUser == null) {
            return null;
        } else {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(existUser, existUser
                    .getPassword(), super.getName());
            return info;
        }
    }
}
