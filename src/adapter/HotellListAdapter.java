package adapter;

import java.util.List;

import model.Hotel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.navigation.R;

public class HotellListAdapter extends BaseAdapter {
	private List<Hotel> hotels;
	private Context context;

	public HotellListAdapter(Context context, List<Hotel> hotels) {
		this.hotels = hotels;
		this.context = context;
	}

	@Override
	public int getCount() {
		return hotels.size();

	}

	@Override
	public Object getItem(int index) {

		return hotels.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(
					R.layout.list_item_title_navigation, null);
		}

		ImageWebView imgIcon = (ImageWebView) convertView
				.findViewById(R.id.imgIcon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
		TextView txtTitle1 = (TextView) convertView.findViewById(R.id.txtTitle1);

		imgIcon.loadImage(hotels.get(position).getImage());

		txtTitle.setText(hotels.get(position).getNomH());

		txtTitle1.setText(hotels.get(position).getEmailH());
		
return convertView;

	}

}
