package com.epweike.model;

import javax.persistence.*;

@Table(name = "dic_basic")
public class DicBasic extends BaseModel {
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
    @Column(name = "w_frequency")
    private Integer wFrequency;

    /**
     * N:名词;V:动词;ADJ:形容词;ADV:副词;CLAS:量词;ECHO:拟声词;STRU:结构助词;AUX:助词;COOR:并列连词;CONJ:连词;SUFFIX:前缀;PREFIX:后缀;PREP:介词;PRON:代词;QUES:疑问词;NUM:数词;IDIOM:成语
     */
    @Column(name = "w_class")
    private String wClass;

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
     * @return w_frequency - 词频
     */
    public Integer getwFrequency() {
        return wFrequency;
    }

    /**
     * 设置词频
     *
     * @param wFrequency 词频
     */
    public void setwFrequency(Integer wFrequency) {
        this.wFrequency = wFrequency;
    }

    /**
     * 获取N:名词;V:动词;ADJ:形容词;ADV:副词;CLAS:量词;ECHO:拟声词;STRU:结构助词;AUX:助词;COOR:并列连词;CONJ:连词;SUFFIX:前缀;PREFIX:后缀;PREP:介词;PRON:代词;QUES:疑问词;NUM:数词;IDIOM:成语
     *
     * @return w_class - N:名词;V:动词;ADJ:形容词;ADV:副词;CLAS:量词;ECHO:拟声词;STRU:结构助词;AUX:助词;COOR:并列连词;CONJ:连词;SUFFIX:前缀;PREFIX:后缀;PREP:介词;PRON:代词;QUES:疑问词;NUM:数词;IDIOM:成语
     */
    public String getwClass() {
        return wClass;
    }

    /**
     * 设置N:名词;V:动词;ADJ:形容词;ADV:副词;CLAS:量词;ECHO:拟声词;STRU:结构助词;AUX:助词;COOR:并列连词;CONJ:连词;SUFFIX:前缀;PREFIX:后缀;PREP:介词;PRON:代词;QUES:疑问词;NUM:数词;IDIOM:成语
     *
     * @param wClass N:名词;V:动词;ADJ:形容词;ADV:副词;CLAS:量词;ECHO:拟声词;STRU:结构助词;AUX:助词;COOR:并列连词;CONJ:连词;SUFFIX:前缀;PREFIX:后缀;PREP:介词;PRON:代词;QUES:疑问词;NUM:数词;IDIOM:成语
     */
    public void setwClass(String wClass) {
        this.wClass = wClass;
    }
}