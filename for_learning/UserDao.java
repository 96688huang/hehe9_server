package cn.hehe9.persistent.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.hehe9.persistent.entity.User;

/**
 * ��˵�����û�daoʵ��
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2012-3-25 ����02:24:15
 */
@Repository
public class UserDao {
	private final String INSERT_USER = "insertUser";
	private final String UPDATE_USER = "updateUser";
	private final String DELETE_USER = "deleteUser";
	private final String FIND_USER_BYID = "findUserById";
	private final String SELECT_ALL_USER = "selectAllUser";
	private final String USER_LOGIN = "userLogin";
//	@Autowired
//	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdsu.edu.dao.impl.UserDao#insertUser(com.pdsu.edu.domain.User)
	 */
	public void insertUser(User user) {
//		sqlSessionTemplate.insert(INSERT_USER, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdsu.edu.dao.impl.UserDao#updateUser(com.pdsu.edu.domain.User)
	 */
	public void updateUser(User user) {
//		sqlSessionTemplate.update(UPDATE_USER, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdsu.edu.dao.impl.UserDao#deleteUser(java.lang.Integer)
	 */
	public void deleteUser(Integer userId) {
//		sqlSessionTemplate.delete(DELETE_USER, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdsu.edu.dao.impl.UserDao#findUserByid(java.lang.Integer)
	 */
	public User findUserByid(Integer userId) {
//		return sqlSessionTemplate.selectOne(FIND_USER_BYID, userId);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdsu.edu.dao.impl.UserDao#findAll()
	 */
	public List<User> findAll() {
//		return sqlSessionTemplate.selectList(SELECT_ALL_USER);
		return null;
	}

	public User userLogin(User user) {
//		return sqlSessionTemplate.selectOne(USER_LOGIN, user);
		return null;
	}
}
