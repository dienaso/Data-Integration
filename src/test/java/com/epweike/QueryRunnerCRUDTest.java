/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 29, 2015 2:32:38 PM 
 * 
 */
package com.epweike;

import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.epweike.model.Users;
import com.epweike.model.solr.Talent;
import com.epweike.util.JdbcUtils;
import com.epweike.util.QueryUtils;

/**
 * @ClassName: DBUtilsCRUDTest
 * @Description:使用dbutils框架的QueryRunner类完成CRUD,以及批处理
 * @author: wuxp
 * @date: 2015-9-29 下午4:56:44
 *
 */
public class QueryRunnerCRUDTest {

	@Test
	public void add() throws SQLException {
		// 将数据源传递给QueryRunner，QueryRunner内部通过数据源获取数据库连接
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(null));
		String sql = "insert into users(name,password,email,birthday) values(?,?,?,?)";
		Object params[] = { "孤傲苍狼", "123", "gacl@sina.com", new Date() };
		// Object params[] = {"白虎神皇","123", "gacl@sina.com", "1988-05-07"};
		qr.update(sql, params);
	}

	@Test
	public void delete() throws SQLException {

		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(null));
		String sql = "delete from users where id=?";
		qr.update(sql, 1);

	}

	@Test
	public void update() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(null));
		String sql = "update users set name=? where id=?";
		Object params[] = { "ddd", 5 };
		qr.update(sql, params);
	}

	@Test
	public void find() throws SQLException {
		String sql = "SELECT a.uid,a.shop_id,a.seller_credit,CRC32(a.brand) AS sch_brand,a.brand,"
				+ "a.mobile,a.phone,a.group_id,task_bid_num,IF(credit_score > 0, credit_score, 0) AS credit_score,"
				+ "a.shop_level,a.w_level,a.w_good_rate,a.integrity,a.integrity_ids,a.user_type,a.reg_time,FROM_UNIXTIME(a.reg_time) AS reg_time_date,"
				+ "a.username,a.is_close,a.province,a.city,a.AREA,a.task_income_cash,a.task_income_credit,a.currency,a.accepted_num,a.auth_realname,"
				+ "a.auth_bank,a.auth_email,a.auth_mobile,a.chief_designer,a.isvip,CRC32(a.city) AS sch_city,CRC32(a.province) AS sch_province,a.integrity_points,"
				+ "a.vip_start_time,a.vip_end_time,CASE WHEN a.come IS NULL THEN 'WEB' WHEN a.come = '' THEN 'WEB' ELSE come END AS come,"
				+ "DATE_FORMAT(FROM_UNIXTIME(a.reg_time),'%Y-%m-%d') AS reg_date,a.last_login_time,b.shop_name,b.shop_desc,b.views,c.skill_id,"
				+ "GROUP_CONCAT(CONCAT(d.g_id,'-',d.indus_pid,'-',d.indus_id)) AS skill_ids,GROUP_CONCAT(d.indus_id) AS indus_ids,GROUP_CONCAT(d.indus_name) AS indus_names"
				+ " FROM keke_witkey_space a LEFT JOIN keke_witkey_shop b ON a.uid=b.uid LEFT JOIN keke_witkey_member_skill c ON a.uid=c.uid LEFT JOIN keke_witkey_industry d ON c.skill_id=d.indus_id"
				+ " WHERE a.uid=? AND a.uid NOT IN (476, 517, 1084, 621530, 1601, 564517) GROUP BY uid";
		Object params[] = { 555 };
		//Talent talent = qr.query(sql, new BeanHandler<Talent>(Talent.class),params);
		QueryUtils<Talent> queryRunnerUtils = new QueryUtils<Talent>(Talent.class);
		Talent talent = queryRunnerUtils.get(sql, params, null);
		System.out.println(talent.getIndus_names());
	}

	@Test
	public void getAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(null));
		String sql = "select * from keke_witkey_space where uid=555";
		//List list = (List) qr.query(sql, new BeanListHandler(Users.class));
		QueryUtils<Talent> queryRunnerUtils = new QueryUtils<Talent>(Talent.class);
		List list = queryRunnerUtils.select(sql, null, null);
		System.out.println(list.size());
	}

	/**
	 * @Method: testBatch
	 * @Description:批处理
	 * @Anthor:孤傲苍狼
	 *
	 * @throws SQLException
	 */
	@Test
	public void testBatch() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(null));
		String sql = "insert into users(name,password,email,birthday) values(?,?,?,?)";
		Object params[][] = new Object[10][];
		for (int i = 0; i < 10; i++) {
			params[i] = new Object[] { "aa" + i, "123", "aa@sina.com",
					new Date() };
		}
		qr.batch(sql, params);
	}

	// 用dbutils完成大数据（不建议用）
	/***************************************************************************
	 * create table testclob ( id int primary key auto_increment, resume text );
	 **************************************************************************/
	@Test
	public void testclob() throws SQLException, IOException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource(null));
		String sql = "insert into testclob(resume) values(?)"; // clob
		// 这种方式获取的路径，其中的空格会被使用“%20”代替
		String path = QueryRunnerCRUDTest.class.getClassLoader()
				.getResource("data.txt").getPath();
		// 将“%20”替换回空格
		path = path.replaceAll("%20", " ");
		FileReader in = new FileReader(path);
		char[] buffer = new char[(int) new File(path).length()];
		in.read(buffer);
		SerialClob clob = new SerialClob(buffer);
		Object params[] = { clob };
		runner.update(sql, params);
	}
}