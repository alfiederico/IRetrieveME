package cloud.iretrieve.com.iretrieve.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by User on 27/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class StaffList  {
    private List<Staff> staffs;


    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }
}
