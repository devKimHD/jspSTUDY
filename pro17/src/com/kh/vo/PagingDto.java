package com.kh.vo;

public class PagingDto {
	private int page = 1;
	private int startRow = 1;
	private int endRow = 10;
	private int startPage = 1;
	private int endPage = 10;
	private int count;
	private int totalPage;
	private String searchType;
	private String keyword;
	private int perPage=10;

	public void setPage(int page,int count) {
		this.page = page;
		this.count= count;
		System.out.println(page);
		setPageInfo();
	}

	private void setPageInfo() {
		// page startR endR
		// 1 1 10
		// 2 11 20
		// 3 21 30
		endRow =page*perPage;
		startRow = endRow-(perPage-1);
		totalPage = (int)(Math.ceil((double)count / perPage));
//		totalPage = count / 10 + ((count % 10) > 0 ? 1 : 0);
		startPage= ((page-1)/10)*10+1;
		System.out.println(startPage);
		endPage=startPage+9;
		if (endPage > totalPage) {
			endPage = totalPage;
		}
	
		
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getPage() {
		return page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	@Override
	public String toString() {
		return "PagingDto [page=" + page + ", startRow=" + startRow + ", endRow=" + endRow + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", count=" + count + ", totalPage=" + totalPage + ", searchType="
				+ searchType + ", keyword=" + keyword + ", perPage=" + perPage + "]";
	}


	


}
