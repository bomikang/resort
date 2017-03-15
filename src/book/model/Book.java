package book.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import member.model.Member;
import structure.model.Structure;

public class Book {
	/* FIELDS */
	private int no;	
	private Member mem;
	private Structure str;
	private String tel;
	private Date regDate;
	private Date startDate;
	private Date endDate;
	private Date cancelDate;
	private String state;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/* CONSTRUCTOR */
	public Book() {}
	
	/* GETTER */
	public int getNo() {
		return no;
	}

	public Member getMem() {
		return mem;
	}

	public Structure getStr() {
		return str;
	}

	public String getTel() {
		return tel;
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

	public Date getCancelDate() {
		return cancelDate;
	}

	public String getState() {
		return state;
	}
	/* SETTER */
	public void setNo(int no) {
		this.no = no;
	}

	public void setMem(Member mem) {
		this.mem = mem;
	}

	public void setStr(Structure str) {
		this.str = str;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public void setState(String state) {
		this.state = state;
	}
	/* METHODS */
	/**
	 * 시설 가격과 숙박 기간을 바탕으로 가격을 계산하고, 
	 * 예약된 가격을 화면에 내보낼 때 가격 형식의 String Form으로 변환하여 Return 하는 Methods 
	 * */
	public int getPrice(){
		int price = this.str.getPrice();
		int period = (int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
		System.out.println(period);
		
		return price * period;
	}
	public String getPriceForm(){
		int totalPrice = getPrice();		
		String priceForm = "";
		Formatter fmt = new Formatter();
		try{
			priceForm = fmt.format("%,d원", totalPrice).toString();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			fmt.close();
		}
		return priceForm;		
	}	

	/**
	 * Date 객체를 SimpleDateFormat을 이용하여 정제된 Sting 객체로 반환하는 Method
	 * */
	public String getRegDateForm(){
		return dateTimeFormat.format(regDate);
	}
	public String getStartDateForm(){
		return dateFormat.format(startDate);
	}
	public String getEndDateForm(){
		return dateFormat.format(endDate);
	}
	public String getCancelForm(){
		if(cancelDate!=null){
			return dateTimeFormat.format(cancelDate);
		}
		return null;
		
	}
}
