package com.project.call.junbum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.project.call.domain.FreeBoard;
import com.project.call.domain.Member;
import com.project.call.domain.PointProduct;
import com.projectcall.daomapper.DaoMapper;

@Repository
public class JBDaoImpl implements JBDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private DaoMapper dm = new DaoMapper();
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Member getloginResult(String email) {
		SqlParameterSource emailparam = new MapSqlParameterSource("email", email);
		return namedParameterJdbcTemplate.query("select * from member where email = :email", emailparam,
				dm.getMemberResultSetExtractor());
	}
	
	@Override
	public Integer getProductCount() {
		SqlParameterSource boardparam = new MapSqlParameterSource("product", "product");
		return namedParameterJdbcTemplate.queryForObject("select count(*) from product", boardparam, Integer.class);
	}
	
	@Override
	public List<PointProduct> getproductList(int startRow, int PAGE_SIZE) {
		SqlParameterSource productparam = new MapSqlParameterSource("startRow", startRow).addValue("PAGE_SIZE", PAGE_SIZE);
		return namedParameterJdbcTemplate.query("select * from product where amount > 0 limit :startRow, :PAGE_SIZE", productparam,dm.getProductRowMapper());
	}
	
	@Override
	public void addProduct(PointProduct p) {
		SqlParameterSource prodparam = new BeanPropertySqlParameterSource(p);
		namedParameterJdbcTemplate.update("insert into product values(0, :pName, :pPrice, ' ', :pAmount, :pImage, 0)", prodparam);
	}
	
	@Override
	public PointProduct productContent(int pNo) {
		SqlParameterSource pNoparam = new MapSqlParameterSource("pNo", pNo);
		return namedParameterJdbcTemplate.query("select * from product where productcode = :pNo", pNoparam, dm.getProductResultSetExtractor());
	}
	
	@Override
	public void updateProduct(PointProduct p) {
		SqlParameterSource prodparam = new BeanPropertySqlParameterSource(p);
		namedParameterJdbcTemplate.update("update product set productname=:pName, point=:pPrice, amount=:pAmount,"
				+ "image=:pImage where productcode=:pProductCode ", prodparam);
	}
	
	@Override
	public void productDelete(int pProductCode) {
		SqlParameterSource pProductCodeparam = new MapSqlParameterSource("pProductCode", pProductCode);
		namedParameterJdbcTemplate.update("delete from product where productcode=:pProductCode ", pProductCodeparam);
	}
	
	@Override
	public void orderProduct(PointProduct p, Member m) {
		SqlParameterSource prodparam = new BeanPropertySqlParameterSource(p);
		SqlParameterSource memparam = new BeanPropertySqlParameterSource(m);
		namedParameterJdbcTemplate.update("update product set amount=:pAmount, buy=:pBuy where productcode=:pProductCode ", prodparam);
		namedParameterJdbcTemplate.update("update member set usepoint=:usepoint where email=:email", memparam);
	}
	
	@Override
	public Integer getaggroCount() {
		SqlParameterSource aggroparam = new MapSqlParameterSource("aggro", "aggro");
		return namedParameterJdbcTemplate.queryForObject("select count(*) from freeboard where area=:aggro", aggroparam, Integer.class);
	}
	
	@Override
	public List<FreeBoard> getAggroList(int startRow, int PAGE_SIZE) {
		SqlParameterSource productparam = new MapSqlParameterSource("startRow", startRow).addValue("PAGE_SIZE", PAGE_SIZE);
		return namedParameterJdbcTemplate.query("select fb.*, (select count(*) from comment where comment.bno = fb.no) as comm "
				+ "from freeboard fb where  fb.area='aggro' order by writedate desc limit :startRow, :PAGE_SIZE ", productparam, dm.getFreeBoardRowMapper());
	}
	
	@Override
	public void aggroBoardWrite(FreeBoard fb) {
		SqlParameterSource fbparam = new BeanPropertySqlParameterSource(fb);
		namedParameterJdbcTemplate.update("insert into freeboard values(0, :frbTitle, :frbPass, :frbContent, '', :frbWriteDate, 0, :frbArea"
				+ ", :frbEmail, :frbWriter)", fbparam);
	}
	
	@Override
	public void aggroBoardWritephoto(FreeBoard fb) {
		SqlParameterSource fbparam = new BeanPropertySqlParameterSource(fb);
		namedParameterJdbcTemplate.update("insert into freeboard values(0, :frbTitle, :frbPass, :frbContent, :Photo1, :frbWriteDate, 0, :frbArea"
				+ ", :frbEmail, :frbWriter)", fbparam);
	}
}
