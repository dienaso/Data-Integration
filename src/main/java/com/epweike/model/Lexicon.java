package com.epweike.model;

import javax.persistence.*;

public class Lexicon extends BaseModel<Lexicon> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    private String word;

    /**
     * 词频
     */
    private Integer frequency;

    /**
     * 词性
     */
    private String pos;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 近义词
     */
    private String synonym;
    /**
     * 分类
     */
    private String category;
    
    public Lexicon() {
	}

	/**
	 * @param sSearch
	 */
	public Lexicon(String sSearch) {
		this.word = sSearch;
	}

	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * 获取词频
     *
     * @return frequency - 词频
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * 设置词频
     *
     * @param frequency 词频
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    /**
     * 获取词性
     *
     * @return pos - 词性
     */
    public String getPos() {
        return pos;
    }

    /**
     * 设置词性
     *
     * @param pos 词性
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * 获取拼音
     *
     * @return pinyin - 拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置拼音
     *
     * @param pinyin 拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * 获取近义词
     *
     * @return synonym - 近义词
     */
    public String getSynonym() {
        return synonym;
    }

    /**
     * 设置近义词
     *
     * @param synonym 近义词
     */
    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }
    
    /**
     * 获取分类
     *
     * @return category - 分类
     */
 	public String getCategory() {
 		return category;
 	}

 	/**
     * 设置近分类
     *
     * @param category 分类
     */
 	public void setCategory(String category) {
 		this.category = category;
 	}
}