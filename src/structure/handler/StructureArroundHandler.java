package structure.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class StructureArroundHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "index.jsp?page=/WEB-INF/structure/str_arround&menu=/WEB-INF/structure/str_menu";
	}

}
