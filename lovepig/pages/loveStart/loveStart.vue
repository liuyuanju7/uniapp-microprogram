<template>
	<view class="main">
		<view class="logo">
			<image mode="scaleToFill" src="@/static/love-fly.svg"></image>
			<view class="logn-content">欢迎来到爱窝</view>
		</view>
		<view v-show="needLogin">
			<view class="login-tip">爱窝是个人空间,访问操作需要登录</view>
			<view>
				<view class="login-item" v-if="isNewUser" @click="getWxUserProfile">
					<view class="login-item-user">
						<view class="u-margin-right-30"><u-avatar :src="loginUserInfo.avatar"></u-avatar></view>
						<view class="login-item-text">
							<view class="u-font-xl">微信登录</view>
							<view class="u-content-color">{{ loginUserInfo.nickname }}</view>
						</view>
					</view>
					<view><u-icon name="arrow-right"></u-icon></view>
				</view>
				<view class="login-item" v-if="!isNewUser">
					<view class="login-item-user">
						<view class="u-margin-right-30"><u-avatar :src="loginUserInfo.avatar"></u-avatar></view>
						<view class="login-item-text">
							<view class="u-font-xl">微信登录</view>
							<view class="u-content-color">{{ loginUserInfo.nickname }}</view>
						</view>
					</view>
					<view><u-icon name="arrow-right"></u-icon></view>
				</view>
			</view>
		</view>
		<view v-show="!needLogin">
			<view class="login-user-info">
				<view class="login-item-user">
					<u-avatar :src="loginUserInfo.avatar"></u-avatar>
					<view>{{ loginUserInfo.nickname }}</view>
				</view>
			</view>
			<view class="">
				<navigator class="buffer-btn" v-if="state.loginUser.loveId === 0" hover-class="none" url="/pages/loveStart/sendLove">创建爱窝</navigator>
				<navigator class="buffer-btn" v-if="state.loginUser.loveId != 0 && state.loginUser.spouseId === 0" hover-class="none" url="/pages/loveStart/sendLove">
					查看邀请
				</navigator>
				<navigator open-type="switchTab" class="buffer-btn" v-if="sstate.loginUser.loveId != 0 && state.loginUser.spouseId != 0" hover-class="none" url="/pages/index/index">
					进入爱窝
				</navigator>
				<view class="buffer-btn">随机访问</view>
			</view>
		</view>
	</view>
</template>

<script>
import { mapGetters, mapState, mapActions, mapMutations } from 'vuex';
export default {
	data() {
		return {
			loginUserInfo: {
				avatar: '/static/in-love.svg',
				nickname: '点击登录'
			},
			token: '',
			isNewUser: false,
			needLogin: true,
			wxUserInfo: {}
		};
	},
	computed: {
		...mapGetters(['getIsLogin', 'getIsNewUser', 'getLoginUser'])
	},
	onLoad() {
		if (this.getIsLogin) {
			console.info('已经登录过');
			this.loginUserInfo = this.getLoginUser;
			this.needLogin = false;
			//判断用户当前状态,跳转指定页面
			// this.navigatePath();
		} else if (this.getIsNewUser) {
			this.isNewUser = this.getIsNewUser;
			console.info('(新用户)没有登录,走登录流程,等待用户操作');
		} else {
			console.info('(老用户)没有登录,走登录流程');
			//如果没有登录,就走登录流程
			this.wxlogin();
		}
	},
	methods: {
		...mapMutations(['modifyToken', 'modifyLoveStartId']),
		...mapActions(['login', 'updateLoginUser']),
		/**
		 * 1.
		 * 一打开登录页面先预订登录
		 * 如果不是新用户就登录成功了
		 * 如果是新用户就设置下,
		 * 显示获取用户登录信息登录的按钮
		 * 老用户流程1-2(默认)
		 * 新用户1-2-(3-1-4)
		 * @param {Object} userInfo
		 */
		wxlogin(userInfo) {
			let that = this;
			uni.login({
				provider: 'weixin',
				success: function(res) {
					if (res.code) {
						if (that.isNewUser) {
							that.wechatRegistered(res.code, userInfo);
						} else {
							that.wechatLogin(res.code);
						}
					} else {
						console.log('获取登录code失败!!');
					}
				}
			});
		},
		//2.预登录-正常登录
		wechatLogin(code) {
			this.$u.api
				.wechatLogin({
					appid: this.appid,
					code: code
				})
				.then(res => {
					console.info(res);
					if (res.newUser) {
						console.info('该用户是新用户需要获取信息登录');
						this.isNewUser = true;
					} else {
						this.goLogin(res);
					}
				});
		},
		//3.获取信息,用户注册登录
		getWxUserProfile(e) {
			wx.getUserProfile({
				desc: '用于完善您的登录信息',
				success: res => {
					// console.log(res);
					this.wxlogin(res.userInfo);
				},
				fail: res => {
					console.log(res);
				}
			});
		},
		//4.注册登录
		wechatRegistered(code, userInfo) {
			let that = this;
			this.$u.api
				.wechatRegister({
					appid: this.appid,
					code: code,
					nickname: userInfo.nickName,
					gender: userInfo.gender,
					avatar: userInfo.avatarUrl
				})
				.then(res => {
					console.info(res);
					//把token放到缓存中
					this.goLogin(res);
					this.navigatePath();
				});
		},
		/**
		 * 老用户用户点击登录
		 */
		goLogin(res) {
			//把token放到缓存中
			this.modifyToken(res.token);
			this.updateLoginUser(res.loginUser);
			this.loginUserInfo = res.loginUser;
			this.needLogin = false;
		},
		navigatePath(res) {
			//1.如果没有创建空间跳转到loveStart
			//2.如果已经创建跳转到邀请页面
			//3.如果已经有对象直接跳转首页
			if (this.loginUserInfo.loveId === 0) {
				console.info('如果没有创建空间跳转到loveStart');
				// uni.reLaunch({
				// 	url: "/pages/loveStart/loveStart"
				// })
			} else if (this.loginUserInfo.spouseId === 0) {
				console.info('如果已经创建跳转到邀请页面');
				uni.reLaunch({
					url: '/pages/loveStart/sendLove'
				});
			} else {
				console.info('如果已经有对象直接跳转首页');
				uni.switchTab({
					url: '/pages/index/index'
				});
			}
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bg-color;
}

.main {
	margin: $love-big-margin;
}

.logo {
	display: flex;
	justify-content: center;
	align-items: center;
	background-color: white;
	padding: $love-padding 0rpx;
	border-radius: $love-border-radius;
	box-shadow: 5rpx 5rpx 5rpx 3rpx #f0f0f0;

	image {
		height: 250rpx;
		width: 250rpx;
	}

	.logn-content {
		color: $love-color;
		font-size: 50rpx;
	}
}

.login-tip {
	text-align: center;
	color: $u-content-color;
	margin: $love-view-margin 0;
	padding: 20rpx 0;
	background-color: $u-type-primary-light;
	border-radius: $love-border-radius;
}

.login-item {
	display: flex;
	justify-content: space-between;
	padding: 30rpx;
	align-items: center;
	height: 200rpx;
	background-color: white;
	margin: $love-margin 0;
	border-radius: $love-border-radius;

	.login-item-user {
		display: flex;

		.login-item-text {
			display: flex;
			flex-direction: column;
			justify-content: center;
		}
	}
}

.need-login {
	background-color: white;
	text-align: center;
	padding: 20rpx 0;
	border-radius: $love-border-radius;
	margin: 50rpx auto;

	.need-login-tip {
		font-size: 20rpx;
	}
}

.login-user-info {
	text-align: center;
	padding: 20rpx 0;
	margin: 50rpx auto;
}

.buffer-btn {
	text-align: center;
	padding: $love-big-padding 0;
	background-color: white;
	margin: $love-view-margin 0;
	border-radius: $love-border-radius;
}
</style>
