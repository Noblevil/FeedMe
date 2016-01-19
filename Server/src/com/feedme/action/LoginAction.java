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
	
	private boolean saveFlag = true;// �������˺� ����Ϸ�����֤

	@Override
	public String execute() throws Exception {

		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out;
		out = response.getWriter();

		UserService userService = new UserServiceImpl();// �õ�ҵ���߼���ķ���

		if (saveFlag == false) {// �û��˺ţ����벻�Ϸ�
			return null;
		}

		boolean isExit = userService.isExitUserAccount(getUserAccount());// �ж��û���ע����˺��Ƿ���ڣ�

		if (isExit != true) {
			// ���û���ע����˺Ų�����
			result.setResult(2);
			System.out.println(gson.toJson(result));
			out.println(gson.toJson(result));
			return null;
		} else {
			boolean isTrue = userService.regexLogin(userAccount, userPassword);// �Ƿ��������
			if (isTrue != true) {
				// �������
				result.setResult(0);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				return null;
			} else {
				// ��֤�ɹ��������½
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
