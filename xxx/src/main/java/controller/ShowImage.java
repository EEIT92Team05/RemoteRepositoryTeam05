package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductService;
import model.dao.ProductDAOJdbc;

/**
 * Servlet implementation class ShowImage
 */
@WebServlet("/showImage.do")
public class ShowImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service;
		Map<String, String> errors = new HashMap<String, String>();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String fileName = request.getParameter("fileName");
			if(fileName.endsWith("jpg")){
			     response.setContentType("image/jpeg");
			}else if(fileName.endsWith("png")){
				response.setContentType("image/png");
			}else{
				response.setContentType("image/gif");
			}
			service = new ProductService();
			Blob photo = service.getPhoto(id);
			byte[] b = photo.getBytes(1, (int) photo.length());
			System.out.println(b);
			OutputStream out = response.getOutputStream();
			out.write(b , 0 , b.length);
			out.close();
		} catch (NumberFormatException e) {
			errors.put("data error", "Wrong Data!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
