<template>
	<view class="">
		<message-popup ref="messagePopup" :message="misss"></message-popup>
		<view class="love-wrapper">
			<view class="love-items">
				<!-- 留言 -->
				<view class="love-item message-main" v-if="misss.messageContent" @tap="showMessagePopup">
					<view class="message-time">
						{{ $u.timeFrom(misss.createTime, 'yyyy-mm-dd') }}•{{ misss.userBase.nickname }}
					</view>
					<view class="message-content">
						{{ misss.messageContent }}
					</view>
				</view>
				<view class="message-main love-add" v-else @tap="showMessagePopup">
					<u-icon class="icon" name="chat" color="#FFF" size="50"></u-icon>
					<view>每日留言</view>
				</view>
				<!-- 承诺 -->
				<navigator class="love-item promise" v-if="promise.promiseName"
					:url="'/pages/promise/promiseInfo?promiseId='+promise.promiseId">
					<view class="promise-title">{{ promise.promiseName }}还有</view>
					<view class="promise-time">
						<view class="promise-time-day">{{ promise.daysApart }}</view>
						<view class="promise-time-desc">天</view>
					</view>
					<view class="promise-time-info">{{ $u.timeFormat(promise.targetTime, 'mm月dd日') }}</view>
				</navigator>
				<navigator class="promise love-add" v-else url="/pages/promise/editPromise">
					<u-icon class="icon" name="gift" color="#FFF" size="50"></u-icon>
					<view>
						添加承诺
					</view>
				</navigator>
			</view>
			<!-- 图片 -->
			<navigator class="love-pic" url="/pages/album/index">
				<image :src="photo.resourceUrl" class="image-style" alt="" mode="aspectFill" />
			</navigator>
		</view>
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
				misss: {
					messageContent: '',
					userBase: {
						nickname: ''
					}
				},
				photo: {
					resourceId: 251,
					resourceUrl: ''
				},
				promise: {}
			};
		},
		computed: {
			...mapGetters(['getLoveStartId','getLoginUser'])
		},
		created() {
			this.init();
		},
		methods: {
			...mapMutations(['modifyLastSendDate']),
			init() {
				let that = this;
				//查询思念留言
				this.$u.api
					.queryMessageList({
						loveId: this.getLoveStartId,
						messageType: 1,
						pageSize: 1,
						todayMessage:true,
						userId:this.getLoginUser.spouseId
					})
					.then(res => {
						// console.info(res);
						if (res.data.length != 0) {
							this.misss = res.data[0];
						}
					});
				//查最新图片
				this.$u.api
					.queryResourceList({
						loveId: this.getLoveStartId,
						resourceType: 0,
						currPage: 1,
						pageSize: 1,
						queryNew: true
					})
					.then(res => {
						if (res.data.length != 0) {
							that.photo = res.data[0];
						}
					});
				// 查最近的承诺
				this.getPromise();
			},
			generateNumber() {}, //查询承诺
			getPromise() {
				//查询思念留言
				this.$u.api
					.queryPromiseList({
						loveId: this.getLoveStartId,
						promiseType: 1,
						currPage: 1,
						pageSize: 1
					})
					.then(res => {
						// console.info(res);
						if (res.length != 0) {
							this.promise = res[0];
						}
					});
			},
			showMessagePopup() {
				this.$refs.messagePopup.showMessagePopup();
			}
		}
	};
</script>
<style lang="scss" scoped>
	.love-wrapper {
		width: 100%;
		height: 400rpx;
		margin: $love-big-margin 0;
		display: flex;
		justify-content: space-around;
		overflow: hidden;
		// background-color: $love-bgrey-color;
	}

	.love-items {
		width: 46%;
		display: flex;
		flex-direction: column;
		justify-content: space-between;

		.love-item {
			height: 192rpx;
			overflow: hidden;
			border-radius: $love-border-radius;
			display: flex;
			flex-direction: column;
			color: white;

		}
	}

	.message-main {
		background-color: rgba($love-blue, 0.6);

		.message-time {
			background-color: $love-blue;
			padding: $love-padding 0;
			display: inline;
			text-align: center;
		}

		.message-content {
			padding: 10rpx;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: normal;
		}
	}

	.promise {
		background-color: rgba($love-red, 0.6);
		text-align: center;

		.promise-title {
			background-color: $love-red;
			padding: $love-padding 0;
		}

		.promise-time {
			.promise-time-day {
				font-size: 70rpx;
				font-weight: bold;
				display: inline;
			}

			.promise-time-desc {
				display: inline;
			}
		}

		.promise-time-info {
			color: #f2f3f4;
			font-size: 18rpx;
			margin-bottom: 10rpx;
		}
	}


	.love-pic {
		width: 46%;
		height: 100%;
		background-color: $love-green;
		border-radius: $love-border-radius;
	}

	.love-add {
		height: 192rpx;
		border-radius: $love-border-radius;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;
		color: white;
		// font-size: 40rpx;
		font-weight: bold;
		box-shadow: 0 10px 10px #F5F5F5;
	}
</style>