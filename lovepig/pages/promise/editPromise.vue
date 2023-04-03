<template>
	<view class="p-warp">
		<u-toast ref="uToast" />
		<loading ref="lovdLoading"></loading>
		<view class="p-main">
			<view class="p-title">许下承诺那一刻,我深知我有多么爱你!</view>
			<u-form :model="promiseData" ref="uForm" :label-width="100" label-align="center" :error-type="errorType">
				<u-form-item label="时间" v-if="!changePromise">
					<u-input v-model="promiseData.promiseTime" @click="showDateView = true" :disabled="true"
						placeholder="承诺完成时间" />
				</u-form-item>
				<u-form-item label="周期" v-if="!changePromise">
					<u-input v-model="promiseData.repeatInfo" type="select" @click="showRepeatView = true"
						default-value="3" />
				</u-form-item>
				<u-form-item label="主题" prop="promiseName" v-if="!changePromise">
					<u-input v-model="promiseData.promiseName" :clearable="false" placeholder="承诺主题" />
				</u-form-item>
				<u-form-item label="提醒"><u-input v-model="promiseData.remindInfo" type="select"
						@click="showRemindView = true" /></u-form-item>
				<u-form-item label="备注">
					<u-input v-model="promiseData.promiseRemarks" :clearable="false" class="promise-remarks"
						maxlength="150" type="textarea" placeholder="备注(可以不写)" />
				</u-form-item>
			</u-form>

			<view class="base_button" @click="submit" v-if="!changePromise">承诺</view>
			<view class="base_button" @click="changeSubmit" v-if="changePromise">确认更改</view>

			<u-calendar active-bg-color="#ff6773" v-model="showDateView" max-date="2099-12-31" :min-date="nowDate"
				mode="date" toolTip="请选择承诺时间" @change="changePromiseDate"></u-calendar>
			<u-select v-model="showRepeatView" :list="repeatList" @confirm="changeRepeat"></u-select>
			<u-select v-model="showRemindView" :list="remindList" @confirm="changeRemind"></u-select>
		</view>
		<view class="p-tip">
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart" color="#ff6773"></u-icon>
				<view>承诺的一定要做到哦!</view>
			</view>
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart-fill" color="#ff6773"></u-icon>
				<view>承诺完成可以打卡记录哦!</view>
			</view>
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart" color="#ff6773"></u-icon>
				<view>承诺的一但创建不可以后悔哦!(不可以改的)</view>
			</view>
			<view class="p-tip-item">
				<u-icon class="item-icon" name="heart-fill" color="#ff6773"></u-icon>
				<view>虽然会提醒你完成承诺但是更希望你主动去提前完成承诺哦!</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState
	} from 'vuex';
	export default {
		data() {
			return {
				showLoading: false,
				nowDate: '',
				errorType: ['toast'],
				showDateView: false,
				showRepeatView: false,
				showRemindView: false,
				changePromise: false,
				promiseData: {
					loveId: '',
					promiseName: '',
					promiseType: 1,
					promiseRemarks: '',
					promiseTime: '',
					promiseCategory: '',
					promiseRepeat: '1O',
					promiseRemind: '1',
					repeatInfo: '就一次',
					remindInfo: '提前一天',
					promiseBackground: ''
				},
				rules: {
					promiseName: [{
						required: true,
						message: '请输入承诺主题',
						// 可以单个或者同时写两个触发验证方式
						trigger: ['change', 'blur']
					}]
				},
				repeatList: this.$pig.promise.repeatList,
				remindList: this.$pig.promise.remindList
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
		onReady() {
			this.$refs.uForm.setRules(this.rules);
			var now = new Date().getTime();
			this.nowDate = this.$u.timeFormat(now, 'yyyy-mm-dd');
		},
		methods: {
			async editInit(promiseId) {
				let that = this;
				if (promiseId) {
					this.showLoading = true;
					that.changePromise = true;
					uni.setNavigationBarTitle({
						title: '动承诺'
					});
					let pd = await that.$u.api.promiseDetail({
						promiseId: promiseId,
						loveId: that.getLoveStartId
					});
					that.promiseData.promiseId = pd.promiseId;
					that.promiseData.promiseName = pd.promiseName;
					that.promiseData.promiseRemarks = pd.promiseRemarks;
					that.promiseData.promiseTime = that.$u.timeFormat(pd.promiseTime, 'yyyy-mm-dd');
					that.promiseData.promiseCategory = pd.promiseCategory;
					that.promiseData.promiseRepeat = pd.promiseRepeat;
					that.promiseData.promiseRemind = pd.promiseRemind;
					that.promiseData.repeatInfo = that.repeatList.find(item => item.value === pd.promiseRepeat).label;
					that.promiseData.remindInfo = that.remindList.find(item => item.value === pd.promiseRemind).label;
				}
				this.$nextTick(() => {
					console.info(this.$refs.lovdLoading);
					this.$refs.lovdLoading.hidden();
				})
			},
			submitBefore() {
				if (this.promiseData.promiseTime === '今天') {
					this.promiseData.promiseTime = this.nowDate;
				}
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
			changeSubmit() {
				this.submitBefore();
				this.$u.api.editPromise(this.promiseData).then(res => {
					// console.info(res);
					const eventChannel = this.getOpenerEventChannel();
					eventChannel.emit('updatePromise', {
						updatePromiseRes: true
					});
					this.$refs.uToast.show({
						title: '修改成功啦~',
						type: 'primary',
						back: true
					});
				});
			},
			cretePromise() {
				this.submitBefore();
				this.$u.api.savePromise(this.promiseData).then(res => {
					// console.info(res);
					this.$refs.uToast.show({
						title: '许诺成功啦~',
						type: 'primary'
					});
					//加载动画过度
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/promise/index'
						});
					}, 1500);
				});
			},
			changePromiseDate(info) {
				// console.info(info);
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

	.p-warp>view {
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