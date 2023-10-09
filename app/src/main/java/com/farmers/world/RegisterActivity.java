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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.content.ClipData;
import android.content.Context;
import android.os.Vibrator;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.view.View;
import android.widget.AdapterView;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class RegisterActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private HashMap<String, Object> map = new HashMap<>();
	private double n = 0;
	private String colorCode = "";
	private boolean cancel = false;
	private String avatarUrl = "";
	private String profilePath = "";
	private String profileName = "";
	private String gende = "";
	private String aT = "";
	private String aM = "";
	private String aU = "";
	private String aIM = "";
	private String iconurl = "";
	
	private ArrayList<String> photo = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> tmp = new ArrayList<>();
	private ArrayList<String> gender = new ArrayList<>();
	
	private LinearLayout linear_main;
	private TextView textview1;
	private LinearLayout l_pic;
	private LinearLayout l_item;
	private EditText edittext1;
	private Spinner spinner1;
	private ProgressBar progressbar1;
	private ImageView imageview1;
	private LinearLayout linear4;
	private EditText tx_first_name;
	private EditText tx_last_name;
	
	private Intent i = new Intent();
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	
	private SharedPreferences file;
	private TimerTask time;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Vibrator vr;
	private StorageReference avatar = _firebase_storage.getReference("avatar");
	private OnCompleteListener<Uri> _avatar_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _avatar_download_success_listener;
	private OnSuccessListener _avatar_delete_success_listener;
	private OnProgressListener _avatar_upload_progress_listener;
	private OnProgressListener _avatar_download_progress_listener;
	private OnFailureListener _avatar_failure_listener;
	
	private RequestNetwork rq;
	private RequestNetwork.RequestListener _rq_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.register);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		
		linear_main = findViewById(R.id.linear_main);
		textview1 = findViewById(R.id.textview1);
		l_pic = findViewById(R.id.l_pic);
		l_item = findViewById(R.id.l_item);
		edittext1 = findViewById(R.id.edittext1);
		spinner1 = findViewById(R.id.spinner1);
		progressbar1 = findViewById(R.id.progressbar1);
		imageview1 = findViewById(R.id.imageview1);
		linear4 = findViewById(R.id.linear4);
		tx_first_name = findViewById(R.id.tx_first_name);
		tx_last_name = findViewById(R.id.tx_last_name);
		auth = FirebaseAuth.getInstance();
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		vr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		rq = new RequestNetwork(this);
		
		l_pic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					
				}
				if (_position == 1) {
					gende = "Male";
				}
				if (_position == 2) {
					gende = "Female";
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tx_first_name.getText().toString().replace(" ", "").equals("")) {
					vr.vibrate((long)(300));
				}
				else {
					if (tx_first_name.getText().toString().length() < 3) {
						vr.vibrate((long)(300));
					}
					else {
						if (profilePath.equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Select image");
						}
						else {
							_fabRotation(true);
							_pickRandomColor();
							map = new HashMap<>();
							map.put("username", tx_first_name.getText().toString().trim().concat(".".concat(tx_last_name.getText().toString().trim().concat(".".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(4), (int)(999999)))))))).toLowerCase());
							map.put("first_name", tx_first_name.getText().toString());
							map.put("last_name", tx_last_name.getText().toString());
							map.put("display_name", tx_first_name.getText().toString().trim().concat(" ".concat(tx_last_name.getText().toString().trim())));
							map.put("avatar", avatarUrl);
							map.put("number", file.getString("number", ""));
							map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							map.put("bio", "Hi! I'm using farmer world app. ");
							map.put("color", colorCode);
							map.put("verified", "false");
							map.put("email", edittext1.getText().toString());
							map.put("gender", gende);
							map.put("phone", "public");
							user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
							map.clear();
							time = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											_normalnotification("Farmer world", "Welcome to Farmer World😊");
											i.setClass(getApplicationContext(), HomeActivity.class);
											startActivity(i);
											finish();
										}
									});
								}
							};
							_timer.schedule(time, (int)(3000));
						}
					}
				}
			}
		});
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					file.edit().putString("account", FirebaseAuth.getInstance().getCurrentUser().getUid()).commit();
					i.putExtra("link", "null");
					i.setClass(getApplicationContext(), HomeActivity.class);
					startActivity(i);
					finish();
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		
		_avatar_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				_fab.setEnabled(false);
			}
		};
		
		_avatar_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_avatar_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				progressbar1.setVisibility(View.GONE);
				_setCircleImagePath(imageview1, profilePath, 0, "#FFFFFF");
				avatarUrl = _downloadUrl;
				l_pic.setEnabled(true);
				_fab.setEnabled(true);
			}
		};
		
		_avatar_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_avatar_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_avatar_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_rq_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map = new HashMap<>();
				map.put("username", tx_first_name.getText().toString().trim().concat(".".concat(tx_last_name.getText().toString().trim().concat(".".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(4), (int)(999999)))))))).toLowerCase());
				map.put("first_name", tx_first_name.getText().toString());
				map.put("last_name", tx_last_name.getText().toString());
				map.put("display_name", tx_first_name.getText().toString().trim().concat(" ".concat(tx_last_name.getText().toString().trim())));
				map.put("avatar", avatarUrl);
				map.put("number", file.getString("number", ""));
				map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("bio", "Hi! I'm using farmer world app. ");
				map.put("color", colorCode);
				map.put("verified", "false");
				map.put("phone", "public");
				user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
				map.clear();
				time = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								i.setClass(getApplicationContext(), HomeActivity.class);
								startActivity(i);
								finish();
							}
						});
					}
				};
				_timer.schedule(time, (int)(3000));
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				_fabRotation(false);
			}
		};
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_extra();
		_ProgresbarShow("Loading Data.... ");
		linear_main.setVisibility(View.GONE);
		com.google.android.material.appbar.AppBarLayout appBar =
		    (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
		appBar.setElevation(3f);
		appBar.setStateListAnimator(null);
		setTitle("Profile");
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		_setProgressBarColor(progressbar1, "#FFFFFF");
		progressbar1.setVisibility(View.GONE);
		_set_imeGo_Click(tx_last_name, _fab);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		_LengthOfEditText(tx_first_name, 13);
		_LengthOfEditText(tx_last_name, 12);
		avatarUrl = "null";
		_check_username();
		gender.add("Gender");
		gender.add("Male");
		gender.add("Female");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, gender));
		spinner1.setSelection((int)(0));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				progressbar1.setVisibility(View.GONE);
				profilePath = _filePath.get((int)(0));
				profileName = Uri.parse(profilePath).getLastPathSegment();
				avatar.child(profileName).putFile(Uri.fromFile(new File(profilePath))).addOnFailureListener(_avatar_failure_listener).addOnProgressListener(_avatar_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return avatar.child(profileName).getDownloadUrl();
					}}).addOnCompleteListener(_avatar_upload_success_listener);
				imageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(profilePath, 1024, 1024));
				l_pic.setEnabled(false);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	@Override
	public void onBackPressed() {
		
	}
	public void _pickRandomColor() {
		n = SketchwareUtil.getRandom((int)(0), (int)(7));
		if (n == 0) {
			colorCode = "#65A9E0";
		}
		if (n == 1) {
			colorCode = "#E56555";
		}
		if (n == 2) {
			colorCode = "#5FBED5";
		}
		if (n == 3) {
			colorCode = "#F2739A";
		}
		if (n == 4) {
			colorCode = "#76C84C";
		}
		if (n == 5) {
			colorCode = "#8D84EE";
		}
		if (n == 6) {
			colorCode = "#50A6E6";
		}
		if (n == 7) {
			colorCode = "#F28C48";
		}
	}
	
	
	public void _fabRotation(final boolean _start) {
		if (_start) {
			_fab.setEnabled(false);
			cancel = false;
			_fab.setImageResource(R.drawable.loading_100);
			time = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!cancel) {
								_fab.setRotation((float)(_fab.getRotation() + 10));
							}
						}
					});
				}
			};
			_timer.scheduleAtFixedRate(time, (int)(0), (int)(20));
		}
		else {
			_fab.setImageResource(R.drawable.ic_arrow_forward_white);
			time.cancel();
			cancel = true;
			_fab.setRotation((float)(0));
			_fab.setEnabled(true);
		}
	}
	
	
	public void _LengthOfEditText(final TextView _editText, final double _value_character) {
		InputFilter[] gb = _editText.getFilters(); InputFilter[] newFilters = new InputFilter[gb.length + 1]; System.arraycopy(gb, 0, newFilters, 0, gb.length); newFilters[gb.length] = new InputFilter.LengthFilter((int)_value_character); _editText.setFilters(newFilters);
	}
	
	
	public void _set_imeGo_Click(final TextView _edit, final View _view) {
		_edit.setOnEditorActionListener(new EditText.OnEditorActionListener() { public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_GO) { _view.performClick(); return true; } return false; } });
	}
	
	
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _setProgressBarColor(final ProgressBar _progressbar, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			_progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
		}
	}
	
	
	public void _setCircleImagePath(final ImageView _imageview, final String _path, final double _strokeWidth, final String _strokeColor) {
		
		//DO NOT REMOVE THIS
	}
	
	
	public void _check_username() {
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			user.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					tmp = new ArrayList<>();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							tmp.add(_map);
						}
					}
					catch (Exception _e) {
						_e.printStackTrace();
					}
					if (tmp.size() == 0) {
						_ProgresbarDimiss();
						linear_main.setVisibility(View.VISIBLE);
					}
					else {
						double _tmpNum = 0;
						boolean _isRegistered = false;
						for(int _repeat23 = 0; _repeat23 < (int)(tmp.size()); _repeat23++) {
							if (tmp.get((int)_tmpNum).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
								_isRegistered = true;
								_ProgresbarDimiss();
								i.setClass(getApplicationContext(), HomeActivity.class);
								startActivity(i);
								finish();
							}
							else {
								if ((_tmpNum == (tmp.size() - 1)) && !_isRegistered) {
									_ProgresbarDimiss();
									linear_main.setVisibility(View.VISIBLE);
								}
							}
							_tmpNum++;
						}
					}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
			});
		}
		else {
			_ProgresbarDimiss();
			i.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
		}
	}
	
	
	public void _extra() {
	} private ProgressDialog prog; {
	}
	
	
	public void _ProgresbarDimiss() {
		if(prog != null)
		{
			prog.dismiss();
		}
	}
	
	
	public void _ProgresbarShow(final String _title) {
		prog = new ProgressDialog(RegisterActivity.this);
		prog.setMax(100);
		prog.setMessage(_title);
		prog.setIndeterminate(true);
		prog.setCancelable(false);
		prog.show();
	}
	
	
	public void _normalnotification(final String _title, final String _content) {
		final Context context = getApplicationContext();
		
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent intent = new Intent(this, RegisterActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0); 
		androidx.core.app.NotificationCompat.Builder builder; 
		
		    int notificationId = 1;
		    String channelId = "channel-01";
		    String channelName = "Channel Name";
		    int importance = NotificationManager.IMPORTANCE_HIGH;
		
		    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			        NotificationChannel mChannel = new NotificationChannel(
			                channelId, channelName, importance);
			        notificationManager.createNotificationChannel(mChannel);
			    }
		
		   /*
Developer :- androidbulb
*/
		 androidx.core.app.NotificationCompat.Builder mBuilder = new androidx.core.app.NotificationCompat.Builder(context, channelId)
		            .setSmallIcon(R.drawable.img)
		            .setContentTitle(_title)
		            .setContentText(_content)
		            .setAutoCancel(true)
		            .setOngoing(false)
		            .setContentIntent(pendingIntent);
		    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		    stackBuilder.addNextIntent(intent);
		    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		    );
		    mBuilder.setContentIntent(resultPendingIntent);
		
		    notificationManager.notify(notificationId, mBuilder.build());
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