package adapter;

import java.util.List;

import model.Pharmacie;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.navigation.R;

public class PharmacieListAdapter extends BaseAdapter {
	private List<Pharmacie> pharmacies;
	private Context context;

	public PharmacieListAdapter(Context context, List<Pharmacie> pharmacies) {
		this.pharmacies = pharmacies;
		this.context = context;
	}

	@Override
	public int getCount() {
		return pharmacies.size();

	}

	@Override
	public Object getItem(int index) {

		return pharmacies.get(index);
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

		imgIcon.loadImage(pharmacies.get(position).getImage());

		txtTitle.setText(pharmacies.get(position).getNom());
		return convertView;

	}

}
