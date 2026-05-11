package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.CashBillItems;
import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.entity.CreditBillItems;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public ByteArrayInputStream generateCashBillPdf(CashBill bill) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11);

            // TITLE
            Paragraph title = new Paragraph("CASH BILL", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(Chunk.NEWLINE);

            // CUSTOMER DETAILS
            document.add(new Paragraph("Customer : " + bill.getName(), normalFont));
            document.add(new Paragraph("Bill No : " + bill.getBillNo(), normalFont));
            document.add(new Paragraph("Bill Date : " + bill.getBillDate(), normalFont));

            if (bill.getLorry() != null) {
                document.add(new Paragraph("Lorry : " + bill.getLorry().getName(), normalFont));
            }

            if (bill.getBroker() != null) {
                document.add(new Paragraph("Broker : " + bill.getBroker().getBrokerName(), normalFont));
            }

            document.add(Chunk.NEWLINE);

            // TABLE
            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);

            table.addCell("Product");
            table.addCell("Item");
            table.addCell("Tax Type");
            table.addCell("Rate");
            table.addCell("Qty");
            table.addCell("Tax");
            table.addCell("Total");
            table.addCell("HSN");

            double grandTotal = 0;

            for (CashBillItems item : bill.getItems()) {

                table.addCell(item.getProductCode());
                table.addCell(item.getItemName());
                table.addCell(item.getTaxType());

                table.addCell(String.valueOf(item.getRate()));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(item.getTax());

                table.addCell(String.valueOf(item.getTotal()));

                table.addCell(
                        item.getBrNo() != null
                                ? String.valueOf(item.getBrNo())
                                : ""
                );

                grandTotal += item.getTotal() != null
                        ? item.getTotal()
                        : 0;
            }

            document.add(table);

            document.add(Chunk.NEWLINE);

            // TOTAL
            Paragraph total = new Paragraph(
                    "Grand Total : ₹ " + grandTotal,
                    new Font(Font.HELVETICA, 13, Font.BOLD)
            );

            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(total);

            document.add(Chunk.NEWLINE);

            // REMARKS
            document.add(new Paragraph("Remarks : " + bill.getRemarks(), normalFont));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    public ByteArrayInputStream generateCreditBillPdf(CreditBill bill) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11);

            // TITLE
            Paragraph title = new Paragraph("CREDIT BILL", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(Chunk.NEWLINE);

            // CUSTOMER DETAILS
            document.add(new Paragraph("Customer : " + bill.getName(), normalFont));
            document.add(new Paragraph("Bill No : " + bill.getBillNo(), normalFont));
            document.add(new Paragraph("Bill Date : " + bill.getBillDate(), normalFont));

            if (bill.getLorry() != null) {
                document.add(new Paragraph("Lorry : " + bill.getLorry().getName(), normalFont));
            }

            if (bill.getBroker() != null) {
                document.add(new Paragraph("Broker : " + bill.getBroker().getBrokerName(), normalFont));
            }

            document.add(Chunk.NEWLINE);

            // TABLE
            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);

            table.addCell("Product");
            table.addCell("Item");
            table.addCell("Tax Type");
            table.addCell("Rate");
            table.addCell("Qty");
            table.addCell("Tax");
            table.addCell("Total");
            table.addCell("HSN");

            double grandTotal = 0;

            for (CreditBillItems item : bill.getItems()) {

                table.addCell(item.getProductCode());
                table.addCell(item.getItemName());
                table.addCell(item.getTaxType());

                table.addCell(String.valueOf(item.getRate()));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(item.getTax());

                table.addCell(String.valueOf(item.getTotal()));

                table.addCell(
                        item.getBrNo() != null
                                ? String.valueOf(item.getBrNo())
                                : ""
                );

                grandTotal += item.getTotal() != null
                        ? item.getTotal()
                        : 0;
            }

            document.add(table);

            document.add(Chunk.NEWLINE);

            // TOTAL
            Paragraph total = new Paragraph(
                    "Grand Total : ₹ " + grandTotal,
                    new Font(Font.HELVETICA, 13, Font.BOLD)
            );

            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(total);

            document.add(Chunk.NEWLINE);

            // REMARKS
            document.add(new Paragraph("Remarks : " + bill.getRemarks(), normalFont));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}