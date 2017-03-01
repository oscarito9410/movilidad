package com.gruposalinas.elektra.movilidadgs.adapters;

import java.util.ArrayList;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RegistroDetallesAdapter extends ArrayAdapter<String> {
	private static final String TAG = "REGISTRO_ADAPTER";
	private LayoutInflater mInflater;
	
	private ArrayList<RegistroGPS> list;
	
	private int mViewResourceId;
	
	public RegistroDetallesAdapter(Context ctx, int viewResourceId, ArrayList<RegistroGPS> list) {
		super(ctx, viewResourceId);
		
		mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.list = list;
		
		mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		TextView fechaText = (TextView)convertView.findViewById(R.id.item_fecha_value);
		fechaText.setText(list.get(position).getFecha() + " - " + list.get(position).getHora());

		TextView posicionText = (TextView)convertView.findViewById(R.id.item_posicion_value);
		posicionText.setText(list.get(position).getLatitud() + "/" + list.get(position).getLongitud());
		
		return convertView;
	}
}