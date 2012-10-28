package com.thx.bizcat.chat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.thx.bizcat.R;
import com.thx.bizcat.adapter.ChatAdapter;
import com.thx.bizcat.adapter.ChatElement;

public class ChatActivity extends Activity {

	private ChatAdapter adapter;
	private List<ChatElement> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_main);
		
		list = new ArrayList<ChatElement>();
		//List<ChatElement> list = new ArrayList<ChatElement>();
		ChatElement item = new ChatElement();
		item.setType(0).setText("오늘 옴팡지게 배가 고프단 말이다");
		list.add(item);
		
		item = new ChatElement();
		item.setType(0).setText("오늘 옴팡지게 배가 고프단 말이다");
		list.add(item);
		
		item = new ChatElement();
		item.setType(1).setText("소시찌 꿔 먹을테냐??");
		list.add(item);
		
		item = new ChatElement();
		item.setType(0).setText("그래그래 지금 언능 꿔먹장 내가 배가 좀 많이 고프당 ㅋㅋ");
		list.add(item);
		
		item = new ChatElement();
		item.setType(0).setText("후딱 꾸자고..ㅋㅋ");
		list.add(item);
		
		
		
		
		ListView listview = (ListView)findViewById(R.id.CHATlist);
		adapter = new ChatAdapter(this, R.layout.chat_item, list);
		listview.setAdapter(adapter);
		
		
		Button left = (Button)findViewById(R.id.button1);
		Button right = (Button)findViewById(R.id.button2);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				ChatElement item = new ChatElement();
				item.setType(0).setText(list.size() + ": 아자아자");
				list.add(0, item);
				
				item = new ChatElement();
				item.setType(0).setText(list.size() + ":아자아자");
				list.add(1, item);
				
				item = new ChatElement();
				item.setType(1).setText(list.size() + ":흐미밍??");
				list.add(2, item);
				adapter.notifyDataSetChanged();
			}
		});
		
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ChatElement item = new ChatElement();
				item.setType(1).setText(list.size() + ":밑에밑에");
				list.add(item);
				
				item = new ChatElement();
				item.setType(1).setText(list.size() + ":들어갔옴");
				list.add(item);
				
				item = new ChatElement();
				item.setType(0).setText(list.size() + ":진짜 마지막임?");
				list.add(item);
				adapter.notifyDataSetChanged();
				
			}
		});
		
		
		
		
		
	}
	
}
