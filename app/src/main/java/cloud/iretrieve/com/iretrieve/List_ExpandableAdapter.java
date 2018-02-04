package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 26/11/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class List_ExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    public boolean bReset = false;
    public Date range1;
    public Date range2;


    public List_ExpandableAdapter(Context context, List<String> listDataHeader,
                                  HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (groupPosition == 0) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_namesubcat_description, null);
            TextView txtListChild = (TextView) convertView
                   .findViewById(R.id.txtListItem);

            txtListChild.setText(childText);
        } else {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listname_daterange, null);

            /**Calendar nextYear = Calendar.getInstance();
             nextYear.add(Calendar.YEAR, 1);
             Calendar minYear = Calendar.getInstance();
             minYear.add(Calendar.YEAR, -2);
             calendar = (CalendarPickerView) convertView.findViewById(R.id.calendar_view);
             calendar.init(minYear.getTime(), nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);**/

            //.withSelectedDate(null)

            final MaterialCalendarView calendar = (MaterialCalendarView) convertView.findViewById(R.id.calendarView);
            calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);


            calendar.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                    try {
                        if ((materialCalendarView.getSelectedDates().size() > 2) || (bReset == true)){
                            materialCalendarView.clearSelection();
                            materialCalendarView.setDateSelected(calendarDay,true);
                            bReset = false;
                        }else if(materialCalendarView.getSelectedDates().size() == 2){
                            CalendarDay com1 = materialCalendarView.getSelectedDates().get(0);
                            CalendarDay com2 = materialCalendarView.getSelectedDates().get(1);
                            if(com2.getDate().compareTo(com1.getDate()) > 0){
                                int days = Days.daysBetween(new DateTime(com1.getDate()), new DateTime(com2.getDate())).getDays();
                                DateTime erm = new DateTime(com1.getDate());
                                for (int i=0; i < days; i++) {
                                    LocalDate d = erm.toLocalDate().withFieldAdded(DurationFieldType.days(), i);
                                    materialCalendarView.setDateSelected(d.toDate(),true);
                                }
                                bReset = true;
                                range1 = com1.getDate();
                                range2 = com2.getDate();
                                Activity_Dashboard e = (Activity_Dashboard) _context;
                                e.showList();
                            }else{
                                materialCalendarView.clearSelection();
                                materialCalendarView.setDateSelected(com2,true);
                                bReset = false;
                            }
                        }

                    } catch (Exception ex) {
                        showMessage(ex.toString());
                    }
                }
            });

        }

        return convertView;
    }

    public void showMessage(String e) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this._context);
        diag.setTitle("Message");
        diag.setMessage(e);
        diag.show();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_namesubcat, null);
        }

        /**TextView lblListHeader = (TextView) convertView
         .findViewById(R.id.txtHeader);
         lblListHeader.setTypeface(null, Typeface.BOLD);
         lblListHeader.setText(headerTitle);**/

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
