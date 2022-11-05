<template>
	<view >
		<image class="background" :src="BGURL" mode="aspectFill"></image>

		<view class="userinfo" @click="toMy">
			<view v:if="hasLogedIn" class="userInfoContainer" >
				<image class="userinfo-avatar" :src="avatar"></image>
			</view>
		</view>

		<view class="signIn" v-if="!hasSigned" @click="checkSignIn">
			<image class="cal" src="../../static/image/Home/cal.png"></image>
			<text class="sign">签到</text>
			<text class="day">{{day}}</text>
		</view>

		<view class="sentence" v-if="hasSigned">
			<view class="daily">
				<text>{{daily}}</text>
			</view>
			<view class="translation">
				<text>{{translation}}</text>
			</view>
		</view>

		<view class="Group" @click="toLearn" style="left:34rpx;">
			<text class="groupText">Learn</text>
			<text class="groupWords">{{todayLearn}}/{{learnNum}}</text>
		</view>

		<view class="Group" @click="toReview" style="right:34rpx;">
			<text class="groupText">Review</text>
			<text class="groupWords">{{reviewNum}}</text>
		</view>

		<view class="function">
			<image src="/static/image/Home/book.png" class="book" @click="toNotebook"></image>
			<image src="/static/image/Home/set.png" class="set" @click="toSet"></image>
		</view>
	</view>
	</view>
</template>

<script>
	const app = getApp();
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				day: getApp().globalData.day,
				userID:getApp().globalData.userInformation.userID,
				avatar:getApp().globalData.avatar,//用户头像
				hasLogedIn: true,//是否登录
				hasSigned: getApp().globalData.userInformation.isClock, //是否签到
				daily: getApp().globalData.daily,
				translation: getApp().globalData.translation,
				studyingBook:{},
				learnNum:getApp().globalData.userInformation.learnNum,
				todayLearn:0,
				reviewNum:0,
				isVip:getApp().globalData.userInformation
			}
		},
		
		onLoad() {
			var that=this
			uni.request({
				url:getApp().globalData.URL+"/user/judgeClockIn",
				data:{
					userID:that.userID
				},
				success:(res)=> {
					that.hasSigned=res.data.isClock
				}
			})
		},
		
		onShow(){
			var that=this
			that.learnNum=getApp().globalData.userInformation.learnNum,
			uni.request({
				url:getApp().globalData.URL+"/userbook/getLearnningBook",
				data:{
					userID:that.userID
				},
				success:(res)=>{
					console.log(res)
					that.studyingBook=res.data
				}
			})
			
			uni.request({
				url:getApp().globalData.URL+'/user/todayLearnWords',
				data:{
					userID:that.userID
				},
				success:(res)=> {
					console.log(res)
					that.todayLearn=res.data.learn
				}
			})
			
			uni.request({
				url:getApp().globalData.URL+'/user/totalReviewWords',
				data:{
					userID:that.userID
				},
				success:(res)=> {
					that.reviewNum=res.data.totalreview
				}
			})
			
			
		},
		onPullDownRefresh(){
			uni.navigateTo({
				animationType: 'slide-in-top',
				animationDuration: 300,
				url: '/pages/Search/Search',
			})
			uni.stopPullDownRefresh();
		},
		methods: {
			checkSignIn: function() {
				console.log(this.userID)
				uni.request({
					url:getApp().globalData.URL+'/user/userSign',
					data:{
						userID:this.userID
					},
					success:(res)=> {
						console.log(res)
						if (this.innerAudioContext) {
							this.innerAudioContext.destroy();
						}
						this.innerAudioContext = uni.createInnerAudioContext();
						this.innerAudioContext.autoplay = false;
						this.innerAudioContext.src = '../../static/sign.mp3';
						this.innerAudioContext.play()
						uni.vibrateShort({})
						uni.showToast({
							title: '签到成功',
							duration: 2000
						});
						this.hasSigned=true
					}
				})
			},
			toMy(){
				uni.navigateTo({
					url:"/pages/My/My"
				})
			},
			toLearn() {
				uni.navigateTo({
					url: '/pages/Learn/Learn',
				})
			},
			toReview() {
				uni.navigateTo({
					url: "/pages/Review/Review",
				})
			},
			toNotebook() {
				uni.navigateTo({
					url: "/pages/Notebook/Notebook?bookID=this.studyingBook.id",
				})
			},
			toSet() {
				uni.navigateTo({
					url: "/pages/Set/Set",
				})
			}
		}
	}
</script>

<style>
	.userinfo{
		position: absolute;
		left: 8%;
		top: 8%;
	}
	
	.userInfoContainer {
		position: fixed;
		left: 8%;
		top: 8%;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: flex-end;
	}

	.userinfo-avatar {
		border: 2px solid white;
		position: relative;
		overflow: hidden;
		width: 108rpx;
		height: 108rpx;
		border-radius: 50%;
		left: 5%;
		top: 5%;
		
	}

	.signIn {
		position: absolute;
		width: 280rpx;
		height: 280rpx;
		left: 50%;
		top: 50%;
		margin-left: -140rpx;
		margin-top: -460rpx;
		background-color: rgba(255, 255, 255, 0.3);

		border-radius: 40rpx;
		box-sizing: border-box;
		box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
		overflow: hidden;
		position: absolute;
		bottom: 286rpx;
		z-index: 2;
		display: flex;
		flex-direction: column;
		/* padding-top: 40%; */
	}

	.signIn::before {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-image: url("https://api.sunweihu.com/api/bing1/api.php");
		background-position: center top;
		background-size: cover;
		background-attachment: fixed;
		filter: blur(25rpx);
	}

	.cal {
		width: 80rpx;
		height: 80rpx;
		margin-left: 100rpx;
		margin-top: 50rpx;
	}

	.sign {
		margin: auto;
		font-size: 35rpx;
		font-weight: bold;
		font-stretch: normal;
		line-height: 50rpx;
		letter-spacing: 2rpx;
		color: #fff;
	}



	.day {
		margin-bottom: 10rpx;
		margin: auto;
		font-size: 25rpx;
		font-weight: bold;
		font-stretch: normal;
		line-height: 50rpx;
		letter-spacing: 2rpx;
		color: #fff;
	}

	.sentence {
		background-color: rgba(0, 0, 0, 0.3);
		width: 97%;
		position: absolute;
		top: 400rpx;
		left: 1.5%;
		display: flex;
		flex-direction: column;
		text-align: center;
		z-index: 2;
		padding-top: 20rpx;
		padding-bottom: 20rpx;
		border-radius: 10rpx;
	}

	.daily {
		font-family: 'Times New Roman', Times, serif;
		font-size: 40rpx;
		font-weight: bold;
		font-stretch: normal;
		line-height: 50rpx;
		letter-spacing: 2rpx;
		color: #fff;
	}

	.translation {
		margin-top: 35rpx;
		font-size: 40rpx;
		font-weight: bold;
		font-stretch: normal;
		line-height: 50rpx;
		letter-spacing: 2rpx;
		color: #fff;
	}

	.Group {
		width: 312rpx;
		height: 145rpx;
		background-color: rgba(255, 255, 255, 0.3);
		border-radius: 10px;
		box-sizing: border-box;
		box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
		overflow: hidden;
		position: absolute;
		bottom: 286rpx;
		z-index: 2;
		display: flex;
		flex-direction: column;
	}

	.Group::before {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: -1;
		background-image: url("https://api.sunweihu.com/api/bing1/api.php");
		background-position: center top;
		background-size: cover;
		background-attachment: fixed;
		filter: blur(5rpx);
	}

	.groupText {
		margin-top: 20rpx;
		margin-left: 30rpx;
		font-size: 40rpx;
		font-weight: bold;
		font-stretch: normal;
		letter-spacing: 2rpx;
		color: #ffffff;
	}

	.groupWords {
		margin-top: 8rpx;
		margin-left: 30rpx;
		font-size: 35rpx;
		font-weight: bold;
		font-stretch: normal;
		letter-spacing: 2rpx;
		color: #ff9500;
	}

	.book {
		width: 56rpx;
		height: 56rpx;
	}

	.set {
		width: 55rpx;
		height: 55rpx;
	}
</style>
