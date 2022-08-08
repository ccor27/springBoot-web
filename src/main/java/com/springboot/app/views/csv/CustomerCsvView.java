package com.springboot.app.views.csv;

import com.springboot.app.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.util.Map;

@Component("list")
public class CustomerCsvView extends AbstractView {

    public CustomerCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

       response.setHeader("Content-disposition","attachment; filename=\"customers.csv\"");
       response.setContentType(getContentType());
       Page<Customer> customers = (Page<Customer>) model.get("customers");

       ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
       String[] header = {"id","name","lastName","email","createAt"};
       beanWriter.writeHeader(header);

        for (Customer customer:customers) {
             beanWriter.write(customer,header);
        }

        beanWriter.close();

    }

}
