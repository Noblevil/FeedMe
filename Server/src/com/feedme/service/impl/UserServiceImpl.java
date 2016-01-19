package com.feedme.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.feedme.dao.BaseDao;
import com.feedme.dao.impl.BaseDaoImpl;
import com.feedme.entity.User;
import com.feedme.service.UserService;
import com.feedme.util.MD5Util;
import com.feedme.util.Result;

public class UserServiceImpl implements UserService {
	
	private BaseDao<User> dao = new BaseDaoImpl<User>();

	@Override
	public int saveUser(User user) {
		Integer userId=dao.save(user);
		return userId;
	}

	@Override
	public User initUser(User user) throws ParseException {
		Date time = new Date();
		Timestamp tsCreateTime = new Timestamp(time.getTime());		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//对用户的密码进行编码
		String MD5=MD5Util.convertMD5(user.getUserPassword());
		user.setUserPassword(MD5);
		user.setUserName(user.getUserAccount());
		user.setUserHead("http://121.42.195.113/feedme/images/default.png");
		user.setUserCreateTime(tsCreateTime);
		user.setUserPoint(0);
		return user;
	}

	@Override
	public boolean isExitUserAccount(String userAccount) {
		User user = getUser(userAccount);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User getUser(String userAccount) {
		String hql = "from User as a where a.userAccount=?";
		String param[] = { userAccount };		
		User user = (User) dao.get(hql, param);
		return user;
	}

	@Override
	public User getUser(int userId) {
		return dao.get(User.class, userId);
	}

	@Override
	public Result setUserResult(int resultState, User user) {
		Result result = new Result();
		result.setResult(resultState);
		result.setUserId(user.getUserId());
		result.setUserAccount(user.getUserAccount());
		result.setUserName(user.getUserName());
		result.setUserHead(user.getUserHead());
		result.setUserCreateTime(user.getUserCreateTime());
		result.setUserPoint(user.getUserPoint());
		return result;
	}

	@Override
	public boolean regexLogin(String userAccount, String userPassword) {
		String MD5 = MD5Util.convertMD5(userPassword);
		String hql = "from User as a where a.userAccount=? and a.userPassword=?";
		String param[] = { userAccount,MD5};//MD5为加密后的密码		
		User user = (User) dao.get(hql, param);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

}
