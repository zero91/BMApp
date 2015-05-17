package com.boostme.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class TradeInfoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("trade_no")
	private String tradeNo;

	@SerializedName("target_id")
	private String targetId;

	@SerializedName("type")
	private int type;

	@SerializedName("buy_num")
	private int buyNum;

	@SerializedName("uid")
	private String uid;

	@SerializedName("target_info")
	private TargetInfo targetInfo;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public TargetInfo getTargetInfo() {
		return targetInfo;
	}

	public void setTargetInfo(TargetInfo targetInfo) {
		this.targetInfo = targetInfo;
	}

	public double getTargetPrices() {
		
		double prices = targetInfo.getTargetPrice() * buyNum;
		BigDecimal bd = new BigDecimal(prices).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		prices = bd.doubleValue();
		return prices;
	}

	public String getTargetName() {
		return targetInfo.getTargetName();
	}

	public double getTargetPrice() {
		return targetInfo.getTargetPrice();
	}

	public String getTargetContent() {
		if (type == 2)
			return "[服务]: " + targetInfo.getTargetContentService()
					+ "\n[服务时长]: " + targetInfo.getTargetServiceTime() + "分钟";
		else
			return "[资料]: " + targetInfo.getTargetContentTitle();
	}

	public String toString() {

		return "TradeInfoEntity [no=" + tradeNo + ", targetId=" + targetId
				+ ", type=" + type + ", buyNum=" + buyNum + ", uid=" + uid
				+ ", targetInfo=" + targetInfo.toString() + "]";
	}

	static class TargetInfo implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SerializedName("username")
		private String targetName;

		@SerializedName("price")
		private double targetPrice;

		@SerializedName("service_content")
		private String targetContentService;

		@SerializedName("title")
		private String targetContentTitle;

		@SerializedName("service_time")
		private String targetServiceTime;

		public String getTargetName() {
			return targetName;
		}

		public void setTargetName(String targetName) {
			this.targetName = targetName;
		}

		public double getTargetPrice() {
			return targetPrice;
		}

		public void setTargetPrice(double targetPrice) {
			this.targetPrice = targetPrice;
		}

		public String getTargetContentService() {
			return targetContentService;
		}

		public void setTargetContentService(String targetContentService) {
			this.targetContentService = targetContentService;
		}

		public String getTargetContentTitle() {
			return targetContentTitle;
		}

		public void setTargetContentTitle(String targetContentTitle) {
			this.targetContentTitle = targetContentTitle;
		}

		public String getTargetServiceTime() {
			return targetServiceTime;
		}

		public void setTargetServiceTime(String targetServiceTime) {
			this.targetServiceTime = targetServiceTime;
		}

		public String toString() {

			return "TargetInfo [price=" + targetPrice + ", service="
					+ targetContentService + ", title=" + targetContentTitle
					+ ", serviceTime=" + targetServiceTime + "]";
		}
	}

}
