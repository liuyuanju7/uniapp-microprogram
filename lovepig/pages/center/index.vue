<template>
	<view>
		<!-- 顶部图片 -->
		<view class="top-img"><image class="fengrui-img" src="../../static/images/me-top.svg" mode="aspectFill"></image></view>

		<!-- 头像 -->
		<view class="my-maim" @click="lookMe">
			<view class="my-avatar"><u-avatar :src="userInfo.avatar"></u-avatar></view>
			<view class="my-info">
				<view class="my-info-name">{{ userInfo.nickname }}</view>
				<view class="my-info-motto">{{ userInfo.motto }}</view>
			</view>
		</view>
		<view class="memories-wrap" v-for="(ci, cIndex) in memoriesItems" :key="cIndex">
			<view class="header-wrap">
				<view class="header-title">{{ ci.contentTitle }}</view>
				<view class="header-tip">{{ ci.contentTip }}</view>
			</view>
			<u-row gutter="16">
				<u-col span="3" v-for="(item, index) in ci.contentItems" :key="index">
					<view hover-class="none" @click="openSendView(cIndex, index)">
						<view class="mode_item">
							<image class="mode_image" :src="item.icon" mode="aspectFill"></image>
							<view class="mode_title">{{ item.title }}</view>
						</view>
					</view>
				</u-col>
			</u-row>
		</view>
		<view class="nav-main">
			<view class="nav-item" v-for="(item, index) in list" :key="index" hover-class="none" @click="onClick(item)">
				<view class="nav-i-left">
					<u-icon :name="item.icon" size="45" color="#757575"></u-icon>
					<view class="nav-i-title">{{ item.name }}</view>
				</view>
				<view class="nav-i-right"><u-icon name="arrow-right" size="30" class="p-right-icon"></u-icon></view>
			</view>
		</view>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
import { mapGetters, mapState, mapMutations } from 'vuex';
export default {
	data() {
		return {
			memoriesItems: [
				{
					contentTitle: '点滴',
					contentTip: '"生活的点点滴滴都在这里啦~"',
					contentItems: [
						{
							title: '故事',
							pageUrl: '/pages/story/index',
							icon: '/static/icon/memoriesPage/story1.png'
						},
						{
							title: '留言',
							pageUrl: '/pages/message/index',
							icon: '/static/icon/memoriesPage/message.png'
						},
						{
							title: '纪念日',
							pageUrl: '/pages/anniversary/anniversarys',
							icon: '/static/icon/memoriesPage/time.png'
						},
						{
							title: '承诺',
							pageUrl: '/pages/promise/index',
							icon: '/static/icon/memoriesPage/promise.png'
						},
						{
							title: '照片',
							pageUrl: '/pages/album/index',
							icon: '/static/icon/memoriesPage/photo.png'
						},
						// {
						// 	title: '视频',
						// 	pageUrl: '/pages/memories/videos',
						// 	icon: '/static/icon/memoriesPage/video.png'
						// }
					]
				}
			],
			list: [
				{
					name: '空间装饰',
					id: 'loveRoom',
					icon: 'star-fill',
					pageUrl: '/pages/center/loveRoom',
					iconBackground: '#398c0c'
				},
				{
					name: '设置',
					id: 'setUp',
					icon: 'setting-fill',
					iconBackground: '#3b2021'
				}
			],
			userInfo: null
		};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getIsLogin', 'getLoginUser'])
	},
	onLoad() {
		// console.info(this.getIsLogin);
		// console.info(this.getLoveStartId);
		// console.info(this.getLoginUser);
		this.userInfo = this.getLoginUser;
	},
	methods: {
		...mapMutations(['modifyLoveStartInfo']),
		//跳转到发送故事的页面
		openSendView(contentIndex, itemIndex) {
			let that = this;
			let contentItems = that.memoriesItems[contentIndex].contentItems;
			let pageUrl = contentItems[itemIndex].pageUrl;
			console.info(pageUrl);
			if (pageUrl == '') {
				that.$refs.uToast.show({
					title: '该功能正在开发中~',
					type: 'primary'
				});
			} else {
				uni.navigateTo({
					url: pageUrl
				});
			}
		},
		lookMe() {
			if (!this.getIsLogin) {
				this.$user.toLogin();
			} else {
				this.$refs.uToast.show({
					title: '该功能正在开发中~',
					type: 'primary'
				});
			}
		},
		onClick(item) {
			console.info(item);
			uni.navigateTo({
				url: item.pageUrl
			});
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bg-color;
}
.top-img {
	width: 100%;
	height: 220rpx;
	overflow: hidden;
}
.my-maim {
	display: flex;
	align-items: center;
	height: 180rpx;
	background-color: white;
	padding: $love-padding;
	margin: $love-view-margin;
	margin-top: 0;
	border-radius: $love-border-radius;

	.my-avatar {
		margin-right: 30rpx;
	}
}
.my-info {
	.my-info-name {
		color: $u-main-color;
		font-weight: bold;
		font-size: 36rpx;
		margin-bottom: 10rpx;
	}
	.my-info-motto {
		color: $u-content-color;
	}
}

.nav-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	background-color: #ffffff;
	margin: $love-view-margin;
	border-radius: $love-border-radius;
	height: 90rpx;
	.nav-i-left {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-left: $love-view-margin;
	}
	.nav-i-title {
		font-size: 34rpx;
		font-weight: 500;
		padding-left: $love-padding;
	}
	.nav-i-right {
		padding: 0 30rpx;
	}
}

.memories-wrap {
	// background-color: red;
	margin: $love-view-margin 0;
}
.header-wrap {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 30rpx;
	.header-title {
		font-size: 34rpx;
		font-weight: bold;
		color: $u-main-color;
		margin: 0 30rpx;
	}
	.header-tip {
		color: $u-tips-color;
		font-size: 18rpx;
	}
}


.mode_item {
	text-align: center;
	border-radius: $love-border-radius;
	margin-bottom: 30rpx;
	.mode_title {
		// color: $u-content-color;
		// margin: 4rpx 0;
	}
	.mode_image {
		width: 66rpx;
		height: 66rpx;
	}
}

</style>
