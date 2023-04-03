<template>
	<view class="story-card" @click="onClick" v-if="story != null">
		<!-- 
		文章显示一张首图 1.标题加摘要 2.标题加首图 3.标题加首图加简短内容摘要 
		动态显示多张    1.摘要 2.摘要加单图 3.摘要加2图 4.摘要加多图最多9
		-->
		<!--动态 -->
		<navigator v-if="story.storyType === 1" :url="'/pages/story/dynamicDetail?id=' + story.storyId" hover-class="none">
			<view class="story-d">
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
				<view class="story-d-content ">{{ story.summary }}...</view>

				<story-image :images="story.resources"></story-image>
				<view class="story-extend">
					<view class="promise" @click.stop="openPromise()" v-if="story.promiseRecord != null">
						<u-tag :text="story.promiseRecord.promiseName + '第' + story.promiseRecord.annual + '次记录'" mode="plain" shape="circle" type="primary" />
					</view>
				</view>
				<!-- 	<view class="story-d-footer">
						<u-icon name="heart" label="111" color="u-light-color" size="42"></u-icon>
						<u-icon name="chat" label="111" color="u-light-color" size="42"></u-icon>
						<u-icon name="moments" label="111" color="u-light-color" size="42"></u-icon>
					</view> -->
			</view>
		</navigator>
		<!--文章（标题-大图-摘要）-->
		<navigator v-if="story.storyType === 2" :url="'/pages/story/story-detail?id=' + story.id" hover-class="none">
			<view class="article-base">
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
				<view class="article-base-title">{{ story.title }}</view>
				<view class="article-banner" v-if="story.banner != ''"><image class="picture-image" :src="story.banner" mode="aspectFill"></image></view>
				<view class="article-base-content ">{{ story.summary }}...</view>
			</view>
		</navigator>

		<!--文章（大图） -->
		<navigator v-if="story.storyType === 3" :url="'/pages/story/story-detail?id=' + story.id" hover-class="none">
			<view class="article-big">
				<image class="article-img" :src="story.banner" mode="aspectFill"></image>
				<view class="article-title">{{ story.title }}</view>
			</view>
		</navigator>
	</view>
</template>

<script>
/**
 * ArticleCard 图文卡片
 * @description 卡片视图组件
 * @property {String} title 文章标题
 * @property {String} author 文章作者名称
 * @property {String, Number} number 文章评论数
 * @property {String, Number} publishTime 文章发布时间
 * @property {Array} resources 三图图片列表
 * @property {String} mode =卡片模式
 * @event {Function} click 点击 Card 触发事件
 * @example <uni-card title="标题文字" thumbnail="https://img-cdn-qiniu.dcloud.net.cn/new-page/uni.png" extra="额外信息" note="Tips">内容主体，可自定义内容及样式</uni-card>
 */
export default {
	name: 'Story',
	props: {
		story: {
			type: Object,
			default: {
				storyId: {
					type: Number,
					default: 0
				}, // 标题
				// 内容类型 dynamic=动态  article-base=普通文章  article-big=大图文章
				storyType: {
					type: Number,
					default: 1
				},
				userBase: {
					// 作者
					type: Object,
					default: {
						id: {
							// 标题
							type: Number,
							default: 0
						},
						nickname: {
							// 昵称
							type: String,
							default: ''
						},
						avatar: {
							// 头像
							type: String,
							default: ''
						}
					}
				},
				title: {
					// 标题
					type: String,
					default: ''
				},
				banner: {
					// 头图
					type: String,
					default: ''
				},
				summary: {
					// 摘要
					type: String,
					default: ''
				},
				content: {
					//内容
					type: String,
					default: ''
				},
				publishTime: {
					// 发布文章的时间
					type: String,
					default: '1小时前'
				},
				weather: {
					//天气
					type: String,
					default: ''
				},
				resources: {
					//文章图片
					type: Array,
					default() {
						return [];
					}
				},
				commentNum: {
					// 评论数
					type: Number,
					default: '0'
				},
				likeNum: {
					// 评论数
					type: Number,
					default: '0'
				}
			}
		},
		margins: {
			type: [String],
			default: ''
		}, // 边距
		radius: {
			type: [String],
			default: ''
		} // 圆角
	},
	methods: {
		onClick() {
			this.$emit('click');
		},
		openPromise() {
			// console.info(`/pages/promise/promiseInfo?promiseId=${this.story.promiseRecord.promiseId}`);
			uni.navigateTo({
				url: `/pages/promise/promiseInfo?promiseId=${this.story.promiseRecord.promiseId}`,
				animationType: 'fade-in'
			});
		},
		lookImage(currImage) {
			// console.log('点击查看' + currImage);
			var images = [];
			this.story.resources.forEach(item => images.push(item.resourceUrl));
			// console.log(images);
			uni.navigateTo({
				url: '/pages/commoms/showimg?imgs=' + JSON.stringify(images) + '&current=' + currImage,
				animationType: 'fade-in'
			});
		},
		lookImageOld(imageUrl) {
			console.log(imageUrl); // http://192.168.100.251:8970/6_1597822634094.png
			var images = [];
			imageUrl.forEach(item => images.push(item.resourceUrl));
			console.log(images); // ["http://192.168.100.251:8970/6_1597822634094.png"]
			uni.previewImage({
				// 预览图片  图片路径必须是一个数组 => ["http://192.168.100.251:8970/6_1597822634094.png"]
				current: 0,
				urls: images,
				longPressActions: {
					//长按保存图片到相册
					itemList: ['保存图片'],
					success: data => {
						console.log(data);
						uni.saveImageToPhotosAlbum({
							//保存图片到相册
							filePath: payUrl,
							success: function() {
								uni.showToast({
									icon: 'success',
									title: '保存成功'
								});
							},
							fail: err => {
								uni.showToast({
									icon: 'none',
									title: '保存失败，请重新尝试'
								});
							}
						});
					},
					fail: err => {
						console.log(err.errMsg);
					}
				}
			});
		}
	}
};
</script>

<style lang="scss">
.story-card {
	background-color: #ffffff;
	// box-shadow: 0 10px 30px rgba(209,213,223,.5);
	border-radius: $love-border-radius;
	// margin: $love-view-margin 0;
	margin-bottom: $love-view-margin;
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
		// margin-bottom: 4rpx;
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

.story-d-footer {
	display: flex;
	flex-wrap: wrap;
	flex-direction: row;
	align-items: center;
	justify-content: space-around;
	padding-top: $love-border-radius;
}

// end 基础样式

.story-d {
	// padding: $love-padding;
	margin: $love-margin;
	.story-d-content {
		margin: $love-margin 0;
		word-break:break-all;
	}

	// 动态大图
	.story-d-picture-big {
		height: 280rpx;
		width: 100%;
		margin: $love-margin 0;

		.picture-image {
			height: 100%;
			width: 100%;
			border-radius: $love-border-radius;
		}
	}

	// 动态两个图
	.story-double-picture {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		justify-content: space-between;
		width: 100%;
		border-radius: $love-border-radius;

		.iamge_item {
			flex: 0 0 49.3%;
			border-radius: $love-border-radius;
			height: 280rpx;
			margin-bottom: $love-margin;
		}

		.picture-image {
			width: 100%;
			height: 280rpx;
			border-radius: $love-border-radius;
		}
	}

	// 动态多个图
	.story-d-picture {
		display: flex;
		align-items: center;
		flex-flow: wrap;
		justify-content: space-between;
		border-radius: $love-border-radius;

		.iamge_item {
			flex: 0 0 32.4%;
			border-radius: $love-border-radius;
			height: 220rpx;
			margin-bottom: 8rpx;
		}

		.picture-image {
			width: 100%;
			height: 220rpx;
			border-radius: $love-border-radius;
		}
	}

	.story-extend {
		display: flex;
		.promise {
			margin: $love-margin 0;
		}
	}
}

.article-base {
	padding: $love-padding;
	.article-base-title {
		font-size: 34rpx;
		font-weight: bold;
		padding: $love-padding 0;
	}

	.article-banner {
		height: 280rpx;
		width: 100%;

		.picture-image {
			height: 100%;
			width: 100%;
			border-radius: $love-border-radius;
		}
	}

	.article-base-content {
		padding-top: $love-padding;
		color: $u-content-color;
	}
}

.article-big {
	height: 360rpx;
	position: relative;

	.article-img {
		height: 100%;
		width: 100%;
		border-radius: $love-border-radius;
	}

	.article-title {
		position: absolute;
		bottom: 0;
		left: 0;
		color: white;
		font-size: 34rpx;
		font-weight: bold;
		padding: $love-padding;
		border-radius: $love-border-radius;
		width: 100%;
		background-color: rgba($color: #000000, $alpha: 0.3);
	}
}
</style>
