<template>
	<view>
		<u-toast ref="uToast" />
		<u-form :model="album" ref="uForm" label-width="180" label-align="center">
			<u-form-item label="相册名称:" prop="albumName"><u-input v-model="album.albumName" placeholder="请填写相册名称.." /></u-form-item>
			<u-form-item label="相册简介:"><u-input type="textarea" v-model="album.albumDesc" placeholder="请填写相册简介.." /></u-form-item>
			<u-form-item label="相册封面:"><image @click="selectBanner" class="album-banner" :src="album.albumBanner" mode="aspectFill"></image></u-form-item>
		</u-form>

		<view class="base_button" @click="changeAlbumFun">确认修改</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			album: {
				albumId: 0,
				albumName: '',
				albumDesc: '',
				albumBanner: '',
				albumPermission: ''
			},
			rules: {
				albumName: [
					{
						required: true,
						message: '请填写相册名称',
						trigger: ['change']
					}
				]
			}
		};
	},
	onLoad() {
		let that = this;
		const eventChannel = that.getOpenerEventChannel();
		eventChannel.on('albumInfo', function(data) {
			that.album = data;
		});
	},
	onReady() {
		this.$refs.uForm.setRules(this.rules);
	},
	methods: {
		changeAlbumFun() {
			let that = this;
			// console.info(that.album);
			that.$u.api.changeAlbum(that.album).then(res => {
				that.$refs.uToast.show({
					title: '修改成功',
					duration:"1200",
					type: 'primary',
					back: true
				});
			});
		},
		selectBanner() {
			// console.info('选择壁纸');
			let that = this;
			uni.navigateTo({
				url: '/pages/album/selectImage?albumId=' + that.album.albumId,
				events: {
					//获取选择的照片地址
					selectedImage: function(data) {
						// console.log(data);
						that.album.albumBanner = data;
					}
				}
			});
		}
	}
};
</script>

<style lang="scss">
.album-banner {
	width: 200rpx;
	height: 200rpx;
	border-radius: $love-border-radius;
}
</style>
