package com.cong.chenchong.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.cong.chenchong.R;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.adapter.CardAdapter;
import com.cong.chenchong.bean.Girl;
import com.cong.chenchong.widget.swipecard.SwipeFlingAdapterView;

import java.util.ArrayList;

public class SwipeCardActivity extends SlidingActivity {

    private ArrayList<Girl> girls = new ArrayList<>();
    private CardAdapter cardAdapter;
    private SwipeFlingAdapterView viewSwipeCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        findViewById(R.id.iv_dislike).setOnClickListener(v -> dislike());
        findViewById(R.id.iv_like).setOnClickListener(v -> like());

        girls.add(new Girl("小姐姐", 16, photos[0]));
        girls.add(new Girl("小姐姐", 21, photos[1]));
        girls.add(new Girl("小姐姐", 18, photos[2]));
        girls.add(new Girl("小姐姐", 21, photos[3]));
        girls.add(new Girl("小姐姐", 23, photos[4]));
        girls.add(new Girl("小姐姐", 21, photos[5]));
        girls.add(new Girl("小姐姐", 21, photos[6]));
        girls.add(new Girl("小姐姐", 25, photos[7]));
        girls.add(new Girl("小姐姐", 21, photos[8]));
        girls.add(new Girl("小姐姐", 23, photos[9]));
        girls.add(new Girl("小姐姐", 21, photos[10]));
        girls.add(new Girl("小姐姐", 22, photos[11]));
        girls.add(new Girl("小姐姐", 21, photos[12]));
        girls.add(new Girl("小姐姐", 21, photos[13]));
        girls.add(new Girl("小姐姐", 25, photos[14]));
        girls.add(new Girl("小姐姐", 21, photos[15]));
        girls.add(new Girl("小姐姐", 24, photos[16]));
        girls.add(new Girl("小姐姐", 21, photos[17]));
        girls.add(new Girl("小姐姐", 21, photos[18]));
        girls.add(new Girl("小姐姐", 22, photos[19]));
        girls.add(new Girl("小姐姐", 21, photos[20]));
        girls.add(new Girl("小姐姐", 22, photos[21]));
        girls.add(new Girl("小姐姐", 21, photos[22]));
        girls.add(new Girl("小姐姐", 23, photos[23]));
        girls.add(new Girl("小姐姐", 21, photos[24]));
        girls.add(new Girl("小姐姐", 21, photos[25]));
        girls.add(new Girl("小姐姐", 25, photos[26]));
        girls.add(new Girl("小姐姐", 21, photos[27]));
        girls.add(new Girl("小姐姐", 26, photos[28]));
        girls.add(new Girl("小姐姐", 21, photos[29]));
        girls.add(new Girl("小姐姐", 21, photos[30]));
        girls.add(new Girl("小姐姐", 24, photos[31]));
        girls.add(new Girl("小姐姐", 21, photos[32]));
        girls.add(new Girl("小姐姐", 23, photos[33]));
        girls.add(new Girl("小姐姐", 22, photos[34]));
        girls.add(new Girl("小姐姐", 21, photos[35]));
        girls.add(new Girl("小姐姐", 21, photos[36]));
        girls.add(new Girl("小姐姐", 21, photos[37]));
        girls.add(new Girl("小姐姐", 20, photos[38]));
        girls.add(new Girl("小姐姐", 21, photos[39]));
        girls.add(new Girl("小姐姐", 20, photos[40]));
        girls.add(new Girl("小姐姐", 21, photos[41]));
        girls.add(new Girl("小姐姐", 20, photos[42]));
        girls.add(new Girl("小姐姐", 21, photos[43]));
        girls.add(new Girl("小姐姐", 20, photos[44]));
        girls.add(new Girl("小姐姐", 20, photos[45]));
        girls.add(new Girl("小姐姐", 21, photos[46]));
        girls.add(new Girl("小姐姐", 21, photos[47]));
        girls.add(new Girl("小姐姐", 25, photos[48]));
        girls.add(new Girl("小姐姐", 21, photos[49]));
        girls.add(new Girl("小姐姐", 23, photos[50]));
        girls.add(new Girl("小姐姐", 21, photos[51]));
        girls.add(new Girl("小姐姐", 21, photos[52]));
        girls.add(new Girl("小姐姐", 23, photos[53]));
        girls.add(new Girl("小姐姐", 21, photos[54]));
        girls.add(new Girl("小姐姐", 23, photos[55]));
        girls.add(new Girl("小姐姐", 21, photos[56]));

        cardAdapter = new CardAdapter(this, girls);
        viewSwipeCard = (SwipeFlingAdapterView) findViewById(R.id.view_swipe_card);
        viewSwipeCard.setAdapter(cardAdapter);
        viewSwipeCard.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                girls.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(SwipeCardActivity.this, "不喜欢");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(SwipeCardActivity.this, "喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                girls.add(new Girl("循环测试", 18, photos[itemsInAdapter % photos.length - 1]));
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = viewSwipeCard.getSelectedView();
                    view.findViewById(R.id.view_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.view_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        viewSwipeCard.setOnItemClickListener((itemPosition, dataObject) -> makeToast(this, "点击图片：" + itemPosition));
    }

    public void dislike() {
        viewSwipeCard.getTopCardListener().selectLeft();
    }

    public void like() {
        viewSwipeCard.getTopCardListener().selectRight();
    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    public final int[] photos = new int[]{
            R.drawable.ol1,
            R.drawable.ol2,
            R.drawable.ol3,
            R.drawable.ol4,
            R.drawable.ol5,
            R.drawable.ol6,
            R.drawable.ol7,
            R.drawable.ol8,
            R.drawable.ol9,
            R.drawable.ol10,
            R.drawable.ol11,
            R.drawable.ol12,
            R.drawable.ol13,
            R.drawable.ol14,
            R.drawable.ol15,
            R.drawable.ol16,
            R.drawable.ol17,
            R.drawable.ol18,
            R.drawable.ol19,
            R.drawable.ol20,
            R.drawable.ol21,
            R.drawable.ol22,
            R.drawable.ol23,
            R.drawable.ol24,
            R.drawable.ol25,
            R.drawable.ol26,
            R.drawable.ol27,
            R.drawable.ol28,
            R.drawable.ol29,
            R.drawable.ol30,
            R.drawable.ol31,
            R.drawable.ol32,
            R.drawable.ol33,
            R.drawable.ol34,
            R.drawable.ol35,
            R.drawable.ol36,
            R.drawable.ol37,
            R.drawable.ol38,
            R.drawable.ol39,
            R.drawable.ol40,
            R.drawable.ol41,
            R.drawable.ol42,
            R.drawable.ol43,
            R.drawable.ol44,
            R.drawable.ol45,
            R.drawable.ol46,
            R.drawable.ol47,
            R.drawable.ol48,
            R.drawable.ol49,
            R.drawable.ol50,
            R.drawable.ol51,
            R.drawable.ol52,
            R.drawable.ol53,
            R.drawable.ol54,
            R.drawable.ol55,
            R.drawable.ol56,
            R.drawable.ol57,
    };

}
