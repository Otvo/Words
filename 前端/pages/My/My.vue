<template>
	<view>
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>

		<scroll-view scroll-y :style="windowHeight">
			<view class="userinfo">
				<view class="userInfoContainer" v-if="hasLogedIn"  @click="toInfoSet">
					<image class="userinfo-avatar" :src="avatar"></image>
				</view>
				<view class="userinfo-name"  @click="toInfoSet">
					<text class="name">{{name}}</text>
				</view>
				<view class="openvip" v-if="isVip" @click="toVip">
					<text class="vip">我的vip</text>
				</view>
				<view class="openvip-no" v-else @click="toVip">
					<text class="vip-no">开通vip</text>
				</view>
				<view class="infoSet" @click="toInfoSet">
					<uni-icons type="arrowright" size="15" color="grey"></uni-icons>
				</view>
			</view>

			<view class="ctxCard">
				<view class="title">
					<text>学习设置 </text>
				</view>
				<view class="SetCard">
					<view class="topCtx">
						<text class="content">每日学习单词量</text>
						<input class="dailyStudyNum" type="number" :value="studyNum" @confirm="changeStudyNum" />
					</view>
					<view class="ctx">
						<text class="content">切换美式英式发音</text>
						<view class="changePronun">
							<picker mode="selector" :range='array' :value="index" @change="bindPronunChange">
								{{array[index]}}
							</picker>
						</view>
					</view>
					<view class="remindCtx">
						<view class="ctx">
							<text class="content"> 是否每日提醒</text>
							<switch class="reminderOrNot" :checked="isReminder" @change="bindReminderChange"
								color="rgb(126, 165, 241)"></switch>
						</view>
						<view v-if='isReminder' class="ctx">
							<text class="content">每日复习提醒时间</text>
							<view class="reminder">
								<picker mode="time" :value="time" @change="bindTimeChange">
									{{time}}
								</picker>
							</view>
						</view>
					</view>
				</view>
			</view>

			<view class="ctxCard">
				<view class="title">
					<text>系统设置</text>
				</view>
				<view class="SetCard">
					<view class="topCtx">
						<text class="content">评价应用</text>
						<text class="version">v1.0.0</text>
					</view>
					<view class="ctx">
						<text class="content">帮助中心</text>
					</view>
					<view class="ctx" @click="shareToggle">
						<text class="content">推荐给好友</text>
					</view>
					<view class="ctx">
						<text class="content"> 服务条款</text>
					</view>
					<view class="bottomCtx" @click="dialogToggle('error')">
						<text class="content">注销账户</text>
					</view>
				</view>
			</view>

			<view class="btn">
				<button class="exitButton" @click="bindExit">退出登录</button>
			</view>

			<view>
				<!-- 分享示例 -->
				<uni-popup ref="share" background-color="#fff" type="share">
					<uni-popup-share></uni-popup-share>
				</uni-popup>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				avatar: getApp().globalData.avatar, //用户头像
				windowTop: "",
				isReminder: false,
				name: getApp().globalData.userInformation.name,
				time: '09:00',
				isVip:getApp().globalData.userInformation.isVip,
				hasLogedIn:getApp().globalData.hasLogedIn,
				array: ['美式', '英式'],
				index: 0,
				studyNum: getApp().globalData.userInformation.learnNum,
				reviewNum: getApp().globalData.userInformation.reviewNum
			}
		},
		onLoad() {
			var that = this
			uni.getSystemInfo({
					success(e) {
						that.windowTop = "height:" + e.windowTop + "px";
						that.windowHeight = "height:" + (e.windowHeight) + "px";
					}
				}),

				that.getPronun()
			that.getReminder()
		},
		
		onShow() {
			var that = this
			uni.request({
				url:getApp().globalData.URL+"/user/getVipState",
				data:{
					userID:getApp().globalData.userInformation.userID
				},
				success(res) {
					console.log(res)
					getApp().globalData.userInformation.vipStartTime=res.vipStartTime,
					getApp().globalData.userInformation.vipLength=res.vipLength,
					getApp().globalData.userInformation.vipEndTime=res.vipEndTime,
					getApp().globalData.userInformation.isVip=res.isVip,
					that.isVip=res.data.isVip
				}
			})
		},
		
		methods: {
			toVip(){
				uni.navigateTo({
					url:"../Vip/Vip"
				})
			},
			getPronun() {
				if (getApp().globalData.userInformation.pronunciation == 'us') {
					this.index = 0
				} else {
					this.index = 1
				}
			},
			getReminder() {
				if (getApp().globalData.userInformation.node) {
					this.isReminder = true
					var date = new Date(getApp().globalData.userInformation.time)
					var hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
					var min = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
					this.time = hour + ':' + min
				} else {
					this.isReminder = false
					this.time = '09:00'
				}
			},
			toInfoSet() {
				uni.navigateTo({
					url: '../InfoSet/InfoSet'
				})
			},
			changeStudyNum(e) {
				uni.showLoading({
					title:"更改中"
				}),
				console.log(e.detail.value)
				uni.request({
					url: getApp().globalData.URL+"/user/setLearnNum",
					data: {
						userID: getApp().globalData.userInformation.userID,
						learnNum: e.detail.value
					},
					success:()=>{
						getApp().globalData.userInformation.learnNum=e.detail.value
						this.studyNum = e.detail.value
						uni.hideLoading()
					}
				})
			},
			bindPronunChange(e) {
				uni.showLoading({
					title:"更改中"
				}),
				uni.request({
					url: getApp().globalData.URL+"/user/setPronunciation",
					data: {
						userID: getApp().globalData.userInformation.userID,
						pronunciation: e.detail.value == 0 ? 'us' : 'en'
					},
					success:()=>{
						getApp().globalData.userInformation.pronunciation=e.detail.value == 0 ? 'us' : 'en'
						this.index = e.detail.value
						uni.hideLoading()
					}
				})
			},
			bindReminderChange(e) {
				console.log(e.detail.value)
				uni.showLoading({
					title:"更改中"
				}),
				uni.request({
					url: getApp().globalData.URL+"/user/setNode",
					data: {
						userID: getApp().globalData.userInformation.userID,
						node: e.detail.value
					},
					success:()=>{
						
						getApp().globalData.userInformation.node=e.detail.value
						// getApp().globalData.userInformation.time="09:00"
						this.isReminder = e.detail.value
						this.time="09:00"
						uni.hideLoading()
					}
				})
			},
			bindTimeChange(e) {
				this.time = e.detail.value
				var date = new Date();
				date.setTime(date.getTime());
				var Y = date.getFullYear();
				var M = date.getMonth();
				var D = date.getDate();
				var H=e.detail.value.substring(0,2)
				var MM=e.detail.value.substring(3,5)
				var reminderDate = new Date(Y,M,D,H,MM,0); 
				uni.showLoading({
					title:"更改中"
				}),
				
				uni.request({
					url: getApp().globalData.URL+"/user/setNodeTime",
					data: {
						userID: getApp().globalData.userInformation.userID,
						time: reminderDate
					},
					success:()=>{
						getApp().globalData.userInformation.time=reminderDate
						this.time=e.detail.value
						uni.hideLoading()
					}
				})
			},
			shareToggle() {
				this.$refs.share.open()
			},
			dialogToggle(type) {
				uni.showModal({
					title: '注销账户',
					content: '注销后所有记录都将被清空,且无法恢复!\n确定要注销吗？',
					// cancelText:"放弃注销",
					// confirmText:"确定注销",
					confirmColor:"#ff0000",
					success(res) {
						if (res.confirm){
							uni.request({
								url:getApp().globalData.URL+'/user/logout',
								data:{
									userID: getApp().globalData.userInformation.userID
								},
								success() {
									getApp().globalData.avatar='../../static/image/touxiang.png'
									uni.reLaunch({
										url:"../Login/Login"
									})
								}
							})
							
						}else if(res.cancel){}
					}
				});
			},
			bindExit() {
				getApp().globalData.avatar='../../static/image/touxiang.png'
				getApp().globalData.hasLogedIn=false
				uni.reLaunch({
					url: "../Login/Login"
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
		height: 120rpx;
		line-height: 120rpx;
	}
	
	.openvip{
		position: relative;
		margin-left: 10%;
		margin-top: 35rpx;
		height: 46rpx;
		width: 150rpx;
		
		border-radius: 30rpx;
		line-height: 50rpx;
		background-color: rgba(255, 216, 166, 0.6);
		z-index: 100;
	}
	
	.vip{
		font-size: small;
		font-weight: bold;
		margin-left: 23%;
		color: rgb(253, 123, 19);
		text-align: center;
	}
	.openvip-no{
		position: relative;
		margin-left: 10%;
		margin-top: 35rpx;
		height: 46rpx;
		width: 150rpx;
		
		border-radius: 30rpx;
		line-height: 50rpx;
		background-color: rgba(255, 216, 166, 0.6);

	}
	.vip-no{
		font-size: small;
		font-weight: bold;
		margin-left: 23%;
		color: rgb(128, 128, 128);
		text-align: center;
	}

	.infoSet {
		text-align: right;
		font-weight: bold;
		position: absolute;
		right: 30rpx;
		height: 120rpx;
		width: 200rpx;
		line-height: 120rpx;
	}

	.content {
		margin-left: 10rpx;
		margin-top: 5rpx;
		font-size: 28rpx;
		font-family: 'Courier New', Courier, monospace
	}

	.version {
		position: absolute;
		right: 30rpx;

		margin-top: 10rpx;
		font-size: 28rpx;
		color: #808080;
	}

	.ctxCard {
		position: relative;
		width: 94%;
		margin-left: 3%;

	}

	.title {
		position: relative;
		margin-top: 2%;
		color: white;
		font-weight: bold;
	}

	.SetCard {
		position: relative;
		margin-top: 5rpx;
		background-color: rgba(255, 255, 255, 1);
		border-radius: 15rpx;
	}

	.topCtx {
		position: relative;
		display: flex;
		flex-direction: row;
		text-align: justify;
		top: 10rpx;
		margin-left: 25rpx;
		padding-top: 20rpx;
		padding-bottom: 15rpx;
	}

	.bottomCtx {
		position: relative;
		display: flex;
		flex-direction: row;
		text-align: justify;
		margin-top: 30rpx;
		margin-left: 25rpx;
		padding-top: 30rpx;
		padding-bottom: 40rpx;
		border-top: solid rgba(190, 194, 194, 0.7) 1px;
	}

	.dailyStudyNum {
		position: absolute;
		width: 170rpx;
		right: 20rpx;
		border: 2rpx solid rgb(126, 165, 241);
		border-radius: 10rpx;
		font-weight: bold;
		color: rgb(126, 165, 241);
		text-align: center;
	}

	.ctx {
		position: relative;
		display: flex;
		flex-direction: row;
		text-align: justify;
		margin-top: 30rpx;
		margin-left: 25rpx;
		padding-top: 30rpx;
		padding-bottom: 10rpx;
		border-top: solid rgba(190, 194, 194, 0.7) 1px;
	}

	.dailyReviewNum {
		position: absolute;
		width: 170rpx;
		right: 20rpx;
		border: 2rpx solid rgb(126, 165, 241);
		border-radius: 10rpx;
		font-weight: bold;
		color: rgb(126, 165, 241);
		text-align: center;
	}



	.changePronun {
		position: absolute;
		background-color: rgb(126, 165, 241);
		color: white;
		width: 175rpx;
		border-radius: 10rpx;
		right: 20rpx;
		height: 50rpx;
		line-height: 50rpx;
		text-align: center;
		display: block;
		font-size: small;
	}

	.remindCtx {
		padding-bottom: 40rpx;
	}



	.reminderOrNot {
		position: absolute;
		right: 120rpx;
		margin-top: 8rpx;
		zoom: .6;
	}



	.reminder {
		position: absolute;
		background-color: rgb(126, 165, 241);
		color: white;
		width: 175rpx;
		border-radius: 10rpx;
		right: 20rpx;
		height: 50rpx;
		line-height: 50rpx;
		text-align: center;
		display: block;
	}

	.exitButton {
		color: white;
		font-weight: bold;
		margin-top: 50rpx;
		width: 70%;
		margin-bottom: 20rpx;
		background-color: rgb(253, 123, 19);
	}
	
	.btn{
		padding-bottom: 20rpx;
	}
</style>
