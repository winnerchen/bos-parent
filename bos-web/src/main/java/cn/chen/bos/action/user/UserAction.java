package cn.chen.bos.action.user;

import cn.chen.bos.domain.user.User;
import cn.chen.bos.service.impl.FacadeService;
import cn.chen.bos.action.base.BaseAction;
import cn.itcast.send.message.RandStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class UserAction extends BaseAction<User> {
	@Autowired
	private FacadeService facadeService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;


	@Action(value = "userAction_save", results = {@Result(name = "save", location =
			"/WEB-INF/pages/base/userlist.jsp")})
	public String save() {
		String[] grantRoles = getRequest().getParameterValues("grantRoles");

		facadeService.getUserService().save(model,grantRoles);
		return "save";
	}
	@Action(value = "userAction_pageQuery")
	public String pageQuery() {
		Page<User> pageData = facadeService.getUserService().pageQuery(getPageRequest());
		setPageData(pageData);
		return "pageQuery";
	}


	// userAction_validCheckCode
	@Action(value = "userAction_validCheckCode", results = { @Result(name = "validCheckCode", type = "json") })
	public String validCheckCode() {
		String checkcode = getParameter("checkcode");
		String session_checkcode = (String) getSessionAttribute("key");
		if (checkcode.equalsIgnoreCase(session_checkcode)) {
			push(true);// 压入栈顶 json结果集源码 执行execute (没有配置root的情况下)将 栈顶元素进行json序列化
		} else {
			push(false);
		}
		return "validCheckCode";
	}

	// 登陆业务方法
	@Action(value = "userAction_login", results = {
			@Result(name = "login_error", location = "/login.jsp"),
			@Result(name = "login_ok", type = "redirect", location = "/index.jsp"),
			@Result(name = "input", location = "/login.jsp") })
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(model.getEmail(), model
				.getPassword());
		try {
			subject.login(token);
			return "login_ok";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			this.addActionError(this.getText("login.usernameOrPassword.error"));
			return "login_error";
		}
	}
	@Action(value = "userAction_logout", results = {
			@Result(name = "logout",type = "redirect",location = "/login.jsp")})
	public String userAction_logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "logout";
	}

	@Action(value = "userAction_sendSms", results = { @Result(name = "sendSms", type = "json") })
	public String sendSms() {
		System.out.println("进入sendSms方法");
		try { // 发送验证码操作
			final String code = RandStringUtils.getCode();
			redisTemplate.opsForValue().set(model.getTelephone(), code, 120,
					TimeUnit.SECONDS);// 120秒 有效找回 redis保存验证码
			System.out.println(code + "----------短信密码找回验证码----------");
			// SmsSystem.sendSms(code, model.getTelephone());// 没有ACTIVEMQ
			// 单线程发送给用户手机
			// 调用ActiveMQ jmsTemplate，发送一条消息给MQ
			jmsTemplate.send("bos_sms", new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {
					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("telephone", model.getTelephone());
					mapMessage.setString("msg", code);
					return mapMessage;
				}
			});
			push(true);
		} catch (Exception e) {
			push(false);
		}
		return "sendSms";
	}
	@Action(value = "userAction_smsPassword", results = { @Result(name = "smsPassword", type = "json") })
	public String smsPassword() {
		// 判断用户提交验证码是否有效,要求用户提交的验证码必须和Redis保存的验证码一致
		String code = getParameter("checkcode");// 客户端用户输入的验证码
		String rediscode = redisTemplate.opsForValue().get(model.getTelephone());//
		if (StringUtils.isNotBlank(rediscode)) {
			if (rediscode.equals(code)) {
				// 用户输入的验证码和Redis保存的验证码一致
				push(true);

				//jmsTemplate.del(model.getTelephone());// 验证码使用之后,需要将redis保存的验证码删除
			} else {
				push(false);
			}
		} else {
			push(false);
		}
		return "smsPassword";
	}
	@Action(value = "userAction_resetPassword", results = { @Result(name = "resetPassword", type = "json") })
	public String resetPassword() {
		User existUser = facadeService.getUserService().findUserByTelpehone(model.getTelephone());// 根据用户的手机号找到用户.
		System.out.println(model.getTelephone());// 用户号有效才可以重置密码!
		if (existUser == null) {
			push(false);
		} else {
			facadeService.getUserService().updatePasswordByTelephone(model.getTelephone(), model.getPassword());
			redisTemplate.delete(model.getTelephone());
			push(true);
		}
		return "resetPassword";
	}




}
