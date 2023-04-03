<template>
	<view class="main">
		<view class="story-filter">
			<u-dropdown active-color="#ff6773" border-radius="30">
				<u-dropdown-item v-model="originator" title="发布人" :options="originatorData" @change="changeOriginator"></u-dropdown-item>
				<u-dropdown-item v-model="storyType" title="故事类型" :options="storyTypeData" @change="changestoryType"></u-dropdown-item>
			</u-dropdown>
		</view>

		<view class="story-main"><story v-for="(item, index) in storys" :key="index" :story="item"></story></view>

		<u-loadmore :status="loadStatus" height="200" v-if="storys.length > 3"></u-loadmore>
		<u-empty text="哦no竟然没有任何故事" src="/static/in-love.svg" margin-top="50" :show="showEmpty"></u-empty>
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
			storys: [], //故事
			originator: 0,
			storyType: 0,
			originatorData: [
				{
					label: '全部',
					value: 0
				},
				{
					label: '我的',
					value: 1
				},
				{
					label: 'ta的',
					value: 2
				}
			],
			storyTypeData: [
				{
					label: '全部',
					value: 0
				},
				{
					label: '动态',
					value: 1
				},
				{
					label: '文章',
					value: 2
				}
			]
		};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getLoveStartInfo'])
	},
	onLoad() {
		//查询故事
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
		this.getStorys();
	},
	methods: {
		init() {
			// this.showLoading=true;
			this.currPage = 0;
			//查询故事
			this.$u.api
				.queryStoryList({
					loveId: this.getLoveStartId,
					currPage: ++this.currPage,
					pageSize: 10,
					storyType: this.storyType,
					originator: this.originator
				})
				.then(res => {
					this.storys = res.data;
					this.showEmpty = this.storys.length == 0;
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
					//加载动画过度
					this.$refs.lovdLoading.hidden();
				});
		},
		//查询故事
		getStorys() {
			//查询故事
			this.$u.api
				.queryStoryList({
					loveId: this.getLoveStartId,
					currPage: ++this.currPage,
					pageSize: 10,
					storyType: this.storyType,
					originator: this.originator
				})
				.then(res => {
					// console.info(res);
					this.storys = this.storys.concat(res.data);
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		},
		changeOriginator(value) {
			// console.info(value);
			this.init();
		},
		changestoryType(value) {
			// console.info(value);
			this.init();
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bg-color;
}

.story-filter {
	background-color: white;
}

.story-main {
	margin: $love-view-margin;
}
</style>
