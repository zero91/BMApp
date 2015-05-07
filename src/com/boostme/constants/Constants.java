package com.boostme.constants;


public interface Constants {
	String SESSIONID = "sessionid";// "bm_sid"
	String AUTH = "bm_auth";
	String DB_NAME = "boostme";
	boolean IS_DEBUG = true;

	String ERROR_NO = "errno";
	int SUCCESS = 0;
	int FAIL = 1;

	String BASE_URL = "http://www.boostme.cn:9507";

	String TRADE_STATUS_WAIT_BUYER_PAY = "等待买家付款 ";
	String TRADE_STATUS_WAIT_SELLER_SEND_GOODS = "买家已付款,等待卖家发货 ";
	String TRADE_STATUS_WAIT_BUYER_CONFIRM_GOODS = "卖家已发货,等待买家确认";
	String TRADE_STATUS_FINISHED = "交易成功结束 ";
	String TRADE_STATUS_CLOSED = "交易中途关闭";
	String[] TRADE_STATUS_LIST = { TRADE_STATUS_WAIT_BUYER_PAY,
			TRADE_STATUS_WAIT_SELLER_SEND_GOODS,
			TRADE_STATUS_WAIT_BUYER_CONFIRM_GOODS, TRADE_STATUS_FINISHED,
			TRADE_STATUS_CLOSED };

}
