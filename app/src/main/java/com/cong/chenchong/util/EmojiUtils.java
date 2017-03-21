
package com.cong.chenchong.util;

import java.util.ArrayList;
import java.util.List;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.ViewPageAdapter;
import com.cong.chenchong.helper.EmotionPlugin;
import com.cong.chenchong.helper.PageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class EmojiUtils {
    public static class Emoji {
        public int resId;

        public String text;

        public Emoji(int resId, String text) {
            this.resId = resId;
            this.text = text;
        }
    }

    /** 每一页表情的个数 */
    private int EMOJI_COUNT_PER_PAGE = 20;

    private final int DEL_INDEX = -1;

    private static EmojiUtils mEmojiUtils;

    /**
     * 不可显示的表情
     */
    public static final int[] NO_EMOTION_CODES = {
        0x1f641, 0x1f642, 0x1f643, 0x1f644
    };

    public static final int[] EMOTION_CODES = {
        0x1F601, 0x1F614, 0x1F60A, 0x1F61C, 0x1F633, 0x1F444, 0x1F631, 0x1F60C, 0x1F60D, 0x1F623, 0x1F613, 0x1F60F, 0x1F62D, 0x1F604, 0x1F612, 0x1F616, 0x1F632, 0x1F609, 0x1F618, 0x1F61D, 0x1F602,
        0x1F621, 0x1F623, 0x1F622, 0x1F637, 0x1F62A, 0x1F630, 0x1F628, 0x1F61E, 0x1F603, 0x1F61A, 0x1F47F, 0x263A, 0x1F494, 0x2764, 0x1F44D, 0x1F44F, 0x1F4AA
    };
    /*
     * private static final int UNICODE_EMOJI_START = 0x1f600; private static
     * final int UNICODE_EMOJI_END = 0x1f650;
     */

    /** 所有表情 */
    private List<Emoji> emojis = new ArrayList<Emoji>();

    /** 表情分页的结果集合 */
    public List<List<Emoji>> emojiLists = new ArrayList<List<Emoji>>();

    /*
     * private boolean isValidEmoji(int codePoint){ boolean ret = true; for(int
     * i:NO_EMOTION_CODES){ if (i == codePoint){ return false; } } return ret; }
     */

    private EmojiUtils() {
        try {
            int emojiCount = EMOTION_CODES.length;
            for (int i = 0; i < emojiCount; i++) {

                /*
                 * if (!isValidEmoji(i)){ continue; }
                 */
                char[] aa = Character.toChars(EMOTION_CODES[i]);
                String text = String.valueOf(aa);
                emojis.add(new Emoji(i, text));
            }
            int pageCount = (int) Math.ceil(emojiCount * 1.0 / (EMOJI_COUNT_PER_PAGE));
            for (int i = 0; i < pageCount; i++) {
                emojiLists.add(getData(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EmojiUtils getInstace() {
        if (mEmojiUtils == null) {
            mEmojiUtils = new EmojiUtils();
        }
        return mEmojiUtils;
    }

    /**
     * 获取分页数据
     *
     * @param page
     * @return
     */
    private List<Emoji> getData(int page) {
        int startIndex = page * EMOJI_COUNT_PER_PAGE;
        int endIndex = startIndex + EMOJI_COUNT_PER_PAGE;

        if (endIndex > emojis.size()) {
            endIndex = emojis.size();
        }
        List<Emoji> list = new ArrayList<Emoji>();
        list.addAll(emojis.subList(startIndex, endIndex));
        // if (list.size() < EMOJI_COUNT_PER_PAGE) {
        // for (int i = list.size(); i < EMOJI_COUNT_PER_PAGE; i++) {
        // list.add(0);
        // }
        // }
        // if (list.size() == EMOJI_COUNT_PER_PAGE) {
        list.add(new Emoji(DEL_INDEX, ""));
        // }
        return list;
    }

    private static class EmojiAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private List<Emoji> mEmojiList = null;

        public EmojiAdapter(Context c, List<Emoji> emojiList) {
            super();
            mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mEmojiList = emojiList;
        }

        @Override
        public int getCount() {
            return mEmojiList != null ? mEmojiList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mEmojiList != null ? mEmojiList.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = mInflater.inflate(R.layout.smiley_faces_item, null);
            } else {
                view = convertView;
            }

            Emoji emoji = (Emoji) getItem(position);
            TextView tv_face = (TextView) view.findViewById(R.id.sfi_tv_face);
            ImageView iv_face = (ImageView) view.findViewById(R.id.sfi_iv_face);
            if (emoji.resId == -1) {
                iv_face.setVisibility(View.VISIBLE);
                tv_face.setText("");
                tv_face.setVisibility(View.INVISIBLE);
            } else {
                iv_face.setVisibility(View.GONE);
                tv_face.setText(emoji.text);
                tv_face.setVisibility(View.VISIBLE);
            }
            // iv_face.setImageResource(emoji.resId);

            view.setTag(emoji);
            return view;

        }

    }

    private static List<View> getEmojiListView(Context context, final EmotionPlugin.EmotionSelectedHandler emotionSelectedHandler) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        List<View> pageViews = new ArrayList<View>();
        // 左侧添加空页
        View nullView1 = new View(context);
        // 设置透明背景
        nullView1.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);

        // 中间添加表情页
        for (int i = 0; i < EmojiUtils.getInstace().emojiLists.size(); i++) {
            GridView view = (GridView) inflater.inflate(R.layout.emotion_gridview, null);
            EmojiAdapter adapter = new EmojiAdapter(context, EmojiUtils.getInstace().emojiLists.get(i));
            view.setAdapter(adapter);
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> viewgroup, View view, int position, long id) {
                    if (emotionSelectedHandler != null) {
                        Emoji emoji = (Emoji) view.getTag();
                        String emotion = TextUtils.isEmpty(emoji.text) ? null : emoji.text;
                        emotionSelectedHandler.onEmotionSelected(EmotionPlugin.TYPE_TEXT, emotion, null);
                    }
                }

            });
            pageViews.add(view);
        }

        // 右侧添加空页面
        View nullView2 = new View(context);
        // 设置透明背景
        nullView2.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView2);

        return pageViews;
    }

    public static void createEmojiLayout(Context c, final ViewPager vp, final ViewFlipper flipper, final EmotionPlugin.EmotionSelectedHandler emotionSelectedHandler) {
        final List<View> viewList = EmojiUtils.getEmojiListView(c, emotionSelectedHandler);
        ViewPageAdapter adapter = new ViewPageAdapter(viewList);
        vp.setAdapter(adapter);

        PageIndicator indicator = new PageIndicator(c) {

            @Override
            public void onPageSelected(int page) {
                super.onPageSelected(page);
                // 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
                if (page == viewList.size() - 1 || page == 0) {
                    if (page == 0) {
                        vp.setCurrentItem(page + 1);// 第二屏 会再次实现该回调方法实现跳转.
                    } else {
                        vp.setCurrentItem(page - 1);// 倒数第二屏
                    }
                }
            }

        };
        indicator.setFilterEnd(true);
        indicator.init(c, viewList.size());
        vp.setOnPageChangeListener(indicator);

        flipper.addView(indicator);
        vp.setCurrentItem(1);
    }

}
