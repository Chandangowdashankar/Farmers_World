package com.farmers.world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class JobActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	
	private ArrayList<HashMap<String, Object>> list_job = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private Intent i = new Intent();
	private DatabaseReference job = _firebase.getReference("job");
	private ChildEventListener _job_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.job);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_fab = findViewById(R.id._fab);
		
		linear1 = findViewById(R.id.linear1);
		listview1 = findViewById(R.id.listview1);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AddJobActivity.class);
				startActivity(i);
			}
		});
		
		_job_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				job.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list_job = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list_job.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(list_job));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				job.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list_job = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list_job.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(list_job));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		job.addChildEventListener(_job_child_listener);
	}
	
	private void initializeLogic() {
	}
	
	public void _Shadow(final double _sadw, final double _cru, final String _wc, final View _widgets) {
		android.graphics.drawable.GradientDrawable wd = new android.graphics.drawable.GradientDrawable();
		wd.setColor(Color.parseColor(_wc));
		wd.setCornerRadius((int)_cru);
		_widgets.setElevation((int)_sadw);
		_widgets.setBackground(wd);
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.list_job, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final TextView textview5 = _view.findViewById(R.id.textview5);
			final TextView textview6 = _view.findViewById(R.id.textview6);
			final ImageView imageview3 = _view.findViewById(R.id.imageview3);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final Button button1 = _view.findViewById(R.id.button1);
			
			_Shadow(20, 40, "#A5D6A7", linear1);
			if (list_job.get((int)_position).containsKey("username")) {
				textview1.setText("Name:- ".concat(list_job.get((int)_position).get("username").toString()));
			}
			if (list_job.get((int)_position).containsKey("phone number")) {
				textview2.setText("Number:- ".concat(list_job.get((int)_position).get("phone number").toString()));
			}
			if (list_job.get((int)_position).containsKey("address")) {
				textview3.setText("Address:- ".concat(list_job.get((int)_position).get("address").toString()));
			}
			if (list_job.get((int)_position).containsKey("job type")) {
				textview4.setText("Job type:- ".concat(list_job.get((int)_position).get("job type").toString()));
			}
			if (list_job.get((int)_position).containsKey("salary")) {
				textview5.setText("salary:- ".concat(list_job.get((int)_position).get("salary").toString()));
			}
			if (list_job.get((int)_position).containsKey("work time")) {
				textview6.setText("Job duration:- ".concat(list_job.get((int)_position).get("work time").toString()));
			}
			if (list_job.get((int)_position).containsKey("job image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(list_job.get((int)_position).get("job image").toString())).into(imageview3);
			}
			if (list_job.get((int)_position).containsKey("profile")) {
				Glide.with(getApplicationContext()).load(Uri.parse(list_job.get((int)_position).get("profile").toString())).into(circleimageview1);
			}
			if (list_job.get((int)_position).get("verified").toString().equals("true")) {
				imageview1.setVisibility(View.VISIBLE);
			}
			else {
				if (list_job.get((int)_position).get("verified").toString().equals("false")) {
					imageview1.setVisibility(View.GONE);
				}
			}
			button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setAction(Intent.ACTION_CALL);
					i.setData(Uri.parse("tel: ".concat(list_job.get((int)_position).get("phone number").toString())));
					startActivity(i);
				}
			});
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}