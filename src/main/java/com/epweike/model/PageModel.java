/**
 * 
 */
package com.epweike.model;

import java.util.List;

/**
 * @author 吴小平
 *
 */
public class PageModel<T> extends BaseModel<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int iTotalRecords;
	 
    int iTotalDisplayRecords;
    
    int iDisplayStart;
    
	int iDisplayLength;
 
    String sEcho;
 
    String sColumns;
    
    String sSearch;
 
    List<T> aaData;
 
    public int getiTotalRecords() {
	return iTotalRecords;
    }
 
    public void setiTotalRecords(int iTotalRecords) {
	this.iTotalRecords = iTotalRecords;
    }
 
    public int getiTotalDisplayRecords() {
	return iTotalDisplayRecords;
    }
 
    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
	this.iTotalDisplayRecords = iTotalDisplayRecords;
    }
 
    public String getsEcho() {
	return sEcho;
    }
 
    public void setsEcho(String sEcho) {
	this.sEcho = sEcho;
    }
 
    public String getsColumns() {
	return sColumns;
    }
 
    public void setsColumns(String sColumns) {
	this.sColumns = sColumns;
    }
 
    public List<T> getAaData() {
        return aaData;
    }
 
    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }
    
	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	
}
