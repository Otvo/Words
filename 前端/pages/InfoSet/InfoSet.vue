<template>
	<view>
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>

		<view class="top">
			<view class="userInfoContainer">
				<image class="userinfo-avatar" :src="avatar"></image>
			</view>
			<view class="cameraContainer">
				<image class="camera" src="../../static/image/InfoSet/camera.png"></image>
			</view>

			<!-- 输入框示例 -->
			<view>
				<uni-popup ref="inputName" type="dialog">
					<uni-popup-dialog ref="inputClose" mode="input" title="昵称" :value="name" :beforeClose="true"
						placeholder="请输入昵称" @confirm="bindNameConfirm" @close="bindNameClose"></uni-popup-dialog>
				</uni-popup>
			</view>

			<view>
				<uni-popup ref="inputEmail" type="dialog">
					<uni-popup-dialog ref="inputClose" mode="input" title="邮箱" value="" :beforeClose="true"
						placeholder="请输入邮箱" @confirm="bindEmailConfirm" @close="bindEmailClose"></uni-popup-dialog>
				</uni-popup>
			</view>

			<view>
				<uni-popup ref="inputTel" type="dialog">
					<uni-popup-dialog ref="inputClose" mode="input" title="手机" value="" :beforeClose="true"
						placeholder="请输入手机号" @confirm="bindTelConfirm" @close="bindTelClose"></uni-popup-dialog>
				</uni-popup>
			</view>
			
			<view>
				<uni-popup ref="inputCode" type="dialog">
					<uni-popup-dialog ref="inputClose" mode="input" title="验证码" value="" :beforeClose="true"
						placeholder="请输入验证码" @confirm="bindCodeConfirm" @close="bindCodeClose"></uni-popup-dialog>
				</uni-popup>
			</view>

			<view>
				<uni-popup ref="inputPwd" type="dialog">
					<uni-popup-dialog ref="inputClose" mode="input" title="修改密码" value="" :beforeClose="true"
						placeholder="请输入新密码" @confirm="bindPwdConfirm" @close="bindPwdClose"></uni-popup-dialog>
				</uni-popup>
			</view>

			<view>
				<uni-popup ref="inputPwd2" type="dialog">
					<uni-popup-dialog ref="inputClose" mode="input" title="修改密码" value="" :beforeClose="true"
						placeholder="确认新密码" @confirm="bindPwd2Confirm" @close="bindPwd2Close"></uni-popup-dialog>
				</uni-popup>
			</view>
			
			<view class="vipMod" v-if="!isVip">
				<text class="openVip-no" @click="toVip">开通vip</text>
				<uni-icons type="arrowright" size="10" color="rgb(128, 128, 128)"></uni-icons>
			</view>
			
			<view class="vipMod" v-else>
				<text class="openVip" @click="toVip">我的vip</text>
				<uni-icons type="arrowright" size="10" color="rgb(253, 123, 19)"></uni-icons>
			</view>

			<view class="topCtx" @click="bindNameChange">
				<text class="content">昵称</text>
				<view class="infoArrow">
					<text class="info" style="font-weight: normal;">{{name}}</text>
					<uni-icons type="arrowright" size="15" color="grey"></uni-icons>
				</view>
			</view>

			<view class="ctx" @click="bindEmailChange">
				<text class="content" @click="">邮箱</text>
				<view class="infoArrow">
					<text class="info" style="font-weight: normal;">{{email}}</text>
					<uni-icons type="arrowright" size="15" color="grey"></uni-icons>
				</view>
			</view>

			<view class="bottomCtx" @click="bindPwdChange">
				<text class="content">修改密码</text>
				<view class="infoArrow">
					<uni-icons type="arrowright" size="15" color="grey"></uni-icons>
				</view>
			</view>
		</view>

		<view class="bottom">
			<view class="topCtx">
				<text class="content">手机</text>
				<view class="infoArrow">
					<text class="info" v-if='isTelBind' style="font-weight: normal;" @click="cancelBindTel">{{tel}}</text>
					<text class="infoNo" v-if='!isTelBind' style="font-weight: normal;" @click="bindTelChange">立即绑定</text>
				</view>
			</view>

			<view class="ctx">
				<text class="content">QQ</text>
				<view class="infoArrow">
					<text class="info" v-if='isQqBind' style="font-weight: normal;" @click="cancelBindQq">{{qq}}</text>
					<text class="infoNo" v-if='!isQqBind' style="font-weight: normal;" @click="bindQq">立即绑定</text>
				</view>
			</view>

			<view class="ctx">
				<text class="content">微信</text>
				<view class="infoArrow">
					<text class="info" v-if='isWechatBind' style="font-weight: normal;"
						@click="cancelBindWechat">{{wechat}}</text>
					<text class="infoNo" v-if='!isWechatBind' style="font-weight: normal;"
						@click="bindWechat">立即绑定</text>
				</view>
			</view>

			<view class="bottomCtx">
				<text class="content">微博</text>
				<view class="infoArrow">
					<text class="info" v-if='isSinaBind' style="font-weight: normal;"
						@click="cancelBindSina">{{sina}}</text>
					<text class="infoNo" v-if='!isSinaBind' style="font-weight: normal;" @click="bindSina">立即绑定</text>
				</view>
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
				windowTop: "",
				name: getApp().globalData.userInformation.name,
				email: "",
				tempTel:"",
				tel: "",
				pwd: "",
				qq: "",
				wechat: "",
				sina: "",
				isSinaBind: false,
				isWechatBind: false,
				isQqBind: false,
				isTelBind:false,
				isVip:getApp().globalData.userInformation.isVip
			}
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
		onLoad() {
			var that = this
			uni.getSystemInfo({
				success(e) {
					that.windowTop = "height:" + e.windowTop + "px";
					that.windowHeight = "height:" + (e.windowHeight) + "px";
				}
			})
		},
		methods: {
			toVip(){
				uni.navigateTo({
					url:"../Vip/Vip"
				})
			},
			bindNameChange() {
				this.$refs.inputName.open()
			},
			bindNameConfirm(val) {
				uni.request({
					url: getApp().globalData.URL+"/user/updateName",
					data: {
						userID: getApp().globalData.userInformation.userID,
						name: val
					},
					success: () => {
						this.name = val
						this.$refs.inputName.close()
						uni.showToast({
							title: '修改成功',
							duration: 2000
						});
					},
					fail: () => {
						uni.showToast({
							title: '修改失败',
							icon: 'error',
							duration: 2000
						});
					}
				})
			},
			bindNameClose() {
				this.$refs.inputName.close()
			},

			bindEmailChange() {
				this.$refs.inputEmail.open()
			},
			bindEmailConfirm(val) {
				this.email = val
				this.$refs.inputEmail.close()
				uni.showToast({
					title: '修改成功',
					duration: 2000
				});
			},
			bindEmailClose() {
				this.$refs.inputEmail.close()
			},

			bindTelChange() {
				this.$refs.inputTel.open()
			},
			bindTelConfirm(val) {
				this.tempTel=val
				this.$refs.inputTel.close()
				this.$refs.inputCode.open()
				uni.showToast({
					title: '验证码已发送',
					duration: 2000
				});
			},
			bindTelClose() {
				this.$refs.inputTel.close()
			},
			
			bindCodeConfirm(val) {
				this.tel = this.tempTel
				this.isTelBind=true
				this.$refs.inputCode.close()
				uni.showToast({
					title: '修改成功',
					duration: 2000
				});
			},
			bindCodeClose() {
				this.$refs.inputCode.close()
				this.$refs.inputTel.open()
			},

			bindPwdChange() {
				this.$refs.inputPwd.open()
			},
			bindPwdConfirm(val) {
				this.pwd = val
				this.$refs.inputPwd.close()
				this.$refs.inputPwd2.open()
			},
			bindPwdClose() {
				this.$refs.inputPwd.close()
			},

			bindPwd2Change() {
				this.$refs.inputPwd2.open()
			},
			bindPwd2Confirm(val) {

				if (this.pwd == val) {
					uni.request({
						url: getApp().globalData.URL+"/user/updatepwd",
						data: {
							userID: getApp().globalData.userInformation.userID,
							password: val
						},
						success: () => {
							uni.showToast({
								title: '修改成功',
								duration: 2000
							});
							this.$refs.inputPwd2.close()
						}
					})
				} else {
					uni.showToast({
						icon: "error",
						title: '两次密码不一致',
						duration: 2000
					});
				}
			},
			bindPwd2Close() {
				this.$refs.inputPwd2.close()
				this.$refs.inputPwd.open()
			},

			bindSina() {
				var that = this;
				uni.showModal({
					title: "“全民单词”想要打开“微博”",
					success(res) {
						if (res.confirm) {
							uni.login({
								provider: 'sinaweibo',
								success: async function() {
									uni.getUserInfo({
										provider: 'sinaweibo',
										success: (res) => {
											uni.showToast({
												title: '绑定成功',
												duration: 2000
											});
											that.isSinaBind = true
											that.sina = res.userInfo.nickname
										}
									})
								}
							})
						}
					}
				})
			},

			cancelBindSina() {
				var that = this;
				uni.showModal({
					title: "确定解除微博绑定吗",
					content: "解绑后将不能使用微博登录你的账户",
					confirmText: "解绑",
					success(res) {
						if (res.confirm) {
							uni.showToast({
								title: '解绑成功',
								duration: 2000
							});
							that.isSinaBind = false
							that.sina = ''
						}
					}
				})
			},
			
			cancelBindTel(){
				var that = this;
				uni.showModal({
					title: "确定解除手机号绑定吗",
					content: "解绑后将不能使用手机号登录你的账户",
					confirmText: "解绑",
					success(res) {
						if (res.confirm) {
							uni.showToast({
								title: '解绑成功',
								duration: 2000
							});
							that.isTelBind = false
							that.tel = ''
						}
					}
				})
			},


			bindWechat() {
				var that = this;
				uni.showModal({
					title: "“全民单词”想要打开“微信”",
					success(res) {
						if (res.confirm) {
							uni.login({
								provider: 'weixin',
								success: async function() {
									uni.getUserInfo({
										provider: 'weixin',
										success: (res) => {
											uni.showToast({
												title: '绑定成功',
												duration: 2000
											});
											that.isWechatBind = true
											that.wechat = res.userInfo.nickName
										}
									})
								}
							})
						}
					}
				})
			},
			cancelBindWechat() {
				var that = this;
				uni.showModal({
					title: "确定解除微信绑定吗",
					content: "解绑后将不能使用微信登录你的账户",
					confirmText: "解绑",
					success(res) {
						if (res.confirm) {
							uni.showToast({
								title: '解绑成功',
								duration: 2000
							});
							that.isWechatBind = false
							that.wechat = ''
						}
					}
				})
			},

			bindQq() {
				var that = this;
				uni.showModal({
					title: "“全民单词”想要打开“QQ”",
					success(res) {
						if (res.confirm) {
							uni.login({
								provider: 'qq',
								success: async function() {
									uni.getUserInfo({
										provider: 'qq',
										success: (res) => {
											uni.showToast({
												title: '绑定成功',
												duration: 2000
											});
											that.isQqBind = true
											that.qq = res.userInfo.nickname
										}
									})
								}
							})
						}
					}
				})
			},
			cancelBindQq() {
				var that = this;
				uni.showModal({
					title: "确定解除QQ绑定吗",
					content: "解绑后将不能使用QQ登录你的账户",
					confirmText: "解绑",
					success(res) {
						if (res.confirm) {
							uni.showToast({
								title: '解绑成功',
								duration: 2000
							});
							that.isQqBind = false
							that.qq = ''
						}
					}
				})
			},
		},
	}
</script>

<style>
	.top {
		position: relative;
		display: flex;
		flex-direction: column;
		top: 8%;
		width: 94%;
		margin-left: 3%;
		border-radius: 15rpx;
		padding-top: 10rpx;
		padding-bottom: 10rpx;
		background-color: white;
	}

	.userInfoContainer {
		position: relative;
		margin: 0 auto;
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
	}


	.userinfo-avatar {
		border: 2px solid rgba(195, 199, 199, 0.7);
		position: relative;
		width: 108rpx;
		height: 108rpx;
		border-radius: 50%;
		margin: 0 auto;
	}

	.cameraContainer {
		width: 47rpx;
		height: 47rpx;
		margin: 0 auto;
		border-radius: 50%;
		background-color: white;
		margin-top: -15rpx;
		z-index: 1;
	}

	.camera {
		width: 30rpx;
		height: 30rpx;
		z-index: 2;
		margin-left: 10rpx;
		margin-top: 8rpx;
	}
	
	.vipMod{
		margin: 0 auto;
		margin-top: 20rpx;
		background-color: rgba(255, 216, 166, 0.6);
		width: 200rpx;
		height: 50rpx;
		line-height: 50rpx;
		border-radius: 100rpx;
		text-align: center;
	}
	
	
	
	.openVip{
		font-size: small;
		color: rgb(253, 123, 19);
		font-weight: bold;
	}
	
	.openVip-no{
		font-size: small;
		color: rgb(128, 128, 128);
		font-weight: bold;
	}
	

	.content {
		margin-left: 10rpx;
		margin-top: 5rpx;
		font-size: 28rpx;
		font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif
	}

	.topCtx {
		position: relative;
		display: flex;
		flex-direction: row;
		text-align: justify;
		top: 10rpx;
		margin-left: 25rpx;
		padding-top: 40rpx;
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

	.infoArrow {
		font-family: 'Courier New', Courier, monospace;
		font-weight: bold;
		position: absolute;
		right: 30rpx;
		margin-top: 8rpx;
		font-size: 28rpx;
		color: #808080;
	}

	.infoNo {
		color: #FF9500;
	}

	.bottom {
		position: relative;
		display: flex;
		flex-direction: column;
		margin-top: 20rpx;
		width: 94%;
		margin-left: 3%;
		border-radius: 15rpx;
		padding-top: 10rpx;
		padding-bottom: 10rpx;
		background-color: white;
	}
</style>
