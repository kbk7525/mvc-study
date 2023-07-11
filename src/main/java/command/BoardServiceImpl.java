package command;

import java.util.ArrayList;

import dao.BDao;
import dao.BDaoImpl;
import dto.BDto;

public class BoardServiceImpl implements BoardService{
	public ArrayList<BDto> showBoardList() {
		ArrayList<BDto> dtos = null;
		BDao bdao = new BDaoImpl();
		dtos = bdao.showBoardList();
		return dtos;
	}
	public int writeContent(BDto bdto) {
		int ret = 0;
		BDao bdao = new BDaoImpl();
		ret = bdao.writeContent(bdto);
		return ret;
	}
	public BDto viewContent(int id) {
		BDto dto =  null;
		BDao dao = new BDaoImpl();
		dto = dao.viewContent(id);
		return dto;
	}
	public void modifyContent(BDto bdto) {
		BDao dao = new BDaoImpl();
		dao.modifyContent(bdto);
	}
	public int deleteContent(int id) {
		int ret = 0;
		BDao dao = new BDaoImpl();
		ret = dao.deleteContent(id);
		return ret;
	}
}
