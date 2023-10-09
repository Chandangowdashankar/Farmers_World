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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class CountryActivity extends AppCompatActivity {
	
	private double r = 0;
	private double map_length = 0;
	private String data = "";
	private double n = 0;
	
	private ArrayList<HashMap<String, Object>> list_country = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> countries = new ArrayList<>();
	private ArrayList<String> dialing_codes = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private Intent i = new Intent();
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.country);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		listview1 = findViewById(R.id.listview1);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				file.edit().putString("name", list_country.get((int)_position).get("name").toString()).commit();
				file.edit().putString("code", list_country.get((int)_position).get("dial_code").toString()).commit();
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		_getCountryCode();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	public void _search(final String _str) {
		
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
	
	
	public void _getCountryCode() {
		data = "[{\"name\":\"Afghanistan\",\"dial_code\":\"+93\",\"code\":\"AF\"},{\"name\":\"Albania\",\"dial_code\":\"+355\",\"code\":\"AL\"},{\"name\":\"Algeria\",\"dial_code\":\"+213\",\"code\":\"DZ\"},{\"name\":\"American Samoa\",\"dial_code\":\"+1 684\",\"code\":\"AS\"},{\"name\":\"Andorra\",\"dial_code\":\"+376\",\"code\":\"AD\"},{\"name\":\"Angola\",\"dial_code\":\"+244\",\"code\":\"AO\"},{\"name\":\"Anguilla\",\"dial_code\":\"+1 264\",\"code\":\"AI\"},{\"name\":\"Antarctica\",\"dial_code\":\"+672\",\"code\":\"AQ\"},{\"name\":\"Antigua and Barbuda\",\"dial_code\":\"+1268\",\"code\":\"AG\"},{\"name\":\"Argentina\",\"dial_code\":\"+54\",\"code\":\"AR\"},{\"name\":\"Armenia\",\"dial_code\":\"+374\",\"code\":\"AM\"},{\"name\":\"Aruba\",\"dial_code\":\"+297\",\"code\":\"AW\"},{\"name\":\"Australia\",\"dial_code\":\"+61\",\"code\":\"AU\"},{\"name\":\"Austria\",\"dial_code\":\"+43\",\"code\":\"AT\"},{\"name\":\"Azerbaijan\",\"dial_code\":\"+994\",\"code\":\"AZ\"},{\"name\":\"Bahamas\",\"dial_code\":\"+1 242\",\"code\":\"BS\"},{\"name\":\"Bahrain\",\"dial_code\":\"+973\",\"code\":\"BH\"},{\"name\":\"Bangladesh\",\"dial_code\":\"+880\",\"code\":\"BD\"},{\"name\":\"Barbados\",\"dial_code\":\"+1 246\",\"code\":\"BB\"},{\"name\":\"Belarus\",\"dial_code\":\"+375\",\"code\":\"BY\"},{\"name\":\"Belgium\",\"dial_code\":\"+32\",\"code\":\"BE\"},{\"name\":\"Belize\",\"dial_code\":\"+501\",\"code\":\"BZ\"},{\"name\":\"Benin\",\"dial_code\":\"+229\",\"code\":\"BJ\"},{\"name\":\"Bermuda\",\"dial_code\":\"+1 441\",\"code\":\"BM\"},{\"name\":\"Bhutan\",\"dial_code\":\"+975\",\"code\":\"BT\"},{\"name\":\"Bolivia, Plurinational State of\",\"dial_code\":\"+591\",\"code\":\"BO\"},{\"name\":\"Bosnia and Herzegovina\",\"dial_code\":\"+387\",\"code\":\"BA\"},{\"name\":\"Botswana\",\"dial_code\":\"+267\",\"code\":\"BW\"},{\"name\":\"Brazil\",\"dial_code\":\"+55\",\"code\":\"BR\"},{\"name\":\"British Indian Ocean Territory\",\"dial_code\":\"+246\",\"code\":\"IO\"},{\"name\":\"Brunei Darussalam\",\"dial_code\":\"+673\",\"code\":\"BN\"},{\"name\":\"Bulgaria\",\"dial_code\":\"+359\",\"code\":\"BG\"},{\"name\":\"Burkina Faso\",\"dial_code\":\"+226\",\"code\":\"BF\"},{\"name\":\"Burundi\",\"dial_code\":\"+257\",\"code\":\"BI\"},{\"name\":\"Cambodia\",\"dial_code\":\"+855\",\"code\":\"KH\"},{\"name\":\"Cameroon\",\"dial_code\":\"+237\",\"code\":\"CM\"},{\"name\":\"Canada\",\"dial_code\":\"+1\",\"code\":\"CA\"},{\"name\":\"Cape Verde\",\"dial_code\":\"+238\",\"code\":\"CV\"},{\"name\":\"Cayman Islands\",\"dial_code\":\"+ 345\",\"code\":\"KY\"},{\"name\":\"Central African Republic\",\"dial_code\":\"+236\",\"code\":\"CF\"},{\"name\":\"Chad\",\"dial_code\":\"+235\",\"code\":\"TD\"},{\"name\":\"Chile\",\"dial_code\":\"+56\",\"code\":\"CL\"},{\"name\":\"China\",\"dial_code\":\"+86\",\"code\":\"CN\"},{\"name\":\"Christmas Island\",\"dial_code\":\"+61\",\"code\":\"CX\"},{\"name\":\"Cocos (Keeling) Islands\",\"dial_code\":\"+61\",\"code\":\"CC\"},{\"name\":\"Colombia\",\"dial_code\":\"+57\",\"code\":\"CO\"},{\"name\":\"Comoros\",\"dial_code\":\"+269\",\"code\":\"KM\"},{\"name\":\"Congo\",\"dial_code\":\"+242\",\"code\":\"CG\"},{\"name\":\"Congo, The Democratic Republic of the\",\"dial_code\":\"+243\",\"code\":\"CD\"},{\"name\":\"Cook Islands\",\"dial_code\":\"+682\",\"code\":\"CK\"},{\"name\":\"Costa Rica\",\"dial_code\":\"+506\",\"code\":\"CR\"},{\"name\":\"Cote d'Ivoire\",\"dial_code\":\"+225\",\"code\":\"CI\"},{\"name\":\"Croatia\",\"dial_code\":\"+385\",\"code\":\"HR\"},{\"name\":\"Cuba\",\"dial_code\":\"+53\",\"code\":\"CU\"},{\"name\":\"Cyprus\",\"dial_code\":\"+537\",\"code\":\"CY\"},{\"name\":\"Czech Republic\",\"dial_code\":\"+420\",\"code\":\"CZ\"},{\"name\":\"Denmark\",\"dial_code\":\"+45\",\"code\":\"DK\"},{\"name\":\"Djibouti\",\"dial_code\":\"+253\",\"code\":\"DJ\"},{\"name\":\"Dominica\",\"dial_code\":\"+1 767\",\"code\":\"DM\"},{\"name\":\"Dominican Republic\",\"dial_code\":\"+1 849\",\"code\":\"DO\"},{\"name\":\"Ecuador\",\"dial_code\":\"+593\",\"code\":\"EC\"},{\"name\":\"Egypt\",\"dial_code\":\"+20\",\"code\":\"EG\"},{\"name\":\"El Salvador\",\"dial_code\":\"+503\",\"code\":\"SV\"},{\"name\":\"Equatorial Guinea\",\"dial_code\":\"+240\",\"code\":\"GQ\"},{\"name\":\"Eritrea\",\"dial_code\":\"+291\",\"code\":\"ER\"},{\"name\":\"Estonia\",\"dial_code\":\"+372\",\"code\":\"EE\"},{\"name\":\"Ethiopia\",\"dial_code\":\"+251\",\"code\":\"ET\"},{\"name\":\"Falkland Islands (Malvinas)\",\"dial_code\":\"+500\",\"code\":\"FK\"},{\"name\":\"Faroe Islands\",\"dial_code\":\"+298\",\"code\":\"FO\"},{\"name\":\"Fiji\",\"dial_code\":\"+679\",\"code\":\"FJ\"},{\"name\":\"Finland\",\"dial_code\":\"+358\",\"code\":\"FI\"},{\"name\":\"France\",\"dial_code\":\"+33\",\"code\":\"FR\"},{\"name\":\"French Guiana\",\"dial_code\":\"+594\",\"code\":\"GF\"},{\"name\":\"French Polynesia\",\"dial_code\":\"+689\",\"code\":\"PF\"},{\"name\":\"Gabon\",\"dial_code\":\"+241\",\"code\":\"GA\"},{\"name\":\"Gambia\",\"dial_code\":\"+220\",\"code\":\"GM\"},{\"name\":\"Georgia\",\"dial_code\":\"+995\",\"code\":\"GE\"},{\"name\":\"Germany\",\"dial_code\":\"+49\",\"code\":\"DE\"},{\"name\":\"Ghana\",\"dial_code\":\"+233\",\"code\":\"GH\"},{\"name\":\"Gibraltar\",\"dial_code\":\"+350\",\"code\":\"GI\"},{\"name\":\"Greece\",\"dial_code\":\"+30\",\"code\":\"GR\"},{\"name\":\"Greenland\",\"dial_code\":\"+299\",\"code\":\"GL\"},{\"name\":\"Grenada\",\"dial_code\":\"+1 473\",\"code\":\"GD\"},{\"name\":\"Guadeloupe\",\"dial_code\":\"+590\",\"code\":\"GP\"},{\"name\":\"Guam\",\"dial_code\":\"+1 671\",\"code\":\"GU\"},{\"name\":\"Guatemala\",\"dial_code\":\"+502\",\"code\":\"GT\"},{\"name\":\"Guernsey\",\"dial_code\":\"+44\",\"code\":\"GG\"},{\"name\":\"Guinea\",\"dial_code\":\"+224\",\"code\":\"GN\"},{\"name\":\"Guinea-Bissau\",\"dial_code\":\"+245\",\"code\":\"GW\"},{\"name\":\"Guyana\",\"dial_code\":\"+595\",\"code\":\"GY\"},{\"name\":\"Haiti\",\"dial_code\":\"+509\",\"code\":\"HT\"},{\"name\":\"Holy See (Vatican City State)\",\"dial_code\":\"+379\",\"code\":\"VA\"},{\"name\":\"Honduras\",\"dial_code\":\"+504\",\"code\":\"HN\"},{\"name\":\"Hong Kong\",\"dial_code\":\"+852\",\"code\":\"HK\"},{\"name\":\"Hungary\",\"dial_code\":\"+36\",\"code\":\"HU\"},{\"name\":\"Iceland\",\"dial_code\":\"+354\",\"code\":\"IS\"},{\"name\":\"India\",\"dial_code\":\"+91\",\"code\":\"IN\"},{\"name\":\"Indonesia\",\"dial_code\":\"+62\",\"code\":\"ID\"},{\"name\":\"Iran, Islamic Republic of\",\"dial_code\":\"+98\",\"code\":\"IR\"},{\"name\":\"Iraq\",\"dial_code\":\"+964\",\"code\":\"IQ\"},{\"name\":\"Ireland\",\"dial_code\":\"+353\",\"code\":\"IE\"},{\"name\":\"Isle of Man\",\"dial_code\":\"+44\",\"code\":\"IM\"},{\"name\":\"Israel\",\"dial_code\":\"+972\",\"code\":\"IL\"},{\"name\":\"Italy\",\"dial_code\":\"+39\",\"code\":\"IT\"},{\"name\":\"Jamaica\",\"dial_code\":\"+1 876\",\"code\":\"JM\"},{\"name\":\"Japan\",\"dial_code\":\"+81\",\"code\":\"JP\"},{\"name\":\"Jersey\",\"dial_code\":\"+44\",\"code\":\"JE\"},{\"name\":\"Jordan\",\"dial_code\":\"+962\",\"code\":\"JO\"},{\"name\":\"Kazakhstan\",\"dial_code\":\"+7 7\",\"code\":\"KZ\"},{\"name\":\"Kenya\",\"dial_code\":\"+254\",\"code\":\"KE\"},{\"name\":\"Kiribati\",\"dial_code\":\"+686\",\"code\":\"KI\"},{\"name\":\"Korea, Democratic People's Republic of\",\"dial_code\":\"+850\",\"code\":\"KP\"},{\"name\":\"Korea, Republic of\",\"dial_code\":\"+82\",\"code\":\"KR\"},{\"name\":\"Kuwait\",\"dial_code\":\"+965\",\"code\":\"KW\"},{\"name\":\"Kyrgyzstan\",\"dial_code\":\"+996\",\"code\":\"KG\"},{\"name\":\"Lao People's Democratic Republic\",\"dial_code\":\"+856\",\"code\":\"LA\"},{\"name\":\"Latvia\",\"dial_code\":\"+371\",\"code\":\"LV\"},{\"name\":\"Lebanon\",\"dial_code\":\"+961\",\"code\":\"LB\"},{\"name\":\"Lesotho\",\"dial_code\":\"+266\",\"code\":\"LS\"},{\"name\":\"Liberia\",\"dial_code\":\"+231\",\"code\":\"LR\"},{\"name\":\"Libyan Arab Jamahiriya\",\"dial_code\":\"+218\",\"code\":\"LY\"},{\"name\":\"Liechtenstein\",\"dial_code\":\"+423\",\"code\":\"LI\"},{\"name\":\"Lithuania\",\"dial_code\":\"+370\",\"code\":\"LT\"},{\"name\":\"Luxembourg\",\"dial_code\":\"+352\",\"code\":\"LU\"},{\"name\":\"Macao\",\"dial_code\":\"+853\",\"code\":\"MO\"},{\"name\":\"Macedonia, The Former Yugoslav Republic of\",\"dial_code\":\"+389\",\"code\":\"MK\"},{\"name\":\"Madagascar\",\"dial_code\":\"+261\",\"code\":\"MG\"},{\"name\":\"Malawi\",\"dial_code\":\"+265\",\"code\":\"MW\"},{\"name\":\"Malaysia\",\"dial_code\":\"+60\",\"code\":\"MY\"},{\"name\":\"Maldives\",\"dial_code\":\"+960\",\"code\":\"MV\"},{\"name\":\"Mali\",\"dial_code\":\"+223\",\"code\":\"ML\"},{\"name\":\"Malta\",\"dial_code\":\"+356\",\"code\":\"MT\"},{\"name\":\"Marshall Islands\",\"dial_code\":\"+692\",\"code\":\"MH\"},{\"name\":\"Martinique\",\"dial_code\":\"+596\",\"code\":\"MQ\"},{\"name\":\"Mauritania\",\"dial_code\":\"+222\",\"code\":\"MR\"},{\"name\":\"Mauritius\",\"dial_code\":\"+230\",\"code\":\"MU\"},{\"name\":\"Mayotte\",\"dial_code\":\"+262\",\"code\":\"YT\"},{\"name\":\"Mexico\",\"dial_code\":\"+52\",\"code\":\"MX\"},{\"name\":\"Micronesia, Federated States of\",\"dial_code\":\"+691\",\"code\":\"FM\"},{\"name\":\"Moldova, Republic of\",\"dial_code\":\"+373\",\"code\":\"MD\"},{\"name\":\"Monaco\",\"dial_code\":\"+377\",\"code\":\"MC\"},{\"name\":\"Mongolia\",\"dial_code\":\"+976\",\"code\":\"MN\"},{\"name\":\"Montenegro\",\"dial_code\":\"+382\",\"code\":\"ME\"},{\"name\":\"Montserrat\",\"dial_code\":\"+1664\",\"code\":\"MS\"},{\"name\":\"Morocco\",\"dial_code\":\"+212\",\"code\":\"MA\"},{\"name\":\"Mozambique\",\"dial_code\":\"+258\",\"code\":\"MZ\"},{\"name\":\"Myanmar\",\"dial_code\":\"+95\",\"code\":\"MM\"},{\"name\":\"Namibia\",\"dial_code\":\"+264\",\"code\":\"NA\"},{\"name\":\"Nauru\",\"dial_code\":\"+674\",\"code\":\"NR\"},{\"name\":\"Nepal\",\"dial_code\":\"+977\",\"code\":\"NP\"},{\"name\":\"Netherlands\",\"dial_code\":\"+31\",\"code\":\"NL\"},{\"name\":\"Netherlands Antilles\",\"dial_code\":\"+599\",\"code\":\"AN\"},{\"name\":\"New Caledonia\",\"dial_code\":\"+687\",\"code\":\"NC\"},{\"name\":\"New Zealand\",\"dial_code\":\"+64\",\"code\":\"NZ\"},{\"name\":\"Nicaragua\",\"dial_code\":\"+505\",\"code\":\"NI\"},{\"name\":\"Niger\",\"dial_code\":\"+227\",\"code\":\"NE\"},{\"name\":\"Nigeria\",\"dial_code\":\"+234\",\"code\":\"NG\"},{\"name\":\"Niue\",\"dial_code\":\"+683\",\"code\":\"NU\"},{\"name\":\"Norfolk Island\",\"dial_code\":\"+672\",\"code\":\"NF\"},{\"name\":\"Northern Mariana Islands\",\"dial_code\":\"+1 670\",\"code\":\"MP\"},{\"name\":\"Norway\",\"dial_code\":\"+47\",\"code\":\"NO\"},{\"name\":\"Oman\",\"dial_code\":\"+968\",\"code\":\"OM\"},{\"name\":\"Pakistan\",\"dial_code\":\"+92\",\"code\":\"PK\"},{\"name\":\"Palau\",\"dial_code\":\"+680\",\"code\":\"PW\"},{\"name\":\"Palestinian Territory, Occupied\",\"dial_code\":\"+970\",\"code\":\"PS\"},{\"name\":\"Panama\",\"dial_code\":\"+507\",\"code\":\"PA\"},{\"name\":\"Papua New Guinea\",\"dial_code\":\"+675\",\"code\":\"PG\"},{\"name\":\"Paraguay\",\"dial_code\":\"+595\",\"code\":\"PY\"},{\"name\":\"Peru\",\"dial_code\":\"+51\",\"code\":\"PE\"},{\"name\":\"Philippines\",\"dial_code\":\"+63\",\"code\":\"PH\"},{\"name\":\"Pitcairn\",\"dial_code\":\"+872\",\"code\":\"PN\"},{\"name\":\"Poland\",\"dial_code\":\"+48\",\"code\":\"PL\"},{\"name\":\"Portugal\",\"dial_code\":\"+351\",\"code\":\"PT\"},{\"name\":\"Puerto Rico\",\"dial_code\":\"+1 939\",\"code\":\"PR\"},{\"name\":\"Qatar\",\"dial_code\":\"+974\",\"code\":\"QA\"},{\"name\":\"Romania\",\"dial_code\":\"+40\",\"code\":\"RO\"},{\"name\":\"Russia\",\"dial_code\":\"+7\",\"code\":\"RU\"},{\"name\":\"Rwanda\",\"dial_code\":\"+250\",\"code\":\"RW\"},{\"name\":\"Réunion\",\"dial_code\":\"+262\",\"code\":\"RE\"},{\"name\":\"Saint Barthélemy\",\"dial_code\":\"+590\",\"code\":\"BL\"},{\"name\":\"Saint Helena, Ascension and Tristan Da Cunha\",\"dial_code\":\"+290\",\"code\":\"SH\"},{\"name\":\"Saint Kitts and Nevis\",\"dial_code\":\"+1 869\",\"code\":\"KN\"},{\"name\":\"Saint Lucia\",\"dial_code\":\"+1 758\",\"code\":\"LC\"},{\"name\":\"Saint Martin\",\"dial_code\":\"+590\",\"code\":\"MF\"},{\"name\":\"Saint Pierre and Miquelon\",\"dial_code\":\"+508\",\"code\":\"PM\"},{\"name\":\"Saint Vincent and the Grenadines\",\"dial_code\":\"+1 784\",\"code\":\"VC\"},{\"name\":\"Samoa\",\"dial_code\":\"+685\",\"code\":\"WS\"},{\"name\":\"San Marino\",\"dial_code\":\"+378\",\"code\":\"SM\"},{\"name\":\"Sao Tome and Principe\",\"dial_code\":\"+239\",\"code\":\"ST\"},{\"name\":\"Saudi Arabia\",\"dial_code\":\"+966\",\"code\":\"SA\"},{\"name\":\"Senegal\",\"dial_code\":\"+221\",\"code\":\"SN\"},{\"name\":\"Serbia\",\"dial_code\":\"+381\",\"code\":\"RS\"},{\"name\":\"Seychelles\",\"dial_code\":\"+248\",\"code\":\"SC\"},{\"name\":\"Sierra Leone\",\"dial_code\":\"+232\",\"code\":\"SL\"},{\"name\":\"Singapore\",\"dial_code\":\"+65\",\"code\":\"SG\"},{\"name\":\"Slovakia\",\"dial_code\":\"+421\",\"code\":\"SK\"},{\"name\":\"Slovenia\",\"dial_code\":\"+386\",\"code\":\"SI\"},{\"name\":\"Solomon Islands\",\"dial_code\":\"+677\",\"code\":\"SB\"},{\"name\":\"Somalia\",\"dial_code\":\"+252\",\"code\":\"SO\"},{\"name\":\"South Africa\",\"dial_code\":\"+27\",\"code\":\"ZA\"},{\"name\":\"South Georgia and the South Sandwich Islands\",\"dial_code\":\"+500\",\"code\":\"GS\"},{\"name\":\"Spain\",\"dial_code\":\"+34\",\"code\":\"ES\"},{\"name\":\"Sri Lanka\",\"dial_code\":\"+94\",\"code\":\"LK\"},{\"name\":\"Sudan\",\"dial_code\":\"+249\",\"code\":\"SD\"},{\"name\":\"Suriname\",\"dial_code\":\"+597\",\"code\":\"SR\"},{\"name\":\"Svalbard and Jan Mayen\",\"dial_code\":\"+47\",\"code\":\"SJ\"},{\"name\":\"Swaziland\",\"dial_code\":\"+268\",\"code\":\"SZ\"},{\"name\":\"Sweden\",\"dial_code\":\"+46\",\"code\":\"SE\"},{\"name\":\"Switzerland\",\"dial_code\":\"+41\",\"code\":\"CH\"},{\"name\":\"Syrian Arab Republic\",\"dial_code\":\"+963\",\"code\":\"SY\"},{\"name\":\"Taiwan, Province of China\",\"dial_code\":\"+886\",\"code\":\"TW\"},{\"name\":\"Tajikistan\",\"dial_code\":\"+992\",\"code\":\"TJ\"},{\"name\":\"Tanzania, United Republic of\",\"dial_code\":\"+255\",\"code\":\"TZ\"},{\"name\":\"Thailand\",\"dial_code\":\"+66\",\"code\":\"TH\"},{\"name\":\"Timor-Leste\",\"dial_code\":\"+670\",\"code\":\"TL\"},{\"name\":\"Togo\",\"dial_code\":\"+228\",\"code\":\"TG\"},{\"name\":\"Tokelau\",\"dial_code\":\"+690\",\"code\":\"TK\"},{\"name\":\"Tonga\",\"dial_code\":\"+676\",\"code\":\"TO\"},{\"name\":\"Trinidad and Tobago\",\"dial_code\":\"+1 868\",\"code\":\"TT\"},{\"name\":\"Tunisia\",\"dial_code\":\"+216\",\"code\":\"TN\"},{\"name\":\"Turkey\",\"dial_code\":\"+90\",\"code\":\"TR\"},{\"name\":\"Turkmenistan\",\"dial_code\":\"+993\",\"code\":\"TM\"},{\"name\":\"Turks and Caicos Islands\",\"dial_code\":\"+1 649\",\"code\":\"TC\"},{\"name\":\"Tuvalu\",\"dial_code\":\"+688\",\"code\":\"TV\"},{\"name\":\"Uganda\",\"dial_code\":\"+256\",\"code\":\"UG\"},{\"name\":\"Ukraine\",\"dial_code\":\"+380\",\"code\":\"UA\"},{\"name\":\"United Arab Emirates\",\"dial_code\":\"+971\",\"code\":\"AE\"},{\"name\":\"United Kingdom\",\"dial_code\":\"+44\",\"code\":\"GB\"},{\"name\":\"United States\",\"dial_code\":\"+1\",\"code\":\"US\"},{\"name\":\"Uruguay\",\"dial_code\":\"+598\",\"code\":\"UY\"},{\"name\":\"Uzbekistan\",\"dial_code\":\"+998\",\"code\":\"UZ\"},{\"name\":\"Vanuatu\",\"dial_code\":\"+678\",\"code\":\"VU\"},{\"name\":\"Venezuela, Bolivarian Republic of\",\"dial_code\":\"+58\",\"code\":\"VE\"},{\"name\":\"Viet Nam\",\"dial_code\":\"+84\",\"code\":\"VN\"},{\"name\":\"Virgin Islands, British\",\"dial_code\":\"+1 284\",\"code\":\"VG\"},{\"name\":\"Virgin Islands, U.S.\",\"dial_code\":\"+1 340\",\"code\":\"VI\"},{\"name\":\"Wallis and Futuna\",\"dial_code\":\"+681\",\"code\":\"WF\"},{\"name\":\"Yemen\",\"dial_code\":\"+967\",\"code\":\"YE\"},{\"name\":\"Zambia\",\"dial_code\":\"+260\",\"code\":\"ZM\"},{\"name\":\"Zimbabwe\",\"dial_code\":\"+263\",\"code\":\"ZW\"},{\"name\":\"Åland Islands\",\"dial_code\":\"+358\",\"code\":\"AX\"}]";
		list_country = new Gson().fromJson(data, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		listview1.setAdapter(new Listview1Adapter(list_country));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
				_view = _inflater.inflate(R.layout.list_country, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView tx_name = _view.findViewById(R.id.tx_name);
			final TextView tx_code = _view.findViewById(R.id.tx_code);
			
			tx_name.setText(_data.get((int)_position).get("name").toString());
			tx_code.setText(_data.get((int)_position).get("dial_code").toString());
			
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