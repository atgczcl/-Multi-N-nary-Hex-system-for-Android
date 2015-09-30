package com.atgczcl.sysconvertfuncalculater;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atgczcl.SysConvert.tools.AdmobHelper;
import com.atgczcl.SysConvert.tools.MySoftHelper;
import com.atgczcl.SysConvert.tools.NumberTools;

public class Convert_Calculater extends Activity {
	private EditText page5_data1;
	private EditText page5_data2;
	private EditText page5_from1;
	private EditText page5_from2;
	private CheckBox jia;
	private CheckBox jian;
	private CheckBox cheng;
	private CheckBox chu;
	private CheckBox yu;
	private Button page5_bt_clear;
	private Button page5_result;
	private LinearLayout page5_list;
	
	
	static final int page3_msg = 3;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case page3_msg:
//				SimpleAdapter simpleAdapter3 = new SimpleAdapter(Convert_Calculater.this,
//						list3, R.layout.mylist_num_item, new String[] {
//								"title", "value" }, new int[] { R.id.title,
//								R.id.value });
//				page5_list.setAdapter(simpleAdapter3);
//				setListViewHeightBasedOnChildren(page5_list);
				page5_list.removeAllViews();
				for (int i = 0; i < list3.size(); i++) {
					HashMap<String, String> temHashMap=list3.get(i);
					addListItem(temHashMap.get("title"), temHashMap.get("value"));
				}
				break;
			default:
				break;
			}
		};
	};
	private AdmobHelper admobHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convert__calculater);
		
		page5_data1=(EditText)findViewById(R.id.page5_data1);
		page5_data2=(EditText)findViewById(R.id.page5_data2);
		page5_from1=(EditText)findViewById(R.id.page5_from1);
		page5_from2=(EditText)findViewById(R.id.page5_from2);
		jia=(CheckBox)findViewById(R.id.jia);
		jian=(CheckBox)findViewById(R.id.jian);
		cheng=(CheckBox)findViewById(R.id.cheng);
		chu=(CheckBox)findViewById(R.id.chu);
		yu=(CheckBox)findViewById(R.id.yu);
		page5_bt_clear=(Button)findViewById(R.id.page5_bt_clear);
		page5_result=(Button)findViewById(R.id.page5_result);
		page5_list=(LinearLayout)findViewById(R.id.page5_list);
		
		page5Init();
		
		admobHelper=new AdmobHelper(this);
//		admobHelper.makeFullScreenAd();
		admobHelper.loadGoogleAdmobSide(R.id.imAdview5);
		admobHelper.loadGoogleAdmobSide(R.id.imAdview7, 20*1000, 20*1000);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		admobHelper.onDestroy();
	}
	
	List<HashMap<String, String>> list3 = new ArrayList<HashMap<String, String>>();
	private void page5Init() {
		
		page5_result.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getData5();
			}
		});
		
		page5_bt_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				list3.clear();
//				SimpleAdapter simpleAdapter2 = new SimpleAdapter(Convert_Calculater.this,
//						list3, R.layout.mylist_num_item, new String[] {
//								"title", "value" }, new int[] { R.id.title,
//								R.id.value });
//				page5_list.setAdapter(simpleAdapter2);
				page5_list.removeAllViews();
			}
		});

//		page5_list.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				TextView textView = (TextView) arg1.findViewById(R.id.value);
//				if (textView != null) {
//					// 定义一个粘贴板管理器
//					final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//					// 在Menu事件呼叫的时候运行这一行代码 文字就复制到粘贴板了
//					clipBoard.setText(textView.getText());
//					showToast("复制成功");
//				}
//
//			}
//		});
	}
	
	boolean isManualRun;
	Thread thread;
	private void getData5() {
		if (isManualRun) {
			showToast("still running...");
			return;
		}
		if (thread == null) {
			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					isManualRun = true;
					BigDecimal data1=genTenNum(page5_data1, page5_from1);
					BigDecimal data2=genTenNum(page5_data2, page5_from2);
					Log.e("GetTenNum", data1.toString()+"   data2="+data2.toString());
					ArrayList<String> result=new ArrayList<String>();
					ArrayList<String> signal=new ArrayList<String>();
					if (jia.isChecked()) {
						result.add(data1.add(data2).toString());
						signal.add("		+");
					}
					if (jian.isChecked()) {
						result.add(data1.subtract(data2).toString());
						signal.add("		-");
					}
					if (cheng.isChecked()) {
						result.add(data1.multiply(data2).toString());
						signal.add("		*");
					}
					MathContext mc = new MathContext(36, RoundingMode.HALF_DOWN);
					if (chu.isChecked()) {
						if (data2.compareTo(BigDecimal.ZERO)!=0) {
							result.add(data1.divide(data2, mc).toString());
						}else {
							result.add("NaN");
						}
						signal.add("		/");
					}
					if (yu.isChecked()) {
						if (data2.compareTo(BigDecimal.ZERO)!=0) {
							BigDecimal[] bDecimals=data1.divideAndRemainder(data2, mc);
							if (bDecimals.length==0) {
								result.add(data1.divideAndRemainder(data2, mc).toString());
							}else {
								result.add("Head="+data1.divideAndRemainder(data2, mc)[0].toString()+"\n"+
										"Tail="+data1.divideAndRemainder(data2, mc)[1].toString());
							}
						}else {
							result.add("NaN");
						}
						signal.add("		%");
					}
					
					
					
					
					// map2.put("title", fromsString+"进制-"+toString+"进制");
					for (int i = 0; i < result.size(); i++) {
						HashMap<String, String> map2 = new HashMap<String, String>();
//						map2.put("title", "Data_1=(D)"+data1.toString() +"\n"+ signal.get(i) +"\n"+ "Data_2=(D)"+data2.toString());
						map2.put("title", signal.get(i) +"\n");
						map2.put("value", result.get(i));
						Log.i("", i+"");
						list3.add(0, map2);
					}
					Log.i("", list3.size()+"=size");
					handler.sendEmptyMessage(page3_msg);
					isManualRun = false;
				}
			});
			thread.start();
		} else {
			if (thread.isAlive()) {
				thread.run();
			} else {
				thread = null;
				getData5();
			}
		}
	}
	
	public static String replaceSt(String string) {
		return string.replace(" ", "").replace("\n", "");
	}
	
	private BigDecimal genTenNum(EditText data, EditText from) {
		String numString1 = data.getText().toString();
		numString1 = replaceSt(numString1);
		char headString = 0;
		if (numString1.length() > 0) {
			headString = numString1.charAt(0);
			if (headString == '-') {
				numString1 = numString1.replace("-", "");
			}
		}
		// int and float
		String[] strings = numString1.split("\\.");
		
		if (strings != null && strings.length > 0) {
			NumberTools numberTools = new NumberTools(Convert_Calculater.this);
			String fromsString = (from.getText().toString())+ "";
			String toString = (10) + "";
			String decimString = numberTools.anyToDecimal(
					strings[0], fromsString, true,
					strings.length > 1 ? true : false);
			String decimFloatString = null;
			if (strings.length > 1) {
				decimFloatString = numberTools.floatAnyToDecimal(
						strings[1], fromsString, true);
			}
			String tempString = numberTools.decimal2Any(
					decimString, toString);
			String tempFloat = numberTools.floatDecimalToAny(
					decimFloatString, toString);
			tempString += "." + tempFloat;
			numberTools.clean();
			BigDecimal bigDecimal=new BigDecimal(tempString);
			return bigDecimal;
		}
		return BigDecimal.ZERO;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_convert__calculater, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())//得到被点击的item的itemId
        {
        case R.id.menu_settings://这里的Id就是布局文件中定义的Id，在用R.id.XXX的方法获取出来
        	MySoftHelper.ShowSoftInfoDialog(this, getString(R.string.menu_settings), getString(R.string.info));
            break;
        }
        return true;
    }
	
	private void addListItem(String titlesString, String valueString) {
		View listItemView=View.inflate(this, R.layout.mylist_num_item, null);
		listItemView.setBackgroundDrawable(getResources().getDrawable(R.drawable.mybutton_whiteblue_style));
		final TextView title=(TextView)listItemView.findViewById(R.id.title);
		final TextView value=(TextView)listItemView.findViewById(R.id.value);
		title.setText(titlesString);
		value.setText(valueString);
		listItemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (value != null) {
					// 定义一个粘贴板管理器
					final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					// 在Menu事件呼叫的时候运行这一行代码 文字就复制到粘贴板了
					clipBoard.setText(value.getText());
					showToast("copy ok!");
				}
			}
		});
		page5_list.addView(listItemView, 0);

	}
	
	
	private void showToast(final String text) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(Convert_Calculater.this, text, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		return false;
	}
	
	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, this.getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
		}
	}
	
	/**
	 * @param listView
	 */
	public  void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
               return;
        }
       
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
               View listItem = listAdapter.getView(i, null, listView);
               listItem.measure(0, 0);  //计算子项View 的宽高
               totalHeight += listItem.getMeasuredHeight()+100; //统计所有子项的总高度
               System.out.println(listItem.getMeasuredHeight());
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
 }

}
