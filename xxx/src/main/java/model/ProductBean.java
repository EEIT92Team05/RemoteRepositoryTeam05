package model;

import java.sql.Blob;
import java.util.Date;

public class ProductBean {
	private int pdid;
	private String pdname;
	private double pdprice;
	private Date pddate;
	private double pddiscount;
	private String pdinfo;
	private String filename;
	private Blob pdimg;

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

	public Date getPddate() {
		return pddate;
	}

	public void setPddate(Date pddate) {
		this.pddate = pddate;
	}

	public double getPddiscount() {
		return pddiscount;
	}

	public void setPddiscount(double pddiscount) {
		this.pddiscount = pddiscount;
	}

	public String getPdinfo() {
		return pdinfo;
	}

	public void setPdinfo(String pdinfo) {
		this.pdinfo = pdinfo;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Blob getPdimg() {
		return pdimg;
	}

	public void setPdimg(Blob pdimg) {
		this.pdimg = pdimg;
	}
}
