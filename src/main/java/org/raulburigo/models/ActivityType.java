package org.raulburigo.models;


public enum ActivityType  {
    
    CREATED("criou o cartão"),
    MOVED("moveu o cartão"),
    COMPLETED("marcou como completo"),
    DELETED("deletou o cartão");

    private String activityText;

    ActivityType(String activityText) {
        this.activityText = activityText;
    }

    public String getActivityText() {
        return activityText;
    }

}
