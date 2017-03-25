package member.handler;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class MyInfoUpdate implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "index.jsp?page=/WEB-INF/member/myinfo&menu=/WEB-INF/member/mem_menu";
		} else if (req.getMethod().equalsIgnoreCase("post")) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				String name = req.getParameter("name");
				String id = req.getParameter("id");
				String pwd = req.getParameter("password");
				String mail = req.getParameter("mail");
				String tel = req.getParameter("tel");
				MemberDao dao = MemberDao.getInstance();
				LoginMemberInfo userInfo = (LoginMemberInfo) req.getSession().getAttribute("user_info");
				Member memberinfo = dao.selectByNo(conn, userInfo.getMy_no());

				Member user_Info = new Member(userInfo.getMy_no(), userInfo.getMy_id(), memberinfo.getPassword(),
						userInfo.getMy_name(), userInfo.getMy_mail(), userInfo.getMy_tel(), null, null, null);
				String result = "";

				if (name != null) {
					user_Info.setName(name);

					dao.updateInFo(conn, user_Info);
					result = "ok";
					Member mem = dao.selectById(conn, user_Info.getId());

					LoginMemberInfo myinfo = new LoginMemberInfo(mem.getNo(), mem.getId(), mem.getName(), mem.getMail(),
							mem.getIsMng(), mem.getTel());

					req.getSession().setAttribute("user_info", myinfo);
					// json 사용 시 필요구문
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(result); // JSP화면 JSON
																	// 데이터
																	// result값
																	// 반환

					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();
				}

				if (pwd != null) {
					user_Info.setPassword(pwd);
					dao.updateInFo(conn, user_Info);
					result = "ok";
					Member mem = dao.selectById(conn, user_Info.getId());
					LoginMemberInfo myinfo = new LoginMemberInfo(mem.getNo(), mem.getId(), mem.getName(), mem.getMail(),
							mem.getIsMng(), mem.getTel());

					req.getSession().setAttribute("user_info", myinfo);

					// json 사용 시 필요구문
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(result); // JSP화면 JSON
																	// 데이터
																	// result값
																	// 반환

					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();

				}

				if (mail != null) {
					user_Info.setMail(mail);
					dao.updateInFo(conn, user_Info);
					result = "ok";
					Member mem = dao.selectById(conn, user_Info.getId());
					LoginMemberInfo myinfo = new LoginMemberInfo(mem.getNo(), mem.getId(), mem.getName(), mem.getMail(),
							mem.getIsMng(), mem.getTel());

					req.getSession().setAttribute("user_info", myinfo);

					// json 사용 시 필요구문
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(result); // JSP화면 JSON
																	// 데이터
																	// result값
																	// 반환

					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();

				}
				if (tel != null) {
					user_Info.setTel(tel);
					dao.updateInFo(conn, user_Info);
					result = "ok";
					Member mem = dao.selectById(conn, user_Info.getId());
					LoginMemberInfo myinfo = new LoginMemberInfo(mem.getNo(), mem.getId(), mem.getName(), mem.getMail(),
							mem.getIsMng(), mem.getTel());

					req.getSession().setAttribute("user_info", myinfo);

					// json 사용 시 필요구문
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(result); // JSP화면 JSON
																	// 데이터
																	// result값
																	// 반환

					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();

				}

			} finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
