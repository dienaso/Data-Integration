package com.epweike.model;

import javax.persistence.*;

@Table(name = "webpage")
public class Webpage extends BaseModel<Webpage> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

    private Integer status;

    @Column(name = "modifiedTime")
    private Long modifiedtime;

    @Column(name = "prevModifiedTime")
    private Long prevmodifiedtime;

    private Float score;

    private String typ;

    @Column(name = "batchId")
    private String batchid;

    @Column(name = "baseUrl")
    private String baseurl;

    private String title;

    @Column(name = "reprUrl")
    private String reprurl;

    @Column(name = "fetchInterval")
    private Integer fetchinterval;

    @Column(name = "prevFetchTime")
    private Long prevfetchtime;

    @Column(name = "fetchTime")
    private Long fetchtime;

    @Column(name = "retriesSinceFetch")
    private Integer retriessincefetch;

    private byte[] headers;

    private String text;

    private byte[] markers;

    @Column(name = "parseStatus")
    private byte[] parsestatus;

    private byte[] content;

    private byte[] inlinks;

    @Column(name = "prevSignature")
    private byte[] prevsignature;

    private byte[] outlinks;

    @Column(name = "protocolStatus")
    private byte[] protocolstatus;

    private byte[] signature;

    private byte[] metadata;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return modifiedTime
     */
    public Long getModifiedtime() {
        return modifiedtime;
    }

    /**
     * @param modifiedtime
     */
    public void setModifiedtime(Long modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    /**
     * @return prevModifiedTime
     */
    public Long getPrevmodifiedtime() {
        return prevmodifiedtime;
    }

    /**
     * @param prevmodifiedtime
     */
    public void setPrevmodifiedtime(Long prevmodifiedtime) {
        this.prevmodifiedtime = prevmodifiedtime;
    }

    /**
     * @return score
     */
    public Float getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * @return typ
     */
    public String getTyp() {
        return typ;
    }

    /**
     * @param typ
     */
    public void setTyp(String typ) {
        this.typ = typ;
    }

    /**
     * @return batchId
     */
    public String getBatchid() {
        return batchid;
    }

    /**
     * @param batchid
     */
    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    /**
     * @return baseUrl
     */
    public String getBaseurl() {
        return baseurl;
    }

    /**
     * @param baseurl
     */
    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return reprUrl
     */
    public String getReprurl() {
        return reprurl;
    }

    /**
     * @param reprurl
     */
    public void setReprurl(String reprurl) {
        this.reprurl = reprurl;
    }

    /**
     * @return fetchInterval
     */
    public Integer getFetchinterval() {
        return fetchinterval;
    }

    /**
     * @param fetchinterval
     */
    public void setFetchinterval(Integer fetchinterval) {
        this.fetchinterval = fetchinterval;
    }

    /**
     * @return prevFetchTime
     */
    public Long getPrevfetchtime() {
        return prevfetchtime;
    }

    /**
     * @param prevfetchtime
     */
    public void setPrevfetchtime(Long prevfetchtime) {
        this.prevfetchtime = prevfetchtime;
    }

    /**
     * @return fetchTime
     */
    public Long getFetchtime() {
        return fetchtime;
    }

    /**
     * @param fetchtime
     */
    public void setFetchtime(Long fetchtime) {
        this.fetchtime = fetchtime;
    }

    /**
     * @return retriesSinceFetch
     */
    public Integer getRetriessincefetch() {
        return retriessincefetch;
    }

    /**
     * @param retriessincefetch
     */
    public void setRetriessincefetch(Integer retriessincefetch) {
        this.retriessincefetch = retriessincefetch;
    }

    /**
     * @return headers
     */
    public byte[] getHeaders() {
        return headers;
    }

    /**
     * @param headers
     */
    public void setHeaders(byte[] headers) {
        this.headers = headers;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return markers
     */
    public byte[] getMarkers() {
        return markers;
    }

    /**
     * @param markers
     */
    public void setMarkers(byte[] markers) {
        this.markers = markers;
    }

    /**
     * @return parseStatus
     */
    public byte[] getParsestatus() {
        return parsestatus;
    }

    /**
     * @param parsestatus
     */
    public void setParsestatus(byte[] parsestatus) {
        this.parsestatus = parsestatus;
    }

    /**
     * @return content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return inlinks
     */
    public byte[] getInlinks() {
        return inlinks;
    }

    /**
     * @param inlinks
     */
    public void setInlinks(byte[] inlinks) {
        this.inlinks = inlinks;
    }

    /**
     * @return prevSignature
     */
    public byte[] getPrevsignature() {
        return prevsignature;
    }

    /**
     * @param prevsignature
     */
    public void setPrevsignature(byte[] prevsignature) {
        this.prevsignature = prevsignature;
    }

    /**
     * @return outlinks
     */
    public byte[] getOutlinks() {
        return outlinks;
    }

    /**
     * @param outlinks
     */
    public void setOutlinks(byte[] outlinks) {
        this.outlinks = outlinks;
    }

    /**
     * @return protocolStatus
     */
    public byte[] getProtocolstatus() {
        return protocolstatus;
    }

    /**
     * @param protocolstatus
     */
    public void setProtocolstatus(byte[] protocolstatus) {
        this.protocolstatus = protocolstatus;
    }

    /**
     * @return signature
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * @param signature
     */
    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    /**
     * @return metadata
     */
    public byte[] getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     */
    public void setMetadata(byte[] metadata) {
        this.metadata = metadata;
    }
}