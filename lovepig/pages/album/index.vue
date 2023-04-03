<template>
	<view class="main">
		<!-- <view class="header-content"></view> -->
		<view class="album-content">
			<item-header title="相册" navigatorUrl="/pages/album/albumPage"></item-header>
			<scroll-view :scroll-x="true" class="scroll-X">
				<navigator class="album-item" v-for="(item, index) in albums" :key="index" :url="'/pages/album/albumDetail?id=' + item.albumId" hover-class="none">
					<view class="album-box"><image class="album-image" :src="item.albumBanner" mode="aspectFill" /></view>
					<view class="album-name">{{ item.albumName }}</view>
					<view class="album-desc">{{ item.albumDesc }}</view>
				</navigator>
			</scroll-view>
		</view>
		<view class="photo-content">
			<item-header title="最新照片" :showMoreBtn="false" margin="10"></item-header>
			<u-waterfall class="photo-main" v-model="photos" ref="photosWaterfall" add-time="0">
				<template v-slot:left="{ leftList }">
					<view class="photo" v-for="(item, index) in leftList" :key="item.resourceId">
						<image class="photo-img" :src="item.resourceUrl" mode="widthFix" @click="catPhotoNew(item.resourceId)"></image>
					</view>
				</template>
				<template v-slot:right="{ rightList }">
					<view class="photo" v-for="(item, index) in rightList" :key="item.resourceId">
						<image class="photo-img" :src="item.resourceUrl" mode="widthFix" @click="catPhotoNew(item.resourceId)"></image>
					</view>
				</template>
			</u-waterfall>
		</view>

		<u-loadmore :status="loadStatus" height="200" v-show="showLoadmore"></u-loadmore>
		<u-empty text="哦no竟然没有任何照片" src="/static/in-love.svg" margin-top="50" v-if="showEmpty"></u-empty>
		<loading ref="lovdLoading"></loading>
		<view class="right-btn" @click="openPutPhotoView()"><u-icon name="plus"></u-icon></view>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			showEmpty: false,
			showLoadmore: false,
			currPage: 0,
			loadStatus: 'loadmore',
			leftList: [],
			rightList: [],
			photos: [], //相册
			albums: []
		};
	},
	onLoad() {
		console.info(this.getLoveStartId)
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
		this.getPhotos();
	},
	computed: {
		...mapGetters(['getLoveStartId']),
		//每次查询到图片，提取其中的图片地址存储起来方便查看时复用
		finallyPhotos() {
			return this.photos.map(p => p.resourceUrl);
		}
	},
	methods: {
		init() {
			let that = this;
			this.getAlbumList();
			this.$u.api
				.queryResourceList({
					loveId: this.getLoveStartId,
					resourceType: 0,
					currPage: ++this.currPage,
					pageSize: 10,
					queryNew: true
				})
				.then(res => {
					console.info(res);
					that.photos = res.data;
					that.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
					that.showEmpty = res.data.length === 0;
					that.showLoadmore = res.data.length > 6;
					//加载动画过度
					that.$refs.lovdLoading.hidden();
				});
		},
		//查询相册
		getAlbumList() {
			this.$u.api
				.queryAlbumList({
					loveId: this.getLoveStartId,
					currPage: 1,
					pageSize: 5
				})
				.then(res => {
					// console.info(res);
					this.albums = res;
				});
		},
		//查询照片
		getPhotos() {
			this.$u.api
				.queryResourceList({
					loveId: this.getLoveStartId,
					resourceType: 0,
					currPage: ++this.currPage,
					pageSize: 10,
					queryNew: true
				})
				.then(res => {
					// console.info(res);
					this.photos = this.photos.concat(res.data);
					// console.info(this.photos)
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		},
		catPhoto(photo) {
			let p = [photo];
			uni.previewImage({
				urls: p,
				indicator: 'none'
			});
		},
		//查看照片
		catPhotoNew(photoId) {
			//根据图片的id查询图片在数组中的下标
			let toIndex = this.photos.findIndex(img => img.resourceId == photoId);
			uni.previewImage({
				urls: this.finallyPhotos,
				current: toIndex,
				longPressActions: {
					// itemList: ['发送给朋友', '保存图片', '收藏'],
					success: function(data) {
						console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
					},
					fail: function(err) {
						console.log(err.errMsg);
					}
				}
			});
		},
		openPutPhotoView(index) {
			let that = this;
			uni.chooseImage({
				count: 9,
				sizeType: ['original', 'compressed'],
				sourceType: ['album'],
				success: function(selectImages) {
					uni.navigateTo({
						url: '/pages/album/pushPhoto',
						success: function(event) {
							event.eventChannel.emit('selectImages', { images: selectImages.tempFilePaths, albumId: 0 });
						}
					});
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
.header-content {
	// height: 300rpx;
}
// 去除scroll-view横向滚动条
::-webkit-scrollbar {
	display: none;
}

.main {
	margin: $love-view-margin 0;
	padding: 0 10rpx;
}

.album-content {
	margin-bottom: $love-view-margin;
	margin-left: 10rpx;
}

.scroll-X {
	width: 100%;
	overflow: hidden;
	white-space: nowrap;
	border-radius: $love-border-radius;
	.album-item {
		width: 560rpx;
		display: inline-block;
		margin-right: $love-view-margin;
		background-color: white;
		border-radius: $love-border-radius;
	}

	.album-box {
		width: 560rpx;
		position: relative;

		.album-image {
			width: 560rpx;
			height: 430rpx;
			border-radius: $love-border-radius $love-border-radius 0 0;
		}
	}

	.album-name {
		margin: $love-margin;
		white-space: pre-wrap;
		word-wrap: break-word;
		-webkit-line-clamp: 2;
	}

	.album-desc {
		font-size: 20rpx;
		margin: $love-margin;
		color: $u-tips-color;
		white-space: pre-wrap;
		word-wrap: break-word;
		-webkit-line-clamp: 2;
	}
}

.photo-content {
	// margin-left: $love-margin;
}

.photo {
	margin: 0 10rpx $love-view-margin 10rpx;
	border-radius: $love-border-radius;
	background-color: white;
	max-height: 550rpx;
	overflow: hidden;
	display: flex;
	.photo-img {
		border-radius: $love-border-radius;
		width: 100%;
	}
}
</style>
