<template>
	<view class="p-warp">
		<u-toast ref="uToast" />
		<view class="p-main">
			<view class="p-title">宝贵</view>
			<u-form :model="promiseData" ref="uForm" :label-width="100" label-align="center" :error-type="errorType">
				<u-form-item label="主题" prop="promiseName"><u-input v-model="promiseData.promiseName" :clearable="false" placeholder="纪念日主题" /></u-form-item>
				<u-form-item label="时间"><u-input v-model="promiseData.promiseTime" @click="showDateView = true" :disabled="true" placeholder="纪念日时间" /></u-form-item>
				<u-form-item label="提醒"><u-input v-model="promiseData.remindInfo" type="select" @click="showRemindView = true" /></u-form-item>
				<u-form-item label="备注">
					<u-input v-model="promiseData.promiseRemarks" :clearable="false" maxlength="150" type="textarea" placeholder="备注(可以不写)" />
				</u-form-item>
			</u-form>
			<view class="base_button" @click="submit">确定</view>
			<u-calendar active-bg-color="#ff6773" v-model="showDateView" mode="date" toolTip="请选择纪念日时间" @change="changePromiseDate"></u-calendar>
			<u-select v-model="showRemindView" :list="remindList" @confirm="changeRemind"></u-select>
		</view>

		<view class="p-tip">
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart" color="#ff6773"></u-icon>
				<view class="">纪念日每年都会提醒你哦!</view>
			</view>

			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart-fill" color="#ff6773"></u-icon>
				<view class="">纪念日男孩子一定要提前准备礼物呐!</view>
			</view>
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart" color="#ff6773"></u-icon>
				<view class="">每次纪念日都可以打卡留念哦!</view>
			</view>
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart-fill" color="#ff6773"></u-icon>
				<view class="">纪念日都是以年为单位的,要是你想每周每月都是纪念日那建议定纪念日哦!</view>
			</view>
		</view>
		<loading ref="lovdLoading"></loading>
	</view>
</template>

<script>
import { mapGetters, mapState } from 'vuex';
export default {
	data() {
		return {
			changePromise: false,
			errorType: ['toast'],
			showDateView: false,
			showRepeatView: false,
			showRemindView: false,
			promiseData: {
				loveId: '',
				promiseName: '',
				promiseType: 2,
				promiseRemarks: '',
				promiseTime: '今天',
				promiseCategory: '',
				promiseRepeat: '1Y',
				promiseRemind: '1',
				repeatInfo: '每年',
				remindInfo: '提前一天'
			},
			rules: {
				promiseName: [
					{
						required: true,
						message: '请输入纪念日主题',
						trigger: ['change', 'blur']
					}
				]
			},
			remindList: [
				{
					value: '-1',
					label: '不提醒'
				},
				{
					value: '0',
					label: '当天'
				},
				{
					value: '1',
					label: '提前一天'
				},
				{
					value: '3',
					label: '提前三天'
				},
				{
					value: '7',
					label: '提前一周'
				}
			]
		};
	},
	computed: {
		...mapGetters(['getLoveStartId', 'getIsLogin']),
		...mapState(['loginUser'])
	},
	onLoad(e) {
		this.promiseData.loveId = this.getLoveStartId;
		this.editInit(e.promiseId);
	},
	// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
	onReady() {
		this.$refs.uForm.setRules(this.rules);
		var now = new Date().getTime();
		this.nowDate = this.$u.timeFormat(now, 'yyyy-mm-dd');
	},
	methods: {
		async editInit(promiseId) {
			let that = this;
			if (promiseId) {
				that.changePromise = true;
				let pd = await that.$u.api.promiseDetail({
					promiseId: promiseId,
					loveId: that.getLoveStartId
				});
				that.promiseData.promiseId = pd.promiseId;
				that.promiseData.promiseName = pd.promiseName;
				that.promiseData.promiseRemarks = pd.promiseRemarks;
				that.promiseData.promiseTime = that.$u.timeFormat(pd.promiseTime, 'yyyy-mm-dd');
				that.promiseData.promiseCategory = pd.promiseCategory;
				that.promiseData.promiseRemind = pd.promiseRemind;
				that.promiseData.remindInfo = that.remindList.find(item => item.value === pd.promiseRemind).label;
			}
			that.$refs.lovdLoading.hidden();
		},
		submit() {
			this.$refs.uForm.validate(valid => {
				if (valid) {
					console.log('验证通过');
					this.cretePromise();
				} else {
					console.log('验证失败');
				}
			});
		},
		cretePromise() {
			if (this.promiseData.promiseTime === '今天') {
				this.promiseData.promiseTime = this.nowDate;
			}
			if (this.changePromise) {
				this.$u.api.editPromise(this.promiseData).then(res => {
					// console.info(res);
					const eventChannel = this.getOpenerEventChannel();
					eventChannel.emit('updatePromise', { updatePromiseRes: true });
					this.$refs.uToast.show({
						title: '修改成功啦~',
						type: 'primary',
						back: true
					});
				});
			} else {
				this.$u.api.savePromise(this.promiseData).then(res => {
					console.info(res);
					this.$refs.uToast.show({
						title: '添加纪念日成功啦~',
						type: 'primary',
						back: true
					});
					//加载动画过度
					// setTimeout(() => {
					// 	uni.redirectTo({
					// 		url: '/pages/anniversary/index'
					// 	});
					// }, 1500);
				});
			}
		},
		changePromiseDate(info) {
			console.info(info);
			this.promiseData.promiseTime = info.result;
		},
		changeRepeat(info) {
			this.promiseData.promiseRepeat = info[0].value;
			this.promiseData.repeatInfo = info[0].label;
		},
		changeRemind(info) {
			this.promiseData.promiseRemind = info[0].value;
			this.promiseData.remindInfo = info[0].label;
		}
	}
};
</script>

<style lang="scss">
page {
	background: $love-color;
}

.p-warp {
	margin: $love-view-margin;
}

.p-warp > view {
	margin-bottom: $love-view-margin;
}

.p-main {
	border-radius: 50rpx;
	background-color: white;
	min-height: 500rpx;
	padding: $love-padding;
}

.p-title {
	text-align: center;
	font-size: 20rpx;
	font-weight: blod;
	padding: $love-padding;
	color: $u-tips-color;
}

.submit-btn {
	width: 80%;
	height: 100rpx;
	text-align: center;
	line-height: 100rpx;
	color: white;
	font-size: 32rpx;
	background: linear-gradient(to right, #ed4264, #ffedbc);
	border-radius: 50rpx;
	margin: $love-view-margin auto;
}

.p-tip {
	background-color: white;
	border-radius: 50rpx;
	padding: 40rpx $love-padding;
	color: $u-content-color;

	.p-tip-item {
		display: flex;
		margin-bottom: $love-view-margin;

		.item-icon {
			margin-right: $love-margin;
		}
	}
}
</style>
