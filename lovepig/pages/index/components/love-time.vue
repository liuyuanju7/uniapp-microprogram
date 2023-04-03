<template>
	<view class="love-time-info">
		<view>
			<u-avatar :src="getLoveStartInfo.inviteUserInfo.avatar" size="80"></u-avatar>
			<view class="we-name">{{ getLoveStartInfo.inviteUserInfo.nickname }}</view>
		</view>
		<view class="love-time-day">
			<view class="u-font-sm">我们已经相恋</view>
			<view style="color: #ff6773;">{{ getLoveStartInfo.loveDay }}天</view>
		</view>
		<view>
			<u-avatar :src="getLoveStartInfo.acceptedUserInfo.avatar" size="80"></u-avatar>
			<view class="we-name">{{ getLoveStartInfo.acceptedUserInfo.nickname }}</view>
		</view>
	</view>
</template>

<script>
import { mapGetters, mapState, mapMutations } from 'vuex';
export default {
	name: "LoveTime",
	data() {
		return {};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getLoveStartInfo', 'getIsLogin', 'getLoginUser'])
	},
	onLoad() {
	},
	methods: {
	}
};
</script>

<style lang="scss" scoped>
.love-time-info {
	z-index: 10;
	display: flex;
	justify-content: space-around;
	background-color: rgba($color: #fff, $alpha: 0.3);
	width: 80vw;
	// height: 150rpx;
	margin-top: 30%;
	border-radius: 80rpx;
	padding: $love-padding 0;
	text-align: center;
	backdrop-filter: blur(8px);
}

.love-time-day {
	margin-top: 10rpx;
	font-size: 40rpx;
}

.we-name {
	font-size: 24rpx;
	// color: #000;
}
</style>
