<template>
	<view class="frame">
		<view class="miss">
			想你
		</view>
		<view class="heart left"></view>
		<view class="heart right"></view>
		<view class="heart bottom"></view>
	</view>
</template>

<script>
	export default {
		name: "love-btn",
		methods:{
		}
	}
</script>
<style lang="scss" scoped>
	.frame {
		position: relative;
		width: 296rpx;
		height: 250rpx;
		
	}
	.miss{
		position: absolute;
		top: 30%;
		text-align: center;
		width: 100%;
		color: white;
		z-index: 100;
		font-weight: bold;
	}

	.left,
	.right {
		top: 0;
		width: 148rpx;
		height: 148rpx;
		border-radius: 50%;
	}

	.left {
		left: 26rpx;
		z-index: 2;
	}

	.right {
		right: 26rpx;
		z-index: 1;
	}

	.bottom {
		bottom: 55rpx;
		left: 75rpx;
		width: 148rpx;
		height: 148rpx;
		transform: rotate(45deg);
		z-index: 1;

	}

	.heart {
		position: absolute;
		box-shadow: 0 0 40rpx #d5093c;
		animation: beat .6s ease infinite normal;
		background: linear-gradient(-90deg, #F50A45 0%, #d5093c 40%);
	}

	@keyframes beat {
		0% {
			transform: scale(1) rotate(225deg);
			box-shadow: 0 0 40rpx #d5093c;
		}

		50% {
			transform: scale(1.1) rotate(225deg);
			box-shadow: 0 0 70rpx #d5093c;
		}

		100% {
			transform: scale(1) rotate(225deg);
			box-shadow: 0 0 40rpx #d5093c;
			;
		}
	}
</style>