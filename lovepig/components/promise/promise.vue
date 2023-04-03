<template>
	<view class="promise-main" @click="onClick" v-if="promise != null">
		<navigator v-if="promise.promiseType === 1"><view class="promise"></view></navigator>
	</view>
</template>

<script>
export default {
	name: 'Promise',
	props: {
		story: {
			type: Object,
			default: {
				id: {
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
		}
	},
	methods: {
		onClick() {
			this.$emit('click');
		}
	}
};
</script>

<style lang="scss"></style>
