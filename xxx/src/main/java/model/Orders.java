package model;

import java.util.Date;
import java.util.List;

public class Orders {
	private int oid;
	private int mid;
	private double totalcost;
	private String bno;
	private Date orderdate;
	private List<OrderItem> items;
	
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}
    
	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) throws Exception {
		String pattern = "[A-Z]{2}-\\d{8}";
		if (bno != null && bno.trim().length() > 0 && bno.matches(pattern))
			this.bno = bno;
		else
			throw new Exception("輸入的發票號碼不正確!");
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
    
	
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public static void main(String[] args){
		Orders order = new Orders();
		try {
			order.setBno("XY-12323132");
			System.out.println(order.getBno());
		} catch (Exception e) {
			System.out.println("WRONG!!");
		}
	} 
}
