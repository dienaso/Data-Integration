package com.epweike.model.solr;

import org.apache.solr.client.solrj.beans.Field;

import com.epweike.model.BaseModel;

public class Service extends BaseModel<Service> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field
	private Integer service_id;
	@Field
	private Integer model_id;
	@Field
	private Integer service_type;
	@Field
	private Integer indus_id;
	@Field
	private Integer indus_pid;
	@Field
	private String title;
	@Field
	private Double price;
	@Field
	private Integer is_stop;
	@Field
	private Integer sale_num;
	@Field
	private Integer focus_num;
	@Field
	private Integer mark_num;
	@Field
	private Integer leave_num;
	@Field
	private Integer views;
	@Field
	private String pay_item;
	@Field
	private Float total_sale;
	@Field
	private Integer service_status;
	@Field
	private Integer is_top;
	@Field
	private Integer listorder;
	@Field
	private Integer recommend_listorder;
	@Field
	private Integer catid;
	@Field
	private String type;
	
	/**
	 * @return the service_id
	 */
	public Integer getService_id() {
		return service_id;
	}
	/**
	 * @param service_id the service_id to set
	 */
	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}
	/**
	 * @return the model_id
	 */
	public Integer getModel_id() {
		return model_id;
	}
	/**
	 * @param model_id the model_id to set
	 */
	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}
	/**
	 * @return the service_type
	 */
	public Integer getService_type() {
		return service_type;
	}
	/**
	 * @param service_type the service_type to set
	 */
	public void setService_type(Integer service_type) {
		this.service_type = service_type;
	}
	/**
	 * @return the indus_id
	 */
	public Integer getIndus_id() {
		return indus_id;
	}
	/**
	 * @param indus_id the indus_id to set
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
	 * @param indus_pid the indus_pid to set
	 */
	public void setIndus_pid(Integer indus_pid) {
		this.indus_pid = indus_pid;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the is_stop
	 */
	public Integer getIs_stop() {
		return is_stop;
	}
	/**
	 * @param is_stop the is_stop to set
	 */
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	/**
	 * @return the sale_num
	 */
	public Integer getSale_num() {
		return sale_num;
	}
	/**
	 * @param sale_num the sale_num to set
	 */
	public void setSale_num(Integer sale_num) {
		this.sale_num = sale_num;
	}
	/**
	 * @return the focus_num
	 */
	public Integer getFocus_num() {
		return focus_num;
	}
	/**
	 * @param focus_num the focus_num to set
	 */
	public void setFocus_num(Integer focus_num) {
		this.focus_num = focus_num;
	}
	/**
	 * @return the mark_num
	 */
	public Integer getMark_num() {
		return mark_num;
	}
	/**
	 * @param mark_num the mark_num to set
	 */
	public void setMark_num(Integer mark_num) {
		this.mark_num = mark_num;
	}
	/**
	 * @return the leave_num
	 */
	public Integer getLeave_num() {
		return leave_num;
	}
	/**
	 * @param leave_num the leave_num to set
	 */
	public void setLeave_num(Integer leave_num) {
		this.leave_num = leave_num;
	}
	/**
	 * @return the views
	 */
	public Integer getViews() {
		return views;
	}
	/**
	 * @param views the views to set
	 */
	public void setViews(Integer views) {
		this.views = views;
	}
	/**
	 * @return the pay_item
	 */
	public String getPay_item() {
		return pay_item;
	}
	/**
	 * @param pay_item the pay_item to set
	 */
	public void setPay_item(String pay_item) {
		this.pay_item = pay_item;
	}
	/**
	 * @return the total_sale
	 */
	public Float getTotal_sale() {
		return total_sale;
	}
	/**
	 * @param total_sale the total_sale to set
	 */
	public void setTotal_sale(Float total_sale) {
		this.total_sale = total_sale;
	}
	/**
	 * @return the service_status
	 */
	public Integer getService_status() {
		return service_status;
	}
	/**
	 * @param service_status the service_status to set
	 */
	public void setService_status(Integer service_status) {
		this.service_status = service_status;
	}
	/**
	 * @return the is_top
	 */
	public Integer getIs_top() {
		return is_top;
	}
	/**
	 * @param is_top the is_top to set
	 */
	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}
	/**
	 * @return the listorder
	 */
	public Integer getListorder() {
		return listorder;
	}
	/**
	 * @param listorder the listorder to set
	 */
	public void setListorder(Integer listorder) {
		this.listorder = listorder;
	}
	/**
	 * @return the recommend_listorder
	 */
	public Integer getRecommend_listorder() {
		return recommend_listorder;
	}
	/**
	 * @param recommend_listorder the recommend_listorder to set
	 */
	public void setRecommend_listorder(Integer recommend_listorder) {
		this.recommend_listorder = recommend_listorder;
	}
	/**
	 * @return the catid
	 */
	public Integer getCatid() {
		return catid;
	}
	/**
	 * @param catid the catid to set
	 */
	public void setCatid(Integer catid) {
		this.catid = catid;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}