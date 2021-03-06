package com.maxi.chatdemo.ui.base;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxi.chatdemo.R;
import com.maxi.chatdemo.common.ChatConst;
import com.maxi.chatdemo.db.ChatDbManager;
import com.maxi.chatdemo.db.ChatMessageBean;
import com.maxi.chatdemo.utils.ScreenUtil;
import com.maxi.chatdemo.widget.ChatBottomView;
import com.maxi.chatdemo.widget.HeadIconSelectorView;
import com.maxi.chatdemo.widget.pulltorefresh.PullToRefreshLayout;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public abstract class BaseActivity extends Activity {

    int group_index = 0;
    int player_plot_index = 0;
    int mom_plot_index = 0;
    public final static int MOM = 0;
    public final static int PLAYER = 1;
    public final static int CHOICE = 2;
    public final static int AUTO_PIC = 0;
    public final static int MANUAL_PIC = 1;
    int pic_mode = 0;
    int role = 1;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setAllIndexs(int new_group_index) {
        group_index = new_group_index;
        player_plot_index = 0;
        mom_plot_index = 0;
    }

    public void addMomIndex() {
        if (mom_plot_index <= plots[group_index][0].length - 1) {
            mom_plot_index++;
        }
    }

    public void addPlayerIndex() {
        if (player_plot_index <= plots[group_index][1].length - 1) {
            player_plot_index++;
        }
    }

//    {{
//        "看起來怎麼樣？",
//                "兒子不要去那裡，我認為這很危險。"
//    }, {
//        "等等，媽媽，我看到一所房子",
//                "這是一間木屋，但是很笨重。等等，我給你發照片",
//                "但是也許有什麼..."
//    }},

    /**
     * mom and son plots
     */
    public String[][][] plots = {{{
            "你在哪裡？",
            "你為什麼不告訴我？",
            "...無論如何，你現在在售貨亭見面嗎？"
    }, {
            "剛從廁所出來",
            "我以為你會等",
            "好的"
    }}, {{
            "不...我以前從未見過",
            "好的，等你，注意。你好嗎？",
            "發生了什麼事？",
            "哦，真的嗎？是燒食物的老方法，現在沒有多少人會使用它"
    }, {
            "樹為什麼變得那樣？你看到了嗎？",
            "那真是太奇怪了...什麼都沒有...我來了",
            "一切都還好。 \n？？！！！？！",
            "什麼都沒有，我只是看到好像血跡的染料殘留在樓梯上。\n哦，我看到這裡有一些木頭在草地上，好像有人在這裡燒烤過"
    }}, {{
            "什麼？",
            "什麼？!!!",
            "發生了什麼事？",
            "快點。",
            "看起來怎麼樣？",
            "兒子不要去那裡，我認為這很危險。"
    }, {
            "哦，天哪，我剛剛看到了什麼……",
            "一頭死鹿……等等……\n…",
            "是幻覺還是只是……",
            "沒事。",
            "等等，媽媽，我看到一所房子",
            "這是一間木屋，但是很殘舊。",
            "但是也許有什麼..."
    }}, {{
            "不要想太多，也許只是動物。",
            "總之，快點。",
            "兒子？"
    }, {
            "我覺得有人在跟著我……有人躲在那兒……",
            "但是我不認為這是……",
            "等等。現在聲音越來越近…"
    }}, {{
            "你在那裡看到了什麼？",
            "給我發照片嗎？"
    }, {
            "...",
            "...一個家庭...我不知道為什麼，我覺得我很早以前就認識他們。"
    }}, {{
            "...馬上離開。",
            "躲起來！"
    }, {
            "有人來了......",
            "有人來了"
    }}, {{
            "請盡快來",
            "一定要小心。"
    }, {
            "讓我確認現在是安全的",
            "當然\n我剛下車，我會小心的。"
    }}, {{
            "那好，快過來。\n兒子？\n回答我\n兒子？！\n你還好嗎？"
    }, {
            "我認為那個人已經走了。"
    }}};

    /**
     * the logic of choice branch
     */
    void roadMap() {
        if (group_index == 0) {
            //{"0", "左", "右", "1"}
            if (choice == 0) {
                Toast.makeText(this, "不是這樣，我記得她說要去自助服務亭", Toast.LENGTH_SHORT).show();
            } else {
                setRole(PLAYER);
                setAllIndexs(1);
                setPlotView(null);
            }
        } else if (group_index == 1) {
            //{"1", "更近看", "走開", "0"}
            if (choice == 0) {
                pic_id = R.drawable.pic4;
                autoPicHandler.sendEmptyMessageDelayed(0, 500);
                setRole(PLAYER);
                setAllIndexs(2);
                setPlotView(null);
            } else {
                setRole(PLAYER);
                setAllIndexs(3);
                setPlotView(null);
            }
        } else if (group_index == 2) {
            //{"2", "讓我檢查一下。", "無論如何，我來了。", "1"}
            if (choice == 0) {
                pic_id = R.drawable.pic5;
                autoPicHandler.sendEmptyMessageDelayed(0, 500);
                setRole(PLAYER);
                setAllIndexs(4);
                setPlotView(null);
            } else {
                endIv.setImageResource(R.drawable.normal_end);
                endIv.setVisibility(View.VISIBLE);
            }
        } else if (group_index == 3) {
            //{"3", "被幻覺吞噬", "避免被幻想誤吞", "0"}
            mess_et_click.setClickable(false);
            if (choice == 0) {
                playerEndGIF(R.drawable.end4);
            } else {
                playerEndGIF(R.drawable.end2);
            }
        } else if (group_index == 4) {
            //{"4", "我將拍攝照片拿下", "我將照片留在此處", "0"}
            if (choice == 0) {
                setRole(PLAYER);
                setAllIndexs(5);
                setPlotView(null);
            } else {
                endIv.setImageResource(R.drawable.end3);
                endIv.setVisibility(View.VISIBLE);
            }
        } else if (group_index == 5) {
            //{"5", "藏在壁櫥裡", "躲在門後", "0"}
            if (choice == 0) {
                setRole(PLAYER);
                setAllIndexs(6);
                setPlotView(null);
            } else {
                setRole(PLAYER);
                setAllIndexs(7);
                setPlotView(null);
            }
        }
    }

    public void playerEndGIF(int id) {
        endIv.setImageResource(id);
        endIv.setVisibility(View.VISIBLE);
        endGIFDrawable=(GifDrawable)endIv.getDrawable();
        endGIFDrawable.start();
        endGIFDrawable.setLoopCount(1);
    }

    public Handler playerEndGIFHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            playerEndGIF(msg.what);
            playerEndGIFHandler2.sendEmptyMessageDelayed(R.drawable.end1,7000);
        }
    };

    public Handler playerEndGIFHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            playerEndGIF(msg.what);
        }
    };

    public String getMomPlot() throws ArrayIndexOutOfBoundsException {
        return plots[group_index][MOM][mom_plot_index];
    }

    public String getPlayerPlot() throws ArrayIndexOutOfBoundsException {
        return plots[group_index][PLAYER][player_plot_index];
    }

    protected void setPlotView(String[] c) {
        if(c == null) {
            choice2.setVisibility(View.GONE);
            try {
                choice1.setText(getPlayerPlot());
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else {
            mess_et_click.setClickable(true);
            choice1.setText(c[1]);
            choice2.setText(c[2]);
            choice2.setVisibility(View.VISIBLE);
        }
    }

    // choice location
    int[][] choice_indexs = {
            {0, PLAYER, 3},
            {1, MOM, 4},
            {2, PLAYER, 7},
            {3, MOM, 2},
            {4, MOM, 2},
            {5, MOM, 2}
    };

    // choice content
    public String[][] choices = {
            {"0", "左", "右", "1"},
            {"1", "更近看", "走開", "0"},
            {"2", "讓我檢查一下。", "無論如何，我來了。", "1"},
            {"3", "被幻覺吞噬", "避免被幻想誤吞", "0"},
            {"4", "我將拍攝照片拿下", "我將照片留在此處", "0"},
            {"5", "藏在壁櫥裡", "躲在門後", "0"}
    };

    public String[] getOnechoice() {
        for (int i = 0; i < choice_indexs.length; i++) {
            if (choice_indexs[i][0] == group_index) {
                if (PLAYER == choice_indexs[i][1]) {
                    if (choice_indexs[i][2] == player_plot_index+1) {
                        return choices[i];
                    }
                }
                if (MOM == choice_indexs[i][1]) {
                    if (choice_indexs[i][2] == mom_plot_index+1) {
                        return choices[i];
                    }
                }
            }
        }
        return null;
    }

    // all pictures id
    public int[] pics = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic6
    };

    //picture location
    int[][] pic_indexs = {
            {0, PLAYER, 3, AUTO_PIC},
            {1, PLAYER, 1, AUTO_PIC},
            {1, PLAYER, 3, AUTO_PIC},
            {1, PLAYER, 4, AUTO_PIC}
    };

    public int getPics() {
        for (int i = 0; i < pic_indexs.length; i++) {
            if (pic_indexs[i][0] == group_index) {
                if (pic_indexs[i][1] == PLAYER) {
                    if(pic_indexs[i][2] == player_plot_index){
                        pic_mode = pic_indexs[i][3];
                        return pics[i];
                    }
                } else if (pic_indexs[i][1] == MOM) {
                    if(pic_indexs[i][2] == mom_plot_index){
                        pic_mode = pic_indexs[i][3];
                        return pics[i];
                    }
                }

            }

        }
        return -1;
    }

    final protected Handler autoPicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    photoIv.setImageDrawable(getResources().getDrawable(pic_id));
                    photoIv.setVisibility(View.VISIBLE);
                    clearPic();
                    break;
                case 1:
                    photoIv.setVisibility(View.GONE);
//                    showPic();
                    break;
                default:
                    break;
            }
        }
    };

    int choice;

    public PullToRefreshLayout pullList;
    public boolean isDown = false;
    private boolean CAN_WRITE_EXTERNAL_STORAGE = true;
    private boolean CAN_RECORD_AUDIO = true;
    public int position;
    public int bottomStatusHeight = 0;
    private Button send;
    private Button photo;

    public Button getPhoto() {
        return photo;
    }

    private ImageView photoIv;
    private GifImageView endIv;
    private GifDrawable endGIFDrawable;
    private ChatBottomView tbbv;

    public ChatBottomView getTbbv() {
        return tbbv;
    }

    private Button choice1;
    private Button choice2;
    public View mess_et_click;

    public View getMess_et_click() {
        return mess_et_click;
    }

    public EditText mEditTextContent;

    public EditText getmEditTextContent() {
        return mEditTextContent;
    }

    private File mCurrentPhotoFile;
    private Toast mToast;
    public String userName = "test";
    private String camPicPath;
    public List<ChatMessageBean> tblist = new ArrayList<>();
    private List<String> reslist;
    public ChatDbManager mChatDbManager;
    public int page = 0;
    public int number = 10;
    public List<ChatMessageBean> pagelist = new ArrayList<>();
    public ArrayList<String> imageList = new ArrayList<>();
    public HashMap<Integer, Integer> imagePosition = new HashMap<>();
    private static final int SDK_PERMISSION_REQUEST = 127;
    private static final int IMAGE_SIZE = 100 * 1024;
    public static final int SEND_OK = 0x1110;
    public static final int REFRESH = 0x0011;
    public static final int RECERIVE_OK = 0x1111;
    public static final int PULL_TO_REFRESH_DOWN = 0x0111;

    protected BaseActivity() {

    }

    protected abstract void sendMessage();

    protected abstract void sendMessageWithoutReceive();

    protected abstract void loadRecords();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findView();
        initpop();
        init();
    }

    @Override
    protected void onDestroy() {
        cancelToast();
        setAllIndexs(0);
        super.onDestroy();
    }

    protected void findView() {
        pullList = (PullToRefreshLayout) findViewById(R.id.content_lv);
        mEditTextContent = (EditText) findViewById(R.id.mess_et);
        mess_et_click = findViewById(R.id.mess_et_click);
        send = findViewById(R.id.send);
        photo = findViewById(R.id.photo);
        photoIv = findViewById(R.id.photoIv);
        endIv = findViewById(R.id.endIv);
        endGIFDrawable = (GifDrawable) endIv.getDrawable();
        endGIFDrawable.start();
        endGIFDrawable.setLoopCount(1);
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            mEditTextContent.setInputType(InputType.TYPE_NULL);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(mEditTextContent, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tbbv = (ChatBottomView) findViewById(R.id.other_lv);
        choice1 = tbbv.getImageGroup();
        choice2 = tbbv.getCameraGroup();
        setPlotView(null);
        initActionBar();
    }

    protected int pic_id = -1;

    protected void showPic() {
        pic_id = getPics();
        if (pic_id != -1) {
            if (pic_mode == AUTO_PIC) {
                mess_et_click.setClickable(false);
                autoPicHandler.sendEmptyMessageDelayed(0, 500);
            } else {
                photo.setBackgroundColor(getResources().getColor(R.color.red1));
                photo.setClickable(true);
            }
        }
    }

    void clearPic() {
        autoPicHandler.sendEmptyMessageDelayed(1, 3000);
        mess_et_click.setClickable(true);
    }

    protected void init() {
//        mEditTextContent.setOnKeyListener(onKeyListener);
        mChatDbManager = new ChatDbManager();
        PullToRefreshLayout.pulltorefreshNotifier pullNotifier = new PullToRefreshLayout.pulltorefreshNotifier() {
            @Override
            public void onPull() {
                downLoad();
            }
        };
        mess_et_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRole(PLAYER);
                if (tbbv.getVisibility() == View.GONE) {
                    mEditTextContent.setVisibility(View.VISIBLE);
                    tbbv.setVisibility(View.VISIBLE);
                } else {
                    tbbv.setVisibility(View.GONE);
                }
            }
        });
        pullList.setpulltorefreshNotifier(pullNotifier);

        tbbv.setOnHeadIconClickListener(new HeadIconSelectorView.OnHeadIconClickListener() {

            @SuppressLint("InlinedApi")
            @Override
            public void onClick(int from) {
                if (choice2.getVisibility() == View.GONE) {
                    if (from == ChatBottomView.FROM_GALLERY) {
                        try {
                            mEditTextContent.setText(getPlayerPlot());
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                    }
                } else if (choice2.getVisibility() == View.VISIBLE) { //two choices
//                    setRole(CHOICE);
                    if (from == ChatBottomView.FROM_GALLERY) {
                        choice = 0;
                    } else if (from == ChatBottomView.FROM_CAMERA) {
                        choice = 1;
                    }
                    tbbv.setVisibility(View.GONE);
                    roadMap();
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!mEditTextContent.getText().toString().isEmpty()) {
                    tbbv.setVisibility(View.GONE);
                    mess_et_click.setClickable(false);
                    photo.setClickable(false);
                    sendMessage();
                }
            }
        });

        final Handler takePhotoHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                photoIv.setVisibility(View.GONE);
                photo.setClickable(false);
                photo.setBackgroundColor(getResources().getColor(R.color.light_gray_11));
                mess_et_click.setClickable(true);
            }
        };
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoIv.setVisibility(View.VISIBLE);
                photoIv.setImageDrawable(getResources().getDrawable(pic_id));
                takePhotoHandler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        photo.setClickable(false);
        reslist = getExpressionRes(40);

        List<View> views = new ArrayList<>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);

        mEditTextContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tbbv.setVisibility(View.GONE);
            }

        });

        bottomStatusHeight = ScreenUtil.getNavigationBarHeight(this);

        page = (int) mChatDbManager.getPages(number);
        loadRecords();
    }

    private void initActionBar() {
        if (getActionBar() == null) {
            return;
        }
        getActionBar().setCustomView(R.layout.layout_action_bar);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ivLeft:
                        doLeft();
                        break;
                    case R.id.ivRight:
                        doRight();
                        break;
                    case R.id.llRight:
                        doRight();
                        break;
                }
            }
        };
        getActionBar().getCustomView().findViewById(R.id.ivLeft).setOnClickListener(listener);
        getActionBar().getCustomView().findViewById(R.id.ivRight).setOnClickListener(listener);
        getActionBar().getCustomView().findViewById(R.id.llRight).setOnClickListener(listener);
        ((TextView) getActionBar().getCustomView().findViewById(R.id.tvTitle)).setText(getTitle().toString());
    }

    public void setTitle(CharSequence title) {
        ((TextView) getActionBar().getCustomView().findViewById(R.id.tvTitle)).setText(title);
    }

    protected void doLeft() {
        finish();
    }

    protected void doRight() {

    }

    @SuppressLint({"NewApi", "InflateParams"})
    private void initpop() {
    }

    private void downLoad() {
        if (!isDown) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    loadRecords();
                }
            }).start();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            tbbv.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void reset() {
        tbbv.setVisibility(View.GONE);

    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.layout_expression_gridview, null);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, reslist.size()));
        }
        list.add("delete_expression");
        return view;
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "f" + x;
            reslist.add(filename);
        }
        return reslist;

    }


    public ChatMessageBean getTbub(String username, int type,
                                   String Content, String imageIconUrl, String imageUrl,
                                   String imageLocal, String userVoicePath, String userVoiceUrl,
                                   Float userVoiceTime, @ChatConst.SendState int sendState) {
        ChatMessageBean tbub = new ChatMessageBean();
        tbub.setUserName(username);
        String time = returnTime();
        tbub.setTime(time);
        tbub.setType(type);
        tbub.setUserContent(Content);
        tbub.setImageIconUrl(imageIconUrl);
        tbub.setImageUrl(imageUrl);
        tbub.setUserVoicePath(userVoicePath);
        tbub.setUserVoiceUrl(userVoiceUrl);
        tbub.setUserVoiceTime(userVoiceTime);
        tbub.setSendState(sendState);
        tbub.setImageLocal(imageLocal);
//        mChatDbManager.insert(tbub);

        return tbub;
    }


    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }


}
