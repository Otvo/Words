<template>
	<view>
		<meta name="referrer" content="never">
		<image class="background" :src="BGURL" mode="aspectFill"></image>
		<view :style="windowTop"></view>
		<scroll-view scroll-y="true" :style="windowHeight" show-scrollbar=false @scrolltolower="bottom">
			<view class="container">
				<uni-group top="1" mode="card" class="group">
					<picker mode="selector" :range="languages" range-key="name" @change="from_change">
						<view>原文：{{from}}▼</view>
					</picker>
				    <uni-easyinput type="textarea" :value="inputValue" maxlength=1000 autoHeight placeholder="请输入内容" :inputBorder="false" @input="input"></uni-easyinput>
				</uni-group>
				<button type="default" @click="translate" :loading="loading">翻译</button>
				<uni-group top="1" mode="card" class="group">
					<picker mode="selector" :range="languages" range-key="name" @change="to_change">
						<view>译文：{{to}}▼</view>
					</picker>
				    <uni-easyinput type="textarea" maxlength=-1 :value="result" autoHeight :inputBorder="false" disabled>
					</uni-easyinput>
				</uni-group>
				<view style="height: 10%;"></view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import md5 from "../../static/md5.js";
	var input="";
	var from="auto";
	var to="zh";
	const languages=[
		{
			name:"自动检测",
			value:"auto"
		},
		{
			name:"中文",
			value:"zh"
		},
		{
			name:"英语",
			value:"en"
		},
		{
			name:"日语",
			value:"jp"
		},
		{
			name:"繁体中文",
			value:"cht"
		},
		{
			name:"粤语",
			value:"yue"
		},
		{
			name:"文言文",
			value:"wyw"
		},
		{
			name:"韩语",
			value:"kor"
		},
		{
			name:"法语",
			value:"fra"
		},
		{
			name:"西班牙语",
			value:"spa"
		},
		{
			name:"俄语",
			value:"ru"
		},
		{
			name:"德语",
			value:"de"
		},
		{
			name:"泰语",
			value:"th"
		},
		{
			name:"阿拉伯语",
			value:"ara"
		},
		
	]
	export default {
		data() {
			return {
				inputValue:"",
				result:"",
				loading:false,
				from:"自动检测",
				to:"中文",
				languages:languages,
				BGURL: getApp().globalData.BGURL, //背景图片
			}
		},
		onLoad(e) {
			if(e.word){
				input=e.word;
				this.inputValue=e.word;
				this.translate()
			}
			var that = this
			uni.getSystemInfo({
				success(e) {
					that.windowTop = "height:" + e.windowTop + "px";
					that.windowHeight = "height:" + (e.windowHeight) + "px";
				}
			})
		},
		methods: {
			bottom(){
				uni.navigateBack({
					delta:1,
				})
			},
			from_change(e){
				var index=e.detail.value
				from=languages[index].value;
				this.from=languages[index].name;
			},
			to_change(e){
				var index=e.detail.value
				to=languages[index].value;
				this.to=languages[index].name
			},
			input(e){
				input=e;
			},
			translate(){
				this.loading=true;
				var appid="20210722000895051";
				var password="uDatoA9FrhNdXW45LcrG";
				var salt=Math.round(Math.random()*1000000000);
				var that = this;
				var str=appid+input+salt+password
				var sign=md5.MD5(str)
				uni.request({
					url: "https://fanyi-api.baidu.com/api/trans/vip/translate",
					dataType: 'jsonp',
					data: {
						q:input,
						from:from,
						to:to,
						appid:appid,
						salt:salt,
						sign:sign
					},
					success: (res) => {
						that.loading=false;
						if(JSON.parse(res.data).trans_result)
							that.result=JSON.parse(res.data).trans_result[0].dst;
					}
				});
			}
		}
	}
</script>

<style>
	.container{
		height:101%;
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
	}
	.group{
		height:35%;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}
	
	.input{
		background-color: white;
	}
	button{
		width: 40%;
	}
</style>
