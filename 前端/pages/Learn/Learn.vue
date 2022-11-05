<template>
	<view>
		<meta name="referrer" content="never">
		<image class="background filter" :src="BGURL"></image>
		<view class="mask"></view>
		<view class="mainContainer">
			<view class="status_bar"></view>
			<view class="word_container">
				<view class="progress-box">
					<progress :percent="today/learnNum*100" class="progress" />
					<text>{{today}}/{{learnNum}}</text>
				</view>

				<view class="center_column">
					<view class="word" @click="accent">{{words[cur].spell}}</view>
					<view @click="accent">{{words[cur].usAccent}}</view>
				</view>

				<!-- 释义 -->
				<view v-if="isShow" class="meaning">
					请把英文发音和中文解释说出口
				</view>
				<view v-else class="meaning">
					<view v-for="meaning in words[cur].meanings">
						{{meaning[0]}}
						{{meaning[1]}}
					</view>
				</view>

				<!-- 按钮 -->
				<view v-if="isShow" class="functionBar">
					<view class="center_column" @click="show">
						<text>看答案</text>
						<image src="/static/image/Learn/point.svg" class="function-icon"></image>
					</view>
				</view>
				<view v-else class="functionBar">
					<view id="2" class="center_column" @click="next">
						<text>认识</text>
						<image src="/static/image/Learn/point.svg" class="function-icon"></image>
					</view>
					<view id="1" class="center_column" @click="next">
						<text>模糊</text>
						<image src="/static/image/Learn/point.svg" class="function-icon"></image>
					</view>
					<view id="0" class="center_column" @click="next">
						<text>不认识</text>
						<image src="/static/image/Learn/point.svg" class="function-icon"></image>
					</view>
				</view>

				<!-- 底部功能键 -->
				<view class="functionBar">
					<image class="function-icon" src="/static/image/Learn/return.svg" @click="back"></image>
					<image class="function-icon" :src="'/static/image/Learn/'+(isCollect?'collect':'nocollect')+'.svg'"
						@click="collect"></image>
					<image class="function-icon" src="/static/image/Learn/detail.svg" @click="detail"></image>
					<image class="function-icon" src="/static/image/Learn/delete.svg" @click="del"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	const app = getApp();
	export default {
		data() {
			return {
				today: 1,
				words: [{
					spell: 'No internet'
				}],
				cur: 0,
				learnNum: getApp().globalData.userInformation.learnNum,
				isShow: true,
				isCollect: false,
				BGURL: getApp().globalData.BGURL, //背景图片
			}
		},
		onLoad() {
			var that = this
			uni.getSystemInfo({
				success(e) {
					that.windowHeight = "height:" + (e.windowHeight - 60) + "px";
				}
			})
		},
		onShow() {
			var that = this;
			uni.request({
				url: app.globalData.URL + "/user/todayLearnWords",
				data: {
					userID: app.globalData.userInformation.userID,
				},
				success: (res) => {
					that.today = res.data.learn + 1;
					if (this.today >= this.learnNum) {
						uni.navigateTo({
							animationType: 'fade-in',
							animationDuration: 300,
							url: '/pages/Share/Share',
						})
					} else {
						this.setWords();
					}
				}
			});
		},
		methods: {
			detail() {
				var that = this;
				uni.navigateTo({
					animationType: 'slide-in-top',
					animationDuration: 300,
					url: '/pages/Search/Search?word=' + that.words[that.cur].spell,
				})
			},
			del() {
				var e = {
					currentTarget: {
						id: 1
					}
				}
				this.next(e);
			},
			next(e) {
				if (this.today == this.learnNum) {
					uni.navigateTo({
						animationType: 'fade-in',
						animationDuration: 300,
						url: '/pages/Share/Share',
					})
				} else {
					var that = this;
					uni.request({
						url: app.globalData.URL + "/learn/learnWords",
						data: {
							userID: app.globalData.userInformation.userID,
							word: that.words[that.cur].spell,
							master: e.currentTarget.id,
						},
						success: (res) => {
							this.today = this.today + 1;
							this.cur = this.cur + 1;
							this.accent()
							this.isCollect = false;
							this.isShow = !this.isShow;
						}
					});
				}
			},
			accent() {
				if (this.innerAudioContext) {
					this.innerAudioContext.destroy();
				}
				this.innerAudioContext = uni.createInnerAudioContext();
				this.innerAudioContext.autoplay = false;
				this.innerAudioContext.src = this.words[this.cur].pronunciation;
				this.innerAudioContext.play()
			},
			show() {
				this.isShow = false;
			},
			setWords() {
				var that = this;
				uni.request({
					url: app.globalData.URL + "/learn/getLearnWords",
					data: {
						userID: app.globalData.userInformation.userID,
					},
					success: (res) => {
						if (res.statusCode == 500) {
							uni.showModal({
								title: '还未添加书籍',
								cancelText: '返回',
								confirmText: '去添加',
								showCancel: true,
								success(res) {
									if (res.confirm) {
										uni.redirectTo({
											url: "../MyBooks/MyBooks"
										})
									} else {
										uni.navigateBack()
									}
								}
							});
						} else
							that.words = res.data;
					}
				});
			},
			back() {
				uni.navigateBack({
					delta: 1
				});
			},
			collect() {
				var e = {
					currentTarget: {
						id: 1
					}
				}
				this.next(e);
				var that = this;
				uni.request({
					url: app.globalData.URL + "/learn/collect",
					data: {
						userID: app.globalData.userInformation.userID,
						word: that.words[that.cur].spell
					},
				});
				if (this.isCollect) {
					this.isCollect = false;
				} else {
					this.isCollect = true;
				}
			}
		}
	}
</script>

<style>
	.progress {
		width: 70%;
	}

	.progress-box {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
		width: 100%;
	}

	.mainContainer {
		color: white;
		height: 100%;
		width: 100%;
		position: absolute;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.meaning {
		height: 50%;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}

	.word {
		font-size: 80rpx;
		font-weight: 700;
	}

	.word_container {
		height: 100%;
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
	}

	.function-icon {
		width: 50rpx;
		height: 50rpx;
	}

	.functionBar {
		width: 100%;
		display: flex;
		justify-content: space-around;
	}
</style>
