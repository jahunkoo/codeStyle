package com.android.jahhunkoo.codestyle.dto.gson;

import com.android.jahhunkoo.codestyle.dto.BigRegionDTO;

import java.util.List;

/**
 * Created by Jahun Koo on 2015-02-04.
 */
public class RawData {

    private String network;
    private List<BigRegionDTO> bigRegionList;

    public RawData(String network, List<BigRegionDTO> bigRegionList) {
        this.network = network;
        this.bigRegionList = bigRegionList;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public List<BigRegionDTO> getBigRegionList() {
        return bigRegionList;
    }

    public void setBigRegionList(List<BigRegionDTO> bigRegionList) {
        this.bigRegionList = bigRegionList;
    }
}
