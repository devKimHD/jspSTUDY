package com.kh.vo;

import java.sql.Date;

public class PointVo {
	private int p_no;
	private String id;
	private int point;
	private int point_code;
	private Date point_date;
	private String point_name;
	private String point_desc;
	public PointVo() {
		super();
	}
	public PointVo(int p_no, String id, int point, int point_code, Date point_date, String point_name,
			String point_desc) {
		super();
		this.p_no = p_no;
		this.id = id;
		this.point = point;
		this.point_code = point_code;
		this.point_date = point_date;
		this.point_name = point_name;
		this.point_desc = point_desc;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getPoint_code() {
		return point_code;
	}
	public void setPoint_code(int point_code) {
		this.point_code = point_code;
	}
	public Date getPoint_date() {
		return point_date;
	}
	public void setPoint_date(Date point_date) {
		this.point_date = point_date;
	}
	public String getPoint_name() {
		return point_name;
	}
	public void setPoint_name(String point_name) {
		this.point_name = point_name;
	}
	public String getPoint_desc() {
		return point_desc;
	}
	public void setPoint_desc(String point_desc) {
		this.point_desc = point_desc;
	}
	@Override
	public String toString() {
		return "PointVo [p_no=" + p_no + ", id=" + id + ", point=" + point + ", point_code=" + point_code
				+ ", point_date=" + point_date + ", point_name=" + point_name + ", point_desc=" + point_desc + "]";
	}
	
}
