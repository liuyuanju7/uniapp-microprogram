<template>
	<view>
		<view class="wrap">
			<view class="u-tabs-box"><u-tabs active-color="#ff6b81" :list="tabList" :current="tabCurrent" @change="tabChange" :is-scroll="false"></u-tabs></view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="swiperChange">
				<!-- 故事 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box" v-if="storys.length > 0">
							<story v-for="(item, index) in storys" :key="index" :story="item"></story>
							<u-loadmore :status="loadStatus[tabCurrent]" v-show="loadPageCurr[tabCurrent] > 1"></u-loadmore>
						</view>
						<u-empty text="哦no竟然没有任何故事" src="/static/in-love.svg" color="#ffffff" v-if="storys.length === 0"></u-empty>
					</scroll-view>
				</swiper-item>
				<!-- 相册 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box" v-if="photos.length > 0">
							<u-waterfall class="photo-main" v-model="photos" ref="photosWaterfall" add-time="50">
								<template v-slot:left="{ leftList }">
									<view class="photo" v-for="(item, index) in leftList" :key="index">
										<image class="photo-img" :src="item.resourceUrl" mode="aspectFill" @click="catPhoto(item)"></image>
									</view>
								</template>
								<template v-slot:right="{ rightList }">
									<view class="photo" v-for="(item, index) in rightList" :key="index">
										<image class="photo-img" :src="item.resourceUrl" mode="aspectFill" @click="catPhoto(item)"></image>
									</view>
								</template>
							</u-waterfall>
							<u-loadmore :status="loadStatus[tabCurrent]" v-show="loadPageCurr[tabCurrent] > 1"></u-loadmore>
						</view>
						<u-empty text="哦no竟然没有任何照片" src="/static/in-love.svg" color="#ffffff" v-if="photos.length === 0"></u-empty>
					</scroll-view>
				</swiper-item>
				<!-- 视频 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box"><u-loadmore :status="loadStatus[2]" v-if="videos.length > 0"></u-loadmore></view>
						<u-empty text="哦no竟然没有任何视频" src="/static/in-love.svg" color="#ffffff" v-if="videos.length === 0"></u-empty>
					</scroll-view>
				</swiper-item>
				<!--  要做的事-->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="todo-count">
								<view class="todo-count-item">
									<view class="todo-count-num">{{ promise.undone }}</view>
									<view class="todo-count-txt">待完成</view>
								</view>
								<view class="todo-count-item">
									<view>{{ promise.complete }}</view>
									<view>已完成</view>
								</view>
							</view>
							<view class="todo-main">
								<view class="todo-item" v-for="(p, index) in promise.promiseList" :key="index">
									<view class="todo-item-info">
										<u-avatar :src="p.user.avatar" size="mini"></u-avatar>
										<view class="content">
											<view class="u-main-color u-font-xl">{{ p.user.nickname }}:{{ p.promiseName }}</view>
											<view class="desc u-font-sm u-tips-color u-padding-top-6" v-if="p.promiseDesc != null">{{ p.promiseDesc }}</view>
										</view>
									</view>
									<u-tag v-if="p.daysApart == 0" text="今天" mode="light" shape="circle" type="success" />
									<u-tag v-if="p.daysApart > 0" :text="p.daysApart + '天后'" mode="light" shape="circle" type="primary" />
								</view>
							</view>
							<u-loadmore :status="loadStatus[tabCurrent]" v-show="loadPageCurr[tabCurrent] > 1"></u-loadmore>
						</view>
						<u-empty text="哦no竟然没有任何承诺" src="/static/in-love.svg" color="#ffffff" v-if="promise.length === 0"></u-empty>
					</scroll-view>
				</swiper-item>
				<!-- 思念 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<u-waterfall v-model="messages" ref="messagesWaterfall" add-time="50">
								<template v-slot:left="{ leftList }">
									<view class="">
										<view class="message-item" v-for="(item, index) in leftList" :key="index">
											<view class="message-header u-flex u-margin-bottom-10">
												<u-avatar :src="item.userBase.avatar" size="mini"></u-avatar>
												<view class="u-content-color u-font-sm u-margin-left-15">{{ item.userBase.nickname }}</view>
											</view>
											<view class="message-content">{{ item.messageContent }}</view>
											<view class="message-time">
												<u-tag
													:bg-color="item.userBase.gender == 2 ? '#fe6c87' : '#2e93f7'"
													:text="$u.timeFrom(item.createTime, false)"
													mode="dark"
													shape="circle"
													size="mini"
												/>
											</view>
										</view>
									</view>
								</template>
								<template v-slot:right="{ rightList }">
									<view class="">
										<view class="message-item" v-for="(item, index) in rightList" :key="index">
											<view class="u-flex u-margin-bottom-10">
												<u-avatar :src="item.userBase.avatar" size="mini"></u-avatar>
												<view class="u-content-color u-font-sm u-margin-left-15">{{ item.userBase.nickname }}</view>
											</view>
											<view>{{ item.messageContent }}</view>
											<view class="message-time">
												<u-tag
													:bg-color="item.userBase.gender == 2 ? '#fe6c87' : '#2e93f7'"
													:text="$u.date(item.createTime, 'yyyy')"
													mode="dark"
													shape="circle"
													size="mini"
												/>
											</view>
										</view>
									</view>
								</template>
							</u-waterfall>
							<u-loadmore :status="loadStatus[tabCurrent]" v-show="loadPageCurr[tabCurrent] > 1"></u-loadmore>
						</view>
						<u-empty text="哦no竟然没有任何留言" src="/static/in-love.svg" color="#ffffff" v-if="messages.length === 0"></u-empty>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
		<u-mask class="putStory" :show="putStoryShow" @click="putStoryShow = false">
			<view class="putStory-main">
				<view class="putStory-1">
					<view class="putStory-item">发动态</view>
					<view class="putStory-item">写文章</view>
				</view>
				<view class="putStory-2">
					<view class="putStory-2-item">美图</view>
					<view class="putStory-2-item">视频</view>
					<view class="putStory-2-item">承诺</view>
					<view class="putStory-2-item">承诺</view>
				</view>
			</view>
		</u-mask>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			putStoryShow: false,
			test: true,
			leftList: [],
			rightList: [],
			messages: [],
			storys: [], //故事
			photos: [], //相册
			promise: [], //承诺
			videos: [], //视频
			//tab选择状态
			tabCurrent: 0,
			swiperCurrent: 0,
			//加载更多标识
			loadStatus: ['loadmore', 'loadmore', 'loadmore', 'loadmore', 'loadmore'],
			loadPageCurr: [0, 0, 0, 0, 0], //当前第几页
			loadPageEmpty: [true, true, true, true, true], //当前第几页
			tabList: [
				{
					name: '故事'
				},
				{
					name: '相册'
				},
				{
					name: '视频'
				},
				{
					name: '承诺'
				},
				{
					name: '留言'
				}
			]
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	onLoad() {
		//默认直接开打页面  查询故事
		this.initContent(0);
		console.info(this.photos.length);
		console.info(this.photos.length === 0);
	},
	onShow() {
		//支持从首页跳转
		let value = uni.getStorageSync('memoriesCurrent');
		if (value || value === 0) {
			this.tabCurrent = value;
			this.swiperCurrent = value;
			//跳转过来初始化一次防止页面没有初始化
			this.initContent(value);
			//恢复默认
			uni.removeStorageSync('memoriesCurrent');
		}
	},
	onNavigationBarButtonTap() {
		this.putStoryShow = true;
	},
	methods: {
		initContent(index) {
			//如果页面已经初始化过了就不要再进来一次了
			if (this.loadPageCurr[index] > 0) {
				return;
			}
			switch (index) {
				case 0:
					//查询故事
					this.$u.api.queryStoryList({ loveId: this.getLoveStartId, currPage: this.loadPageCurr[0], pageSize: 10 }).then(res => {
						if (res.data && res.data.length > 0) {
							this.loadPageEmpty[index] = false;
						}
						this.storys = res.data;
					});
					break;
				case 1:
					this.$u.api.queryResourceList({ loveId: this.getLoveStartId, resourceType: 0, currPage: this.loadPageCurr[1], pageSize: 20 }).then(res => {
						if (res.data && res.data.length > 0) {
							this.loadPageEmpty[index] = false;
						}
						this.photos = res.data;
					});

					break;
				case 2:
					this.$u.api.queryResourceList({ loveId: this.getLoveStartId, resourceType: 1, currPage: this.loadPageCurr[2], pageSize: 1 }).then(res => {
						if (res.data && res.data.length > 0) {
							this.loadPageEmpty[index] = false;
						}
						this.videos = res.data;
					});
					break;
				case 3:
					this.$u.api.queryPromisePage({ loveId: this.getLoveStartId }).then(res => {
						if (res.promiseList && res.promiseList.length > 0) {
							this.loadPageEmpty[index] = false;
						}
						this.promise = res;
					});
					break;
				case 4:
					this.$u.api.queryMessageList({ loveId: this.getLoveStartId, messageType: 1, currPage: this.loadPageCurr[4], pageSize: 20 }).then(res => {
						if (res.data && res.data.length > 0) {
							this.loadPageEmpty[index] = false;
						}
						this.messages = res.data;
						console.info(this.messages);
					});
					break;
			}
			this.loadPageCurr[index] = 1;
		},
		//触底刷新数据
		reachBottom() {
			console.info('触底了' + this.tabCurrent);
			if (this.loadStatus[this.tabCurrent] === 'nomore' || this.loadStatus[this.tabCurrent] === 'loading') {
				return;
			}
			this.loadStatus.splice(this.tabCurrent, 1, 'loading');
			this.$u.throttle(
				setTimeout(() => {
					// 查询更多故事
					if (this.tabCurrent == 0) {
						this.getStorys();
					} else if (this.tabCurrent == 1) {
						this.getPhotos();
					} else if (this.tabCurrent == 2) {
						this.getVideos();
					} else if (this.tabCurrent == 3) {
						this.getPromise();
					} else if (this.tabCurrent == 4) {
						this.getMessages();
					}
				}, 1500),
				1500
			);
		},
		//查询故事
		getStorys() {
			//查询故事
			this.$u.api.queryStoryList({ loveId: this.getLoveStartId, currPage: ++this.loadPageCurr[0], pageSize: 10 }).then(res => {
				// console.info(res);
				this.storys = this.storys.concat(res.data);
				this.loadMoreTip(res.data.length);
			});
		},
		//查询照片
		getPhotos() {
			this.$u.api.queryResourceList({ loveId: this.getLoveStartId, resourceType: 0, currPage: ++this.loadPageCurr[1], pageSize: 10 }).then(res => {
				// console.info(res);
				this.photos = this.photos.concat(res.data);
				this.loadMoreTip(res.data.length);
			});
		},
		//查询视频
		getVideos() {
			this.$u.api.queryResourceList({ loveId: this.getLoveStartId, resourceType: 1, currPage: ++this.loadPageCurr[2], pageSize: 10 }).then(res => {
				// console.info(res);
				this.videos = this.videos.concat(res.data);
				this.loadMoreTip(res.data.length);
			});
		},
		//查询留言
		getMessages() {
			//查询思念留言
			this.$u.api.queryMessageList({ loveId: this.getLoveStartId, messageType: 1, currPage: ++this.loadPageCurr[4], pageSize: 20 }).then(res => {
				// console.info(res);
				this.messages = this.messages.concat(res.data);
				this.loadMoreTip(res.data.length);
			});
		},
		//查询承诺
		getPromise() {
			//查询思念留言
			this.$u.api.queryPromiseList({ loveId: this.getLoveStartId, currPage: ++this.loadPageCurr[3], pageSize: 10 }).then(res => {
				console.info(res);
				this.promise.promiseList = this.promise.promiseList.concat(res.data);
				this.loadMoreTip(res.data.length);
			});
		},
		loadMoreTip(count) {
			if (count == 0) {
				this.loadStatus.splice(this.tabCurrent, 1, 'nomore');
			} else {
				this.loadStatus.splice(this.tabCurrent, 1, 'loadmore');
			}
		},
		// tabs通知swiper切换
		tabChange(index) {
			console.info('修改了tab' + index);
			this.tabCurrent = index;
			this.swiperCurrent = index;
			//修改时初始化页面内容一次
			this.initContent(index);
		},
		// swiper通知swiper切换
		swiperChange(swiper) {
			console.info('修改了tab' + JSON.stringify(swiper) );
			this.tabCurrent = swiper.current;
			this.swiperCurrent = swiper.current;
			//修改时初始化页面内容一次
			this.initContent(swiper.current);
		},
		//查看照片
		catPhoto(photo) {
			console.info(photo.resourceUrl);
			let p = [photo.resourceUrl];
			console.info(p);
			uni.previewImage({
				urls: p,
				longPressActions: {
					itemList: ['发送给朋友', '保存图片', '收藏'],
					success: function(data) {
						console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
					},
					fail: function(err) {
						console.log(err.errMsg);
					}
				}
			});
		}
	}
};
</script>
<style lang="scss">
page {
	background-color: $love-bg-color;
}
.putStory-main {
	padding: 0 50rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	width: 100%;
	height: 100%;

	.putStory-1 {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		width: 100%;

		.putStory-item {
			line-height: 140rpx;
			text-align: center;
			width: 280rpx;
			height: 140rpx;
			background-color: $love-color;
			color: white;
			font-size: 34rpx;
			border-radius: $love-border-radius;
		}
	}

	.putStory-2 {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		width: 100%;
		margin-top: 38rpx;

		.putStory-2-item {
			width: 100rpx;
			height: 100rpx;
			background-color: #19be6b;
			border-radius: $love-border-radius;
			line-height: 100rpx;
			text-align: center;
			color: white;
		}
	}
}

.message-item {
	background-color: white;
	padding: 10rpx 20rpx;
	margin: 10rpx;
	border-radius: $love-border-radius;
	// 自动调整宽度
	width: 96%;
	display: inline-block !important;
	display: inline;
	//超出换行
	text-align: justify;
	text-justify: newspaper;
	word-break: break-all;
	.message-header {
		// width: 100%;
	}
	.message-content {
		// width: 100%;
	}
	.message-time {
		margin-top: 6rpx;
	}
}

.todo-count {
	display: flex;
	height: 200rpx;
	background-color: white;
	margin: $love-margin;
	border-radius: $love-border-radius;

	.todo-count-item {
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		width: 50%;
		height: 100%;
		font-size: 40rpx;

		.todo-count-num {
			margin: $love-margin 0;
		}
	}
}

.photo {
	margin: $love-margin;
	// background-color: #ffffff;
	min-height: 300rpx;
	height: 300rpx;
	.photo-img {
		border-radius: $love-border-radius;
		width: 100%;
		height: 100%;
	}
}

.wrap {
	display: flex;
	flex-direction: column;
	height: calc(100vh - var(--window-top));
	width: 100%;
}

.swiper-box {
	flex: 1;
}

.swiper-item {
	height: 100%;
}

.todo-main {
	.todo-item {
		background-color: white;
		border-radius: $love-border-radius;
		margin: $love-margin;
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 120rpx;
		color: $love-content-color;
		padding: 0 12rpx;
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
</style>
