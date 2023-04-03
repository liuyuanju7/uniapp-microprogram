<template>
	<view class="main">
		<u-toast ref="uToast" />
		<view class="love-wrapper">
			
			<view class="love-user">
				<u-avatar :src="love.inviteUserInfo.avatar" size="large"></u-avatar>
				<view class="love-user-name">{{love.inviteUserInfo.nickname}}</view>
			</view>
			<view class="love-info">
				<view class="love-info-msg">
					对你说:"{{love.loveMessage}}?"
				</view>
				<view class="love-info-tip">
					您是否愿意接收?
				</view>
			</view>

			<button class="lw-btn base_button" @click="accept()">接收邀请</button>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapMutations,
		mapActions
	} from 'vuex';
	export default {
		data() {
			return {
				loveId: 0,
				love: {}
			}
		},
		onLoad(e) {
			this.loveId = e.loveId;
			this.queryLoveStart();
		},
		computed: {
			...mapGetters(['getIsLogin', 'getIsNewUser', 'getLoginUser', 'getLoginUserId', 'getLoveStartInfo'])
		},
		methods: {
			...mapActions(['login', 'updateLoginUser']),
			async queryLoveStart() {
				let loveStart = await this.$u.api.queryLoveStart({
					loveId: this.loveId
				});
				//恋爱空间已经被接收
				if (loveStart.loveState == 1) {
					console.log("恋爱空间已经被接收")
					uni.switchTab({
						url: "/pages/index/index"
					})
					return;
				}
				//如果发起者进入页面直接跳转到创建页面
				if (this.getLoginUserId === loveStart.inviteUser) {
					console.log("发起者进入页面直接跳转到创建页面")
					uni.redirectTo({
						url: "/pages/loveStart/sendLove"
					})
					return;
				}

				console.log(loveStart)
				this.love = loveStart;
				this.love.loveTime = this.$u.timeFormat(this.love.loveTime, 'yyyy-mm-dd');
			},
			async accept() {
				//如果是没有登录并且是新用户,走登录流程
				if (!this.getIsLogin && this.getIsNewUser) {
					console.log("当前用户没有登录并且是新用户,走登录流程")
					let res = await this.login();
					console.log(res)
				}
				//如果发起者进入页面直接跳转到创建页面
				if (this.getLoginUserId === this.getLoveStartInfo.inviteUser) {
					uni.redirectTo({
						url: "/pages/loveStart/sendLove"
					})
					return;
				}
				this.$u.api.acceptLove({
					loveId: this.loveId,
					acceptUser: this.getLoginUserId,
					inviteUser: this.love.inviteUser
				}).then(res => {
					//修改本地信息
					this.getLoginUser.loveId = this.loveId;
					this.getLoginUser.spouseId = this.love.inviteUser;
					this.updateLoginUser(this.getLoginUser);
					uni.switchTab({
						url: "/pages/index/index"
					})
				});
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

	.love-wrapper {
		background-color: white;
		width: 100%;
		border-radius: $love-border-radius;
		text-align: center;
		padding: 30rpx 50rpx;

		&>view {
			margin: 30rpx 0;
		}

		.love-user-name {
			margin-top: 10rpx;
		}

		.love-info {

			// margin-top: 30rpx;
			.love-info-tip {
				margin-top: 10rpx;
				color: $u-tips-color;
				font-size: 24rpx;
			}
		}

		.lw-btn {
			margin-top: 50rpx;
		}


	}
</style>
