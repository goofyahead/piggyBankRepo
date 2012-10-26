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
    private String objetive;
    private List<String> shared;
    private int type;
    private int id;

    public Piggy(String name, String number, float amount, Date expiration, String objetive, int type, List<String> shared) {
        super();
        this.name = name;
        this.number = number;
        this.amount = amount;
        this.shared = shared;
        this.expiration = expiration;
        this.objetive = objetive;
        this.type = type;
    }

    public String getObjetive() {
        return objetive;
    }

    public void setObjetive(String objetive) {
        this.objetive = objetive;
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
}
