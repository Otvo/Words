<template>
	<view>
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view class="title_bar" :style="windowTop"></view>
		<view class="top">
			<text class="number">共{{number}}个</text>
			<view class="shelter">
				<text>遮挡释义</text>
				<switch class="select" :checked="isAllHidden" @change="changeSwitch" color="rgb(107,142,225)">
				</switch>
			</view>
		</view>

		<scroll-view scroll-y :style="windowHeight" class="content">
			<uni-swipe-action>
				<uni-swipe-action-item class="words" v-for="(word,index) in words" :right-options="options1"
					@click="deleteWord(index)">
					<view class="high">
						<text class="spell">{{word.spell}}</text>
						<text class="phonetic">{{word.phonetic}}</text>
						<image class="sound" :id="index" :src="voiceIcon" @click="voice"></image>
						<text class="hide" :id="index" @click="hiding" v-if="!isHidden[index]">收起释义 ∧</text>
						<text class="show" :id="index" @click="showing" v-if="isHidden[index]">展开释义 ∨</text>
					</view>


					<view class="low" v-if="!isHidden[index]">
						<view class="left">
							<text class="trans">释义</text>
						</view>
						<view class="right">
							<view v-for="item in word.meanings">
								<text class="meanings">
									{{item[0]}}{{item[1]}}\n
								</text>
							</view>
						</view>
					</view>
				</uni-swipe-action-item>
			</uni-swipe-action>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				windowTop: "",
				windowHeight: "",
				options1: [{
					text: '删除',
					style: {
						backgroundColor: '#dd524d'
					}
				}],
				BGURL: getApp().globalData.BGURL, //背景图片
				studingBook: {},
				number: 11,
				isAllHidden: false,
				isHidden: [],
				voiceIcon: "../../static/image/Notebook/voice.png",
				words: [],
				word: {
					//中文释义
					cnMeanings: [],
					//释义词性
					partsOfSpeech: [],
					//音标
					phonetic: "",
					//英文拼写
					spell: "",
					//发音
					//pronunciation: URL()
				}
			}
		},

		onLoad(option) {
			var that = this
			uni.getSystemInfo({
				success(e) {
					that.windowTop = "height:" + e.windowTop + "px";
					that.windowHeight = "height:" + (e.windowHeight - 42.5) + "px";
				}
			})
			var array = [];
			for (var i = 0; i < that.words.length; i++) {
				array.push(false)
			}
			that.isHidden = array;

			uni.request({
				url: getApp().globalData.URL+'/user/getCollectWords',
				data: {
					userID: getApp().globalData.userInformation.userID,
					bookid: option.bookID
				},
				success(res) {
					console.log(res)
					that.words = res.data
					that.number = res.data.length
				}
			})
		},
		methods: {
			changeSwitch: function(e) {
				var that = this
				var array1 = [];
				var array2 = [];
				for (var i = 0; i < that.words.length; i++) {
					array1[i] = true;
					array2[i] = false;
				}
				if ((!e.detail.value)) {
					that.isHidden = array2,
						that.isAllHidden = false
				} else {
					that.isHidden = array1,
						that.isAllHidden = true
				}
			},

			hiding: function(e) {
				var that = this
				var idx = parseInt(e.currentTarget.id);
				var array = that.isHidden
				array[idx] = true
				that.isHidden = array
				this.$forceUpdate()
			},

			showing: function(e) {
				var that = this
				var idx = parseInt(e.currentTarget.id);
				var array = that.isHidden
				array[idx] = false
				that.isHidden = array
				this.$forceUpdate()
			},

			voice: function(e) {
				var idx = parseInt(e.currentTarget.id);

				if (this.innerAudioContext) {
					this.innerAudioContext.destroy();
				}
				this.innerAudioContext = uni.createInnerAudioContext();
				this.innerAudioContext.autoplay = false;
				this.innerAudioContext.src = this.words[idx].pronunciation;
				this.innerAudioContext.play()
			},

			deleteWord(index) {
				uni.request({
					url: getApp().globalData.URL+"/learn/collect",
					data: {
						userID: getApp().globalData.userInformation.userID,
						word: this.words[index].spell
					},
					success: () => {
						this.words.splice(index, 1)
					}
				})
			}
		}
	}
</script>

<style>
	.note {
		display: flex;
		flex-direction: column;
	}

	.top {
		background-color: rgba(255, 255, 255, 0.7);
		display: flex;
		flex-direction: row;
		border-radius: 0px 0px 20rpx 20rpx;
		box-shadow: 0px 4px 15px 0px rgba(182, 182, 182, 0.31);
		position: relative;
		width: 100%;
		height: 65rpx;
		text-align: justify;
	}

	.number {
		position: absolute;
		top: 13rpx;
		left: 40rpx;
		font-size: 25rpx;
	}

	.shelter {
		position: absolute;
		top: 13rpx;
		right: 100rpx;
		font-size: 25rpx;
	}

	.select {
		position: absolute;
		top: 7rpx;
		margin-left: 30rpx;
		zoom: .5;
	}

	.content {
		position: absolute;
		margin-top: 20rpx;
	}

	.words {
		position: relative;
		margin-bottom: 20rpx;
		width: 100%;
		background-color: rgba(255, 255, 255, 0.7);
		border-radius: 20rpx;
	}

	.high {
		position: relative;
		top: 10rpx;
		margin-bottom: 30rpx;
	}

	.spell {
		margin-left: 30rpx;
		font-weight: bold;
		font-size: 40rpx;
	}

	.phonetic {
		margin-left: 20rpx;
		font-size: 20rpx;
	}

	.sound {
		height: 40rpx;
		width: 40rpx;
		padding: 5rpx;
		background-color: rgb(238, 243, 254);
		border-radius: 10rpx;
		position: absolute;
		margin-top: 10rpx;
		margin-left: 20rpx;
	}

	.hide {
		position: absolute;
		top: 20rpx;
		right: 40rpx;
		font-size: 25rpx;
	}

	.show {
		position: absolute;
		top: 20rpx;
		right: 40rpx;
		font-size: 25rpx;
	}

	.trans {
		padding-left: 10rpx;
		padding-right: 10rpx;
		color: rgb(240, 145, 55);
		border: 2rpx solid rgb(242, 160, 83);
		border-radius: 5rpx;
		font-size: 22rpx;
	}

	.low {
		position: relative;
		display: flex;
		flex-direction: row;
		bottom: 20rpx;
	}

	.left {
		margin-left: 30rpx;
		margin-top: 10rpx;
		white-space: nowrap;
		display: flex;
		align-items: center;
	}

	.meanings {
		font-size: 25rpx;
	}

	.right {
		margin-left: 30rpx;
	}

	.strengthen {
		background-color: rgb(236, 243, 255, 0.85);
		color: rgb(126, 165, 241);
		height: 100rpx;
		width: 362rpx;
		text-align: center;
		border-radius: 50rpx;
		line-height: 100rpx;
		margin-top: 270rpx;
		margin-bottom: 20rpx;
		margin-left: 25%
	}
</style>
