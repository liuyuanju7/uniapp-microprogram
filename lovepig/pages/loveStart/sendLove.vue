<template>
	<view class="main">
		<u-toast ref="uToast" />

		<view class="love-wrapper">
			<view class="logo">
				<image mode="scaleToFill" src="@/static/love-fly.svg"></image>
				<view class="logn-content">欢迎来到爱窝</view>
			</view>
			<view class="base-input">
				<textarea class="textareaStyle" v-model="love.loveMessage" placeholder="诉说告白" maxlength="100"
					cursor-spacing="75" />
			</view>
			<view class="base-input">
				<input type="text" v-model="love.loveName" placeholder="空间名称" maxlength="10" />
			</view>
			<view class="base-input">
				<picker mode="date" :value="love.loveTime" @change="changeloveTime">
					<input type="text" v-model="love.loveTime" placeholder="相恋时间" :disabled="true" />
				</picker>
			</view>
			<button class="lw-btn base_button" open-type="share">发送邀请</button>
			<view class="highlight">
				下拉刷新查看进度
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapActions,
		mapMutations
	} from 'vuex';
	export default {
		data() {
			return {
				love: {
					loveMessage: "",
					loveName: "",
					loveTime: ""
				},
			};
		},
		computed: {
			...mapGetters(['getIsLogin', 'getLoginUser', 'getLoveStartId'])
		},
		onLoad() {
			if (this.getLoveStartId != 0) {
				this.queryLoveStart();
			}
		},
		onShow() {

		},
		onPullDownRefresh() {
			if (this.getLoveStartId != 0) {
				console.info(111)
				this.queryLoveStart();
			}
		},
		async onShareAppMessage(res) {
			let that = this;
			if (that.getLoveStartId === 0) {
				let res = await that.sendLove()
			}
			return {
				title: that.love.loveMessage,
				imageUrl: 'https://lovestatic.jokeo.cn/love/202203/121629117546.jpg',
				path: 'pages/loveStart/acceptLove?loveId=' + that.getLoveStartId
			}
		},
		methods: {
			...mapMutations(['modifyLoveStartId']),
			...mapActions(['login','updateLoginUser']),
			async queryLoveStart() {
				let loveStart = await this.$u.api.queryLoveStart({
					loveId: this.getLoveStartId
				});
				this.love = loveStart;
				this.love.loveTime = this.$u.timeFormat(this.love.loveTime, 'yyyy-mm-dd');
				console.info(loveStart)
				if (loveStart.loveState === 0) {
					this.$refs.uToast.show({
						title: '对方还未接受邀请',
						type: 'primary'
					})
				} else if (loveStart.loveState === 1) {
					uni.switchTab({
						url: "/pages/index/index"
					})
				}
			},
			async sendLove() {
				console.info(this.love.loveName.trim())
				if (this.love.loveName.trim() === '') {
					this.$refs.uToast.show({
						title: '请填写恋爱空间名称',
						type: 'warning'
					})
					return false;
				}
				if (this.love.loveTime.trim() === '') {
					this.love.loveTime = this.$u.timeFormat(new Date().getTime(), 'yyyy-mm-dd');
				}
				if (this.love.loveMessage.trim() === '') {
					this.love.loveMessage = '我把我们的爱找了一个家';
				}
				let loveRes = await this.$u.api.createLoveStart(this.love);
				console.info(loveRes)
				this.getLoginUser.loveId = loveRes.loveId;
				this.updateLoginUser(this.getLoginUser);
				return true;
			},
			changeloveTime(event) {
				// console.info(event);
				this.love.loveTime = event.detail.value;
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $love-color;
	}

	.main {
		padding: 0 $love-view-padding;
		height: 100vh;
		width: 100vw;
		display: flex;
		flex-direction: column;
		// align-items: center;
		justify-content: center;
	}

	.logo {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: $love-padding 0rpx;

		image {
			height: 250rpx;
			width: 250rpx;
		}

		.logn-content {
			color: $love-color;
			font-size: 50rpx;
		}
	}

	.love-header {
		// margin-top: 150rpx;
		margin-bottom: 60rpx;
		text-align: center;
		color: white;
		font-weight: bold;
		font-size: 38rpx;
	}

	.love-wrapper {
		background-color: white;
		width: 100%;
		border-radius: $love-border-radius;
		text-align: center;
		padding: 30rpx 0;

		.textareaStyle {
			max-height: 150rpx;
			width: 100%;
		}

		.lw-btn {
			margin-top: 50rpx;
		}
	}
</style>
