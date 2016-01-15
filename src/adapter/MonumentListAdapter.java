package adapter;

import java.util.List;
import model.Monument;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.navigation.R;

public class MonumentListAdapter  extends BaseAdapter {
	private List<Monument> monuments;
	private Context context;

	public MonumentListAdapter(Context context, List<Monument> monuments) {
		this.monuments = monuments;
		this.context = context;
	}

	@Override
	public int getCount() {
		return monuments.size();

	}

	@Override
	public Object getItem(int index) {

		return monuments.get(index);
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

		imgIcon.loadImage(monuments.get(position).getImage());

		txtTitle.setText(monuments.get(position).getNomM());
		return convertView;

	}

}