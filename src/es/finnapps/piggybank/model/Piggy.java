package es.finnapps.piggybank.model;

import java.util.Date;
import java.util.List;

public class Piggy {

    public static final int PIGGY_TYPE_SAVING = 0;
    public static final int PIGGY_TYPE_OBJETIVE = 1;
    public static final int PIGGY_TYPE_GIFT = 2;
    public static final int PIGGY_TYPE_SHARED = 3;
    public static final int PIGGY_TYPE_CAIXA = 4;
    public static final int PIGGY_TYPE_OTHER = 5;

    private String name;
    private String number;
    private float amount;
    private Date expiration;
    private String objectiveName;
    private float objectiveAmount;
    private float amountToShare; // <=0 if no amount defined
    private List<String> shared;
    private int type;
    private int id;
    private String account_number;

    public Piggy(String name, String number, float amount, Date expiration, String objetiveName, int type, List<String> shared, String account_number, float objectiveAmount) {
        super();
        this.name = name;
        this.number = number;
        this.amount = amount;
        this.shared = shared;
        this.expiration = expiration;
        this.objectiveName = objetiveName;
        this.type = type;
        this.objectiveAmount = objectiveAmount;
        this.account_number = account_number;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public List<String> getShared() {
        return shared;
    }

    public void setShared(List<String> shared) {
        this.shared = shared;
    }

    public String getExpiration() {
        return expiration.toString();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public float getObjectiveAmount() {
        return objectiveAmount;
    }

    public void setObjectiveAmount(float objectiveAmount) {
        this.objectiveAmount = objectiveAmount;
    }


    public float getAmountToShare() {
        return amountToShare;
    }


    public void setAmountToShare(float amountToShare) {
        this.amountToShare = amountToShare;
    }


    public String getAccount_number() {
        return account_number;
    }


    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }


}
