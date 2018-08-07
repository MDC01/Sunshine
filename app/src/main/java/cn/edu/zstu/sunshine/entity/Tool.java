package cn.edu.zstu.sunshine.entity;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * 主页面工具类的实体
 */

public class Tool {
    private String name;
    private int icon;

    public Tool(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @BindingAdapter({"iconRes"})
    public static void loadImage(ImageView iconView, int iconRes) {
//        Picasso.with(iconView.getContext())
//                .load(iconRes)
//                .into(iconView);
        //用Picasso真是大材小用了
        iconView.setImageResource(iconRes);
    }
}
