package com.zdj.go.map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.zdj.souye.R;

import java.util.ArrayList;
import java.util.List;

import weather.Weather_today;

/**
 */

public class SecondRoute extends Activity implements OnGetRoutePlanResultListener {
    private MapView mMap=null;
    private BaiduMap mBaidu=null;
    private View.OnClickListener listener1;
    private View.OnClickListener listener2;
    Button mBtnPre = null; // 上一个节点
    Button mBtnNext = null; // 下一个节点
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    MassTransitRouteLine massroute = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    private TextView popupText = null; // 泡泡view
    int nowSearchType = -1; // 当前进行的检索，供判断浏览节点时结果使用。

    private EditText cityEdit;
    private EditText startEdit;
    private EditText endEdit;
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    TransitRouteResult nowResultransit = null;
    DrivingRouteResult nowResultdrive = null;
    boolean hasShownDialogue = false;
    private ImageButton back;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondloca);
        mMap=findViewById(R.id.map);
        mBaidu=mMap.getMap();
        cityEdit=findViewById(R.id.oritation);
        startEdit=findViewById(R.id.start);
        endEdit=findViewById(R.id.end);
        mBtnPre=findViewById(R.id.preButton);
        mBtnNext=findViewById(R.id.nextButton);
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(16.0f);
        mBaidu.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mThread();
        back=findViewById(R.id.back);
    }
    public void voice_se(View view){
        switch (view.getId()){
            case R.id.voice0:flag=1;initSpeech(SecondRoute.this,flag);break;
            case R.id.voice2:flag=2;initSpeech(SecondRoute.this,flag);break;
            case R.id.voice3:flag=3;initSpeech(SecondRoute.this,flag);break;
        }
    }
    public void initSpeech(final Context context, final int flag) {
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    String result = parseVoice(recognizerResult.getResultString());
                    switch (flag){
                        case 1:cityEdit.setText(result);cityEdit.setSelection(cityEdit.getText().toString().length());break;
                        case 2:startEdit.setText(result);startEdit.setSelection(startEdit.getText().toString().length());break;
                        case 3:endEdit.setText(result);endEdit.setSelection(endEdit.getText().toString().length());break;
                    }
                }
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        mDialog.show();
    }
    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Weather_today.Voice voiceBean = gson.fromJson(resultString, Weather_today.Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Weather_today.Voice.WSBean> ws = voiceBean.ws;
        for (Weather_today.Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }
    public void backClick(View view){
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            if ((findViewById(R.id.preButton)).getVisibility() == View.VISIBLE){
                mBaidu.clear();
                (findViewById(R.id.preButton)).setVisibility(View.INVISIBLE);
                (findViewById(R.id.nextButton)).setVisibility(View.INVISIBLE);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.zoom(18.0f);
                mBaidu.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            else
                finish();
        }
        return false;
    }
    public void mThread(){
        listener1=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 重置浏览节点的路线数据
                route = null;
                mBtnPre.setVisibility(View.INVISIBLE);
                mBtnNext.setVisibility(View.INVISIBLE);
                mBaidu.clear();
                // 处理搜索按钮响应
                // 设置起终点信息，对于tranist search 来说，城市名无意义
                PlanNode stNode = PlanNode.withCityNameAndPlaceName(cityEdit.getText().toString(), startEdit.getText().toString());
                PlanNode enNode = PlanNode.withCityNameAndPlaceName(cityEdit.getText().toString(), endEdit.getText().toString());

                // 实际使用中请对起点终点城市进行正确的设定
                if (view.getId() == R.id.driveButton) {
                    mSearch.drivingSearch((new DrivingRoutePlanOption())
                            .from(stNode).to(enNode));
                    nowSearchType = 1;
                } else if (view.getId() == R.id.transitButton) {
                    mSearch.transitSearch((new TransitRoutePlanOption())
                            .from(stNode).city(cityEdit.getText().toString()).to(enNode));
                    nowSearchType = 2;
                }
            }
        };
        listener2=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng nodeLocation = null;
                String nodeTitle = null;
                Object step = null;
                if (nowSearchType != 0 && nowSearchType != -1) {
                    // 非跨城综合交通
                    if (route == null || route.getAllStep() == null) {
                        return;
                    }
                    if (nodeIndex == -1 && view.getId() == R.id.preButton) {
                        return;
                    }
                    // 设置节点索引
                    if (view.getId() == R.id.nextButton) {
                        if (nodeIndex < route.getAllStep().size() - 1) {
                            nodeIndex++;
                        } else {
                            return;
                        }
                    } else if (view.getId() == R.id.preButton) {
                        if (nodeIndex > 0) {
                            nodeIndex--;
                        } else {
                            return;
                        }
                    }
                    // 获取节结果信息
                    step = route.getAllStep().get(nodeIndex);
                    if (step instanceof DrivingRouteLine.DrivingStep) {
                        nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
                        nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
                    } else if (step instanceof TransitRouteLine.TransitStep) {
                        nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
                        nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
                    }
                }
                if (nodeLocation == null || nodeTitle == null) {
                    return;
                }

                // 移动节点至中心
                mBaidu.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
                // show popup
                popupText = new TextView(SecondRoute.this);
                popupText.setBackgroundResource(R.drawable.popup);
                popupText.setTextColor(0xFF000000);
                popupText.setText(nodeTitle);
                mBaidu.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
            }
        };
        (findViewById(R.id.driveButton)).setOnClickListener(listener1);
        (findViewById(R.id.transitButton)).setOnClickListener(listener1);
        (findViewById(R.id.preButton)).setOnClickListener(listener2);
        (findViewById(R.id.nextButton)).setOnClickListener(listener2);
    }
    class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView transitRouteList;
        private RouteLineAdapter mTransitAdapter;

        LocationMap.OnItemInDlgClickListener onItemInDlgClickListener;

        public MyTransitDlg(Context context, int theme) {
            super(context, theme);
        }

        public MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
                type) {
            this(context, 0);
            mtransitRouteLines = transitRouteLines;
            mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        public void setOnDismissListener(OnDismissListener listener) {
            super.setOnDismissListener(listener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transit_dialog);

            transitRouteList = (ListView) findViewById(R.id.transitList);
            transitRouteList.setAdapter(mTransitAdapter);

            transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemInDlgClickListener.onItemClick(position);
                    mBtnPre.setVisibility(View.VISIBLE);
                    mBtnNext.setVisibility(View.VISIBLE);
                    dismiss();
                    hasShownDialogue = false;
                }
            });
        }

        public void setOnItemInDlgClickLinster(LocationMap.OnItemInDlgClickListener itemListener) {
            onItemInDlgClickListener = itemListener;
        }

    }
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(SecondRoute.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            result.getSuggestAddrInfo();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);


            if (result.getRouteLines().size() > 1) {
                nowResultransit = result;
                if (!hasShownDialogue) {
                    MyTransitDlg myTransitDlg = new MyTransitDlg(SecondRoute.this,
                            result.getRouteLines(),
                            RouteLineAdapter.Type.TRANSIT_ROUTE);
                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            hasShownDialogue = false;
                        }
                    });
                    myTransitDlg.setOnItemInDlgClickLinster(new LocationMap.OnItemInDlgClickListener() {
                        public void onItemClick(int position) {

                            route = nowResultransit.getRouteLines().get(position);
                            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidu);
                            mBaidu.setOnMarkerClickListener(overlay);
                            routeOverlay = overlay;
                            overlay.setData(nowResultransit.getRouteLines().get(position));
                            overlay.addToMap();
                            overlay.zoomToSpan();
                        }

                    });
                    myTransitDlg.show();
                    hasShownDialogue = true;
                }
            } else if (result.getRouteLines().size() == 1) {
                // 直接显示
                route = result.getRouteLines().get(0);
                TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidu);
                mBaidu.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            } else {
                Log.d("route result", "结果数<0");
                return;
            }
        }
    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(SecondRoute.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            result.getSuggestAddrInfo();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;


            if (result.getRouteLines().size() > 1) {
                nowResultdrive = result;
                if (!hasShownDialogue) {
                    MyTransitDlg myTransitDlg = new MyTransitDlg(SecondRoute.this,
                            result.getRouteLines(),
                            RouteLineAdapter.Type.DRIVING_ROUTE);
                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            hasShownDialogue = false;
                        }
                    });
                    myTransitDlg.setOnItemInDlgClickLinster(new LocationMap.OnItemInDlgClickListener() {
                        public void onItemClick(int position) {
                            route = nowResultdrive.getRouteLines().get(position);
                            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidu);
                            mBaidu.setOnMarkerClickListener(overlay);
                            routeOverlay = overlay;
                            overlay.setData(nowResultdrive.getRouteLines().get(position));
                            overlay.addToMap();
                            overlay.zoomToSpan();
                        }

                    });
                    myTransitDlg.show();
                    hasShownDialogue = true;
                }
            } else if (result.getRouteLines().size() == 1) {
                route = result.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidu);
                routeOverlay = overlay;
                mBaidu.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
                mBtnPre.setVisibility(View.VISIBLE);
                mBtnNext.setVisibility(View.VISIBLE);
            } else {
                Log.d("route result", "结果数<0");
                return;
            }

        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }
    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        mMap.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMap.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMap.onDestroy();
        mMap=null;
        super.onDestroy();
    }
}
