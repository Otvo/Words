<template>
	<view>
		<uni-swipe-action class="mainSwipe">
			<uni-swipe-action-item :right-options="options" @click="tap">
				<view class="book_main" @click="change">
					<view class="book_box2">
						<image :src="book.image" mode="aspectFill" class="image"></image>
						<view class="book_box1"
							style="align-items:baseline;justify-content:space-between;height:250rpx">
							<view>{{book.name}}</view>
							<view v-if="book.isStudying" style="color:#F09137">正在学习</view>
							<view style="color:gray; font-size:small">{{book.type}}</view>
						</view>
						<view></view>
						<view></view>
					</view>
					<view class="progress-box">
						<progress activeColor="#F09137" :percent="book.study/book.sumWords*100" stroke-width=6
							active />
						<view class="wordsText">
							<text>已学习 {{book.study}}</text>
							<text>总词数 {{book.sumWords}}</text>
						</view>
					</view>
				</view>
			</uni-swipe-action-item>
		</uni-swipe-action>

		<view>
			<uni-popup ref="popup" type='dialog'>
				<uni-popup-dialog :duration="2000" @close="close" @confirm="confirm">
					<view>
						<view>是否<text style="color: red;">{{text}}</text>图书</view>
						<view style="color: #007AFF;">
							<<{{book.name}}>>
						</view>
					</view>
				</uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		name: "book",
		props: ['book'],
		data() {
			return {
				text: "",
				options: [{
					text: '清空',
					style: {
						backgroundColor: '#007aff'
					}
				}, {
					text: '删除',
					style: {
						backgroundColor: '#dd524d'
					}
				}]
			};
		},
		methods: {
			change() {
				if (!this.$props.book.isStudying) {
					this.text = "更改正在学习"
					this.$refs.popup.open();
				}
			},
			tap(e) {
				this.text = e.content.text;
				this.$refs.popup.open();
			},
			close() {
				this.$refs.popup.close()
			},
			confirm() {
				if (this.$props.book.name!="请选择书籍"){
					var that = this;
					uni.showLoading({
						title: '正在' + this.text
					})
					var operate=""
					if (this.text == "更改正在学习") {
						operate="/userbook/setLearnningBook"
					}else if(this.text=="清空"){
						operate="/learn/clearBookRecord"
					}else if(this.text=="删除"){
						operate="/userbook/deleteBook"
					}
					uni.request({
						url: getApp().globalData.URL + operate,
						data: {
							userID: getApp().globalData.userInformation.userID,
							bookID: that.$props.book.id,
						},
						success: (res) => {
							uni.hideLoading()
							that.$emit('update')
						}
					});
					this.$refs.popup.close()
				}else{
					uni.showToast({
						title:"无法进行此操作",
						icon:'error',
						duration:1300
					})
				}
			}
		}
	}
</script>

<style>
	.mainSwipe {
		margin: 3%;
		width: 94%;
	}

	.book_main {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-around;
		border-radius: 30rpx;
		height: 330rpx;
		background-color: rgba(255, 255, 255, 0.91);
	}

	.progress-box {
		width: 95%;
	}
	
	.wordsText {
		margin-top: 5rpx;
		color: gray;
		font-size: small;
		display: flex;
		width: 100%;
		justify-content: space-between;
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
		flex-direction: row;
		justify-content: space-between;
		width: 93%;
	}
</style>
