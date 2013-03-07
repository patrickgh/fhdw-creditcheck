package de.puzzles.webapp.page.newrequest.steps;

import com.vaynberg.wicket.select2.Select2Choice;
import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.Customer;
import de.puzzles.webapp.components.choiceprovider.ConsultantChoiceProvider;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import java.util.Date;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 06.03.13
 */
public class PersonalInformationStep extends WizardStep {

    public PersonalInformationStep(IModel<CreditRequest> defaultModel) {
        super();

        CompoundPropertyModel<CreditRequest> requestModel = new CompoundPropertyModel<CreditRequest>(defaultModel);
        CompoundPropertyModel<Customer> customerModel = new CompoundPropertyModel<Customer>(requestModel.getObject().getCustomer());
        TextField firstName = new TextField("firstName",customerModel.bind("firstname"));
        add(firstName);

        TextField lastName = new TextField("lastName",customerModel.bind("lastname"));
        add(lastName);

        DateTextField birthdayField = new DateTextField("birthday", customerModel.<Date>bind("birthday"));
        add(birthdayField);

        TextField street = new TextField("street",customerModel.bind("street"));
        add(street);

        TextField zipCode = new TextField("zipcode", customerModel.bind("zipcode"));
        add(zipCode);

        TextField city = new TextField("city", customerModel.bind("city"));
        add(city);

        TextField telephone = new TextField("telephone", customerModel.bind("telephone"));
        add(telephone);

        TextField email = new TextField("email", customerModel.bind("email"));
        email.add(EmailAddressValidator.getInstance());
        add(email);

        TextField accountNumber = new TextField("accountnumber", customerModel.bind("accountnumber"));
        add(accountNumber);

        TextField bankCode = new TextField("bankcode", customerModel.bind("bankcode"));
        add(bankCode);

        Select2Choice<Integer> consultant = new Select2Choice<Integer>("consultant", requestModel.<Integer>bind("consultantId"), new ConsultantChoiceProvider());
        consultant.setRequired(true);
        add(consultant);

    }
}