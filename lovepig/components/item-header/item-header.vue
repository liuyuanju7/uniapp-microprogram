<template>
	<view>
		<navigator :url="navigatorUrl" hover-class="none">
			<view
				class="item-header"
				:style="{
					margin: '0 ' + margin + 'rpx'
				}"
			>
				<view class="header-left">
					<!-- <u-icon name="heart-fill" color="#ff3757"></u-icon> -->
					<view class="item-title">{{ title }}</view>
				</view>

				<view class="item-more" @click="onClick" v-if="showMoreBtn">{{ moreBtnTitle }}<u-icon name="arrow-right"></u-icon></view>
			</view>
		</navigator>
	</view>
</template>

<script>
export default {
	name: 'ItemHeader',
	props: {
		title: {
			type: String,
			default: ''
		}, //标题
		showMoreBtn: {
			type: Boolean,
			default: true
		}, //是否显示更多按钮
		moreBtnTitle: {
			type: String,
			default: '更多'
		}, //是否显示更多按钮
		navigatorUrl: {
			type: String,
			default: ''
		},
		// 组件距离上一个元素之间的距离
		margin: {
			type: [String, Number],
			default: 0
		}
	},
	methods: {
		onClick() {
			this.$emit('click');
		}
	}
};
</script>

<style lang="scss">
.item-header {
	// background-color: #19BE6B;
	// border-top-left-radius: $love-border-radius;
	// border-top-right-radius: $love-border-radius;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-between;
	// margin:0 $love-margin;
	padding-bottom: $love-padding;
	padding-top: $love-padding;
}

.header-left {
	display: flex;
	flex-direction: row;
	align-items: center;
}

.item-title {
	// margin:0 6rpx;
	font-size: 32rpx;
	font-weight: bold;
	// font-style:oblique;
}

.item-more {
	font-size: 25rpx;
	color: $u-tips-color;
	// border-radius: 20rpx;
	padding: 0 $love-padding;
	// padding-right: $love-padding;
	// background-color: #f3f4f6;
	// border-radius: $love-border-radius;
}
</style>
