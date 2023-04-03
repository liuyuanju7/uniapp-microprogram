<template>
	<view>
		<loading  ref="lovdLoading"></loading>
		<u-empty text="哦no竟然没有任何视频" src="/static/in-love.svg" margin-top="50" v-if="showEmpty"></u-empty>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			
			showEmpty:false,
			currPage: 0,
			loadStatus: 'loadmore',
			videos: [] //视频
		};
	},
	onLoad() {
		this.getVideos();
		let that=this;
		// 关闭加载动画
		setTimeout(function() {
			that.showLoading = false;
		}, 300);
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	//下拉
	onPullDownRefresh() {},
	//触底刷新
	onReachBottom() {
		if (this.loadStatus === 'nomore') {
			return;
		}
		this.loadStatus='loading';
		this.getVideos();
	},
	methods: {
		//查询视频
		getVideos() {
			this.$u.api.queryResourceList({ loveId: this.getLoveStartId, resourceType: 1, currPage: ++this.currPage, pageSize: 10 }).then(res => {
				// console.info(res);
				this.videos = this.videos.concat(res.data);
				this.showEmpty=this.videos.length==0;
				this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
			});
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bg-color;
}
</style>
