package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.CustomerBean;
import model.CustomerService;

@Controller
@RequestMapping(path={"/secure/login.controller"})
@SessionAttributes(names={"user"})
public class LoginController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})
	public String method(
			@RequestParam(name="username") String username,
			@RequestParam(name="password") String password,
			Model model,
			@SessionAttribute(name="dest", required=false) String dest) {
//接收資料
//驗證資料
		Map<String, String> errors = new HashMap<String, String>();
		model.addAttribute("errors", errors);
		
		if(username==null || username.length()==0) {
			errors.put("username", "Please enter ID for login (MVC)");
		}
		if(password==null || password.length()==0) {
			errors.put("password", "Please enter PWD for login (MVC)");
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "login.fail";
		}
		
//呼叫model
		CustomerBean bean = customerService.login(username, password);
		
//根據model執行結果，導向view
		if(bean==null) {
			errors.put("password", "Login failed, please try again.");
			return "login.fail";
		} else {
			model.addAttribute("user", bean);
			
			if(dest==null || dest.length()==0) {
				return "login.success";
			} else {
				return "redirect:"+dest;
			}
		}
	}
}

