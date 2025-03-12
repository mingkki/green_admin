package egovframework.coing.cmm.util;

import egovframework.coing.board.service.BoardWriteService;
import egovframework.coing.board.vo.BoardWriteVO;
import egovframework.coing.cmm.service.UserService;
import egovframework.coing.cmm.vo.UserLoginLogVO;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DashBoardHelper {
    
    private WebApplicationContext context;
    private BoardWriteService boardWriteService;
    private UserService userService;
    
    public DashBoardHelper(HttpServletRequest request) throws Exception {
        
        this.context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        this.boardWriteService = (BoardWriteService) this.context.getBean("boardWriteService");
        this.userService = (UserService) this.context.getBean("userService");
    }

	/*public int getBoardWriteListCnt(String brdId) throws Exception {

		return getBoardWriteListCnt(brdId, 0, "", "");
	}

	public int getBoardWriteListCnt(String brdId,
			int categoryId,
			String searchCondition,
			String searchKeyword) throws Exception {

		BoardWriteVO searchVO = new BoardWriteVO();
		searchVO.setBrdId(brdId);
		searchVO.setBwrDelYn("N");
		searchVO.setBwrCategoryId(categoryId);
		searchVO.setSearchCondition(searchCondition);
		searchVO.setSearchKeyword(searchKeyword);

		return boardWriteService.selectBoardWriteListCnt(searchVO);
	}*/
    
    
    public List<BoardWriteVO> getBoardWriteList(String brdId) throws Exception {
        
        BoardWriteVO boardWriteVO = new BoardWriteVO();
        List<BoardWriteVO> list = boardWriteService.selectBoardWriteListAll(boardWriteVO);
        
        return list;
    }
    
    public List<UserLoginLogVO> getLoginLogList(String ullId) throws Exception {
        
        UserLoginLogVO userLoginLogVO = new UserLoginLogVO();
        List<UserLoginLogVO> list = userService.selectLoginLogListAll(userLoginLogVO);
        
        return list;
    }
    
}