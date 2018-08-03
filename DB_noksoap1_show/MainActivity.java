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
	// ���ҧ����� ��������� �繻�����  HashMap ����  MyArrList
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
		
		// ���ҧ����� params
		 ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		try {
			// ���¡�����ʹ  getJSONUrl ���觤�� url,params � ����觤�� return ��� str �ҡ�ҵ���� ��Ѻ�� ��  data
			JSONArray data = new JSONArray(getJSONUrl(url,params));
			
		//	data=new JSONArray(getj)
			
			// ���ҧ����� MyArrList �繵���û�����  ArrayList �红����Ż����� Hashmap
			MyArrList = new ArrayList<HashMap<String, String>>();
			//  ���ҧ����û�����  HashMap ���� map
			HashMap<String, String> map=new HashMap<String,String>();
			
			for(int i = 0; i < data.length(); i++){
				
				// ��� c �� JSONObject ����Ѻ��� data ���ǹ�ٻ
                JSONObject c = data.getJSONObject(i);
                
    			map = new HashMap<String, String>();
    			map.put("MemberID", c.getString("MemberID"));
    			map.put("Username", c.getString("Username"));
    			map.put("Password", c.getString("Password"));
    			map.put("Name", c.getString("Name"));
    			map.put("Email", c.getString("Email"));
    			map.put("Tel", c.getString("Tel"));
    			
    			// MyArrList  �����  map �ҡ���ǹ�ٻ����Ф���
    			MyArrList.add(map);	
			}

			//  lisView1 ���¡�����ʹ setAdapter ���繵���û���������  ImageAdapter
			// lisView1.setAdapter(new ImageAdapter(this));
			// ���  lisView1 ��Ҥ�� 3 ��Ŵ� ����Ѻ�� ��  convertview ������ �� adap 
			 lisView1.setAdapter(new ImageAdapter(this));
						
			//   ��� register ����� lisView1 ����� activity_column ������������  �˹�� main
			//   ��� lisView1 �ʴ��������
			registerForContextMenu(lisView1);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

     public class ImageAdapter extends BaseAdapter 
    {
        private Context context;
        
        // ������¡�� ImageAdapter  ��ͧ�觵���û����� Context (��ͤ���) �����
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
			// ���ҧ����� inflater ����᷹  ����ʴ�˹�Ҩ� xml  ��ٻ�ͧ context
			
LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
				if (convertView == null) {
					//convertView  �繵�Ƿ��ᵡ�� activity_column.xml  
					convertView = inflater.inflate(R.layout.activity_column, null); 
				}

				// ColMemberID
				// ���ҧ  textView ����� column  ��  convertView ����
				TextView txtMemberID = (TextView) convertView.findViewById(R.id.ColMemberID);
				txtMemberID.setPadding(10, 0, 0, 0);
				// ��� conconvertView ���� txtMemberID ����ͤ��� �ҡ���˹� position �ҡ �ҵ����
				txtMemberID.setText(MyArrList.get(position).get("MemberID") +".");
				
				// R.id.ColName
				TextView txtName = (TextView) convertView.findViewById(R.id.ColName);
				txtName.setPadding(5, 0, 0, 0);
				txtName.setText(MyArrList.get(position).get("Name"));
				
				// R.id.ColTel
				TextView txtTel = (TextView) convertView.findViewById(R.id.ColTel);
				txtTel.setPadding(5, 0, 0, 0);
				txtTel.setText(MyArrList.get(position).get("Tel"));
				
				// �觤׹��� convertView ������������ ��Ѻ�
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
		// �觤�ҷ����ҡ server 㹪��͵����  str 
		return str.toString();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

