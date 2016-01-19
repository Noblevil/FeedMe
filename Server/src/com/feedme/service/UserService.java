package com.feedme.service;

import java.text.ParseException;

import com.feedme.entity.User;
import com.feedme.util.Result;

public interface UserService {
	

	/** 
     *  通过账号得到用户对象
     *  
     * @param userAccount 
     * @return user
     */
	public User getUser(String userAccount);
	/** 
     *  通过账号得到用户对象
     *  
     * @param userAccount 
     * @return user
     */
	public User getUser(int userId);
	
	
	 /** 
     *  判断用户想注册的账号是否存在？ 
     *  
     * @param userAccount 
     * @return boolean
     */
	public boolean isExitUserAccount(String userAccount);
	
	/** 
     *  初始化一个用户用于注册 
     *  
     * @param user 
     * @return user
	 * @throws ParseException 
     */
	public User initUser(User user) throws ParseException;

	/** 
     *  保存用户 
     *  
     * @param user 
     * @return user 
     */
	public int saveUser(User user);
	
	/** 
     *  设置用户对象结果集
     *  
     * @param userAccount 
     * @return user
     */
	public Result setUserResult(int resultState,User user);
	
	/** 
     *  判断账户密码是否正确 
     *  
     * @param userAccount userPassword
     * @return boolean
     */
	public boolean regexLogin(String userAccount,String userPassword);
}
