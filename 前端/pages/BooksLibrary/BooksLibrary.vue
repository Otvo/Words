<template>
	<view>
		<meta name="referrer"  content="never">
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view class="title_bar" :style="windowTop"></view>
		<view class="swiper-tab">
			<view class="swiper-tab-list" :class="currentTab==0 ? 'on' : ''" data-current="0" @click="swichNav">小学
			</view>
			<view class="swiper-tab-list" :class="currentTab==1 ? 'on' : ''" data-current="1" @click="swichNav">初中
			</view>
			<view class="swiper-tab-list" :class="currentTab==2 ? 'on' : ''" data-current="2" @click="swichNav">高中
			</view>
			<view class="swiper-tab-list" :class="currentTab==3 ? 'on' : ''" data-current="3" @click="swichNav">大学
			</view>
			<view class="swiper-tab-list" :class="currentTab==4 ? 'on' : ''" data-current="4" @click="swichNav">其他
			</view>
		</view>

		<view>
			<swiper :current="currentTab" class="swiper-box" duration="300" :style="windowHeight" @change="onchange" :autoplay="false">
				<swiper-item v-for="booktype in books">
					<scroll-view scroll-y="true" style="height:100%">
						<view v-for="book in booktype">
							<book_noUser :book="book"></book_noUser>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				BGURL: getApp().globalData.BGURL, //背景图片
				//总的书籍数组，应从后端获取
				books: [],
				//分类
				types: ['小学', '初中', '高中', '大学', '其他'],
				// tab切换
				currentTab: 0,
				windowHeight:"",
				windowTop:"",
			}
		},
		onLoad() {
			var that=this
			uni.getSystemInfo({
				success(e) {
					that.windowTop="height:"+e.windowTop+"px";
					that.windowHeight="height:"+(e.windowHeight-42)+"px";
				}
			})
			this.setBooks()
		},
		methods: {
			onchange(e){
				this.currentTab=e.detail.current;
			},
			//把对象数据按照某一个属性进行分类
			sort_pro: function(data) {
				var c = [];
				for (var type of this.types) {
					let temp = data.filter(function(obj) {
						return obj.type == type
					})
					c.push(temp)
				}
				return c;
			},
			//切换
			swichNav: function(e) {
				if (this.currentTab === e.target.dataset.current) {
					return false;
				} else {
					this.currentTab = e.target.dataset.current;
				}
			},
			//获取书籍信息
			setBooks() {
				var that=this;
				uni.request({
				    url: getApp().globalData.URL+"/book/getAllBook",
				    header: {
				        'custom-header': 'hello' //自定义请求头信息
				    },
				    success: (res) => {
				        that.books = that.sort_pro(res.data);
				    }
				});
				
			},
		}
	}
</script>

<style>
	.swiper-tab {
		width: 100%;
		position: fixed;
		z-index: 3;
		width: 100%;
		border-bottom: 2rpx solid #777777;
		text-align: center;
		line-height: 80rpx;
	}

	.swiper-tab-list {
		font-size: 30rpx;
		display: inline-block;
		width: 20%;
		color: #ffffff;
	}

	.on {
		color: #F09137;
		border-bottom: 5rpx solid #f09137;
	}

	.swiper-box {
		margin-top: 84rpx;
		width: 100%;
	}

	.swiper-box view {
		text-align: center;
	}
</style>
