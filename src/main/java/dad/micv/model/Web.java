package dad.micv.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Web {

	private StringProperty url = new SimpleStringProperty();

	public Web(String url) {
		setUrl(url);
	}
	
	public final StringProperty urlProperty() {
		return this.url;
	}

	public final String getUrl() {
		return this.urlProperty().get();
	}

	public final void setUrl(final String url) {
		this.urlProperty().set(url);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Web) {
			Web nuevo = (Web) obj;
			return nuevo.getUrl().equals(this.getUrl());
		}
		return super.equals(obj);
	}

}
