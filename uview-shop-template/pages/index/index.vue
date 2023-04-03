<template>

	<view class="content">

		<view class="u-swiper m_top m_list_bg m_card">

			<u-swiper :list="bannerList"  :autoplay="true" radius="5"
				bgColor="#ffffff" ></u-swiper>
		</view>
		<view class="m_top m_list_bg m_card">
			<u-grid :border="false" col="4">
				<u-grid-item @click="open()">
					<u-icon :customStyle="{paddingTop:20+'rpx'}" name="home" :size="40"></u-icon>
					<text class="grid-text">关于民康</text>
				</u-grid-item>
				<u-grid-item>
					<u-icon :customStyle="{paddingTop:20+'rpx'}" name="home" :size="40"></u-icon>
					<text class="grid-text">民康文化</text>
				</u-grid-item>

				<u-grid-item>
					<u-icon :customStyle="{paddingTop:20+'rpx'}" name="home" :size="40"></u-icon>
					<text class="grid-text">我的团队</text>
				</u-grid-item>
				<u-grid-item>
					<u-icon :customStyle="{paddingTop:20+'rpx'}" name="home" :size="40"></u-icon>
					<text class="grid-text">会员中心</text>
				</u-grid-item>
			</u-grid>

		</view>

		<view class="m_top m_list_bg m_card">
			<u-notice-bar direction="column" :text="noticeList"></u-notice-bar>
		</view>

		<view class="m_top m_list_bg m_card">
			<video object-fit="fill" :show-center-play-btn="true" style="width: 100%;height: 300rpx;" 
			:src="video.url" controls></video>
		</view>

		<view class="m_top  m_card" style="margin-bottom: 50rpx;">
			<view class="goods_flex" style="width: 100%;">
				<view class="goods_list" v-for="(item,index) in shopList" :key="index">
					<view class="goods_item" style="" @click="shopDetail(item.id)">
						<view class="list-img">
							<u-image :lazy-load="true" width="100%" height="300rpx"
								:src="item.img" mode="scaleToFill"></u-image>
						</view>
						<view class="goods-title">
							<u-text :lines="1" :text="item.title"></u-text>
						</view>
						<view class="goods-title2 ">
							<u-text :lines="1" color="#888888" text="111"></u-text>
						</view>
						<view class="goods-price">
							<text class="price">￥{{item.money}}</text><text class="del">￥{{item.shi_money}}</text>
							
						</view>
						
					</view>
				</view>


			</view>

		</view>

		<Login ref="loginView" :Show="LoginShow"></Login>
	</view>

</template>

<script>
	import Login from "../../components/login/login.vue";
	export default {
		data() {
			return {
				banner: [


				],
				bannerList: [
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				shopList: [],
				notice: "新系统正在升级",
				video:"",
				LoginShow: false,
                 noticeList:[
					
				 ]
			}
		},
		onLoad(option) {

			var that = this;
			var user = this.common.value('user')
			console.log(option);
			this.getInfo()
			if (user) {
				console.log('已登录');
				console.log(user);
			} else {
				console.log('未登录');
				if (option.key) {
					this.common.value('key', option.key);
				}
			}
		},
		methods: {
			getInfo() {
				var that = this;

				this.common.post('index/getIndex', {}, function(res) {
					console.log(res)
					if (res.code == 200) {

						that.notice = res.data.notice;
						that.noticeList = res.data.noticeList;
						that.bannerList = res.data.bannerList;
						that.banner = res.data.banner;
						that.shopList = res.data.shop;
						that.video = res.data.video;
					}
				}, "加载中")
			},
			isLogin() {
				var user = this.common.value('user');
				if (!user) {
					this.$refs.loginView.setLoginPopupShow();
				}
			},
			shopDetail(id){
				uni.navigateTo({
					url:'../shop/goodsDetail/goodsDetail?id='+id
				})
			}
		},
		components: {
			Login
		}
	}
</script>

<style lang="scss">
	.content {
		height: 100%;
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
