<template>
	<view class="promise-main">
		<view class="" v-if="showEmpty">
			<u-empty text="哦no竟然没有任何纪念日" src="/static/in-love.svg" margin-top="250"></u-empty>
			<navigator url="/pages/anniversary/editAnniversary" hover-class="none" class="base_button">添加纪念日</navigator>
		</view>
		<view class="" v-else>
			<navigator class="now-anniversary top-anniversary" hover-class="none" :url="'/pages/promise/promiseInfo?promiseId=' + nowAnniversary.promiseId">
				<view class="anniversary-title">今天是{{ nowAnniversary.promiseName }}{{ nowAnniversary.annual }}周年</view>
				<view class="anniversary-info">
					<view class="anniversary-day">纪念日:{{ $u.timeFormat(nowAnniversary.targetTime, 'yyyy年mm月dd日') }}</view>
				</view>
			</navigator>
			<view class="anniversary-main">
				<navigator class="anniversary-item" v-for="(p, index) in promiseList" :key="index" hover-class="none" :url="'/pages/promise/promiseInfo?promiseId=' + p.promiseId">
					<view class="a-item-left">
						<u-icon name="integral-fill" size="50"></u-icon>
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
						<view class="" v-if="p.daysApart == 0">今天</view>
						<view class="" v-if="p.daysApart > 0">
							{{ p.daysApart }}
							<view class="day-tip">天后</view>
						</view>
						<!-- 	<u-tag v-if="p.daysApart == 0" text="今天" mode="light" shape="circle" type="success" />
						<u-tag v-if="p.daysApart > 0" :text="p.daysApart + '天后'" mode="light" shape="circle" type="primary" /> -->
					</view>
				</navigator>
			</view>
		</view>
		<u-toast ref="uToast" />
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
		// console.info(this.loveStart);
		this.init();
	},
	//下拉
	onPullDownRefresh() {
		this.getPromise();
	},
	//触底刷新
	onReachBottom() {
		this.getPromise();
	},
	methods: {
		init() {
			let that = this;
			this.$u.api
				.queryPromiseList({
					loveId: this.getLoveStartId,
					promiseType: 2,
					currPage: ++this.currPage,
					pageSize: 10
				})
				.then(res => {
					this.showEmpty = res.length === 0;
					this.nowAnniversary = res.shift();
					this.promiseList = res;
					console.info(this.nowAnniversary);
				});
		},
		//查询承诺
		getPromise() {
			let that = this;
			this.$u.api
				.queryPromiseList({
					loveId: this.getLoveStartId,
					promiseType: 2,
					currPage: ++this.currPage,
					pageSize: 10
				})
				.then(res => {
					this.promiseList = this.promiseList.concat(res);
				});
		}
	}
};
</script>
<style lang="scss">
page {
	background-color: $love-bgrey-color;
}
.promise-main {
	margin: $love-big-margin $love-view-margin;
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
	// margin: 0 $love-view-margin;

	.anniversary-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		// height: 120rpx;
		// background-color: rgba($color: $love-pink, $alpha: 1);
		background-color: #fff;
		margin: $love-view-margin 0;
		border-radius: $love-border-radius;
		padding: 38rpx $love-padding;
		// color: white;
	}

	.a-item-left {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.a-item-content {
		margin-left: 20rpx;
	}
	.a-item-name {
		font-weight: bold;
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
	.a-item-right{
		font-size: 40rpx;
		font-weight: bold;
	}
	.day-tip{
		font-size: 18rpx;
		text-align: right;
		font-weight: 500;
	}
}
.base_button {
	width: 50%;
}
</style>
