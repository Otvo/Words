<template>
	<view>
		<view class="mask"></view>
		<image class="background" :src="BGURL" mode="aspectFill"></image>

		<view v-show="isLog">
			<view class="logIn">
				Log In
			</view>
			<view class="logWord">To Your Account</view>

			<form @submit="logIn">
				<view class="container">
					<input class="input" name="userID"  placeholder="UserID" placeholder-class="co" />
					<input class="input"  name="password" placeholder="password"
						placeholder-class="co" />
				</view>
				<button class="btn" plain="true" form-type="submit">
					Log In
				</button>
			</form>
			<view class="logWord">
				Don't have an account? <text style="color: #FF9500; margin-left: 20rpx;"
					@click="changeType">Register</text>
			</view>
			<view class="logWord" style="margin-top: 100rpx;">
				Or Connect With Social Media
			</view>
			<view class="iconBox">
				<view class="iconfont icon" @click="logInBy('weixin')">&#xe659;</view>
				<view class="iconfont icon" @click="logInBy('qq')">&#xe6b4;</view>
				<view class="iconfont icon" @click="logInBy('sinaweibo')">&#xe641;</view>
			</view>
		</view>
S

		<view v-show="!isLog">
			<view class="logIn">
				Register
			</view>
			<view class="logWord">Your Account</view>
			<form @submit="register" >
				<view class="container">
					<input class="input" name="userID" v-model="userID" placeholder="UserID" placeholder-class="co" />
					<input class="input" name="nickName" placeholder="NickName" placeholder-class="co" />
					<input class="input" password="true" name="password1" placeholder="password"
						placeholder-class="co" />
					<input class="input" password="true" name="password2" placeholder="confirm password"
						placeholder-class="co" />
				</view>
				<button class="btn" plain="true" form-type="submit">
					Register
				</button>
			</form>
			<view class="logWord">
				Already have an account? 
				<text style="color: #FF9500; margin-left: 20rpx;" @click="changeType">\nLog In</text>
			</view>
		</view>

		<!-- <view class="btn" @click="login">
			<text class="iconfont" style="font-size:45rpx;margin-right: 40rpx;">&#xe659; </text>
			Log In
		</view> -->
		<!-- <view class="btn">
			<text class="iconfont" style="font-size:45rpx;margin-right: 40rpx;">&#xe6b4;</text>Log In
		</view> -->

		<!-- <view class="usermotto">
		  The shortest answer is doing.
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				isLog: true,
				userInfo: {},
				nickName: '',
				openId: '',
				avatarUrl: ''
			}
		},
		methods: {
			logIn: async function(e) {
				uni.showLoading({
					title: 'Loading'
				});
				console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
				const data = e.detail.value;
				var userID = data.userID.trim();
				var password = data.password.trim();
				if (userID == "" || password == "") {
					uni.hideLoading();
					uni.showModal({
						title: '提示',
						content: '请填写完整',
						showCancel: false
					});
					return;
				}
				//发送后端登录请求
				var [error, res] = await uni.request({
					url: getApp().globalData.URL+'/user/verify',
					data: {
						userID: userID,
						password: password
					}
				});
				console.log(res);

				//登录判断
				if (res.data.boolean == true) {
					getApp().globalData.userInformation = res.data.information
					getApp().globalData.hasLogedIn=true
					this.$forceUpdate()
					console.log(getApp().globalData.userInformation)
					//跳转页面
					uni.reLaunch({
						url: '../Home/Home'

					})
					uni.hideLoading();
				} else {
					//弹出提醒
					uni.hideLoading();
					uni.showModal({
						title: '提示',
						content: '账号或密码错误',
						showCancel: false
					});
				}
			},

			changeType: function() {
				//修改页面类型
				this.isLog = !this.isLog
			},
			

			logInBy: function(loginType) {
				var that = this;
				console.log(loginType)
				uni.login({
					//给provider传入不同的值, 调用不同的应用登陆接口
					provider: loginType,
					success: async function(loginRes) {
						uni.getUserInfo({
							provider: loginType,
							success: (res) => {
								that.userInfo = res.userInfo
								// console.log(that.userInfo)
								if (loginType == "weixin") {
									that.nickName = that.userInfo.nickName
									that.avatarUrl = that.userInfo.avatarUrl
									that.openId = that.userInfo.openId
								} else if (loginType == "qq") {
									that.nickName = that.userInfo.nickname
									that.avatarUrl = that.userInfo.figureurl_qq_2
									// qq返回了多个尺寸的头像, 按需选择
									that.openId = that.userInfo.openId
								} else if (loginType == "sinaweibo") {
									that.nickName = that.userInfo.nickname
									that.avatarUrl = that.userInfo.avatar_large
									that.openId = that.userInfo.id
								}
								//发送后端请求
								//先发送一次注册请求
								uni.showLoading({
									title: "登陆中"
								})
								console.log(that.openId)

								var success = that.registerRequest(that.openId, that.openId,
									that.nickName);
								console.log(success)
								if (1) {
									uni.request({
										url: getApp().globalData.URL+'/user/verify',
										data: {
											userID: that.openId,
											password: that.openId
										},
										success(res) {
											//登录判断
											console.log(res.data.information.isVip)
											if (res.data.boolean == true) {
												//跳转页面
												getApp().globalData.userInformation =
													res.data.information
												getApp().globalData.avatar = that
													.avatarUrl
												console.log(getApp().globalData
													.userInformation)
												uni.reLaunch({
													url: "../Home/Home"
												})
												uni.hideLoading()
											} else {
												//弹出提醒
												uni.hideLoading()
												uni.showModal({
													title: '提示',
													content: '登录失败',
													showCancel: false
												});
											}
										}
									});
								}
							}
						})
					}
				})

			},
			register: function(e) {
				console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
				const data = e.detail.value;
				var userID = data.userID.trim();
				var nickName = data.nickName.trim();
				var password1 = data.password1.trim();
				var password2 = data.password2.trim();
				if (userID == "" || nickName == "" || password1 == "" || password2 == "") {
					uni.showModal({
						title: '提示',
						content: '请填写完整',
						showCancel: false
					});
					return;
				}

				//校验两次密码
				if (password1 != password2) {
					uni.showModal({
						title: '提示',
						content: '密码不一致',
						showCancel: false
					});
					return;
				}

				//调用注册接口
				//发送后端登录请求
				uni.showLoading({
					title: "注册中"
				})
				var success = this.registerRequest(userID, password1, nickName)

				const _this = this;
				if (success) {
					//注册成功
					uni.hideLoading()
					uni.showModal({
						title: '提示',
						content: '注册成功',
						showCancel: false,
					});
					this.isLog = true
					this.$forceUpdate

				} else {
					//注册失败，用户名已存在
					uni.hideLoading()
					uni.showModal({
						title: '提示',
						content: '用户ID已注册',
						showCancel: false
					});
				}
				console.log(12344)
				this.userID = ''
			},
			registerRequest: async function(userID, password, nickName) {
				var [error, res] = await uni.request({
					url: getApp().globalData.URL+'/user/register',
					data: {
						userID: userID,
						password: password,
						name: nickName
					}
				});
				console.log(res.data);
				return res.data.success;
			}
			
		}
	}
</script>

<style>
	page {
		font-family: 'Noto Serif JP', serif;
	}

	.background {
		box-sizing: border-box;
		overflow: hidden;
		position: absolute;
		z-index: -2;
		display: flex;
		flex-direction: column;
	}

	.mask {
		position: absolute;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.3);
		z-index: -1;
	}

	.logIn {
		padding-top: 200rpx;
		height: 100rpx;
		line-height: 100rpx;
		font-size: 80rpx;
		text-align: center;
		z-index: 2;
		color: #FF9500;
	}

	.logWord {
		font-size: 35rpx;
		height: 50rpx;
		margin-top: 30rpx;
		margin-bottom: 50rpx;
		line-height: 50rpx;
		color: #e8eded;
		text-align: center;
	}

	.input {
		height: 90rpx;
		width: 550rpx;
		margin: 40rpx auto;
		padding: 0rpx 50rpx;
		font-family: 'Noto Serif JP', serif;
		border: 1px solid #e5e5e5;
		border-radius: 45rpx;
		color: #ffffff;
	}

	.co {
		font-family: 'Noto Serif JP', serif;
		color: #cccccc;
	}

	.btn {
		width: 650rpx;
		margin: 60rpx auto 20rpx auto;
		text-align: center;
		height: 90rpx;
		border-radius: 45rpx;
		color: #ffffff !important;
		font-size: 45rpx;
		line-height: 90rpx;
		background: rgba(255, 255, 255,0.15) !important;
		
		border: 1px solid #e5e5e5;
	}

	.iconBox {
		width: 500rpx;
		height: 100rpx;
		line-height: 100rpx;
		margin: 0 auto;
		display: flex;
		flex-direction: row;
		justify-content: space-around;

	}

	.icon {
		font-size: 45rpx;
	}

	.usermotto {
		position: fixed;
		bottom: 300rpx;
		text-align: center;
		height: 50rpx;
		width: 100%;
		line-height: 50rpx;
		font-size: 40rpx;
		color: #595959;
	}
</style>
