package jdbc;
/* 게시판 인덱스 구성 관련 임시 클래스 */
public class IndexOfPage {
	int start;
	int end;
	int maxIndex;
	int nowIndex;
	
	public IndexOfPage() {}
	
	public IndexOfPage(int maxIndex, int nowIndex) {		
		this.maxIndex = maxIndex;
		this.nowIndex = nowIndex;
		this.start = getStartIndex(nowIndex);
		this.end = getEndIndex(nowIndex, maxIndex);
		System.out.println("start : "+start);
		System.out.println("end : "+end);
	}
	public int getEndIndex(int now, int max) {
		int endIndex = (int)((now/10)+1)*10;
		if(endIndex >= maxIndex){
			endIndex = maxIndex;
		}
		return endIndex;
	}

	public int getStartIndex(int now) {
		return (int)(now/10)*10+1;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getMaxIndex() {
		return maxIndex;
	}
	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}
	public int getNowIndex() {
		return nowIndex;
	}
	public void setNowIndex(int nowIndex) {
		this.nowIndex = nowIndex;
	}
}
