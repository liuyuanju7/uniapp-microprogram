<template>
	<view class="myInfo">

		<view class="headBox"
			:style="{background:'linear-gradient(to left top,'+PrimaryColor+','+freeSpecsButtonBackground+')',paddingTop:systemInfo.navBarH+'px'}">

			<!-- 登录 -->
			<view class="u-flex u-p-l-30 u-p-r-20 u-p-t-30 u-p-b-30">
				<view class="u-m-r-20">
					<view class="avatar u-flex" style="justify-content: center;">
						<u-icon name="account-fill" color="#fff" size="30"></u-icon>
					</view>
				</view>
				<view class="u-flex-1" @click="openLogin">
					<view class="u-font-lg" style="color: #fff;font-weight: bold;">登录 / 注册</view>
					<view class="detail">登录后享受更好的服务体验</view>
				</view>
				<view>
					<u-icon name="arrow-right" color="#fff" size="13"></u-icon>
				</view>
			</view>

			<!-- 会员卡 -->
			<view class="vipBox">
				<view class="card">
					<view class="left">
						<view class="title">VIP</view>
						<view class="tips" v-if="isVIP">尊贵的会员，您好！</view>
						<view class="tips" v-else>成为会员，享受更好的服务体验~</view>
					</view>
					<view class="right">
						<view class="button" v-if="isVIP">会员中心</view>
						<view class="button" v-else>成为会员</view>
					</view>
				</view>
			</view>
		</view>
		<view class="m_top m_list_bg m_card">
			<u-grid :border="false" @click="click" col="4" style="padding: 30rpx 0;">
				<u-grid-item>
					<view>
						<u-text  :bold="true" text="100"></u-text>
					</view>
					<view style="margin-top: 10rpx;">
						<u-text size="13" text="鲵宝"></u-text>
					</view>

				</u-grid-item>
				<u-grid-item>
					<view>
						<u-text  :bold="true" text="100"></u-text>
					</view>
					<view style="margin-top: 10rpx;">
						<u-text size="13" text="鲵豆"></u-text>
					</view>
				</u-grid-item>

				<u-grid-item>
					<view>
						<u-text  :bold="true" text="100"></u-text>
					</view>
					<view style="margin-top: 10rpx;">
						<u-text size="13" text="充值积分"></u-text>
					</view>
				</u-grid-item>
				<u-grid-item>
					<view>
						<u-text  :bold="true" text="100"></u-text>
					</view>
					<view style="margin-top: 10rpx;">
						<u-text size="13" text="消费积分"></u-text>
					</view>
				</u-grid-item>
			</u-grid>

		</view>
		<view class="main">
			<view class="itemBox" @click="checkOpen('/pages/order/order')">
				<view class="titleBox u-flex">
					<view class="title u-flex-m">我的订单</view>
					<view class="word">全部订单</view>
					<u-icon name="arrow-right" :size="13" color="#999"></u-icon>
				</view>
				<u-grid :col="5" :border="false">
					<u-grid-item v-for="(item,index) in order" :key="index">
						<view class="u-flex u-p-t-30 u-p-b-30" style="flex-direction: column;justify-content: center;">
							<u-icon :name="item.icon" :size="28" :color="PrimaryColor"></u-icon>
							<view class="grid-text">{{item.word}}</view>
						</view>
					</u-grid-item>
				</u-grid>
			</view>
			<view class="itemBox" @click="onTokenJump('pages/order/order')">
				<view class="titleBox u-flex">
					<view class="title u-flex-m">餐饮订单</view>
					<view class="word">全部订单</view>
					<u-icon name="arrow-right" :size="13" color="#999"></u-icon>
				</view>
				<u-grid :col="3" :border="false">
					<u-grid-item >
						<view class="u-flex u-p-t-30 u-p-b-30" style="flex-direction: column;justify-content: center;">
							<u-icon name="/static/my/daifukuan.png" :size="28" ></u-icon>
							<view class="grid-text">待付款</view>
						</view>
						
					</u-grid-item>
					<u-grid-item >
						<view class="u-flex u-p-t-30 u-p-b-30" style="flex-direction: column;justify-content: center;">
							<u-icon name="/static/my/shouhuo.png" :size="28" ></u-icon>
							<view class="grid-text">已接单</view>
						</view>
						
					</u-grid-item>
					<u-grid-item >
						<view class="u-flex u-p-t-30 u-p-b-30" style="flex-direction: column;justify-content: center;">
							<u-icon name="/static/my/yishou.png" :size="28" ></u-icon>
							<view class="grid-text">已完成</view>
						</view>
						
					</u-grid-item>
					
				</u-grid>
			</view>
			<view class="itemBox">
				<view class="titleBox u-flex" style="border: none;padding-bottom: 0;">
					<view class="title">功能与服务</view>
				</view>
				<u-grid :col="5" :border="false">
					<u-grid-item v-for="(item,index) in moreFun" :key="index" @click="navClick(item.onPlate)">
						<view class="u-flex u-p-t-30 u-p-b-30"
							style="position: relative;flex-direction: column;justify-content: center;">
							<image style="width: 70rpx;height: 70rpx;" :src="item.icon" />
							<view class="grid-text" style="color: #666;font-size: 22rpx;">{{item.word}}</view>
						
						</view>
					</u-grid-item>
				</u-grid>
			</view>
			<view class="m_top  m_card" style="">
				<view style="width: 60%;margin: auto;padding: 50rpx;">
					<u-button  icon="close" text="退出"></u-button>
				</view>	
			</view>
			
		</view>


	</view>
</template>

<script>
	export default {

		data() {
			return {
				baseUrl: 'base.baseUrl',
				systemInfo: 'base.systemInfo',
				scrollTop: 0,
				PrimaryColor: '#f7403f', //主题色
				freeSpecsButtonBackground: '',
				isVIP: false,
				// 订单
				order: [{
					icon: '/static/my/daifukuan.png',
					word: '待付款'
				}, {
					icon: '/static/my/fahuo.png',
					word: '待配送'
				}, {
					icon: '/static/my/shouhuo.png',
					word: '待收货'
				}, {
					icon: '/static/my/yishou.png',
					word: '已收货'
				}, {
					icon: '/static/my/kefu.png',
					word: '售后'
				}],
				moreFun: [{
						icon: '/static/my/info.png',
						word: '个人资料',
						onPlate: 'goAbout',
					},{
						icon: '/static/my/addr.png',
						word: '收货地址',
						onPlate: 'goMyAddressList',
					}, {
						icon: '/static/my/team.png',
						word: '我的团队',
						onPlate: 'goAbout',
					},
					// {
					// 	icon: '/static/my/my.png',
					// 	word: '客服帮助',
					// 	onPlate: 'onHelp',
					// },
					{
						icon: '/static/my/yijian.png',
						word: '意见反馈',
						onPlate: 'goFeedback',
					}, {
						icon: '/static/my/about.png',
						word: '关于我们',
						onPlate: 'goAbout',
					}, {
						icon: '/static/my/share.png',
						word: '我要推广',
						onPlate: 'goAbout',
					}, {
						icon: '/static/my/set.png',
						word: '设置',
						onPlate: 'goAbout',
					}, 
				],
				userInfo: {
					token: '1'
				}
			}
		},
		computed: {

		},
		onLoad() {

		},
		methods: {

			open(){
				
			},
			checkOpen(url){
				console.log(url);
				uni.navigateTo({
					url:url
				})
			},
			openLogin() {
				this.judgeLogin()
			},
			navClick(e) {
				var url = ''
				if (e == 'goMyAddressList') {
					this.$u.toast('您点击了收货地址~')
				} else if (e == 'goCashCouponList') {
					this.$u.toast('您点击了我的优惠券~')
				} else if (e == 'onHelp') {
					// #ifndef MP-WEIXIN
					console.log('小程序客服~')
					// #endif
				} else if (e == 'goFeedback') {
					this.$u.toast('您点击了意见反馈~')
				} else if (e == 'goAbout') {
					this.$u.toast('您点击了关于我们~')
				}
				// url!='' && this.onJump(url)
			},
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
	}
</script>

<style lang="scss" scoped>
	.myInfo {
		// 这里设置高度，上拉显示菜单栏---正式环境删除
		// min-height: 2000rpx;
	}

	.headBox {
		padding-top: 82rpx;
		background: linear-gradient(to left top, #f32735, #fc674d);
		border-radius: 50% / 0 0 5% 5%;
		overflow: hidden;

		.avatar {
			width: 50px;
			height: 50px;
			border-radius: 25px;
			background-color: #ccc;
		}

		.nickName {
			.btn {
				font-size: 22rpx;
				font-weight: normal;
				color: #666;
				background: #fff;
				border-radius: 5rpx;
				height: 45rpx;
				line-height: 45rpx;
				padding: 0 10rpx;
				position: relative;

				.itemButton {
					border-radius: 0;
					text-align: left;
					opacity: 0;
					width: 100%;
					height: 100%;
					position: absolute;
					left: 0;
					top: 0;
				}
			}

			.name {
				color: #fff;
				font-weight: bold;
				font-size: 32rpx;
			}

			.placardVip {
				background: #2a2e44;
				color: #f4d6a1;
				font-size: 22rpx;
				padding: 4rpx 10rpx;
				text-align: center;
				border-radius: 4rpx;
			}

		}

		.detail {
			color: #fff;
			font-size: 24rpx;
			padding-top: 6rpx;
		}

		.vipBox {
			padding: 0 24rpx;

			.card {
				background-image: linear-gradient(to right, #314177, #202646);
				padding: 16rpx 32rpx 24rpx 32rpx;
				border-top-left-radius: 30rpx;
				border-top-right-radius: 30rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;

				.left {
					display: flex;
					flex-direction: row;
					align-items: center;

					.title {
						font-size: 40rpx;
						font-weight: bold;
						font-style: italic;
						color: #f9bd90;
					}

					.tips {
						font-size: 24rpx;
						color: #f9bd90;
						margin-left: 20rpx;
					}

				}

				.right {
					.button {
						padding: 8rpx 16rpx;
						color: 212849;
						border-radius: 30rpx;
						background: #f9bd90;
						font-size: 24rpx;
					}
				}
			}
		}
	}

	.main {
		padding: 0 24rpx;
	}

	.itemBox {
		background: #fff;
		padding: 0 24rpx;
		border-radius: 20rpx;
		overflow: hidden;
		margin-top: 24rpx;

		.titleBox {
			padding: 32rpx 0;
			border-bottom: 1rpx solid #eee;

			.title {
				font-size: 28rpx;
				font-weight: bold;
			}

			.word {
				font-size: 24rpx;
				color: #999;
			}
		}

		.grid-text {
			font-size: 24rpx;
			color: #333;
			padding-top: 10rpx;
		}
	}

	.u-font-lg {
		font-size: 38rpx;
	}
</style>
