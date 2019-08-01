package org.shop.api.controller.base;

public enum ResMsgEnum {
	
	SUCCESS(0,"success"),
	REQUEST_PARAM_MISSING(-1,"缺少请求参数."),
	OPEN_ID_MISSING(-2,"微信OPENID缺失."),
	FAILURE(-99,"system error");

	public int CODE;
	
	public String DESC;

	public int getCODE() {
		return CODE;
	}

	public void setCODE(int CODE) {
		this.CODE = CODE;
	}

	public String getDESC() {
		return DESC;
	}

	public void setDESC(String dESC) {
		DESC = dESC;
	}

	ResMsgEnum(int CODE,String DESC){
		this.CODE = CODE;
		this.DESC = DESC;
	}
	
}
