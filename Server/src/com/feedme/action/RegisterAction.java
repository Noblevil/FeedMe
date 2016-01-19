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
	 * �������к�
	 */
	private static final long serialVersionUID = 4359250061315060783L;

	private User user = new User();
	private Result result = new Result();

	private boolean saveFlag = true;// �������˺� ����Ϸ�����֤

	@Override
	public String execute() throws Exception {
		Gson gson = new Gson();// �õ�GOSN
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out = response.getWriter();// �õ���Ӧ����

		UserService userService = new UserServiceImpl();// �õ�ҵ���߼���ķ���

		if (saveFlag == false) {// ����˺����벻�Ϸ�
			return null;
		}
		
		boolean isExit = userService.isExitUserAccount(user.getUserAccount());// �ж��û���ע����˺��Ƿ���ڣ�
		if (isExit == true) {
			// ���û���ע����˺��Ѵ���
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

		RegexUtils regex = new RegexUtils();// �õ���֤�����֤����

		try {
			out = response.getWriter();
			// ��֤�˻�
			if (!regex.regexUserAccount(userAccount)||userAccount==null) {
				result.setResult(1001);
				System.out.println(gson.toJson(result));
				out.println(gson.toJson(result));
				saveFlag = false;
				return;
			}
			// ��֤����
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
