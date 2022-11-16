<script>
	export default {
		//全局变量
		globalData: {
			userInformation: {},//用户信息
			URL: "http://127.0.0.1:12306",
			avatar:'/static/image/touxiang.png',//用户头像
			BGURL: "https://api.kdcc.cn",//背景图片地址
			day: "",
			daily: "",
			translation: "",
			hasLogedIn:false
		},
		onLaunch: function() {
			var that=this;
			// uni.request({
			//     url: this.globalData.URL+"/user/verify", 
			//     data: {
			//         userID: '001',
			// 		password:"abc"
			//     },
			//     success: (res) => {
			//         that.globalData.userInformation=res.data.information
			// 		console.log(res.data.information)
			//     }
			// });
			
			var date = new Date();
			date.setTime(date.getTime());
			var Y = date.getFullYear();
			var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
			var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
			//console.log(M + D);//今日日期
			let show_day = new Array('Sun.', 'Mon.', 'Tue.', 'Wed', 'Thu.', 'Fri.', 'Sat.');
			let day = date.getDay();
			var weekday = show_day[day];
			this.globalData.day = M + '/' + D + ' ' + weekday
			
			uni.request({
				url: 'http://open.iciba.com/dsapi/?date=' + Y + '-' + M + '-' + D,
				data:{
					type:'last'
				},
				headers: {
					'Content-Type': 'application/json'
				},
				success:(res)=> {
					that.globalData.daily= res.data.content,
					that.globalData.translation= res.data.note
					console.log(res)
				},
			})
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	@import 'static/iconfont/iconfont.css';
	
	.mask{
		z-index: 0;
		width: 100%;
		height: 100%;
		position: fixed;
		background-color: rgba(0,0,0,0.2);
	}
	.filter {
		filter: blur(40rpx);
		transform: scale(1.5);
	}

	/*背景图片样式*/
	.background {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: -1;
	}

	/*每个页面公共css */
	.function {
		position: absolute;
		bottom: 50rpx;
		left: 65rpx;
		right: 61rpx;
		display: flex;
		z-index: 2;
		justify-content: space-between;
	}

	/* 水平居中 */
	.center {
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/* 垂直排列居中 */
	.center_column {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	/*状态栏占位高度*/
	.status_bar {
		height: var(--status-bar-height);
		width: 100%;
	}


	/* 标题栏占位宽度 */
	.title_bar {
		width: 100%;
		background-color: rgba(255, 255, 255, 0);
	}

	/* 小字体 */
	.smallFont {
		font-size: 25rpx;
	}
</style>
