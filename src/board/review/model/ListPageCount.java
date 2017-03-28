package board.review.model;

import java.util.List;

public class ListPageCount {
	private int total;
	private int currentPage;  // 요청한 페이지 번호를 버관 
	private List<Review> content;
	private int totalPages; // 전체 페이지 갯수 
	private int startPage; 	// 페이지 이동 링크의 시작번호 와 끝번호 저장
	private int endPage;
	public ListPageCount(int total, int currentPage,int size ,List<Review> content) {
		
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		if(total == 0){// 게시글 수가 0개이면 나머지도 0개
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		}else{
			totalPages = total / size;
			if(total%size>0){	 // size : size로 나눈값을 페이지 갯수로 사용 size가 0보다 크면 페이지수 1증가
				totalPages ++;
			}
			int modVal = currentPage % 5;
			startPage = currentPage / 5*5 +1;
			if(modVal ==0)startPage -=5;
			endPage = startPage +4;
			if(endPage>totalPages)endPage = totalPages;
		} 
		
	}
	public int getTotal() {
		return total;
	}
	
	public boolean hasNoArticles(){
		return total ==0;
	}				
	public boolean hasArticles(){
		return total >0;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<Review> getContent() {
		return content;
	}
	public void setContent(List<Review> content) {
		this.content = content;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
}
