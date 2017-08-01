package cn.chen.bos.service.impl;

import java.util.List;
import java.util.Set;

import cn.chen.bos.domain.user.User;
import cn.chen.bos.domain.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.chen.bos.dao.UserDao;
import cn.chen.bos.service.UserService;


@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	/*@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		//return userDao.findByEmailAndPassword(email, password);
		return userDao.login(email, password);
		//return userDao.login1(email,password);
	}

	@Override
	public User findUserByTelpehone(String telephone) {
		return userDao.findByTelephone(telephone);

	}

	@Override
	public void updatePasswordByTelephone(String telephone, String password) {
		userDao.updatePasswordByTelephone( telephone,  password);
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public void save(User model, String[] grantRoles) {
		userDao.saveAndFlush(model);
		if (grantRoles != null && grantRoles.length != 0) {
			Set<Role> roles = model.getRoles();
			for (String rid : grantRoles) {
				Role role = new Role();
				role.setId(rid);
				roles.add(role);
			}
		}
	}

	@Override
	public Page<User> pageQuery(PageRequest request) {
		return userDao.findAll(request);
	}

}
