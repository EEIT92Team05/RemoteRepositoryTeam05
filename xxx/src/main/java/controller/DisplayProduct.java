package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProductBean;
import model.ProductService;
import model.dao.ProductDAOJdbc;
@WebServlet("/getProduct.do")
public class DisplayProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null) {
			pageNo = "1";
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("pageNo")) {
						pageNo = cookie.getValue();
					}
				}
			}
		}
		Cookie cookie = new Cookie("pageNo", pageNo);
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(30*60*60);
		response.addCookie(cookie);

		ProductService service = new ProductService();
		List<ProductBean> list = service.getPageProduct(Integer.parseInt(pageNo));
		HttpSession session = request.getSession();
		session.setAttribute("productList", list);
		session.setAttribute("pageNo", pageNo);
        response.sendRedirect(response.encodeRedirectURL("/xxx/displayProduct.jsp"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
