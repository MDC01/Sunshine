package cn.edu.zstu.sunshine.utils;

import android.app.Activity;
import android.content.Intent;

import cn.edu.zstu.sunshine.tools.timetable.TimetableAddActivity;

/**
 * Activity启动的工具类
 */

public class IntentUtil {

    public static void startTimetableAddActivity(Activity activity, String courseId) {
        Intent intent = new Intent(activity, TimetableAddActivity.class);
        intent.putExtra("courseId", courseId);
        activity.startActivity(intent);
    }
}
