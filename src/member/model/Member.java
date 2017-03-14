package member.model;

public class Member {
	private int no;
	private String id;
	private String password;
	private String name;
	private String mail;
	private String tel;
	private Boolean mem_out;
	
	public Member(int no, String id, String password, String name, String mail, String tel,
			Boolean mem_out) {
		super();
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
	
		this.mail = mail;
		this.tel = tel;
		this.mem_out = mem_out;
	}

	public Member(String id) {
		super();
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean getMem_out() {
		return mem_out;
	}

	public void setMem_out(Boolean mem_out) {
		this.mem_out = mem_out;
	}
	
	
	
}
