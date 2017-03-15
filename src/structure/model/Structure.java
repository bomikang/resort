package structure.model;

public class Structure {
	private int no;
	private String name;
	private int people;
	private int price;
	private String option;
	private String image;
	private String width; //면적
	
	public Structure() {}

	public Structure(int no, String name, int people, int price, String option, String image) {
		this.no = no;
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
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	
}
