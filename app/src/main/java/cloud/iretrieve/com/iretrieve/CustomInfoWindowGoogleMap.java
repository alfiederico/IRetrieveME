package cloud.iretrieve.com.iretrieve;

/**
 * Created by Alfie on 10/01/18.
 */

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import cloud.iretrieve.com.iretrieve.domain.Report;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView reportid = view.findViewById(R.id.reportid);
        TextView type = view.findViewById(R.id.type);

        TextView subject = view.findViewById(R.id.subject);
        TextView description = view.findViewById(R.id.description);
        TextView date = view.findViewById(R.id.date);

        TextView contact = view.findViewById(R.id.contact);

        reportid.setText(marker.getTitle());
        type.setText(marker.getSnippet());

        Report report = (Report) marker.getTag();


        subject.setText(report.getSubject());
        description.setText(report.getDescription());
        date.setText(report.getDate());
        contact.setText(report.getContact());

        return view;
    }
}