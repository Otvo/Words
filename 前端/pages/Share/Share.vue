<template>
	<view>
		<meta name="referrer" content="never">
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>
		<scroll-view scroll-y="true" :style="windowHeight" show-scrollbar=false>
			<view class="container">
				恭喜，你已经完成了本日学习任务！
				<button type="default" @click="back">返回</button>
			</view>
			
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
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
		methods: {
			back(){
				uni.redirectTo({
					url:"/pages/Home/Home"
				})
			}
		}
	}
</script>

<style>
	.container{
		font-size: large;
		color:white;
		display: flex;
		flex-direction: column;
		height:70%;
		justify-content: space-around;
		align-items: center;
	}
</style>
