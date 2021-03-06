package de.puzzles.webapp.page.newrequest.wizard.steps;

import com.vaynberg.wicket.select2.Select2Choice;
import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.Customer;
import de.puzzles.webapp.components.provider.ConsultantChoiceProvider;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import java.util.Date;

/**
 * Wizard step for the personal informations. Contains all form components for the personal informations (e.g. firstname, lastname, birthday,...)
 *
 * @author Patrick Groß-Holtwick
 *         Date: 06.03.13
 */
public class PersonalInformationStep extends WizardStep {

    private DateTextField birthdayField;

    public PersonalInformationStep(IModel<CreditRequest> defaultModel) {
        super();
        boolean requireFields = System.getProperty("disableRequiredFields") == null;

        CompoundPropertyModel<CreditRequest> requestModel = new CompoundPropertyModel<CreditRequest>(defaultModel);
        CompoundPropertyModel<Customer> customerModel = new CompoundPropertyModel<Customer>(requestModel.getObject().getCustomer());
        TextField firstName = new TextField("firstName", customerModel.bind("firstname"));
        firstName.setRequired(requireFields);
        add(firstName);

        TextField lastName = new TextField("lastName", customerModel.bind("lastname"));
        lastName.setRequired(requireFields);
        add(lastName);

        birthdayField = new DateTextField("birthday", customerModel.<Date>bind("birthday"), "dd.MM.yyyy");
        birthdayField.setRequired(requireFields);
        add(birthdayField);

        TextField street = new TextField("street", customerModel.bind("street"));
        street.setRequired(requireFields);
        add(street);

        TextField zipCode = new TextField("zipcode", customerModel.bind("zipcode"));
        zipCode.setRequired(requireFields);
        add(zipCode);

        TextField city = new TextField("city", customerModel.bind("city"));
        city.setRequired(requireFields);
        add(city);

        TextField telephone = new TextField("telephone", customerModel.bind("telephone"));
        telephone.setRequired(requireFields);
        add(telephone);

        TextField email = new TextField("email", customerModel.bind("email"));
        email.add(EmailAddressValidator.getInstance());
        email.setRequired(requireFields);
        add(email);

        TextField accountNumber = new TextField("accountnumber", customerModel.bind("accountnumber"));
        add(accountNumber);

        TextField bankCode = new TextField("bankcode", customerModel.bind("bankcode"));
        bankCode.setRequired(requireFields);
        add(bankCode);

        Select2Choice<Integer> consultant = new Select2Choice<Integer>("consultant", requestModel.<Integer>bind("consultantId"), new ConsultantChoiceProvider());
        consultant.setRequired(requireFields);
        add(consultant);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$('#" + birthdayField.getMarkupId() + "').datepicker({format:'dd.mm.yyyy', language:'de',autoclose:true,startView:2});"));
    }
}
