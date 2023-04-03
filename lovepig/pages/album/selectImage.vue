<template>
	<view>
		<view class="album-list" v-if="showAlbumList">
			<item-header title="选择相册" :showMoreBtn="false"></item-header>
			<scroll-view :scroll-x="true" class="scroll-X">
				<view
					class="album-name"
					v-for="(item, index) in albums"
					:key="index"
					:style="{ color: index === currentAlbumIndex ? '#fff' : '', backgroundColor: index === currentAlbumIndex ? '#ff6773' : '#fff' }"
					@click="selectAlbum(item.albumId, index)"
				>
					{{ item.albumName }}
				</view>
			</scroll-view>
		</view>
		<view class="album-content">
			<u-row gutter="12">
				<u-col span="4" class="photo" v-for="(item, index) in photos" :key="index">
					<image class="album-image" :lazy-load="true" :src="item.resourceUrl" mode="aspectFill" @click="selectPhoto(item.resourceUrl)"></image>
				</u-col>
			</u-row>
		</view>
		<u-loadmore :status="loadStatus" height="200" v-show="showLoadmore"></u-loadmore>
		<u-empty text="哦no竟然没有任何照片" src="/static/in-love.svg" margin-top="220" v-if="showEmpty"></u-empty>
		<loading ref="lovdLoading"></loading>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			showAlbumList: false,
			showEmpty: false,
			showLoadmore: false,
			loadStatus: 'loadmore',
			albumId: 0,
			currPage: 0,
			currentAlbumIndex: 0,
			photos: [],
			albums: []
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	onLoad(e) {
		if (e.albumId) {
			this.albumId = e.albumId;
			this.initPhotos();
		} else {
			this.showAlbumList = true;
			this.getAlbumList();
		}
	},
	onReachBottom() {
		this.getPhotos();
	},
	methods: {
		initPhotos() {
			this.currPage = 0;
			this.$u.api
				.queryAlbumContent({
					loveId: this.getLoveStartId,
					albumId: this.albumId,
					currPage: ++this.currPage,
					pageSize: 20
				})
				.then(res => {
					this.$refs.lovdLoading.hidden();K
					this.photos = res.data;
					this.showLoadmore = res.data.length >= 20;
					this.showEmpty = res.totalCount == 0;
					this.loadStatus = this.$pig.base.getloadMoreTip(res.data.length);
				});
		},
		getPhotos() {
			if (this.loadStatus === 'nomore') {
				return;
			}
			this.loadStatus = 'loading';
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
		selectPhoto(img) {
			// console.info(img)
			const eventChannel = this.getOpenerEventChannel();
			eventChannel.emit('selectedImage', img);
			uni.navigateBack();
		},
		//查询相册
		getAlbumList() {
			this.$u.api
				.queryAlbumList({
					loveId: this.getLoveStartId,
					currPage: 1,
					pageSize: 100
				})
				.then(res => {
					this.albums = res;
					this.selectAlbum(res[0].albumId, 0);
				});
		},
		//选择一个相册
		selectAlbum(albumId, index) {
			this.currentAlbumIndex = index;
			this.albumId = albumId;
			console.info(albumId);
			this.initPhotos();
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bg-color;
}
.album-list {
	margin: $love-big-margin $love-view-margin;
}
.scroll-X {
	width: 100%;
	overflow: hidden;
	white-space: nowrap;
	border-radius: $love-border-radius;
	.album-name {
		padding: $love-padding;
		width: auto;
		display: inline-block;
		border: 1rpx solid #ff6773;
		margin: 0 $love-margin;
		border-radius: $love-border-radius;
	}
}
.album-content {
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
