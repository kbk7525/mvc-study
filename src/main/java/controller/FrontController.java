package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import command.BoardService;
import command.BoardServiceImpl;
import dao.BDao;
import dao.BDaoImpl;
import dto.BDto;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		request.setCharacterEncoding("euc-kr");
		String uri = request.getRequestURI();
		String conpath = request.getContextPath();
		String com = uri.substring(conpath.length());
		String viewPage = null;
		BoardService bcmd = new BoardServiceImpl();
		HttpSession session = request.getSession();
		
		System.out.println("actionDo = " + com);
		if(com.equals("/list.do")) {
			ArrayList<BDto> bList = bcmd.showBoardList();
			session.setAttribute("bList", bList);
			viewPage = "board_list.jsp";
		}
		if(com.equals("/write_view.do")) {
			viewPage = "write_view.jsp";
		}
		if(com.equals("/write.do")) {
			int ret = 0;
			String bName = request.getParameter("bName");
			String bTitle = request.getParameter("bTitle");
			String bContent = request.getParameter("bContent");
			BDto bto = new BDto(0, bName, bTitle, bContent,null,0); 
			ret = bcmd.writeContent(bto);
			System.out.println(bName + "-" + bTitle + "-" + bContent);
			viewPage = "list.do";
		}
		if(com.equals("/content_view.do")) {
			String sId = request.getParameter("bId");
			int bId = Integer.parseInt(sId);
			BDto bdto = bcmd.viewContent(bId);
			session.setAttribute("content_view", bdto);
			viewPage = "content_view.jsp";
		}
		if(com.equals("/modify.do")) {
			String sId = request.getParameter("bId");
			int bId = Integer.parseInt(sId);
			String bName = request.getParameter("bName");
			String bTitle = request.getParameter("bTitle");
			String bContent = request.getParameter("bContent");
			BDto bdto = new BDto(bId, bName, bTitle, bContent, null, 0);
			bcmd.modifyContent(bdto);
			viewPage = "list.do";
		}
		if(com.equals("/delete.do")) {
			String sId = request.getParameter("bId");
			int bId = Integer.parseInt(sId);
			bcmd.deleteContent(bId);
			viewPage = "list.do";
		}
		response.sendRedirect(viewPage);
	}
}
