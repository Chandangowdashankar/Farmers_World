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
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.content.ClipData;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class AddChatActivity extends AppCompatActivity {
	
	public final int REQ_CD_FILE = 101;
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private String key = "";
	private String profilePath = "";
	private String profileName = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String avatarUrl = "";
	private String verification = "";
	private String name = "";
	private String profile = "";
	private boolean upload = false;
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private EditText edittext1;
	private ImageView imageview1;
	
	private StorageReference chat_pic = _firebase_storage.getReference("chat_pic");
	private OnCompleteListener<Uri> _chat_pic_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _chat_pic_download_success_listener;
	private OnSuccessListener _chat_pic_delete_success_listener;
	private OnProgressListener _chat_pic_upload_progress_listener;
	private OnProgressListener _chat_pic_download_progress_listener;
	private OnFailureListener _chat_pic_failure_listener;
	
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
	
	private DatabaseReference chat = _firebase.getReference("chat");
	private ChildEventListener _chat_child_listener;
	private Intent i = new Intent();
	private Intent file = new Intent(Intent.ACTION_GET_CONTENT);
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_chat);
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
		
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		edittext1 = findViewById(R.id.edittext1);
		imageview1 = findViewById(R.id.imageview1);
		fauth = FirebaseAuth.getInstance();
		file.setType("image/*");
		file.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		net = new RequestNetwork(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(file, REQ_CD_FILE);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				key = chat.push().getKey();
				if (profileName.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "select image");
				}
				else {
					if (edittext1.getText().toString().equals("")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Enter caption");
					}
					else {
						if (upload) {
							chat_pic.child(profileName).putFile(Uri.fromFile(new File(profilePath))).addOnFailureListener(_chat_pic_failure_listener).addOnProgressListener(_chat_pic_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return chat_pic.child(profileName).getDownloadUrl();
								}}).addOnCompleteListener(_chat_pic_upload_success_listener);
							_ProgresbarShow("Uploading post...");
						}
						else {
							map = new HashMap<>();
							map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
							map.put("caption", edittext1.getText().toString());
							map.put("key", key);
							map.put("verified", verification);
							map.put("name", name);
							map.put("profile", profile);
							chat.child(key).updateChildren(map);
							map.clear();
							finish();
						}
					}
				}
			}
		});
		
		_chat_pic_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_chat_pic_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_chat_pic_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				map = new HashMap<>();
				map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("caption", edittext1.getText().toString());
				map.put("post", _downloadUrl);
				map.put("key", key);
				map.put("verified", verification);
				map.put("name", name);
				map.put("profile", profile);
				chat.child(key).updateChildren(map);
				map.clear();
				_ProgresbarDimiss();
				finish();
			}
		};
		
		_chat_pic_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_chat_pic_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_chat_pic_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_chat_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		chat.addChildEventListener(_chat_child_listener);
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map = new HashMap<>();
				map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("caption", edittext1.getText().toString());
				map.put("post", avatarUrl);
				map.put("key", key);
				map.put("verified", verification);
				map.put("name", name);
				map.put("profile", profile);
				chat.child(key).updateChildren(map);
				map.clear();
				finish();
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					name = _childValue.get("display_name").toString();
					if (_childValue.get("verified").toString().equals("true")) {
						verification = "true";
					}
					else {
						if (_childValue.get("verified").toString().equals("false")) {
							verification = "false";
						}
					}
					if (_childValue.get("avatar").toString().equals("null")) {
						profile = "https://firebasestorage.googleapis.com/v0/b/farmers-world-df196.appspot.com/o/WhatsApp%20Image%202021-07-05%20at%208.19.27%20PM.jpeg?alt=media&token=5cb4d151-369e-4fc0-ac18-d30c3d076bdd";
					}
					else {
						profile = _childValue.get("avatar").toString();
					}
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
		_extra();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FILE:
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
				profilePath = _filePath.get((int)(0));
				profileName = Uri.parse(profilePath).getLastPathSegment();
				imageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(profilePath, 1024, 1024));
				upload = true;
			}
			else {
				upload = false;
			}
			break;
			default:
			break;
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
		prog = new ProgressDialog(AddChatActivity.this);
		prog.setMax(100);
		prog.setMessage(_title);
		prog.setIndeterminate(true);
		prog.setCancelable(false);
		prog.show();
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