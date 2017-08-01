package cn.chen.bos.service;

import java.util.List;

import cn.chen.bos.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
	public void save(User user);

	public void delete(User user);

	public User findUserById(Integer id);

	public List<User> findAll();

	// 业务 登录
	/*public User findUserByUsernameAndPassword(String username, String password);*/
	public User login(String email, String password);


	User findUserByTelpehone(String telephone);

	void updatePasswordByTelephone(String telephone, String password);

	User findUserByEmail(String username);

	void save(User model, String[] grantRoles);

	Page<User> pageQuery(PageRequest request);
}
