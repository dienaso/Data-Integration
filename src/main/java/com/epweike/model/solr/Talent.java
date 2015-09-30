package com.epweike.model.solr;

import org.apache.solr.client.solrj.beans.Field;

import com.epweike.model.BaseModel;

public class Talent extends BaseModel<Talent> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field
	private Integer uid;
	@Field
	private Integer shop_id;
	@Field
	private Integer seller_credit;
	@Field
	private long sch_brand;
	@Field
	private Integer group_id;
	@Field
	private Integer task_bid_num;
	@Field
	private Integer credit_score;
	@Field
	private Integer shop_level;
	@Field
	private String w_level;
	@Field
	private float w_good_rate;
	@Field
	private Integer integrity;
	@Field
	private String integrity_ids;
	@Field
	private Integer user_type;
	@Field
	private String username;
	@Field
	private Integer is_close;
	@Field
	private String province;
	@Field
	private String city;
	@Field
	private String area;
	@Field
	private float task_income_cash;
	@Field
	private float task_income_credit;
	@Field
	private String currency;
	@Field
	private Integer accepted_num;
	@Field
	private Integer auth_realname;
	@Field
	private Integer auth_bank;
	@Field
	private Integer auth_email;
	@Field
	private Integer auth_mobile;
	@Field
	private Integer chief_designer;
	@Field
	private Integer isvip;
	@Field
	private long sch_city;
	@Field
	private long sch_province;
	@Field
	private Integer integrity_points;
	@Field
	private long vip_start_time;
	@Field
	private long vip_end_time;
	@Field
	private String come;
	@Field
	private String shop_name;
	@Field
	private String shop_desc;
	@Field
	private long on_time;
	@Field
	private String last_login_time;
	@Field
	private Integer views;
	@Field
	private String skill_ids;
	@Field
	private String indus_ids;
	@Field
	private String indus_names;

	/**
	 * @return the last_login_time
	 */
	public String getLast_login_time() {
		return last_login_time;
	}

	/**
	 * @param last_login_time
	 *            the last_login_time to set
	 */
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}

	/**
	 * @return the on_time
	 */
	public long getOn_time() {
		return on_time;
	}

	/**
	 * @param on_time
	 *            the on_time to set
	 */
	public void setOn_time(long on_time) {
		this.on_time = on_time;
	}

	/**
	 * @return the uid
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * @return the shop_id
	 */
	public Integer getShop_id() {
		return shop_id;
	}

	/**
	 * @param shop_id
	 *            the shop_id to set
	 */
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	/**
	 * @return the seller_credit
	 */
	public Integer getSeller_credit() {
		return seller_credit;
	}

	/**
	 * @param seller_credit
	 *            the seller_credit to set
	 */
	public void setSeller_credit(Integer seller_credit) {
		this.seller_credit = seller_credit;
	}

	/**
	 * @return the sch_brand
	 */
	public long getSch_brand() {
		return sch_brand;
	}

	/**
	 * @param sch_brand
	 *            the sch_brand to set
	 */
	public void setSch_brand(long sch_brand) {
		this.sch_brand = sch_brand;
	}

	/**
	 * @param sch_city
	 *            the sch_city to set
	 */
	public void setSch_city(long sch_city) {
		this.sch_city = sch_city;
	}

	/**
	 * @param sch_province
	 *            the sch_province to set
	 */
	public void setSch_province(long sch_province) {
		this.sch_province = sch_province;
	}

	/**
	 * @return the group_id
	 */
	public Integer getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id
	 *            the group_id to set
	 */
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return the task_bid_num
	 */
	public Integer getTask_bid_num() {
		return task_bid_num;
	}

	/**
	 * @param task_bid_num
	 *            the task_bid_num to set
	 */
	public void setTask_bid_num(Integer task_bid_num) {
		this.task_bid_num = task_bid_num;
	}

	/**
	 * @return the credit_score
	 */
	public Integer getCredit_score() {
		return credit_score;
	}

	/**
	 * @param credit_score
	 *            the credit_score to set
	 */
	public void setCredit_score(Integer credit_score) {
		this.credit_score = credit_score;
	}

	/**
	 * @return the shop_level
	 */
	public Integer getShop_level() {
		return shop_level;
	}

	/**
	 * @param shop_level
	 *            the shop_level to set
	 */
	public void setShop_level(Integer shop_level) {
		this.shop_level = shop_level;
	}

	/**
	 * @return the w_level
	 */
	public String getW_level() {
		return w_level;
	}

	/**
	 * @param w_level
	 *            the w_level to set
	 */
	public void setW_level(String w_level) {
		this.w_level = w_level;
	}

	/**
	 * @return the w_good_rate
	 */
	public float getW_good_rate() {
		return w_good_rate;
	}

	/**
	 * @param w_good_rate
	 *            the w_good_rate to set
	 */
	public void setW_good_rate(float w_good_rate) {
		this.w_good_rate = w_good_rate;
	}

	/**
	 * @return the integrity
	 */
	public Integer getIntegrity() {
		return integrity;
	}

	/**
	 * @param integrity
	 *            the integrity to set
	 */
	public void setIntegrity(Integer integrity) {
		this.integrity = integrity;
	}

	/**
	 * @return the integrity_ids
	 */
	public String getIntegrity_ids() {
		return integrity_ids;
	}

	/**
	 * @param integrity_ids
	 *            the integrity_ids to set
	 */
	public void setIntegrity_ids(String integrity_ids) {
		this.integrity_ids = integrity_ids;
	}

	/**
	 * @return the user_type
	 */
	public Integer getUser_type() {
		return user_type;
	}

	/**
	 * @param user_type
	 *            the user_type to set
	 */
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the is_close
	 */
	public Integer getIs_close() {
		return is_close;
	}

	/**
	 * @param is_close
	 *            the is_close to set
	 */
	public void setIs_close(Integer is_close) {
		this.is_close = is_close;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the task_income_cash
	 */
	public float getTask_income_cash() {
		return task_income_cash;
	}

	/**
	 * @param task_income_cash
	 *            the task_income_cash to set
	 */
	public void setTask_income_cash(float task_income_cash) {
		this.task_income_cash = task_income_cash;
	}

	/**
	 * @return the task_income_credit
	 */
	public float getTask_income_credit() {
		return task_income_credit;
	}

	/**
	 * @param task_income_credit
	 *            the task_income_credit to set
	 */
	public void setTask_income_credit(float task_income_credit) {
		this.task_income_credit = task_income_credit;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the accepted_num
	 */
	public Integer getAccepted_num() {
		return accepted_num;
	}

	/**
	 * @param accepted_num
	 *            the accepted_num to set
	 */
	public void setAccepted_num(Integer accepted_num) {
		this.accepted_num = accepted_num;
	}

	/**
	 * @return the auth_realname
	 */
	public Integer getAuth_realname() {
		return auth_realname;
	}

	/**
	 * @param auth_realname
	 *            the auth_realname to set
	 */
	public void setAuth_realname(Integer auth_realname) {
		this.auth_realname = auth_realname;
	}

	/**
	 * @return the auth_bank
	 */
	public Integer getAuth_bank() {
		return auth_bank;
	}

	/**
	 * @param auth_bank
	 *            the auth_bank to set
	 */
	public void setAuth_bank(Integer auth_bank) {
		this.auth_bank = auth_bank;
	}

	/**
	 * @return the auth_email
	 */
	public Integer getAuth_email() {
		return auth_email;
	}

	/**
	 * @param auth_email
	 *            the auth_email to set
	 */
	public void setAuth_email(Integer auth_email) {
		this.auth_email = auth_email;
	}

	/**
	 * @return the auth_mobile
	 */
	public Integer getAuth_mobile() {
		return auth_mobile;
	}

	/**
	 * @param auth_mobile
	 *            the auth_mobile to set
	 */
	public void setAuth_mobile(Integer auth_mobile) {
		this.auth_mobile = auth_mobile;
	}

	/**
	 * @return the chief_designer
	 */
	public Integer getChief_designer() {
		return chief_designer;
	}

	/**
	 * @param chief_designer
	 *            the chief_designer to set
	 */
	public void setChief_designer(Integer chief_designer) {
		this.chief_designer = chief_designer;
	}

	/**
	 * @return the isvip
	 */
	public Integer getIsvip() {
		return isvip;
	}

	/**
	 * @param isvip
	 *            the isvip to set
	 */
	public void setIsvip(Integer isvip) {
		this.isvip = isvip;
	}

	/**
	 * @return the sch_city
	 */
	public long getSch_city() {
		return sch_city;
	}

	/**
	 * @param sch_city
	 *            the sch_city to set
	 */
	public void setSch_city(Integer sch_city) {
		this.sch_city = sch_city;
	}

	/**
	 * @return the sch_province
	 */
	public long getSch_province() {
		return sch_province;
	}

	/**
	 * @param sch_province
	 *            the sch_province to set
	 */
	public void setSch_province(Integer sch_province) {
		this.sch_province = sch_province;
	}

	/**
	 * @return the integrity_points
	 */
	public Integer getIntegrity_points() {
		return integrity_points;
	}

	/**
	 * @param integrity_points
	 *            the integrity_points to set
	 */
	public void setIntegrity_points(Integer integrity_points) {
		this.integrity_points = integrity_points;
	}

	/**
	 * @return the vip_start_time
	 */
	public long getVip_start_time() {
		return vip_start_time;
	}

	/**
	 * @param vip_start_time
	 *            the vip_start_time to set
	 */
	public void setVip_start_time(long vip_start_time) {
		this.vip_start_time = vip_start_time;
	}

	/**
	 * @return the vip_end_time
	 */
	public long getVip_end_time() {
		return vip_end_time;
	}

	/**
	 * @param vip_end_time
	 *            the vip_end_time to set
	 */
	public void setVip_end_time(long vip_end_time) {
		this.vip_end_time = vip_end_time;
	}

	/**
	 * @return the come
	 */
	public String getCome() {
		return come;
	}

	/**
	 * @param come
	 *            the come to set
	 */
	public void setCome(String come) {
		this.come = come;
	}

	/**
	 * @return the shop_name
	 */
	public String getShop_name() {
		return shop_name;
	}

	/**
	 * @param shop_name
	 *            the shop_name to set
	 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	/**
	 * @return the shop_desc
	 */
	public String getShop_desc() {
		return shop_desc;
	}

	/**
	 * @param shop_desc
	 *            the shop_desc to set
	 */
	public void setShop_desc(String shop_desc) {
		this.shop_desc = shop_desc;
	}

	/**
	 * @return the views
	 */
	public Integer getViews() {
		return views;
	}

	/**
	 * @param views
	 *            the views to set
	 */
	public void setViews(Integer views) {
		this.views = views;
	}

	/**
	 * @return the skill_ids
	 */
	public String getSkill_ids() {
		return skill_ids;
	}

	/**
	 * @param skill_ids
	 *            the skill_ids to set
	 */
	public void setSkill_ids(String skill_ids) {
		this.skill_ids = skill_ids;
	}

	/**
	 * @return the indus_ids
	 */
	public String getIndus_ids() {
		return indus_ids;
	}

	/**
	 * @param indus_ids
	 *            the indus_ids to set
	 */
	public void setIndus_ids(String indus_ids) {
		this.indus_ids = indus_ids;
	}

	/**
	 * @return the indus_names
	 */
	public String getIndus_names() {
		return indus_names;
	}

	/**
	 * @param indus_names
	 *            the indus_names to set
	 */
	public void setIndus_names(String indus_names) {
		this.indus_names = indus_names;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}