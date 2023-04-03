<template>
	<view class="main">
		<view class="story-main">
			<view class="story-header">
				<u-avatar class="story-u-avatar" size="mini" :src="story.userBase.avatar"></u-avatar>
				<view>
					<view class="story-username">{{ story.userBase.nickname }}</view>
					<view class="story-publish">
						<view class="story-publishinfo">{{ $u.timeFormat(story.publishTime, 'yyyy年mm月dd日') }}</view>
						<u-icon name="heart" style="margin-right: 6rpx;"></u-icon>
						<view class="story-publishinfo">{{ story.weather }}</view>
					</view>
				</view>
			</view>
			<view class="story-content">
				<text :user-select="true" space="nbsp">{{ story.content }}</text>
			</view>
			<story-image :images="story.resources"></story-image>
		</view>

		<navigator class="promise" v-if="story.promiseRecord != null" :url="'/pages/promise/promiseInfo?promiseId=' + story.promiseRecord.promiseId" hover-class="none">
			<view class="promise-info">
				<image class="promise-avatar" src="/static/icon/promise/promise.png"></image>
				<view class="">
					<view class="promise-title">{{ story.promiseRecord.promiseName }}</view>
					<view class="promise-tip">第{{ story.promiseRecord.annual }}次兑现记录</view>
				</view>
			</view>
			<view class="promise-more">
				更多记录
				<u-icon name="arrow-right"></u-icon>
			</view>
		</navigator>

		<view class="story-comment">
			<item-header title="评论" :showMoreBtn="false"></item-header>
			<view class="comment-none" v-if="!existComment"><u-empty text="还没人说话呢" mode="message"></u-empty></view>
			<view class="comment" v-if="!existComment" v-for="(res, index) in commentList" :key="res.id">
				<view class="left"><image :src="res.url" mode="aspectFill"></image></view>
				<view class="right">
					<view class="top">
						<view class="name">{{ res.name }}</view>
						<view class="like" :class="{ highlight: res.isLike }">
							<view class="num">{{ res.likeNum }}</view>
							<u-icon v-if="!res.isLike" name="thumb-up" :size="30" color="#9a9a9a" @click="getLike(index)"></u-icon>
							<u-icon v-if="res.isLike" name="thumb-up-fill" :size="30" @click="getLike(index)"></u-icon>
						</view>
					</view>
					<rich-text class="content" :nodes="res.contentText"></rich-text>
					<!-- <view class="content">{{ res.contentText }}</view> -->
					<view class="reply-box">
						<view class="item" v-for="(item, index) in res.replyList" :key="item.index">
							<view class="username">{{ item.name }}</view>
							<view class="text">{{ item.contentStr }}</view>
						</view>
						<view class="all-reply" @tap="toAllReply" v-if="res.replyList != undefined">
							共{{ res.allReply }}条回复
							<u-icon class="more" name="arrow-right" :size="26"></u-icon>
						</view>
					</view>
					<view class="bottom">
						{{ res.date }}
						<view class="reply" @click="showAddComment = true">回复</view>
					</view>
				</view>
			</view>
		</view>

		<view class="comment-module" v-if="false">
			<view class="comment-module-input" @click="showAddComment = true">
				<u-icon name="edit-pen"></u-icon>
				爱叭叭
			</view>
			<view class="comment-module-option">
				<u-icon name="chat" size="38" label="1" label-size="24x" @click="showAddComment = true"></u-icon>
				<u-icon name="heart" size="36" label="200" label-size="24"></u-icon>
				<u-icon name="share" size="34"></u-icon>
			</view>
			<!-- 操作交互 -->
			<u-popup v-model="showAddComment" class="add-comment" mode="bottom" border-radius="36" height="360">
				<view class="add-comment-top">
					<view>评论</view>
					<view>发布</view>
				</view>
				<!-- <textarea placeholder="回复 joke" maxlength="-1" /> -->
				<u-input class="add-comment-input" maxlength="-1" v-model="value" type="textarea" :auto-height="false" :clearable="false" />
			</u-popup>
		</view>
		<!-- 加载动画 -->
		<loading ref="lovdLoading"></loading>
	</view>
</template>

<script>
export default {
	data() {
		return {
			story: {
				id: 3,
				loveId: 521,
				storyType: 1,
				title: '上海之恋',
				banner: null,
				summary: null,
				content:
					'从素颜到淡妆，从没牌子到有牌子，从沐浴露的味道到香水的味道，从帆布鞋到高跟鞋；从爸爸满头的黑发到渐渐出现的白发，从妈妈光滑嫩白的皮肤到眼角的皱纹，从爷爷笔直的身子到佝偻的背，从你天真的脸庞到所谓大人的模样。我知道我们都回不去了。',
				commentState: 1,
				commentNum: 0,
				likeNum: 0,
				location: '上海.外滩',
				weather: '多云',
				storyTime: '2021-06-19 18:16:13',
				publishTime: '2021-06-19 18:09:09',
				state: 1,
				createTime: '2021-06-19 18:09:19',
				modifyTime: '2021-06-19 18:09:20',
				userBase: {
					id: 1,
					username: 'joke',
					gender: null,
					nickname: '大宝贝',
					avatar: 'https://lovestatic.jokeo.cn/202106/222.jpg'
				},
				resources: []
			},
			banner: {},
			existComment: false,
			commentList: [],
			showAddComment: false,
			value: ''
		};
	},
	onLoad(e) {
		this.getStoryDetail(e.id);
		// this.getComment();
	},
	methods: {
		getStoryDetail(id) {
			this.$u.api
				.queryStoryDetail({
					id
				})
				.then(res => {
					console.log(res);
					this.story = res;

					//加载动画过度
					this.$refs.lovdLoading.hidden();
				});
		},
		//跳转到评论区域
		toComment() {
			//从当前位置到达目标位置
			uni.createSelectorQuery()
				.select('#comment')
				.boundingClientRect(data => {
					//目标位置的节点：类或者id
					uni.createSelectorQuery()
						.select('.main')
						.boundingClientRect(res => {
							//最外层盒子的节点：类或者id
							uni.pageScrollTo({
								duration: 100, //过渡时间
								scrollTop: data.top - res.top //到达距离顶部的top值
							});
						})
						.exec();
				})
				.exec();
		},
		// 跳转到全部回复
		toAllReply() {
			uni.navigateTo({
				url: '/pages/template/comment/reply'
			});
		},
		// 点赞
		getLike(index) {
			this.commentList[index].isLike = !this.commentList[index].isLike;
			if (this.commentList[index].isLike == true) {
				this.commentList[index].likeNum++;
			} else {
				this.commentList[index].likeNum--;
			}
		},
		// 评论列表
		getComment() {
			this.commentList = [
				{
					id: 1,
					name: '叶轻眉',
					date: '12-25 18:58',
					contentText: '我不信伊朗会没有后续反应，美国肯定会为今天的事情付出代价的',
					url: 'https://cdn.uviewui.com/uview/template/SmilingDog.jpg',
					allReply: 12,
					likeNum: 33,
					isLike: false,
					replyList: [
						{
							name: 'uview',
							contentStr: 'uview是基于uniapp的一个UI框架，代码优美简洁，宇宙超级无敌彩虹旋转好用，用它！'
						},
						{
							name: '粘粘',
							contentStr: '今天吃什么，明天吃什么，晚上吃什么，我只是一只小猫咪为什么要烦恼这么多'
						}
					]
				},
				{
					id: 2,
					name: '叶轻眉1',
					date: '01-25 13:58',
					contentText: '我不信伊朗会没有后续反应，美国肯定会为今天的事情付出代价的',
					allReply: 0,
					likeNum: 11,
					isLike: false,
					url: 'https://cdn.uviewui.com/uview/template/niannian.jpg'
				},
				{
					id: 3,
					name: '叶轻眉2',
					date: '03-25 13:58',
					contentText: '我不信伊朗会没有后续反应，美国肯定会为今天的事情付出代价的',
					allReply: 0,
					likeNum: 21,
					isLike: false,
					allReply: 2,
					url: '../../../static/logo.png',
					replyList: [
						{
							name: 'uview',
							contentStr: 'uview是基于uniapp的一个UI框架，代码优美简洁，宇宙超级无敌彩虹旋转好用，用它！'
						},
						{
							name: '豆包',
							contentStr: '想吃冰糖葫芦粘豆包，但没钱5555.........'
						}
					]
				},
				{
					id: 4,
					name: '叶轻眉3',
					date: '06-20 13:58',
					contentText: '我不信伊朗会没有后续反应，美国肯定会为今天的事情付出代价的',
					url: 'https://cdn.uviewui.com/uview/template/SmilingDog.jpg',
					allReply: 0,
					likeNum: 150,
					isLike: false
				}
			];
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $love-bgrey-color;
}

.main {
	display: flex;
	flex-direction: column;
}

.main > view {
	padding: 0 $love-view-padding;
	background-color: white;
}

.story-main > view {
	margin: $love-view-margin 0;
}

//start 基础样式
.story-header {
	display: flex;
	flex-direction: row;
	align-items: center;
	color: $u-main-color;

	.story-u-avatar {
		margin-right: $love-margin;
	}

	.story-username {
		// font-weight: 530;
		// margin-bottom: 2rpx;
		font-size: 28rpx;
	}

	.story-publish {
		display: flex;
		color: $u-tips-color;

		.story-publishinfo {
			font-size: 24rpx;
			margin-right: 6rpx;
		}
	}
}

.story-content {
	font-size: 30rpx;
	overflow: hidden;
	word-break: break-all;
	line-height: 1.67;
	letter-spacing: 1rpx;
}

.promise {
	height: 100rpx;
	margin-top: $love-margin;
	padding: 0 $love-view-padding;
	background-color: white;
	display: flex;
	justify-content: space-between;
	align-items: center;

	.promise-info {
		display: flex;
		// justify-content: space-between;
		align-items: center;
	}

	.promise-avatar {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		margin-right: $love-margin;
	}

	.promise-title {
		font-size: 30rpx;
		font-weight: bold;
		// margin-bottom: 0rpx;
	}

	.promise-tip {
		font-size: 24rpx;
		color: $u-content-color;
	}

	.promise-more {
		font-size: 24rpx;
		color: $u-content-color;
	}
}

.story-comment {
	background-color: #ffffff;
	height: 1200rpx;
	margin-top: $love-margin;
}

.comment {
	display: flex;
	padding: 30rpx;

	.left {
		image {
			width: 64rpx;
			height: 64rpx;
			border-radius: 50%;
			background-color: #f2f2f2;
		}
	}

	.right {
		flex: 1;
		padding-left: 20rpx;
		font-size: 30rpx;

		.top {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 10rpx;

			.name {
				color: $love-color;
			}

			.like {
				display: flex;
				align-items: center;
				color: #9a9a9a;
				font-size: 26rpx;

				.num {
					margin-right: 4rpx;
					color: #9a9a9a;
				}
			}

			.highlight {
				color: $love-color;

				.num {
					color: $love-color;
				}
			}
		}

		.content {
			margin-bottom: 10rpx;
		}

		.reply-box {
			background-color: rgb(242, 242, 242);
			border-radius: 12rpx;

			.item {
				padding: 20rpx;
				border-bottom: solid 2rpx $u-border-color;

				.username {
					font-size: 24rpx;
					color: #999999;
				}
			}

			.all-reply {
				padding: 20rpx;
				display: flex;
				color: $love-color;
				align-items: center;

				.more {
					margin-left: 6rpx;
				}
			}
		}

		.bottom {
			margin-top: 20rpx;
			display: flex;
			font-size: 24rpx;
			color: #9a9a9a;

			.reply {
				color: $love-color;
				margin-left: 10rpx;
			}
		}
	}
}

.comment-module {
	position: fixed;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 80rpx;
	background-color: #ffffff;
	box-shadow: 0 1px 1px 1px rgba(0, 0, 0, 0.3);
	display: flex;
	flex-direction: row;
	justify-content: space-between;

	.comment-module-input {
		height: 100%;
		width: 60%;
		background-color: white;
		// padding: 20rpx;
		line-height: 80rpx;
		color: $u-tips-color;
	}

	// .comment-module-input:active {
	// 	background-color: $love-color;
	// }

	.comment-module-option {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		height: 100%;
		width: 40%;
	}
}

.add-comment {
	.add-comment-top {
		display: flex;
		justify-content: space-between;
		width: 100%;

		view {
			padding: 20rpx 30rpx;
			font-size: 32rpx;
		}
	}

	textarea {
		background-color: $u-bg-color;
		margin: 0 28rpx;
		width: 92%;
	}

	.add-comment-input {
		margin: 0 30rpx;
		max-height: 230rpx;
		// border: 1rpx solid #000000;
	}
}
</style>
