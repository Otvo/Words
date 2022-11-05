<template>
	<view>
		<view class="center_column">
			<view class="smallFont">
				<image :src="imageSrc" class="dataImage"></image>
				{{" "+title}}
			</view>
			<view>
				<text class="largeFont">{{number}}</text>
				<text class="wordFont">{{" "+unit}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "StudyData",
		props: ['imageSrc', 'title', 'number', 'unit'],
		data() {
			return {

			};
		}
	}
</script>

<style lang="scss">
	.dataImage {
		width: 25rpx;
		height: 25rpx;
	}

	.wordFont {
		font-size: small;
		color: $uni-text-color-grey;
	}

	.largeFont {
		font-size: 50rpx;
		font-weight: 800;
	}
</style>
