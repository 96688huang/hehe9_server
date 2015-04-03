package cn.hehe9.service.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ��˵�����û�Serviceʵ��
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2012-3-25 ����02:26:48
 */
@Service
@Transactional
public class UserService {

	/*
	@Autowired
	private UserDao userDao;

	// ����û�
	public void addUser(User user) {
		userDao.insertUser(user);
	}

	// �����û�
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public void deleteUser(Integer userId) {
		userDao.deleteUser(userId);
	}

	public User findUserById(Integer userId) {
		return userDao.findUserByid(userId);
	}

	public List<User> findAllUser() {
		return userDao.findAll();
	}

	public User login(User user) {
		return userDao.userLogin(user);
	}*/
}
