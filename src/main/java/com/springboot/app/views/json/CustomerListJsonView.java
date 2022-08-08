package com.springboot.app.views.json;

import com.springboot.app.model.entity.Customer;
import com.springboot.app.views.xml.CustomerList;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("list.json")
public class CustomerListJsonView extends MappingJackson2JsonView {
    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("title");
        model.remove("page");
        Page<Customer> customers = (Page<Customer>) model.get("customers");
        model.remove("customers");
        model.put("customerList",customers.getContent());
        return super.filterModel(model);
    }
}
