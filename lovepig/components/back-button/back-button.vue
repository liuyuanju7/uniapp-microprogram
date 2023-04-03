<template>
	<view class="back" :style="{ top: statusBarHeight }" @click="BackPage()">
		<view class="back-btn">
			<image class="image" v-if="home_mode === 'arrow'" src="/static/icon/common/back.png">
			</image>
			<image class="image" v-else-if="home_mode === 'home'" src="/static/icon/common/home.png">
			</image>
		</view>
	</view>
</template>

<script>
	export default {
		name: "back-button",
		data() {
			return {
				// 是不是从首页进来的
				home_mode: "arrow",
				// 系统高度
				statusBarHeight: 20
			};
		},
		props: {
			"color": {
				default: "white"
			},
		},
		created() {
			// 获取状态栏高度
			// console.log(JSON.stringify(uni.getSystemInfoSync()))
			// 加的这10，是为了对齐右侧关闭按钮
			this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight + 5 + 'px'
			// 如果 getCurrentPages() 的数组长度大于1，就说明是从首页进来的，如果不是，就说明是直接进入的这个内页
			// console.log(getCurrentPages())
			if (getCurrentPages().length > 1) {
				this.home_mode = "arrow"
			} else {
				this.home_mode = "home"
			}
		},
		methods: {
			BackPage() {
				if (this.home_mode === "arrow") {
					uni.navigateBack({
						delta: 1
					});
				} else {
					uni.reLaunch({
						url: '/pages/index/index'
					})
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.back {
		position: fixed;
		// left: 10px;
		z-index: 100;
	}

	.image {
		width: 15px;
		height: 15px;
		display: block;
	}

	.back-btn {
		width: 32px;
		height: 32px;
		border-radius: 50%;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-left: 10px;
		backdrop-filter:blur(5px);
		background-color: rgba($color: #000000, $alpha: .3);
	}
</style>
