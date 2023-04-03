<template>
	<view>
		<view class=" m_list_bg ">
			<u-tabs :list="list1" @click="click"></u-tabs>
		</view>
		<view  style="">
			<u-cell-group class="m_top m_list_bg m_card">
			
				<u-cell :iconStyle="iconStyle" icon="https://www.uviewui.com/common/logo.png" :border="false" value="内容"
					label="描述信息" title="只是近黄昏"></u-cell>
				<view style="padding-left: 150rpx;font-size: 38rpx;font-weight: 700;color: #F7403F;">￥:2222</view>
			</u-cell-group>
			
			<u-empty
			        mode="data"
			        icon="http://cdn.uviewui.com/uview/empty/car.png"
			>
			</u-empty>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				 list1: [{
				                    name: '关注',
				                }, {
				                    name: '推荐',
				                }, {
				                    name: '电影'
				                }, {
				                    name: '科技'
				                }, {
				                    name: '音乐'
				                }, {
				                    name: '美食'
				                }, {
				                    name: '文化'
				                }, {
				                    name: '财经'
				                }, {
				                    name: '手工'
				                }]
			}
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
