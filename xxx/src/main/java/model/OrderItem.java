package model;

public class OrderItem {
	private int oiid;
	private int oid;
	private int pdid;
	private String pdname;
	private double pdprice;
	private double pddiscount;
	private int amount;
	
	public int getOiid() {
		return oiid;
	}
	public void setOiid(int oiid) {
		this.oiid = oiid;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getPdid() {
		return pdid;
	}
	public void setPdid(int pdid) {
		this.pdid = pdid;
	}
	public String getPdname() {
		return pdname;
	}
	public void setPdname(String pdname) {
		this.pdname = pdname;
	}
	public double getPdprice() {
		return pdprice;
	}
	public void setPdprice(double pdprice) {
		this.pdprice = pdprice;
	}
	public double getPddiscount() {
		return pddiscount;
	}
	public void setPddiscount(double pddiscount) {
		this.pddiscount = pddiscount;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}	
}
