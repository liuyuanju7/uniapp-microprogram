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
					<u-row customStyle="margin-top: 10px;" gutter="10px">
						<u-col justify="start" span="10">
							<view>
								<u-search placeholder="type msg" v-model="keyword" :show-action="false"></u-search>
							</view>
						</u-col>
						<u-col justify="end" span="2">
							<view class="">
								<u-badge shape="horn" type="error" max="9999" value="99999"></u-badge>
							</view>
						</u-col>
					</u-row>
					<u-row customStyle="margin-top: 10px;">
						<u-col justify="end" span="12">
							<view>
								<u--textarea v-model="question" placeholder="请输入内容" count></u--textarea>
							</view>
						</u-col>
					</u-row>
					<u-row customStyle="margin-top: 10px;">
						<u-col justify="end" span="12">
							<view>
								<u-grid :border="true" @click="click">
									<u-grid-item v-for="(itemListItem, itemListIndex) in itemList" :key="baseListIndex">
										<u-icon :customStyle="{paddingTop:20+'rpx'}" :name="itemListItem.name"
											:size="22"></u-icon>
										<text class="grid-text">{{itemListItem.title}}</text>
									</u-grid-item>
								</u-grid>
							</view>
						</u-col>
					</u-row>

					<u-button @tap=getAnswer></u-button>

				</view>
			</view>
		</view>

		<view>
			<u-tabbar :value="value1" @change="change1" :fixed="true" :placeholder="false" :safeAreaInsetBottom="false">
				<u-tabbar-item text="首页" icon="home" @click="click1"></u-tabbar-item>
				<u-tabbar-item text="问答" icon="photo" @click="toBaseQuestion"></u-tabbar-item>
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
				value1: 0,
				itemList: [{
						name: 'photo',
						title: '图片'
					},
					{
						name: 'lock',
						title: '锁头'
					},
					{
						name: 'star',
						title: '星星'
					},
					{
						name: 'hourglass',
						title: '沙漏'
					},
					{
						name: 'home',
						title: '首页'
					},
					{
						name: 'star',
						title: '音量'
					},
				]
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

			},
			toBaseQuestion() {
				uni.navigateTo({
					url: "/pages/question/base"
				})
			},
			getAnswer() {
				uni.request({
					url: 'https://service-107sam5o-1317183770.hk.apigw.tencentcs.com/v1/completions', //仅为示例，并非真实接口地址。
					data: {
						model: "text-davinci-003",
						prompt: `input:测试问题?
			        output:`,
						max_tokens: 300,
						temperature: .6,
						stop: ['output:']
					},
					header: {
						'Authorization': 'Bearer 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'
					},
					contentType: 'json',
					dataType: 'json',
					success: (res) => {
						console.log(res.data);
						this.question = 'request success' + this.keyword;
					}
				});

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

	.badge-box {
		width: 40px;
		height: 30px;
		background-color: #909193;
		border-radius: 15px;
	}

	.grid-text {
		font-size: 14px;
		color: #909399;
		padding: 10rpx 0 20rpx 0rpx;
		/* #ifndef APP-PLUS */
		box-sizing: border-box;
		/* #endif */
	}
</style>
