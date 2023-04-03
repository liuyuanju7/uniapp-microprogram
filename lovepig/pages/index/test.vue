<template>
	<view id="frame">
		<view class="heart left"></view>
		<view class="heart right"></view>
		<view class="heart bottom"></view>
	</view>
</template>

<script>
	export default {
		onLoad() {
			console.info(11111)
		},
		onShow() {
			console.info(11111)
		}
	}
</script>
<style lang="scss" scoped>
	#frame {
		position: relative;
		width: 296rpx;
		height: 250rpx;
		margin-top: 60%;
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

	}

	.right {
		right: 26rpx;
		z-index: -1;
	}

	.bottom {
		bottom: 55rpx;
		left: 75rpx;
		width: 148rpx;
		height: 148rpx;
		transform: rotate(45deg);
		z-index: -1;

	}

	.heart {
		position: absolute;
		box-shadow: 0 0 40rpx #d5093c;
		animation: beat .5s ease infinite normal;
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