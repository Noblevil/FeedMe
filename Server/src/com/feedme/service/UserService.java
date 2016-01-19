package com.feedme.service;

import java.text.ParseException;

import com.feedme.entity.User;
import com.feedme.util.Result;

public interface UserService {
	

	/** 
     *  ͨ���˺ŵõ��û�����
     *  
     * @param userAccount 
     * @return user
     */
	public User getUser(String userAccount);
	/** 
     *  ͨ���˺ŵõ��û�����
     *  
     * @param userAccount 
     * @return user
     */
	public User getUser(int userId);
	
	
	 /** 
     *  �ж��û���ע����˺��Ƿ���ڣ� 
     *  
     * @param userAccount 
     * @return boolean
     */
	public boolean isExitUserAccount(String userAccount);
	
	/** 
     *  ��ʼ��һ���û�����ע�� 
     *  
     * @param user 
     * @return user
	 * @throws ParseException 
     */
	public User initUser(User user) throws ParseException;

	/** 
     *  �����û� 
     *  
     * @param user 
     * @return user 
     */
	public int saveUser(User user);
	
	/** 
     *  �����û���������
     *  
     * @param userAccount 
     * @return user
     */
	public Result setUserResult(int resultState,User user);
	
	/** 
     *  �ж��˻������Ƿ���ȷ 
     *  
     * @param userAccount userPassword
     * @return boolean
     */
	public boolean regexLogin(String userAccount,String userPassword);
}
