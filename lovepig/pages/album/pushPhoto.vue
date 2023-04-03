<template>
	<view class="main">
		<u-toast ref="uToast" />
		<view class="select-album" v-if="needSelctAlbum">
			<picker @change="changeAlbum" :value="selectAlbumIndex" :range="albums" range-key="albumName">
				<view class="sa-content">上传到『{{ albums[selectAlbumIndex].albumName }}』相册</view>
			</picker>
		</view>
		<view class="upload-main">
			<u-upload ref="uUpload" :action="uploadUrl" max-count="100" max-size="3145728" :auto-upload="false"
				:form-data="uploadForm" width="220" height="220" :header="uploadHeader" :file-list="fileList"
				@on-uploaded="uploadSuccess" @on-success="uploadOnSuccess"></u-upload>
		</view>
		<view class="base_button" @click="uploadImage">开始上传</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState,
		mapMutations
	} from 'vuex';
	export default {
		data() {
			return {
				uploadUrl: this.$pig.constData.uploadUrl,
				fileList: [],
				uploadForm: {
					loveId: 0,
					entityId: 0,
					entityType: 3
				},
				uploadHeader: {
					Authorization: ''
				},
				albums: [],
				needSelctAlbum: false,
				selectAlbumIndex: 0
			};
		},
		computed: {
			...mapGetters(['getLoveStartId', 'getIsLogin', 'getToken'])
		},
		onLoad(e) {
			let that = this;
			that.init();
			const eventChannel = that.getOpenerEventChannel();
			eventChannel.on('selectImages', function(data) {
				console.info(data)
				data.images.forEach(img => {
					let param = {};
					param.url = img;
					param.progress = 0;
					that.fileList.push(param);
				});
				if (data.albumId == 0) {
					that.getAlbumList();
					that.needSelctAlbum = true;
				} else {
					that.uploadForm.entityId = data.albumId;
				}
			});
			// console.info(that.uploadForm)
		},
		methods: {
			init() {
				this.uploadForm.loveId = this.getLoveStartId;
				this.uploadHeader.Authorization = this.getToken;
			},
			uploadImage() {
				this.$refs.uUpload.upload();
			},
			getAlbumList() {
				this.$u.api
					.queryAlbumList({
						loveId: this.getLoveStartId,
						currPage: 1,
						pageSize: 100
					})
					.then(res => {
						console.info(res);
						this.albums = res;
						this.uploadForm.entityId = this.albums[0].albumId;
					});
			},
			changeAlbum: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value);
				this.selectAlbumIndex = e.target.value;
				this.uploadForm.entityId = this.albums[this.selectAlbumIndex].albumId;
			},
			//每张图片上传成功之后的回调
			uploadOnSuccess(data, index, lists, name) {
				// console.info(data);
				// console.info(index);
				// console.info(lists);
				// console.info(name);
			},
			async uploadSuccess() {
				let files = this.$refs.uUpload.lists.filter(val => val.progress == 100);
				await this.$u.api.ChangeUploadInfo({
					albumId: this.uploadForm.entityId,
					imageNumber: files.length
				});
				if (this.needSelctAlbum) {
					this.$refs.uToast.show({
						title: '上传完成',
						type: 'primary'
					});
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/album/albumDetail?id=' + this.uploadForm.entityId
						});
					}, 1500);
				} else {
					const eventChannel = this.getOpenerEventChannel();
					let tempImg = [];
					this.$refs.uUpload.lists.forEach(img => {
						console.info(img);
						tempImg.push({
							"resourceUrl": img.response.data
						})
					})
					eventChannel.emit('onUploadImages', {
						onUploadImages: tempImg
					});
					this.$refs.uToast.show({
						title: '上传完成',
						type: 'primary',
						back: true
					});
				}
			}
		}
	};
</script>
<style lang="scss">
	.upload-main {
		// padding: 0 auto;
		margin-left: 20rpx;
	}

	.select-album {
		.sa-content {
			height: 100rpx;
			margin: $love-big-margin;
			border-radius: $love-border-radius;
			background-color: #f3f4f6;
			text-align: center;
			line-height: 100rpx;
			font-size: 32rpx;
		}
	}
</style>
