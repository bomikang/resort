package structure.model;

import java.util.Calendar;

import book.model.Book;

public class Structure {
	private int no;
	private int id;
	private String name;
	private int people;
	private int price;
	private String option;
	private String image;
	private String width; //면적
	
	public Structure() {}

	public Structure(int no, int id, String name, int people, int price, String option, String image) {
		this.no = no;
		this.id = id;
		this.name = name;
		this.people = people;
		this.price = price;
		this.option = option;
		this.image = image;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWidth() {
		switch (people) {
		case 4:
			width = "26.44㎡"; break;
		case 6:
			width = "39.66㎡"; break;
		case 8:
			width = "46.27㎡"; break;
		case 17:
			width = "86㎡"; break;
		}
		return width;
	}
	
	/* 구분 번호 별 시설 명 리턴 */
	public String getNameById(){
		String result = "";
		switch (this.id) {
		case 1:
			result = "숲속의집"; break;
		case 2:
			result = "산림휴양관"; break;
		case 3:
			result = "캐라반"; break;
		case 4:
			result = "돔하우스"; break;
		}
		return result;
	}

	/* 시설 안내 화면에서 성수기 요금을 보여주기 */
	public String getOriginPriceToString(){
		return String.format("%,d", getPrice());
	}
	
	/* 시설 안내 화면에서 비수기 요금을 보여주기 */
	public String getLessPriceToString(){
		int price = (int) (getPrice() * 0.7);
		return String.format("%,d", price);
	}
	
}
