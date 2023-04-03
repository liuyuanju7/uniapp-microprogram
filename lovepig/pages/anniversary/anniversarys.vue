<template>
	<view class="main">
		<view class="now-anniversary top-anniversary" v-if="showNow">
			<!-- <image src="https://lovestatic.jokeo.cn/love/202112/162303464498.jpg" mode="aspectFill" class="anniversary-bg"></image> -->
			<view class="anniversary-title">今天是大宝贝{{ nowAnniversary.annual }}岁生日</view>
			<view class="anniversary-info">
				<view class="anniversary-day">纪念日:{{ $u.timeFormat(nowAnniversary.targetTime, 'yyyy年mm月dd日') }}</view>
			</view>
		</view>
		<!-- <view class="love-anniversary top-anniversary" v-if="!showNow">
			
		</view> -->
		<!-- 首页头部恋爱时间以及我们相册轮播 -->
		<view class="love-time" :style="'background-image:url(' + loveStart.banners[0].resourceUrl + ');'">
			<view class="love-time-info">
				<view>
					<u-avatar :src="loveStart.inviteUserInfo.avatar" size="80"></u-avatar>
					<view class="we-name">{{ loveStart.inviteUserInfo.nickname }}</view>
				</view>
				<view class="love-time-day">
					<view class="u-font-sm">我们已经相恋</view>
					<view style="color: #ff6773;">{{ loveStart.loveDay }}天</view>
				</view>
				<view>
					<u-avatar :src="loveStart.acceptedUserInfo.avatar" size="80"></u-avatar>
					<view class="we-name">{{ loveStart.acceptedUserInfo.nickname }}</view>
				</view>
			</view>
		</view>

		<view class="anniversary-main">
			<navigator class="anniversary-item" v-for="(p, index) in promiseList" :key="index" hover-class="none" :url="'/pages/promise/promiseInfo?promiseId=' + p.promiseId">
				<view class="a-item-left">
					<!-- <u-icon name="camera-fill" color="#ff6773" size="50"></u-icon> -->
					<view class="a-item-content">
						<view class="a-item-name">{{ p.promiseName }}</view>
						<view class="a-item-tip">
							<view class="last-day">
								<u-icon name="calendar"></u-icon>
								<view class="u-m-l-6">过去{{ p.pastDays }}天</view>
							</view>
							<view class="last-day">
								<u-icon name="heart"></u-icon>
								<view class="u-m-l-6">{{ p.annual }}周年</view>
							</view>
							<view class="last-day">
								<u-icon name="bell"></u-icon>
								<view class="u-m-l-6">提前{{ p.promiseRemind }}天</view>
							</view>
						</view>
					</view>
				</view>
				<view class="a-item-right">
					<u-tag v-if="p.daysApart == 0" text="今天" mode="light" shape="circle" type="success" />
					<u-tag v-if="p.daysApart > 0" :text="p.daysApart + '天后'" mode="light" shape="circle" type="primary" />
				</view>
			</navigator>
		</view>
		<u-loadmore :status="loadStatus" v-if="showLoadmore"></u-loadmore>
		<u-empty text="哦no竟然没有任何承诺" src="/static/in-love.svg" margin-top="250" v-if="showEmpty"></u-empty>
		<loading ref="lovdLoading"></loading>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			loveStart: {},
			showLoadmore: false,
			showEmpty: false,
			currPage: 0,
			loadStatus: 'loadmore',
			promiseList: [],
			nowAnniversary: {},
			showNow: false
		};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getLoveStartInfo'])
	},
	onLoad() {
		this.loveStart = this.getLoveStartInfo;
		console.info(this.loveStart);
		this.getPromise(true);
	},
	//下拉
	onPullDownRefresh() {},
	//触底刷新
	onReachBottom() {
		this.getPromise();
	},
	methods: {
		//查询承诺
		async getPromise(first) {
			let that = this;
			//查询思念留言
			let res = await this.$u.api.queryPromiseList({
				loveId: this.getLoveStartId,
				promiseType: 2,
				currPage: ++this.currPage,
				pageSize: 10
			});
			console.info(res);
			if (first) {
				this.nowAnniversary = res.push();
				this.promiseList = res;
			} else {
				this.promiseList = this.promiseList.concat(res);
			}
			that.$refs.lovdLoading.hidden();
			this.loadStatus = this.$pig.base.getloadMoreTip(res.length);
		},
		getNowAnniversary() {
			let nowA = this.promiseList[0];
			if (nowA.daysApart === 0) {
				this.nowAnniversary = nowA;
				this.showNow = true;
			}
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bgrey-color;
}

.main {
}

.love-time {
	margin: $love-big-margin $love-view-margin;
	display: flex;
	justify-content: space-around;
	position: relative;
	text-align: center;
	height: 380rpx;
	background-size: cover;
	border-radius: $love-border-radius;
	padding-top: $love-padding;
	color: white;

	// view {
	// 	z-index: 100;
	// }
	.love-time-info {
		display: flex;
		background-color: rgba($color: #fff, $alpha: 0.3);
		justify-content: space-around;
		width: 80%;
		height: 145rpx;
		border-radius: 80rpx;
		padding: $love-padding 0;
	}

	.love-time-day {
		margin-top: 10rpx;
		font-size: 40rpx;
	}

	.we-name {
		font-size: 24rpx;
	}

	&:after {
		position: absolute;
		left: 0;
		top: 0;
		content: '';
		background: white;
		width: 100%;
		height: 100%;
		opacity: 0;
		border-radius: 0 0 $love-border-radius $love-border-radius;
	}
}

.top-anniversary {
	height: 300rpx;
	position: relative;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	text-align: center;
	color: white;
	margin: $love-big-margin $love-view-margin;
	border-radius: $love-border-radius;
}

.love-anniversary {
	background-color: $love-color;
}

.now-anniversary {
	background-image: url('https://lovestatic.jokeo.cn/love/202112/162303464498.jpg');
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center;

	z-index: 10;

	.anniversary-title {
		font-weight: bold;
		font-size: 42rpx;
	}

	.anniversary-info {
		margin-top: $love-view-margin;
	}

	&:after {
		position: absolute;
		left: 0;
		top: 0;
		content: '';
		background: rgba($color: #000, $alpha: 0.1);
		z-index: -1;
		width: 100%;
		height: 100%;
		border-radius: $love-border-radius;
	}
}

.anniversary-main {
	margin: 0 $love-view-margin;

	.anniversary-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 120rpx;
		// background-color: rgba($color: $love-pink, $alpha: 1);
		background-color: white;
		margin: $love-view-margin 0;
		border-radius: $love-border-radius;
		padding: 0 $love-padding;
	}

	.a-item-left {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.a-item-content {
		margin-left: 20rpx;
	}
	.a-item-name{
		font-weight: 600;
	}

	.a-item-tip {
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 18rpx;
		color: $u-tips-color;
		text-align: center;
		// color: white;
		margin-top: 10rpx;

		& > view {
			display: flex;
			align-items: center;
			justify-content: center;
			margin-right: 12rpx;
		}
	}
}
</style>
