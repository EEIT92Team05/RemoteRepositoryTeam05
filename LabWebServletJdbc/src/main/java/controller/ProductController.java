package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.ProductBean;
import model.ProductService;
import model.spring.CustomPrimitiveNumberEditor;

@Controller
@RequestMapping(path={"/pages/product.controller"})
public class ProductController {
	private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
	@InitBinder
	public void initialize(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(int.class, new CustomPrimitiveNumberEditor(Integer.class, true));
		webDataBinder.registerCustomEditor(double.class, new CustomPrimitiveNumberEditor(Double.class, true));
		webDataBinder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(sdFormat, true));
	}
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})
	public String method(ProductBean bean, BindingResult bindingResult, String prodaction,
			@RequestParam(name="id") String temp1, Model model) {
//接收資料	
		Map<String, String> errors = new HashMap<String, String>();
		model.addAttribute("errors", errors);
		
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(temp1==null || temp1.length()==0) {
				errors.put("id", "請輸入Id以便於執行"+prodaction);
			}
		}
		
		if(bindingResult!=null && bindingResult.getErrorCount()!=0) {
			if(bindingResult.getFieldError("id")!=null) {
				errors.put("id", "Id必須是整數(MVC)");
			}
			if(bindingResult.getFieldError("price")!=null) {
				errors.put("price", "Price必須是數字(MVC)");
			}
			if(bindingResult.getFieldError("make")!=null) {
				errors.put("make", "Make必須是日期，並且遵守YYYY-MM-DD的格式(MVC)");
			}
			if(bindingResult.getFieldError("expire")!=null) {
				errors.put("expire", "Expire必須是整數(MVC)");
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "product.error";
		}
		
//呼叫model, 根據Model執行結果呼叫View
		if("Select".equals(prodaction)) {
			List<ProductBean> select = productService.select(bean);
			model.addAttribute("select", select);
			return "product.select";
		} else if("Insert".equals(prodaction)) {
			ProductBean insert = productService.insert(bean);
			if(insert==null) {
				errors.put("action", "Insert失敗");
			} else {
				model.addAttribute("insert", insert);
			}
			return "product.error";
		} else if("Update".equals(prodaction)) {
			ProductBean update = productService.update(bean);
			if(update==null) {
				errors.put("action", "Update失敗");
			} else {
				model.addAttribute("update", update);
			}
			return "product.error";
		} else if("Delete".equals(prodaction)) {
			boolean success = productService.delete(bean);
			if(success) {
				model.addAttribute("delete", 1);
			} else {
				model.addAttribute("delete", 0);
			}
			return "product.error";
		} else {
			errors.put("action", "Unknown Action:"+prodaction);
			return "product.error";
		}
	}
}
