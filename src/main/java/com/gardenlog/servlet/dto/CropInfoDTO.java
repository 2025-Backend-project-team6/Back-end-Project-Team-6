package com.gardenlog.servlet.dto;

public class CropInfoDTO {

    private String cropCode;   // 작물 코드: A001 등
    private int section;       // section0.xml → 0
    private int orderNo;       // 문단/셀 순서 번호
    private String content;    // 문단 텍스트 또는 셀 텍스트

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
