package introduce.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class IntroGalleryHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//return "index.jsp?page=/WEB-INF/structure/str_map&menu=/WEB-INF/structure/str_menu";
		return "index.jsp?page=/WEB-INF/introduce/intro_gallery&menu=/WEB-INF/introduce/intro_menu";
	}

}
