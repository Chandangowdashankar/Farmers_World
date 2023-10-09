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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Context;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class LoginActivity extends AppCompatActivity {
	
	public final int REQ_CD_CAM = 101;
	
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private String data = "";
	private double n = 0;
	private boolean choosedCountry = false;
	private String zenPath = "";
	private String zenPath2 = "";
	private String zenPath3 = "";
	private String zenPath4 = "";
	private String zenPath5 = "";
	private String zenPath6 = "";
	private String get_link = "";
	private boolean cancel = false;
	private String phoneNumber = "";
	private String otp = "";
	private boolean verifieMode = false;
	private boolean codesBlank = false;
	private String getNumber = "";
	private String verificationCode = "";
	
	private ArrayList<HashMap<String, Object>> countries = new ArrayList<>();
	private ArrayList<String> dialing_codes = new ArrayList<>();
	
	private LinearLayout l_main;
	private LinearLayout linear_phone;
	private LinearLayout linear_verification;
	private TextView textview1;
	private ImageView imageview1;
	private TextView textview2;
	private LinearLayout l_country;
	private LinearLayout l_phone;
	private TextView tx_country_name;
	private LinearLayout l_bar;
	private EditText tx_country_code;
	private EditText tx_number;
	private ImageView im_logo;
	private TextView tx_exm;
	private TextView tx_attention;
	private LinearLayout linear_codes;
	private TextView tx_otp;
	private EditText tx_code1;
	private EditText tx_code2;
	private EditText tx_code3;
	private EditText tx_code4;
	private EditText tx_code5;
	private EditText tx_code6;
	
	private Intent i = new Intent();
	private RequestNetwork rq;
	private RequestNetwork.RequestListener _rq_request_listener;
	private SharedPreferences file;
	private AlertDialog.Builder alert;
	private TimerTask time;
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
	
	private Vibrator vr;
	private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_cam;
	private SharedPreferences welcome;
	private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneAuth;
	private PhoneAuthProvider.ForceResendingToken phoneAuth_resendToken;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.login);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		
		l_main = findViewById(R.id.l_main);
		linear_phone = findViewById(R.id.linear_phone);
		linear_verification = findViewById(R.id.linear_verification);
		textview1 = findViewById(R.id.textview1);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
		l_country = findViewById(R.id.l_country);
		l_phone = findViewById(R.id.l_phone);
		tx_country_name = findViewById(R.id.tx_country_name);
		l_bar = findViewById(R.id.l_bar);
		tx_country_code = findViewById(R.id.tx_country_code);
		tx_number = findViewById(R.id.tx_number);
		im_logo = findViewById(R.id.im_logo);
		tx_exm = findViewById(R.id.tx_exm);
		tx_attention = findViewById(R.id.tx_attention);
		linear_codes = findViewById(R.id.linear_codes);
		tx_otp = findViewById(R.id.tx_otp);
		tx_code1 = findViewById(R.id.tx_code1);
		tx_code2 = findViewById(R.id.tx_code2);
		tx_code3 = findViewById(R.id.tx_code3);
		tx_code4 = findViewById(R.id.tx_code4);
		tx_code5 = findViewById(R.id.tx_code5);
		tx_code6 = findViewById(R.id.tx_code6);
		rq = new RequestNetwork(this);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		alert = new AlertDialog.Builder(this);
		auth = FirebaseAuth.getInstance();
		vr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		_file_cam = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_cam;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_cam = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_cam);
		} else {
			_uri_cam = Uri.fromFile(_file_cam);
		}
		cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
		cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		welcome = getSharedPreferences("welcome", Activity.MODE_PRIVATE);
		
		l_country.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), CountryActivity.class);
				startActivity(i);
			}
		});
		
		tx_otp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				getNumber = tx_country_code.getText().toString().concat(tx_number.getText().toString());
				PhoneAuthProvider.getInstance().verifyPhoneNumber(getNumber, 60, TimeUnit.SECONDS, LoginActivity.this, phoneAuth, phoneAuth_resendToken);
			}
		});
		
		tx_code1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!_charSeq.equals("")) {
					tx_code2.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		tx_code2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("")) {
					tx_code1.requestFocus();
				}
				else {
					tx_code3.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		tx_code3.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("")) {
					tx_code2.requestFocus();
				}
				else {
					tx_code4.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		tx_code4.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("")) {
					tx_code3.requestFocus();
				}
				else {
					tx_code5.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		tx_code5.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("")) {
					tx_code4.requestFocus();
				}
				else {
					tx_code6.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		tx_code6.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!_charSeq.equals("")) {
					tx_code2.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (verifieMode) {
					codesBlank = tx_code1.getText().toString().equals("") || (tx_code2.getText().toString().equals("") || (tx_code3.getText().toString().equals("") || (tx_code4.getText().toString().equals("") || (tx_code5.getText().toString().equals("") || tx_code6.getText().toString().equals("")))));
					if (codesBlank) {
						vr.vibrate((long)(300));
					}
					else {
						otp = tx_code1.getText().toString().concat(tx_code2.getText().toString().concat(tx_code3.getText().toString().concat(tx_code4.getText().toString().concat(tx_code5.getText().toString().concat(tx_code6.getText().toString())))));
						FirebaseAuth.getInstance().signInWithCredential(PhoneAuthProvider.getCredential(verificationCode, otp)).addOnCompleteListener(auth_phoneAuthListener);
						_fabRotation(true);
					}
				}
				else {
					if (!tx_country_code.getText().toString().equals("+00")) {
						if (tx_number.getText().toString().equals("")) {
							vr.vibrate((long)(300));
						}
						else {
							phoneNumber = tx_country_code.getText().toString().concat(tx_number.getText().toString());
							PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, LoginActivity.this, phoneAuth);
							_fabRotation(true);
						}
					}
					else {
						vr.vibrate((long)(300));
					}
				}
			}
		});
		
		_rq_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (!choosedCountry) {
					tx_country_code.setText(_response);
					if (dialing_codes.contains(_response)) {
						tx_country_name.setText(countries.get((int)dialing_codes.indexOf(_response)).get("name").toString());
					}
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				_getCountryCode();
			}
		};
		
		phoneAuth = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(PhoneAuthCredential _credential) {
				FirebaseAuth.getInstance().signInWithCredential(_credential).addOnCompleteListener(auth_phoneAuthListener);
			}
			
			@Override
			public void onVerificationFailed(FirebaseException e) {
				final String _exception = e.getMessage();
				SketchwareUtil.CustomToast(getApplicationContext(), _exception, 0xFFFFFFFF, 12, 0xFF424242, 60, SketchwareUtil.BOTTOM);
				_fabRotation(false);
			}
			
			@Override
			public void onCodeSent(String _verificationId, PhoneAuthProvider.ForceResendingToken _token) {
				verificationCode = _verificationId;
				phoneAuth_resendToken = _token;
				_setVerifyMode();
				_fabRotation(false);
				SketchwareUtil.CustomToast(getApplicationContext(), "Code Sent.", 0xFFFFFFFF, 12, 0xFF424242, 60, SketchwareUtil.BOTTOM);
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
				if (_success) {
					file.edit().putString("number", phoneNumber).commit();
					i.setClass(getApplicationContext(), RegisterActivity.class);
					startActivity(i);
					finish();
				}
				else {
					if (_errorMessage.equals("The sms verification code used to create the phone auth credential is invalid. Please resend the verification code sms and be sure use the verification code provided by the user.")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Invalid verification code.");
					}
					else {
						SketchwareUtil.CustomToast(getApplicationContext(), _errorMessage, 0xFFFFFFFF, 12, 0xFF424242, 60, SketchwareUtil.BOTTOM);
					}
				}
				_fabRotation(false);
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
		com.google.android.material.appbar.AppBarLayout appBar =
		    (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
		appBar.setElevation(3f);
		appBar.setStateListAnimator(null);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		tx_number.requestFocus();
		_rippleEffect(l_country);
		_getCountryCode();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		choosedCountry = false;
		_LenghtCode();
		_set_imeGo_Click(tx_number, _fab);
		_set_imeGo_Click(tx_code6, _fab);
		zenPath = FileUtil.getExternalStorageDir().concat("/zen/status/");
		if (!FileUtil.isExistFile(zenPath)) {
			FileUtil.makeDir(zenPath);
		}
		zenPath2 = FileUtil.getExternalStorageDir().concat("/zen/");
		if (!FileUtil.isExistFile(zenPath2)) {
			FileUtil.makeDir(zenPath2);
		}
		zenPath3 = FileUtil.getExternalStorageDir().concat("/zen/audios/");
		if (!FileUtil.isExistFile(zenPath3)) {
			FileUtil.makeDir(zenPath3);
		}
		zenPath4 = FileUtil.getExternalStorageDir().concat("/zen/documents/");
		if (!FileUtil.isExistFile(zenPath4)) {
			FileUtil.makeDir(zenPath4);
		}
		zenPath5 = FileUtil.getExternalStorageDir().concat("/zen/images/");
		if (!FileUtil.isExistFile(zenPath5)) {
			FileUtil.makeDir(zenPath5);
		}
		zenPath6 = FileUtil.getExternalStorageDir().concat("/zen/videos/");
		if (!FileUtil.isExistFile(zenPath6)) {
			FileUtil.makeDir(zenPath6);
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		if (file.getString("name", "").equals("") || file.getString("code", "").equals("")) {
			choosedCountry = false;
		}
		else {
			choosedCountry = true;
			tx_country_name.setText(file.getString("name", ""));
			tx_country_code.setText(file.getString("code", ""));
		}
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			try{ Intent intent = getIntent(); Uri data = intent.getData();
				get_link = data.toString().replace("http://zenapp.me/", "");
				i.putExtra("link", get_link);
				i.setClass(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
			} catch (Exception e){
				i.putExtra("link", "null");
				i.setClass(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	public void _checkSelfPermission() {
		/*

*/
		try {
					if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
					|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
					|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
					|| ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
				SketchwareUtil.showMessage(getApplicationContext(), "Permission danied!");
				finishAffinity();
			} else {
				if (welcome.getString("welcome", "").equals("")) {
					i.setClass(getApplicationContext(), LoginActivity.class);
					startActivity(i);
				}
			}
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), e.toString());
		}
	}
	
	
	public void _LenghtCode() {
		_LengthOfEditText(tx_code1, 1);
		_LengthOfEditText(tx_code2, 1);
		_LengthOfEditText(tx_code3, 1);
		_LengthOfEditText(tx_code4, 1);
		_LengthOfEditText(tx_code5, 1);
		_LengthOfEditText(tx_code6, 1);
	}
	
	
	public void _getCountryCode() {
		data = "[{\"name\":\"Afghanistan\",\"dial_code\":\"+93\",\"code\":\"AF\"},{\"name\":\"Albania\",\"dial_code\":\"+355\",\"code\":\"AL\"},{\"name\":\"Algeria\",\"dial_code\":\"+213\",\"code\":\"DZ\"},{\"name\":\"AmericanSamoa\",\"dial_code\":\"+1 684\",\"code\":\"AS\"},{\"name\":\"Andorra\",\"dial_code\":\"+376\",\"code\":\"AD\"},{\"name\":\"Angola\",\"dial_code\":\"+244\",\"code\":\"AO\"},{\"name\":\"Anguilla\",\"dial_code\":\"+1 264\",\"code\":\"AI\"},{\"name\":\"Antarctica\",\"dial_code\":\"+672\",\"code\":\"AQ\"},{\"name\":\"Antigua and Barbuda\",\"dial_code\":\"+1268\",\"code\":\"AG\"},{\"name\":\"Argentina\",\"dial_code\":\"+54\",\"code\":\"AR\"},{\"name\":\"Armenia\",\"dial_code\":\"+374\",\"code\":\"AM\"},{\"name\":\"Aruba\",\"dial_code\":\"+297\",\"code\":\"AW\"},{\"name\":\"Australia\",\"dial_code\":\"+61\",\"code\":\"AU\"},{\"name\":\"Austria\",\"dial_code\":\"+43\",\"code\":\"AT\"},{\"name\":\"Azerbaijan\",\"dial_code\":\"+994\",\"code\":\"AZ\"},{\"name\":\"Bahamas\",\"dial_code\":\"+1 242\",\"code\":\"BS\"},{\"name\":\"Bahrain\",\"dial_code\":\"+973\",\"code\":\"BH\"},{\"name\":\"Bangladesh\",\"dial_code\":\"+880\",\"code\":\"BD\"},{\"name\":\"Barbados\",\"dial_code\":\"+1 246\",\"code\":\"BB\"},{\"name\":\"Belarus\",\"dial_code\":\"+375\",\"code\":\"BY\"},{\"name\":\"Belgium\",\"dial_code\":\"+32\",\"code\":\"BE\"},{\"name\":\"Belize\",\"dial_code\":\"+501\",\"code\":\"BZ\"},{\"name\":\"Benin\",\"dial_code\":\"+229\",\"code\":\"BJ\"},{\"name\":\"Bermuda\",\"dial_code\":\"+1 441\",\"code\":\"BM\"},{\"name\":\"Bhutan\",\"dial_code\":\"+975\",\"code\":\"BT\"},{\"name\":\"Bolivia, Plurinational State of\",\"dial_code\":\"+591\",\"code\":\"BO\"},{\"name\":\"Bosnia and Herzegovina\",\"dial_code\":\"+387\",\"code\":\"BA\"},{\"name\":\"Botswana\",\"dial_code\":\"+267\",\"code\":\"BW\"},{\"name\":\"Brazil\",\"dial_code\":\"+55\",\"code\":\"BR\"},{\"name\":\"British Indian Ocean Territory\",\"dial_code\":\"+246\",\"code\":\"IO\"},{\"name\":\"Brunei Darussalam\",\"dial_code\":\"+673\",\"code\":\"BN\"},{\"name\":\"Bulgaria\",\"dial_code\":\"+359\",\"code\":\"BG\"},{\"name\":\"Burkina Faso\",\"dial_code\":\"+226\",\"code\":\"BF\"},{\"name\":\"Burundi\",\"dial_code\":\"+257\",\"code\":\"BI\"},{\"name\":\"Cambodia\",\"dial_code\":\"+855\",\"code\":\"KH\"},{\"name\":\"Cameroon\",\"dial_code\":\"+237\",\"code\":\"CM\"},{\"name\":\"Canada\",\"dial_code\":\"+1\",\"code\":\"CA\"},{\"name\":\"Cape Verde\",\"dial_code\":\"+238\",\"code\":\"CV\"},{\"name\":\"Cayman Islands\",\"dial_code\":\"+ 345\",\"code\":\"KY\"},{\"name\":\"Central African Republic\",\"dial_code\":\"+236\",\"code\":\"CF\"},{\"name\":\"Chad\",\"dial_code\":\"+235\",\"code\":\"TD\"},{\"name\":\"Chile\",\"dial_code\":\"+56\",\"code\":\"CL\"},{\"name\":\"China\",\"dial_code\":\"+86\",\"code\":\"CN\"},{\"name\":\"Christmas Island\",\"dial_code\":\"+61\",\"code\":\"CX\"},{\"name\":\"Cocos (Keeling) Islands\",\"dial_code\":\"+61\",\"code\":\"CC\"},{\"name\":\"Colombia\",\"dial_code\":\"+57\",\"code\":\"CO\"},{\"name\":\"Comoros\",\"dial_code\":\"+269\",\"code\":\"KM\"},{\"name\":\"Congo\",\"dial_code\":\"+242\",\"code\":\"CG\"},{\"name\":\"Congo, The Democratic Republic of the\",\"dial_code\":\"+243\",\"code\":\"CD\"},{\"name\":\"Cook Islands\",\"dial_code\":\"+682\",\"code\":\"CK\"},{\"name\":\"Costa Rica\",\"dial_code\":\"+506\",\"code\":\"CR\"},{\"name\":\"Cote d'Ivoire\",\"dial_code\":\"+225\",\"code\":\"CI\"},{\"name\":\"Croatia\",\"dial_code\":\"+385\",\"code\":\"HR\"},{\"name\":\"Cuba\",\"dial_code\":\"+53\",\"code\":\"CU\"},{\"name\":\"Cyprus\",\"dial_code\":\"+537\",\"code\":\"CY\"},{\"name\":\"Czech Republic\",\"dial_code\":\"+420\",\"code\":\"CZ\"},{\"name\":\"Denmark\",\"dial_code\":\"+45\",\"code\":\"DK\"},{\"name\":\"Djibouti\",\"dial_code\":\"+253\",\"code\":\"DJ\"},{\"name\":\"Dominica\",\"dial_code\":\"+1 767\",\"code\":\"DM\"},{\"name\":\"Dominican Republic\",\"dial_code\":\"+1 849\",\"code\":\"DO\"},{\"name\":\"Ecuador\",\"dial_code\":\"+593\",\"code\":\"EC\"},{\"name\":\"Egypt\",\"dial_code\":\"+20\",\"code\":\"EG\"},{\"name\":\"El Salvador\",\"dial_code\":\"+503\",\"code\":\"SV\"},{\"name\":\"Equatorial Guinea\",\"dial_code\":\"+240\",\"code\":\"GQ\"},{\"name\":\"Eritrea\",\"dial_code\":\"+291\",\"code\":\"ER\"},{\"name\":\"Estonia\",\"dial_code\":\"+372\",\"code\":\"EE\"},{\"name\":\"Ethiopia\",\"dial_code\":\"+251\",\"code\":\"ET\"},{\"name\":\"Falkland Islands (Malvinas)\",\"dial_code\":\"+500\",\"code\":\"FK\"},{\"name\":\"Faroe Islands\",\"dial_code\":\"+298\",\"code\":\"FO\"},{\"name\":\"Fiji\",\"dial_code\":\"+679\",\"code\":\"FJ\"},{\"name\":\"Finland\",\"dial_code\":\"+358\",\"code\":\"FI\"},{\"name\":\"France\",\"dial_code\":\"+33\",\"code\":\"FR\"},{\"name\":\"French Guiana\",\"dial_code\":\"+594\",\"code\":\"GF\"},{\"name\":\"French Polynesia\",\"dial_code\":\"+689\",\"code\":\"PF\"},{\"name\":\"Gabon\",\"dial_code\":\"+241\",\"code\":\"GA\"},{\"name\":\"Gambia\",\"dial_code\":\"+220\",\"code\":\"GM\"},{\"name\":\"Georgia\",\"dial_code\":\"+995\",\"code\":\"GE\"},{\"name\":\"Germany\",\"dial_code\":\"+49\",\"code\":\"DE\"},{\"name\":\"Ghana\",\"dial_code\":\"+233\",\"code\":\"GH\"},{\"name\":\"Gibraltar\",\"dial_code\":\"+350\",\"code\":\"GI\"},{\"name\":\"Greece\",\"dial_code\":\"+30\",\"code\":\"GR\"},{\"name\":\"Greenland\",\"dial_code\":\"+299\",\"code\":\"GL\"},{\"name\":\"Grenada\",\"dial_code\":\"+1 473\",\"code\":\"GD\"},{\"name\":\"Guadeloupe\",\"dial_code\":\"+590\",\"code\":\"GP\"},{\"name\":\"Guam\",\"dial_code\":\"+1 671\",\"code\":\"GU\"},{\"name\":\"Guatemala\",\"dial_code\":\"+502\",\"code\":\"GT\"},{\"name\":\"Guernsey\",\"dial_code\":\"+44\",\"code\":\"GG\"},{\"name\":\"Guinea\",\"dial_code\":\"+224\",\"code\":\"GN\"},{\"name\":\"Guinea-Bissau\",\"dial_code\":\"+245\",\"code\":\"GW\"},{\"name\":\"Guyana\",\"dial_code\":\"+595\",\"code\":\"GY\"},{\"name\":\"Haiti\",\"dial_code\":\"+509\",\"code\":\"HT\"},{\"name\":\"Holy See (Vatican City State)\",\"dial_code\":\"+379\",\"code\":\"VA\"},{\"name\":\"Honduras\",\"dial_code\":\"+504\",\"code\":\"HN\"},{\"name\":\"Hong Kong\",\"dial_code\":\"+852\",\"code\":\"HK\"},{\"name\":\"Hungary\",\"dial_code\":\"+36\",\"code\":\"HU\"},{\"name\":\"Iceland\",\"dial_code\":\"+354\",\"code\":\"IS\"},{\"name\":\"India\",\"dial_code\":\"+91\",\"code\":\"IN\"},{\"name\":\"Indonesia\",\"dial_code\":\"+62\",\"code\":\"ID\"},{\"name\":\"Iran, Islamic Republic of\",\"dial_code\":\"+98\",\"code\":\"IR\"},{\"name\":\"Iraq\",\"dial_code\":\"+964\",\"code\":\"IQ\"},{\"name\":\"Ireland\",\"dial_code\":\"+353\",\"code\":\"IE\"},{\"name\":\"Isle of Man\",\"dial_code\":\"+44\",\"code\":\"IM\"},{\"name\":\"Israel\",\"dial_code\":\"+972\",\"code\":\"IL\"},{\"name\":\"Italy\",\"dial_code\":\"+39\",\"code\":\"IT\"},{\"name\":\"Jamaica\",\"dial_code\":\"+1 876\",\"code\":\"JM\"},{\"name\":\"Japan\",\"dial_code\":\"+81\",\"code\":\"JP\"},{\"name\":\"Jersey\",\"dial_code\":\"+44\",\"code\":\"JE\"},{\"name\":\"Jordan\",\"dial_code\":\"+962\",\"code\":\"JO\"},{\"name\":\"Kazakhstan\",\"dial_code\":\"+7 7\",\"code\":\"KZ\"},{\"name\":\"Kenya\",\"dial_code\":\"+254\",\"code\":\"KE\"},{\"name\":\"Kiribati\",\"dial_code\":\"+686\",\"code\":\"KI\"},{\"name\":\"Korea, Democratic People's Republic of\",\"dial_code\":\"+850\",\"code\":\"KP\"},{\"name\":\"Korea, Republic of\",\"dial_code\":\"+82\",\"code\":\"KR\"},{\"name\":\"Kuwait\",\"dial_code\":\"+965\",\"code\":\"KW\"},{\"name\":\"Kyrgyzstan\",\"dial_code\":\"+996\",\"code\":\"KG\"},{\"name\":\"Lao People's Democratic Republic\",\"dial_code\":\"+856\",\"code\":\"LA\"},{\"name\":\"Latvia\",\"dial_code\":\"+371\",\"code\":\"LV\"},{\"name\":\"Lebanon\",\"dial_code\":\"+961\",\"code\":\"LB\"},{\"name\":\"Lesotho\",\"dial_code\":\"+266\",\"code\":\"LS\"},{\"name\":\"Liberia\",\"dial_code\":\"+231\",\"code\":\"LR\"},{\"name\":\"Libyan Arab Jamahiriya\",\"dial_code\":\"+218\",\"code\":\"LY\"},{\"name\":\"Liechtenstein\",\"dial_code\":\"+423\",\"code\":\"LI\"},{\"name\":\"Lithuania\",\"dial_code\":\"+370\",\"code\":\"LT\"},{\"name\":\"Luxembourg\",\"dial_code\":\"+352\",\"code\":\"LU\"},{\"name\":\"Macao\",\"dial_code\":\"+853\",\"code\":\"MO\"},{\"name\":\"Macedonia, The Former Yugoslav Republic of\",\"dial_code\":\"+389\",\"code\":\"MK\"},{\"name\":\"Madagascar\",\"dial_code\":\"+261\",\"code\":\"MG\"},{\"name\":\"Malawi\",\"dial_code\":\"+265\",\"code\":\"MW\"},{\"name\":\"Malaysia\",\"dial_code\":\"+60\",\"code\":\"MY\"},{\"name\":\"Maldives\",\"dial_code\":\"+960\",\"code\":\"MV\"},{\"name\":\"Mali\",\"dial_code\":\"+223\",\"code\":\"ML\"},{\"name\":\"Malta\",\"dial_code\":\"+356\",\"code\":\"MT\"},{\"name\":\"Marshall Islands\",\"dial_code\":\"+692\",\"code\":\"MH\"},{\"name\":\"Martinique\",\"dial_code\":\"+596\",\"code\":\"MQ\"},{\"name\":\"Mauritania\",\"dial_code\":\"+222\",\"code\":\"MR\"},{\"name\":\"Mauritius\",\"dial_code\":\"+230\",\"code\":\"MU\"},{\"name\":\"Mayotte\",\"dial_code\":\"+262\",\"code\":\"YT\"},{\"name\":\"Mexico\",\"dial_code\":\"+52\",\"code\":\"MX\"},{\"name\":\"Micronesia, Federated States of\",\"dial_code\":\"+691\",\"code\":\"FM\"},{\"name\":\"Moldova, Republic of\",\"dial_code\":\"+373\",\"code\":\"MD\"},{\"name\":\"Monaco\",\"dial_code\":\"+377\",\"code\":\"MC\"},{\"name\":\"Mongolia\",\"dial_code\":\"+976\",\"code\":\"MN\"},{\"name\":\"Montenegro\",\"dial_code\":\"+382\",\"code\":\"ME\"},{\"name\":\"Montserrat\",\"dial_code\":\"+1664\",\"code\":\"MS\"},{\"name\":\"Morocco\",\"dial_code\":\"+212\",\"code\":\"MA\"},{\"name\":\"Mozambique\",\"dial_code\":\"+258\",\"code\":\"MZ\"},{\"name\":\"Myanmar\",\"dial_code\":\"+95\",\"code\":\"MM\"},{\"name\":\"Namibia\",\"dial_code\":\"+264\",\"code\":\"NA\"},{\"name\":\"Nauru\",\"dial_code\":\"+674\",\"code\":\"NR\"},{\"name\":\"Nepal\",\"dial_code\":\"+977\",\"code\":\"NP\"},{\"name\":\"Netherlands\",\"dial_code\":\"+31\",\"code\":\"NL\"},{\"name\":\"Netherlands Antilles\",\"dial_code\":\"+599\",\"code\":\"AN\"},{\"name\":\"New Caledonia\",\"dial_code\":\"+687\",\"code\":\"NC\"},{\"name\":\"New Zealand\",\"dial_code\":\"+64\",\"code\":\"NZ\"},{\"name\":\"Nicaragua\",\"dial_code\":\"+505\",\"code\":\"NI\"},{\"name\":\"Niger\",\"dial_code\":\"+227\",\"code\":\"NE\"},{\"name\":\"Nigeria\",\"dial_code\":\"+234\",\"code\":\"NG\"},{\"name\":\"Niue\",\"dial_code\":\"+683\",\"code\":\"NU\"},{\"name\":\"Norfolk Island\",\"dial_code\":\"+672\",\"code\":\"NF\"},{\"name\":\"Northern Mariana Islands\",\"dial_code\":\"+1 670\",\"code\":\"MP\"},{\"name\":\"Norway\",\"dial_code\":\"+47\",\"code\":\"NO\"},{\"name\":\"Oman\",\"dial_code\":\"+968\",\"code\":\"OM\"},{\"name\":\"Pakistan\",\"dial_code\":\"+92\",\"code\":\"PK\"},{\"name\":\"Palau\",\"dial_code\":\"+680\",\"code\":\"PW\"},{\"name\":\"Palestinian Territory, Occupied\",\"dial_code\":\"+970\",\"code\":\"PS\"},{\"name\":\"Panama\",\"dial_code\":\"+507\",\"code\":\"PA\"},{\"name\":\"Papua New Guinea\",\"dial_code\":\"+675\",\"code\":\"PG\"},{\"name\":\"Paraguay\",\"dial_code\":\"+595\",\"code\":\"PY\"},{\"name\":\"Peru\",\"dial_code\":\"+51\",\"code\":\"PE\"},{\"name\":\"Philippines\",\"dial_code\":\"+63\",\"code\":\"PH\"},{\"name\":\"Pitcairn\",\"dial_code\":\"+872\",\"code\":\"PN\"},{\"name\":\"Poland\",\"dial_code\":\"+48\",\"code\":\"PL\"},{\"name\":\"Portugal\",\"dial_code\":\"+351\",\"code\":\"PT\"},{\"name\":\"Puerto Rico\",\"dial_code\":\"+1 939\",\"code\":\"PR\"},{\"name\":\"Qatar\",\"dial_code\":\"+974\",\"code\":\"QA\"},{\"name\":\"Romania\",\"dial_code\":\"+40\",\"code\":\"RO\"},{\"name\":\"Russia\",\"dial_code\":\"+7\",\"code\":\"RU\"},{\"name\":\"Rwanda\",\"dial_code\":\"+250\",\"code\":\"RW\"},{\"name\":\"Réunion\",\"dial_code\":\"+262\",\"code\":\"RE\"},{\"name\":\"Saint Barthélemy\",\"dial_code\":\"+590\",\"code\":\"BL\"},{\"name\":\"Saint Helena, Ascension and Tristan Da Cunha\",\"dial_code\":\"+290\",\"code\":\"SH\"},{\"name\":\"Saint Kitts and Nevis\",\"dial_code\":\"+1 869\",\"code\":\"KN\"},{\"name\":\"Saint Lucia\",\"dial_code\":\"+1 758\",\"code\":\"LC\"},{\"name\":\"Saint Martin\",\"dial_code\":\"+590\",\"code\":\"MF\"},{\"name\":\"Saint Pierre and Miquelon\",\"dial_code\":\"+508\",\"code\":\"PM\"},{\"name\":\"Saint Vincent and the Grenadines\",\"dial_code\":\"+1 784\",\"code\":\"VC\"},{\"name\":\"Samoa\",\"dial_code\":\"+685\",\"code\":\"WS\"},{\"name\":\"San Marino\",\"dial_code\":\"+378\",\"code\":\"SM\"},{\"name\":\"Sao Tome and Principe\",\"dial_code\":\"+239\",\"code\":\"ST\"},{\"name\":\"Saudi Arabia\",\"dial_code\":\"+966\",\"code\":\"SA\"},{\"name\":\"Senegal\",\"dial_code\":\"+221\",\"code\":\"SN\"},{\"name\":\"Serbia\",\"dial_code\":\"+381\",\"code\":\"RS\"},{\"name\":\"Seychelles\",\"dial_code\":\"+248\",\"code\":\"SC\"},{\"name\":\"Sierra Leone\",\"dial_code\":\"+232\",\"code\":\"SL\"},{\"name\":\"Singapore\",\"dial_code\":\"+65\",\"code\":\"SG\"},{\"name\":\"Slovakia\",\"dial_code\":\"+421\",\"code\":\"SK\"},{\"name\":\"Slovenia\",\"dial_code\":\"+386\",\"code\":\"SI\"},{\"name\":\"Solomon Islands\",\"dial_code\":\"+677\",\"code\":\"SB\"},{\"name\":\"Somalia\",\"dial_code\":\"+252\",\"code\":\"SO\"},{\"name\":\"South Africa\",\"dial_code\":\"+27\",\"code\":\"ZA\"},{\"name\":\"South Georgia and the South Sandwich Islands\",\"dial_code\":\"+500\",\"code\":\"GS\"},{\"name\":\"Spain\",\"dial_code\":\"+34\",\"code\":\"ES\"},{\"name\":\"Sri Lanka\",\"dial_code\":\"+94\",\"code\":\"LK\"},{\"name\":\"Sudan\",\"dial_code\":\"+249\",\"code\":\"SD\"},{\"name\":\"Suriname\",\"dial_code\":\"+597\",\"code\":\"SR\"},{\"name\":\"Svalbard and Jan Mayen\",\"dial_code\":\"+47\",\"code\":\"SJ\"},{\"name\":\"Swaziland\",\"dial_code\":\"+268\",\"code\":\"SZ\"},{\"name\":\"Sweden\",\"dial_code\":\"+46\",\"code\":\"SE\"},{\"name\":\"Switzerland\",\"dial_code\":\"+41\",\"code\":\"CH\"},{\"name\":\"Syrian Arab Republic\",\"dial_code\":\"+963\",\"code\":\"SY\"},{\"name\":\"Taiwan, Province of China\",\"dial_code\":\"+886\",\"code\":\"TW\"},{\"name\":\"Tajikistan\",\"dial_code\":\"+992\",\"code\":\"TJ\"},{\"name\":\"Tanzania, United Republic of\",\"dial_code\":\"+255\",\"code\":\"TZ\"},{\"name\":\"Thailand\",\"dial_code\":\"+66\",\"code\":\"TH\"},{\"name\":\"Timor-Leste\",\"dial_code\":\"+670\",\"code\":\"TL\"},{\"name\":\"Togo\",\"dial_code\":\"+228\",\"code\":\"TG\"},{\"name\":\"Tokelau\",\"dial_code\":\"+690\",\"code\":\"TK\"},{\"name\":\"Tonga\",\"dial_code\":\"+676\",\"code\":\"TO\"},{\"name\":\"Trinidad and Tobago\",\"dial_code\":\"+1 868\",\"code\":\"TT\"},{\"name\":\"Tunisia\",\"dial_code\":\"+216\",\"code\":\"TN\"},{\"name\":\"Turkey\",\"dial_code\":\"+90\",\"code\":\"TR\"},{\"name\":\"Turkmenistan\",\"dial_code\":\"+993\",\"code\":\"TM\"},{\"name\":\"Turks and Caicos Islands\",\"dial_code\":\"+1 649\",\"code\":\"TC\"},{\"name\":\"Tuvalu\",\"dial_code\":\"+688\",\"code\":\"TV\"},{\"name\":\"Uganda\",\"dial_code\":\"+256\",\"code\":\"UG\"},{\"name\":\"Ukraine\",\"dial_code\":\"+380\",\"code\":\"UA\"},{\"name\":\"United Arab Emirates\",\"dial_code\":\"+971\",\"code\":\"AE\"},{\"name\":\"United Kingdom\",\"dial_code\":\"+44\",\"code\":\"GB\"},{\"name\":\"United States\",\"dial_code\":\"+1\",\"code\":\"US\"},{\"name\":\"Uruguay\",\"dial_code\":\"+598\",\"code\":\"UY\"},{\"name\":\"Uzbekistan\",\"dial_code\":\"+998\",\"code\":\"UZ\"},{\"name\":\"Vanuatu\",\"dial_code\":\"+678\",\"code\":\"VU\"},{\"name\":\"Venezuela, Bolivarian Republic of\",\"dial_code\":\"+58\",\"code\":\"VE\"},{\"name\":\"Viet Nam\",\"dial_code\":\"+84\",\"code\":\"VN\"},{\"name\":\"Virgin Islands, British\",\"dial_code\":\"+1 284\",\"code\":\"VG\"},{\"name\":\"Virgin Islands, U.S.\",\"dial_code\":\"+1 340\",\"code\":\"VI\"},{\"name\":\"Wallis and Futuna\",\"dial_code\":\"+681\",\"code\":\"WF\"},{\"name\":\"Yemen\",\"dial_code\":\"+967\",\"code\":\"YE\"},{\"name\":\"Zambia\",\"dial_code\":\"+260\",\"code\":\"ZM\"},{\"name\":\"Zimbabwe\",\"dial_code\":\"+263\",\"code\":\"ZW\"},{\"name\":\"Åland Islands\",\"dial_code\":\"+358\",\"code\":\"AX\"}]";
		countries = new Gson().fromJson(data, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		n = 0;
		for(int _repeat14 = 0; _repeat14 < (int)(countries.size()); _repeat14++) {
			dialing_codes.add(countries.get((int)n).get("dial_code").toString());
			n++;
		}
		rq.startRequestNetwork(RequestNetworkController.GET, "https://ipapi.co/country_calling_code", "Calling_Code", _rq_request_listener);
	}
	
	
	public void _rippleEffect(final View _view) {
		_view.setBackground(CircleDrawables.getSelectableDrawableFor(Color.parseColor("#EEEEEE")));
		
		_view.setClickable(true);
		
		
	}
	
	public static class Drawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[8];
			        Arrays.fill(outerRadii, 0);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
			    }
	}
	public static class CircleDrawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[180];
			/*YOU CAN CHANGE OUTERRADII TO CHANGE THE ROUNDNESS OF EFFECT*/
			        Arrays.fill(outerRadii, 10);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
		}
	}
	
	
	public void _set_imeGo_Click(final TextView _edit, final View _view) {
		_edit.setOnEditorActionListener(new EditText.OnEditorActionListener() { public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_GO) { _view.performClick(); return true; } return false; } });
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
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _setVerifyMode() {
		linear_phone.setVisibility(View.GONE);
		linear_verification.setVisibility(View.VISIBLE);
		tx_code1.requestFocus();
		setTitle("Verify Phone Number");
		_TransitionManager(l_main, 300);
		verifieMode = true;
		tx_attention.setText("We’ve sent an SMS with an activation code to your phone ".concat(phoneNumber.concat(".")));
	}
	
	
	public void _LengthOfEditText(final TextView _editText, final double _value_character) {
		InputFilter[] gb = _editText.getFilters(); InputFilter[] newFilters = new InputFilter[gb.length + 1]; System.arraycopy(gb, 0, newFilters, 0, gb.length); newFilters[gb.length] = new InputFilter.LengthFilter((int)_value_character); _editText.setFilters(newFilters);
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