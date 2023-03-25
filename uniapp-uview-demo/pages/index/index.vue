<template>
	<view class="content">
		<u-swiper :list="list1" @change="change" @click=""></u-swiper>

		<view class="u-page">
			<view class="u-demo-block">
				<view class="u-demo-block__content">
					<u-row customStyle="margin-top: 10px">
						<u-col span="12">
							<view class="bg-purple-light text-area">
								<text class="title">{{title}}</text>
							</view>
						</u-col>
					</u-row>
					<u-row justify="space-between" customStyle="margin-top: 10px;innerWidth: 80%">
						<u-col span="8">
							<view>
								<u-search placeholder="日照香炉生紫烟" v-model="keyword" :show-action="false"></u-search>
							</view>
						</u-col>
					</u-row>
				</view>
			</view>
		</view>

		

		<view>
			<u--textarea v-model="question" placeholder="请输入内容" count></u--textarea>
		</view>


		<view>
			<u-tabbar :value="value1" @change="change1" :fixed="true" :placeholder="false" :safeAreaInsetBottom="false">
				<u-tabbar-item text="首页" icon="home" @click="click1"></u-tabbar-item>
				<u-tabbar-item text="放映厅" icon="photo" @click="click1"></u-tabbar-item>
				<u-tabbar-item text="直播" icon="play-right" @click="click1"></u-tabbar-item>
				<u-tabbar-item text="我的" icon="account" @click="click1"></u-tabbar-item>
			</u-tabbar>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello, Tell me',
				keyword: '',
				question: '',
				list1: [
					'https://cdn.uviewui.com/uview/swiper/swiper11.png',
					'https://cdn.uviewui.com/uview/swiper/swiper21.png',
					'https://cdn.uviewui.com/uview/swiper/swiper31.png',
				],
				value1: 0
			}
		},
		onLoad() {

		},
		methods: {
			change() {

			},
			click1(e) {
				console.log('click1', e);
			},
			change1() {

			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		/* align-items: center; */
		justify-content: center;
		padding: 10px;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
