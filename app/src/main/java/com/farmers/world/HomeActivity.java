package com.farmers.world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import de.hdodenhof.circleimageview.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class HomeActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private String getAvatarLink = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear13;
	private LinearLayout linear3;
	private HorizontalScrollView hscroll1;
	private TextView textview1;
	private HorizontalScrollView hscroll2;
	private LinearLayout linear4;
	private ImageView imageview1;
	private ImageView imageview2;
	private CircleImageView circleimageview1;
	private LinearLayout linear14;
	private TextView textview13;
	private ImageView imageview21;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private ImageView imageview15;
	private TextView textview14;
	private ImageView imageview16;
	private TextView textview15;
	private ImageView imageview17;
	private TextView textview16;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private ImageView imageview18;
	private TextView textview17;
	private ImageView imageview19;
	private TextView textview18;
	private ImageView imageview20;
	private TextView textview19;
	
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	private Intent i = new Intent();
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	
	private AlertDialog.Builder logout;
	private TimerTask r;
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		linear13 = findViewById(R.id.linear13);
		linear3 = findViewById(R.id.linear3);
		hscroll1 = findViewById(R.id.hscroll1);
		textview1 = findViewById(R.id.textview1);
		hscroll2 = findViewById(R.id.hscroll2);
		linear4 = findViewById(R.id.linear4);
		imageview1 = findViewById(R.id.imageview1);
		imageview2 = findViewById(R.id.imageview2);
		circleimageview1 = findViewById(R.id.circleimageview1);
		linear14 = findViewById(R.id.linear14);
		textview13 = findViewById(R.id.textview13);
		imageview21 = findViewById(R.id.imageview21);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		imageview15 = findViewById(R.id.imageview15);
		textview14 = findViewById(R.id.textview14);
		imageview16 = findViewById(R.id.imageview16);
		textview15 = findViewById(R.id.textview15);
		imageview17 = findViewById(R.id.imageview17);
		textview16 = findViewById(R.id.textview16);
		linear11 = findViewById(R.id.linear11);
		linear12 = findViewById(R.id.linear12);
		imageview18 = findViewById(R.id.imageview18);
		textview17 = findViewById(R.id.textview17);
		imageview19 = findViewById(R.id.imageview19);
		textview18 = findViewById(R.id.textview18);
		imageview20 = findViewById(R.id.imageview20);
		textview19 = findViewById(R.id.textview19);
		fauth = FirebaseAuth.getInstance();
		logout = new AlertDialog.Builder(this);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		
		linear9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), JobActivity.class);
				startActivity(i);
			}
		});
		
		linear13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "please wait");
				shareApplication();
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ProfileActivity.class);
				startActivity(i);
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), NotificationsActivity.class);
				startActivity(i);
			}
		});
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ProductsActivity.class);
				startActivity(i);
			}
		});
		
		linear8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ChatActivity.class);
				startActivity(i);
			}
		});
		
		linear11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AboutUsActivity.class);
				startActivity(i);
			}
		});
		
		linear12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ContactusActivity.class);
				startActivity(i);
			}
		});
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					textview13.setText(_childValue.get("display_name").toString());
					if (_childValue.containsKey("avatar")) {
						if (_childValue.get("avatar").toString().equals("null")) {
							Glide.with(getApplicationContext()).load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/hospital-management-1481b.appspot.com/o/profile%2FScreenshot_2021-07-15-07-16-20-214_chat.app.com.jpg?alt=media&token=2e0de50f-7c4e-41be-aef2-1aa735294c63")).into(circleimageview1);
						}
						else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("avatar").toString())).into(circleimageview1);
						}
						getAvatarLink = _childValue.get("avatar").toString();
					}
					if (_childValue.get("verified").toString().equals("true")) {
						imageview21.setVisibility(View.VISIBLE);
					}
					else {
						if (_childValue.get("verified").toString().equals("false")) {
							imageview21.setVisibility(View.GONE);
						}
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					textview13.setText(_childValue.get("display_name").toString());
					if (_childValue.containsKey("avatar")) {
						if (_childValue.get("avatar").toString().equals("null")) {
							Glide.with(getApplicationContext()).load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/hospital-management-1481b.appspot.com/o/profile%2FScreenshot_2021-07-15-07-16-20-214_chat.app.com.jpg?alt=media&token=2e0de50f-7c4e-41be-aef2-1aa735294c63")).into(circleimageview1);
						}
						else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("avatar").toString())).into(circleimageview1);
						}
						getAvatarLink = _childValue.get("avatar").toString();
					}
					if (_childValue.get("verified").toString().equals("true")) {
						imageview21.setVisibility(View.VISIBLE);
					}
					else {
						if (_childValue.get("verified").toString().equals("false")) {
							imageview21.setVisibility(View.GONE);
						}
					}
				}
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
		user.addChildEventListener(_user_child_listener);
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_anim2();
		_anim();
		_SetCornerRadius(linear2, 0, 5, "#D7D7D7");
		_sx2(linear5, 30, 15, "#D7D7D7", true);
		_sx2(linear7, 30, 15, "#D7D7D7", true);
		_sx2(linear8, 30, 15, "#D7D7D7", true);
		_sx2(linear9, 30, 15, "#D7D7D7", true);
		_sx2(linear11, 30, 15, "#D7D7D7", true);
		_sx2(linear12, 30, 15, "#D7D7D7", true);
		_sx2(linear13, 30, 15, "#D7D7D7", true);
		StrictMode.VmPolicy.Builder builder = 
		    new StrictMode.VmPolicy.Builder(); 
		    StrictMode.setVmPolicy(builder.build());
		    if(Build.VERSION.SDK_INT>=24){ 
			         try{
				        java.lang.reflect.Method m = 
				              StrictMode.class.getMethod(
				        "disableDeathOnFileUriExposure"); 
				              m.invoke(null); 
				       }
			      catch(Exception e){ 
				        showMessage(e.toString()); 
				       } 
			    }
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	public void _Shadow(final double _sadw, final double _cru, final String _wc, final View _widgets) {
		android.graphics.drawable.GradientDrawable wd = new android.graphics.drawable.GradientDrawable();
		wd.setColor(Color.parseColor(_wc));
		wd.setCornerRadius((int)_cru);
		_widgets.setElevation((int)_sadw);
		_widgets.setBackground(wd);
	}
	
	
	public void _SetCornerRadius(final View _view, final double _radius, final double _shadow, final String _color) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable();
		
		ab.setColor(Color.parseColor(_color));
		ab.setCornerRadius((float) _radius);
		_view.setElevation((float) _shadow);
		_view.setBackground(ab);
	}
	
	
	public void _sx2(final View _view, final double _radius, final double _shadow, final String _color, final boolean _ripple) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setElevation((int)_shadow);
			
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#212121")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setBackground(gd);
			_view.setElevation((int)_shadow);
		}
	}
	
	
	public void _FadeOut(final View _view, final double _duration) {
		_Animator(_view, "scaleX", 0, 200);
		_Animator(_view, "scaleY", 0, 200);
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_Animator(_view, "scaleX", 1, 200);
						_Animator(_view, "scaleY", 1, 200);
					}
				});
			}
		};
		_timer.schedule(r, (int)(_duration));
	}
	
	
	public void _Animator(final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
		anim.start();
	}
	
	
	public void _anim2() {
		linear2.setScaleX((float)(0));
		linear5.setScaleX((float)(0));
		linear7.setScaleX((float)(0));
		linear8.setScaleX((float)(0));
		linear9.setScaleX((float)(0));
		linear12.setScaleX((float)(0));
		linear11.setScaleX((float)(0));
		linear13.setScaleX((float)(0));
	}
	
	
	public void _anim() {
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear2, 200);
					}
				});
			}
		};
		_timer.schedule(r, (int)(200));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(textview1, 400);
					}
				});
			}
		};
		_timer.schedule(r, (int)(400));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear5, 600);
					}
				});
			}
		};
		_timer.schedule(r, (int)(600));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear7, 800);
					}
				});
			}
		};
		_timer.schedule(r, (int)(800));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear8, 1000);
					}
				});
			}
		};
		_timer.schedule(r, (int)(1000));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear9, 1200);
					}
				});
			}
		};
		_timer.schedule(r, (int)(1200));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear12, 1400);
					}
				});
			}
		};
		_timer.schedule(r, (int)(1400));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear11, 1600);
					}
				});
			}
		};
		_timer.schedule(r, (int)(1600));
		r = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FadeOut(linear13, 1800);
					}
				});
			}
		};
		_timer.schedule(r, (int)(1800));
	}
	
	
	public void _share() {
	}
	private void shareApplication() { 
		      android.content.pm.ApplicationInfo app = 
		      getApplicationContext().getApplicationInfo(); 
		      String filePath = app.sourceDir;
		      Intent intent = new Intent(Intent.ACTION_SEND); 
		      intent.setType("*/*"); 
		      java.io.File originalApk = new java.io.File(filePath); 
		      try {  
			        java.io.File tempFile = new java.io.File(getExternalCacheDir() + "/ExtractedApk"); 
			         if (!tempFile.isDirectory()) 
			         if (!tempFile.mkdirs()) 
			         return; 
			         tempFile = new java.io.File(tempFile.getPath() + "/" + 
			         "export.apk");
			         if (!tempFile.exists()) 
			          {
				           try{
					             if (!tempFile.createNewFile()) { 
						               return; }
					            }
				           catch (java.io.IOException e){} 
				          } 
			         java.io.InputStream in = new java.io.FileInputStream (originalApk);
			         java.io.OutputStream out = new java.io.FileOutputStream(tempFile);
			         byte[] buf = new byte[1024];
			         int len; 
			         while ((len = in.read(buf)) > 0) { 
				            out.write(buf, 0, len); 
				         } 
			         in.close(); 
			         out.close(); 
			         intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
			         startActivity(Intent.createChooser(intent, "Share app via"));
			      } 
		      catch (java.io.IOException e) 
		      { showMessage(e.toString()); 
			      } 
		   }
	{
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