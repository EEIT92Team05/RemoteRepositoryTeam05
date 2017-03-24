package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import init.ApplicationService;
import model.ProductBean;

public class ProductDAOJdbc implements model.ProductDAO {
	private static final String SQLServer_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=GG";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	private static final String FIND_BY_PRIMARY_KEY = "SELECT * FROM product WHERE pdid = ?";
	private static final String SELECT_ALL = "SELECT * FROM product";
	private static final String SELECT_PAGE = "SELECT pdid,pdname,pdprice,pddiscount,pdinfo FROM (" +
				 "SELECT *, rowno=ROW_NUMBER() OVER (ORDER BY pdid desc) FROM product) temp WHERE rowno between ? and ? ";
	private static final String INSERT = "INSERT INTO product (pdname, pdprice, pddate, pddiscount , pdinfo , filename , pdimg) VALUES (?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE product SET pdname = ? , pdprice = ? , pddate = ? , pddiscount = ? , pdinfo = ? , filename = ?, pdimg = ?"
			+ "WHERE pdid = ?";
	private static final String DELETE = "DELETE FROM product WHERE pdid = ?";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ProductBean findByPrimaryKey(int id) {
		ProductBean bean = null;
		try {
			Class.forName(SQLServer_DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(FIND_BY_PRIMARY_KEY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean = new ProductBean();
				bean.setPdid(rs.getInt("pdid"));
				bean.setPdname(rs.getString("pdname"));
				bean.setPdprice(rs.getDouble("pdprice"));
				bean.setPddate(rs.getDate("pddate"));
				bean.setPddiscount(rs.getDouble("pddiscount"));
				bean.setPdinfo(rs.getString("pdinfo"));
				bean.setFilename(rs.getString("filename"));
				bean.setPdimg(rs.getBlob("pdimg"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	public List<ProductBean> selectAll() {
		List<ProductBean> list = new ArrayList<ProductBean>();
		try {
			Class.forName(SQLServer_DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean bean = new ProductBean();
				bean.setPdid(rs.getInt("pdid"));
				bean.setPdname(rs.getString("pdname"));
				bean.setPdprice(rs.getDouble("pdprice"));
				bean.setPddate(rs.getDate("pddate"));
				bean.setPddiscount(rs.getDouble("pddiscount"));
				bean.setPdinfo(rs.getString("pdinfo"));
				bean.setFilename(rs.getString("filename"));
				bean.setPdimg(rs.getBlob("pdimg"));
				list.add(bean);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
    
	public List<ProductBean> selectPage(int pageNo){
		List<ProductBean> list = new ArrayList<ProductBean>();
		try {
			Class.forName(SQLServer_DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_PAGE);
			int start = (pageNo -1) * ApplicationService.RECORDS_PER_PAGE + 1;
			int end = pageNo * ApplicationService.RECORDS_PER_PAGE;
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean bean = new ProductBean();
				bean.setPdid(rs.getInt("pdid"));
				bean.setPdname(rs.getString("pdname"));
				bean.setPdprice(rs.getDouble("pdprice"));
				bean.setPddiscount(rs.getDouble("pddiscount"));
				bean.setPdinfo(rs.getString("pdinfo"));
				list.add(bean);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
	
	public boolean insert(ProductBean bean, String fileName, InputStream is, long size) {
		try {
			Class.forName(SQLServer_DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, bean.getPdname());
			pstmt.setDouble(2, bean.getPdprice());
			pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			pstmt.setDouble(4, bean.getPddiscount());
			pstmt.setString(5, bean.getPdinfo());
			pstmt.setString(6, fileName);
			pstmt.setBinaryStream(7, is, size);
			int success = pstmt.executeUpdate();
			if(success == 1) return true;
			// pdname, pdprice, pddate, pddiscount , pdinfo , filename , pdimg
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(conn != null)
					conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean insert(ProductBean bean) {
		try {
			Class.forName(SQLServer_DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, bean.getPdname());
			pstmt.setDouble(2, bean.getPdprice());
			pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			pstmt.setDouble(4, bean.getPddiscount());
			pstmt.setString(5, bean.getPdinfo());
			pstmt.setString(6, bean.getFilename());
			try {
				File file = new File("C:/_JSP/workspace/xxx/src/main/webapp/images/" + bean.getFilename());
				InputStream is = new FileInputStream(file);
				pstmt.setBinaryStream(7, is, file.length());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int i = pstmt.executeUpdate();
			if (i == 1) {
				System.out.println("商品新增成功!");
				return true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("商品新增失敗!");
		return false;
	}
 
	public boolean update(ProductBean bean) {
		try {
			Class.forName(init.ApplicationService.driverName);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, bean.getPdname());
			pstmt.setDouble(2, bean.getPdprice());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			pstmt.setDate(3, new java.sql.Date(bean.getPddate().getTime()));
			pstmt.setDouble(4, bean.getPddiscount());
			pstmt.setString(5, bean.getPdinfo());
			pstmt.setString(6, bean.getFilename());
			pstmt.setBlob(7, bean.getPdimg());
			pstmt.setInt(8, bean.getPdid());
			int success = pstmt.executeUpdate();
			if (success == 1)
				return true;
			// pdname = ? , pdprice = ? , pddate = ? , pddiscount = ? , pdinfo =
			// ? , filename = ?, pdimg = ?"
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
    
	public boolean update(ProductBean bean , String fileName ,InputStream is , long length) {
		try {
			Class.forName(init.ApplicationService.driverName);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, bean.getPdname());
			pstmt.setDouble(2, bean.getPdprice());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			pstmt.setDate(3, new java.sql.Date(bean.getPddate().getTime()));
			pstmt.setDouble(4, bean.getPddiscount());
			pstmt.setString(5, bean.getPdinfo());
			pstmt.setString(6, fileName);
			pstmt.setBlob(7, is , (int)length);
			pstmt.setInt(8, bean.getPdid());
			int success = pstmt.executeUpdate();
			if (success == 1)
				return true;
			// pdname = ? , pdprice = ? , pddate = ? , pddiscount = ? , pdinfo =
			// ? , filename = ?, pdimg = ?"
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean delete(ProductBean bean) {
		try {
			Class.forName(init.ApplicationService.driverName);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, bean.getPdid());
			int success = pstmt.executeUpdate();
			if (success == 1)
				return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// pdname, pdprice, pddate, pddiscount , pdinfo , filename , pdimg
		//塞資料
//		for(int i=0 ; i<=12 ; i++){
//		 ProductBean bean = new ProductBean();
//		 bean.setPdname("image" + i);
//		 bean.setPdprice(100.00 + i);
//		 bean.setPddate(new java.util.Date());
//		 bean.setPddiscount(1);
//		 bean.setPdinfo("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//		 bean.setFilename("image" + i + ".jpg");
//		 ProductDAO dao = new ProductDAO();
//		 dao.insert(bean);
//		}
		//單筆查詢
//		ProductBean bean = dao.findByPrimaryKey(1);
//		Blob img = bean.getPdimg();
//		OutputStream out = null;
//		try {
//			byte[] b = img.getBytes(1, (int) img.length());
//			out = new FileOutputStream(new File("C:/test/xxx.png"));
//			out.write(b, 0, b.length);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (out != null)
//					out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		ProductDAOJdbc dao = new ProductDAOJdbc();
		System.out.println(dao.selectAll());
	}
}
