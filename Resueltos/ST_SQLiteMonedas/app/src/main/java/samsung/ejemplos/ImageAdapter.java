package samsung.ejemplos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return flagsIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setPadding(2, 2, 2, 2);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(flagsIds[position]);
        imageView.setTag(flagsIds[position]);
        return imageView;
    }

    // Keep all Images in array
    private Integer[] flagsIds = {
            R.drawable.aud_flag, R.drawable.bgn_flag,
            R.drawable.brl_flag, R.drawable.cad_flag,
            R.drawable.chf_flag, R.drawable.cny_flag,
            R.drawable.czk_flag, R.drawable.dkk_flag,
            R.drawable.eur_flag, R.drawable.gbp_flag,
            R.drawable.hkd_flag, R.drawable.hrk_flag,
            R.drawable.huf_flag, R.drawable.idr_flag,
            R.drawable.ils_flag, R.drawable.inr_flag,
            R.drawable.jpy_flag, R.drawable.krw_flag,
            R.drawable.ltl_flag, R.drawable.mxn_flag,
            R.drawable.myr_flag, R.drawable.nok_flag,
            R.drawable.nzd_flag, R.drawable.php_flag,
            R.drawable.pln_flag, R.drawable.ron_flag,
            R.drawable.rub_flag, R.drawable.sek_flag,
            R.drawable.sgd_flag, R.drawable.thb_flag,
            R.drawable.try_flag, R.drawable.ltl_flag,
            R.drawable.usd_flag, R.drawable.zar_flag,

    };
}