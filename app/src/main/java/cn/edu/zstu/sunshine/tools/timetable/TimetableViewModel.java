package cn.edu.zstu.sunshine.tools.timetable;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.design.widget.TabLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.greendao.CourseDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DateUtil;

/**
 * 课表的ViewModel类
 */

public class TimetableViewModel {
    //属性必须为public
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    private Context context;
    private ActivityTimetableBinding binding;
    private List<Course> data = new ArrayList<>();

    private CourseDao dao;

    private int day = DateUtil.getDayOfWeek();//星期几，周一就是1

    TimetableViewModel(Context context, ActivityTimetableBinding binding) {
        this.context = context;
        this.binding = binding;

        dao = DaoUtil.getInstance().getSession().getCourseDao();
        init();
    }

    public List<Course> getData() {
        return data;
    }

    public void setData(List<Course> courses) {
        data.clear();
        data.addAll(courses);
    }

    public void setDay(int day) {
        this.day = day;
    }

    void init() {
        loadDataFromLocal();
        loadDataIntoView();
    }

    private void loadDataFromLocal() {
        List<Course> courses = dao
                .queryBuilder()
                .where(
                        CourseDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                        CourseDao.Properties.Day.eq(day)
                )
                .list();
        setData(courses);
    }

    private void loadDataIntoView() {
        showEmptyView.set(data.size() <= 0);
        if (binding.include.recyclerView.getAdapter() != null) {
            binding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 返回当日数据
     *
     * @param view 按钮
     */
    public void onBtnTodayClickListener(View view) {
        backToToday();
    }

    void backToToday() {
        TabLayout.Tab tab = binding.tabLayout.getTabAt(DateUtil.getDayOfWeek() - 1);
        if (tab != null) {
            tab.select();
        }

        setDay(DateUtil.getDayOfWeek());
        init();
    }

}
