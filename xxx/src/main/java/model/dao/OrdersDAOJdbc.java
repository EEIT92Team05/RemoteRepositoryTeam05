package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.OrderItem;
import model.Orders;

public class OrdersDAOJdbc {
	private static final String SQLServer_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=GG";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	private static final String INSERT_ORDER =
			"INSERT INTO orders (mid,totalcost,orderdate,bno) VALUES (?,?,?,?)";
	private static final String INSERT_ORDERITEMS =
			"INSERT INTO orderitem(oid,pdid,pdname,pdprice,pddiscount,amount) VALUES(?,?,?,?,?,?)";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
    
	public boolean insert(Orders order) throws SQLException{
		try {
			Class.forName(SQLServer_DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT_ORDER,Statement.RETURN_GENERATED_KEYS);
		    pstmt.setInt(1, order.getMid());
		    pstmt.setDouble(2, order.getTotalcost());
		    pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
		    pstmt.setString(4, order.getBno());
            pstmt.executeUpdate();
			int id = 0;
			rs = pstmt.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1);
			}else{
				throw new SQLException("no generated key obtained!");
			}
			pstmt = conn.prepareStatement(INSERT_ORDERITEMS);
			for( OrderItem orderItem : order.getItems()){		
				pstmt.setInt(1, id);
				pstmt.setInt(2, orderItem.getPdid());
				pstmt.setString(3, orderItem.getPdname());
				pstmt.setDouble(4, orderItem.getPdprice());
				pstmt.setDouble(5, orderItem.getPddiscount());
				pstmt.setInt(6, orderItem.getAmount());
				int success = pstmt.executeUpdate();
				if(success != 1) 
					throw new SQLException("新增訂單明細失敗!");
				pstmt.clearParameters();
			}
			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("新增失敗 , Rollback!!");
			if(conn!=null) conn.rollback();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return true;
	}
	public static void main(String[] args){
		Orders order = new Orders();
		order.setMid(15);
		order.setTotalcost(12222.00);
		try {
			order.setBno("XY-12323133");
		} catch (Exception e) {
	        System.out.println("發票號碼不正確");
		}
		order.setOrderdate(new Date());
		
		List<OrderItem> items = new ArrayList<>();
		OrderItem or1 = new OrderItem();
		or1.setPdid(17);
		or1.setPdname("TOSHIBA");
		or1.setPdprice(20000.00);
		or1.setPddiscount(1);
		or1.setAmount(5);
		items.add(or1);
		OrderItem or2 = new OrderItem();
		or2.setPdid(18);
		or2.setPdname("ASUS");
		or2.setPdprice(25000.00);
		or2.setPddiscount(1);
		or2.setAmount(4);
		items.add(or2);
		order.setItems(items);
		OrdersDAOJdbc dao = new OrdersDAOJdbc();
		try {
			dao.insert(order);
		} catch (SQLException e) {
		}
	}
}
