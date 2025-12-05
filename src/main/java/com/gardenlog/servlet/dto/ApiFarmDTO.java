package com.gardenlog.servlet.dto;

public class ApiFarmDTO {
	
	private String title;   // 농장 이름
    private String address; // 도로명 주소 또는 지번 주소
    private String link;    // 상세 정보 URL (사용하지 않을 수도 있지만 일단 포함)
    private String mapx;    // 네이버 TM 좌표계 X (경도 역할)
    private String mapy;    // 네이버 TM 좌표계 Y (위도 역할)
    
    public ApiFarmDTO() {
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMapx() {
		return mapx;
	}

	public void setMapx(String mapx) {
		this.mapx = mapx;
	}

	public String getMapy() {
		return mapy;
	}

	public void setMapy(String mapy) {
		this.mapy = mapy;
	}
    
	@Override
    public String toString() {
        return "FarmInfo{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", mapx='" + mapx + '\'' +
                ", mapy='" + mapy + '\'' +
                '}';
    }
}
