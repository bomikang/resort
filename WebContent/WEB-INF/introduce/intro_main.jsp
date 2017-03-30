<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	span.bold{width:93px; display:inline-block; color:#00894a;}
</style>

<div class="way_top">
	<h3>눈꽃자연휴양림 소개글<br /><span>홈 > 휴양림 소개 > 눈꽃자연휴양림 소개글</span></h3>
</div>
<div id="intro_main" class='intro_padding'>
	<ul>
		<li>
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>휴양림 안내</h2>
			<ul class="inner_content">
				<li><h4>시내중심부를 낙동강이 흐르면서 구미의 또다른 자랑이 될 옥성자연휴양림을 방문하여 주셔서 진심으로 환영합니다.</h4></li>
				<li>
					<p>
						옥성자연휴양림은 첨단산업뿐만 아니라 역사와 전통을 가진 맑고 깨끗한 자연경관을 지닌 구미의 새로운<br> 
						관광자원으로 사람에게 좋다는 황토를 이용한 숲속의 집은 물론 큰 저수지와 함께 어우러져 있는 <br>
						수변데크, 야영데크, 산림욕을 즐길 수 있는 산책로와 등산로는 물론 원두막, 자연관찰원, 숲속교실 등<br>
						다양하고 편안한 시설로 이용객을 맞이하고 있습니다.
					</p>
				</li>
				<li><p>일상의 스트레스를 벗어나 자연을 벗삼아 휴식을 취하시려는 여러분. 옥성자연휴양림을 찾아주세요.</p></li>
				<li><p>옥성자연휴양림은 여러분 모두를 환영합니다.</p></li>
				<li><p><b>감사합니다.</b></p></li>
				<li>
					<p>
						<a href="structure.do" title="시설 현황 페이지로 이동"><img alt="" src="image/str_intro.png"></a>
					</p>
				</li>
			</ul>	
		</li>
		<li>
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>시설 개요</h2>
			<ul class="inner_content">
				<li><span class="bold">위치</span>경상북도 구미시 옥성면 휴양림길 150</li>
				<li><span class="bold">구역 면적</span>153ha</li>
				<li><span class="bold">휴양림 지정</span>산림청 고시 제2000-29호</li>
				<li><span class="bold">숙박시설 </span>
					<c:if test="${!empty sList }">
						<b>${sList.get(0).nameById }</b>(${sList.get(0).people } 실/호) 
						<c:forEach items="${sList }" var="str" begin="1">
							/ <b>${str.nameById }</b>(${str.people } 실/호) 
						</c:forEach>
					</c:if>
					<c:if test="${empty sList }">
						0
					</c:if>
				</li>
				<li><span class="bold">주차장 </span>6개소</li>
			</ul>
		</li>
	</ul>
</div>