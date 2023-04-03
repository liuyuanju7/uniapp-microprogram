<template>
	<view>
		<back-button></back-button>
		<view class="promise-main" :style="'background-image:url(' + promise.promiseBackground + ');'">
			<view class="promise-content">
				<view class="promise-title">{{ promiseTypeName }}『{{ promise.promiseName }}』还有</view>
				<view :class="promise.daysApart == 0 ? 'promise-day highlight' : 'promise-day'">{{ promise.daysApart == 0 ? '今' : promise.daysApart }}天</view>
				<view class="promise-targetTime">{{ $u.timeFormat(promise.targetTime, 'yyyy年mm月dd日') }}</view>
				<view class="promise-remarks" v-if="promise.promiseRemarks != ''">{{ promise.promiseRemarks }}</view>
				<view class="promise-config"></view>
			</view>
			<view class="promise-function" v-if="isOnwer">
				<u-row gutter="16">
					<u-col span="6" @click="editPromise">
						<view class="function-item">
							<u-icon name="edit-pen" size="50"></u-icon>
							<view class="add_mode_title">编辑</view>
						</view>
					</u-col>
					<u-col span="6" @click="finishPromise">
						<view class="function-item">
							<u-icon name="heart-fill" size="50" color="#ff6773"></u-icon>
							<view class="add_mode_title">{{ promise.promiseType == 1 ? '完成' : '记录' }}</view>
						</view>
					</u-col>
					<!-- <u-col span="4" >
						<view class="function-item">
							<u-icon name="photo" size="50"></u-icon>
							<view class="add_mode_title">分享</view>
						</view>
					</u-col> -->
				</u-row>
			</view>
		</view>
		<view class="promise-info">
			<u-row gutter="12">
				<u-col span="6">
					<view class="p-i-item">时间: {{ $u.timeFormat(promise.promiseTime, 'yyyy年mm月dd日') }}</view>
				</u-col>
				<u-col span="6">
					<view class="p-i-item">分类: {{ promise.promiseRecords == null ? '未设置' : promise.promiseRecords }}</view>
				</u-col>
				<u-col span="6">
					<view class="p-i-item">周期: {{ promiseRepeatText }}</view>
				</u-col>
				<u-col span="6">
					<view class="p-i-item">提醒: {{ promiseRemindText }}</view>
				</u-col>
			</u-row>
		</view>
		<view class="promise-records">
			<view class="records-header">
				<view class="records-title">{{ promise.promiseType == 1 ? '承诺完成' : '纪念日' }}记录</view>
				<image class="p-image" src="/static/icon/common/love-beat.gif" mode="aspectFill"></image>
			</view>
			<view>
				<navigator class="story" v-for="(story, index) in storys" :key="index" :url="'/pages/story/dynamicDetail?id=' + story.storyId" hover-class="none">
					<view class="story-header">
						<view class="story-h-time" v-if="promise.promiseType == 1">
							<u-icon class="u-m-r-12" name="clock-fill"></u-icon>
							第{{ story.promiseRecord.annual }}次兑现
						</view>
						<view class="story-h-time" v-else>
							<u-icon class="u-m-r-12" name="clock-fill"></u-icon>
							{{ story.promiseRecord.annual }}周年
						</view>
						<view class="">{{ $u.timeFormat(story.promiseRecord.finishTime, 'yyyy年mm月dd日 hh:MM') }}</view>
					</view>
					<u-line border-style="dashed"></u-line>
					<view class="story-content ">{{ story.summary }}...</view>
					<story-image :images="story.resources"></story-image>
				</navigator>
			</view>
		</view>
		<u-loadmore :status="loadStatus" height="200" v-show="true"></u-loadmore>
		<!-- 加载动画 -->
		<loading ref="lovdLoading"></loading>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			currPromiseId: 0,
			currPage: 0,
			loadStatus: 'nomore',
			isOnwer: false,
			storys: [],
			promise: {
				promiseId: 9,
				user: {
					id: 1,
					username: 'joke',
					gender: 1,
					nickname: '大宝贝',
					avatar: 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLLnsXvmY93UaEB9M1JIaNNjdI243VX5v5ekpuiaNzBDTzJKL4KGLoLR4nKkwf39v2XChWD0JMc0eA/132'
				},
				promiseName: '买衣服',
				promiseRemarks: '111',
				promiseTime: 1633708800000,
				promiseCategory: '1',
				promiseRepeat: '1M',
				daysApart: 0,
				pastDays: 123,
				annual: 4,
				promiseType: 1,
				targetTime: 1644336000000,
				promiseRecords: null,
				promiseBackground: 'https://lovestatic.jokeo.cn/love/202201/292316403791.jpg'
			},
			promiseTypeName: '承诺',
			promiseRepeatText: '承诺',
			promiseRemindText: '承诺',
			updatePromiseRes: false
		};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getLoginUser'])
	},
	onLoad(e) {
		this.currPromiseId = e.promiseId;
		this.isOnwer = this.getLoveStartId!=0 && (this.getLoveStartId == this.getLoginUser.loveId);
		this.init();
	},
	onShow() {
		// console.info(this.updatePromiseRes)
		if (this.updatePromiseRes) {
			this.getPromise();
			this.updatePromiseRes = false;
		}
	},
	//下拉
	onPullDownRefresh() {
		this.init();
	},
	//触底刷新
	onReachBottom() {
		if (this.loadStatus === 'nomore') {
			return;
		}
		this.loadStatus = 'loading';
		this.getPromiseRecord();
	},
	methods: {
		init() {
			this.getPromise();
			this.getPromiseRecord();
		}, //查询承诺
		async getPromise() {
			this.promise = await this.$u.api.promiseDetail({
				promiseId: this.currPromiseId,
				loveId: this.getLoveStartId
			});
			if (!this.promise.promiseBackground) {
				this.promise.promiseBackground = 'https://lovestatic.jokeo.cn/love/202202/041857219978.jpg';
			}
			if (this.promise.promiseType == 2) {
				this.promiseTypeName = '纪念日';
			}
			this.promiseRemindText = this.$pig.promise.promiseRemindText(this.promise.promiseRemind);
			this.promiseRepeatText = this.$pig.promise.promiseRepeatText(this.promise.promiseRepeat);
			//加载动画过度
			this.$refs.lovdLoading.hidden();
		},
		getPromiseRecord() {
			//查询故事
			this.$u.api
				.queryStoryList({
					loveId: this.getLoveStartId,
					currPage: ++this.currPage,
					promiseId: this.currPromiseId,
					pageSize: 5
				})
				.then(res => {
					// console.info(res);
					this.storys = this.storys.concat(res.data);
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		},
		lookPromise(id) {
			console.info(id);
		},
		finishPromise() {
			let path = `/pages/saveStory/put-dynamic?promiseId=${this.promise.promiseId}&promiseName=${this.promise.promiseName}&annual=${
				this.promise.promiseType === 1 ? this.promise.annual : this.promise.annual - 1
			}`;
			console.info(path);
			uni.navigateTo({
				url: path
			});
		},
		editPromise() {
			let that = this;
			let path;
			if (this.promise.promiseType === 1) {
				path = `/pages/promise/editPromise?promiseId=${this.promise.promiseId}`;
			} else if (this.promise.promiseType === 2) {
				path = `/pages/anniversary/editAnniversary?promiseId=${this.promise.promiseId}`;
			}
			console.info(path);
			uni.navigateTo({
				url: path,
				events: {
					updatePromise: function(data) {
						// console.log(data);
						that.updatePromiseRes = data.updatePromiseRes;
						// console.info(that.updatePromiseRes)
					}
				}
			});
		},
		selectBackground() {
			console.info(11);
			let that = this;
			uni.navigateTo({
				url: '/pages/album/selectImage',
				events: {
					//获取选择的照片地址
					selectedImage: function(data) {
						console.log(data);
						// that.album.albumBanner = data;
					}
				}
			});
		},
		sharePromise() {
			let that = this;
			uni.share({
				provider: 'weixin',
				scene: 'WXSceneSession',
				type: 5,
				imageUrl: that.promise.promiseBackground,
				title: that.promiseTypeName + that.promise.promiseName,
				miniProgram: {
					id: 'wx34dfb777ec6e2402',
					path: `/pages/promise/promiseInfo?promiseId=${that.currPromiseId}`,
					type: 1
				},
				success: ret => {
					console.log(JSON.stringify(ret));
				}
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
	width: 100vw;
	height: 100vh;
	// background-image: url('https://lovestatic.jokeo.cn/love/202201/292316403791.jpg');
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center;
	display: flex;
	align-items: center;
	justify-content: center;
	text-align: center;
	color: white;

	.promise-content {
		& > view {
			margin: 38rpx;
		}
	}

	.promise-title {
		font-size: 38rpx;
	}

	.promise-day {
		font-size: 80rpx;
	}

	.promise-targetTime {
		font-size: 20rpx;
	}

	.promise-remarks {
		width: 300px;
		min-height: 80px;
		background-color: rgba($color: #000000, $alpha: 0.3);
		font-size: 20rpx;
		padding: $love-padding;
		border-radius: $love-border-radius;
	}

	.promise-function {
		position: fixed;
		bottom: 50rpx;
		width: 250px;
		padding: $love-padding 0;
		border-radius: 100rpx;
		background-color: rgba($color: #000000, $alpha: 0.3);
		z-index: 100;
		overflow: hidden;
		font-size: 22rpx;
		backdrop-filter: blur(8px);

		.function-item {
			display: flex;
			align-items: center;
			flex-direction: column;
			justify-content: space-around;
			text-align: center;
		}
	}
}

.promise-info {
	margin: 38rpx $love-view-margin;

	.p-i-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		font-size: 26rpx;
		height: 100rpx;
		padding: $love-padding;
		border-radius: $love-border-radius;
		background-color: white;
		margin-bottom: $love-margin;
	}
}

.promise-records {
	margin: $love-view-margin;

	.records-header {
		// width: 100%;
		background-color: white;
		height: 150rpx;
		text-align: center;
		margin: $love-view-margin 0;
		border-radius: $love-border-radius;
		padding: $love-padding 0;
	}

	.records-title {
		font-size: 32rpx;
		font-weight: bold;
	}

	.p-image {
		height: 80rpx;
	}
}

.story {
	padding: $love-padding;
	background-color: #ffffff;
	box-shadow: 0px 0px 6px #c0c4cc20;
	border-radius: $love-border-radius;
	margin-bottom: $love-margin;

	.story-header {
		text-align: center;
		font-weight: bold;
		margin-bottom: $love-margin;

		.story-h-time {
			color: #ff6773;
			font-size: 30rpx;
		}
	}

	.story-content {
		padding: $love-padding 0;
	}
}
</style>
