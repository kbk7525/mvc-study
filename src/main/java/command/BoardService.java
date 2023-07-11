package command;

import java.util.ArrayList;

import dto.BDto;

public interface BoardService {
	ArrayList<BDto> showBoardList();
	int writeContent(BDto bdto);
	BDto viewContent(int id);
	void modifyContent(BDto bdto);
	int deleteContent(int id);
}
