<template>
	<view class="main">
		<view class="todo-count">
			<view class="todo-count-item">
				<view class="todo-num">{{ promise.undone }}</view>
				<view class="todo-txt">待完成</view>
			</view>
			<view class="todo-count-item">
				<view class="todo-num">{{ promise.complete }}</view>
				<view class="todo-txt">已完成</view>
			</view>
			<view class="todo-count-item">
				<view class="todo-num">{{ promise.complete }}</view>
				<view class="todo-txt">完蛋了</view>
			</view>
		</view>
		<view class="todo-main">
			<navigator class="todo-item" v-for="(p, index) in promise.promiseList" :key="index" hover-class="none" :url="'/pages/promise/promiseInfo?promiseId=' + p.promiseId">
				<view class="todo-item-info">
					<u-avatar :src="p.user.avatar" size="mini"></u-avatar>
					<view class="content">
						<view class="u-main-color u-font-xl">{{ p.promiseName }}</view>
						<view class="desc u-font-sm u-tips-color u-padding-top-6" v-if="p.promiseDesc != null">{{ p.promiseDesc }}</view>
					</view>
				</view>
				<u-tag v-if="p.daysApart == 0" text="今天" mode="light" shape="circle" type="success" />
				<u-tag v-if="p.daysApart > 0" :text="p.daysApart + '天后'" mode="light" shape="circle" type="primary" />
			</navigator>
		</view>

		<u-loadmore :status="loadStatus" v-show="promise.promiseList.length > 10"></u-loadmore>
		<u-empty text="哦no竟然没有任何承诺" src="/static/in-love.svg" margin-top="50" v-if="promise.promiseList.length === 0"></u-empty>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			currPage: 0,
			loadStatus: 'loadmore',
			promise: {
				complete: 0,
				undone: 0,
				promiseList: []
			} //承诺
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	onLoad() {
		this.$u.api.queryPromisePage({ loveId: this.getLoveStartId, promiseType: 1 }).then(res => {
			this.promise = res;
		});
	},
	//下拉
	onPullDownRefresh() {},
	//触底刷新
	onReachBottom() {
		if (this.loadStatus === 'nomore') {
			return;
		}
		this.loadStatus='loading';
		this.getPromise();
	},
	methods: {
		//查询承诺
		getPromise() {
			//查询思念留言
			this.$u.api.queryPromiseList({ loveId: this.getLoveStartId, promiseType: 1, currPage: ++this.currPage, pageSize: 10 }).then(res => {
				console.info(res);
				this.promise.promiseList = this.promise.promiseList.concat(res);
				this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
			});
		},
		lookPromise(id) {
			console.info(id);
		}
	}
};
</script>

<style lang="scss">
// page {
// 	background-color: $love-bg-color;
// }
.main{
	margin: $love-big-margin;
}
.todo-main {
	.todo-item {
		background-color: white;
		// margin: $love-margin;
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 120rpx;
		color: $love-content-color;
		// padding: 0 12rpx;
		// border-bottom: $u-type-primary-light 1rpx solid;
		.todo-item-info {
			display: flex;
			.content {
				padding-left: 14rpx;
				display: flex;
				flex-direction: column;
				justify-content: center;
			}
			.desc {
				display: block;
			}
		}
	}
}
.todo-count {
	display: flex;
	height: 200rpx;
	background-image: linear-gradient(to right, #f78ca0 0%, #f9748f 19%, #fd868c 60%, #fe9a8b 100%);
	margin-bottom: $love-big-margin;
	border-radius: $love-border-radius;
	color: white;
	.todo-count-item {
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		width: 50%;
		height: 100%;

		.todo-num {
			margin-bottom: $love-margin;
			font-size: 66rpx;
		}
	}
}
</style>
