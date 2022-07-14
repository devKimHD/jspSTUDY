package com.kh.vo;

import java.sql.Date;

public class MemberVo {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joindate;
	private int point;

	



	public MemberVo() {
		super();

	}
	
	

	public MemberVo(String id, String pwd, String name, String email, Date joindate, int point) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.joindate = joindate;
		this.point = point;
	}



	public MemberVo(String id, String pwd, String name, String email) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}



	public MemberVo(String id, String pwd, String name, String email, Date joindate) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.joindate = joindate;
	}
	public int getPoint() {
		return point;
	}



	public void setPoint(int point) {
		this.point = point;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}



	@Override
	public String toString() {
		return "MemberVo [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", joindate=" + joindate
				+ ", point=" + point + "]";
	}

	

}
