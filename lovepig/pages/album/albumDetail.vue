<template>
	<view class="main">
		<view class="album-header" :style="'background-image:url(' + album.albumBanner + ');'">
			<!-- <image class="album-bg" src="https://lovestatic.jokeo.cn/love/202202/182345379348.jpg" mode="aspectFill"></image> -->
			<view class="album-info baseStyle">
				<view class="album-left">
					<view class="album-name ">{{ album.albumName }}</view>
					<view class="album-desc">{{ album.albumDesc }}</view>
				</view>
				<view class="album-right" @click="setting"><u-icon name="setting" size="52"></u-icon></view>
			</view>
		</view>
		<view class="album-content">
			<u-row gutter="12">
				<u-col span="4" class="photo" v-for="(item, index) in photos" :key="index">
					<image class="album-image" :lazy-load="true" :src="item.resourceUrl" mode="aspectFill" @click="catPhoto(index)"></image>
				</u-col>
			</u-row>
		</view>
		<u-loadmore :status="loadStatus" height="200" v-show="showLoadmore"></u-loadmore>
		<u-empty text="哦no竟然没有任何照片" src="/static/in-love.svg" margin-top="220" v-if="showEmpty"></u-empty>
		<loading  ref="lovdLoading"></loading>
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
			loadStatus: 'loadmore',
			albumId: 0,
			album: {
				albumId: 1,
				loveId: 521,
				userId: 1,
				albumName: '故事',
				albumDesc: '故事的相册',
				albumBanner: 'https://lovestatic.jokeo.cn/love/202202/182345379348.jpg',
				albumPermission: 1,
				photoNum: 0,
				lastUploadTime: 1645329672000,
				state: 0,
				createTime: 1645329684000,
				modifyTime: 1645329700000
			},
			currPage: 0,
			leftList: [],
			rightList: [],
			photos: [], //相册
			changeAlbumRes: false
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	onLoad(e) {
		this.albumId = e.id;
		this.init();
	},
	onShow(e) {
		// this.init();
		//如果修改了相册信息就更改相册信息
		// if (this.changeAlbumRes) {
		// 	this.getAlbumInfo();
		// 	this.changeAlbumRes = false;
		// }
	},
	//下拉
	onPullDownRefresh() {},
	//触底刷新
	onReachBottom() {
		if (this.loadStatus === 'nomore') {
			return;
		}
		this.loadStatus='loading';
		this.getPhotos();
	},
	methods: {
		init() {
			this.getAlbumInfo();
			this.$u.api
				.queryAlbumContent({
					loveId: this.getLoveStartId,
					albumId: this.albumId,
					currPage: ++this.currPage,
					pageSize: 15
				})
				.then(res => {
					// console.info(res);
					this.photos = res.data;
					this.showLoadmore = res.data.length > 12;
					this.showEmpty = res.totalCount == 0;
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		},
		//查询相册信息
		async getAlbumInfo() {
			this.album = await this.$u.api.queryAlbumInfo({
				albumId: this.albumId
			});
			//加载动画过度
			this.$refs.lovdLoading.hidden();
		},
		//查询照片
		getPhotos() {
			this.$u.api
				.queryAlbumContent({
					loveId: this.getLoveStartId,
					albumId: this.albumId,
					currPage: ++this.currPage,
					pageSize: 10
				})
				.then(res => {
					this.photos = this.photos.concat(res.data);
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		},
		//查看照片
		catPhoto(photoIndex) {
			let pics = this.photos.map(p => p.resourceUrl);
			uni.previewImage({
				urls: pics,
				current: photoIndex,
				indicator: 'number',
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
		},
		openPutPhotoView() {
			let that = this;
			uni.chooseImage({
				count: 9,
				sizeType: ['original', 'compressed'],
				sourceType: ['album'],
				success: function(selectImages) {
					uni.navigateTo({
						url: '/pages/album/pushPhoto',
						events: {
							onUploadImages: function(data) {
								console.log(data);
								that.photos = data.onUploadImages.concat(that.photos);
							}
						},
						success: function(event) {
							event.eventChannel.emit('selectImages', {
								images: selectImages.tempFilePaths,
								albumId: that.albumId
							});
						}
					});
				}
			});
		},
		setting() {
			let that = this;
			uni.navigateTo({
				url: '/pages/album/newAlbum',
				success: function(event) {
					//把相册信息传过去
					event.eventChannel.emit('albumInfo', that.album);
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

.album-header {
	background-size: cover;
	position: relative;
	background-position: center;
	height: 450rpx;

	.album-bg {
		width: 100%;
		height: 100%;
	}

	.album-info {
		min-height: 180rpx;
		max-height: 250rpx;
		position: absolute;
		box-shadow: 0 10px 10px #F5F5F5;
		left: 0;
		right: 0;
		bottom: -80rpx;
		z-index: 1;
		display: flex;
		justify-content: space-between;
		width: 90%;
		margin: 0 auto;
		align-items: center;
		padding: $love-padding $love-big-padding;

		.album-name {
			font-size: 36rpx;
			font-weight: bold;
			margin-bottom: 8rpx;
		}

		.album-desc {
			font-size: 22rpx;
			padding-left: $love-padding;
			color: $u-tips-color;
		}

		.album-right {
			padding: $love-padding;
		}
	}

	&:after {
		position: absolute;
		left: 0;
		top: 0;
		content: '';
		// background: linear-gradient(to bottom, transparent 60%, $love-bg-color);
		background: linear-gradient(to bottom, rgba($love-bg-color, 0.1) 50%, $love-bg-color);
		width: 100%;
		height: 100%;
		opacity: 1;
	}
}

.album-content {
	margin-top: 120rpx;
	padding: 0 10rpx;
}

.photo {
	height: 250rpx;
	width: 100%;
	overflow: hidden;
	margin-bottom: 12rpx;
	.album-image {
		width: 100%;
		height: 250rpx;
		border-radius: $love-border-radius;
	}
}
</style>
