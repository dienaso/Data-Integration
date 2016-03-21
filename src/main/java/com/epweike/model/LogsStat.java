package com.epweike.model;

import javax.persistence.*;

@Table(name = "logs_stat")
public class LogsStat {
    @Column(name = "on_time")
    private Integer onTime;

    private Integer pv;

    private Integer reguser;

    private Integer ip;

    private Integer jumper;

    private Integer spider;

    @Column(name = "windows_2000")
    private Integer windows2000;

    @Column(name = "windows_xp")
    private Integer windowsXp;

    @Column(name = "windows_vista")
    private Integer windowsVista;

    @Column(name = "windows_7")
    private Integer windows7;

    @Column(name = "windows_81")
    private Integer windows81;

    @Column(name = "windows_10")
    private Integer windows10;

    private Integer mac;

    private Integer linux;

    private Integer android;

    private Integer ios;

    private Integer otheros;

    /**
     * @return on_time
     */
    public Integer getOnTime() {
        return onTime;
    }

    /**
     * @param onTime
     */
    public void setOnTime(Integer onTime) {
        this.onTime = onTime;
    }

    /**
     * @return pv
     */
    public Integer getPv() {
        return pv;
    }

    /**
     * @param pv
     */
    public void setPv(Integer pv) {
        this.pv = pv;
    }

    /**
     * @return reguser
     */
    public Integer getReguser() {
        return reguser;
    }

    /**
     * @param reguser
     */
    public void setReguser(Integer reguser) {
        this.reguser = reguser;
    }

    /**
     * @return ip
     */
    public Integer getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(Integer ip) {
        this.ip = ip;
    }

    /**
     * @return jumper
     */
    public Integer getJumper() {
        return jumper;
    }

    /**
     * @param jumper
     */
    public void setJumper(Integer jumper) {
        this.jumper = jumper;
    }

    /**
     * @return spider
     */
    public Integer getSpider() {
        return spider;
    }

    /**
     * @param spider
     */
    public void setSpider(Integer spider) {
        this.spider = spider;
    }

    /**
     * @return windows_2000
     */
    public Integer getWindows2000() {
        return windows2000;
    }

    /**
     * @param windows2000
     */
    public void setWindows2000(Integer windows2000) {
        this.windows2000 = windows2000;
    }

    /**
     * @return windows_xp
     */
    public Integer getWindowsXp() {
        return windowsXp;
    }

    /**
     * @param windowsXp
     */
    public void setWindowsXp(Integer windowsXp) {
        this.windowsXp = windowsXp;
    }

    /**
     * @return windows_vista
     */
    public Integer getWindowsVista() {
        return windowsVista;
    }

    /**
     * @param windowsVista
     */
    public void setWindowsVista(Integer windowsVista) {
        this.windowsVista = windowsVista;
    }

    /**
     * @return windows_7
     */
    public Integer getWindows7() {
        return windows7;
    }

    /**
     * @param windows7
     */
    public void setWindows7(Integer windows7) {
        this.windows7 = windows7;
    }

    /**
     * @return windows_81
     */
    public Integer getWindows81() {
        return windows81;
    }

    /**
     * @param windows81
     */
    public void setWindows81(Integer windows81) {
        this.windows81 = windows81;
    }

    /**
     * @return windows_10
     */
    public Integer getWindows10() {
        return windows10;
    }

    /**
     * @param windows10
     */
    public void setWindows10(Integer windows10) {
        this.windows10 = windows10;
    }

    /**
     * @return mac
     */
    public Integer getMac() {
        return mac;
    }

    /**
     * @param mac
     */
    public void setMac(Integer mac) {
        this.mac = mac;
    }

    /**
     * @return linux
     */
    public Integer getLinux() {
        return linux;
    }

    /**
     * @param linux
     */
    public void setLinux(Integer linux) {
        this.linux = linux;
    }

    /**
     * @return android
     */
    public Integer getAndroid() {
        return android;
    }

    /**
     * @param android
     */
    public void setAndroid(Integer android) {
        this.android = android;
    }

    /**
     * @return ios
     */
    public Integer getIos() {
        return ios;
    }

    /**
     * @param ios
     */
    public void setIos(Integer ios) {
        this.ios = ios;
    }

    /**
     * @return otheros
     */
    public Integer getOtheros() {
        return otheros;
    }

    /**
     * @param otheros
     */
    public void setOtheros(Integer otheros) {
        this.otheros = otheros;
    }
}