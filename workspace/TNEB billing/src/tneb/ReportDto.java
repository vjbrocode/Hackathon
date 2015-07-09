package tneb;

public class ReportDto {
	
   private String custId;
   private String startId;
   private String endId;
   private double bill;
   
   //profile details
   private String name;
   private String address;
   
   private String month;
   private String tier_start;
   private String tier_end;
   private String unit;
   
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getStartId() {
		return startId;
	}
	public void setStartId(String startId) {
		this.startId = startId;
	}
	public String getEndId() {
		return endId;
	}
	public void setEndId(String endId) {
		this.endId = endId;
	}
	public double getBill() {
		return bill;
	}
	public void setBill(double bill) {
		this.bill = bill;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMonth() {
		return month;
	}
	   
	//added for db insertion
	
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTier_start() {
		return tier_start;
	}
	public void setTier_start(String tier_start) {
		this.tier_start = tier_start;
	}
	public String getTier_end() {
		return tier_end;
	}
	public void setTier_end(String tier_end) {
		this.tier_end = tier_end;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
		   

}
