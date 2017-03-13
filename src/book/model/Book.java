package book.model;

import java.util.Date;
import java.util.Formatter;

public class Book {
	/* FIELDS */
	private int no;
	private String name;
	private String tel;
	private int price;
//	private Member mem;
//	private Structure str;
	private Date regDate;
	private Date startDate;
	private Date endDate;
	private boolean isCancel;
	private Date calcelDate;
	
	/* CONSTRUCTOR */
	public Book() {}
	
	/* GETTER */
	public int getNo() {
		return no;
	}
	public String getName() {
		return name;
	}
	public String getTel() {
		return tel;
	}
	public int getPrice() {
		return price;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public boolean isCancel() {
		return isCancel;
	}
	public Date getCalcelDate() {
		return calcelDate;
	}
	public String getPriceForm(){
		String priceForm = "";
		Formatter fmt = new Formatter();
		try{
			priceForm = fmt.format("%,d원", price).toString();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			fmt.close();
		}
		return priceForm;		
	}
	
	/* SETTER */
	public void setNo(int no) {
		this.no = no;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
	public void setCalcelDate(Date calcelDate) {
		this.calcelDate = calcelDate;
	}
	
	
	
	
	
}
