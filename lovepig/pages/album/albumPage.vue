<template>
	<view class="ap-main">
		<u-toast ref="uToast" />
		<view class="ap-header baseStyle">
			<view class="aph-left">
				<view class="aph-title">锦瑟</view>
				<view class="aph-content">
					<view>此情可待成追忆？</view>
					<view>只是当时已惘然。</view>
				</view>
			</view>
			<view class="aph-right"><image class="image-style" src="/static/icon/common/photo-header.svg" mode="aspectFill"></image></view>
		</view>

		<view class="ap-box">
			<u-row gutter="20">
				<u-col span="6">
					<view class="ap-create" @click="createAlbumMask = true"><u-icon name="plus"></u-icon></view>
				</u-col>
				<u-col span="6" v-for="(album, index) in albums" :key="index">
					<navigator class="ap-item" :url="'/pages/album/albumDetail?id=' + album.albumId" hover-class="none">
						<image class="image-style" :src="album.albumBanner" mode="aspectFill"></image>
						<view class="api-info">
							<view class="api-title">{{ album.albumName }}</view>
							<view class="api-tip">{{ album.photoNum }}张|{{ $u.timeFrom(album.lastUploadTime, false) }}更新</view>
						</view>
					</navigator>
				</u-col>
			</u-row>
		</view>
		<u-popup
			v-model="createAlbumMask"
			mode="bottom"
			:mask-close-able="false"
			border-radius="30"
			height="600rpx"
			:focus="true"
			:closeable="true"
			:safe-area-inset-bottom="false"
		>
			<view class="create-main">
				<view class="create-title">新建相册</view>
				<view class="base-input"><input v-model="createAlbum.albumName" placeholder="相册名称" maxlength="15" cursor-spacing="15"/></view>
				<view class="base-input "><textarea class="textareaStyle" v-model="createAlbum.albumDesc" placeholder="相册简介(可选)" maxlength="50"  cursor-spacing="50"/></view>
				<view class="base_button" @click="createAlbumFun">完成</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	data() {
		return {
			createAlbum: {
				loveId: this.getLoveStartId,
				userId: this.getLoginUserId,
				albumName: '',
				albumDesc: '',
				albumPermission: 1
			},
			createAlbumMask: false,
			currPage: 0,
			loadStatus: 'loadmore',
			albums: [] //相册
		};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getLoginUserId'])
	},
	onLoad() {
		this.init();
		this.getAlbumList();
	},
	//下拉
	onPullDownRefresh() {},
	//触底刷新
	onReachBottom() {
		if (this.loadStatus === 'nomore') {
			return;
		}
		this.loadStatus = 'loading';
		this.getAlbumList();
	},
	methods: {
		init() {
			this.createAlbum.loveId = this.getLoveStartId;
			this.createAlbum.userId = this.getLoginUserId;
		},
		//创建相册
		async createAlbumFun() {
			if (this.createAlbum.albumName.trim() === '') {
				this.$refs.uToast.show({
					title: '请填写相册名称',
					type: 'warning'
				});
				return;
			}
			let res = await this.$u.api.createAlbum(this.createAlbum);
			if (res) {
				this.$refs.uToast.show({
					title: '创建成功',
					type: 'success',
					url: '/pages/album/albumDetail?id=' + res
				});
			}
		},
		getAlbumList() {
			this.$u.api
				.queryAlbumList({
					loveId: this.getLoveStartId,
					currPage: ++this.currPage,
					pageSize: 10
				})
				.then(res => {
					console.info(res);
					this.albums = this.albums.concat(res);
					this.loadStatus = this.$pig.base.getloadMoreTip(res.length);
				});
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bg-color;
}
//相册头部小组件
.ap-header {
	margin: $love-big-margin $love-view-margin;
	height: 220rpx;
	// margin-top: 50rpx;
	display: flex;
	justify-content: space-between;
	align-items: center;

	.aph-title {
		font-size: 36rpx;
		font-weight: bold;
		margin-bottom: 20rpx;
	}

	.aph-content {
		font-size: 26rpx;
		padding-left: $love-padding;
		color: $u-tips-color;
	}

	.aph-right {
		width: 250rpx;
		height: 250rpx;
		margin: -50rpx 50rpx 0rpx 50rpx;
		flex-shrink: 0;
	}
}

.ap-box {
	margin: 0 10rpx;
}
//创建相册按钮
.ap-create {
	font-size: 120rpx;
	color: $love-color;
	border-radius: $love-border-radius;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: center;
	height: 380rpx;
	background-color: $u-type-primary-light;
	margin-bottom: $love-view-margin;
}
//相册元素
.ap-item {
	height: 380rpx;
	background-color: white;
	border-radius: $love-border-radius;
	position: relative;
	margin-bottom: $love-view-margin;

	.api-info {
		position: absolute;
		bottom: 0px;
		left: 0;
		background-color: rgba($color: #000, $alpha: 0.3);
		border-radius: 0 0 $love-border-radius $love-border-radius;
		height: 90rpx;
		width: 100%;
		color: white;
		text-align: center;
	}

	.api-title {
		font-weight: bold;
		// font-size: 30rpx;
		padding: 6rpx 0;
	}
	.api-tip{
		font-size: 24rpx;
	}
}

// 新建相册
.create-main {
	.create-title {
		text-align: center;
		font-weight: bold;
		font-size: 36rpx;
		line-height: 100rpx;
	}

	.textareaStyle {
		height: 150rpx;
		width: 100%;
	}
}
</style>
