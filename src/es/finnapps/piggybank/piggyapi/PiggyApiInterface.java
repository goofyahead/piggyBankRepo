package es.finnapps.piggybank.piggyapi;

import java.util.Date;
import java.util.List;

import es.finnapps.piggybank.model.Piggy;

public interface PiggyApiInterface {
    /**
     * Register a new user, or an existing one, bank identification should be
     * stored in shared prefs.
     * 
     * @param userName
     *            the pretty name of the user.
     * @param phoneNumber
     *            the phone number that will be used for sharing and
     *            identification.
     * @return true if correctly registered.
     */
    public boolean register(String userName, String phoneNumber);

    /**
     * Request for piggys that are shared with me, a list of piggys should be
     * returned, with current state.
     * 
     * @param phoneNumber
     *            user phone number to check wich accounts are shared with me.
     * @return a list of piggys that are shared with me, and its current state.
     */
    public List<Piggy> getSharedPiggys(String phoneNumber);

    /**
     * Notify the server that a saving is made in a piggy. (Only needed in
     * shared piggys)
     * 
     * @param piggy
     *            the piggy that the money has been saved.
     * @param amountSaved
     *            the amount of the last saving.
     * @return true if operation was correct.
     */
    public boolean notifyMoneySavedOnPiggy(Piggy piggy, float amountSavedTotal);

    /**
     * Request to share a piggy with people, a list with the phoneNumber is
     * provided in the piggy.
     * 
     * @param piggy
     *            the piggy to be shared, from now on this piggy will be tracked
     *            on our piggyAPI.
     * @param phoneNumbers
     *            list of phoneNumbers of the people to share the pig with.
     * @return
     */
    public boolean sharePiggyWith(Piggy piggy);
    
    public boolean createPiggy(Piggy piggy, String telephoneOwner);

    /**
     * Request to create a new piggy gift, an account will be created and
     * returned, all the people will receive an invite for this piggy.
     * 
     * @param piggy
     *            the gift piggy with the people to invite.
     * @param amount
     *            the amount of the gift.
     * @param expirationDate
     *            the expiration date for buying the gift.
     * @return
     */
    public String createPiggyForGift(Piggy piggy, float amount, Date expirationDate);

    
    
}
