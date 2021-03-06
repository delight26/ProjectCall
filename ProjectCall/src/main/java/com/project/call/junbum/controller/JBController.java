package com.project.call.junbum.controller;

import java.io.IOException;
import java.nio.MappedByteBuffer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.call.domain.Member;
import com.project.call.ikjae.service.IJService;
import com.project.call.junbum.service.JBService;

@Controller
public class JBController {

	@Autowired
	private JBService jBService;
	@Autowired
	private IJService iJService;
	private static final String filePath = "/resources/uploadimages/";

	public void setjBService(JBService jBService) {
		this.jBService = jBService;
	}
	
	public void setiJService(IJService iJService) {
		this.iJService = iJService;
	}

	// 로그인 폼
	@RequestMapping(value = "/loginform")
	public String loginForm(HttpServletRequest request) {
		String pProductCode = request.getParameter("pProductCode");
		String quantity = request.getParameter("quantity");
		String page = request.getParameter("page");
		request.setAttribute("pProductCode", pProductCode);
		request.setAttribute("quantity", quantity);
		request.setAttribute("page", page);
		return "member/login";
	}

	// 로그인
	@RequestMapping(value = "loginresult", method = RequestMethod.POST)
	public String loginResult(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		boolean logtf = jBService.loginResult(request, response, session);
		if (logtf) {
			switch (request.getParameter("page")) {
			case "": {
				return "redirect:index";
			}
			case "cart": {
				return "redirect:getcartlist";
			}
			case "pcontent": {
				return "redirect:buyproduct?pProductCode=" + request.getParameter("pProductCode") + "&quantity="
						+ request.getParameter("quantity");
			}
			case "agrroboard": {
				return "redirect:agrroboard";
			}
			case "ask":{
				return "ask/ask";
			}
			default: {
				return "redirect:loginform";
			}
			}
		}
		System.out.println("로그인시도 실패 : " + request.getRemoteHost());
		return "redirect:index";
	}

	// 로그아웃
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:index";
	}

	// 상품 리스트
	@RequestMapping(value = "productlist")
	public String getproductList(HttpServletRequest request) {
		jBService.getproductList(request);

		return "index.jsp?body=product/productlist";
	}

	// 관리자 상품 리스트
	@RequestMapping(value = "adminproductlist")
	public String getAdminproductList(HttpServletRequest request) {
		jBService.getproductList(request);

		return "index.jsp?body=product/adminproductlist";
	}

	// 상품 추가 폼
	@RequestMapping(value = "productadd")
	public String addproductform() {
		return "product/productadd";
	}

	// 상품 추가 결과
	@RequestMapping(value = "productaddresult", method = RequestMethod.POST)
	public String addProduct(MultipartHttpServletRequest request) throws IOException {
		String path = request.getServletContext().getRealPath(filePath);

		jBService.addProduct(request, path);

		return "redirect:productlist";
	}

	// 상품 수정 페이지
	@RequestMapping(value = "productupdate")
	public String productUpdate(HttpServletRequest request) {
		jBService.productUpdate(request);

		return "product/productupdate";
	}

	// 상품수정 완료
	@RequestMapping(value = "productupdateresult", method = RequestMethod.POST)
	public String productUpdateResult(MultipartHttpServletRequest request) throws IOException {
		String path = request.getServletContext().getRealPath(filePath);
		jBService.productUpdateResult(request, path);

		return "redirect:productlist";
	}

	// 상품 상세보기
	@RequestMapping(value = "/productcontent")
	public String productContent(HttpServletRequest request) {
		jBService.productContent(request);

		return "product/productcontent";
	}

	// 관리자 상품 상세보기
	@RequestMapping(value = "adminproductcontent")
	public String adminproductContent(HttpServletRequest request) {
		jBService.productContent(request);
		return "index.jsp?body=product/adminproductcontent";
	}

	// 상품삭제
	@RequestMapping(value = "productdelete")
	public String productDelete(HttpServletRequest request) {
		jBService.productDelete(request);

		return "redirect:productlist";
	}

	// 장바구니 담기
	@RequestMapping(value = "addcart")
	public String addCart(HttpServletRequest request, HttpSession session) {
		jBService.addCart(request, session);

		return "product/cartselect";
	}

	// 장바구니 리스트
	@RequestMapping(value = "getcartlist")
	public String getCart(HttpSession session) {
		jBService.getCart(session);

		return "index.jsp?body=product/cartlist";
	}

	// 장바구니에서 주문
	@RequestMapping(value = "buycartproduct")
	public String buyCartProduct(HttpServletRequest request, HttpSession session) {
		jBService.buyCartProduct(request);
		return "index.jsp?body=product/buyproduct";
	}

	// 장바구니 주문완료
	@RequestMapping(value = "cartorder")
	public String cartOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String path = request.getServletContext().getRealPath(filePath);
		jBService.orderPrduct(request, response, session, path);

		request.setAttribute("message", "구매가 완료 되었습니다.");
		request.setAttribute("returnUrl", "index");
		return "alertAndRedirect";
	}

	// 상품구매페이지
	@RequestMapping(value = "buyproduct")
	public String buyProduct(HttpSession session, HttpServletRequest request) {
		jBService.buyProduct(request);
		return "index.jsp?body=product/buyproduct";
	}

	// 도발 게시판리스트
	@RequestMapping(value = "agrroboard")
	public String aggroBoardList(HttpServletRequest request, HttpSession session) {
		jBService.aggroBoardList(request);

		return "index.jsp?body=aggro/aggroList";
	}

	// 도발 게시판 글쓰기 폼
	@RequestMapping(value = "aggrowrite")
	public String aggroBoardWrite(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:loginform?page=aggro";
		} else {
			jBService.aggroBoardList(request);

			return "aggro/aggrowrite";
		}
	}

	// 도발 게시판 글쓰기 결과
	@RequestMapping(value = "aggrowriteresult")
	public String aggroBoardWriteResult(MultipartHttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String path = request.getServletContext().getRealPath(filePath);
		jBService.aggroBoardWriteResult(request, response, session, path);

		return "redirect:agrroboard";
	}

	// 도발 게시판 내용
	@RequestMapping(value = "aggrocontent")
	public String aggroContent(HttpServletRequest request) {
		jBService.aggroContent(request);

		return "index.jsp?body=aggro/aggrocontent";
	}

	// 도발 게시판 이전글
	@RequestMapping(value = "aggropre")
	public String aggroPreContent(HttpServletRequest request) {
		jBService.aggroPreContent(request);
		System.out.println(request.getAttribute("frb"));
		if (request.getAttribute("frb") == null) {
			request.setAttribute("message", "최신글 입니다.");
			request.setAttribute("returnUrl", "javascript:history.back()");
			return "alertAndRedirect";
		}
		return "index.jsp?body=aggro/aggrocontent";
	}

	// 도발 게시판 다음글
	@RequestMapping(value = "aggronext")
	public String aggroNextContent(HttpServletRequest request) {
		jBService.aggroNextContent(request);
		System.out.println("frb=" + request.getAttribute("frb"));
		if (request.getAttribute("frb") == null) {
			request.setAttribute("message", "마지막글 입니다.");
			request.setAttribute("returnUrl", "javascript:history.back()");
			return "alertAndRedirect";
		}
		return "index.jsp?body=aggro/aggrocontent";
	}

	// 도발 게시판 수정
	@RequestMapping(value = "agrroupdate")
	public String aggroUpdateForm(HttpServletRequest request) {
		jBService.aggroUpdateForm(request);

		return "aggro/agrroupdate";
	}

	// 도발 게시판 수정 결과
	@RequestMapping(value = "aggroupdateresult")
	public String agrroUpdateResult(MultipartHttpServletRequest request) throws IOException {
		String path = request.getServletContext().getRealPath(filePath);
		jBService.agrroUpdateResult(request, path);

		return "redirect:agrroboard";
	}

	// 도발 게시판 삭제
	@RequestMapping(value = "aggrodelete")
	public String aggroDelete(HttpServletRequest request) {
		jBService.aggroDelete(request);

		return "redirect:agrroboard";
	}

	//도발 게시판 검색
	@RequestMapping(value="aggrosearch")
	public String aggroSearch(HttpServletRequest request){
		jBService.aggroSearch(request);
		
		return "index.jsp?body=aggro/aggroList";
	}
	
	// 도발 게시판 댓글
	@RequestMapping(value = "aggrocomment")
	public String aggroComment(@RequestParam("frbNo") String frbNo, @RequestParam("pageNum") String pageNum,
			HttpServletRequest request) {
		jBService.getComment(frbNo, pageNum, request);

		return "aggro/aggrocomment";
	}

	// 도발 게시판 댓글 달기
	@RequestMapping(value = "aggrocommentwrite", method = RequestMethod.POST)
	public String aggroCommentWrite(@RequestParam("frbNo") String frbNo, @RequestParam("content") String content,
			@RequestParam("email") String email, HttpServletRequest request) {
		jBService.aggroCommentWrite(frbNo, content, email);
		jBService.getComment(frbNo, "1", request);
		return "aggro/aggrocomment";
	}

	// 도발게시판 댓글 수정
	@RequestMapping(value = "aggrocommentupdate", method = RequestMethod.POST)
	public String aggroCommentUpdate(@RequestParam("cNo") String cNo, @RequestParam("content") String content,
			@RequestParam("frbNo") String frbNo, HttpServletRequest request) {
		jBService.aggroCommentUpdate(cNo, content);
		jBService.getComment(frbNo, "1", request);
		return "aggro/aggrocomment";
	}

	// 도발게시판 댓글 삭제
	@RequestMapping(value = "aggrocommentdelete", method = RequestMethod.POST)
	public String aggroCommentDelete(@RequestParam("cNo") String cNo, @RequestParam("frbNo") String frbNo,
			HttpServletRequest request) {
		jBService.aggroCommentDelete(cNo);
		jBService.getComment(frbNo, "1", request);
		System.out.println();
		return "aggro/aggrocomment";
	}

	// 신청한 대결 리스트
	@RequestMapping(value = "askresultlist")
	public String askResultList(HttpServletRequest request, HttpSession session) {
		jBService.askResultList(request, session);
		return "ask/askresultlist";
	}

	// 신청한 대결 업데이트
	@RequestMapping(value = "askresultupdate")
	public String askResultUpdate(HttpServletRequest request, HttpSession session) {
		jBService.askResultUpdate(request, session);
		return "index.jsp?body=ask/updateAsk";
	}

	// 신청한 대결 업데이트 결과
	@RequestMapping(value = "askresultupdateresult")
	public String askResultUpdateResult(HttpServletRequest request, HttpSession session) {
		jBService.askResultUpdateResult(request);

		return "redirect:ask";
	}

	// 신청한 대결 취소
	@RequestMapping(value = "askresultdelete")
	public String askResultDelete(HttpServletRequest request) {
		jBService.askResultDelete(request);

		return "redirect:ask";
	}

	// 받은 대결 리스트
	@RequestMapping(value = "askrecevelist")
	public String askReceveList(HttpServletRequest request, HttpSession session) {
		jBService.askReceveList(request, session);
		return "ask/askreceverelist";
	}

	// 받은 대결 수락
	@RequestMapping(value = "askapproval")
	public String askApproval(HttpServletRequest request) {
		jBService.askApproval(request);

		return "redirect:ask";
	}

	// 받은 대결 거절
	@RequestMapping(value="askcancel")
	public String askCancel(HttpServletRequest request) {
		jBService.askCancel(request);

		return "redirect:ask";
	}
	
	//개인정보 수정
	@RequestMapping(value="jbmyInfo")
	public String myInfo(HttpSession session){
		Member m = (Member)session.getAttribute("loginUser");
		System.out.println(m.getName());
		return "myPage/myInfo";
	}
	
	//나의 승부
	@RequestMapping(value="jbmyMatch")
	public String myMatch(HttpSession session, HttpServletRequest request){
		Member m = (Member) session.getAttribute("loginUser");
		String loginUser = m.getEmail();
		Member member = iJService.getMember(loginUser);
		request.setAttribute("member", member);
		iJService.getFightList(loginUser, request);
		float winningRate=0;
		if(member.getWin() + member.getLose()!=0){
			winningRate =
					100 * member.getWin() / (member.getWin() + member.getLose());
		}else{
			winningRate=0;
		}
		request.setAttribute("winningRate", winningRate);
		return "myPage/myMatch";
	}
	
	//나의 포인트
	@RequestMapping(value="jbmyPoint")
	public String myPoint(){
		return "myPage/myPoint";
	}
	
	//비밀번호 체크
	@RequestMapping(value="jbPassCheck")
	public String passCheck(){
		return "myPage/passCheck";
	}
	
	//카트 전체 비우기
	@RequestMapping(value="cartlistremoveall")
	public String cartRemoveAll(HttpServletRequest request, HttpSession session){
		jBService.cartRemoveAll(request, session);
		if(session.getAttribute("pList") != null){
			request.setAttribute("result", 1);
		}else{
			request.setAttribute("result", 0);
		}
		return "AjaxResult";
	}
	
	//카트 선택 삭제
	@RequestMapping(value="cartlistremove")
	public String cartRemove(HttpServletRequest request, HttpSession session){
		jBService.cartRemove(request, session);
		if(session.getAttribute("pList") != null){
			request.setAttribute("result", 1);
		}else{
			request.setAttribute("result", 0);
		}
		return "AjaxResult";
	}
	
	@RequestMapping(value="ask")
	public String ask(){
		
		return "index.jsp?body=ask/ask";
	}
	
	@RequestMapping(value="getMemberId")
	public String getMemberId(HttpServletRequest request){
		jBService.getMemberId(request);
		return "member/getId";
	}
	
	@RequestMapping(value="findIdPass")
	public String findIdPass(){
		
		return "member/findIdPass";
	}
	
	@RequestMapping(value="home")
	public String home(){
		return "index.jsp?body=home";
	}
}
