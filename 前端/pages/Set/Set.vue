<template>
	<view>
		<meta name="referrer" content="never">
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>
		<scroll-view scroll-y="true" :style="windowHeight" show-scrollbar=false>
			<view class="main">
				<text class="title">正在学习</text>
				<view class="BookContainer1">
					<view class="BookContainer2">
						<view>
							<view class='ChooseBook'>
								<image class="book1" :src="book.image"></image>
								<view class="book2 smallFont">
									更换书籍
									<image class="img" src="/static/image/Set/add.png" @click="toLibrary"></image>
								</view>
							</view>
							<text class="mainColor">{{book.name}}</text>
						</view>

						<view class="progress-box">
							<progress activeColor="#F09137" :percent="book.study/book.sumWords*100"
								stroke-width=10 active />
							<view class="wordsText">
								<text>已学习 {{book.study}}</text>
								<text>总词数 {{book.sumWords}}</text>
							</view>
						</view>
					</view>
				</view>
				<text class="title">我的数据</text>
				<view class="dataContainer1">
					<view class="dataContainer2">
						<text>概览</text>
						<view class="dataContainer3">
							<StudyData title="今日学习" :number="con_learn" imageSrc="/static/image/Set/today.png" unit="词">
							</StudyData>
							<StudyData title="累计学习" :number="sum_learn" imageSrc="/static/image/Set/sum.png" unit="词">
							</StudyData>

						</view>
						<view class="dataContainer3">
							<StudyData title="最大连续签到" :number="con_day" imageSrc="/static/image/Set/time.png" unit="天">
							</StudyData>
							<StudyData title="累计签到" :number="sum_day" imageSrc="/static/image/Set/clock_in.png"
								unit="天">
							</StudyData>
						</view>
						<text>日历</text>
					</view>
					<uni-calendar class="calender" :selected="date"></uni-calendar>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	const app = getApp()
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				book: {study:1,sumWords:1},
				date: [{
					date: "2000-01-01",
					info: "今天"
				}],
				sum_day: 0,
				con_day: 0,
				con_learn:0,
				sum_learn:0,
				windowTop: "",
			}
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
		onShow() {
			this.setBook()
			var that = this;
			uni.request({
				url: app.globalData.URL + "/clockin/getClockIn",
				data: {
					userID: app.globalData.userInformation.userID,
				},
				success: (res) => {
					this.setDate(res.data);
				}
			});
			uni.request({
				url: app.globalData.URL + "/user/todayLearnWords",
				data: {
					userID: app.globalData.userInformation.userID,
				},
				success: (res) => {
					this.con_learn=res.data.learn;
				}
			});

		},
		methods: {
			//设置日期
			setDate(dates) {
				var temp1 = new Date("2000-01-02");
				var temp2 = new Date("2000-01-01");
				var oneDay = temp1.getTime() - temp2.getTime();
				this.sum_day = dates.length;
				var max_con_day = 0;
				var temp_con_day = 0;
				var dateinfos = [];
				var dateinfo = {
					date: dates[0],
					info: "打卡"
				};
				dateinfos.push(dateinfo)
				for (var i = 0; i < dates.length - 1; i++) {
					var dateinfo = {
						date: dates[i + 1],
						info: "打卡"
					};
					dateinfos.push(dateinfo)
					var d1 = new Date(dates[i])
					var d2 = new Date(dates[i + 1])
					if (d2.getTime() - d1.getTime() <= oneDay) {
						temp_con_day++;
					} else{
						temp_con_day = 0;
					}
					if (temp_con_day > max_con_day) {
						max_con_day = temp_con_day;
					}
				}
				if(dates.length!=0){
					this.date = dateinfos;
					this.con_day = max_con_day+1;
				}
				
			},
			//跳转到更多书籍界面
			toLibrary() {
				uni.navigateTo({
					url: '/pages/MyBooks/MyBooks',
					animationType: 'fade-in',
					animationDuration: 300,
				})
			},
			//设置书籍
			setBook() {
				var that = this;
				uni.request({
					url: app.globalData.URL + "/userbook/getLearnningBook",
					data: {
						userID: app.globalData.userInformation.userID,
					},
					success: (res) => {
						that.book = res.data;
						that.sum_learn=res.data.study;
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.mainColor {
		color: $uni-color-main;
	}

	.title {
		color: white;
		margin-top: 30rpx;
		margin-bottom: 15rpx;
		font-size: larger;
		font-weight: 500;
	}

	.main {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		width: 94%;
		margin-left: 3%;
	}

	.BookContainer1 {
		height: 500rpx;
		border-radius: 20rpx;
		width: 100%;
		background-color: rgb(255, 255, 255);

	}

	.BookContainer2 {
		height: 100%;
		width: 90%;
		margin-left: 5%;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: space-around;
	}


	.ChooseBook {
		margin-bottom: 20rpx;
		display: flex;
		justify-content: flex-start;
	}

	.book1 {
		border-radius: 10rpx;
		width: 180rpx;
		height: 250rpx;
	}

	.book2 {
		flex-direction: column;
		justify-content: space-between;
		display: flex;
		width: 180rpx;
		margin-left: 50rpx;
		height: 250rpx;
	}

	.img {
		width: 140rpx;
		height: 170rpx;
	}

	.img2 {
		display: flex;
		margin-top: 10rpx;
		margin-left: 200rpx;
		width: 30rpx;
		height: 40rpx;
	}

	.progress-box {
		width: 100%;
	}

	.wordsText {
		margin-top: 5rpx;
		color: gray;
		font-size: small;
		display: flex;
		width: 100%;
		justify-content: space-between;
	}

	.dataContainer1 {
		height: 1300rpx;
		border-radius: 20rpx;
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		background-color: rgb(255, 255, 255);
	}

	.dataContainer2 {
		height: 35%;
		width: 90%;
		margin-left: 5%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
	}

	.dataContainer3 {
		display: flex;
		height: 150rpx;
		justify-content: space-around;
	}

	.calender {
		width: 100%;
	}
</style>
