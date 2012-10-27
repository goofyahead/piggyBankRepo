package es.finnapps.piggybank.roboguice;

import android.content.Context;

import com.google.inject.AbstractModule;

import es.finnapps.piggybank.bankapi.BankApi;
import es.finnapps.piggybank.bankapi.BankApiInterface;
import es.finnapps.piggybank.bankapi.MockBankApi;
import es.finnapps.piggybank.contacts.ContactsProvider;
import es.finnapps.piggybank.piggyapi.MockPiggyApi;
import es.finnapps.piggybank.piggyapi.PiggyApi;
import es.finnapps.piggybank.piggyapi.PiggyApiInterface;

public class CustomConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        Context mContext = PiggyBankAplication.getContext();

        PiggyApiInterface piggyApi = new PiggyApi();
        BankApiInterface bankApi = new BankApi();
        ContactsProvider contacts = new ContactsProvider(mContext);
        

        bind(PiggyApiInterface.class).toInstance(piggyApi);
        bind(BankApiInterface.class).toInstance(bankApi);
        bind(ContactsProvider.class).toInstance(contacts);
    }
}