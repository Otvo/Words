<template>
	<view>
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>

		<view class="userinfo">
			<view class="userInfoContainer">
				<image class="userinfo-avatar" :src="avatar"></image>
			</view>
			<view class="userinfo-name">
				<text class="name">{{name}}</text>
				<text class="vipEndTime" v-if="isVip&!isForever">会员将于 <text
						style="color: rgb(253,123,19); font-weight: bold;">{{vipEndTime}}</text>到期</text>
				<text class="vipEndTime" v-if="!isVip">暂未开通会员</text>
				<text class="vipEndTime" v-if="isForever"
						style="color: rgb(253,123,19); font-weight: bold;">终身会员</text></text>
			</view>

		</view>

		<view v-if="!isForever">
			<view class="ctxCard">
				<view class="title">
					<text>开通/续费</text>
				</view>
			</view>
			<view class="vipbut">
				<button class="button" @click="openVip(['9.9','单月会员','31'])"><text class="button-text">单月会员</text></button>
				<button class="button" @click="openVip(['99','年费会员','365'])"><text class="button-text">年费会员</text></button>
				<button class="button" @click="openVip(['299','终身会员','430700'])"><text class="button-text">终身会员</text></button>
			</view>
		</view>
		
		<view class="ctxCard">
			<view class="title">
				<text>开通记录</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				avatar: getApp().globalData.avatar, //用户头像
				isVip: getApp().globalData.userInformation.isVip,
				name: getApp().globalData.userInformation.name,
				vipEndTime: getApp().globalData.userInformation.vipEndTime,
				windowTop: "",
				orderInfo: "",

			}
		},
		onShow() {
			var that=this;
			uni.request({
				url: getApp().globalData.URL + "/user/getVipState",
				data: {
					userID: getApp().globalData.userInformation.userID
				},
				success(res) {
					console.log(res)
					var date = new Date(res.data.vipEndTime)
					var YY = date.getFullYear() + '-';
					var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					that.vipEndTime = YY + MM + DD;
					if(date.getFullYear()>2100){
						that.isForever=true
					}
					that.isVip=res.data.isVip
				},
				fail: function(err) {
					console.log(err)
				}
			})
		},
		onLoad() {
			// 支付宝沙箱环境
			var EnvUtils = plus.android.importClass("com.alipay.sdk.app.EnvUtils");
			EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
			var that = this
			uni.getSystemInfo({
				success(e) {
					that.windowTop = "height:" + e.windowTop + "px";
					that.windowHeight = "height:" + (e.windowHeight) + "px";
				}
			})

		},
		methods: {
			openVip(type) {
				var that = this
				var date = new Date();

				uni.request({
					url: getApp().globalData.URL + '/alipay/alipay',
					data: {
						amount: type[0],
						tradeNo: date.getTime().toString(),
						context: "全民单词——" + type[1]
					},
					success: (res) => {
						// console.log(res)
						// that.orderInfo = res.data
						uni.requestPayment({
							provider: 'alipay',
							orderInfo: res.data, //微信、支付宝订单数据 【注意微信的订单信息，键值应该全部是小写，不能采用驼峰命名】
							success: function(res) {
								console.log('success:' + JSON.stringify(res));
								uni.request({
									url: getApp().globalData.URL + '/user/openVip',
									data: {
										userID: getApp().globalData.userInformation.userID,
										vipStartTime: date,
										vipLength: type[2]
									},
									success() {
										uni.navigateBack({
											delta: 1
										})
										uni.showToast({
											title: '会员开通成功',
											duration: 1000
										});
									},
									fail: function(err) {
										console.log('fail:' + JSON.stringify(err));
									}
								})
							},
							fail: function(err) {
								console.log('fail:' + JSON.stringify(err));
							}
						});
					},
					fail: function(err) {
						console.log('fail:' + JSON.stringify(err));
					}
				})


			}
		}
	}
</script>

<style>
	.userinfo {
		position: relative;
		display: flex;
		flex-direction: row;
		text-align: justify;

		width: 94%;
		margin-left: 3%;
		border-radius: 15rpx;
		padding-top: 10rpx;
		padding-bottom: 10rpx;
		background-color: white;
	}

	.userInfoContainer {
		position: relative;
		left: 5%;
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
	}

	.userinfo-avatar {
		border: 2px solid rgba(195, 199, 199, 0.7);
		position: relative;
		overflow: hidden;
		width: 108rpx;
		height: 108rpx;
		border-radius: 50%;
		background-color: white;
		left: 5%;

	}

	.userinfo-name {
		position: relative;
		margin-left: 10%;
		margin-top: 3%;
		display: flex;
		flex-direction: column;
	}

	.vipEndTime {
		font-size: small;
	}

	.vipbut {
		display: flex;
		flex-direction: row;
	}

	.button {
		font-size: medium;
	}

	.ctxCard {
		position: relative;
		width: 94%;
		margin-left: 3%;

	}

	.title {
		position: relative;
		margin-top: 4%;
		margin-bottom: 1%;
		color: white;
		font-weight: bold;
	}
</style>
