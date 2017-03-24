package book.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import member.model.Member;
import structure.model.Structure;

public class Book {
	/* FIELDS */
	private String no;	
	private Member mem;
	private Structure str;
	private String name;
	private String tel;
	private Date regDate;
	private Date startDate;
	private Date endDate;
	private Date cancelDate;
	private String state;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/* CONSTRUCTOR */
	public Book() {}
	
	/* GETTER */
	public String getNo() {
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
	
	public String getName() {
		return name;
	}

	/* SETTER */
	public void setNo(String no) {
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
	
	public void setName(String name) {
		this.name = name;
	}

	/* METHODS */
	/**
	 * 시설 가격과 숙박 기간을 바탕으로 총 예약 금액을 계산한다.
	 * 주로 성수기로 분류 되는 7, 8월 그리고 금요일, 토요일에 숙박하는 경우를 성수기로 분류하고 가격 전부를 부과하고,
	 * 그 외는 비수기로 분류하여 30%할인된 가격을 부과한다. 공휴일은 아직 적용되지 않기 때문에 공휴일은 제외하고 계산하는 메소드 
	 * 예약된 가격을 화면에 내보낼 때 가격 형식의 String Form으로 변환하여 Return 하는 Methods 
	 * */
	public int getPrice(){
		int price = this.str.getPrice();
		int period = (int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
		int totalPrice = 0;
		Calendar temp = Calendar.getInstance();
		temp.setTime(this.startDate);
		for(int i=0;i<period;i++){
			if(temp.get(Calendar.MONTH)==6||temp.get(Calendar.MONTH)==7||temp.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY||temp.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
				totalPrice += price;
			}else{
				totalPrice += (price*0.7);
			}
			temp.add(Calendar.DATE, 1);
		}
		System.out.println("예약 기간 : "+period);
		System.out.println("총가격 : "+totalPrice);		
		
		return totalPrice;
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
	public String getRegDateNoTimeForm(){
		return dateFormat.format(regDate);
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
		return "";
		
	}
	/**
	 * 예약 시점에 BookDao 내 insert함수를 사용하기 위해 임시로 생성한 Book객체 내 regDate(객체 생성 시점 시간)를 바탕으로  
	 * 예약번호를 String형태로 생성해 반환해 주는 Method(auto_increment대신 15자리의 시간정보를 담은 String을 primary key로 사용하기 위해서...)
	 * */
	public String getNoForm() {
		SimpleDateFormat bookNoFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.out.println(bookNoFormat.format(regDate));
		return bookNoFormat.format(regDate);
	}
	
	
}
