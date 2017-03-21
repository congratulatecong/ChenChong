
package com.cong.chenchong.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cong.chenchong.R;
import com.cong.chenchong.base.SPConfigManager;
import com.cong.chenchong.base.SingleChatPubNumHelper;
import com.cong.chenchong.bean.CallshowExtItem;
import com.cong.chenchong.helper.EmotionPlugin;
import com.cong.chenchong.robot.ChatMessage;
import com.cong.chenchong.robot.ChatMessage.Type;
import com.cong.chenchong.robot.ChatMessageAdapter;
import com.cong.chenchong.robot.HttpUtils;
import com.cong.chenchong.util.EmojiUtils;
import com.cong.chenchong.util.Utils;
import com.cong.chenchong.widget.BottomQuickAction;
import com.cong.chenchong.widget.BottomQuickAction.BottomItem;
import com.cong.chenchong.widget.EditTextBase;
import com.cong.chenchong.widget.EditTextBase.OnCloseOtherViewsListerner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class RobotActivity extends BaseActivity implements OnClickListener {

    private ListView mMsgs;

    private ChatMessageAdapter mAdapter;

    private List<ChatMessage> mDatas;

    private EditTextBase mEtInputMsg;

    private SPConfigManager mSharedPrefrences;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            ChatMessage fromMessge = (ChatMessage) msg.obj;
            mDatas.add(fromMessge);
            // mAdapter.setTxtSize(mTxtSize);
            mAdapter.notifyDataSetChanged();
            mMsgs.setSelection(mDatas.size() - 1);
        };

    };

    private TextView mTvFirstPubNum = null;

    private TextView mTvSecondPubNum = null;

    private TextView mTvThirdPubNum = null;

    private LinearLayout mLlPubNum = null;

    private LinearLayout mLlInput = null;

    protected ImageView mTvOpenPubNum;

    protected ImageView mTvHidePubNum;

    private boolean mAllowedPubNum = false; // 该值表示，校验公共账号状态通过，1，号码是否允许，2该号码是否于现在手机sim卡是否对应。

    private boolean mBottomPubNumVisual = true;

    // 企业菜单数据
    private Map<Integer, CallshowExtItem> mPubNumItems = null;

    private TextView mTvSmsInputCount;

    private View mll_chat_options_up;

    private Button mBtnRightTop;

    private ImageView moption_more;

    private ImageView moption_goto_fullscreen_input;

    public InputMethodManager mInputMethodManager = null;

    private TextView mEtInvisible;

    private SendMessageTask mSendMessageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getIntent().getStringExtra("title"));
        mSharedPrefrences = new SPConfigManager(this);
        mTxtSize = mSharedPrefrences.getFontSize();
        mBottomPubNumVisual = mSharedPrefrences.isPopupMenuShow(); // 取上一次底部状态。

        initView();
        initEmoji();
        initDatas();
        showChangeChatMsgSizeGuaid();

        mAllowedPubNum = true;// 加载公共账号相关变量,后者依赖前者
        if (mAllowedPubNum) {// 这里有个优化：对普通号码,不载inflate公共账号的工具栏
            initPubNumView();
        }
        initViewEvent();

    }

    private void initViewEvent() {
        mEtInputMsg.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("cc", "ACTION_DOWN1");
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.v("cc", "ACTION_DOWN2");
                    mEtInputMsg.setFocusable(true);
                    mEtInputMsg.requestFocus();
                }
                return false;
            }
        });
    }

    private class SendMessageTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... message) {
            ChatMessage fromMessage = HttpUtils.sendMessage(message[0]);
            Message m = mHandler.obtainMessage();
            m.obj = fromMessage;
            mHandler.sendMessage(m);
            return null;
        }

    }

    /**
     * 初始化view，设置为默认模式，在onresume中根据情况显示
     */
    private void initPubNumView() {
        if (null == mLlPubNum) {
            ViewStub stub = (ViewStub) findViewById(R.id.stub_pub_num_bottom);
            stub.inflate();
            mLlPubNum = (LinearLayout) findViewById(R.id.ll_pub_num_bottom);
        }
        mTvFirstPubNum = (TextView) mLlPubNum.findViewById(R.id.button_first_tv);
        mTvSecondPubNum = (TextView) mLlPubNum.findViewById(R.id.button_second_tv);
        mTvThirdPubNum = (TextView) mLlPubNum.findViewById(R.id.button_third_tv);
        mLlInput = (LinearLayout) findViewById(R.id.ll_chat_bottom);
        mTvHidePubNum = (ImageView) mLlPubNum.findViewById(R.id.close_view);
        initPubNumstate();
    }

    private void initPubNumstate() {
        if (mLlPubNum != null) {
            mLlPubNum.setVisibility(View.GONE);
        }
        if (mLlInput != null) {
            mLlInput.setVisibility(View.VISIBLE);
        }
        if (mTvOpenPubNum != null) {
            mTvOpenPubNum.setVisibility(View.VISIBLE);
        }
        if (mAllowedPubNum) {
            // mBottomPubNumVisual = mSharedPrefrences.isPopupMenuShow(); //
            // 取上一次底部状态。
            int titleLen = 0;
            Set<Integer> keySets = mPubNumItems.keySet();
            Drawable drawable = getResources().getDrawable(R.drawable.singlechat_bottom_pubnum_next_menu);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            final BottomQuickAction.OnActionItemClickListener listener = SingleChatPubNumHelper.getInstance().getItemClickListener(RobotActivity.this, mHandler);
            if (keySets != null && keySets.size() > 0) {
                for (Integer key : keySets) {
                    titleLen++;
                    final CallshowExtItem callshowExtItem = mPubNumItems.get(key);
                    final BottomItem bottomItem = new BottomItem();
                    bottomItem.data = callshowExtItem;
                    SpannableString ss = new SpannableString(callshowExtItem.clientType + " ");
                    switch (titleLen) {
                        case 1:
                            mTvFirstPubNum.setVisibility(View.VISIBLE);
                            mTvFirstPubNum.setText(callshowExtItem.clientType);
                            mTvFirstPubNum.setTag(callshowExtItem.id);
                            if (callshowExtItem.operateType == 1) {
                                if (drawable != null) {
                                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                                    ss.setSpan(span, callshowExtItem.clientType.length(), callshowExtItem.clientType.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                }
                                mTvFirstPubNum.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        showVerticalPopupItem(mTvFirstPubNum, transData2BottomItem(callshowExtItem.children));
                                    }
                                });
                            } else {
                                mTvFirstPubNum.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        listener.onItemClick(bottomItem);
                                    }
                                });
                            }
                            mTvFirstPubNum.setText(ss);

                            break;
                        case 2:
                            mTvSecondPubNum.setVisibility(View.VISIBLE);
                            mTvSecondPubNum.setText(callshowExtItem.clientType);
                            mTvSecondPubNum.setTag(callshowExtItem.id);
                            if (callshowExtItem.operateType == 1) {
                                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                                ss.setSpan(span, callshowExtItem.clientType.length(), callshowExtItem.clientType.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                mTvSecondPubNum.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        showVerticalPopupItem(mTvSecondPubNum, transData2BottomItem(callshowExtItem.children));
                                    }
                                });
                            } else {
                                mTvSecondPubNum.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        listener.onItemClick(bottomItem);
                                    }
                                });
                            }
                            mTvSecondPubNum.setText(ss);
                            break;
                        case 3:
                            mTvThirdPubNum.setVisibility(View.VISIBLE);
                            mTvThirdPubNum.setText(callshowExtItem.clientType);
                            mTvThirdPubNum.setTag(callshowExtItem.id); // 设置按钮的id，
                            if (callshowExtItem.operateType == 1) {
                                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                                ss.setSpan(span, callshowExtItem.clientType.length(), callshowExtItem.clientType.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                mTvThirdPubNum.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        showVerticalPopupItem(mTvThirdPubNum, transData2BottomItem(callshowExtItem.children));
                                    }
                                });
                            } else {
                                mTvThirdPubNum.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        listener.onItemClick(bottomItem);
                                    }
                                });
                            }
                            mTvThirdPubNum.setText(ss);
                            break;
                        default:
                            break;
                    }
                }
            } else { // 异常处理分支，判断可以显示企业账号服务菜单，但是加载菜单的时候出现异常，恢复初始状态。
                if (mLlPubNum != null) {
                    mLlPubNum.setVisibility(View.GONE);
                }
                if (mLlInput != null) {
                    mLlInput.setVisibility(View.VISIBLE);
                }
                if (mTvOpenPubNum != null) {
                    mTvOpenPubNum.setVisibility(View.GONE);
                }
                mAllowedPubNum = false;
            }

            mTvOpenPubNum.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // closeAttachmentView();
                    mInputMethodManager.hideSoftInputFromWindow(mEtInputMsg.getWindowToken(), 0);
                    setBottomPubNumAnimationIn(mLlInput, mLlPubNum);
                    mBottomPubNumVisual = true;

                }
            });
            mTvHidePubNum.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mBottomPubNumVisual = false;
                    setBottomPubNumAnimationIn(mLlPubNum, mLlInput);
                }
            });
        }
    }

    /**
     * 显示二级菜单
     *
     * @param v 一级菜单的view
     * @param
     */
    private void showVerticalPopupItem(final View v, List<BottomItem> secondPubNumItems) {
        BottomQuickAction mBottomQuickAction = new BottomQuickAction(this, v, mLlPubNum);

        mBottomQuickAction.addActionListItems(secondPubNumItems);
        mBottomQuickAction.setOnActionItemClickListener(SingleChatPubNumHelper.getInstance().getItemClickListener(RobotActivity.this, mHandler));
        mBottomQuickAction.show(v);
    }

    public List<BottomItem> transData2BottomItem(List<CallshowExtItem> dataIn) {
        List<BottomItem> data = new ArrayList<BottomItem>();
        for (CallshowExtItem children : dataIn) {
            BottomItem bi = new BottomItem();
            bi.title = children.clientType;
            bi.data = children;
            data.add(bi);
        }
        return data;
    }

    /**
     * 底部UI切换动画，
     *
     * @param fromview 消失的
     * @param toview 出现的
     */
    private void setBottomPubNumAnimationIn(View fromview, View toview) {
        fromview.clearAnimation();
        toview.clearAnimation();
        final View endView = toview;
        final View startView = fromview;
        Animation animationOut = AnimationUtils.loadAnimation(this, R.anim.bottom_popmenu_animation_out);
        final Animation animationIn = AnimationUtils.loadAnimation(this, R.anim.bottom_popmenu_animation_in);
        animationOut.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                startView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                try {
                    setBottomState4PubNum();
                    endView.setVisibility(View.VISIBLE);
                    endView.startAnimation(animationIn);
                    startView.setVisibility(View.GONE);
                    startView.clearAnimation();
                } catch (Exception e) {
                    if (!isFinishing()) {
                        setBottomState4PubNum();
                        endView.setVisibility(View.VISIBLE);
                        startView.setVisibility(View.GONE);
                    }
                }

            }
        });
        fromview.startAnimation(animationOut);
    }

    /**
     * 单聊页面状态跳转的时候设置底部UI，如，多选模式，onResume，切换正常输入模式和底部按钮模式
     */
    private void setBottomState4PubNum() {
        if (!mAllowedPubNum) {
            return;
        }

        // moption_goto_fullscreen_input.setVisibility(View.GONE);
        // if (MODE_NORMAL == mMode) {
        if (mBottomPubNumVisual) {
            mLlPubNum.setVisibility(View.VISIBLE);
            mLlInput.setVisibility(View.GONE);
            mTvOpenPubNum.setVisibility(View.VISIBLE);
            mTvHidePubNum.setVisibility(View.VISIBLE);
            // if (mTvSmsInputCount != null && mTvSmsInputCount.getVisibility()
            // == View.VISIBLE) {
            // mTvSmsInputCount.setVisibility(View.INVISIBLE);
            // }
            if (mEtInputMsg != null) {
                mEtInputMsg.clearFocus();
            }
        } else {
            mLlInput.setVisibility(View.VISIBLE);
            mLlPubNum.setVisibility(View.GONE);
            mTvOpenPubNum.setVisibility(View.VISIBLE);
            mTvHidePubNum.setVisibility(View.VISIBLE);
            // if (mTvSmsInputCount != null && mTvSmsInputCount.getVisibility()
            // == View.INVISIBLE) {
            // mTvSmsInputCount.setVisibility(View.VISIBLE);
            // }
            // }

        }
        // else if (MODE_BATCH_OPER == mMode && mBottomPubNumVisual) {
        // mLlPubNum.setVisibility(View.GONE);
        // }
    }

    /**
     * 显示双指缩放改变聊天气泡字体大小的引导界面
     */
    private void showChangeChatMsgSizeGuaid() {

        if (mSharedPrefrences.isChangeSizeGuaidShow()) {

            ViewGroup parent = (ViewGroup) getWindow().getDecorView();
            if (parent != null) {
                LayoutParams flp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
                final ImageView imgGuaid = new ImageView(this);
                imgGuaid.setImageResource(R.drawable.img_chat_message_size_guaid);
                imgGuaid.setScaleType(ScaleType.CENTER_INSIDE);
                imgGuaid.setBackgroundColor(getResources().getColor(R.color.half_transparent));
                parent.addView(imgGuaid, flp);
                imgGuaid.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ViewGroup guaidParentView = (ViewGroup) imgGuaid.getParent();
                        if (guaidParentView != null) {
                            guaidParentView.removeView(imgGuaid);
                        }
                    }
                });
            }

            mSharedPrefrences.setChangeSizeGuaidShow(false);
        }
    }

    private void initDatas() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好", Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mAdapter.setTxtSize(mTxtSize);
        mMsgs.setAdapter(mAdapter);

        CallshowExtItem row1 = new CallshowExtItem(1, "常用查询", "常用查询", 1, "");
        CallshowExtItem row11 = new CallshowExtItem(11, "北京天气", "常用查询", 11, "");
        CallshowExtItem row12 = new CallshowExtItem(11, "北京空气", "常用查询", 12, "");
        CallshowExtItem row13 = new CallshowExtItem(11, "航班查询", "常用查询", 13, "");
        CallshowExtItem row14 = new CallshowExtItem(11, "列车查询", "常用查询", 14, "");
        CallshowExtItem row15 = new CallshowExtItem(11, "顺丰快递", "常用查询", 15, "");
        CallshowExtItem row2 = new CallshowExtItem(2, "互动功能", "冷笑话", 1, "");
        CallshowExtItem row21 = new CallshowExtItem(11, "讲个冷笑话", "常用查询", 21, "");
        CallshowExtItem row22 = new CallshowExtItem(11, "成语接龙", "常用查询", 22, "");
        CallshowExtItem row23 = new CallshowExtItem(11, "星座运势", "常用查询", 23, "");
        CallshowExtItem row3 = new CallshowExtItem(3, "科技新闻", "天气查询", 0, "");
        ArrayList<CallshowExtItem> popupMenu = new ArrayList<CallshowExtItem>();
        popupMenu.add(row11);
        popupMenu.add(row12);
        popupMenu.add(row13);
        popupMenu.add(row14);
        popupMenu.add(row15);
        row1.children = popupMenu;
        popupMenu = new ArrayList<CallshowExtItem>();
        popupMenu.add(row21);
        popupMenu.add(row22);
        popupMenu.add(row23);
        row2.children = popupMenu;
        row3.children = popupMenu;
        mPubNumItems = new LinkedHashMap<Integer, CallshowExtItem>();
        mPubNumItems.put(1, row1);
        mPubNumItems.put(2, row2);
        mPubNumItems.put(3, row3);

    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.listview_robot);
        // mEtInputMsg = (EditText) findViewById(R.id.et_message);
        // mBtnRightTop = (Button) findViewById(R.id.btn_send_message);

        // 初始化文本编辑框
        mll_chat_options_up = findViewById(R.id.layout_input);

        mBtnRightTop = (Button) mll_chat_options_up.findViewById(R.id.btn_right_top);
        mBtnRightTop.setOnClickListener(this);
        moption_more = (ImageView) mll_chat_options_up.findViewById(R.id.option_more);

        moption_goto_fullscreen_input = (ImageView) mll_chat_options_up.findViewById(R.id.option_goto_emoji);
        mTvOpenPubNum = (ImageView) mll_chat_options_up.findViewById(R.id.option_open_public_number);

        mEtInvisible = (TextView) findViewById(R.id.et_input_qualcomm);
        mEtInputMsg = (EditTextBase) findViewById(R.id.dd_et_input);
        mEtInputMsg.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    mBtnRightTop.setClickable(true);
                    mBtnRightTop.setEnabled(true);
                } else {
                    mBtnRightTop.setClickable(false);
                    mBtnRightTop.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtInputMsg.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.v("cc", "hasFocus->" + hasFocus);
            }
        });

        mEtInputMsg.setOnCloseOtherViewsListerner(new OnCloseOtherViewsListerner() {

            @Override
            public void onCloseOtherViews(EditTextBase etb) {
                closeEmotionLayout();
            }
        });

    }

    private View mEmotionLayout;

    private void initEmoji() {

        moption_goto_fullscreen_input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mEmotionLayout == null) {
                    return;
                }
                if (mEmotionLayout.getVisibility() == View.VISIBLE) {
                    closeEmotionLayout();
                } else {
                    openEmotionLayout(v);
                }
            }
        });
        mEmotionLayout = findViewById(R.id.emotion_layout);
        ViewPager vp = (ViewPager) mEmotionLayout.findViewById(R.id.pager);
        ViewFlipper vf = (ViewFlipper) mEmotionLayout.findViewById(R.id.indicator);
        EmojiUtils.createEmojiLayout(this, vp, vf, new EmotionPlugin.EmotionSelectedHandler() {

            @Override
            public void onEmotionSelected(int type, String content, Bundle data) {
                String emotion = content;
                if (!Utils.isEmpty(emotion)) {
                    if (type == EmotionPlugin.TYPE_TEXT) {
                        if (!mEtInputMsg.isFocused()) {
                            mEtInputMsg.requestFocus();
                        }
                        int selectionStart = mEtInputMsg.getSelectionStart();
                        int selectionEnd = mEtInputMsg.getSelectionEnd();
                        if (selectionStart == -1) {
                        } else {
                            mEtInputMsg.getText().replace(selectionStart, selectionEnd, emotion);
                            mEtInputMsg.setSelection(selectionStart + emotion.length());
                        }
                    }
                } else {
                    final KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
                    mEtInputMsg.onKeyDown(KeyEvent.KEYCODE_DEL, keyEventDown);
                }
            }

        });
    }

    protected void openEmotionLayout(View view) {
        // closeAttachmentView();
        // if (mIsFromNewChat) {
        // mInputMethodManager.hideSoftInputFromWindow(mSearchEdit.getWindowToken(),
        // 0);
        // }
        if (mEtInputMsg != null) {
            mInputMethodManager.hideSoftInputFromWindow(mEtInputMsg.getWindowToken(), 0);
        }
        if (mEmotionLayout != null) {
            mEmotionLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void closeEmotionLayout() {
        if (mEmotionLayout == null || mEmotionLayout.getVisibility() != View.VISIBLE) {
            return;
        }
        mEmotionLayout.setVisibility(View.GONE);
    }

    private static final int TOUCH_STATE_NONE = 0;

    private static final int TOUCH_STATE_ZOOM = 1;

    private int mTouchState = TOUCH_STATE_NONE;

    private float mOldDistance = 0;

    public static final int TxtSizeDefault = 16;

    public static final int TxtSizeMin = 10;

    public static final int TxtSizeMax = 45;

    protected int mTxtSize = 0;// 文字信息的字体大小 sp

    protected int mFullTxtSize = 0;// 文字信息的字体大小 sp

    private boolean fontSizeMaxWarnShown = false;

    private boolean fontSizeMinWarnShown = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_POINTER_2_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (mTouchState != TOUCH_STATE_ZOOM) {

                    mOldDistance = spacing(event);
                    mTouchState = TOUCH_STATE_ZOOM;

                    if (mEtInputMsg != null) {
                        mEtInputMsg.setLongClickable(false);
                        mEtInputMsg.setSelected(false);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchState == TOUCH_STATE_ZOOM) {
                    if (mOldDistance > 0 && event.getPointerCount() > 1) {
                        float newDistance = spacing(event);

                        boolean shoudlChange = false;
                        boolean increase = true;
                        if (newDistance / mOldDistance >= 1.1) {
                            shoudlChange = true;
                        } else if (newDistance / mOldDistance <= 0.9) {
                            shoudlChange = true;
                            increase = false;
                        }

                        if (shoudlChange) {
                            changeTextSize(increase);
                            mOldDistance = newDistance;
                        }
                    }
                } else {

                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_NONE;
                fontSizeMaxWarnShown = false;
                fontSizeMinWarnShown = false;
                if (mEtInputMsg != null) {
                    mEtInputMsg.setLongClickable(true);
                    mEtInputMsg.setSelected(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_NONE;
                fontSizeMaxWarnShown = false;
                fontSizeMinWarnShown = false;
                if (mEtInputMsg != null) {
                    mEtInputMsg.setLongClickable(true);
                    mEtInputMsg.setSelected(true);
                }
                break;
        }
        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    protected void changeTextSize(boolean increase) {
        if (increase) {
            mTxtSize++;
            if (mTxtSize > TxtSizeMax) {
                mTxtSize = TxtSizeMax;
                if (!fontSizeMaxWarnShown) {
                    Toast.makeText(this, R.string.max_font_size, Toast.LENGTH_SHORT).show();
                    fontSizeMaxWarnShown = true;
                }
            }
        } else {
            mTxtSize--;
            if (mTxtSize < TxtSizeMin) {
                mTxtSize = TxtSizeMin;
                if (!fontSizeMinWarnShown) {
                    Toast.makeText(this, R.string.min_font_size, Toast.LENGTH_SHORT).show();
                    fontSizeMinWarnShown = true;
                }
            }
        }

        mAdapter.setTxtSize(mTxtSize);
        mAdapter.notifyDataSetChanged();

        mSharedPrefrences.setFontSize(mTxtSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottomState4PubNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAllowedPubNum && mLlPubNum != null) {
            mBottomPubNumVisual = mLlPubNum.getVisibility() == View.VISIBLE ? true : false;
            mSharedPrefrences.setPopupMenuShow(mBottomPubNumVisual);
        }
        if (mSendMessageTask != null) {
            mSendMessageTask.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_top:
                final String toMsg = mEtInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(RobotActivity.this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mEtInputMsg.setText("");

                sendMessage(toMsg);
                break;

            default:
                break;
        }

    }

    private void sendMessage(final String message) {
        ChatMessage toMessage = new ChatMessage();
        toMessage.setDate(new Date());
        toMessage.setMsg(message);
        toMessage.setType(Type.OUTCOMING);
        mDatas.add(toMessage);
        mAdapter.notifyDataSetChanged();
        mMsgs.setSelection(mDatas.size() - 1);

        mSendMessageTask = new SendMessageTask();
        mSendMessageTask.execute(message);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mEmotionLayout != null && mEmotionLayout.getVisibility() == View.VISIBLE) {
                closeEmotionLayout();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
