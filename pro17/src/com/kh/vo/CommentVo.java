package com.kh.vo;

import java.sql.Date;

public class CommentVo {
	private int c_id;
	private String c_content;
	private String id;
	private int b_no;
	private Date c_date;
	public CommentVo() {
		super();
	}
	public CommentVo(String c_content, String id, int b_no) {
		super();
		this.c_content = c_content;
		this.id = id;
		this.b_no = b_no;
	}
	public CommentVo(int c_id, String c_content, String id, int b_no, Date c_date) {
		super();
		this.c_id = c_id;
		this.c_content = c_content;
		this.id = id;
		this.b_no = b_no;
		this.c_date = c_date;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public Date getC_date() {
		return c_date;
	}
	public void setC_date(Date c_date) {
		this.c_date = c_date;
	}
	@Override
	public String toString() {
		return "CommentVo [c_id=" + c_id + ", c_content=" + c_content + ", id=" + id + ", b_no=" + b_no + ", c_date="
				+ c_date + "]";
	}
	
}
