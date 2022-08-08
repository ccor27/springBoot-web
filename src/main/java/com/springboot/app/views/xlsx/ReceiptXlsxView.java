package com.springboot.app.views.xlsx;

import com.springboot.app.model.entity.ItemReceipt;
import com.springboot.app.model.entity.Receipt;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("receipt/see.xlsx")
public class ReceiptXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition","attachment; filename=\"receipt_view.xlxs\"");
        MessageSourceAccessor messages = getMessageSourceAccessor();
        Cell cell = null;

        Receipt receipt = (Receipt) model.get("receipt");
        Sheet sheet = workbook.createSheet(messages.getMessage("text.global.xlsx"));

        sheet.createRow(0).createCell(0).setCellValue(messages.getMessage("text.receipt.see.data.customer"));

        sheet.createRow(1).createCell(0).setCellValue(receipt.getCustomer().getName() + " " + receipt.getCustomer().getLastName());

        sheet.createRow(2).createCell(0).setCellValue(receipt.getCustomer().getEmail());

        sheet.createRow(4).createCell(0).setCellValue(messages.getMessage("text.receipt.see.data.receipt"));
        sheet.createRow(5).createCell(0).setCellValue(messages.getMessage("text.customer.receipt.folio") + ": " + receipt.getId());
        sheet.createRow(6).createCell(0).setCellValue(messages.getMessage("text.customer.receipt.description") + ": " + receipt.getDescription());
        sheet.createRow(7).createCell(0).setCellValue(messages.getMessage("text.customer.receipt.date") + ": " + receipt.getCreateAt());

        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillBackgroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(messages.getMessage("text.receipt.form.item.name"));
        header.createCell(1).setCellValue(messages.getMessage("text.receipt.form.item.price"));
        header.createCell(2).setCellValue(messages.getMessage("text.receipt.form.item.amount"));
        header.createCell(3).setCellValue(messages.getMessage("text.receipt.form.item.total"));

        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);

        int num = 10;

        for (ItemReceipt item : receipt.getItems()) {
            Row file = sheet.createRow(num++);
            cell = file.createCell(0);
            cell.setCellValue(item.getProduct().getName());
            cell.setCellStyle(tbodyStyle);

            cell = file.createCell(1);
            cell.setCellValue(item.getProduct().getPrice());
            cell.setCellStyle(tbodyStyle);

            cell = file.createCell(2);
            cell.setCellValue(item.getAmount());
            cell.setCellStyle(tbodyStyle);

            cell = file.createCell(3);
            cell.setCellValue(item.calculateAmount());
            cell.setCellStyle(tbodyStyle);
        }

        Row finalTotal = sheet.createRow(num);
        finalTotal.createCell(2).setCellValue(messages.getMessage("text.receipt.form.total"));
        finalTotal.createCell(3).setCellValue(receipt.getTotal());

    }
}
