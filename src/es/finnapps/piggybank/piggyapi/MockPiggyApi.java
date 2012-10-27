package es.finnapps.piggybank.piggyapi;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.finnapps.piggybank.model.Piggy;

public class MockPiggyApi implements PiggyApiInterface {

    public boolean register(String userName, String phoneNumber) {
        return true;
    }

    public List<Piggy> getSharedPiggys(String phoneNumber) {
        List<String> shared = new LinkedList<String>();
        shared.add("634783448");
        shared.add("617561189");
        Piggy mockPig = new Piggy("mockPiggy", "ABC2020020202-23423", (float) 300.22, new Date(), "GIFT!",
                Piggy.PIGGY_TYPE_GIFT, shared, 500.0f);
        List<Piggy> mockList = new LinkedList<Piggy>();
        mockList.add(mockPig);
        return mockList;
    }

    public boolean notifyMoneySavedOnPiggy(Piggy piggy, float amountSaved) {
        return true;
    }

    public boolean sharePiggyWith(Piggy piggy) {
        return true;
    }

    public String createPiggyForGift(Piggy piggy, float amount, Date expirationDate) {
        return "ACB2020202020101202-33333";
    }

    public boolean createPiggy(Piggy piggy, String telephoneOwner) {
        
        return true;
    }

}
