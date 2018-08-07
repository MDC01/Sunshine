package cn.edu.zstu.sunshine.tools.user;

import android.content.Context;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityUserAddBinding;
import cn.edu.zstu.sunshine.entity.User;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;

/**
 * 添加学生用户
 */

public class UserAddViewModel {

    public static final String INTENT_ADD_USER_TYPE = "INTENT_ADD_USER_TYPE";

    private Context context;
    private ActivityUserAddBinding binding;

    private boolean isThisClose = false;

    UserAddViewModel(Context context, ActivityUserAddBinding binding) {
        this.context = context;
        this.binding = binding;

        getIntent();
    }

    private void getIntent() {
        isThisClose = ((UserAddActivity) context).getIntent().getBooleanExtra(UserAddViewModel.INTENT_ADD_USER_TYPE, false);
        Logger.e("Intent：" + isThisClose);
    }

    /**
     * @return 返回用户添加成功与否
     */
    boolean isAddUserDone() {
        String id = binding.editCardId.getText().toString().trim();
        String name = binding.editCardHolder.getText().toString().trim();

        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();

        //如果用户列表为空，那么说明这是用户第一次添加用户，把该用户的ID设为默认ID
        if (userDao.queryBuilder().build().list().isEmpty()) {
            AppConfig.setDefaultUserId(id);
        }

        User user = userDao.queryBuilder().where(UserDao.Properties.UserId.eq(id)).build().unique();
        if (user == null) {
            userDao.insert(new User(id, name));
            if (isThisClose) {
                ToastUtil.showShortToast(R.string.toast_user_add_success);
                ((UserAddActivity) context).finish();
                return false;
            }
            return true;
        } else {
            ToastUtil.showShortToast(R.string.toast_user_exist);
            return false;
        }

    }

    public void onBtnBackClick(View view) {
        ((UserAddActivity) context).finish();
    }
}
