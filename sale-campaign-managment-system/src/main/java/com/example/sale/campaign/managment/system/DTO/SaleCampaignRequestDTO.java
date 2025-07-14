package com.example.sale.campaign.managment.system.DTO;

import java.util.List;

public class SaleCampaignRequestDTO {

    private String startDate;
    private String endDate;
    private String campaignName;
    private List<CampaignDiscountDTO> listOfCampaign;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public List<CampaignDiscountDTO> getListOfCampaign() {
        return listOfCampaign;
    }

    public void setListOfCampaign(List<CampaignDiscountDTO> listOfCampaign) {
        this.listOfCampaign = listOfCampaign;
    }
}
