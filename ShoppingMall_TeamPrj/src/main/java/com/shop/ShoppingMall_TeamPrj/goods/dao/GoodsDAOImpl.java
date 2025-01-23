package com.shop.ShoppingMall_TeamPrj.goods.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;
/*import com.myspring.pro30.board.vo.ImageVO;*/

@Repository("goodsDAO")
public class GoodsDAOImpl implements GoodsDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectGoodsList(int category_id) throws DataAccessException {
		List<GoodsVO> goodsList = sqlSession.selectList("mapper.goods.selectGoodsList", category_id);
		return goodsList;
	}
	@Override
	public List selectGoodsNewList() throws DataAccessException {
		List<GoodsVO> goodsList = sqlSession.selectList("mapper.goods.selectGoodsNewList");
		return goodsList;
	}
	
	@Override
	public GoodsVO selectGoods(int product_id) throws DataAccessException {
		return sqlSession.selectOne("mapper.goods.selectGoods", product_id);
	}

	@Override
	public ArrayList selectGoodsBySearchWord(String searchWord) throws DataAccessException{
		ArrayList list=(ArrayList)sqlSession.selectList("mapper.goods.selectGoodsBySearchWord",searchWord);
		 return list;
	}
	/*
	 * @Override public int insertNewArticle(Map articleMap) throws
	 * DataAccessException { int articleNO = selectNewArticleNO();
	 * articleMap.put("articleNO", articleNO);
	 * sqlSession.insert("mapper.board.insertNewArticle",articleMap); return
	 * articleNO; }
	 * 
	 * @Override public void insertReplyArticle(Map articleMap) throws
	 * DataAccessException { int articleNO = selectNewArticleNO();
	 * articleMap.put("articleNO", articleNO);
	 * sqlSession.insert("mapper.board.insertReplyArticle",articleMap); }
	 * 
	 * //다중 파일 업로드
	 * 
	 * @Override public void insertNewImage(Map articleMap) throws
	 * DataAccessException { List<ImageVO> imageFileList =
	 * (ArrayList)articleMap.get("imageFileList"); int articleNO =
	 * (Integer)articleMap.get("articleNO"); int imageFileNO =
	 * selectNewImageFileNO(); for(ImageVO imageVO : imageFileList){
	 * imageVO.setImageFileNO(++imageFileNO); imageVO.setArticleNO(articleNO); }
	 * sqlSession.insert("mapper.board.insertNewImage",imageFileList); }
	 * 
	 * 
	 * 
	 * @Override public ArticleVO selectArticle(int articleNO) throws
	 * DataAccessException { return
	 * sqlSession.selectOne("mapper.board.selectArticle", articleNO); }
	 * 
	 * @Override public void updateArticle(Map articleMap) throws
	 * DataAccessException { sqlSession.update("mapper.board.updateArticle",
	 * articleMap); }
	 * 
	 * @Override public void deleteArticle(int articleNO) throws DataAccessException
	 * { sqlSession.delete("mapper.board.deleteArticle", articleNO);
	 * 
	 * }
	 * 
	 * @Override public List selectImageFileList(int articleNO) throws
	 * DataAccessException { List<ImageVO> imageFileList = null; imageFileList =
	 * sqlSession.selectList("mapper.board.selectImageFileList",articleNO); return
	 * imageFileList; }
	 * 
	 * private int selectNewArticleNO() throws DataAccessException { return
	 * sqlSession.selectOne("mapper.board.selectNewArticleNO"); }
	 * 
	 * private int selectNewImageFileNO() throws DataAccessException { return
	 * sqlSession.selectOne("mapper.board.selectNewImageFileNO"); }
	 */
}
