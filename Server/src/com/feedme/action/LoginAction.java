package com.feedme.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.feedme.entity.User;
import com.feedme.service.UserService;
import com.feedme.service.impl.UserServiceImpl;
import com.feedme.util.HibernateProxyTypeAdapter;
import com.feedme.util.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = -5437773056446887837L;
	private String userAccount = null;
	private String userPassword = null;
	private Result result = new Result();
	
	private boolean saveFlag = true;// 用来做账号 密码合法性验证

	@Override
	public String execute() throws Exception {

		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out;
		out = response.getWriter();

		UserService userService = new UserServiceImpl();// 得到业务逻辑层的方法

		if (saveFlag == false) {// 用户账号，密码不合法
			return null;
		}

		boolean isExit = userService.isExitUserAccount(getUserAccount());// 判断用户想注册的账号是否存在？

		if (isExit != true) {
			// 则用户想注册的账号不存在
			result.setResult(2);
			System.out.println(gson.toJson(result));
			out.println(gson.toJson(result));
			return null;
		} else {
			boolean isTrue = userService.regexLogin(userAccount, userPassword);// 是否密码符合
			if (isTrue != true) {
				// 密码错误
				result.setResult(0);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				return null;
			} else {
				// 验证成功，允许登陆
				User user = userService.getUser(userAccount);
				result = userService.setUserResult(1, user);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				return null;
			}
		}
	}

	@Override
	public void validate() {
		String userAccount = getUserAccount();
		String userPassword = getUserPassword();

		Gson gson = new Gson();
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (userAccount == null || userAccount.length() < 6) {
				result.setResult(1001);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				saveFlag = false;
				return;
			}
			if (userPassword == null || userPassword.length() < 6) {
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

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
