package com.farmers.world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
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
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AboutUsActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double n = 0;
	private boolean truee = false;
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private ViewPager viewpager1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about_us);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
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
		viewpager1 = findViewById(R.id.viewpager1);
	}
	
	private void initializeLogic() {
		n = 0;
		truee = true;
		File file = new File(getCacheDir(), "project synopsis3.pdf"); if (!file.exists()) { try { InputStream asset = getAssets().open("project synopsis3.pdf"); FileOutputStream output = null; output = new FileOutputStream(file); final byte[] buffer = new byte[1024]; int size; while ((size = asset.read(buffer)) != -1) { output.write(buffer, 0, size); } asset.close(); output.close(); } catch (IOException e) { e.printStackTrace(); } }
		try {
				renderer = new android.graphics.pdf.PdfRenderer(new ParcelFileDescriptor(ParcelFileDescriptor.open(new java.io.File(file.getAbsolutePath()), ParcelFileDescriptor.MODE_READ_ONLY)));
			for(int _repeat15 = 0; _repeat15 < (int)(renderer.getPageCount()); _repeat15++) {
				n++;
				{
					HashMap<String, Object> _item = new HashMap<>();
					_item.put("pdf", String.valueOf((long)(n)));
					listmap.add(_item);
				}
				
				viewpager1.setAdapter(new Viewpager1Adapter(listmap));
				viewpager1.setCurrentItem((int)0);
			}
		} catch (Exception e){
				  
		}
	}
	
	public void _pdf() {
	}
	private
	android.graphics.pdf.PdfRenderer
	renderer;
	ZoomableImageView touch;
	public class ZoomableImageView extends ImageView {
		
		
		Matrix matrix = new Matrix();
		
		static final int NONE = 0;
		
		static final int DRAG = 1;
		
		static final int ZOOM = 2;
		
		static final int CLICK = 3;
		
		int mode = NONE;
		
		PointF last = new PointF();
		
		PointF start = new PointF();
		
		float minScale = 1f;
		
		float maxScale = 4f;
		
		float[] m;
		
		float redundantXSpace, redundantYSpace;
		
		float width, height;
		
		float saveScale = 1f;
		
		float right, bottom, origWidth, origHeight, bmWidth, bmHeight;
		
		ScaleGestureDetector mScaleDetector;
		
		Context context;
		
		
		public ZoomableImageView(Context context) {
			
			super(context);
			
			super.setClickable(true);
			
			this.context = context;
			
			mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
			
			matrix.setTranslate(1f, 1f);
			
			m = new float[9];
			
			setImageMatrix(matrix); setScaleType(ScaleType.MATRIX);
			
			
			setOnTouchListener(new OnTouchListener() {
				
				@Override
				
				public boolean onTouch(View v, MotionEvent event) {
					
					mScaleDetector.onTouchEvent(event);
					
					matrix.getValues(m);
					
					float x = m[Matrix.MTRANS_X];
					
					float y = m[Matrix.MTRANS_Y];
					
					PointF curr = new PointF(event.getX(), event.getY());
					
					switch (event.getAction()) {
						
						case MotionEvent.ACTION_DOWN: last.set(event.getX(), event.getY()); start.set(last); mode = DRAG;
						
						break;
						
						case MotionEvent.ACTION_POINTER_DOWN: last.set(event.getX(), event.getY()); start.set(last);
						
						mode = ZOOM;
						
						break;
						
						case MotionEvent.ACTION_MOVE:
						
						if (mode == ZOOM || (mode == DRAG && saveScale > minScale)) {
							
							float deltaX = curr.x - last.x;
							
							float deltaY = curr.y - last.y;
							
							float scaleWidth = Math.round(origWidth * saveScale);
							
							float scaleHeight = Math.round(origHeight * saveScale);
							
							if (scaleWidth < width) {
								
								deltaX = 0;
								
								if (y + deltaY > 0) deltaY = -y;
								
								else if (y + deltaY < -bottom) deltaY = -(y + bottom);
								
							} else if (scaleHeight < height) {
								
								deltaY = 0;
								
								if (x + deltaX > 0) deltaX = -x;
								
								else if (x + deltaX < -right) deltaX = -(x + right);
								
							} else {
								
								if (x + deltaX > 0) deltaX = -x;
								
								else if (x + deltaX < -right) deltaX = -(x + right);
								
								if (y + deltaY > 0) deltaY = -y;
								
								else if (y + deltaY < -bottom) deltaY = -(y + bottom);
								
							}
							
							matrix.postTranslate(deltaX, deltaY);
							
							last.set(curr.x, curr.y);
							
						}
						
						break;
						
						case MotionEvent.ACTION_UP:
						
						mode = NONE;
						
						int xDiff = (int) Math.abs(curr.x - start.x);
						
						int yDiff = (int) Math.abs(curr.y - start.y);
						
						if (xDiff < CLICK && yDiff < CLICK) performClick();
						
						break;
						
						case MotionEvent.ACTION_POINTER_UP:
						
						mode = NONE;
						
						break;
						
					}
					
					setImageMatrix(matrix);
					
					invalidate();
					
					return true;
					
				}
				
			});
			
		}
		
		
		@Override
		
		public void setImageBitmap(Bitmap bm) {
			
			super.setImageBitmap(bm);
			
			bmWidth = bm.getWidth();
			
			bmHeight = bm.getHeight(); }
		
		
		public void setMaxZoom(float x) {
			
			maxScale = x; }
		
		
		private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
			
			@Override
			
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				
				mode = ZOOM;
				
				return true;}
			
			@Override
			
			public boolean onScale(ScaleGestureDetector detector) {
				
				float mScaleFactor = detector.getScaleFactor();
				
				float origScale = saveScale;
				
				saveScale *= mScaleFactor;
				
				if (saveScale > maxScale){
					
					saveScale = maxScale;
					
					mScaleFactor = maxScale / origScale;
					
				} else if (saveScale < minScale) {
					
					saveScale = minScale;
					
					mScaleFactor = minScale / origScale;}
				
				right = width * saveScale - width - (2 * redundantXSpace * saveScale);
				
				bottom = height * saveScale - height - (2 * redundantYSpace * saveScale);
				
				if (origWidth * saveScale <= width || origHeight * saveScale <= height) {
					
					matrix.postScale(mScaleFactor, mScaleFactor, width / 2, height / 2);
					
					if (mScaleFactor < 1) {
						
						matrix.getValues(m);
						
						float x = m[Matrix.MTRANS_X];
						
						float y = m[Matrix.MTRANS_Y];
						
						if (mScaleFactor < 1) {
							
							if (Math.round(origWidth * saveScale) < width) {
								
								if (y < -bottom) matrix.postTranslate(0, -(y + bottom));
								
								else if (y > 0) matrix.postTranslate(0, -y);
								
							} else {
								
								if (x < -right) matrix.postTranslate(-(x + right), 0);
								
								else if (x > 0) matrix.postTranslate(-x, 0);}
							
						}
						
					}
					
				} else {
					
					matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY()); matrix.getValues(m);
					
					float x = m[Matrix.MTRANS_X];
					
					float y = m[Matrix.MTRANS_Y];
					
					if (mScaleFactor < 1) {
						
						if (x < -right) matrix.postTranslate(-(x + right), 0);
						
						else if (x > 0) matrix.postTranslate(-x, 0);
						
						if (y < -bottom) matrix.postTranslate(0, -(y + bottom));
						
						else if (y > 0) matrix.postTranslate(0, -y);}
					
				}
				
				return true;
				
			}
			
		}
		
		
		@Override
		
		protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
			
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			
			width = MeasureSpec.getSize(widthMeasureSpec);
			
			height = MeasureSpec.getSize(heightMeasureSpec);
			
			float scale;
			
			float scaleX = width / bmWidth;
			
			float scaleY = height / bmHeight;
			
			scale = Math.min(scaleX, scaleY); matrix.setScale(scale, scale); setImageMatrix(matrix);
			
			saveScale = 1f;
			
			redundantYSpace = height - (scale * bmHeight) ;
			
			redundantXSpace = width - (scale * bmWidth);
			
			redundantYSpace /= 2;
			
			redundantXSpace /= 2; matrix.postTranslate(redundantXSpace, redundantYSpace);
			
			origWidth = width - 2 * redundantXSpace;
			
			origHeight = height - 2 * redundantYSpace;
			
			right = width * saveScale - width - (2 * redundantXSpace * saveScale);
			
			bottom = height * saveScale - height - (2 * redundantYSpace * saveScale); setImageMatrix(matrix);}
		
	}
	
	    
	{
	}
	
	public class Viewpager1Adapter extends PagerAdapter {
		
		Context _context;
		ArrayList<HashMap<String, Object>> _data;
		
		public Viewpager1Adapter(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {
			_context = _ctx;
			_data = _arr;
		}
		
		public Viewpager1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_context = getApplicationContext();
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public boolean isViewFromObject(View _view, Object _object) {
			return _view == _object;
		}
		
		@Override
		public void destroyItem(ViewGroup _container, int _position, Object _object) {
			_container.removeView((View) _object);
		}
		
		@Override
		public int getItemPosition(Object _object) {
			return super.getItemPosition(_object);
		}
		
		@Override
		public CharSequence getPageTitle(int pos) {
			// Use the Activity Event (onTabLayoutNewTabAdded) in order to use this method
			return "page " + String.valueOf(pos);
		}
		
		@Override
		public Object instantiateItem(ViewGroup _container,  final int _position) {
			View _view = LayoutInflater.from(_context).inflate(R.layout.view, _container, false);
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			
			touch = new
			ZoomableImageView(AboutUsActivity.this);
			linear1.addView(touch);
			File file = new File(getCacheDir(), "project synopsis3.pdf"); if (!file.exists()) { try { InputStream asset = getAssets().open("project synopsis3.pdf"); FileOutputStream output = null; output = new FileOutputStream(file); final byte[] buffer = new byte[1024]; int size; while ((size = asset.read(buffer)) != -1) { output.write(buffer, 0, size); } asset.close(); output.close(); } catch (IOException e) { e.printStackTrace(); } }
			try {
					renderer = new android.graphics.pdf.PdfRenderer(new ParcelFileDescriptor(ParcelFileDescriptor.open(new java.io.File(file.getAbsolutePath()), ParcelFileDescriptor.MODE_READ_ONLY)));
				android.graphics.pdf.PdfRenderer.Page page = renderer.openPage((int)_position);
				
				Bitmap mBitmap = Bitmap.createBitmap((int)getDip(page.getWidth()), (int)getDip(page.getHeight()), Bitmap.Config.ARGB_8888);
				
				page.render(mBitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
				
				touch.setImageBitmap(mBitmap);
				
				page.close();
			} catch (Exception e){
					  
			}
			
			_container.addView(_view);
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