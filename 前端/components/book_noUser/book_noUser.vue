<template>
	<view>
		<view class="book_main" @click="tap">
			<view class="book_box2">
				<image :src="book.image" mode="aspectFill" class="image"></image>
				<view class="book_box1" style="align-items:baseline;justify-content:space-between;height:250rpx">
					<view>{{book.name}}</view>
					<view style="color:gray; font-size:small">总词数:{{book.sum}}</view>
					<view style="color:gray; font-size:small">{{book.type}}</view>
				</view>
				<view></view>
				<view></view>
			</view>
		</view>
		<view>
			<uni-popup ref="popup" type='dialog'>
				<uni-popup-dialog :duration="2000" @close="close" @confirm="confirm">
					<view>
						<view>是否将</view>
						<view style="color: #007AFF;">
							<<{{book.name}}>>
						</view>
						添加到我的图书
					</view>
				</uni-popup-dialog>
			</uni-popup>
		</view>
	</view>

</template>

<script>
	export default {
		name: "book_noUser",
		data() {
			return {};
		},
		props: ['book'],
		methods: {
			tap: function() {
				this.$refs.popup.open()
			},
			close() {
				this.$refs.popup.close()
			},
			confirm() {
				uni.showLoading({
					title: '正在添加'
				})
				var that=this;
				uni.request({
				    url: getApp().globalData.URL+"/userbook/addBook",
				    data:{
						userID: getApp().globalData.userInformation.userID,
						bookID: that.$props.book.bookid,
					},
				    success: (res) => {
				        that.books = res.data;
						uni.hideLoading()
						uni.navigateBack({
						    delta: 1
						});
				    }
				});
				this.$refs.popup.close()
			}
		}
	}
</script>

<style>
	/* components/book/book.wxss */
	.book_main {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-around;
		border-radius: 30rpx;
		margin-top: 2%;
		margin-left:3%;
		width: 94%;
		height: 280rpx;
		background-color: rgba(255, 255, 255, 0.9);
	}

	.progress {
		width: 85%;
	}

	.image {
		border-radius: 10rpx;
		height: 250rpx;
		width: 180rpx;
	}

	.book_box1 {
		align-items: baseline;
		justify-content: space-between;
		height: 250rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.book_box2 {
		display: flex;
		justify-content: space-between;
		flex-direction: row;
		width: 93%;
	}
</style>
