<template>
	<view class="memories-main">
		<view class="memories-wrap" v-for="(ci, cIndex) in memoriesItems" :key="cIndex">
			<view class="header-wrap">
				<view class="header-title">{{ci.contentTitle}}</view>
				<view class="header-tip">{{ci.contentTip}}</view>
			</view>
			<u-row gutter="16">
				<u-col span="3" v-for="(item, index) in ci.contentItems" :key="index">
					<view hover-class="none" @click="openSendView(cIndex,index)">
						<view class="mode_item">
							<image class="mode_image" :src="item.icon" mode="aspectFill"></image>
							<view class="mode_title">{{ item.title }}</view>
						</view>
					</view>
				</u-col>
			</u-row>
		</view>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			memoriesItems: [
				{
					contentTitle:"点滴",
					contentTip:'"生活的点点滴滴都在这里啦~"',
					contentItems:[
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
						{
							title: '视频',
							pageUrl: '/pages/memories/videos',
							icon: '/static/icon/memoriesPage/video.png'
						}
					]
				}
				
			]
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	onLoad() {},
	onShow() {},
	methods: {
		//跳转到发送故事的页面
		openSendView(contentIndex,itemIndex) {
			let that = this;
			let contentItems = that.memoriesItems[contentIndex].contentItems;
			let pageUrl = contentItems[itemIndex].pageUrl;
			console.info(pageUrl)
			if(pageUrl==''){
				that.$refs.uToast.show({
					title: '该功能正在开发中~',
					type: 'primary'
				})
			}else{
				uni.navigateTo({
					url: pageUrl
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
.memories-main{
	margin: $love-big-margin $love-view-margin;
}
.memories-wrap {
	background-color: white;
	margin-bottom: $love-big-margin;
	border-radius: $love-border-radius;
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
}
.mode_title {
	color: $u-content-color;
	margin: 4rpx 0;
}
.mode_image {
	width: 80rpx;
	height: 80rpx;
}
</style>
