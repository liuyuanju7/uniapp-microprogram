<template>
	<view>
		<view class="m_top m_list_bg m_card" style="">
			<u-cell-group :border="false">
				<u-cell :border="false" icon="map" title="设置收货地址" :isLink="true"></u-cell>

			</u-cell-group>
		</view>
		<view class="m_top m_list_bg m_card" style="">
			<u-cell-group :border="false">
				<u-cell :border="false" title="共有商品" value="1"></u-cell>

			</u-cell-group>
			<u-cell-group>

				<u-cell :iconStyle="iconStyle" icon="https://www.uviewui.com/common/logo.png" :border="false" value="内容"
					label="描述信息" title="只是近黄昏"></u-cell>
				<view style="padding-left: 150rpx;font-size: 38rpx;font-weight: 700;color: #F7403F;">￥:2222</view>
			</u-cell-group>
		</view>

		<view class="m_top m_list_bg m_card" style="">
			<u-cell-group :border="false">
				<u-cell :border="false" title="备注信息"></u-cell>

			</u-cell-group>
			<view>
				<u-textarea :count="true" maxlength="50" style="background-color: #F8F8F8;" v-model="beizhu"
					placeholder="请输入内容"></u-textarea>
			</view>

		</view>

		<view class="m_top m_list_bg m_card" style="">
			<u-cell-group :border="false">
				<u-cell :border="false" title="商品总价" value="111"></u-cell>

			</u-cell-group>


		</view>
		<view class="footer">
			<view style="height: 100rpx;">
				<u-row customStyle="margin-bottom: 10px;height: 100rpx;">
					<u-col span="6">
						<view>
							合计: <text style="color: #F7403F;">￥:2255</text>
						</view>
					</u-col>
					<u-col span="6">

						<view style="text-align: right;">
							<u-button type="warning" shape="circle" @click="xzpay()" text="立即支付"></u-button>
						</view>

					</u-col>
				</u-row>
			</view>

		</view>

		<u-popup :show="popShow" round="10" mode="bottom" @close="close()" :closeOnClickOverlay="true">
			<view>
				<view style="padding: 20rpx;">
					<u-text align="center" :bold="true" text="选择支付类型"></u-text>
				</view>
				<u-divider text=""></u-divider>
				<view>
					<view style="padding: 5rpx;">
						<u-text align="center" color="#82848f" text="支付金额"></u-text>
					</view>
					<view style="padding-top: 10rpx;padding-bottom: 50rpx;color:red ;text-align: center;">
						<text>￥</text><text style="font-weight: bold;font-size: 70rpx;">222</text>
						
					</view>

				</view>
				<view>
					<u-cell-group>
						<radio-group @change="radioChange">
							<label class="uni-list-cell uni-list-cell-pd" v-for="(item, index) in items"
								:key="item.value">
								<u-cell :title="item.name">
									<u-icon color="#2979ff" space="100px" style="padding-right:20px ;" slot="icon"
										size="32" name="rmb-circle-fill"></u-icon>
									<radio slot="value" :value="item.value" :checked="index === current" />
								</u-cell>
							</label>
						</radio-group>
					</u-cell-group>

				</view>
				<view style="width: 80%;text-align: center;margin:50rpx auto;">
					<u-button type="primary" text="立即支付"></u-button>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				beizhu: '',
				popShow: false,
				iconStyle: 'width: 120rpx;height: 120rpx',
				icon: 'color:red',
				items: [{
						value: 'USA',
						name: '微信支付',
						checked: 'true'
					},
					{
						value: 'CHN',
						name: '鲵宝支付'
					},
					{
						value: 'BRA',
						name: '鲵豆支付'
					}
				],
				current: 0


			}
		},
		methods: {
			xzpay() {
				this.popShow = true;
			},
			close() {
				this.popShow = false

			}
		}
	}
</script>

<style>
	.u-cell__right-icon-wrap--down {
		-webkit-transform: rotate(1deg) !important;
		transform: rotate(1deg) !important;
	}
</style>
