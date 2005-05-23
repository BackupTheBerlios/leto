package org.eu.leto.form;

public class PropertyValueModelTest extends AbstractValueModelTest {
    @Override
    protected ValueModel createValueModel() {
        final SimpleBean simpleBean = new SimpleBean();
        simpleBean.setLogin("alex");

        return new PropertyValueModel(application, simpleBean, "login");
    }
}
