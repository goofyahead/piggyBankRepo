package es.finnapps.piggybank.roboguice;

import android.content.Context;

import com.google.inject.AbstractModule;

import es.finnapps.piggybank.bankapi.BankApiInterface;
import es.finnapps.piggybank.bankapi.MockBankApi;
import es.finnapps.piggybank.contacts.ContactsProvider;
import es.finnapps.piggybank.piggyapi.MockPiggyApi;
import es.finnapps.piggybank.piggyapi.PiggyApiInterface;

public class CustomConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        Context mContext = PiggyBankAplication.getContext();

        PiggyApiInterface piggyApi = new MockPiggyApi();
        BankApiInterface bankApi = new MockBankApi();
        ContactsProvider contacts = new ContactsProvider(mContext);

        bind(PiggyApiInterface.class).toInstance(piggyApi);
        bind(BankApiInterface.class).toInstance(bankApi);
    }
}