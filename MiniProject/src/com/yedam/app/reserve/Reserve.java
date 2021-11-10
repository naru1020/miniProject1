package com.yedam.app.reserve;

import java.sql.Date;

public class Reserve {
	private Date reserveDate;
	private String reserveType;
	private String userId;
	
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	
	public String getReserveType() {
		return reserveType;
	}
	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "예약 일시 : " + reserveDate + " , 좌석 타입 : " + reserveType + ", 회원 아이디 : " + userId;
	}

}
