package com.myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// สร้างตัวแปร อาเรย์ลิส เป็นประเภท  HashMap ชื่อ  MyArrList
	// ArrayList<HashMap<String, String>> MyArrList;
	    
    private ArrayList<HashMap<String, String>> MyArrList;
	private Button btnSearch;
	private ListView lisView1;


	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // btnSearch
        btnSearch = (Button) findViewById(R.id.btnSearch);      
   	 // listView1
       lisView1 = (ListView)findViewById(R.id.listView1); 	
        

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        
        ShowData(); 
        
    }
    
    public void ShowData()
    {

        
		String url = "http://192.168.43.139/showAllData.php";
		
		// สร้างตัวแปร params
		 ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		try {
			// เรียกใช้เมธอด  getJSONUrl โดยส่งค่า url,params ไป และส่งค่า return ค่า str จากดาต้าเบส กลับมา เป็น  data
			JSONArray data = new JSONArray(getJSONUrl(url,params));
			
		//	data=new JSONArray(getj)
			
			// สร้างตัวแปร MyArrList เป็นตัวแปรประเภท  ArrayList เก็บข้อมูลประเภท Hashmap
			MyArrList = new ArrayList<HashMap<String, String>>();
			//  สร้างตัวแปรประเภท  HashMap ชื่อ map
			HashMap<String, String> map=new HashMap<String,String>();
			
			for(int i = 0; i < data.length(); i++){
				
				// ค่า c เป็น JSONObject ที่รับค่า data ที่วนลูป
                JSONObject c = data.getJSONObject(i);
                
    			map = new HashMap<String, String>();
    			map.put("MemberID", c.getString("MemberID"));
    			map.put("Username", c.getString("Username"));
    			map.put("Password", c.getString("Password"));
    			map.put("Name", c.getString("Name"));
    			map.put("Email", c.getString("Email"));
    			map.put("Tel", c.getString("Tel"));
    			
    			// MyArrList  ใส่ค่า  map จากการวนลูปในแต่ละครั้ง
    			MyArrList.add(map);	
			}

			//  lisView1 เรียกใช้เมธอด setAdapter โดยเป็นตัวแปรประเภทคลาส  ImageAdapter
			// lisView1.setAdapter(new ImageAdapter(this));
			// ให้  lisView1 เอาค่า 3 ฟิลด์ ที่รับมา เป็น  convertview ตัวรวม มา adap 
			 lisView1.setAdapter(new ImageAdapter(this));
						
			//   ให้ register โดยเอา lisView1 ในแต่ละ activity_column ใส่ในแต่ละเมนู  ในหน้า main
			//   ให้ lisView1 แสดงผลในเมนู
			registerForContextMenu(lisView1);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

     public class ImageAdapter extends BaseAdapter 
    {
        private Context context;
        
        // ถ้าเรียกใช้ ImageAdapter  ต้องส่งตัวแปรประเภท Context (ข้อความ) เข้ามา
        public ImageAdapter(Context c) 
        {
        	// TODO Auto-generated method stub
            context = c;
        }
 
        public int getCount() {
        	//  return 0;
            return MyArrList.size();
        }
 
        public Object getItem(int position) {
        	// return null;
            return position;
        }
 
        public long getItemId(int position) {
        	// return 0;
            return position;
        }
		public View getView(final int position, View convertView, ViewGroup parent) {
			// return null;
			// สร้างตัวแปร inflater เพื่อแทน  การแสดงหน้าจอ xml  ในรูปของ context
			
LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
				if (convertView == null) {
					//convertView  เป็นตัวที่แตกเป็น activity_column.xml  
					convertView = inflater.inflate(R.layout.activity_column, null); 
				}

				// ColMemberID
				// สร้าง  textView ในแต่ละ column  เป็น  convertView ย่อย
				TextView txtMemberID = (TextView) convertView.findViewById(R.id.ColMemberID);
				txtMemberID.setPadding(10, 0, 0, 0);
				// ให้ conconvertView ย่อย txtMemberID ใส่ข้อความ จากตำแหน่ง position จาก ดาต้าเบส
				txtMemberID.setText(MyArrList.get(position).get("MemberID") +".");
				
				// R.id.ColName
				TextView txtName = (TextView) convertView.findViewById(R.id.ColName);
				txtName.setPadding(5, 0, 0, 0);
				txtName.setText(MyArrList.get(position).get("Name"));
				
				// R.id.ColTel
				TextView txtTel = (TextView) convertView.findViewById(R.id.ColTel);
				txtTel.setPadding(5, 0, 0, 0);
				txtTel.setText(MyArrList.get(position).get("Tel"));
				
				// ส่งคืนค่า convertView ตัวรวมทั้งหมด กลับไป
				return convertView;		
		}
    } 
    
    
    
    

	public String getJSONUrl(String url,List<NameValuePair> params) {
		
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Download OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ส่งค่าที่ได้จาก server ในชื่อตัวแปร  str 
		return str.toString();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

