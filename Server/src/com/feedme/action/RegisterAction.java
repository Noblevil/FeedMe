package com.feedme.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.feedme.entity.User;
import com.feedme.service.UserService;
import com.feedme.service.impl.UserServiceImpl;
import com.feedme.util.RegexUtils;
import com.feedme.util.Result;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class RegisterAction extends ActionSupport implements ModelDriven<User> {

	/**
	 * 生成序列号
	 */
	private static final long serialVersionUID = 4359250061315060783L;

	private User user = new User();
	private Result result = new Result();

	private boolean saveFlag = true;// 用来做账号 密码合法性验证

	@Override
	public String execute() throws Exception {
		Gson gson = new Gson();// 得到GOSN
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out = response.getWriter();// 得到响应对象

		UserService userService = new UserServiceImpl();// 得到业务逻辑层的方法

		if (saveFlag == false) {// 如果账号密码不合法
			return null;
		}
		
		boolean isExit = userService.isExitUserAccount(user.getUserAccount());// 判断用户想注册的账号是否存在？
		if (isExit == true) {
			// 则用户想注册的账号已存在
			result.setResult(2);
			System.out.println(gson.toJson(result));
			out.println(gson.toJson(result));
			return null;
		}
		
		User userInited=userService.initUser(user);
		
		int userId=userService.saveUser(userInited);
		
		result.setResult(1);
		result.setUserId(userId);
		System.out.println(gson.toJson(result));
		out.println(gson.toJson(result));
		return null;
	}

	@Override
	public void validate() {
		Gson gson = new Gson();
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out;

		String userAccount = user.getUserAccount();
		String userPassword = user.getUserPassword();

		RegexUtils regex = new RegexUtils();// 得到验证类和验证方法

		try {
			out = response.getWriter();
			// 验证账户
			if (!regex.regexUserAccount(userAccount)||userAccount==null) {
				result.setResult(1001);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				saveFlag = false;
				return;
			}
			// 验证密码
			if (!regex.regexUserPassword(userPassword)||userPassword==null) {
				result.setResult(1003);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				saveFlag = false;
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getModel() {
		
		return user;
	}

}
