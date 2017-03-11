package introduce.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class IntroduceHandler implements CommandHandler{
	private static final String VIEW_FORDER = "/WEB-INF/introduce/";

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "index.jsp";
		}
		return null;
	}

}
