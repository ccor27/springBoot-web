package com.springboot.app.views.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.app.model.entity.ItemReceipt;
import com.springboot.app.model.entity.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Locale;
import java.util.Map;

@Component("receipt/see")
public class ReceiptPdfView extends AbstractPdfView {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Receipt receipt = (Receipt) model.get("receipt");

        Locale locale = localeResolver.resolveLocale(request);

        MessageSourceAccessor messages = getMessageSourceAccessor();

        PdfPCell cell = null;

        PdfPTable tableCustomer = new PdfPTable(1);
        tableCustomer.setSpacingAfter(20);

        cell=new PdfPCell(new Phrase(messages.getMessage("text.receipt.see.data.customer")));
        cell.setBackgroundColor(new Color(184,218,255));
        tableCustomer.addCell(cell);

        tableCustomer.addCell(receipt.getCustomer().getName()+" "+receipt.getCustomer().getLastName());
        tableCustomer.addCell(receipt.getCustomer().getEmail());

        PdfPTable tableReceipt = new PdfPTable(1);
        tableReceipt.setSpacingAfter(20);

        cell=new PdfPCell(new Phrase(messages.getMessage("text.receipt.see.data.receipt")));
        cell.setBackgroundColor(new Color(195,230,203));
        tableReceipt.addCell(cell);

        tableReceipt.addCell(messages.getMessage("text.customer.receipt.folio")+": "+receipt.getId());
        tableReceipt.addCell(messages.getMessage("text.customer.receipt.description")+": "+receipt.getDescription());
        tableReceipt.addCell(messages.getMessage("text.customer.receipt.date")+": "+receipt.getCreateAt());

        PdfPTable tableItems = new PdfPTable(4);
        tableItems.setWidths(new float[]{3.5f,1,1,1});
        tableItems.addCell(messages.getMessage("text.receipt.form.item.name"));
        tableItems.addCell(messages.getMessage("text.receipt.form.item.price"));
        tableItems.addCell(messages.getMessage("text.receipt.form.item.amount"));
        tableItems.addCell(messages.getMessage("text.receipt.form.item.total"));

        for (ItemReceipt item:receipt.getItems()) {
           tableItems.addCell(item.getProduct().getName());
            tableItems.addCell(item.getProduct().getPrice().toString());
            cell = new PdfPCell(new Phrase(item.getAmount().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableItems.addCell(cell);
            tableItems.addCell(item.calculateAmount().toString());
        }

        cell = new PdfPCell(new Phrase(messages.getMessage("text.receipt.form.total")+": "));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        tableItems.addCell(cell);
        tableItems.addCell(receipt.getTotal().toString());

        document.add(tableCustomer);
        document.add(tableReceipt);
        document.add(tableItems);
    }
}
