package com.epweike.model.solr;

import org.apache.solr.client.solrj.beans.Field;

import com.epweike.model.BaseModel;

public class Task extends BaseModel<Task> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field
	private Integer task_id;
	@Field
	private Integer model_id;
	@Field
	private double task_cash;
	@Field
	private Integer uid;
	@Field
	private long pub_time;
	@Field
	private Integer work_count;
	@Field
	private Integer task_status;
	@Field
	private String task_title;
	@Field
	private String task_desc;
	@Field
	private Integer indus_id;
	@Field
	private Integer indus_pid;
	@Field
	private Integer view_num;
	@Field
	private Integer work_num;
	@Field
	private String pay_item;
	@Field
	private Integer task_type;
	@Field
	private Integer cash_status;
	@Field
	private Integer is_delay;
	@Field
	private Integer is_top;
	@Field
	private Integer no_show;
	@Field
	private String point;
	@Field
	private Integer focus_num;
	@Field
	private String logo_type;
	@Field
	private Integer logo_tc;
	@Field
	private Integer w_uid;
	@Field
	private String w_username;
	@Field
	private Integer w_bid_time;
	@Field
	private Integer task_pay_time;
	@Field
	private String payitem_time;
	@Field
	private long start_time;
	@Field
	private long sort_time;
	@Field
	private String task_akey_desc;
	@Field
	private Integer task_cash_coverage;

	@Field
	private Integer service_id;

	@Field
	private Integer g_id;

	@Field
	private long w_integrity;

	/**
	 * @return the task_id
	 */
	public Integer getTask_id() {
		return task_id;
	}

	/**
	 * @param task_id
	 *            the task_id to set
	 */
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}

	/**
	 * @return the start_time
	 */
	public long getStart_time() {
		return start_time;
	}

	/**
	 * @param start_time
	 *            the start_time to set
	 */
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}

	/**
	 * @return the task_cash_coverage
	 */
	public Integer getTask_cash_coverage() {
		return task_cash_coverage;
	}

	/**
	 * @param task_cash_coverage
	 *            the task_cash_coverage to set
	 */
	public void setTask_cash_coverage(Integer task_cash_coverage) {
		this.task_cash_coverage = task_cash_coverage;
	}

	/**
	 * @return the sort_time
	 */
	public long getSort_time() {
		return sort_time;
	}

	/**
	 * @param sort_time
	 *            the sort_time to set
	 */
	public void setSort_time(long sort_time) {
		this.sort_time = sort_time;
	}

	/**
	 * @return the task_akey_desc
	 */
	public String getTask_akey_desc() {
		return task_akey_desc;
	}

	/**
	 * @param task_akey_desc
	 *            the task_akey_desc to set
	 */
	public void setTask_akey_desc(String task_akey_desc) {
		this.task_akey_desc = task_akey_desc;
	}

	/**
	 * @return the task_cash
	 */
	public double getTask_cash() {
		return task_cash;
	}

	/**
	 * @param task_cash
	 *            the task_cash to set
	 */
	public void setTask_cash(double task_cash) {
		this.task_cash = task_cash;
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
	 * @return the pub_time
	 */
	public long getPub_time() {
		return pub_time;
	}

	/**
	 * @param pub_time
	 *            the pub_time to set
	 */
	public void setPub_time(long pub_time) {
		this.pub_time = pub_time;
	}

	/**
	 * @return the model_id
	 */
	public Integer getModel_id() {
		return model_id;
	}

	/**
	 * @param model_id
	 *            the model_id to set
	 */
	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	/**
	 * @return the work_count
	 */
	public Integer getWork_count() {
		return work_count;
	}

	/**
	 * @param work_count
	 *            the work_count to set
	 */
	public void setWork_count(Integer work_count) {
		this.work_count = work_count;
	}

	/**
	 * @return the task_status
	 */
	public Integer getTask_status() {
		return task_status;
	}

	/**
	 * @param task_status
	 *            the task_status to set
	 */
	public void setTask_status(Integer task_status) {
		this.task_status = task_status;
	}

	/**
	 * @return the task_title
	 */
	public String getTask_title() {
		return task_title;
	}

	/**
	 * @param task_title
	 *            the task_title to set
	 */
	public void setTask_title(String task_title) {
		this.task_title = task_title;
	}

	/**
	 * @return the task_desc
	 */
	public String getTask_desc() {
		return task_desc;
	}

	/**
	 * @param task_desc
	 *            the task_desc to set
	 */
	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}

	/**
	 * @return the indus_id
	 */
	public Integer getIndus_id() {
		return indus_id;
	}

	/**
	 * @param indus_id
	 *            the indus_id to set
	 */
	public void setIndus_id(Integer indus_id) {
		this.indus_id = indus_id;
	}

	/**
	 * @return the indus_pid
	 */
	public Integer getIndus_pid() {
		return indus_pid;
	}

	/**
	 * @param indus_pid
	 *            the indus_pid to set
	 */
	public void setIndus_pid(Integer indus_pid) {
		this.indus_pid = indus_pid;
	}

	/**
	 * @return the view_num
	 */
	public Integer getView_num() {
		return view_num;
	}

	/**
	 * @param view_num
	 *            the view_num to set
	 */
	public void setView_num(Integer view_num) {
		this.view_num = view_num;
	}

	/**
	 * @return the work_num
	 */
	public Integer getWork_num() {
		return work_num;
	}

	/**
	 * @param work_num
	 *            the work_num to set
	 */
	public void setWork_num(Integer work_num) {
		this.work_num = work_num;
	}

	/**
	 * @return the pay_item
	 */
	public String getPay_item() {
		return pay_item;
	}

	/**
	 * @param pay_item
	 *            the pay_item to set
	 */
	public void setPay_item(String pay_item) {
		this.pay_item = pay_item;
	}

	/**
	 * @return the is_delay
	 */
	public Integer getIs_delay() {
		return is_delay;
	}

	/**
	 * @param is_delay
	 *            the is_delay to set
	 */
	public void setIs_delay(Integer is_delay) {
		this.is_delay = is_delay;
	}

	/**
	 * @return the is_top
	 */
	public Integer getIs_top() {
		return is_top;
	}

	/**
	 * @param is_top
	 *            the is_top to set
	 */
	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}

	/**
	 * @return the no_show
	 */
	public Integer getNo_show() {
		return no_show;
	}

	/**
	 * @param no_show
	 *            the no_show to set
	 */
	public void setNo_show(Integer no_show) {
		this.no_show = no_show;
	}

	/**
	 * @return the point
	 */
	public String getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(String point) {
		this.point = point;
	}

	/**
	 * @return the task_type
	 */
	public Integer getTask_type() {
		return task_type;
	}

	/**
	 * @param task_type
	 *            the task_type to set
	 */
	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	/**
	 * @return the cash_status
	 */
	public Integer getCash_status() {
		return cash_status;
	}

	/**
	 * @param cash_status
	 *            the cash_status to set
	 */
	public void setCash_status(Integer cash_status) {
		this.cash_status = cash_status;
	}

	/**
	 * @return the focus_num
	 */
	public Integer getFocus_num() {
		return focus_num;
	}

	/**
	 * @param focus_num
	 *            the focus_num to set
	 */
	public void setFocus_num(Integer focus_num) {
		this.focus_num = focus_num;
	}

	/**
	 * @return the logo_type
	 */
	public String getLogo_type() {
		return logo_type;
	}

	/**
	 * @param logo_type
	 *            the logo_type to set
	 */
	public void setLogo_type(String logo_type) {
		this.logo_type = logo_type;
	}

	/**
	 * @return the logo_tc
	 */
	public Integer getLogo_tc() {
		return logo_tc;
	}

	/**
	 * @param logo_tc
	 *            the logo_tc to set
	 */
	public void setLogo_tc(Integer logo_tc) {
		this.logo_tc = logo_tc;
	}

	/**
	 * @return the w_uid
	 */
	public Integer getW_uid() {
		return w_uid;
	}

	/**
	 * @param w_uid
	 *            the w_uid to set
	 */
	public void setW_uid(Integer w_uid) {
		this.w_uid = w_uid;
	}

	/**
	 * @return the w_username
	 */
	public String getW_username() {
		return w_username;
	}

	/**
	 * @param w_username
	 *            the w_username to set
	 */
	public void setW_username(String w_username) {
		this.w_username = w_username;
	}

	/**
	 * @return the w_bid_time
	 */
	public Integer getW_bid_time() {
		return w_bid_time;
	}

	/**
	 * @param w_bid_time
	 *            the w_bid_time to set
	 */
	public void setW_bid_time(Integer w_bid_time) {
		this.w_bid_time = w_bid_time;
	}

	/**
	 * @return the task_pay_time
	 */
	public Integer getTask_pay_time() {
		return task_pay_time;
	}

	/**
	 * @param task_pay_time
	 *            the task_pay_time to set
	 */
	public void setTask_pay_time(Integer task_pay_time) {
		this.task_pay_time = task_pay_time;
	}

	/**
	 * @return the payitem_time
	 */
	public String getPayitem_time() {
		return payitem_time;
	}

	/**
	 * @param payitem_time
	 *            the payitem_time to set
	 */
	public void setPayitem_time(String payitem_time) {
		this.payitem_time = payitem_time;
	}

	/**
	 * @return the service_id
	 */
	public Integer getService_id() {
		return service_id;
	}

	/**
	 * @param service_id
	 *            the service_id to set
	 */
	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}

	/**
	 * @return the g_id
	 */
	public Integer getG_id() {
		return g_id;
	}

	/**
	 * @param g_id
	 *            the g_id to set
	 */
	public void setG_id(Integer g_id) {
		this.g_id = g_id;
	}

	/**
	 * @return the w_integrity
	 */
	public long getW_integrity() {
		return w_integrity;
	}

	/**
	 * @param w_integrity
	 *            the w_integrity to set
	 */
	public void setW_integrity(long w_integrity) {
		this.w_integrity = w_integrity;
	}

}