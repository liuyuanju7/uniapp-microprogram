<template>
	<view class="loading-fixed" v-show="showLoading">
		<view class="loading"><image class="loading-image" src="/static/icon/common/love-load.gif" mode=""></image></view>
	</view>
</template>
<script>
export default {
	data() {
		return {
			showLoading: true
		};
	},
	methods: {
		//隐藏加载动画
		hidden() {
			//加载动画过度
			let timer = setTimeout(() => {
				console.info("隐藏动画")
				this.showLoading = false;
				clearTimeout(timer);
			}, 300);
		}
	}
};
</script>
<style lang="scss" scoped>
/* 加载动画 */
.loading-fixed {
	position: fixed;
	top: 0%;
	left: 0%;
	height: 100%;
	width: 100%;
	z-index: 1029;
	background-color: white;
	.loading {
		height: 200rpx;
		width: 200rpx;
		margin: auto;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
	.loading-image {
		height: 100%;
		width: 100%;
	}
}
</style>
