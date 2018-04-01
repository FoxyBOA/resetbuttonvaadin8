package org.vaadin.addons;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.Registration;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.TextField;

import org.vaadin.addons.client.ResetButtonClickRpc;
import org.vaadin.addons.client.ResetButtonForTextFieldState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResetButtonForTextField extends AbstractExtension {
    private final List<ResetButtonClickListener> listeners = new ArrayList<>();

    public static ResetButtonForTextField extend(final TextField field) {
        ResetButtonForTextField resetButton = new ResetButtonForTextField();
        resetButton.extend((AbstractClientConnector) field);
        resetButton.addResetButtonClickedListener(field::clear);
        return resetButton;
    }

    ResetButtonForTextField() {
        ResetButtonClickRpc resetButtonClickRpc = () -> listeners.forEach(ResetButtonClickListener::resetButtonClicked);
        registerRpc(resetButtonClickRpc);
    }

    public Registration addResetButtonClickedListener(ResetButtonClickListener listener) {
        Objects.requireNonNull(listener);

        listeners.add(listener);

        return () -> listeners.remove(listener);
    }

	@Override
	protected ResetButtonForTextFieldState getState() {
		return (ResetButtonForTextFieldState) super.getState();
	}
	
	public void setShowForReadOnly(boolean pValue) {
		getState().showForReadOnly = pValue;
	}
	
	public boolean isShowForReadOnly() {
		return getState().showForReadOnly;
	}
}