<template>
	<view>
		<meta name="referrer" content="never">
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>
		<scroll-view scroll-y="true" :style="windowHeight" show-scrollbar=false>
			<button class='btn1' @click="addbook()">
				<view>+ 添加图书</view>
			</button>
			<view class="title_container">
				<text class="title">正在学习</text>
				<image src="../../static/image/MyBooks/bulb.png" class='img'></image>
			</view>
			<view v-for="book in books">
				<view v-if="book.isStudying">
					<book :book="book" @update='setBook'></book>
				</view>
			</view>
			<view class="title_container">
				<text class="title">我的图书</text>
				<image src="../../static/image/MyBooks/book.png" class='img'></image>
			</view>
			<view v-for="book in books">
				<view v-if="!book.isStudying&&(book.id!=-1)">
					<book :book="book" @update='setBook'></book>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	const app=getApp();
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				books:{},//我的图书
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
			this.setBook();
		},
		methods: {
			setBook(){
				var that=this;
				uni.request({
				    url: getApp().globalData.URL+"/userbook/getUserBook",
				    data:{
						userID: app.globalData.userInformation.userID,
					},
				    success: (res) => {
				        that.books = res.data;
				    }
				});
			},
			addbook(){
				uni.navigateTo({
					url: '/pages/BooksLibrary/BooksLibrary',
					animationType: 'fade-in',
					animationDuration: 300,
				})
			}
		}
	}
</script>

<style>
	.title_container{
		margin-left:20rpx;
		margin-top: 30rpx;
	}
	.title{
		color: white;
		font-size:35rpx;
		font-weight:bold
	}
	.btn1 {
	  font-size:39rpx;
	  width:90%;
	  height:80rpx;
	  background-color: rgb(162, 205, 240);
	  color: rgba(13, 97, 175, 0.897);
	  border-radius: 98rpx;
	  display: flex;
	  flex-direction: row;
	  align-items: center;
	  justify-content: center;
	}
	
	.img{
	  width:35rpx;
	  height:35rpx;
	}
	
	.top {
	  z-index: -1;
	  position: fixed;
	  color: #ffffff;
	  width: 100%;
	  font-weight: 700;
	}
	#scroll{
	  width:100%;
	  position: absolute;
	}
</style>
