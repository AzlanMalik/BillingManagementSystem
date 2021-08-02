package Application.DAO;


import java.io.Serializable;

public class ProductJRBean implements Serializable {
    private String organizationAddress;
    private String organizationName;
    private String organizationContact;
    private String searchFilter1, searchFilter2, searchFilter3, searchFilter4, searchFilter5;
    private String logoPath;


    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public void setOrganizationContact(String organizationContact) {
        this.organizationContact = organizationContact;
    }

    public void setSearchFilter1(String searchFilter1) {
        this.searchFilter1 = searchFilter1;
    }

    public void setSearchFilter2(String searchFilter2) {
        this.searchFilter2 = searchFilter2;
    }

    public void setSearchFilter3(String searchFilter3) {
        this.searchFilter3 = searchFilter3;
    }

    public void setSearchFilter4(String searchFilter4) {
        this.searchFilter4 = searchFilter4;
    }

    public void setSearchFilter5(String searchFilter5) {
        this.searchFilter5 = searchFilter5;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public String getOrganizationContact() {
        return organizationContact;
    }

    public String getSearchFilter1() {
        return searchFilter1;
    }

    public String getSearchFilter2() {
        return searchFilter2;
    }

    public String getSearchFilter3() {
        return searchFilter3;
    }

    public String getSearchFilter4() {
        return searchFilter4;
    }

    public String getSearchFilter5() {
        return searchFilter5;
    }

    public String getLogoPath() {
        return logoPath;
    }




}
