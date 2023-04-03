<template>
	<view class="add-story-popup">
		<view class="story-fab" @click="showStoryPopup = true" v-if="isTodayend"><u-icon name="plus"></u-icon></view>
		<u-popup v-model="showStoryPopup" mode="bottom" border-radius="20" height="200px">
			<view class="love-tip">~此刻留下的记忆是你我最美好的回忆~</view>
			<view class="add-story-mode">
				<u-row gutter="16">
					<u-col span="4" v-for="(item, index) in addStoryModes" :key="index">
						<view hover-class="none" @click="openSendView(index)">
							<view class="add_mode_item">
								<image class="mode_image" :src="item.icon" mode="aspectFill"></image>
								<!-- <u-icon :name="item.icon" :color="item.iconBackground" size="100rpx"></u-icon> -->
								<view class="add_mode_title">{{ item.title }}</view>
							</view>
						</view>
					</u-col>
				</u-row>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState,
		mapMutations
	} from 'vuex';
	export default {
		name: 'push-popup',
		data() {
			return {
				showStoryPopup: false,
				addStoryModes: [{
						title: '发动态',
						id: 'loveRoom',
						icon: '/static/icon/memoriesPage/story1.png',
						pageUrl: '/pages/saveStory/put-dynamic'
					},
					{
						title: '定承诺',
						id: 'loveRoom',
						icon: '/static/icon/memoriesPage/promise.png',
						pageUrl: '/pages/promise/editPromise'
					},
					{
						title: '纪念日',
						id: 'loveRoom',
						icon: '/static/icon/memoriesPage/time.png',
						pageUrl: '/pages/anniversary/editAnniversary'
					},
					{
						title: '发照片',
						id: 'putPhoto',
						icon: '/static/icon/memoriesPage/photo1.png',
						pageUrl: '/pages/album/pushPhoto'
					}
				]
			};
		},
		watch: {

		},
		computed: {
			...mapGetters(['isTodayend'])
		},
		methods: {
			//跳转到发送故事的页面
			openSendView(index) {
				if (!this.$pig.user.isLogin()) {
					return;
				}
				let that = this;
				that.showStoryPopup = false;
				let id = that.addStoryModes[index].id;
				if (id === 'putPhoto') {
					that.openPutPhotoView(index);
					return;
				}
				let pageUrl = that.addStoryModes[index].pageUrl;
				// console.info(pageUrl);
				//在起始页面跳转到test.vue页面并传递参数
				uni.navigateTo({
					url: pageUrl
				});
			},
			openPutPhotoView(index) {
				let that = this;
				let pageUrl = that.addStoryModes[index].pageUrl;
				uni.chooseImage({
					count: 9,
					sizeType: ['original', 'compressed'],
					sourceType: ['album'],
					success: function(selectImages) {
						// console.info(JSON.stringify(selectImages.tempFilePaths));
						uni.navigateTo({
							url: pageUrl,
							success: function(event) {
								event.eventChannel.emit('selectImages', {
									images: selectImages.tempFilePaths,
									albumId: 0
								});
							}
						});
					}
				});
			}
		}
	};
</script>

<style lang="scss" scoped>
	.add-story-popup {
		.love-tip {
			font-size: 20rpx;
			color: $u-tips-color;
			text-align: center;
			margin: 30rpx 0;
		}

		.story-fab {
			position: fixed;
			display: flex;
			justify-content: center;
			align-items: center;
			z-index: 10000;
			border-radius: 45px;
			box-shadow: 0 0 2px 0px $love-color;
			width: 90rpx;
			height: 90rpx;
			color: #ffffff;
			background-color: $love-color;
			font-size: 50rpx;
			right: 30px;
			bottom: 40px;
		}

		.add-story-mode {
			margin: $love-margin 0;

			.add_mode_item {
				text-align: center;
				border-radius: $love-border-radius;
				margin-bottom: 20rpx;
			}

			.add_mode_title {
				// margin: 6rpx 0;
			}

			.mode_image {
				width: 66rpx;
				height: 66rpx;
			}
		}
	}
</style>