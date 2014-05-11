package com.ytk.offcomm;

public class Contact {

	String _name;
	String _phone_number;
	String _email;

	Contact() {

	}

	Contact(String _name, String _phone_number, String _email) {
		this._name = _name;
		this._phone_number = _phone_number;
		this._email = _email;

	}

	
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_phone_number() {
		return _phone_number;
	}

	public void set_phone_number(String _phone_number) {
		this._phone_number = _phone_number;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

}
