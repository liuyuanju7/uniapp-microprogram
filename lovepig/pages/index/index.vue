<template>
	<view class="love-main">
		<!-- 首页头部恋爱时间以及我们相册轮播 @click.stop="changeLoveBanner()"-->
		<view class="love-time"
			:style="'background-image:url(' + getLoveStartBanners + ');height: '+(isTodayend?60:100)+'vh;' ">
			<LoveTime></LoveTime>
			<LoveBtnFun class="love-btn" v-if="!isTodayend"></LoveBtnFun>
		</view>
		<LoveFour class="love-four"></LoveFour>
		<view class="story">
			<item-header title="故事" navigatorUrl="/pages/story/index" margin="20"></item-header>
			<story v-for="(item, index) in storys" :key="index" :story="item"></story>
		</view>

		<u-divider height="200">到心底啦~</u-divider>
		<push-popup ref="addStoryPopup"></push-popup>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
	import LoveTime from './components/love-time.vue';
	import LoveBtnFun from './components/love-btn-fun.vue';
	import LoveFour from './components/love-four.vue';
	import {
		mapGetters,
		mapState,
		mapMutations
	} from 'vuex';
	import {
		DateUtil,
		format
	} from '../../common/DateUtil';
	export default {
		components: {
			LoveTime,
			LoveBtnFun,
			LoveFour
		},
		data() {
			return {
				storys: [],
				misss: [{
					messageContent: '',
					userBase: {
						nickname: ''
					}
				}],
				missPage: 1,
				missData: {
					loveId: '',
					messageContent: '',
					messageType: 1,
					state: 1
				},
				promise: [],
				canIGetUserProfile: false,
				isNewUser: false,
				bannerHeight: 100
			};
		},
		computed: {
			...mapGetters(['getLoveStartId', 'getLoveStartInfo', 'getLoveStartBanners', 'getIsLogin', 'getLoginUser',
				'isTodayend'
			])
		},
		onLoad() {
			if (this.getLoveStartId != 0) {
				this.init();
			}
		},
		onPullDownRefresh() {
			this.init();
		},
		methods: {
			...mapMutations(['modifyLoveStartInfo', 'modifyLastSendDate']),
			init() {
				this.missData.loveId = this.getLoveStartId;
				//查询恋爱空间信息
				this.$u.api
					.queryLoveStart({
						loveId: this.getLoveStartId
					})
					.then(res => {
						// console.info(res);
						this.modifyLoveStartInfo(res);
					});
				//查询思念留言
				this.$u.api
					.queryMessageList({
						loveId: this.getLoveStartId,
						messageType: 1,
						pageSize: 1,
						todayMessage:true,
						userId:this.getLoginUser.id
					})
					.then(res => {
						// console.info(res);
						this.misss = res.data;
						if (this.misss.length != 0) {
							this.modifyLastSendDate(new Date(this.misss[0].createTime));
						}

					});
				//查询故事
				this.$u.api
					.queryStoryList({
						loveId: this.getLoveStartId
					})
					.then(res => {
						// console.info(res);
						this.storys = res.data;
						uni.stopPullDownRefresh();
					});

			},
			//跳转更多信息页面
			moreInfo: function(currId) {
				uni.switchTab({
					url: '/pages/memories/memories',
					success() {
						uni.setStorageSync('memoriesCurrent', currId);
					}
				});
			},
			changeLoveBanner() {
				let that = this;
				uni.showActionSheet({
					itemList: ['修改壁纸'],
					success: function(res) {
						if (res.tapIndex == 0) {
							that.changeRoomWallpaper();
						}
					}
				});
			},
			changeRoomWallpaper() {
				let that = this;
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album'],
					success: function(res) {
						// console.log(res.tempFilePaths);
						that.uploadBanner(res.tempFilePaths);
					}
				});
			},
			uploadBanner(tempFilePaths) {
				let that = this;
				uni.uploadFile({
					url: that.$pig.constData.uploadUrl,
					filePath: tempFilePaths[0],
					name: 'file',
					formData: {
						loveId: that.getLoveStartId,
						entityId: that.getLoveStartId,
						entityType: 2
					},
					success: uploadFileRes => {
						// console.log(uploadFileRes.data);
						//查询恋爱空间信息
						that.$u.api
							.queryLoveStart({
								loveId: that.getLoveStartId
							})
							.then(res => {
								// console.info(res);
								that.loveStart = res;
								that.modifyLoveStartInfo(res);
							});
						that.$refs.uToast.show({
							title: '修改成功',
							type: 'success'
						});
					}
				});
			}
		}
	};
</script>

<style lang="scss">
	.love-time {
		height: 100vh;
		width: 100vw;
		display: flex;
		align-items: center;
		flex-direction: column;
		padding-top: $love-padding;
		background-repeat: no-repeat;
		background-size: cover;
		background-position: center;
		position: relative;

		transition:all 1s;
	}

	.love-btn {
		margin: 90%;
	}

	.message {
		z-index: 100;
		margin-bottom: $love-big-margin;
		margin-top: -30rpx;
		border-radius: $love-border-radius $love-border-radius 0 0;
		background-color: white;
		padding: $love-padding;

		.msg-send {
			padding: 8rpx;
			border-radius: $love-border-radius;
			margin-bottom: $love-margin;
			display: flex;
			background-color: #f3f4f6;

			// max-height: 300rpx;
			.msg-send-input {
				width: 100%;
				font-size: 28rpx;
			}

			.msg-send-input,
			.uni-textarea {
				min-height: 20px;
				max-height: 150px;
			}

			.msg-send-btn {
				padding: 0 $love-padding;
			}
		}

		.we-like {
			width: 100%;
			height: 100rpx;
			margin-bottom: $love-margin;
			display: flex;
			justify-content: space-between;

			.we-like-item {
				width: 49%;
				display: flex;
				height: 100rpx;
				color: white;
				border-radius: $love-border-radius;
				background: linear-gradient(to right, #a6ffcb, #12d8fa);
				text-align: center;
				align-items: center;
			}

			.we-like-woman {
				background: linear-gradient(to right, #ed4264, #ffedbc) !important;
				justify-content: flex-end;
			}

			.people-image {
				width: 100rpx;
				height: 100rpx;
			}

			.we-like-content {
				line-height: 50rpx;
				width: 60%;
			}

			.we-like-number {}
		}

		.message-main {
			height: 288rpx;
			// background:  $love-green;
			// padding: 0 $love-padding;
		}

		.message-item {
			display: flex;
			justify-content: flex-start;
			align-items: flex-start;
			align-content: flex-start;
			flex-wrap: nowrap;
			flex-direction: row;
			width: 49vw;
			// margin: $love-margin;
			background: white;
			// box-shadow: 0 14px 20px -10px rgba(0,0,0,.1);
			box-shadow: 0 6rpx 12rpx rgba(233, 238, 249, 0.9);

			margin-bottom: $love-margin;
			word-break: break-all;
			border-radius: $love-border-radius;
		}

		.message-content {
			// background-color: #feeaec;
			// border: 1rpx solid #fca8b0;
			//     color: #ff6773;

			background: white;
			padding: $love-padding;
			word-break: break-all;
			border-radius: $love-border-radius;
		}
	}

	.todo {
		// margin: 0 $love-margin $love-view-margin $love-margin;

		.todo-main {
			.todo-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				height: 60rpx;
				border-radius: $love-border-radius;
				padding: $love-padding;
				margin-bottom: $love-margin;
				background-color: white;
			}
		}
	}

	.story {
		// margin: 0 $love-margin;
		// margin-left: $love-view-margin;
		// margin-right: $love-view-margin;
	}
</style>