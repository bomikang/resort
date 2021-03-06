<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_detail #warning{color:#cc0000;}
	.book_detail_table{width:800px; margin: 0 auto;}
	.book_detail_table th{width:20%;}
	.book_detail_table td{text-align: left; padding-left:10px;}
</style>
<script>
	$(function(){
		$("#btnBack").click(function(){
			location.href = "bookcheck.do";
		});
		$(".bkcancel").submit(function(){
			var bkNo = $(this).find("input[name='bkNo']").val();
		
			if(!confirm("예약번호 : "+bkNo+"의 예약을 취소하시겠습니까?")){
				return false;
			}
		});
		<c:if test="${pageId=='process' }">
			alert("예약이 성공적으로 완료되었습니다.");
		</c:if>
	});
</script>
<c:if test="${empty user_info }">
	<script>
		alert("로그인이 필요한 페이지 입니다.");
		location.href="login.do?category=bookcheckdetail";
	</script>
</c:if>	
<div class="way_top">
	<h3>예약내역<br /><span>홈 > 예약안내 > 예약조회 / 취소 > 예약내역</span></h3>
</div>
<div id="bk_detail" class='intro_padding'>
	<c:if test="${empty book }">
		<script type="text/javascript">
			alert("예약 정보가 존재하지 않습니다.");
		</script>	
	</c:if>
	<c:if test="${!empty book }">
		<c:if test="${pageId=='process' }">
			<p id="warning">인터넷으로 예약하신 후 예약일로 부터 3일 이내(휴일제외) 지정계좌로 사용료를 결제하셔야만 예약이 확정되며,<br />
			 	사용료 입금시에는 반드시 예약번호와 예약자 성명을 함께 기재하여 입금해 주십시오.(계좌번호 : 유진뱅크 123-45-123456 눈꽃자연휴양림)<br>
				예약일 포함 3일 이내에 결제하지 않으시면 자동으로 예약이 취소되므로 이점 유의하시기 바랍니다.
			</p>
		</c:if>
		<h3 class='table_caption'>예약자 정보</h3>
		<table class='book_detail_table'>
			<tr>
				<th>예약 번호</th>
				<td>${book.no }</td>
			</tr>
			<tr>
				<th>예약날짜</th>
				<td>${book.regDateForm }</td>
			</tr>
			<tr>
				<th>예약자</th>
				<td>${book.name }</td>
			</tr>
			<tr>
				<th>연락처</th>
				<td>${book.tel }</td>
			</tr>
		</table>
		<br>
		<h3 class='table_caption'>시설 정보</h3>
		<table class='book_detail_table'>
			<tr>
				<th>시설 명</th>
				<td>${book.str.nameById } ${book.str.name }</td>
			</tr>
			<tr>
				<th>시설 정보</th>
				<td>
					면적 : ${book.str.width }/ 수용인원 : ${book.str.people }명<br>
					옵션 : ${book.str.option }
				</td>
			</tr>
			<tr>
				<th>예약 기간</th>
				<td>${book.startDateForm } ~ ${book.endDateForm }</td>
			</tr>
			<tr>
				<th>예약 구분</th>
				<td>${book.state }
				<c:if test="${!empty book.cancelDate }">
				(취소 날짜  : ${book.cancelForm })
				</c:if>
				</td>
			<tr>
				<th>가격</th>
				<td>${book.priceForm }</td>
			</tr>
		</table>
		<c:choose>
			<c:when test="${pageId=='check' }">
				<c:choose>
					<c:when test="${book.state=='예약취소' }">
						<p class="act_btn_area"><button type="button" id="btnBack">확인</button></p>
					</c:when>
					<c:when test="${book.state=='예약완료' }">
						<p class="act_btn_area"><button type="button" id="btnBack">확인</button></p>
					</c:when>
					<c:when test="${book.state=='입금완료' }">
						<p class="act_btn_area"><button type="button" id="btnBack">확인</button></p>
					</c:when>
					<c:otherwise>
						<form action="bookcancel.do" method="post" class="bkcancel">
							<input type="hidden" value="${book.no }" name="bkNo">
							<p class="act_btn_area">
								<input type="submit" value="취소하기">
								<button type="button" id="btnBack">돌아가기</button>
							</p>
						</form>	
					</c:otherwise>
				</c:choose>
			</c:when>	
			<c:when test="${pageId=='process' }">
				<p class="act_btn_area">
					<button type="button" id="btnBack">확인</button>
				</p>
			</c:when>
		</c:choose>		
	</c:if>
</div>