package com.eurm.vaadin.flow.test.ui;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value="")
@Push(value=PushMode.MANUAL, transport=Transport.LONG_POLLING)
@SpringComponent
@UIScope
@HtmlImport("frontend://bower_components/vaadin-lumo-styles/presets/compact.html")
@Theme(Lumo.class)
public class UnloggedRoute extends VerticalLayout{
	private static final long serialVersionUID = 3738481769104073928L;

	public static final String USER_NAME_TEXT_FIELD_ID = UnloggedRoute.class.getSimpleName() + "userNameTextFieldId";
	public static final String PASSWORD_FIELD_ID = UnloggedRoute.class.getSimpleName() + "passwordTextFieldId";
	public static final String LOGIN_BUTTON_ID = UnloggedRoute.class.getSimpleName() + "loginButtonId";

	private Logger logger = LoggerFactory.getLogger(UnloggedRoute.class);

	private TextField txtUser;
	private PasswordField password;
	private Button loginButton;

	private String userName;
	private String passWord;
	
	private Binder<UnloggedRoute> userBinder;

	
	public UnloggedRoute() {
	}
	
	@PostConstruct
	public void init() {
		try {
			setSizeFull();
			setSpacing(false);
			setMargin(false);
			
			userBinder = new Binder<>();

			// Create the user input field
			txtUser = new TextField("USERNAME");
			txtUser.setId(USER_NAME_TEXT_FIELD_ID);
			txtUser.setRequiredIndicatorVisible(true);
			txtUser.setPlaceholder("Your username");

			userBinder.forField(txtUser).withValidator(userName -> {
				boolean exit = false;
				try {
					if (userName != null) {
						exit = userName.length() >= 3;
					}
				} catch (Exception error) {
					logger.error(error.toString(),error);
				}
				return exit;
			}, "User name must contain at least three characters").bind(UnloggedRoute::getUserName, UnloggedRoute::setUserName);

			// Create the password input field
			password = new PasswordField("PASSWORD");
			password.setId(PASSWORD_FIELD_ID);
			password.setRequiredIndicatorVisible(true);
			password.setValueChangeMode(ValueChangeMode.EAGER);
			password.setPlaceholder("Password");
			password.addKeyPressListener(Key.ENTER, e -> doLogin());

			userBinder.forField(password).withValidator(passwordString -> {
				boolean exit = false;
				try {
					if (passwordString != null) {
						exit = passwordString.length() >= 3;
					}
				} catch (Exception error) {
					logger.error(error.toString(),error);
				}
				return exit;
			}, "Password must contain at least three characters").bind(UnloggedRoute::getPassWord, UnloggedRoute::setPassWord);

			// Create login button
			loginButton = new Button("Login", event -> doLogin());
			loginButton.setId(LOGIN_BUTTON_ID);

			// Enable button if user and password are correct
			userBinder.setValidationStatusHandler(status -> loginButton.setEnabled(status.isOk()));
			userBinder.setBean(this);

			// Login details field
			FormLayout loginFields = new FormLayout(txtUser, password, loginButton);
			loginFields.setSizeUndefined();
			
			VerticalLayout verticalLayout = new VerticalLayout();
			verticalLayout.setMargin(false);
			verticalLayout.setSpacing(false);
			verticalLayout.setWidth("100%");
			verticalLayout.add(loginFields);
			verticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, loginFields);
			
			Div wrapperPanelFields = new Div();
			wrapperPanelFields.add(verticalLayout);
			wrapperPanelFields.setSizeUndefined();
			wrapperPanelFields.setWidth("500px");
			wrapperPanelFields.getStyle().set("background-color", "#FFFFFF");
			wrapperPanelFields.getStyle().set("border-style", "solid");
			//: solid
			
			Html user4eo = new Html("<center><h1 style=\"color:#FFFFFF;\">ASMADII LOGIN </h1> </center>");
			
			Div wrapperPanelTitle = new Div();
			wrapperPanelTitle.setSizeUndefined();
			wrapperPanelTitle.setWidth("500px");
			wrapperPanelTitle.getStyle().set("background-color", "#8B0000");
			wrapperPanelTitle.getStyle().set("border-style", "solid");
			wrapperPanelTitle.add(user4eo);
			
			VerticalLayout top = new VerticalLayout();
			top.setMargin(true);
			top.setSpacing(false);
			top.add(wrapperPanelTitle);
			top.add(wrapperPanelFields);
			top.setHorizontalComponentAlignment(Alignment.CENTER, wrapperPanelTitle);
			top.setHorizontalComponentAlignment(Alignment.CENTER, wrapperPanelFields);
			
			add(top);
			setHorizontalComponentAlignment(Alignment.CENTER, top);
			
			setClassName("backgroundImageLogin");
		} catch (Exception error) {
			logger.error(error.toString(),error);
		}
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void doLogin() {
		try {
			if (userBinder.validate().isOk()) {

			
			}
		} catch (Exception error) {
			logger.error(error.toString(),error);
		}
	}

}
