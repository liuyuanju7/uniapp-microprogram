<template>
	<view>
		<view>
			<u-swiper :list="banner" height="380" :indicator="true"></u-swiper>
		</view>
		<view>
			<view class="m_top m_list_bg m_card">
				<view style="font-size: 40rpx;padding: 30rpx;color:#f7403f ;padding-left: 5rpx;padding-bottom: 5rpx;">
					￥<text style="font-size: 50rpx;font-weight: 700;">200</text>

					<view style="font-weight: bold;font-size: 40rpx;">
						<u-text :bold="true" lines="2" size="18" text="我用十年青春,赴你最后之约用十年青春,赴你最后之约用十年青春,赴你最后之约"></u-text>
					</view>

				</view>
				<view style="color: #82848f;margin-top: 10rpx;padding: 10rpx;">
					<u-row customStyle="margin-bottom: 10px">
						<u-col span="4">
							原价:￥22
						</u-col>
						<u-col span="4">
							库存:855
						</u-col>
						<u-col span="4">
							销量:355
						</u-col>
					</u-row>
				</view>
			</view>
		</view>

		<view class="m_top m_list_bg m_card">
			<u-cell-group :border="false">
				<u-cell title="服务品质" value="正品保障"></u-cell>
				<u-cell :border="false" title="快递费用" value="包邮"></u-cell>
			</u-cell-group>
		</view>
		<view class="m_top m_list_bg m_card">

			<u-row customStyle="margin-bottom: 10px">
				<u-col span="4">
					<u-text style="padding-left: 30rpx;" text="购买份数"></u-text>
				</u-col>
				<u-col span="4">

				</u-col>
				<u-col span="4">
					<u-number-box v-model="value" @change="valChange" :min="1" :max="2" integer></u-number-box>
				</u-col>
			</u-row>

		</view>

		<view class="m_top m_list_bg m_card" style="">
			<u-parse :lazyLoad="true" :content="content"></u-parse>
		</view>
		<view style="height: 100rpx;"></view>
		<view class="footer">
			<view style="height: 100rpx;">
				<u-row customStyle="margin-bottom: 10px;height: 100rpx;">
					<u-col span="6">
						<view>
							<u-grid :border="false" @click="click">
								<u-grid-item>
									<u-icon name="home" :size="25">
									</u-icon>
									<text class="grid-text" style="font-size: 30rpx;">
										<u-text text="首页"></u-text>
									</text>
								</u-grid-item>
							</u-grid>
						</view>
					</u-col>
					<u-col span="6">

						<view style="text-align: right;">
							<u-button type="warning" shape="circle" @click="openpay" text="立即购买"></u-button>
						</view>

					</u-col>
				</u-row>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				banner: [
					'https://cdn.uviewui.com/uview/swiper/swiper1.png',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				content: `
									<p>露从今夜白，月是故乡明</p>
									<img src="https://cdn.uviewui.com/uview/swiper/2.jpg" />
								`,
				value:1,
			}
		},
		methods: {
              openpay(){
				  uni.navigateTo({
				  	url:'../pay/pay'
				  })
			  }
		}
	}
</script>

<style lang="scss">

</style>
