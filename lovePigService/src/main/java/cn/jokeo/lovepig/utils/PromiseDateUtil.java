package cn.jokeo.lovepig.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.jokeo.lovepig.entity.LovePromise;
import cn.jokeo.lovepig.entity.vo.PromiseListVo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 纪念日计算工具
 *
 * @author joke
 * @version 1.0
 * @date 2021/7/26 22:14
 */
public class PromiseDateUtil {
    /**
     * 计算当前时间距离纪念日还是多少时间
     * 纪念日包括 生日 结婚纪念日 恋爱纪念日 等
     *
     * @param memorialDay
     * @return
     */
    public static long memorialDay(Date memorialDay) {
        //当前年份
        int thisYear = DateUtil.thisYear();
        //当前时间
        DateTime nowDate = DateUtil.beginOfDay(DateUtil.date());
        //当前年份纪念日时间
        DateTime newMemorialDay = DateUtil.beginOfDay(memorialDay).setField(DateField.YEAR, thisYear);
        //比较当前时间和纪念日的时间
        int differenceTime = nowDate.compareTo(newMemorialDay);
        //当前时间大于纪念日时间,说明今年的纪念日已经过去了,推算下一年的即可
        if (differenceTime > 0) {
            newMemorialDay.setField(DateField.YEAR, thisYear + 1);
        }
        //计算当前时间距离下次纪念日还有多少天
        return DateUtil.betweenDay(nowDate, newMemorialDay, false);
    }

    /**
     * 计算当前是第几周年
     *
     * @param memorialDay
     * @return
     */
    public static int annual(Date memorialDay) {
        //直接计算岁数加一
        return DateUtil.ageOfNow(memorialDay) + 1;
    }

    /**
     * 计算当前时间到承诺的时间还有多少天
     *
     * @param promiseDay
     * @return
     */
    public static long promiseDay(Date promiseDay) {
        if (promiseDay == null) {
            return -1L;
        }
        //计算当前时间距离承诺完成还有多少天(今天到承诺的那天)
        return DateUtil.between(new Date(), promiseDay, DateUnit.DAY, false);
        // return DateUtil.betweenDay(new Date(), promiseDay, true);
    }

    /**
     * 计算承诺到现在已经过了多少天了
     *
     * @param promiseDay
     * @return
     */
    public static long promiseLastDay(Date promiseDay) {
        if (promiseDay == null) {
            return -1L;
        }
        //承诺那天到今天
        return DateUtil.betweenDay(promiseDay, new Date(), true);
    }

    /**
     * localDateTime转date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static void memorialDay(PromiseListVo promise) {
        Date promiseTime = promise.getPromiseTime();
        //当前年份
        int thisYear = DateUtil.thisYear();
        //当前时间
        DateTime nowDate = DateUtil.beginOfDay(DateUtil.date());
        //当前年份纪念日时间
        DateTime newMemorialDay = DateUtil.beginOfDay(promiseTime).setField(DateField.YEAR, thisYear);
        //比较当前时间和纪念日的时间
        int differenceTime = nowDate.compareTo(newMemorialDay);
        //当前时间大于纪念日时间,说明今年的纪念日已经过去了,推算下一年的即可
        if (differenceTime > 0) {
            newMemorialDay.setField(DateField.YEAR, thisYear + 1);
        }
        //计算当前时间距离下次纪念日还有多少天
        long daysApart = DateUtil.betweenDay(nowDate, newMemorialDay, false);
        //计算几周年
        long annual = DateUtil.ageOfNow(newMemorialDay) + 1;

        //纪念日(纪念日时间都是以年为单位)
        promise.setTargetTime(newMemorialDay);
        promise.setDaysApart(daysApart);
        //如果当天为纪念日,那纪念日减一
        promise.setAnnual(promise.getDaysApart() == 0 ? annual - 1 : annual);
    }

    public static Date getLastDayOfMonth(Date sDate1) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(sDate1);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    public static void promiseDay(PromiseListVo promise) {
        DateTime promiseTime = DateUtil.beginOfDay(promise.getPromiseTime());
        promiseTime.setMutable(false);
        String promiseRepeat = promise.getPromiseRepeat();

        //==公共参数==
        //当前年份
        int thisYear = DateUtil.thisYear();
        //当前时间

        DateTime nowDate = DateUtil.beginOfDay(DateUtil.date());
        //目标日期,默认目标就是当前时间
        DateTime promiseTargetTime = DateUtil.beginOfDay(DateUtil.date());
        //周期
        long annual = 1;
        //比较当前时间和承诺时间
        int diffTime = nowDate.compareTo(promiseTime);
        //如果当前时间小于承诺时间
        if (diffTime == -1) {
            long daysApart = DateUtil.betweenDay(nowDate, promiseTime, true);
            promise.setTargetTime(promiseTime);
            promise.setDaysApart(daysApart);
            promise.setAnnual(annual);
            return;
        }
        //计算目标日期
        if (promiseRepeat.endsWith("Y")) {
            //当前年份承诺时间
            promiseTargetTime = DateUtil.beginOfDay(promise.getPromiseTime());
            promiseTargetTime.setField(DateField.YEAR, thisYear);
            //比较当前时间和承诺时间
            int differenceTime = nowDate.compareTo(promiseTargetTime);
            //当前时间大于承诺时间,说明今年的承诺时间已经过去了,推算下一年的即可
            if (differenceTime > 0) {
                promiseTargetTime.setField(DateField.YEAR, thisYear + 1);
            }

            //计算几周年
            annual = DateUtil.ageOfNow(promiseTime);
            //如果今天不为纪念日当天 纪念日加1
            if (differenceTime != 0) {
                annual++;
            }
        } else if (promiseRepeat.endsWith("M")) {
            //承诺的是几号
            int promiseDayOfMonth = DateUtil.dayOfMonth(promiseTime);
            //当前是几号
            int nowDayOfMonth = DateUtil.thisDayOfMonth();
            //当前月的最后一天
            int lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            //如果指定的日子大于当前月的最大日子那就给他改成当前月的最大的一天
            if (promiseDayOfMonth > lastDay) {
                promiseDayOfMonth = lastDay;
            }
            promiseTargetTime.setField(DateField.DAY_OF_MONTH, promiseDayOfMonth);
            if (nowDayOfMonth > promiseDayOfMonth) {
                promiseTargetTime.offset(DateField.MONTH, 1);
            }
            annual = DateUtil.betweenMonth(promiseTime, promiseTargetTime, true) + 1;
        } else if (promiseRepeat.endsWith("W")) {
            //当前是星期几
            int thisDayOfWeek = DateUtil.thisDayOfWeek();
            //承诺的是周几
            int promiseTimeWeek = DateUtil.dayOfWeek(promiseTime);
            //如果承诺的星期已经过去了那就在加上7天定为下一周
            if (promiseTimeWeek < thisDayOfWeek) {
                promiseTimeWeek += 7;
            }
            //目标日期减去当前星期
            int offsetDay = promiseTimeWeek - thisDayOfWeek;
            promiseTargetTime.offset(DateField.DAY_OF_YEAR, offsetDay);
            annual = DateUtil.betweenMonth(promiseTime, promiseTargetTime, true) + 1;
        } else if (promiseRepeat.endsWith("O")) {
            //一次的承诺,设置目标时间为承诺时间
            promiseTargetTime = DateUtil.beginOfDay(promise.getPromiseTime());
        }
        //计算当前时间距离下次承诺时间还有多少天
        long daysApart = DateUtil.between(nowDate, promiseTargetTime, DateUnit.DAY, false);
        //计算承诺过去多少天了
        long pastDays = DateUtil.between(promiseTime, nowDate, DateUnit.DAY, false);
        //计算当前时间距离承诺完成还有多少天(今天到承诺的那天)
        promise.setTargetTime(promiseTargetTime);
        promise.setDaysApart(daysApart);
        promise.setPastDays(pastDays);
        //如果当天为纪念日,那纪念日减一
        promise.setAnnual(annual);
    }

    public static void calculateTargetTime(LovePromise promise) {
        DateTime promiseTime = DateUtil.beginOfDay(promise.getPromiseTime());
        promiseTime.setMutable(false);
        String promiseRepeat = promise.getPromiseRepeat();

        //==公共参数==
        //当前年份
        int thisYear = DateUtil.thisYear();
        //当前时间

        DateTime nowDate = DateUtil.beginOfDay(DateUtil.date());
        //目标日期,默认目标就是当前时间
        DateTime promiseTargetTime = DateUtil.beginOfDay(DateUtil.date());

        //比较当前时间和承诺时间
        int diffTime = nowDate.compareTo(promiseTime);
        //如果当前时间小于承诺时间
        if (diffTime == -1) {
            promise.setTargetTime(promiseTime);
        }
        //计算目标日期
        if (promiseRepeat.endsWith("Y")) {
            //当前年份承诺时间
            promiseTargetTime = DateUtil.beginOfDay(promise.getPromiseTime());
            promiseTargetTime.setField(DateField.YEAR, thisYear);
            //比较当前时间和承诺时间
            int differenceTime = nowDate.compareTo(promiseTargetTime);
            //当前时间大于承诺时间,说明今年的承诺时间已经过去了,推算下一年的即可
            if (differenceTime > 0) {
                promiseTargetTime.setField(DateField.YEAR, thisYear + 1);
            }
        } else if (promiseRepeat.endsWith("M")) {
            //承诺的是几号
            int promiseDayOfMonth = DateUtil.dayOfMonth(promiseTime);
            //当前是几号
            int nowDayOfMonth = DateUtil.thisDayOfMonth();
            //当前月的最后一天
            int lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            //如果指定的日子大于当前月的最大日子那就给他改成当前月的最大的一天
            if (promiseDayOfMonth > lastDay) {
                promiseDayOfMonth = lastDay;
            }
            promiseTargetTime.setField(DateField.DAY_OF_MONTH, promiseDayOfMonth);
            if (nowDayOfMonth > promiseDayOfMonth) {
                promiseTargetTime.offset(DateField.MONTH, 1);
            }
        } else if (promiseRepeat.endsWith("W")) {
            //当前是星期几
            int thisDayOfWeek = DateUtil.thisDayOfWeek();
            //承诺的是周几
            int promiseTimeWeek = DateUtil.dayOfWeek(promiseTime);
            //如果承诺的星期已经过去了那就在加上7天定为下一周
            if (promiseTimeWeek < thisDayOfWeek) {
                promiseTimeWeek += 7;
            }
            //目标日期减去当前星期
            int offsetDay = promiseTimeWeek - thisDayOfWeek;
            promiseTargetTime.offset(DateField.DAY_OF_YEAR, offsetDay);
        } else if (promiseRepeat.endsWith("O")) {
            //一次的承诺,设置目标时间为承诺时间
            promiseTargetTime = DateUtil.beginOfDay(promise.getPromiseTime());
        }
        promise.setTargetTime(promiseTargetTime);
    }
}
