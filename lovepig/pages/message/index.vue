<template>
	<view class="main">
		<u-waterfall v-model="messages" ref="messagesWaterfall" add-time="0">
			<template v-slot:left="{ leftList }">
				<!-- :style="item.userBase.gender == 2 ? 'background-color: #f1d6dd' : 'background-color: #a6edf3'" -->
				<view class="message-item" v-for="(item, index) in leftList" :key="index">
					<view class="message-header">
						<u-avatar :src="item.userBase.avatar" size="50"></u-avatar>
						<view class="username">{{ item.userBase.nickname }}</view>
					</view>
					<view class="message-content">{{ item.messageContent }}</view>
					<view class="message-time">
						<u-tag :bg-color="item.userBase.gender == 2 ? '#ff6773' : '#2979ff'" :text="item.createTime" mode="dark" shape="circle" size="mini" />
					</view>
				</view>
			</template>
			<template v-slot:right="{ rightList }">
				<view class="message-item" v-for="(item, index) in rightList" :key="index">
					<view class="message-header">
						<u-avatar :src="item.userBase.avatar" size="50"></u-avatar>
						<view class="username">{{ item.userBase.nickname }}</view>
					</view>
					<view class="message-content">{{ item.messageContent }}</view>
					<view class="message-time">
						<u-tag :bg-color="item.userBase.gender == 2 ? '#ff6773' : '#2979ff'" :text="item.createTime" mode="dark" shape="circle" size="mini" />
					</view>
				</view>
			</template>
		</u-waterfall>

		<u-loadmore :status="loadStatus" height="200" v-if="messages.length > 19"></u-loadmore>
		<u-empty text="哦no竟然没有任何留言" src="/static/in-love.svg" margin-top="50" :show="showEmpty"></u-empty>
		<!-- 加载动画 -->
		<loading ref="lovdLoading"></loading>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			showEmpty: false,
			currPage: 0,
			loadStatus: 'loadmore',
			leftList: [],
			rightList: [],
			messages: []
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	onLoad() {
		this.init();
	},
	//下拉
	onPullDownRefresh() {},
	//触底刷新
	onReachBottom() {
		if (this.loadStatus === 'nomore') {
			return;
		}
		this.loadStatus = 'loading';
		this.getMessages();
	},
	methods: {
		async init() {
			let that = this;
			//查询思念留言
			let res = await this.$u.api.queryMessageList({
				loveId: this.getLoveStartId,
				messageType: 1,
				currPage: ++this.currPage,
				pageSize: 20
			});
			// console.info(res);
			let data = res.data;
			data.forEach(d => {
				d.createTime = that.$u.timeFrom(d.createTime, 'yyyy-mm-dd');
			});
			that.messages = data;
			that.showEmpty = that.messages.length == 0;
			that.loadStatus = that.$pig.base.getloadMoreTip(res.data.length);
			//加载动画过度
			this.$refs.lovdLoading.hidden();
		},
		//查询留言
		getMessages() {
			let that = this;
			//查询思念留言
			this.$u.api
				.queryMessageList({
					loveId: this.getLoveStartId,
					messageType: 1,
					currPage: ++this.currPage,
					pageSize: 20
				})
				.then(res => {
					// console.info(res);
					let data = res.data;
					data.forEach(d => {
						d.createTime = that.$u.timeFrom(d.createTime, 'yyyy-mm-dd');
					});
					this.messages = this.messages.concat(data);
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bgrey-color;
}
.main {
	margin: 0 6rpx;
}
.message-item {
	background-color: white;
	padding: $love-padding;
	margin: 0 6rpx;
	margin-top: $love-margin;
	border-radius: $love-border-radius;
	//超出换行
	text-align: justify;
	text-justify: newspaper;
	word-break: break-all;
	.message-header {
		display: flex;
		flex-direction: row;
		align-items: center;
		.username {
			font-size: 26rpx;
			color: $u-content-color;
			margin-left: $love-margin;
		}
	}

	.message-content {
		margin: $love-margin 0;
	}

	.message-time {
		display: flex;
		flex-direction: row;
		align-items: center;
		// justify-content: flex-end;
	}
}
</style>
