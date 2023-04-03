<template>
  <view class="mask" v-if="showCalendar" @click.stop="showCalendar = false">
    <view class="calendar" @tap.stop>
      <view class="content" @tap.stop>
        <view class="title">
          <view class="sure" @click="cancel">取消</view>
          <view class="left">{{
            type === "solar" ? dateInfo.solarDate : dateInfo.lunarDate
          }}</view>
          <view class="sure" @click="confirmFun">确定</view>
        </view>
        <picker-view
          :indicator-style="indicatorStyle"
          :value="selectValue"
          @change="bindChange"
        >
          <picker-view-column>
            <view class="item" v-for="(item, index) in years" :key="index"
              >{{ item }}年</view
            >
          </picker-view-column>
          <picker-view-column>
            <view class="item" v-for="(item, index) in months" :key="index">{{
              item
            }}</view>
          </picker-view-column>
          <picker-view-column>
            <view class="item" v-for="(item, index) in days" :key="index">{{
              item
            }}</view>
          </picker-view-column>
        </picker-view>
        <view class="bottom" v-if="isSwitch">
          <view class="switch">
            <view
              class="left"
              :class="{ active: type === 'solar' }"
              @click="switchBtn('solar')"
              >公历</view
            >
            <view
              class="right"
              :class="{ active: type === 'lunar' }"
              @click="switchBtn('lunar')"
              >农历</view
            >
            <view class="bg" :class="type"></view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { calendar } from "./calendar";
export default {
  name: "calendar",
  data() {
    return {
      years: [], //年
      months: [], //月
      days: [], //日
      selectValue: [], //默认 [100,0,0] 2000-01-01
      type: "", //lunar农历，solar公历
      dateInfo: {}, // 完整日期数据
      indicatorStyle: `height: ${Math.round(
        uni.getSystemInfoSync().screenWidth / (750 / 100)
      )}px;`,
    };
  },
  watch: {
    selectValue(newV, oldV) {
      this.getYear();
      this.getMonth();
      this.getDay();
    },
  },
  props: {
    showCalendar: {
      type: Boolean,
      default: () => false,
    },
    defaultValue: {
      type: Array,
      default: () => [100, 0, 0],
    },
    defaultType: {
      type: String,
      default: () => "solar",
    },
    minYear: {
      type: Number,
      default: () => 1900,
    },
    maxYear: {
      type: Number,
      default: () => 2100,
    },
    isSwitch: {
      type: Boolean,
      default: true,
    },
  },
  components: {},
  created() {},
  mounted() {
    this.type = this.defaultType;
    const selectValue = this.defaultValue;
    this.setPicker(selectValue, this.type);
  },
  methods: {
    // 支持父组件调用设置初值
    async setPicker(selectValue, type, isLeap) {
      this.selectValue = (await selectValue) || this.defaultValue;
      this.type = type;

      let val = this.selectValue;
      let year = val[0] + this.minYear;
      let month = val[1] + 1;
      let day = val[2] + 1;

      if (this.type === "solar") {
        this.setSolarDate(year, month, day);
      } else {
        // this.setLunarDate(year, month, day);
        const dt = calendar.lunar2solar(year, month, day, isLeap);
        this.setLunarDate(dt.cYear, dt.cMonth, dt.cDay);
      }
    },
    getYear() {
      this.years = [];
      if (this.type === "solar") {
        for (let i = this.minYear; i <= this.maxYear; i++) {
          // 年
          this.years.push(i);
        }
        this.getMonth();
        return;
      }

      if (this.type === "lunar") {
        for (let i = this.minYear; i <= this.maxYear; i++) {
          // 年
          this.years.push(calendar.toChinaYear(i));
        }
        return;
      }
    },
    getMonth() {
      this.months = [];
      if (this.type === "solar") {
        for (let i = 1; i <= 12; i++) {
          //月
          this.months.push(i + "月");
        }
        return;
      }

      if (this.type === "lunar") {
        const year = this.selectValue[0] + this.minYear;
        const leap_month = calendar.leapMonth(year);
        //返回农历 闰月没有就返回0
        for (let i = 1; i <= 12; i++) {
          this.months.push(calendar.toChinaMonth(i));
          if (i == leap_month) {
            this.months.push("闰" + calendar.toChinaMonth(i));
          }
        }
        return;
      }
    },
    getDay() {
      this.days = [];
      let year = this.selectValue[0] + this.minYear;
      let month = this.selectValue[1] + 1;
      if (this.type === "solar") {
        for (let i = 1; i <= calendar.solarDays(year, month); i++) {
          //日
          this.days.push(i + "日");
        }
        return;
      }

      if (this.type === "lunar") {
        // 农历返回天数
        // **leapMonth 返回闰月 calendar.leapMonth(year)
        // **monthDays 返回非闰月的天数
        // **leapDays 返回闰月的天数
        if (
          calendar.leapMonth(year) != 0 &&
          (calendar.leapMonth(year) == month ||
            month - 1 == calendar.leapMonth(year))
        ) {
          for (
            let i = 1;
            i <= calendar.leapDays(year, calendar.leapMonth(year));
            i++
          ) {
            this.days.push(calendar.toChinaDay(i));
          }
        } else {
          let lunarMonth = "";
          if (calendar.leapMonth(year)) {
            lunarMonth = month < calendar.leapMonth(year) ? month : month - 1;
          } else {
            lunarMonth = month;
          }
          for (let i = 1; i <= calendar.monthDays(year, lunarMonth); i++) {
            this.days.push(calendar.toChinaDay(i));
          }
        }
        return;
      }
    },
    async bindChange(e) {
      let val = e.detail.value;
      this.selectValue = await val;
      const year = val[0] + this.minYear;
      const month = val[1] + 1;
      const day = val[2] + 1;
      if (this.type === "solar") {
        this.setSolarDate(year, month, day);
      } else {
        const dt = calendar.lunar2solar(year, month, day, this.dateInfo.isLeap);
        this.setLunarDate(dt.cYear, dt.cMonth, dt.cDay);
      }
    },
    setSolarDate(y, m, d) {
      const dt = calendar.solar2lunar(y, m, d);
      //设置公历数据
      let dateInfo = {
        type: "solar",
        Animal: dt.Animal,
        astro: dt.astro,
        ncWeek: dt.ncWeek,
        nWeek: dt.nWeek,
        gzYear: dt.gzYear,
        gzMonth: dt.gzMonth,
        gzDay: dt.gzDay,
        IMonthCn: dt.IMonthCn,
        IDayCn: dt.IDayCn,
        festival: dt.festival,
        year: dt.cYear,
        month: dt.cMonth,
        day: dt.cDay,
        solarDate: dt.date,
        lDay: dt.lDay,
        lMonth: dt.lMonth,
        lYear: dt.lYear,
        lunarDate: `${dt.gzYear}年${dt.IMonthCn}${dt.IDayCn}(${dt.date})`,
        lunarDateNumber: dt.lunarDate,
        Term: dt.Term,
        isTerm: dt.isTerm,
        isLeap: dt.isLeap,
        isToday: dt.isToday,
      };
      this.dateInfo = dateInfo;
    },
    setLunarDate(y, m, d) {
      const dt = calendar.solar2lunar(y, m, d);
      let dateInfo = {
        type: "lunar",
        Animal: dt.Animal,
        astro: dt.astro,
        ncWeek: dt.ncWeek,
        nWeek: dt.nWeek,
        gzYear: dt.gzYear,
        gzMonth: dt.gzMonth,
        gzDay: dt.gzDay,
        IMonthCn: dt.IMonthCn,
        IDayCn: dt.IDayCn,
        festival: dt.festival,
        year: dt.cYear,
        month: dt.cMonth,
        day: dt.cDay,
        solarDate: dt.date,
        lDay: dt.lDay,
        lMonth: dt.lMonth,
        lYear: dt.lYear,
        lunarDate: `${dt.gzYear}年${dt.IMonthCn}${dt.IDayCn}(${dt.date})`,
        lunarDateNumber: dt.lunarDate,
        Term: dt.Term,
        isTerm: dt.isTerm,
        isLeap: dt.isLeap,
        isToday: dt.isToday,
      };
      this.dateInfo = dateInfo;
    },
    cancel() {
      this.$emit("cancel");
    },
    confirmFun() {
      // 确定
      let dateInfo = this.dateInfo;
      this.$emit("confirm", dateInfo);
    },

    async switchBtn(type) {
      //切换
      this.type = await type;
      if (type === "solar") {
        const pickerYear = this.dateInfo.year - this.minYear;
        const pickerMonth = this.dateInfo.month - 1;
        const pickerDay = this.dateInfo.day - 1;
        this.selectValue = [pickerYear, pickerMonth, pickerDay];

        const solarYear = this.dateInfo.year;
        const solarMonth = this.dateInfo.month;
        const solarDay = this.dateInfo.day;
        this.setSolarDate(solarYear, solarMonth, solarDay);
      } else {
        const lunarDateArr = this.dateInfo.lunarDateNumber.split("-");
        const pickerYear = Number(lunarDateArr[0]) - this.minYear;
        const pickerMonth = Number(lunarDateArr[1]) - 1;
        const pickerDay = Number(lunarDateArr[2]) - 1;
        this.selectValue = [pickerYear, pickerMonth, pickerDay];

        const solarYear = this.dateInfo.year;
        const solarMonth = this.dateInfo.month;
        const solarDay = this.dateInfo.day;
        this.setLunarDate(solarYear, solarMonth, solarDay);
      }
    },
  },
};
</script>

<style lang="scss" scope>
.mask {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  width: 750rpx;
  box-sizing: border-box;
  background: rgba(0, 0, 0, 0.3);
  z-index: 9999999;
}
.u-mask-zoom {
  transform: scale(1, 1);
}
.calendar {
  display: flex;
  align-items: flex-end;
  justify-content: center;
  height: 100%;
  .content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    // height: 100%;
    background-color: #fff;
    border-top-left-radius: 24rpx;
    border-top-right-radius: 24rpx;
    .title {
      display: flex;
      flex-direction: row;
      align-items: center;
      height: 86rpx;
      border-bottom: 2rpx solid #e6e6e6;
      width: 100%;
      justify-content: space-between;
      // border: 1rpx solid red;
      background-color: #ffffff;
      border-top-left-radius: 24rpx;
      border-top-right-radius: 24rpx;
      /* #ifdef MP-WEIXIN */
      margin-top: 0;
      /* #endif */
      .left {
        color: #666666;
        font-size: 30rpx;
        padding-left: 30rpx;
      }
      .sure {
        color: #eb344a;
        font-size: 30rpx;
        padding: 0 30rpx;
      }
    }
    picker-view {
      width: 100%;
      height: 650rpx;
      margin-top: 20rpx;
      /deep/.item {
        display: flex;
        align-items: center;
        justify-content: center;
        color: #333333;
        font-size: 32rpx;
        font-weight: bold;
      }
      .uni-picker-view-wrapper {
        uni-picker-view-column {
          display: flex;
          align-items: center;
          justify-content: center;
          color: #333333;
          font-size: 32rpx;
          font-weight: bold;
          .uni-picker-view-group {
            .uni-picker-view-content {
              text-align: center;
              line-height: 110rpx;
              .item {
                display: flex;
                align-items: center;
                justify-content: center;
                color: #333333;
                font-size: 32rpx;
                font-weight: bold;
              }
            }
          }
        }
      }
    }
    .bottom {
      height: 110rpx;
      width: calc(100% - 31rpx);
      display: flex;
      align-items: center;
      justify-content: flex-end;
      padding-right: 31rpx;
      background-color: #ffffff;
      .switch {
        background-color: #e6e6e6;
        width: 180rpx;
        height: 70rpx;
        display: flex;
        flex-direction: row;
        border-radius: 35rpx;
        justify-content: space-around;
        align-items: center;
        position: relative;
        .left {
          position: absolute;
          z-index: 1;
          left: 25%;
          font-size: 28rpx;
          transform: translateX(-50%);
        }
        .right {
          color: #333333;
          font-size: 28rpx;
          position: absolute;
          right: 0;
          transform: translateX(-25%);
          color: #333333;
          z-index: 1;
        }
        .bg {
          background-color: #eb344a;
          color: #333333;
          height: 70rpx;
          border-radius: 40rpx;
          width: 90rpx;
          position: absolute;
          // right: 0;
          top: 0;
          z-index: 0;
        }
        .active {
          color: #ffffff;
        }
        .lunar {
          left: 90rpx;
          animation: switchsolar 500ms;
        }
        @keyframes switchsolar {
          0% {
            left: 0;
          }
          100% {
            left: 90rpx;
          }
        }
        .solar {
          left: 0;
          animation: switchlunar 500ms;
        }
        @keyframes switchlunar {
          0% {
            left: 90rpx;
          }
          100% {
            left: 0;
          }
        }
      }
    }
  }
}
</style>
